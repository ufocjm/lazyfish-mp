package fun.nibaba.lazyfish.mybatis.plus.core.wrappers;

import lombok.Getter;

import java.io.Serializable;

/**
 * 查询的表
 *
 * @author chenjiamin
 * @date 2021/12/14 2:55 下午
 */
@Getter
public class LazyTable<TableModel> extends LazyColumn<TableModel> implements Serializable {

    public LazyTable(Class<TableModel> tableClass) {
        this(tableClass, null);
    }

    public LazyTable(Class<TableModel> tableClass, String tableNameAlia) {
        super(tableClass, tableNameAlia);

    }
}
