package com.mylove.sqlitelib.callback;

/**
 * @author yangjiewei
 * @time 2019/5/14 17:37
 */
public interface TableUpdateCallBack {
    <T> int findFirst(T t);

    <T> int findLast(T t);

    <T> int[] findAll(T t);
}
