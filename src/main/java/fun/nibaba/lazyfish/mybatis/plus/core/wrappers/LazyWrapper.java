package fun.nibaba.lazyfish.mybatis.plus.core.wrappers;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.GroupBySegment;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.OrderBySegment;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.SelectSegment;
import fun.nibaba.lazyfish.mybatis.plus.core.segments.WhereSegment;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 查询sql构建器
 *
 * @author chenjiamin
 * @date 2021/12/14 11:07 上午
 */
public class LazyWrapper {

    /**
     * select
     */
    private final SelectSegment selectSegment;

    /**
     * tableName as
     */
    private final String tableNameAlias;

    /**
     * where
     */
    private final WhereSegment whereSegment;

    /**
     * group by
     */
    private final GroupBySegment groupBySegment;

    /**
     * order by
     */
    private final OrderBySegment orderBySegment;

    /**
     * last sql segment
     */
    private final String lastSql;

    /**
     * join sql segments
     */
    @Getter
    private final List<LazyJoinWrapper> joinWrapperList;

    /**
     * params map
     */
    private Map<String, Object> paramNameValuePairs;

    LazyWrapper(SelectSegment selectSegment,
                String tableNameAlias,
                List<LazyJoinWrapper> joinWrapperList,
                WhereSegment whereSegment,
                GroupBySegment groupBySegment,
                OrderBySegment orderBySegment,
                String lastSql) {

        this.selectSegment = selectSegment;
        this.tableNameAlias = tableNameAlias;
        this.joinWrapperList = joinWrapperList;
        this.whereSegment = whereSegment;
        this.groupBySegment = groupBySegment;
        this.orderBySegment = orderBySegment;
        this.lastSql = lastSql;
    }

    /**
     * 生成 builder
     *
     * @param lazyTable    表
     * @param <TableModel> 表类型
     * @return builder
     */
    public static <TableModel> LazyWrapperBuilder<TableModel> builder(LazyTable<TableModel> lazyTable) {
        return LazyWrapperBuilder.builder(lazyTable);
    }

    /**
     * 提供给Mybatis使用的get方法
     *
     * @return 表别名
     */
    public String getTableNameAlias() {
        return this.tableNameAlias;
    }

    /**
     * 提供给Mybatis使用的get方法
     *
     * @return 查询字段
     */
    public String getSqlSelect() {
        if (selectSegment.isEmpty() && CollUtil.isEmpty(this.joinWrapperList)) {
            return null;
        }
        List<String> selectSegments = new ArrayList<>();
        if (!selectSegment.isEmpty()) {
            selectSegments.add(selectSegment.getSqlSegment());
        }

        if (CollUtil.isNotEmpty(this.joinWrapperList)) {
            List<String> segmentList = this.joinWrapperList.stream()
                    .map(LazyJoinWrapper::getSqlSelect)
                    .filter(StrUtil::isNotBlank)
                    .collect(Collectors.toList()
                    );
            if (CollUtil.isNotEmpty(segmentList)) {
                selectSegments.add(String.join(StringPool.COMMA + StringPool.SPACE, segmentList));
            }
        }
        if (CollUtil.isEmpty(selectSegments)) {
            return null;
        }
        return String.join(StringPool.COMMA, selectSegments);
    }

    /**
     * 提供给Mybatis使用的get方法
     *
     * @return 条件
     */
    public String getWhereSegment() {
        if (whereSegment.isEmpty()) {
            return null;
        }
        return whereSegment.getSqlSegment();
    }

    /**
     * 提供给Mybatis使用的get方法
     *
     * @return group by
     */
    public String getGroupBySegment() {
        if (groupBySegment.isEmpty()) {
            return null;
        }
        return groupBySegment.getSqlSegment();
    }

    /**
     * 提供给Mybatis使用的get方法
     *
     * @return 排序字段
     */
    public String getOrderBySegment() {
        if (orderBySegment.isEmpty()) {
            return null;
        }
        return orderBySegment.getSqlSegment();
    }

    /**
     * 提供给Mybatis使用的get方法
     *
     * @return 末尾强制拼接的sql
     */
    public String getLastSql() {
        return StrUtil.isNotBlank(this.lastSql) ? this.lastSql : StringPool.EMPTY;
    }

    /**
     * 提供给Mybatis使用的get方法
     *
     * @return where条件的所有值的键值对
     */
    public Map<String, Object> getParamNameValuePairs() {
        if (paramNameValuePairs == null) {
            paramNameValuePairs = new HashMap<>();
            paramNameValuePairs.putAll(this.whereSegment.getParamNameValuePairs());
            if (this.joinWrapperList != null) {
                this.joinWrapperList.forEach(joinWrapper -> {
                    if (joinWrapper.getJoinOnSegment() != null && joinWrapper.getJoinOnSegment().getParamNameValuePairs() != null) {
                        paramNameValuePairs.putAll(joinWrapper.getJoinOnSegment().getParamNameValuePairs());
                    }
                });
            }
        }
        return paramNameValuePairs;
    }

}
