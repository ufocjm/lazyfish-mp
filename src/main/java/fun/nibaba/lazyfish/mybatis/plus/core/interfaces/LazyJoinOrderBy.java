package fun.nibaba.lazyfish.mybatis.plus.core.interfaces;

import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import fun.nibaba.lazyfish.mybatis.plus.core.wrappers.LazyTable;

/**
 * 联表排序封装
 *
 * @author chenjiamin
 * @date 2021/6/4 5:32 下午
 */
public interface LazyJoinOrderBy<Child extends LazyJoinOrderBy<Child>> {


    /**
     * 排序-升序
     *
     * @param lazyTable 表
     * @param column    列名
     * @return 条件封装对象
     */
    default <TableModel> Child orderByAsc(LazyTable<TableModel> lazyTable, SFunction<TableModel, ?> column) {
        return orderByAsc(true, lazyTable, column);
    }

    /**
     * 排序-升序
     *
     * @param condition 执行条件
     * @param lazyTable 表
     * @param column    列名
     * @return 条件封装对象
     */
    default <TableModel> Child orderByAsc(boolean condition, LazyTable<TableModel> lazyTable, SFunction<TableModel, ?> column) {
        return orderBy(condition, lazyTable, column, SqlKeyword.ASC);
    }

    /**
     * 排序-降序
     *
     * @param lazyTable 表
     * @param column    列名
     * @return 条件封装对象
     */
    default <TableModel> Child orderByDesc(LazyTable<TableModel> lazyTable, SFunction<TableModel, ?> column) {
        return orderByDesc(true, lazyTable, column);
    }

    /**
     * 排序-降序
     *
     * @param condition 执行条件
     * @param lazyTable 表
     * @param column    列名
     * @return 条件封装对象
     */
    default <TableModel> Child orderByDesc(boolean condition, LazyTable<TableModel> lazyTable, SFunction<TableModel, ?> column) {
        return orderBy(condition, lazyTable, column, SqlKeyword.DESC);
    }

    /**
     * 排序
     *
     * @param condition 执行条件
     * @param lazyTable 表
     * @param column    列名
     * @param orderRule 排序规则
     * @return 条件封装对象
     */
    <TableModel> Child orderBy(boolean condition, LazyTable<TableModel> lazyTable, SFunction<TableModel, ?> column, SqlKeyword orderRule);

}
