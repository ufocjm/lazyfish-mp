package fun.nibaba.lazyfish.mybatis.plus.core.enums;

import com.baomidou.mybatisplus.core.conditions.ISqlSegment;
import lombok.AllArgsConstructor;

/**
 * 函数
 *
 * @author chenjiamin
 * @date 2022/1/24 3:50 下午
 */
@AllArgsConstructor
public enum LazyFunction implements ISqlSegment {

    COUNT("COUNT(%s)"),

    SUM("SUM(%s)"),

    AVG("AVG(%s)"),

    MAX("MAX(%s)"),

    MIN("MIN(%s)");

    private final String function;

    @Override
    public String getSqlSegment() {
        return this.function;
    }
}
