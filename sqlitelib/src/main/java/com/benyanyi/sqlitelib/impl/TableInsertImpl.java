package com.benyanyi.sqlitelib.impl;

import java.util.List;

/**
 * @author YanYi
 * @date 2019/5/16 17:17
 * @email ben@yanyi.red
 * @overview 插入数据
 */
public interface TableInsertImpl<T> {
    /**
     * 插入一条数据
     *
     * @param t 数据
     * @return 当前行号
     */
    long find(T t);

    /**
     * 插入多条数据
     *
     * @param list 数据集
     * @return 对应的行号数组
     */
    long[] find(List<T> list);
}
