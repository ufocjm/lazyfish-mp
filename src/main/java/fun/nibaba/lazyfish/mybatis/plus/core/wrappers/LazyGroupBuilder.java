package fun.nibaba.lazyfish.mybatis.plus.core.wrappers;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import fun.nibaba.lazyfish.mybatis.plus.core.interfaces.LazyGroupBy;
import fun.nibaba.lazyfish.mybatis.plus.core.interfaces.LazyJoinGroupBy;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.ColumnSegment;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.GroupBySegment;

/**
 * 分组构建器
 *
 * @author chenjiamin
 * @date 2021/12/14 4:27 下午
 */
public class LazyGroupBuilder<Main>
        implements LazyGroupBy<LazyGroupBuilder<Main>, Main>,
        LazyJoinGroupBy<LazyGroupBuilder<Main>> {

    final LazyTable<Main> lazyTable;

    final GroupBySegment groupBySegment;

    public static <TableModel> LazyGroupBuilder<TableModel> builder(LazyTable<TableModel> lazyTable) {
        return new LazyGroupBuilder<>(lazyTable);
    }

    private LazyGroupBuilder(LazyTable<Main> lazyTable) {
        this.lazyTable = lazyTable;
        groupBySegment = new GroupBySegment();
    }


    @Override
    public LazyGroupBuilder<Main> groupBy(boolean condition, SFunction<Main, ?> column) {
        if (condition) {
            this.groupBySegment.add(new ColumnSegment(this.lazyTable.getTableNameAlia(), this.lazyTable.getColumnName(column)));
        }
        return this;
    }

    @Override
    public <Join> LazyGroupBuilder<Main> groupBy(boolean condition, LazyTable<Join> lazyTable, SFunction<Join, ?> column) {
        if (condition) {
            this.groupBySegment.add(new ColumnSegment(lazyTable.getTableNameAlia(), lazyTable.getColumnName(column)));
        }
        return this;
    }
}
