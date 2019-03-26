package com.mylove.sqlitelib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.mylove.sqlitelib.util.StringUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yanyi
 * sqlite工具类
 */

public class DBUtil {
    private static DBUtil instance;
    private String tName;
    private SQLiteDatabase db;

    private DBUtil(String tabName, DBHelper dbHelper) {
        this.tName = tabName;
        //创建数据库
        this.db = dbHelper.getWritableDatabase();
    }

    /**
     * 单例模式
     *
     * @param mContext 上下文
     * @param tabName  tab名
     * @param dbName   db名
     * @param fieldMap 表字段
     * @param version  版本号
     * @return this
     */
    public static DBUtil getInstance(Context mContext, String tabName, String dbName, Map<String, String> fieldMap, int version) {
//        if (instance == null) {
//            synchronized (DBUtil.class) {
        DBHelper dbHelper = new DBHelper(mContext, tabName, fieldMap, dbName, version);
        instance = new DBUtil(tabName, dbHelper);
//            }
//        }
        return instance;
    }

    /**
     * 插入数据
     *
     * @param oMap 要插入数据的key和value
     */
    public long insert(Map<String, String> oMap) {
        return db.insert(tName, null, values(oMap));
    }

    /**
     * 查询全部语句
     *
     * @return 返回结果
     */
    public Cursor query() {
        return db.query(tName, null, null, null, null, null, null);
    }

    /**
     * 查询全部语句
     *
     * @return 返回结果
     */
    public <T> List<T> query(Class<T> tClass) {
        List<T> oList = new ArrayList<>();
        JSONArray array = getCursor(db.query(tName, null, null, null, null, null, null));
        Gson gson = new Gson();
        if (StringUtil.isJSONArrayNotEmpty(array)) {
            try {
                for (int i = 0; i < array.length(); i++) {
                    String s = array.getJSONObject(i).toString();
                    oList.add(gson.fromJson(s, tClass));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return oList;
    }

    /**
     * 根据条件查询数据
     *
     * @param oMap 查询条件的key和value
     * @return 返回结果
     */
    public Cursor query(Map<String, String> oMap) {
        return db.query(tName, null, selection(oMap), arr(oMap), null, null, null);
    }

    /**
     * 根据条件查询数据
     *
     * @param oMap 查询条件的key和value
     * @return 返回结果
     */
    public <T> List<T> query(Map<String, String> oMap, Class<T> tClass) {
        List<T> oList = new ArrayList<>();
        JSONArray array = getCursor(db.query(tName, null, selection(oMap), arr(oMap), null, null, null));
        Gson gson = new Gson();
        if (StringUtil.isJSONArrayNotEmpty(array)) {
            try {
                for (int i = 0; i < array.length(); i++) {
                    String s = array.getJSONObject(i).toString();
                    oList.add(gson.fromJson(s, tClass));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return oList;
    }

    /**
     * 修改数据
     *
     * @param oMap 修改条件key和value
     * @param sMap 修改后的内容key和value
     * @return 修改条数
     */
    public int update(Map<String, String> oMap, Map<String, String> sMap) {
        return db.update(tName, values(oMap), selection(sMap), arr(sMap));
    }

    /**
     * 删除数据
     *
     * @param oMap 删除的条件key和value
     * @return 删除的数量
     */
    public int delete(Map<String, String> oMap) {
        return db.delete(tName, selection(oMap), arr(oMap));
    }

    private ContentValues values(Map<String, String> map) {
        ContentValues values = new ContentValues();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            values.put(entry.getKey(), entry.getValue());
        }
        return values;
    }

    private String selection(Map<String, String> map) {
        StringBuilder selection = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            selection.append(entry.getKey()).append("=? and ");
        }
        selection = selection.delete(selection.length() - 4, selection.length());
        return selection.toString();
    }

    private String[] arr(Map<String, String> map) {
        int i = 0;
        String[] arr = new String[map.size()];
        for (Map.Entry<String, String> entry : map.entrySet()) {
            arr[i] = entry.getValue();
            i++;
        }
        return arr;
    }

    private JSONArray getCursor(Cursor cursor) {
        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (Exception e) {
                        Log.d("TAG_NAME", e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        return resultSet;
    }
}
