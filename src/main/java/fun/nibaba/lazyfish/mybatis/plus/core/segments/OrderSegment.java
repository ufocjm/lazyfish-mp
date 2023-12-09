package fun.nibaba.lazyfish.mybatis.plus.core.segments;

import com.baomidou.mybatisplus.core.enums.SqlKeyword;

/**
 * 排序 sql 片段
 *
 * @author chenjiamin
 * @date 2021/6/4 5:07 下午
 */
public class OrderSegment implements LazySqlSegment {

    private final ColumnSegment columnSegment;

    private final SqlKeyword orderRule;

    public OrderSegment(ColumnSegment columnSegment) {
        this(columnSegment, SqlKeyword.ASC);
    }

    public OrderSegment(ColumnSegment columnSegment, SqlKeyword orderRule) {
        this.columnSegment = columnSegment;
        this.orderRule = orderRule != null ? orderRule : SqlKeyword.ASC;
    }

    @Override
    public String getSqlSegment() {
        return columnSegment.getSqlSegment() + SPACE + orderRule.getSqlSegment();
    }
}
