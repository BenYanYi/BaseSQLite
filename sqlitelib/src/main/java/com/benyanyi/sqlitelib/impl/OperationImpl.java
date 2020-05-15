package com.benyanyi.sqlitelib.impl;

/**
 * @author YanYi
 * @date 2019/3/29 14:10
 * @email ben@yanyi.red
 * @overview 逻辑操作
 */
public interface OperationImpl<T> {

    /**
     * 清空条件
     *
     * @return
     */
    OperationImpl<T> cleanCondition();

    /**
     * 数据插入
     *
     * @return
     */
    TableInsertImpl<T> insert();

    /**
     * 数据删除
     *
     * @return
     */
    TableDeleteImpl<T> delete();

    /**
     * 数据查询
     *
     * @return
     */
    TableQueryImpl<T> query();

    /**
     * 数据修改
     *
     * @return
     */
    TableUpdateImpl<T> update();

    /**
     * 存在符合条件的数据则修改数据，不存在符合条件的数据则添加数据
     *
     * @return
     */
    TableChangeOrAddImpl<T> changeOrAdd();
}
