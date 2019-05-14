package com.mylove.sqlitelib.callback;

import java.util.List;

/**
 * @author yangjiewei
 * @time 2019/5/14 17:10
 * 插入数据
 */
public interface TableInsertCallBack {
    /**
     * 插入一条数据
     *
     * @param t   数据
     * @param <T> 数据类型(需要定义了TableBean的数据类)
     * @return
     */
    <T> long find(T t);

    /**
     * 插入多条数据
     *
     * @param list 数据集
     * @param <T>  数据类型(需要定义了TableBean的数据类)
     * @return
     */
    <T> long[] find(List<T> list);
}
