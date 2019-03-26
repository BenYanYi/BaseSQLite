package com.mylove.sqlitelib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Map;

/**
 * @author yanyi
 */

class DBHelper extends SQLiteOpenHelper {

    private String tabName;
    private Map<String, String> fieldMap;

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder field = new StringBuilder();
        for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
            field.append(entry.getKey()).append(" ").append(entry.getValue()).append(",");
        }
        field = field.deleteCharAt(field.length() - 1);
//        String sql = "create table " + tabName + " (id integer primary key autoincrement," + field + ")";//有主键
        String sql = "create table " + tabName + " (" + field + ")";
        Log.d("sql-->>", sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + tabName;
        db.execSQL(sql);
        onCreate(db);
    }

    /**
     * @param context  上下文
     * @param tabName  tab名
     * @param fieldMap 表字段
     * @param name     db名
     * @param version  版本号
     */
    DBHelper(Context context, String tabName, Map<String, String> fieldMap, String name, int version) {
        super(context, name, null, version);
        this.tabName = tabName;
        this.fieldMap = fieldMap;
    }
}
