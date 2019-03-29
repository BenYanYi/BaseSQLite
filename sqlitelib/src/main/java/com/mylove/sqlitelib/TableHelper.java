package com.mylove.sqlitelib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

/**
 * @author YanYi
 * @date 2019/3/26 17:05
 * @email ben@yanyi.red
 * @overview
 */
class TableHelper extends SQLiteOpenHelper {
    /**
     * 表名
     */
    private String tabName;
    private TabMsg tabMsg;

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder field = new StringBuilder();
        for (FieldMsg fieldMsg : tabMsg.getList()) {
            field.append(fieldMsg.getKey()).append(" ").append(fieldMsg.getType()).append(" ");
            if (fieldMsg.isNotNULL()) {
                field.append("NOT NULL");
            }
            field.append(",");
        }
        field = field.deleteCharAt(field.length() - 1);
        String sql;
        if (tabMsg.isIncrease() && (tabMsg.getType().equals("long") || tabMsg.getType().equals("Long"))) {
            sql = "create table " + tabName + "(" + tabMsg.getId() + " integer primary key autoincrement," + field + ")";//有主键自增
        } else if (!TextUtils.isEmpty(tabMsg.getId()) && !"null".equals(tabMsg.getId()) && tabMsg.getId().trim().length() != 0) {
            sql = "create table " + tabName + "(" + tabMsg.getId() + " integer primary key NOT NULL," + field + ")";//有主键
        } else {
            sql = "create table " + tabName + " (" + field + ")";
        }
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            String sql = "SELECT * INTO " + tabName + "_New" + " FROM " + tabName;
            db.execSQL(sql);
            sql = "DROP TABLE IF EXISTS " + tabName;
            db.execSQL(sql);
            onCreate(db);
            sql = "DROP TABLE IF EXISTS " + tabName + "_New";
            db.execSQL(sql);
        } else {
            String sql = "DROP TABLE IF EXISTS " + tabName;
            db.execSQL(sql);
            onCreate(db);
        }
    }

    /**
     * @param context  上下文
     * @param dbName   tab名
     * @param fieldMap 表字段
     * @param tabName  db名
     * @param version  版本号
     */
    TableHelper(Context context, String dbName, TabMsg tabMsg, String tabName, int version) {
        super(context, dbName, null, version);
        this.tabName = tabName;
        this.tabMsg = tabMsg;
    }
}
