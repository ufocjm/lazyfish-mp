package fun.nibaba.lazyfish.mybatis.plus.core.interfaces;

import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * 查询条件封装
 * join 后面的条件
 *
 * @author chenjiamin
 * @date 2021/6/4 9:59 上午
 */
public interface LazyJoinCompare<Child extends LazyJoinCompare<Child, Main, Join>, Main, Join> {

    /**
     * 等于
     *
     * @param leftColumn  左列
     * @param rightColumn 右列
     * @return this
     */
    default Child eq(SFunction<Main, ?> leftColumn, SFunction<Join, ?> rightColumn) {
        return eq(true, leftColumn, rightColumn);
    }

    /**
     * 等于
     *
     * @param condition   条件
     * @param leftColumn  左列
     * @param rightColumn 右列
     * @return this
     */
    default Child eq(boolean condition, SFunction<Main, ?> leftColumn, SFunction<Join, ?> rightColumn) {
        return compare(condition, leftColumn, SqlKeyword.EQ, rightColumn);
    }

    /**
     * 不等于
     *
     * @param leftColumn  左列
     * @param rightColumn 右列
     * @return this
     */
    default Child ne(SFunction<Main, ?> leftColumn, SFunction<Join, ?> rightColumn) {
        return ne(true, leftColumn, rightColumn);
    }

    /**
     * 不等于
     *
     * @param condition   条件
     * @param leftColumn  左列
     * @param rightColumn 右列
     * @return this
     */
    default Child ne(boolean condition, SFunction<Main, ?> leftColumn, SFunction<Join, ?> rightColumn) {
        return compare(condition, leftColumn, SqlKeyword.NE, rightColumn);
    }

    /**
     * 大于
     *
     * @param leftColumn  左列
     * @param rightColumn 右列
     * @return this
     */
    default Child gt(SFunction<Main, ?> leftColumn, SFunction<Join, ?> rightColumn) {
        return gt(true, leftColumn, rightColumn);
    }

    /**
     * 大于
     *
     * @param condition   条件
     * @param leftColumn  左列
     * @param rightColumn 右列
     * @return this
     */
    default Child gt(boolean condition, SFunction<Main, ?> leftColumn, SFunction<Join, ?> rightColumn) {
        return compare(condition, leftColumn, SqlKeyword.GT, rightColumn);
    }

    /**
     * 大于等于
     *
     * @param leftColumn  左列
     * @param rightColumn 右列
     * @return this
     */
    default Child ge(SFunction<Main, ?> leftColumn, SFunction<Join, ?> rightColumn) {
        return ge(true, leftColumn, rightColumn);
    }

    /**
     * 大于等于
     *
     * @param condition   条件
     * @param leftColumn  左列
     * @param rightColumn 右列
     * @return this
     */
    default Child ge(boolean condition, SFunction<Main, ?> leftColumn, SFunction<Join, ?> rightColumn) {
        return compare(condition, leftColumn, SqlKeyword.GE, rightColumn);
    }

    /**
     * 小于
     *
     * @param leftColumn  左列
     * @param rightColumn 右列
     * @return this
     */
    default Child lt(SFunction<Main, ?> leftColumn, SFunction<Join, ?> rightColumn) {
        return lt(true, leftColumn, rightColumn);
    }

    /**
     * 小于
     *
     * @param condition   条件
     * @param leftColumn  左列
     * @param rightColumn 右列
     * @return this
     */
    default Child lt(boolean condition, SFunction<Main, ?> leftColumn, SFunction<Join, ?> rightColumn) {
        return compare(condition, leftColumn, SqlKeyword.LT, rightColumn);
    }

    /**
     * 小于等于
     *
     * @param leftColumn  左列
     * @param rightColumn 右列
     * @return this
     */
    default Child le(SFunction<Main, ?> leftColumn, SFunction<Join, ?> rightColumn) {
        return le(true, leftColumn, rightColumn);
    }

    /**
     * 小于等于
     *
     * @param condition   条件
     * @param leftColumn  左列
     * @param rightColumn 右列
     * @return this
     */
    default Child le(boolean condition, SFunction<Main, ?> leftColumn, SFunction<Join, ?> rightColumn) {
        return compare(condition, leftColumn, SqlKeyword.LE, rightColumn);
    }

    /**
     * 比较
     *
     * @param condition   条件
     * @param leftColumn  左列
     * @param sqlKeyword  比较方式
     * @param rightColumn 右列
     * @return this
     */
    Child compare(boolean condition, SFunction<Main, ?> leftColumn, SqlKeyword sqlKeyword, SFunction<Join, ?> rightColumn);


}
