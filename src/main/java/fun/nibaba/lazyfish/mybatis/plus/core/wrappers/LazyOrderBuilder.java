package fun.nibaba.lazyfish.mybatis.plus.core.wrappers;

import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import fun.nibaba.lazyfish.mybatis.plus.core.interfaces.LazyJoinOrderBy;
import fun.nibaba.lazyfish.mybatis.plus.core.interfaces.LazyOrderBy;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.ColumnSegment;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.OrderBySegment;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.OrderSegment;

/**
 * 排序构建器
 *
 * @author chenjiamin
 * @date 2021/12/14 4:20 下午
 */
public class LazyOrderBuilder<Main> implements LazyOrderBy<LazyOrderBuilder<Main>, Main>,
        LazyJoinOrderBy<LazyOrderBuilder<Main>> {

    final LazyTable<Main> lazyTable;

    final OrderBySegment orderBySegment;

    private LazyOrderBuilder(LazyTable<Main> lazyTable) {
        this.lazyTable = lazyTable;
        this.orderBySegment = new OrderBySegment();
    }

    public static <TableModel> LazyOrderBuilder<TableModel> build(LazyTable<TableModel> lazyTable) {
        return new LazyOrderBuilder<>(lazyTable);
    }

    @Override
    public LazyOrderBuilder<Main> orderBy(boolean condition, SFunction<Main, ?> column, SqlKeyword orderRule) {
        if (condition) {
            this.orderBySegment.add(new OrderSegment(new ColumnSegment(this.lazyTable.getTableNameAlia(), this.lazyTable.getColumnName(column)), orderRule));
        }
        return this;
    }

    @Override
    public <Join> LazyOrderBuilder<Main> orderBy(boolean condition, LazyTable<Join> lazyTable, SFunction<Join, ?> column, SqlKeyword orderRule) {
        if (condition) {
            this.orderBySegment.add(new OrderSegment(new ColumnSegment(lazyTable.getTableNameAlia(), lazyTable.getColumnName(column)), orderRule));
        }
        return this;
    }
}
