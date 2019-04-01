package com.mylove.sqlitelib.callback;

import com.mylove.sqlitelib.operation.TableDelete;
import com.mylove.sqlitelib.operation.TableQuery;
import com.mylove.sqlitelib.operation.TableUpdate;

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

    TableDelete delete();

    int deleteAll();

    TableQuery query();

    <T> int updateAll(T t);

    TableUpdate update();

//    void sort(TableSort sort);

//    <T> List<T> all();
//
//    <T> T first();
//
//    <T> T last();
}
