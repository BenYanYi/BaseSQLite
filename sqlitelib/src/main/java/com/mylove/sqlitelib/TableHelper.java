package com.mylove.sqlitelib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.mylove.loglib.JLog;
import com.mylove.sqlitelib.annotation.ID;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    private TableMsg tableMsg;
    private Class<?> tClass;

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
        if (tableMsg.isIncrease() && (tableMsg.getType().equals("long") || tableMsg.getType().equals("Long"))) {
            sql = "create table " + tabName + "(" + tableMsg.getId() + " integer primary key autoincrement," + field + ")";//有主键自增
        } else if (!TextUtils.isEmpty(tableMsg.getId()) && !"null".equals(tableMsg.getId()) && tableMsg.getId().trim().length() != 0 && tableMsg.isNotNULL()) {
            sql = "create table " + tabName + "(" + tableMsg.getId() + " integer primary key NOT NULL," + field + ")";//有主键不为空
        } else if (!TextUtils.isEmpty(tableMsg.getId()) && !"null".equals(tableMsg.getId()) && tableMsg.getId().trim().length() != 0 && tableMsg.isNotNULL()) {
            sql = "create table " + tabName + "(" + tableMsg.getId() + " integer primary key," + field + ")";//有主键不为空
        } else {
            sql = "create table " + tabName + " (" + field + ")";
        }
        JLog.e("数据库创建");
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
            JLog.e("数据库" + tableIsExist(this.tabName));
            if (!tableIsExist(this.tabName)) {
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
            if (!tableIsExist(this.tabName)) {
                JLog.e("数据库降级");
                changeTable(db);
            } else {
                String sql = "DROP TABLE IF EXISTS " + this.tabName;
                db.execSQL(sql);
                onCreate(db);
            }
        }
    }

    private void changeTable(SQLiteDatabase db) {
        JLog.e();
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
        JLog.e(list);
        sql = "DROP TABLE IF EXISTS " + this.tabName;
        db.execSQL(sql);
        if (list.size() > 0) {
            List<FieldMsg> list1 = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                JLog.e(list.get(i).size());
                for (Map.Entry<String, String> entry : list.get(i).entrySet()) {
                    FieldMsg fieldMsg = new FieldMsg();
                    fieldMsg.setType("text");
                    fieldMsg.setKey(entry.getKey());
                    list1.add(fieldMsg);
                }
            }
            Field[] declaredFields = tClass.getDeclaredFields();
            for (Field field : declaredFields) {
                ID annotation = field.getAnnotation(ID.class);
                if (annotation != null && annotation.increase()) {
                    for (int i = list1.size() - 1; i >= 0; i--) {
                        if (list1.get(i).getKey().equals(field.getName())) {
                            list1.remove(i);
                        }
                    }
                }
            }
            for (int i = 0; i < this.tableMsg.getList().size(); i++) {
                for (int j = list1.size() - 1; j >= 0; j--) {
                    if (this.tableMsg.getList().get(i).getKey().equals(list1.get(j).getKey())) {
                        list1.remove(j);
                    }
                }
            }
            this.tableMsg.getList().addAll(list1);
            JLog.e(tableMsg.getList());
            JLog.e(list);
            onCreate(db);
            insertData(db, list);
        } else {
            sql = "DROP TABLE IF EXISTS " + this.tabName;
            db.execSQL(sql);
            onCreate(db);
        }
    }

    private void insertData(SQLiteDatabase db, List<Map<String, String>> list) {
//        Field[] declaredFields = tClass.getDeclaredFields();
//        List<Map<String, String>> oList = new LinkedList<>();
//        for (Field field : declaredFields) {
//            ID annotation = field.getAnnotation(ID.class);
//            for (int i = 0; i < list.size(); i++) {
//                for (Map.Entry<String, String> entry : list.get(i).entrySet()) {
//                    if (annotation)
//                }
//            }
//        }
        for (int i = 0; i < list.size(); i++) {
            ContentValues values = new ContentValues();
            for (Map.Entry<String, String> entry : list.get(i).entrySet()) {
                values.put(entry.getKey(), entry.getValue());
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
    TableHelper(Context context, String dbName, TableMsg tableMsg, String tabName, int version, Class<?> tClass) {
        super(context, dbName, null, version);
        this.tabName = tabName;
        this.tableMsg = tableMsg;
        this.tClass = tClass;
    }

    /**
     * 判断某张表是否存在
     *
     * @param tableName 表名
     * @return
     */
    private boolean tableIsExist(String tableName) {
        boolean result = false;
        if (tableName == null) {
            return false;
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
            return false;
        }
        return result;
    }
}
