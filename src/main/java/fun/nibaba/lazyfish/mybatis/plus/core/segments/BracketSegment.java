package fun.nibaba.lazyfish.mybatis.plus.core.segments;

import com.baomidou.mybatisplus.core.conditions.ISqlSegment;
import lombok.AllArgsConstructor;

/**
 * 包裹sql片段 ( sql segment )
 *
 * @author chenjiamin
 * @date 2021/6/3 6:13 下午
 */
@AllArgsConstructor
public class BracketSegment implements LazySqlSegment {

    private final ISqlSegment sqlSegment;

    @Override
    public String getSqlSegment() {
        return LEFT_BRACKET + sqlSegment.getSqlSegment() + RIGHT_BRACKET;
    }

}
