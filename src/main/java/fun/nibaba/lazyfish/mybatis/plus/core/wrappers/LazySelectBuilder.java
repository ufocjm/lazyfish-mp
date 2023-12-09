package fun.nibaba.lazyfish.mybatis.plus.core.wrappers;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import fun.nibaba.lazyfish.mybatis.plus.core.enums.LazyFunction;
import fun.nibaba.lazyfish.mybatis.plus.core.interfaces.LazyQuery;
import fun.nibaba.lazyfish.mybatis.plus.core.interfaces.LazyQueryFunction;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.ColumnAsSegment;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.ColumnSegment;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.FunctionSegment;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.SelectSegment;

/**
 * select 构建器
 *
 * @author chenjiamin
 * @date 2021/12/14 2:34 下午
 */
public class LazySelectBuilder<TableModel> implements
        LazyQuery<LazySelectBuilder<TableModel>, TableModel>,
        LazyQueryFunction<LazySelectBuilder<TableModel>, TableModel> {

    final LazyTable<TableModel> lazyTable;

    final SelectSegment selectSegment;

    private LazySelectBuilder(LazyTable<TableModel> lazyTable) {
        this.lazyTable = lazyTable;
        selectSegment = new SelectSegment(this.lazyTable.getTableNameAlia());
    }

    public static <TableModel> LazySelectBuilder<TableModel> builder(LazyTable<TableModel> lazyTable) {
        return new LazySelectBuilder<>(lazyTable);
    }

    @Override
    public LazySelectBuilder<TableModel> select(SFunction<TableModel, ?> column, String aliasName) {
        ColumnSegment columnSegment = new ColumnSegment(lazyTable.getTableNameAlia(), lazyTable.getColumnName(column));
        selectSegment.add(new ColumnAsSegment(columnSegment, aliasName));
        return this;
    }

    @SafeVarargs
    @Override
    public final LazySelectBuilder<TableModel> select(SFunction<TableModel, ?>... columns) {
        for (SFunction<TableModel, ?> column : columns) {
            this.select(column);
        }
        return this;
    }

    @Override
    public LazySelectBuilder<TableModel> fun(SFunction<TableModel, ?> column, String aliasName, LazyFunction lazyFunction) {
        ColumnSegment columnSegment = new ColumnSegment(lazyTable.getTableNameAlia(), lazyTable.getColumnName(column));
        selectSegment.add(new FunctionSegment(lazyFunction, columnSegment, aliasName));
        return this;
    }

}
