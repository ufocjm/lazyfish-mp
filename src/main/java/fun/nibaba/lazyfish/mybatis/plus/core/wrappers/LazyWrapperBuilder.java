package fun.nibaba.lazyfish.mybatis.plus.core.wrappers;

import cn.hutool.core.util.StrUtil;
import fun.nibaba.lazyfish.mybatis.plus.core.enums.JoinType;
import fun.nibaba.lazyfish.mybatis.plus.core.exceptions.LazyMybatisPlusException;
import fun.nibaba.lazyfish.mybatis.plus.core.interfaces.LazyGroup;
import fun.nibaba.lazyfish.mybatis.plus.core.interfaces.LazyOrder;
import fun.nibaba.lazyfish.mybatis.plus.core.interfaces.LazySelect;
import fun.nibaba.lazyfish.mybatis.plus.core.interfaces.LazyWhere;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.GroupBySegment;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.OrderBySegment;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.SelectSegment;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.WhereSegment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import static com.baomidou.mybatisplus.core.toolkit.Constants.AS;

/**
 * 查询sql构建器 builder
 *
 * @author chenjiamin
 * @date 2021/12/14 11:13 上午
 */
public class LazyWrapperBuilder<TableModel> implements
        LazySelect<LazyWrapperBuilder<TableModel>, TableModel>,
        LazyWhere<LazyWrapperBuilder<TableModel>, TableModel>,
        LazyOrder<LazyWrapperBuilder<TableModel>, TableModel>,
        LazyGroup<LazyWrapperBuilder<TableModel>, TableModel> {

    private final LazyTable<TableModel> lazyTable;

    private final SelectSegment selectSegment;

    private final WhereSegment whereSegment;

    private final GroupBySegment groupBySegment;

    private final OrderBySegment orderBySegment;

    private String lastSql;

    private final List<LazyJoinWrapper> joinSegmentList = new ArrayList<>();


    private LazyWrapperBuilder(LazyTable<TableModel> lazyTable) {
        this(
                lazyTable,
                new SelectSegment(lazyTable.getTableNameAlia()),
                new WhereSegment(),
                new GroupBySegment(),
                new OrderBySegment()
        );
    }

    private LazyWrapperBuilder(LazyTable<TableModel> lazyTable,
                               SelectSegment selectSegment,
                               WhereSegment whereSegment,
                               GroupBySegment groupBySegment,
                               OrderBySegment orderBySegment) {
        this.lazyTable = lazyTable;
        this.selectSegment = selectSegment;
        this.whereSegment = whereSegment;
        this.groupBySegment = groupBySegment;
        this.orderBySegment = orderBySegment;
    }

    /**
     * builder
     *
     * @param lazyTable    表
     * @param <TableModel> 表类型
     * @return builder
     */
    static <TableModel> LazyWrapperBuilder<TableModel> builder(LazyTable<TableModel> lazyTable) {
        return new LazyWrapperBuilder<>(lazyTable);
    }

    /**
     * 查询构建器
     *
     * @param lazySelect consumer
     * @return this
     */
    @Override
    public LazyWrapperBuilder<TableModel> select(Consumer<LazySelectBuilder<TableModel>> lazySelect) {
        LazySelectBuilder<TableModel> builder = LazySelectBuilder.builder(this.lazyTable);
        lazySelect.accept(builder);
        this.selectSegment.addAll(builder.selectSegment);
        return this;
    }

    /**
     * left join 构建器
     *
     * @param lazyTable           主表
     * @param lazyJoinTable       关联表
     * @param joinBuilderConsumer 关联查询consumer
     * @param <Main>              主表类型
     * @param <Join>              子表类型
     * @return this
     */
    public <Main, Join> LazyWrapperBuilder<TableModel> leftJoin(LazyTable<Main> lazyTable,
                                                                LazyTable<Join> lazyJoinTable,
                                                                Consumer<LazyJoinWrapperBuilder<Main, Join>> joinBuilderConsumer) {
        return this.join(lazyTable, JoinType.LEFT_JOIN, lazyJoinTable, joinBuilderConsumer);
    }

    /**
     * inner join 构建器
     *
     * @param lazyTable           主表
     * @param lazyJoinTable       关联表
     * @param joinBuilderConsumer 关联查询consumer
     * @param <Main>              主表类型
     * @param <Join>              子表类型
     * @return this
     */
    public <Main, Join> LazyWrapperBuilder<TableModel> innerJoin(LazyTable<Main> lazyTable,
                                                                 LazyTable<Join> lazyJoinTable,
                                                                 Consumer<LazyJoinWrapperBuilder<Main, Join>> joinBuilderConsumer) {
        return this.join(lazyTable, JoinType.INNER_JOIN, lazyJoinTable, joinBuilderConsumer);
    }

    /**
     * right join 构建器
     *
     * @param lazyTable           主表
     * @param lazyJoinTable       关联表
     * @param joinBuilderConsumer 关联查询consumer
     * @param <Main>              主表类型
     * @param <Join>              子表类型
     * @return this
     */
    public <Main, Join> LazyWrapperBuilder<TableModel> rightJoin(LazyTable<Main> lazyTable,
                                                                 LazyTable<Join> lazyJoinTable,
                                                                 Consumer<LazyJoinWrapperBuilder<Main, Join>> joinBuilderConsumer) {
        return this.join(lazyTable, JoinType.RIGHT_JOIN, lazyJoinTable, joinBuilderConsumer);
    }

    /**
     * join 构建器
     *
     * @param lazyTable           主表
     * @param joinType            关联key
     * @param lazyJoinTable       关联表
     * @param joinBuilderConsumer 关联查询consumer
     * @param <Main>              主表类型
     * @param <Join>              子表类型
     * @return this
     */
    private <Main, Join> LazyWrapperBuilder<TableModel> join(LazyTable<Main> lazyTable,
                                                             JoinType joinType,
                                                             LazyTable<Join> lazyJoinTable,
                                                             Consumer<LazyJoinWrapperBuilder<Main, Join>> joinBuilderConsumer) {
        LazyJoinWrapperBuilder<Main, Join> builder = LazyJoinWrapper.builder(lazyTable, joinType, lazyJoinTable, this.whereSegment);
        joinBuilderConsumer.accept(builder);
        this.joinSegmentList.add(builder.build());
        return this;
    }

    /**
     * 条件构建器
     *
     * @param lazyWhere consumer
     * @return this
     */
    @Override
    public LazyWrapperBuilder<TableModel> where(Consumer<LazyWhereBuilder<TableModel>> lazyWhere) {
        LazyWhereBuilder<TableModel> builder = LazyWhereBuilder.builder(this.lazyTable, this.whereSegment);
        lazyWhere.accept(builder);
        return this;
    }

    /**
     * group by构建器
     *
     * @param lazyGroup consumer
     * @return this
     */
    @Override
    public LazyWrapperBuilder<TableModel> group(Consumer<LazyGroupBuilder<TableModel>> lazyGroup) {
        LazyGroupBuilder<TableModel> builder = LazyGroupBuilder.builder(this.lazyTable);
        lazyGroup.accept(builder);
        this.groupBySegment.addAll(builder.groupBySegment);
        return this;
    }

    /**
     * 排序构建器
     *
     * @param lazyOrder consumer
     * @return this
     */
    @Override
    public LazyWrapperBuilder<TableModel> order(Consumer<LazyOrderBuilder<TableModel>> lazyOrder) {
        LazyOrderBuilder<TableModel> builder = LazyOrderBuilder.build(this.lazyTable);
        lazyOrder.accept(builder);
        this.orderBySegment.addAll(builder.orderBySegment);
        return this;
    }

    /**
     * 在末尾增加sql
     *
     * @param lastSql 末尾sql
     * @return this
     */
    public LazyWrapperBuilder<TableModel> lastSql(String lastSql) {
        this.lastSql = StrUtil.SPACE + lastSql;
        return this;
    }

    /**
     * 生成查询sql构建器
     *
     * @return 查询sql构建器
     */
    public LazyWrapper build() {
        // 做一层简单的校验
        this.duplicateTableAlias();

        return new LazyWrapper(
                this.selectSegment,
                this.lazyTable.getTableNameAlia(),
                this.joinSegmentList,
                this.whereSegment,
                this.groupBySegment,
                this.orderBySegment,
                this.lastSql
        );
    }

    /**
     * 验证表明和别名是否冲突
     */
    private void duplicateTableAlias() {
        // 校验 表别名是否冲突
        Set<String> tableNameSet = new HashSet<>();
        tableNameSet.add(this.lazyTable.getTableName() + AS + this.lazyTable.getTableNameAlia());
        this.joinSegmentList.forEach(joinSegment -> {
            if (tableNameSet.contains(joinSegment.getTableName())) {
                throw new LazyMybatisPlusException("duplicate tableAlias [" + joinSegment.getTableName() + "]");
            } else {
                tableNameSet.add(joinSegment.getTableName());
            }
        });
    }

}
