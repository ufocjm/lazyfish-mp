package fun.nibaba.lazyfish.mybatis.plus.core.interfaces;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * 分组封装
 *
 * @author chenjiamin
 * @date 2021/6/7 9:40 上午
 */
public interface LazyGroupBy<Child extends LazyGroupBy<Child, TableModel>, TableModel> {

    /**
     * 分组
     *
     * @param column 列名
     * @return 条件封装对象
     */
    default Child groupBy(SFunction<TableModel, ?> column) {
        return groupBy(true, column);
    }

    /**
     * 分组
     *
     * @param condition 执行条件
     * @param column    列名
     * @return 条件封装对象
     */
    Child groupBy(boolean condition, SFunction<TableModel, ?> column);


}
