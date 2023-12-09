package fun.nibaba.lazyfish.mybatis.plus.core.functions;

import java.io.Serializable;

/**
 * 值 function
 *
 * @author chenjiamin
 * @date 2021/6/2 4:45 下午
 */
@FunctionalInterface
public interface ValueFunction<T> extends Serializable {

    /**
     * 获取值
     *
     * @return 值
     */
    T getValue();

}
