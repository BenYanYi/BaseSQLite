package com.benyanyi.sqlitelib;

import android.database.sqlite.SQLiteDatabase;

/**
 * @author YanYi
 * @date 2019/5/17 14:28
 * @email ben@yanyi.red
 * @overview
 */
interface TableHelperImpl {

    /**
     * SQLiteDatabase
     *
     * @return
     */
    SQLiteDatabase getHelperWritableDatabase();

    /**
     * 判断某张表是否存在
     *
     * @param tableName
     * @return
     */
    boolean tableIsExist(String tableName);

    /**
     * 判断某张表是否存在
     *
     * @param tabName
     * @return
     */
    boolean tabIsExist(String tabName);

    /**
     * 获取db路径
     *
     * @return
     */
    String getDBPath();

    /**
     * 关闭数据库
     */
    void tableClose();
}
