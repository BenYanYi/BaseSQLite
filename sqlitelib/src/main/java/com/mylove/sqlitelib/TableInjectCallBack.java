package com.mylove.sqlitelib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author YanYi
 * @date 2019/5/17 14:22
 * @email ben@yanyi.red
 * @overview
 */
interface TableInjectCallBack {

    SQLiteDatabase getHelperWritableDatabase();

    <T> TableInjectCallBack init(Context context, String dbName, int version, Class<T> tClass);

    /**
     * 判断某张表是否存在
     *
     * @param tableName
     * @return
     */
    boolean tableIsExist(String tableName);

    /**
     * 获取数据库路劲
     */
    String getDBPath();

    /**
     * 关闭数据库
     */
    void close();
}
