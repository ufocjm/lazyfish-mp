package fun.nibaba.lazyfish.mybatis.plus.core.interfaces;

import fun.nibaba.lazyfish.mybatis.plus.core.wrappers.LazyWhereBuilder;

import java.util.function.Consumer;

/**
 * 条件接口
 *
 * @author chenjiamin
 * @date 2021/12/17 9:59 上午
 */
public interface LazyWhere<Self extends LazyWhere<Self, TableModel>, TableModel> {

    /**
     * 条件构建器
     *
     * @param lazyWhere consumer
     * @return 实现子类
     */
    Self where(Consumer<LazyWhereBuilder<TableModel>> lazyWhere);

}
