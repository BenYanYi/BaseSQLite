package com.benyanyi.sqlitelib;

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
class TableHelper extends SQLiteOpenHelper implements TableHelperImpl {

    private List<TableMsg> tableMsg;
    private Context mContext;
    private String dbName;

    private SQLiteDatabase database;

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (TableMsg msg : tableMsg) {
            create(db, msg);
        }
    }

    private void create(SQLiteDatabase db, TableMsg tableMsg) {
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
        boolean boo1 = tableMsg.isIncrease()
                && ("long".equals(tableMsg.getType()) || "Long".equals(tableMsg.getType()));
        //有主键自增
        if (boo1) {
            sql = "create table " + tableMsg.getTableName() + "(" + tableMsg.getId() + " integer primary key autoincrement," + field + ")";
            db.execSQL(sql);
            return;
        }
        //有主键不为空
        boo1 = !TextUtils.isEmpty(tableMsg.getId())
                && !"null".equals(tableMsg.getId().toLowerCase().trim())
                && !"".equals(tableMsg.getId().trim()) && tableMsg.isNotNULL();
        if (boo1) {
            sql = "create table " + tableMsg.getTableName() + "(" + tableMsg.getId() + " integer primary key NOT NULL," + field + ")";
            db.execSQL(sql);
            return;
        }
        //有主键不为空
        boo1 = !TextUtils.isEmpty(tableMsg.getId())
                && !"null".equals(tableMsg.getId().toLowerCase().trim())
                && !"".equals(tableMsg.getId().trim()) && tableMsg.isNotNULL();
        if (boo1) {
            sql = "create table " + tableMsg.getTableName() + "(" + tableMsg.getId() + " integer primary key," + field + ")";
            db.execSQL(sql);
            return;
        }
        sql = "create table " + tableMsg.getTableName() + " (" + field + ")";
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
//        if (oldVersion < newVersion) {
//            for (TableMsg msg : this.tableMsg) {
//                if (tableIsExist(msg.getTableName())) {
//                    changeTable(db, msg);
//                } else {
//                    String sql = "DROP TABLE IF EXISTS " + msg.getTableName();
//                    db.execSQL(sql);
//                    create(db, msg);
//                }
//            }
//        }
        if (oldVersion < newVersion) {
            for (TableMsg msg : tableMsg) {
                if (tableIsExist(db, msg.getTableName())) {
                    changeTable(db, msg);
                } else {
                    create(db, msg);
                }
            }
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion > newVersion) {
            for (TableMsg msg : this.tableMsg) {
                if (tableIsExist(db, msg.getTableName())) {
                    changeTable(db, msg);
//                } else {
//                    String sql = "DROP TABLE IF EXISTS " + msg.getTableName();
//                    db.execSQL(sql);
                    create(db, msg);
                }
            }
        }
    }

    /**
     * 将上一版本数据与字段解析
     *
     * @param db
     */
    private void change(SQLiteDatabase db, TableMsg tableMsg) {
        //第一、先把t_message 未来的表，改名
        String tabBak = "alter table " + tableMsg.getTableName() + " rename to " + tableMsg.getTableName().trim() + "_bak";
        db.execSQL(tabBak);
        //第二、建立2.0的表结构
        create(db, tableMsg);
        //第三、把备份的数据，copy到 新建的2.0的表
        List<FieldMsg> list = tableMsg.getList();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            str.append(list.get(i).getKey());
            if (i != list.size() - 1) {
                str.append(",");
            }
        }
        String copyTable = "insert into " + tableMsg.getTableName() + " select " + str.toString() + " from " + tableMsg.getTableName().trim() + "_bak";
        db.execSQL(copyTable);
        //第四、把备份表drop掉
        String dropTableBak = "drop table if exists " + tableMsg.getTableName().trim() + "_bak";
        db.execSQL(dropTableBak);
    }


    private void changeTable(SQLiteDatabase db, TableMsg tableMsg) {
        try {
            List<Map<String, String>> list = new LinkedList<>();
            String sql = "select * from " + tableMsg.getTableName();
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
            sql = "DROP TABLE IF EXISTS " + tableMsg.getTableName();
            db.execSQL(sql);
            if (list.size() > 0) {
                Set<String> set = new HashSet<>();
                for (int i = 0; i < list.size(); i++) {
                    for (Map.Entry<String, String> entry : list.get(i).entrySet()) {
                        set.add(entry.getKey());
                    }
                }
                Set<String> keySet = new HashSet<>();
                for (int i = 0; i < tableMsg.getList().size(); i++) {
                    for (String str : set) {
                        if (tableMsg.getList().get(i).getKey().equals(str) || tableMsg.getId().equals(str)) {
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
                tableMsg.getList().addAll(fieldMsgList);
                create(db, tableMsg);
                insertData(db, list, tableMsg);
            } else {
                sql = "DROP TABLE IF EXISTS " + tableMsg.getTableName();
                db.execSQL(sql);
                create(db, tableMsg);
            }
        } catch (Exception e) {
            String sql = "DROP TABLE IF EXISTS " + tableMsg.getTableName();
            db.execSQL(sql);
            create(db, tableMsg);
        }
    }

    /**
     * 数据插入表
     *
     * @param db
     * @param list 上一版本数据
     */
    private void insertData(SQLiteDatabase db, List<Map<String, String>> list, TableMsg tableMsg) {
        for (int i = 0; i < list.size(); i++) {
            ContentValues values = new ContentValues();
            for (Map.Entry<String, String> entry : list.get(i).entrySet()) {
                if (!entry.getKey().equals(tableMsg.getId())) {
                    values.put(entry.getKey(), entry.getValue());
                }
            }
            db.insert(tableMsg.getTableName(), null, values);
        }
    }

    /**
     * @param context  上下文
     * @param dbName   tab名
     * @param tableMsg 表信息集合
     * @param version  版本号
     */
    TableHelper(Context context, String dbName, List<TableMsg> tableMsg, int version) {
        super(context, dbName, null, version);
        this.dbName = dbName;
        this.mContext = context;
        this.tableMsg = tableMsg;
//        for (TableMsg msg : this.tableMsg) {
//            Log.v("数据库", msg.getTableName() + "\t" + tableIsExist(msg.getTableName()));
//        }
    }

    @Override
    public SQLiteDatabase getHelperWritableDatabase() {
        if (database == null) {
            database = getWritableDatabase();
        }
        return database;
    }

    /**
     * 判断某张表是否存在
     *
     * @param tableName 表名
     * @return 存在返回false
     */
    public boolean tableIsExist(SQLiteDatabase db, String tableName) {
        boolean result = false;
        if (tableName == null) {
            return false;
        }
        Cursor cursor = null;
        try {
            String sql = "select count(*) as c from Sqlite_master  where type ='table' and name ='" + tableName.trim() + "' ";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }

//    @Override
//    public boolean tableIsExist(String tableName) {
//        boolean result = false;
//        if (tableName == null) {
//            return true;
//        }
//        SQLiteDatabase db;
//        Cursor cursor;
//        try {
//            db = getReadableDatabase();
//            String sql = "select count(*) as c from Sqlite_master  where type ='table' and name ='" + tableName.trim() + "' ";
//            cursor = db.rawQuery(sql, null);
//            if (cursor.moveToNext()) {
//                int count = cursor.getInt(0);
//                if (count > 0) {
//                    result = true;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
////            return true;
//        }
//        return !result;
//    }

    @Override
    public boolean tabIsExist(String tabName) {
        boolean result = false;
        if (tabName == null) {
            return false;
        }
        Cursor cursor;
        try {
            String sql = "select count(*)  from sqlite_master where type='table' and name = '" + tabName.trim() + "'";
            cursor = getReadableDatabase().rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }

    /**
     * 获取数据库路径
     **/
    @Override
    public String getDBPath(String tabName) {
        return this.mContext.getDatabasePath(tabName).getPath();
    }


    @Override
    public void tableClose() {
        this.close();
    }
}
