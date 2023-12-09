package fun.nibaba.lazyfish.mybatis.plus.core.exceptions;

/**
 * 自定义异常
 *
 * @author chenjiamin
 * @date 2021/6/4 5:24 下午
 */
public class LazyMybatisPlusException extends RuntimeException {

    public LazyMybatisPlusException() {
        super();
    }

    public LazyMybatisPlusException(String message) {
        super(message);
    }
}
