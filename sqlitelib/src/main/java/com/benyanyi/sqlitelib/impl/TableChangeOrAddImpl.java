package com.benyanyi.sqlitelib.impl;

/**
 * @author YanYi
 * @date 2019/5/16 17:17
 * @email ben@yanyi.red
 * @overview 存在符合条件的数据则修改数据，不存在符合条件的数据则添加数据
 */
public interface TableChangeOrAddImpl<T> {

    /**
     * 修改符合条件的第一条数据，没找到符合条件的数据时添加数据
     *
     * @param t 数据
     * @return 操作数据的行号
     */
    long findFirst(T t);

    /**
     * 修改符合条件的最后一条数据，没找到符合条件的数据时添加数据
     *
     * @param t 数据
     * @return 操作数据的行号
     */
    long findLast(T t);

    /**
     * 修改符合条件的全部数据，没找到符合条件的数据时添加数据
     *
     * @param t 数据
     * @return 操作数据的行号数组
     */
    long[] findAll(T t);
}
