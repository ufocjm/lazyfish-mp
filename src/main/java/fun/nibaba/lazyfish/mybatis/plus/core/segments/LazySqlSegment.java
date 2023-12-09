package fun.nibaba.lazyfish.mybatis.plus.core.segments;

import com.baomidou.mybatisplus.core.conditions.ISqlSegment;
import com.baomidou.mybatisplus.core.toolkit.Constants;

import java.io.Serializable;

/**
 * 个性化sql片段接口
 * 主要不用再重复实现接口了 毕竟都用得到
 *
 * @author chenjiamin
 * @date 2021/6/3 9:37 上午
 */
public interface LazySqlSegment extends ISqlSegment, Constants, Serializable {

}
