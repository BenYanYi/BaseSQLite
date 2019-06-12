package com.benyanyi.sqlitelib.callback;

/**
 * @author YanYi
 * @date 2019/3/29 14:10
 * @email ben@yanyi.red
 * @overview 逻辑操作
 */
public interface OperationCallBack<T> {
    /**
     * 数据插入
     *
     * @return
     */
    TableInsertCallBack<T> insert();

    /**
     * 数据删除
     *
     * @return
     */
    TableDeleteCallBack<T> delete();

    /**
     * 数据查询
     *
     * @return
     */
    TableQueryCallBack<T> query();

    /**
     * 数据修改
     *
     * @return
     */
    TableUpdateCallBack<T> update();

    /**
     * 存在符合条件的数据则修改数据，不存在符合条件的数据则添加数据
     *
     * @return
     */
    TableChangeOrAddCallBack<T> changeOrAdd();
}
