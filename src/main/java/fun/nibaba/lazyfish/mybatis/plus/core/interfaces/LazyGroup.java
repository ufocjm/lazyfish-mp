package fun.nibaba.lazyfish.mybatis.plus.core.interfaces;

import fun.nibaba.lazyfish.mybatis.plus.core.wrappers.LazyGroupBuilder;

import java.util.function.Consumer;

/**
 * 分组接口
 *
 * @author chenjiamin
 * @date 2021/12/17 10:19 上午
 */
public interface LazyGroup<Self extends LazyGroup<Self, TableModel>, TableModel> {

    /**
     * 分组构建器
     *
     * @param lazyGroup consumer
     * @return 实现子类
     */
    Self group(Consumer<LazyGroupBuilder<TableModel>> lazyGroup);

}
