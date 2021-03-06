package com.benyanyi.sqlitelib.impl;

import android.database.sqlite.SQLiteDatabase;

/**
 * @author YanYi
 * @date 2019/5/17 15:30
 * @email ben@yanyi.red
 * @overview
 */
public interface TableSessionImpl<T> {

    /**
     * 条件处理类（会出现条件缓存）
     *
     * @return
     */
    ConditionImpl<T> where();

    /**
     * 返回数据库管理类
     *
     * @return
     */
    SQLiteDatabase getSQLiteDatabase();

    /**
     * 判断某张表是否存在
     *
     * @return
     */
    boolean tableIsExist();

    /**
     * 判断某张表是否存在
     *
     * @param tableName
     * @return
     */
    boolean tableIsExist(String tableName);

    /**
     * 获取数据库路径
     *
     * @return
     */
    String getDBPath();

    /**
     * 获取数据库路径
     *
     * @return
     */
    String getDBPath(String tabName);

    /**
     * 关闭数据库
     */
    void close();
}
