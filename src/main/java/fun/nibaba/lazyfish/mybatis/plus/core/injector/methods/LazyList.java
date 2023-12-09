package fun.nibaba.lazyfish.mybatis.plus.core.injector.methods;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import fun.nibaba.lazyfish.mybatis.plus.core.enums.LazySqlMethod;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.Map;

/**
 * 关联列表查询
 *
 * @author chenjiamin
 * @date 2021/5/31 4:28 下午
 */
public class LazyList extends AbstractMethod {

    private final static String WRAPPER_TABLE_NAME_ALIAS = WRAPPER_DOT + "tableNameAlias";

    private final static String WRAPPER_JOIN_WRAPPER_LIST = WRAPPER_DOT + "joinWrapperList";

    private final static String JOIN_WRAPPER = "joinWrapper";

    private final static String WHERE_SEGMENT = "whereSegment";

    private final static String JOIN_ON_SEGMENT_SQL = "joinOnSegmentSql";

    private final static String WRAPPER_WHERE_SEGMENT = WRAPPER_DOT + WHERE_SEGMENT;

    private final static String GROUP_BY_SEGMENT = "groupBySegment";

    private final static String WRAPPER_GROUP_BY_SEGMENT = WRAPPER_DOT + GROUP_BY_SEGMENT;

    private final static String ORDER_BY_SEGMENT = "orderBySegment";

    private final static String WRAPPER_ORDER_BY_SEGMENT = WRAPPER_DOT + ORDER_BY_SEGMENT;

    private final static String LAST_SQL = WRAPPER_DOT + "lastSql";

    public LazyList(String methodName) {
        super(methodName);
    }


    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        LazySqlMethod sqlMethod = LazySqlMethod.LAZY_LIST;
        String sql = String.format(sqlMethod.getSql(),
                this.sqlSelectColumns(tableInfo, true),
                tableInfo.getTableName(),
                this.tableNameAlias(),
                //join
                this.joinSegment(),
                this.where(),
                this.groupBy(),
                this.orderBy(),
                //last
                this.last()
        );
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addSelectMappedStatementForOther(mapperClass, sqlMethod.getMethod(), sqlSource, Map.class);
    }

    /**
     * 查询字段
     *
     * @param table
     * @param queryWrapper
     * @return
     */
    @Override
    protected String sqlSelectColumns(TableInfo table, boolean queryWrapper) {
        return SqlScriptUtils.convertChoose(String.format("%s != null and %s != null", WRAPPER, Q_WRAPPER_SQL_SELECT),
                SqlScriptUtils.unSafeParam(Q_WRAPPER_SQL_SELECT), ASTERISK);

    }

    /**
     * 表别名
     *
     * @return 表别名
     */
    protected String tableNameAlias() {
        return AS + SqlScriptUtils.unSafeParam(WRAPPER_TABLE_NAME_ALIAS);
    }

    /**
     * 关联 sql 片段
     *
     * @return
     */
    protected String joinSegment() {
        return SqlScriptUtils.convertIf(this.joinSegmentList(),
                String.format("%s != null and %s != null and %s.size() > 0", WRAPPER, WRAPPER_JOIN_WRAPPER_LIST, WRAPPER_JOIN_WRAPPER_LIST),
                true);
    }

    /**
     * 关联 sql 片段
     *
     * @return
     */
    private String joinSegmentList() {
        return SqlScriptUtils.convertForeach(this.joinType(JOIN_WRAPPER) + SPACE + this.joinTableName(JOIN_WRAPPER) + SPACE + this.joinWhereSegment(JOIN_WRAPPER),
                WRAPPER_JOIN_WRAPPER_LIST,
                null,
                JOIN_WRAPPER, NEWLINE);
    }

    /**
     * 关联类型
     *
     * @param item foreach item
     * @return 关联类型
     */
    private String joinType(String item) {
        return SqlScriptUtils.unSafeParam(item + DOT + "joinType");
    }

    /**
     * 关联表
     *
     * @param item foreach item
     * @return 关联查的条件
     */
    private String joinTableName(String item) {
        return SqlScriptUtils.unSafeParam(item + DOT + "tableName");
    }

    /**
     * 关联查的条件
     *
     * @param item foreach item
     * @return 关联查的条件
     */
    private String joinWhereSegment(String item) {
        return SqlScriptUtils.convertIf(SqlScriptUtils.unSafeParam(item + DOT + JOIN_ON_SEGMENT_SQL),
                String.format("%s != null", item + DOT + JOIN_ON_SEGMENT_SQL),
                true);
    }

    /**
     * where 条件
     *
     * @return where 条件
     */
    protected String where() {
        String sqlScript = SqlScriptUtils.convertIf(SqlScriptUtils.unSafeParam(WRAPPER_WHERE_SEGMENT), String.format("%s != null and %s != null", WRAPPER, WRAPPER_WHERE_SEGMENT), true);
        return SqlScriptUtils.convertWhere(sqlScript);
    }

    /**
     * 分组 sql
     *
     * @return where 条件
     */
    protected String groupBy() {
        return SqlScriptUtils.convertIf(SqlScriptUtils.unSafeParam(WRAPPER_GROUP_BY_SEGMENT),
                String.format("%s != null and %s != null", WRAPPER, WRAPPER_GROUP_BY_SEGMENT),
                true);
    }

    /**
     * 排序 sql
     *
     * @return where 条件
     */
    protected String orderBy() {
        return SqlScriptUtils.convertIf(SqlScriptUtils.unSafeParam(WRAPPER_ORDER_BY_SEGMENT),
                String.format("%s != null and %s != null", WRAPPER, WRAPPER_ORDER_BY_SEGMENT),
                true);
    }

    /**
     * 末尾sql
     *
     * @return
     */
    protected String last() {
        return SqlScriptUtils.convertIf(SqlScriptUtils.unSafeParam(LAST_SQL),
                String.format("%s != null and %s != null", WRAPPER, LAST_SQL),
                true);
    }

}
