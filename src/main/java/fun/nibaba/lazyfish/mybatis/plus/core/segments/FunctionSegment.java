package fun.nibaba.lazyfish.mybatis.plus.core.segments;

import cn.hutool.core.util.StrUtil;
import fun.nibaba.lazyfish.mybatis.plus.core.enums.LazyFunction;
import fun.nibaba.lazyfish.mybatis.plus.core.exceptions.LazyMybatisPlusException;

/**
 * 函数sql片段
 *
 * @author chenjiamin
 * @date 2022/1/24 3:56 下午
 */
public class FunctionSegment implements LazySqlSegment {

    private final LazyFunction lazyFunction;

    private final ColumnSegment columnSegment;

    private final String aliasName;

    public FunctionSegment(LazyFunction lazyFunction, ColumnSegment columnSegment, String aliasName) {
        if (lazyFunction == null) {
            throw new LazyMybatisPlusException("函数不能为空");
        }
        if (columnSegment == null) {
            throw new LazyMybatisPlusException("字段不能为空");
        }
        if (StrUtil.isBlank(aliasName)) {
            throw new LazyMybatisPlusException("别名不能为空");
        }
        this.lazyFunction = lazyFunction;
        this.columnSegment = columnSegment;
        this.aliasName = aliasName;
    }

    @Override
    public String getSqlSegment() {
        return String.format(this.lazyFunction.getSqlSegment(), this.columnSegment.getSqlSegment()) + AS + this.aliasName;
    }
}
