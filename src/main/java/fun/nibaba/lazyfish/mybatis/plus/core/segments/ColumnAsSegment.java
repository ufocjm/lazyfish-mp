package fun.nibaba.lazyfish.mybatis.plus.core.segments;

/**
 * 别名列
 *
 * @author chenjiamin
 * @date 2022/1/7 4:41 下午
 */
public class ColumnAsSegment implements LazySqlSegment {

    private final ColumnSegment columnSegment;

    private final String aliasName;

    public ColumnAsSegment(ColumnSegment columnSegment) {
        this(columnSegment, null);
    }

    public ColumnAsSegment(ColumnSegment columnSegment, String aliasName) {
        this.columnSegment = columnSegment;
        this.aliasName = aliasName;
    }

    @Override
    public String getSqlSegment() {
        return this.columnSegment.getSqlSegment() + (this.aliasName != null ? AS + this.aliasName : EMPTY);
    }
}
