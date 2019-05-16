package com.mylove.sqlitelib.callback;

/**
 * @author YanYi
 * @date 2019/5/16 17:17
 * @email ben@yanyi.red
 * @overview
 */
public interface TableUpdateCallBack<T> {
    int findFirst(T t);

    int findLast(T t);

    int[] findAll(T t);
}
