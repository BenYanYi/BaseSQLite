package com.mylove.sqlitelib.callback;

import java.util.List;

/**
 * @author YanYi
 * @date 2019/5/16 17:17
 * @email ben@yanyi.red
 * @overview 插入数据
 */
public interface TableInsertCallBack<T> {
    /**
     * 插入一条数据
     *
     * @param t 数据
     * @return
     */
    long find(T t);

    /**
     * 插入多条数据
     *
     * @param list 数据集
     * @return
     */
    long[] find(List<T> list);
}
