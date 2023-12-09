package fun.nibaba.lazyfish.mybatis.plus.core.wrappers;

import com.baomidou.mybatisplus.core.conditions.ISqlSegment;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlUtils;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import fun.nibaba.lazyfish.mybatis.plus.core.functions.ValueFunction;
import fun.nibaba.lazyfish.mybatis.plus.core.interfaces.LazyCompare;
import fun.nibaba.lazyfish.mybatis.plus.core.interfaces.LazyNested;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 抽象条件构建器， 将很多共性方法进行一次整合
 *
 * @author chenjiamin
 * @date 2022/1/10 1:57 下午
 */
public abstract class AbstractLazyWhereBuilder<Child extends AbstractLazyWhereBuilder<Child, TableModel>, TableModel> implements
        LazyCompare<Child, TableModel, Object>,
        LazyNested<Child>,
        Constants {

    private final Child typeThis = (Child) this;

    protected final LazyTable<TableModel> lazyTable;

    protected final WhereSegment whereSegment;

    protected AbstractLazyWhereBuilder(LazyTable<TableModel> lazyTable, WhereSegment whereSegment) {
        this.lazyTable = lazyTable;
        this.whereSegment = whereSegment;
    }

    @Override
    public Child compare(boolean condition, SFunction<TableModel, ?> column, ValueFunction<Object> value, SqlKeyword sqlKeyword) {
        this.addWhereSegment(column, value.getValue(), sqlKeyword);
        return typeThis;
    }

    @Override
    public Child like(boolean condition, SFunction<TableModel, ?> column, ValueFunction<Object> value, SqlLike sqlLike, SqlKeyword sqlKeyword) {
        this.addWhereSegment(column, SqlUtils.concatLike(value.getValue(), sqlLike), sqlKeyword);
        return typeThis;
    }

    @Override
    public Child nullable(boolean condition, SFunction<TableModel, ?> column, SqlKeyword sqlKeyword) {
        this.addWhereSegment(new CompareSegment(new ColumnSegment(lazyTable.getTableNameAlia(), lazyTable.getColumnName(column)), sqlKeyword));
        return typeThis;
    }

    @Override
    public Child containable(boolean condition, SqlKeyword sqlKeyword, SFunction<TableModel, ?> column, ValueFunction<Collection<?>> collectionFunction) {
        if (!condition) {
            return typeThis;
        }
        Collection<?> collection = collectionFunction.getValue();
        if (collection == null || collection.isEmpty()) {
            return typeThis;
        }
        ColumnCache columnCache = lazyTable.getColumnCache(column);
        if (collection.size() == 1) {
            Iterator<?> iterator = collection.iterator();
            if (sqlKeyword == SqlKeyword.IN) {
                this.eq(column, iterator.next());
            } else if (sqlKeyword == SqlKeyword.NOT_IN) {
                this.ne(column, iterator.next());
            }
        } else {
            String values = collection.stream()
                    .map(value -> this.whereSegment.formatParam(lazyTable.getTableNameAlia(), columnCache, value))
                    .collect(Collectors.joining(StringPool.COMMA, StringPool.LEFT_BRACKET, StringPool.RIGHT_BRACKET));
            this.whereSegment.add(new CompareValueSegment(new ColumnSegment(lazyTable.getTableNameAlia(), columnCache.getColumnSelect()), sqlKeyword, values));

        }
        return typeThis;
    }

    @Override
    public Child nested(boolean condition, Consumer<Child> consumer, SqlKeyword sqlKeyword) {
        if (!condition) {
            return typeThis;
        }

        this.addWhereSegment(sqlKeyword);
        if (consumer == null) {
            return typeThis;
        }
        Child childWhere = this.getNewThis();
        consumer.accept(childWhere);
        this.addWhereSegment(new BracketSegment(childWhere.whereSegment));

        return typeThis;
    }

    /**
     * new一个当前对象出来
     *
     * @return 当前对象
     */
    abstract Child getNewThis();


    /**
     * 添加 where sql 片段
     *
     * @param column     列
     * @param value      值
     * @param sqlKeyword sql条件连接关键字
     */
    private void addWhereSegment(SFunction<TableModel, ?> column, Object value, SqlKeyword sqlKeyword) {
        ColumnCache columnCache = lazyTable.getColumnCache(column);
        String paramValue = this.whereSegment.formatParam(lazyTable.getTableNameAlia(), columnCache, value);
        this.whereSegment.add(new CompareValueSegment(new ColumnSegment(lazyTable.getTableNameAlia(), columnCache.getColumnSelect()), sqlKeyword, paramValue));
    }

    private void addWhereSegment(ISqlSegment sqlSegment) {
        this.whereSegment.add(sqlSegment);
    }

}
