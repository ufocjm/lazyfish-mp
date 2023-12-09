package fun.nibaba.lazyfish.mybatis.plus.core.wrappers;

import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import fun.nibaba.lazyfish.mybatis.plus.core.interfaces.LazyJoinCompare;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.ColumnSegment;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.CompareColumnSegment;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.JoinWehreSegment;

/**
 * On条件
 *
 * @author chenjiamin
 * @date 2021/12/17 10:35 上午
 */
public class LazyOnBuilder<Main, Join> extends AbstractLazyWhereBuilder<LazyOnBuilder<Main, Join>, Join> implements
        LazyJoinCompare<LazyOnBuilder<Main, Join>, Main, Join> {

    final LazyTable<Main> lazyTable;

    private LazyOnBuilder(LazyTable<Main> lazyTable, LazyTable<Join> lazyJoinTable, JoinWehreSegment whereSegment) {
        super(lazyJoinTable, whereSegment);
        this.lazyTable = lazyTable;
    }

    public static <Main, Join> LazyOnBuilder<Main, Join> builder(LazyTable<Main> lazyTable,
                                                                 LazyTable<Join> lazyJoinTable,
                                                                 JoinWehreSegment whereSegment) {
        return new LazyOnBuilder<>(lazyTable, lazyJoinTable, whereSegment);
    }


    @Override
    public LazyOnBuilder<Main, Join> compare(boolean condition, SFunction<Main, ?> leftColumn, SqlKeyword sqlKeyword, SFunction<Join, ?> rightColumn) {
        if (!condition) {
            return this;
        }
        ColumnSegment leftColumnSegment = new ColumnSegment(lazyTable.getTableNameAlia(), lazyTable.getColumnName(leftColumn));
        ColumnSegment rightColumnSegment = new ColumnSegment(super.lazyTable.getTableNameAlia(), super.lazyTable.getColumnName(rightColumn));
        CompareColumnSegment compareFieldSegment = new CompareColumnSegment(leftColumnSegment, sqlKeyword, rightColumnSegment);
        whereSegment.add(compareFieldSegment);
        return this;
    }

    @Override
    LazyOnBuilder<Main, Join> getNewThis() {
        return new LazyOnBuilder<>(this.lazyTable, super.lazyTable, new JoinWehreSegment());
    }
}
