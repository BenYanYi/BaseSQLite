package com.benyanyi.sqlitelib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author YanYi
 * @date 2019/5/17 14:22
 * @email ben@yanyi.red
 * @overview
 */
interface TableInjectImpl {

    /**
     * SQLiteDatabase
     *
     * @return
     */
    SQLiteDatabase getHelperWritableDatabase();

    /**
     * 初始化
     *
     * @param context
     * @param dbName
     * @param version
     * @return
     */
    TableInjectImpl init(Context context, String dbName, int version, Class<?>... classes);

    /**
     * 判断某张表是否存在
     *
     * @param tableName
     * @return
     */
    <T> boolean tableIsExist(Class<T> tClass);

    /**
     * 判断某张表是否存在
     *
     * @param tableName
     * @return
     */
    boolean tableIsExist(String tableName);

    /**
     * 获取数据库路劲
     *
     * @return
     */
    <T> String getDBPath(Class<T> tClass);

    /**
     * 获取数据库路劲
     *
     * @return
     */
    String getDBPath(String tabName);

    /**
     * 关闭数据库
     */
    void close();
}
