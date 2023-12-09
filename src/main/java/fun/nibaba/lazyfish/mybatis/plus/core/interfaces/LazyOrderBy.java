package fun.nibaba.lazyfish.mybatis.plus.core.interfaces;

import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * 排序封装
 *
 * @author chenjiamin
 * @date 2021/6/4 5:32 下午
 */
public interface LazyOrderBy<Child extends LazyOrderBy<Child,TableModel>,TableModel> {


    /**
     * 排序-升序
     *
     * @param column 列名
     * @return 条件封装对象
     */
    default Child orderByAsc(SFunction<TableModel, ?> column) {
        return orderByAsc(true, column);
    }

    /**
     * 排序-升序
     *
     * @param condition 执行条件
     * @param column    列名
     * @return 条件封装对象
     */
    default Child orderByAsc(boolean condition, SFunction<TableModel, ?> column) {
        return orderBy(condition, column, SqlKeyword.ASC);
    }

    /**
     * 排序-降序
     *
     * @param column 列名
     * @return 条件封装对象
     */
    default Child orderByDesc(SFunction<TableModel, ?> column) {
        return orderByDesc(true, column);
    }

    /**
     * 排序-降序
     *
     * @param condition 执行条件
     * @param column    列名
     * @return 条件封装对象
     */
    default Child orderByDesc(boolean condition, SFunction<TableModel, ?> column) {
        return orderBy(condition, column, SqlKeyword.DESC);
    }

    /**
     * 排序
     *
     * @param condition 执行条件
     * @param column    列名
     * @param orderRule 排序规则
     * @return 条件封装对象
     */
    Child orderBy(boolean condition, SFunction<TableModel, ?> column, SqlKeyword orderRule);

}
