package fun.nibaba.lazyfish.mybatis.plus.core.interfaces;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * 查询字段封装
 *
 * @author chenjiamin
 * @date 2021/6/3 4:16 下午
 */
public interface LazyQuery<Child extends LazyQuery<Child, MainTableClass>, MainTableClass> {

    /**
     * 查询字段
     *
     * @param column 字段
     * @return 返回包装对象
     */
    default Child select(SFunction<MainTableClass, ?> column) {
        return this.select(column, null);
    }

    /**
     * 查询字段
     *
     * @param column    字段
     * @param aliasName 字段别名
     * @return 返回包装对象
     */
    Child select(SFunction<MainTableClass, ?> column, String aliasName);


    /**
     * 查询字段
     *
     * @param columns 字段数组
     * @return 返回包装对象
     */
    Child select(SFunction<MainTableClass, ?>... columns);

}
