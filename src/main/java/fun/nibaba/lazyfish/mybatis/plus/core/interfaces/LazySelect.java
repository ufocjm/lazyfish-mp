package fun.nibaba.lazyfish.mybatis.plus.core.interfaces;

import fun.nibaba.lazyfish.mybatis.plus.core.wrappers.LazySelectBuilder;

import java.util.function.Consumer;

/**
 * 查询接口
 *
 * @author chenjiamin
 * @date 2021/12/16 6:27 下午
 */
public interface LazySelect<Self extends LazySelect<Self, TableModel>, TableModel> {

    /**
     * 查询
     *
     * @param lazySelect consumer
     * @return 实现子类
     */
    Self select(Consumer<LazySelectBuilder<TableModel>> lazySelect);


}
