package fun.nibaba.lazyfish.mybatis.plus.core.wrappers;

import fun.nibaba.lazyfish.mybatis.plus.core.segments.WhereSegment;


/**
 * 条件构造器
 *
 * @author chenjiamin
 * @date 2021/12/14 3:57 下午
 */
public class LazyWhereBuilder<TableModel> extends AbstractLazyWhereBuilder<LazyWhereBuilder<TableModel>, TableModel> {


    private LazyWhereBuilder(LazyTable<TableModel> lazyTable, WhereSegment whereSegment) {
        super(lazyTable, whereSegment);
    }

    public static <TableModel> LazyWhereBuilder<TableModel> builder(LazyTable<TableModel> lazyTable, WhereSegment whereSegment) {
        return new LazyWhereBuilder<>(lazyTable, whereSegment);
    }

    @Override
    LazyWhereBuilder<TableModel> getNewThis() {
        return new LazyWhereBuilder<>(this.lazyTable, new WhereSegment(this.whereSegment));
    }
}

