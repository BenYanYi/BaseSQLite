package com.mylove.sqlitelib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author YanYi
 * @date 2019/3/26 17:05
 * @email ben@yanyi.red
 * @overview
 */
final class TableHelper extends SQLiteOpenHelper implements TableHelperCallBack {
    /**
     * 表名
     */
    private String tabName;
    private TableMsg tableMsg;
    private Context mContext;

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder field = new StringBuilder();
        for (FieldMsg fieldMsg : tableMsg.getList()) {
            field.append(fieldMsg.getKey()).append(" ").append(fieldMsg.getType()).append(" ");
            if (fieldMsg.isNotNULL()) {
                field.append("NOT NULL");
            }
            field.append(",");
        }
        field = field.deleteCharAt(field.length() - 1);
        String sql;
        if (tableMsg.isIncrease()
                && (tableMsg.getType().equals("long") || tableMsg.getType().equals("Long"))) {
            sql = "create table " + tabName + "(" + tableMsg.getId() + " integer primary key autoincrement," + field + ")";//有主键自增
        } else if (!TextUtils.isEmpty(tableMsg.getId())
                && !"null".equals(tableMsg.getId().toLowerCase().trim())
                && !tableMsg.getId().trim().equals("") && tableMsg.isNotNULL()) {
            sql = "create table " + tabName + "(" + tableMsg.getId() + " integer primary key NOT NULL," + field + ")";//有主键不为空
        } else if (!TextUtils.isEmpty(tableMsg.getId())
                && !"null".equals(tableMsg.getId().toLowerCase().trim())
                && !tableMsg.getId().trim().equals("") && tableMsg.isNotNULL()) {
            sql = "create table " + tabName + "(" + tableMsg.getId() + " integer primary key," + field + ")";//有主键不为空
        } else {
            sql = "create table " + tabName + " (" + field + ")";
        }
        db.execSQL(sql);
    }

    /**
     * 版本升级时调用
     *
     * @param db
     * @param oldVersion 上一个版本
     * @param newVersion 新版本
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            if (tableIsExist(this.tabName)) {
                changeTable(db);
            } else {
                String sql = "DROP TABLE IF EXISTS " + this.tabName;
                db.execSQL(sql);
                onCreate(db);
            }
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion > newVersion) {
            if (tableIsExist(this.tabName)) {
                changeTable(db);
            } else {
                String sql = "DROP TABLE IF EXISTS " + this.tabName;
                db.execSQL(sql);
                onCreate(db);
            }
        }
    }

    /**
     * 将上一版本数据与字段解析
     *
     * @param db
     */
    private void changeTable(SQLiteDatabase db) {
        List<Map<String, String>> list = new LinkedList<>();
        String sql = "select * from " + this.tabName;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int totalColumn = cursor.getColumnCount();
            Map<String, String> map = new LinkedHashMap<>();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }
            }
            list.add(map);
            cursor.moveToNext();
        }
        cursor.close();
        sql = "DROP TABLE IF EXISTS " + this.tabName;
        db.execSQL(sql);
        if (list.size() > 0) {
            Set<String> set = new HashSet<>();
            for (int i = 0; i < list.size(); i++) {
                for (Map.Entry<String, String> entry : list.get(i).entrySet()) {
                    set.add(entry.getKey());
                }
            }
            Set<String> keySet = new HashSet<>();
            for (int i = 0; i < this.tableMsg.getList().size(); i++) {
                for (String str : set) {
                    if (this.tableMsg.getList().get(i).getKey().equals(str) || this.tableMsg.getId().equals(str)) {
                        keySet.add(str);
                    }
                }
            }
            for (String str : keySet) {
                set.remove(str);
            }
            List<FieldMsg> fieldMsgList = new ArrayList<>();
            for (String str : set) {
                FieldMsg fieldMsg = new FieldMsg();
                fieldMsg.setKey(str);
                fieldMsg.setType("text");
                fieldMsg.setNotNULL(false);
                if (!fieldMsgList.contains(fieldMsg)) {
                    fieldMsgList.add(fieldMsg);
                }
            }
            this.tableMsg.getList().addAll(fieldMsgList);
            onCreate(db);
            insertData(db, list);
        } else {
            sql = "DROP TABLE IF EXISTS " + this.tabName;
            db.execSQL(sql);
            onCreate(db);
        }
    }

    /**
     * 数据插入表
     *
     * @param db
     * @param list 上一版本数据
     */
    private void insertData(SQLiteDatabase db, List<Map<String, String>> list) {
        for (int i = 0; i < list.size(); i++) {
            ContentValues values = new ContentValues();
            for (Map.Entry<String, String> entry : list.get(i).entrySet()) {
                if (!entry.getKey().equals(this.tableMsg.getId())) {
                    values.put(entry.getKey(), entry.getValue());
                }
            }
            db.insert(this.tabName, null, values);
        }
    }

    /**
     * @param context  上下文
     * @param dbName   tab名
     * @param tableMsg 表信息
     * @param tabName  db名
     * @param version  版本号
     */
    TableHelper(Context context, String dbName, TableMsg tableMsg, String tabName, int version) {
        super(context, dbName, null, version);
        this.mContext = context;
        this.tabName = tabName;
        this.tableMsg = tableMsg;
    }

    @Override
    public SQLiteDatabase getHelperWritableDatabase() {
        return this.getWritableDatabase();
    }

    /**
     * 判断某张表是否存在
     *
     * @param tableName 表名
     * @return 存在返回false
     */
    public boolean tableIsExist(String tableName) {
        boolean result = false;
        if (tableName == null) {
            return true;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            String sql = "select count(*) as c from Sqlite_master  where type ='table' and name ='" + tableName.trim() + "' ";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }
            cursor.close();
        } catch (Exception e) {
            return true;
        }
        return !result;
    }

    public boolean tabIsExist(String tabName) {
        boolean result = false;
        if (tabName == null) {
            return false;
        }
        Cursor cursor = null;
        try {
            String sql = "select count(*) as c from sqlite_master where type ='table' and name ='" + tabName.trim() + "' ";
            cursor = this.getReadableDatabase().rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return result;
    }

    /**
     * 获取数据库路径
     **/
    public String getDBPath() {
        return this.mContext.getDatabasePath(this.tabName).getPath();
    }


    @Override
    public void tableClose() {
        this.close();
    }
}
