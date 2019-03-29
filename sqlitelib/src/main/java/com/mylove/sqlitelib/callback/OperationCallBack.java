package com.mylove.sqlitelib.callback;

import com.mylove.sqlitelib.operation.TabQuery;

import java.util.List;

/**
 * @author YanYi
 * @date 2019/3/29 14:10
 * @email ben@yanyi.red
 * @overview
 */
public interface OperationCallBack {
    <T> long insert(T t);

    <T> long[] insert(List<T> list);

    void delete();

    TabQuery query();

    <T> void update(T t);

//    void sort(TableSort sort);

//    <T> List<T> all();
//
//    <T> T first();
//
//    <T> T last();
}
