package com.mylove.sqlitelib.callback;

import java.util.List;

/**
 * @author YanYi
 * @date 2019/5/16 17:17
 * @email ben@yanyi.red
 * @overview
 */
public interface TableQueryCallBack<T> {
    List<T> findAll();

    T findFirst();

    T findLast();
}
