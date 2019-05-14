package com.mylove.sqlitelib.callback;

/**
 * @author YanYi
 * @date 2019/3/29 14:10
 * @email ben@yanyi.red
 * @overview 逻辑操作
 */
public interface OperationCallBack {
    TableInsertCallBack insert();

    TableDeleteCallBack delete();

    TableQueryCallBack query();

    TableUpdateCallBack update();

    TableChangeOrAddCallBack changeOrAdd();
}
