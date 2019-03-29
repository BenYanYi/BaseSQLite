package com.mylove.sqlitelib.operation;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author YanYi
 * @date 2019/3/29 15:31
 * @email ben@yanyi.red
 * @overview
 */
class TabQuery {
    private SQLiteDatabase database;
    private Class<?> tClass;
    private Builder builder;

    TabQuery(SQLiteDatabase database, Class<?> tClass, Builder builder) {
        this.database = database;
        this.tClass = tClass;
        this.builder = builder;
    }

    void query() {
        Cursor cursor = this.database.query(tClass.getSimpleName(), null, this.builder.conditionKey,
                this.builder.conditionValue, null, null, null);
        Cursor notCursor = this.database.query(tClass.getSimpleName(), null, this.builder.conditionNotKey,
                this.builder.conditionNotValue, null, null, null);

    }

    static class Builder {
        private SQLiteDatabase database;
        private Class<?> tClass;
        private String conditionKey;
        private String conditionNotKey;
        private String[] conditionValue;
        private String[] conditionNotValue;

        Builder setConditionKey(String conditionKey) {
            this.conditionKey = conditionKey;
            return this;
        }

        Builder setConditionNotKey(String conditionNotKey) {
            this.conditionNotKey = conditionNotKey;
            return this;
        }

        Builder setConditionValue(String[] conditionValue) {
            this.conditionValue = conditionValue;
            return this;
        }

        Builder setConditionNotValue(String[] conditionNotValue) {
            this.conditionNotValue = conditionNotValue;
            return this;
        }

        <T> TabQuery builder(SQLiteDatabase database, Class<T> tClass) {
            return new TabQuery(database, tClass, this);
        }
    }
}
