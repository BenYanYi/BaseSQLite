package com.mylove.sqlitelib.operation;

import android.database.sqlite.SQLiteDatabase;

import com.mylove.sqlitelib.config.TableSort;
import com.mylove.sqlitelib.callback.OperationCallBack;
import com.mylove.sqlitelib.exception.TableException;

import java.util.List;

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
    public <T> long insert(T t) {
        if (this.tClass != t.getClass()) {
            throw new TableException("添加的数据与表不符");
        }
        return TableInsert.insert(t, this.database);
    }

    @Override
    public <T> long[] insert(List<T> list) {
        if (list == null || list.size() <= 0) {
            throw new TableException("添加的数据列表不能为空");
        }
        if (this.tClass != list.get(0).getClass()) {
            throw new TableException("添加的数据与表不符");
        }
        long[] l = new long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            l[i] = TableInsert.insert(list.get(i), this.database);
        }
        return l;
    }

    @Override
    public TableDelete delete() {
        return new TableDelete.Builder().setTableQuery(query()).builder(database, this.tClass);
    }

    @Override
    public int deleteAll() {
        return this.database.delete(this.tClass.getSimpleName(), this.conditionKey, this.conditionValue);
    }


    @Override
    public TableQuery query() {
        TableQuery.Builder builder = new TableQuery.Builder()
                .setField(this.field)
                .setSort(this.sort)
                .setConditionKey(this.conditionKey)
                .setConditionValue(this.conditionValue);
        return builder.builder(this.database, this.tClass);
    }

    @Override
    public <T> int updateAll(T t) {
        return this.database.update(t.getClass().getSimpleName(), TableTool.values(t), this.conditionKey, this.conditionValue);
    }

    @Override
    public TableUpdate update() {
        return new TableUpdate.Builder()
                .setTableQuery(query())
                .builder(this.database, this.tClass);
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
