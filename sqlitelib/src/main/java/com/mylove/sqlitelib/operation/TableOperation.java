package com.mylove.sqlitelib.operation;

import android.database.sqlite.SQLiteDatabase;

import com.mylove.sqlitelib.callback.OperationCallBack;
import com.mylove.sqlitelib.config.TableSort;

/**
 * @author YanYi
 * @date 2019/3/29 14:36
 * @email ben@yanyi.red
 * @overview
 */
public class TableOperation implements OperationCallBack {
    private SQLiteDatabase database;
    private Class<?> tClass;
    private TableSort sort;
    private String field;
    private String conditionKey;
    private String[] conditionValue;

    private TableOperation(SQLiteDatabase database, Class<?> tClass, Builder builder) {
        this.database = database;
        this.tClass = tClass;
        this.sort = builder.sort;
        this.field = builder.field;
        this.conditionKey = builder.conditionKey;
        this.conditionValue = builder.conditionValue;
    }

    @Override
    public TableInsert insert() {
        return new TableInsert.Builder()
                .setDatabase(this.database)
                .builder(this.tClass);
    }

    @Override
    public TableDelete delete() {
        return new TableDelete.Builder()
                .setTableQuery(query())
                .setConditionKey(this.conditionKey)
                .setConditionValue(this.conditionValue)
                .builder(database, this.tClass);
    }

    @Override
    public TableQuery query() {
        return new TableQuery.Builder()
                .setField(this.field)
                .setSort(this.sort)
                .setConditionKey(this.conditionKey)
                .setConditionValue(this.conditionValue)
                .builder(this.database, this.tClass);
    }

    @Override
    public TableUpdate update() {
        return new TableUpdate.Builder()
                .setTableQuery(query())
                .builder(this.database, this.tClass);
    }

    @Override
    public TableChangeOrAdd changeOrAdd() {
        TableInsert tableInsert = new TableInsert.Builder()
                .setDatabase(this.database)
                .builder(this.tClass);
        TableQuery tableQuery = new TableQuery.Builder()
                .setField(this.field)
                .setSort(this.sort)
                .setConditionKey(this.conditionKey)
                .setConditionValue(this.conditionValue)
                .builder(this.database, this.tClass);
        TableUpdate tableUpdate = new TableUpdate.Builder()
                .setTableQuery(query())
                .builder(this.database, this.tClass);
        return new TableChangeOrAdd.Builder()
                .setTableInsert(tableInsert)
                .setTableQuery(tableQuery)
                .setTableUpdate(tableUpdate)
                .builder();
    }

    public static class Builder {

        private TableSort sort;
        private String field;
        private String conditionKey;
        private String[] conditionValue;

        public Builder setConditionKey(String conditionKey) {
            this.conditionKey = conditionKey;
            return this;
        }

        public Builder setConditionValue(String[] conditionValue) {
            this.conditionValue = conditionValue;
            return this;
        }

        public Builder setSort(TableSort sort) {
            this.sort = sort;
            return this;
        }

        public Builder setField(String field) {
            this.field = field;
            return this;
        }

        public <T> TableOperation builder(SQLiteDatabase database, Class<T> tClass) {
            return new TableOperation(database, tClass, this);
        }
    }
}
