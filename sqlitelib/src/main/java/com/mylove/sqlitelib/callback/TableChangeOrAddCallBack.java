package com.mylove.sqlitelib.callback;

/**
 * @author yangjiewei
 * @time 2019/5/14 17:14
 */
public interface TableChangeOrAddCallBack {
    <T> long[] findAll(T t);

    <T> long findFirst(T t);

    <T> long findLast(T t);
}
