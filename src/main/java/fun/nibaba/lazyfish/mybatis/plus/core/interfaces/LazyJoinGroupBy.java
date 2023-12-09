package fun.nibaba.lazyfish.mybatis.plus.core.interfaces;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import fun.nibaba.lazyfish.mybatis.plus.core.wrappers.LazyTable;

/**
 * 联表分组封装
 *
 * @author chenjiamin
 * @date 2021/6/7 9:40 上午
 */
public interface LazyJoinGroupBy<Child extends LazyJoinGroupBy<Child>> {

    /**
     * 分组
     *
     * @param lazyTable 表
     * @param column    列名
     * @return 条件封装对象
     */
    default <TableModel> Child groupBy(LazyTable<TableModel> lazyTable, SFunction<TableModel, ?> column) {
        return groupBy(true, lazyTable, column);
    }

    /**
     * 分组
     *
     * @param condition 执行条件
     * @param lazyTable 表
     * @param column    列名
     * @return 条件封装对象
     */
    <TableModel> Child groupBy(boolean condition, LazyTable<TableModel> lazyTable, SFunction<TableModel, ?> column);


}
