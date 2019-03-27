package com.mylove.sqlitelib.init;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.Map;

/**
 * @author YanYi
 * @date 2019/3/26 17:05
 * @email ben@yanyi.red
 * @overview
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    /**
     * 表名
     */
    private String tabName;
    private Map<String, String> fieldMap;
    private String id;
    private String idType;
    private boolean increase;

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder field = new StringBuilder();
        for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
            field.append(entry.getKey()).append(" ").append(entry.getValue()).append(",");
        }
        field = field.deleteCharAt(field.length() - 1);
        String sql;
        if (increase && idType.equals("java.lang.Long")) {
            sql = "create table " + tabName + "(" + id + " integer primary key autoincrement," + field + ")";//有主键自增
        } else if (!TextUtils.isEmpty(id) && !"null".equals(id) && id.trim().length() != 0) {
            sql = "create table " + tabName + "(" + id + " integer primary key," + field + ")";//有主键
        } else {
            sql = "create table " + tabName + " (" + field + ")";
        }
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
     * @param dbName   tab名
     * @param fieldMap 表字段
     * @param tabName  db名
     * @param version  版本号
     */
    public SQLiteHelper(Context context, String dbName, String id, String idType, boolean increase, Map<String, String> fieldMap, String tabName, int version) {
        super(context, dbName, null, version);
        this.tabName = tabName;
        this.id = id;
        this.increase = increase;
        this.fieldMap = fieldMap;
        this.idType = idType;
    }
}
