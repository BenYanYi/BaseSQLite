package com.mylove.sqlitelib.callback;

/**
 * @author YanYi
 * @date 2019/5/16 17:17
 * @email ben@yanyi.red
 * @overview
 */
public interface TableChangeOrAddCallBack<T> {
    long[] findAll(T t);

    long findFirst(T t);

    long findLast(T t);
}
