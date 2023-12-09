package fun.nibaba.lazyfish.mybatis.plus.core.segments;

import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import fun.nibaba.lazyfish.mybatis.plus.core.exceptions.LazyMybatisPlusException;
import lombok.Getter;

/**
 * 比较 sql 片段
 * t.id = / != / > / >= 等
 *
 * @author chenjiamin
 * @date 2021/6/3 9:51 上午
 */
@Getter
public class CompareSegment implements LazySqlSegment {

    /**
     * 列 sql segment
     */
    private final ColumnSegment columnSegment;

    /**
     * 条件连接关键字
     */
    private final SqlKeyword sqlKeyword;

    public CompareSegment(ColumnSegment columnSegment, SqlKeyword sqlKeyword) {
        if (columnSegment == null) {
            throw new LazyMybatisPlusException("列 sql segment 不能为空");
        }
        if (sqlKeyword == null) {
            throw new LazyMybatisPlusException("条件连接关键字不能为空");
        }
        this.columnSegment = columnSegment;
        this.sqlKeyword = sqlKeyword;
    }

    /**
     * 获取key 也就是 table_a.id =
     *
     * @return
     */
    @Override
    public String getSqlSegment() {
        return columnSegment.getSqlSegment() + SPACE + sqlKeyword.getSqlSegment() + SPACE;
    }

}
