package fun.nibaba.lazyfish.mybatis.plus.core.interfaces;

import fun.nibaba.lazyfish.mybatis.plus.core.wrappers.LazyOnBuilder;

import java.util.function.Consumer;

/**
 * 关联查询接口
 *
 * @author chenjiamin
 * @date 2021/12/17 10:34 上午
 */
public interface LazyOn<Self extends LazyOn<Self, MainTableModel, JoinTableModel>, MainTableModel, JoinTableModel> {

    /**
     * 关联查询构建器
     *
     * @param lazyOn consumer
     * @return 实现子类
     */
    Self on(Consumer<LazyOnBuilder<MainTableModel, JoinTableModel>> lazyOn);

}
