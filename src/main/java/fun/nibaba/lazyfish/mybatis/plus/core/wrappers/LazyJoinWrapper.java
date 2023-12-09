package fun.nibaba.lazyfish.mybatis.plus.core.wrappers;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import fun.nibaba.lazyfish.mybatis.plus.core.enums.JoinType;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.JoinWehreSegment;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.SelectSegment;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.WhereSegment;

/**
 * 连接查询sql构建器
 *
 * @author chenjiamin
 * @date 2021/12/16 5:25 下午
 */
public class LazyJoinWrapper {

    private final JoinType joinType;

    private final String tableNameAlias;

    private final String tableName;

    private final SelectSegment selectSegment;

    private final JoinWehreSegment joinOnSegment;

    LazyJoinWrapper(JoinType joinType,
                    String tableNameAlias,
                    String tableName,
                    SelectSegment selectSegment,
                    JoinWehreSegment joinOnSegment) {
        this.joinType = joinType;
        this.tableNameAlias = tableNameAlias;
        this.tableName = tableName;
        this.selectSegment = selectSegment;
        this.joinOnSegment = joinOnSegment;
    }

    /**
     * 生成builder
     *
     * @param lazyTable     主表
     * @param joinType      连接key
     * @param lazyJoinTable 关联表
     * @param whereSegment  条件sql片段，整个查询通用一个 whereSegment
     * @param <Main>        主表类型
     * @param <Join>        关联表类型
     * @return builder
     */
    static <Main, Join> LazyJoinWrapperBuilder<Main, Join> builder(LazyTable<Main> lazyTable,
                                                                   JoinType joinType,
                                                                   LazyTable<Join> lazyJoinTable,
                                                                   WhereSegment whereSegment) {
        return LazyJoinWrapperBuilder.builder(lazyTable, joinType, lazyJoinTable, whereSegment);
    }

    /**
     * 提供给Mybatis使用的get方法
     *
     * @return 连接key
     */
    public String getJoinType() {
        return this.joinType.getSqlKey();
    }

    /**
     * 提供给Mybatis使用的get方法
     *
     * @return 表别名+名称
     */
    public String getTableName() {
        return this.tableName + Constants.AS + this.tableNameAlias;
    }

    /**
     * 提供给Mybatis使用的get方法
     *
     * @return select 字段
     */
    public String getSqlSelect() {
        return selectSegment.getSqlSegment();
    }

    /**
     * 提供给Mybatis使用的get方法
     *
     * @return on条件
     */
    public String getJoinOnSegmentSql() {
        return StringPool.ON + StringPool.SPACE + this.joinOnSegment.getSqlSegment();
    }

    /**
     * 获取join 条件对象
     *
     * @return JoinOnSegment
     */
    JoinWehreSegment getJoinOnSegment() {
        return this.joinOnSegment;
    }

}
