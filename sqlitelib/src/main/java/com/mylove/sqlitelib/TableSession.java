package com.mylove.sqlitelib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mylove.sqlitelib.condition.TableCondition;

import java.util.HashMap;

/**
 * @author YanYi
 * @date 2019/3/27 10:12
 * @email ben@yanyi.red
 * @overview
 */
public class TableSession {
    private Context context;
    public static final String DB_NAME = "base_db_db_name";
    private String dbName;
    private int version;

    TableSession(Context context, Builder builder) {
        this.context = context;
        this.dbName = builder.dbName;
        this.version = builder.version;
    }

    private <T> SQLiteDatabase getDB(Class<T> tClass) {
        TableHelper tableHelper = TableInject.init(context, dbName, version, tClass);
        return tableHelper.getWritableDatabase();
    }

    public <T> TableCondition where(Class<T> tClass) {
        TableCondition.Builder builder = new TableCondition.Builder()
                .setInMap(new HashMap<String, Object>())
                .setNotInMap(new HashMap<String, Object>())
                .setToMap(new HashMap<String, Object>())
                .setNotToMap(new HashMap<String, Object>())
                .setOrMap(new HashMap<String, Object>())
                .setNotOrMap(new HashMap<String, Object>());
        return builder.builder(getDB(tClass), tClass);
    }

    public static class Builder {
        private String dbName = DB_NAME;
        private int version = 1;

        public Builder setDbName(String dbName) {
            this.dbName = dbName;
            return this;
        }

        public Builder setVersion(int version) {
            this.version = version;
            return this;
        }

        public TableSession builder(Context context) {
            return new TableSession(context, this);
        }
    }
}
