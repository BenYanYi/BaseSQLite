package com.mylove.sqlitelib;

import android.database.sqlite.SQLiteDatabase;

/**
 * @author YanYi
 * @date 2019/5/17 14:28
 * @email ben@yanyi.red
 * @overview
 */
interface TableHelperCallBack {

    SQLiteDatabase getHelperWritableDatabase();

    /**
     * 判断某张表是否存在
     *
     * @param tableName
     * @return
     */
    boolean tableIsExist(String tableName);

    boolean tabIsExist(String tabName);

    /**
     * 获取db路径
     */
    String getDBPath();

    /**
     * 关闭数据库
     */
    void tableClose();
}
