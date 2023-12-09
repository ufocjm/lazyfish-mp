package fun.nibaba.lazyfish.mybatis.plus.core.interfaces;

import com.baomidou.mybatisplus.core.enums.SqlKeyword;

import java.util.function.Consumer;

/**
 * 查询条件封装
 * 嵌套
 *
 * @author chenjiamin
 * @date 2021/6/3 5:22 下午
 */
public interface LazyNested<Child extends LazyNested<Child>> {

    /**
     * ignore
     */
    default Child and() {
        return this.and(null);
    }

    /**
     * ignore
     */
    default Child and(Consumer<Child> childConsumer) {
        return this.and(true, childConsumer);
    }

    /**
     * and 条件
     *
     * @param condition 执行条件
     * @param consumer  sql拼装实体
     * @return sql拼装实体
     */
    default Child and(boolean condition, Consumer<Child> consumer) {
        return nested(condition, consumer, SqlKeyword.AND);
    }

    /**
     * ignore
     */
    default Child or() {
        return this.or(null);
    }

    /**
     * ignore
     */
    default Child or(Consumer<Child> childConsumer) {
        return this.or(true, childConsumer);
    }

    /**
     * or 条件
     *
     * @param condition 执行条件
     * @param consumer  sql拼装实体
     * @return sql拼装实体
     */
    default Child or(boolean condition, Consumer<Child> consumer) {
        return nested(condition, consumer, SqlKeyword.OR);
    }

    /**
     * 嵌套查询
     *
     * @param condition  执行条件
     * @param consumer   sql拼装实体
     * @param sqlKeyword 连接关键字 AND / OR
     * @return
     */
    Child nested(boolean condition, Consumer<Child> consumer, SqlKeyword sqlKeyword);

}
