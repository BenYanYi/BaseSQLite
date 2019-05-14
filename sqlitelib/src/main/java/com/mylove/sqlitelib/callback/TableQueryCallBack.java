package com.mylove.sqlitelib.callback;

import java.util.List;

/**
 * @author yangjiewei
 * @time 2019/5/14 17:30
 */
public interface TableQueryCallBack {
    <T> List<T> findAll();

    <T> T findFirst();

    <T> T findLast();
}
