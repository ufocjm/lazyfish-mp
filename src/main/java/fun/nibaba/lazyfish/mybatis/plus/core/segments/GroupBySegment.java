package fun.nibaba.lazyfish.mybatis.plus.core.segments;

import com.baomidou.mybatisplus.core.enums.SqlKeyword;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * 分组 sql 片段
 *
 * @author chenjiamin
 * @date 2021/6/7 9:42 上午
 */
public class GroupBySegment extends ArrayList<ColumnSegment> implements LazySqlSegment {

    @Override
    public String getSqlSegment() {
        if (this.isEmpty()) {
            return null;
        }
        return SqlKeyword.GROUP_BY.getSqlSegment() + SPACE + this.stream().map(ColumnSegment::getSqlSegment).collect(Collectors.joining(COMMA));
    }

}
