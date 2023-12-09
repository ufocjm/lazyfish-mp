package fun.nibaba.lazyfish.mybatis.plus.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 关联查询类型
 *
 * @author chenjiamin
 * @date 2021/6/2 3:05 下午
 */
@AllArgsConstructor
public enum JoinType {

    /**
     * 关联查询类型
     */
    INNER_JOIN("INNER JOIN"),

    LEFT_JOIN("LEFT JOIN"),

    RIGHT_JOIN("RIGHT JOIN");

    @Getter
    private final String sqlKey;
}
