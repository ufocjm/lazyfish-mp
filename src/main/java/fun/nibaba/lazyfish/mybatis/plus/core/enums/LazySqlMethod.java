package fun.nibaba.lazyfish.mybatis.plus.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Nibaba MybatisPlus 支持 SQL 方法
 *
 * @author chenjiamin
 * @date 2021/5/31 4:44 下午
 */
@AllArgsConstructor
@Getter
public enum LazySqlMethod {

    /**
     * 关联列表查询
     * 1.表名
     * 2.说明
     * 3.sql script
     */
    LAZY_LIST("lazyList", "join查询满足条件所有数据", "<script>SELECT %s FROM %s %s %s %s %s %s %s\n</script>"),

    LAZY_ONE("lazyOne", "join查询满足条件单条", "<script>SELECT %s FROM %s %s %s %s %s %s %s\n</script>"),

    LAZY_PAGE("lazyPage", "join查询满足条件所有数据", "<script>SELECT %s FROM %s %s %s %s %s %s %s\n</script>");

    private final String method;

    private final String desc;

    private final String sql;

}
