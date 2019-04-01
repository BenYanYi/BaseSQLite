package com.mylove.sqlitelib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.mylove.sqlitelib.condition.ConditionMsg;
import com.mylove.sqlitelib.condition.TableCondition;

import java.util.ArrayList;

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

    private TableSession(Context context, Builder builder) {
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
                .setEqList(new ArrayList<ConditionMsg>())
                .setNotEqList(new ArrayList<ConditionMsg>())
                .setGreaterList(new ArrayList<ConditionMsg>())
                .setLessList(new ArrayList<ConditionMsg>())
                .setInList(new ArrayList<ConditionMsg>());
        return builder.builder(getDB(tClass), tClass);
    }

    public static class Builder {
        private String dbName = null;
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
            if (TextUtils.isEmpty(dbName)) {
                dbName = context.getPackageName() + "_TABLE_DB";
            }
            return new TableSession(context, this);
        }
    }
}
