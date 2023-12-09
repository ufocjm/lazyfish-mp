package fun.nibaba.lazyfish.mybatis.plus.core.segments;

import com.baomidou.mybatisplus.core.conditions.ISqlSegment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * select table_filed sql segment
 *
 * @author chenjiamin
 * @date 2021/6/3 4:03 下午
 */
@Getter
@AllArgsConstructor
public class SelectSegment extends ArrayList<ISqlSegment> implements LazySqlSegment {

    private final String tableAlias;

    @Override
    public String getSqlSegment() {
        if (this.isEmpty()) {
            return null;
        }
        return this.stream().map(ISqlSegment::getSqlSegment).collect(Collectors.joining(COMMA + SPACE));
    }

}
