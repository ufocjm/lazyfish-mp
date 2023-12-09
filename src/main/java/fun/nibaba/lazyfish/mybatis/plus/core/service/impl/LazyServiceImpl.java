package fun.nibaba.lazyfish.mybatis.plus.core.service.impl;

import cn.hutool.core.util.ClassUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.nibaba.lazyfish.mybatis.plus.core.mapper.LazyMapper;
import fun.nibaba.lazyfish.mybatis.plus.core.service.ILazyService;

/**
 * INibabaService 实现类 Mapper Entity
 *
 * @author chenjiamin
 * @date 2021/6/7 3:57 下午
 */
public class LazyServiceImpl<Mapper extends LazyMapper<Entity>, Entity> extends ServiceImpl<Mapper, Entity>
        implements ILazyService<Entity> {

    @SuppressWarnings("unchecked")
    @Override
    protected Class<Mapper> currentMapperClass() {
        return (Class<Mapper>) ClassUtil.getTypeArgument(this.getClass(), 0);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Class<Entity> currentModelClass() {
        return (Class<Entity>) ClassUtil.getTypeArgument(this.getClass(), 1);
    }

}
