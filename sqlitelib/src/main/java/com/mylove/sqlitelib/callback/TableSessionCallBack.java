package com.mylove.sqlitelib.callback;

import android.database.sqlite.SQLiteDatabase;

/**
 * @author YanYi
 * @date 2019/5/17 15:30
 * @email ben@yanyi.red
 * @overview
 */
public interface TableSessionCallBack<T> {

    /**
     * 条件处理类
     *
     * @return
     */
    ConditionCallBack<T> where();


    SQLiteDatabase getSQLiteDatabase();

    /**
     * 判断某张表是否存在
     *
     * @param tableName
     * @return
     */
    boolean tableIsExist(String tableName);

    /**
     * 获取数据库路径
     */
    String getDBPath();

    void close();
}
