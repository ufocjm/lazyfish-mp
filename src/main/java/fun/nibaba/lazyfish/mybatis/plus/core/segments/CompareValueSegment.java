package fun.nibaba.lazyfish.mybatis.plus.core.segments;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;

/**
 * 值比较片段
 *
 * @author chenjiamin
 * @date 2021/6/3 10:48 上午
 */
public class CompareValueSegment extends CompareSegment {

    protected final String paramValueKey;

    public CompareValueSegment(ColumnSegment columnSegment, SqlKeyword sqlKeyword, String paramValueKey) {
        super(columnSegment, sqlKeyword);
        if (StrUtil.isBlank(paramValueKey)) {
            throw new RuntimeException("sql片段键值对关键字不能为空");
        }
        this.paramValueKey = paramValueKey;
    }


    @Override
    public String getSqlSegment() {
        String sqlSegment = super.getSqlSegment();
        return sqlSegment + paramValueKey;
    }
}
