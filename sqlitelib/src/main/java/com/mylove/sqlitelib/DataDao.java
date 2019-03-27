package com.mylove.sqlitelib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mylove.sqlitelib.init.SQLiteHelper;

/**
 * @author YanYi
 * @date 2019/3/27 10:12
 * @email ben@yanyi.red
 * @overview
 */
public class DataDao {
    public static final String DB_NAME = "base_db_db_name";
    private Context context;
    private String dbName;
    private int version;

    DataDao(Context context, Builder builder) {
        this.context = context;
        this.dbName = builder.dbName;
        this.version = builder.version;
    }

    private SQLiteDatabase getDB() {
        SQLiteHelper sqLiteHelper = SQLiteInject.init(context, dbName, version);
        return sqLiteHelper.getWritableDatabase();
    }

    public <T> void insert(T t) {

    }

    public static class Builder {
        private String dbName = DB_NAME;
        private int version = 1;

        public void setDbName(String dbName) {
            this.dbName = dbName;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public DataDao builder(Context context) {
            return new DataDao(context, this);
        }
    }
}
