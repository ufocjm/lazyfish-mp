package fun.nibaba.lazyfish.mybatis.plus.core.mapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.google.common.collect.Lists;
import fun.nibaba.lazyfish.mybatis.plus.core.wrappers.LazyWrapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 带有 join 查询的 Mapper
 *
 * @author chenjiamin
 * @date 2021/5/31 4:31 下午
 */
public interface LazyMapper<Entity> extends BaseMapper<Entity> {

    /**
     * 联表列表查询
     *
     * @param wrapper sql构造器
     * @return 数据
     */
    List<Map<String, Object>> lazyList(@Param(Constants.WRAPPER) LazyWrapper wrapper);


    /**
     * 联表列表查询
     *
     * @param wrapper     sql构造器
     * @param entityClass 转换实体
     * @return 数据
     */
    default <EntityClass> List<EntityClass> lazyList(@Param(Constants.WRAPPER) LazyWrapper wrapper, Class<EntityClass> entityClass) {
        List<Map<String, Object>> results = this.lazyList(wrapper);
        if (CollUtil.isEmpty(results)) {
            return Lists.newArrayList();
        }
        return results.stream().map(result -> BeanUtil.toBean(result, entityClass)).collect(Collectors.toList());
    }

    /**
     * 联表查询
     *
     * @param wrapper sql构造器
     * @return 数据
     */
    Map<String, Object> lazyOne(@Param(Constants.WRAPPER) LazyWrapper wrapper);

    /**
     * 联表查询
     *
     * @param wrapper     sql构造器
     * @param entityClass 转换实体
     * @return 数据
     */
    default <EntityClass> EntityClass lazyOne(@Param(Constants.WRAPPER) LazyWrapper wrapper, Class<EntityClass> entityClass) {
        Map<String, Object> result = this.lazyOne(wrapper);
        return BeanUtil.toBean(result, entityClass);
    }


    /**
     * 联表列表分页查询
     *
     * @param page    分页参数
     * @param wrapper sql构造器
     * @return 数据
     */
    <EntityClass, PageEntity extends IPage<EntityClass>> IPage<Map<String, Object>> lazyPage(PageEntity page, @Param(Constants.WRAPPER) LazyWrapper wrapper);


    /**
     * 联表列表分页查询
     *
     * @param page        分页参数
     * @param wrapper     sql构造器
     * @param entityClass 转换实体
     * @return 数据
     */
    @SuppressWarnings("unchecked")
    default <EntityClass, PageEntity extends IPage<EntityClass>> PageEntity lazyPage(PageEntity page, @Param(Constants.WRAPPER) LazyWrapper wrapper, Class<EntityClass> entityClass) {
        IPage<Map<String, Object>> resultPage = this.lazyPage(page, wrapper);
        List<Map<String, Object>> records = resultPage.getRecords();
        if (CollUtil.isNotEmpty(records)) {
            ((IPage) resultPage).setRecords(records.stream().map(record-> BeanUtil.toBean(record, entityClass)).collect(Collectors.toList()));
        }
        return (PageEntity) resultPage;
    }


}
