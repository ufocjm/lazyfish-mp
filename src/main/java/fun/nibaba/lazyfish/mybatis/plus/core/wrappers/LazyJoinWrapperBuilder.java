package fun.nibaba.lazyfish.mybatis.plus.core.wrappers;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import fun.nibaba.lazyfish.mybatis.plus.core.enums.JoinType;
import fun.nibaba.lazyfish.mybatis.plus.core.interfaces.LazyOn;
import fun.nibaba.lazyfish.mybatis.plus.core.interfaces.LazySelect;
import fun.nibaba.lazyfish.mybatis.plus.core.interfaces.LazyWhere;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.JoinWehreSegment;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.SelectSegment;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.WhereSegment;

import java.util.function.Consumer;

/**
 * 关联查询构建器 builder
 *
 * @author chenjiamin
 * @date 2021/12/16 5:25 下午
 */
public class LazyJoinWrapperBuilder<Main, Join> implements
        LazySelect<LazyJoinWrapperBuilder<Main, Join>, Join>,
        LazyWhere<LazyJoinWrapperBuilder<Main, Join>, Join>,
        LazyOn<LazyJoinWrapperBuilder<Main, Join>, Main, Join>,
        Constants {

    private final LazyTable<Main> lazyTable;

    private final JoinType joinType;

    private final LazyTable<Join> lazyJoinTable;

    private final SelectSegment selectSegment;

    private final WhereSegment whereSegment;

    private final JoinWehreSegment joinOnSegment;


    private LazyJoinWrapperBuilder(
            LazyTable<Main> lazyTable,
            JoinType joinType,
            LazyTable<Join> lazyJoinTable,
            WhereSegment whereSegment) {
        this.lazyTable = lazyTable;
        this.joinType = joinType;
        this.lazyJoinTable = lazyJoinTable;
        this.selectSegment = new SelectSegment(lazyJoinTable.getTableNameAlia());
        this.whereSegment = whereSegment;
        this.joinOnSegment = new JoinWehreSegment();
    }

    /**
     * 关联查询构建器
     *
     * @param lazyTable     主表
     * @param joinType      关联key
     * @param lazyJoinTable 关联表
     * @param whereSegment  构建器唯一的 where条件sql片段
     * @param <Main>        主表类型
     * @param <Join>        子表类型
     * @return 关联查询构建器
     */
    static <Main, Join> LazyJoinWrapperBuilder<Main, Join> builder(LazyTable<Main> lazyTable,
                                                                   JoinType joinType,
                                                                   LazyTable<Join> lazyJoinTable,
                                                                   WhereSegment whereSegment) {
        return new LazyJoinWrapperBuilder<>(lazyTable, joinType, lazyJoinTable, whereSegment);
    }

    /**
     * 查询构建器
     *
     * @param lazySelect consumer
     * @return this
     */
    @Override
    public LazyJoinWrapperBuilder<Main, Join> select(Consumer<LazySelectBuilder<Join>> lazySelect) {
        LazySelectBuilder<Join> builder = LazySelectBuilder.builder(lazyJoinTable);
        lazySelect.accept(builder);
        this.selectSegment.addAll(builder.selectSegment);
        return this;
    }

    /**
     * 条件构建器
     *
     * @param lazyWhere consumer
     * @return this
     */
    @Override
    public LazyJoinWrapperBuilder<Main, Join> where(Consumer<LazyWhereBuilder<Join>> lazyWhere) {
        LazyWhereBuilder<Join> builder = LazyWhereBuilder.builder(this.lazyJoinTable, this.whereSegment);
        lazyWhere.accept(builder);
        return this;
    }

    /**
     * on 条件构建器
     * 主要用于两张表直接的比对
     *
     * @param lazyOn consumer
     * @return this
     */
    @Override
    public LazyJoinWrapperBuilder<Main, Join> on(Consumer<LazyOnBuilder<Main, Join>> lazyOn) {
        LazyOnBuilder<Main, Join> builder = LazyOnBuilder.builder(this.lazyTable, this.lazyJoinTable, this.joinOnSegment);
        lazyOn.accept(builder);
        return this;
    }

    /**
     * 构建
     *
     * @return 连接查询sql构建器
     */
    LazyJoinWrapper build() {
        return new LazyJoinWrapper(
                this.joinType,
                this.lazyJoinTable.getTableNameAlia(),
                this.lazyJoinTable.getTableName(),
                this.selectSegment,
                this.joinOnSegment
        );
    }

}
