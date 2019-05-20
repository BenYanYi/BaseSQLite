package com.mylove.sqlitelib.callback;

/**
 * @author YanYi
 * @date 2019/3/29 14:10
 * @email ben@yanyi.red
 * @overview 逻辑操作
 */
public interface OperationCallBack<T> {
    /**
     * 数据插入
     */
    TableInsertCallBack<T> insert();

    /**
     * 数据删除
     */
    TableDeleteCallBack<T> delete();

    /**
     * 数据查询
     */
    TableQueryCallBack<T> query();

    /**
     * 数据修改
     */
    TableUpdateCallBack<T> update();

    /**
     * 存在符合条件的数据则修改数据，不存在符合条件的数据则添加数据
     */
    TableChangeOrAddCallBack<T> changeOrAdd();
}
