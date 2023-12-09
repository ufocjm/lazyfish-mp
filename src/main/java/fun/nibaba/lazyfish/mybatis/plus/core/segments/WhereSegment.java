package fun.nibaba.lazyfish.mybatis.plus.core.segments;

import com.baomidou.mybatisplus.core.conditions.ISqlSegment;
import com.baomidou.mybatisplus.core.conditions.segments.MatchSegment;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * where 条件 sql 片段
 *
 * @author chenjiamin
 * @date 2021/6/2 4:10 下午
 */
public class WhereSegment extends ArrayList<ISqlSegment> implements LazySqlSegment {


    public WhereSegment() {
        this(new HashMap<>(), new AtomicInteger());
    }

    public WhereSegment(Map<String, Object> paramNameValuePairs, AtomicInteger paramNameSeq) {
        this.paramNameValuePairs = paramNameValuePairs;
        this.paramNameSeq = paramNameSeq;
    }

    public WhereSegment(WhereSegment whereSegment) {
        this.paramNameValuePairs = whereSegment.paramNameValuePairs;
        this.paramNameSeq = whereSegment.paramNameSeq;
        this.lastValue = whereSegment.lastValue;
    }

    /**
     * 条件具体的值
     */
    @Getter
    protected final Map<String, Object> paramNameValuePairs;

    /**
     * 查询条件排序
     */
    @Getter
    private final AtomicInteger paramNameSeq;

    /**
     * 最后一个元素
     */
    private ISqlSegment lastValue;

    @Override
    public String getSqlSegment() {
        this.flushFirstValue();
        return this.stream().map(ISqlSegment::getSqlSegment).collect(Collectors.joining(SPACE));
    }

    @Override
    public boolean add(ISqlSegment sqlSegment) {
        boolean matchLastValueJoinKey = MatchSegment.AND_OR.match(this.lastValue);
        boolean matchValueJoinKey = MatchSegment.AND_OR.match(sqlSegment);
        if (matchLastValueJoinKey) {
            if (matchValueJoinKey) {
                this.removeLastValue();
            }
        } else {
            if (!matchValueJoinKey) {
                super.add(SqlKeyword.AND);
            }
        }
        super.add(sqlSegment);
        this.refreshLastValue();
        return true;
    }

    /**
     * 刷新最后一个
     */
    protected void refreshLastValue() {
        if (!this.isEmpty()) {
            lastValue = this.get(this.size() - 1);
        }
    }

    /**
     * 删除最后一个
     */
    protected void removeLastValue() {
        this.remove(this.size() - 1);
        this.refreshLastValue();
    }

    /**
     * 清除第一个元素是 AND 或者 OR
     */
    protected void flushFirstValue() {
        if (!this.isEmpty()) {
            ISqlSegment sqlSegment = this.get(0);
            if (MatchSegment.AND_OR.match(sqlSegment)) {
                this.remove(0);
            }
        }
    }

    /**
     * 格式化参数,并且把取值方式放入 Map中
     *
     * @param tableNameAlia 表别名
     * @param columnCache   列缓存
     * @param value         值
     * @return 获取 paramNameValuePairs 的key值 #{eq.paramNameValuePairs.keyName}
     */
    public String formatParam(String tableNameAlia, ColumnCache columnCache, Object value) {
        String paramKey = tableNameAlia + UNDERSCORE + columnCache.getColumn() + UNDERSCORE + this.paramNameSeq.incrementAndGet();
        String paramValue = WRAPPER + WRAPPER_PARAM_MIDDLE + paramKey;
        this.paramNameValuePairs.put(paramKey, value);
        return SqlScriptUtils.safeParam(paramValue);
    }

}
