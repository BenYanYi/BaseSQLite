package com.mylove.sqlitelib.operation;

import android.database.sqlite.SQLiteDatabase;

import com.mylove.sqlitelib.callback.OperationCallBack;
import com.mylove.sqlitelib.callback.TableChangeOrAddCallBack;
import com.mylove.sqlitelib.callback.TableDeleteCallBack;
import com.mylove.sqlitelib.callback.TableInsertCallBack;
import com.mylove.sqlitelib.callback.TableQueryCallBack;
import com.mylove.sqlitelib.callback.TableUpdateCallBack;
import com.mylove.sqlitelib.config.TableSort;

/**
 * @author YanYi
 * @date 2019/3/29 14:36
 * @email ben@yanyi.red
 * @overview 增删拆逻辑区分
 */
public final class TableOperation<T> implements OperationCallBack<T> {
    private SQLiteDatabase database;
    private Class<T> tClass;
    private TableSort sort;
    private String field;
    private String conditionKey;
    private String[] conditionValue;

    private TableOperation() {
    }

    private TableOperation(SQLiteDatabase database, Class<T> tClass, Builder builder) {
        this.database = database;
        this.tClass = tClass;
        this.sort = builder.sort;
        this.field = builder.field;
        this.conditionKey = builder.conditionKey;
        this.conditionValue = builder.conditionValue;
    }

    @Override
    public TableInsertCallBack<T> insert() {
        return new TableInsert.Builder()
                .setDatabase(this.database)
                .builder(this.tClass);
    }

    @Override
    public TableDeleteCallBack<T> delete() {
        return new TableDelete.Builder()
                .setTableQueryCallBack(query())
                .setConditionKey(this.conditionKey)
                .setConditionValue(this.conditionValue)
                .builder(database, this.tClass);
    }

    @Override
    public TableQueryCallBack<T> query() {
        return new TableQuery.Builder()
                .setField(this.field)
                .setSort(this.sort)
                .setConditionKey(this.conditionKey)
                .setConditionValue(this.conditionValue)
                .builder(this.database, this.tClass);
    }

    @Override
    public TableUpdateCallBack<T> update() {
        return new TableUpdate.Builder()
                .setTableQueryCallBack(query())
                .builder(this.database, this.tClass);
    }

    @Override
    public TableChangeOrAddCallBack<T> changeOrAdd() {
        return new TableChangeOrAdd.Builder()
                .setTableInsertCallBack(insert())
                .setTableQueryCallBack(query())
                .setTableUpdateCallBack(update())
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

        public <T> OperationCallBack<T> builder(SQLiteDatabase database, Class<T> tClass) {
            return new TableOperation(database, tClass, this);
        }
    }
}
