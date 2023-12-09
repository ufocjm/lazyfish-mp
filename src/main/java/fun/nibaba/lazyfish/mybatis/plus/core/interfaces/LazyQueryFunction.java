package fun.nibaba.lazyfish.mybatis.plus.core.interfaces;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import fun.nibaba.lazyfish.mybatis.plus.core.enums.LazyFunction;

/**
 * 常用sql 函数
 *
 * @author chenjiamin
 * @date 2021/6/3 4:16 下午
 */
public interface LazyQueryFunction<Child extends LazyQueryFunction<Child, MainTableClass>, MainTableClass> {

    /**
     * count
     *
     * @param column    字段
     * @param aliasName 字段别名
     * @return 返回包装对象
     */
    default Child count(SFunction<MainTableClass, ?> column, String aliasName) {
        return this.fun(column, aliasName, LazyFunction.COUNT);
    }

    /**
     * sum
     *
     * @param column    字段
     * @param aliasName 字段别名
     * @return 返回包装对象
     */
    default Child sum(SFunction<MainTableClass, ?> column, String aliasName) {
        return this.fun(column, aliasName, LazyFunction.SUM);
    }

    /**
     * avg
     *
     * @param column    字段
     * @param aliasName 字段别名
     * @return 返回包装对象
     */
    default Child avg(SFunction<MainTableClass, ?> column, String aliasName) {
        return this.fun(column, aliasName, LazyFunction.AVG);
    }

    /**
     * max
     *
     * @param column    字段
     * @param aliasName 字段别名
     * @return 返回包装对象
     */
    default Child max(SFunction<MainTableClass, ?> column, String aliasName) {
        return this.fun(column, aliasName, LazyFunction.MAX);
    }

    /**
     * min
     *
     * @param column    字段
     * @param aliasName 字段别名
     * @return 返回包装对象
     */
    default Child min(SFunction<MainTableClass, ?> column, String aliasName) {
        return this.fun(column, aliasName, LazyFunction.MIN);
    }


    /**
     * 执行函数
     *
     * @param column       字段
     * @param aliasName    字段别名
     * @param lazyFunction 函数
     * @return 返回包装对象
     */
    Child fun(SFunction<MainTableClass, ?> column, String aliasName, LazyFunction lazyFunction);

}
