package com.mylove.sqlitelib.operation;

import android.database.sqlite.SQLiteDatabase;

import com.mylove.sqlitelib.TableSort;
import com.mylove.sqlitelib.callback.OperationCallBack;
import com.mylove.sqlitelib.exception.TabException;

import java.util.List;
import java.util.Map;

/**
 * @author YanYi
 * @date 2019/3/29 14:36
 * @email ben@yanyi.red
 * @overview
 */
public class TabOperation implements OperationCallBack {
    private SQLiteDatabase database;
    private Builder builder;
    private Class<?> tClass;
    private TableSort sort = TableSort.DETAILS;
    private String field;

    TabOperation(SQLiteDatabase database, Class<?> tClass, TableSort sort, String field, Builder builder) {
        this.database = database;
        this.tClass = tClass;
        this.sort = sort;
        this.field = field;
        this.builder = builder;
    }

    @Override
    public <T> long insert(T t) {
        if (this.tClass != t.getClass()) {
            throw new TabException("添加的数据与表不符");
        }
        return TabInsert.insert(t, this.database);
    }

    @Override
    public <T> long[] insert(List<T> list) {
        if (list == null || list.size() <= 0) {
            throw new TabException("添加的数据列表不能为空");
        }
        if (this.tClass != list.get(0).getClass()) {
            throw new TabException("添加的数据与表不符");
        }
        long[] l = new long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            l[i] = TabInsert.insert(list.get(i), this.database);
        }
        return l;
    }

    @Override
    public void delete() {

    }

    @Override
    public TabQuery query() {
        TabQuery.Builder builder = new TabQuery.Builder()
                .setConditionKey(conditionKey());
        return builder.builder(this.database, this.tClass);
    }

    @Override
    public <T> void update(T t) {

    }

    private String conditionKey() {
        StringBuilder builder = new StringBuilder();
        if (this.builder.toMap != null && this.builder.toMap.size() > 0) {
            for (Map.Entry<String, Object> entry : this.builder.toMap.entrySet()) {
                builder.append(entry.getKey()).append("= ? and ");
            }
            builder = builder.delete(builder.length() - 4, builder.length());
        }
        if (this.builder.inMap != null && this.builder.inMap.size() > 0) {
            for (Map.Entry<String, Object> entry : this.builder.inMap.entrySet()) {
                builder.append(entry.getKey()).append("like ? and ");
            }
            builder = builder.delete(builder.length() - 4, builder.length());
        }
        if (this.builder.orMap != null && this.builder.orMap.size() > 0) {
            for (Map.Entry<String, Object> entry : this.builder.orMap.entrySet()) {
                builder.append(entry.getKey()).append("= ? or ");
            }
            builder = builder.delete(builder.length() - 3, builder.length());
        }
        return builder.toString();
    }

    private String conditionNotKey() {
        StringBuilder builder = new StringBuilder();
        if (this.builder.notToMap != null && this.builder.notToMap.size() > 0) {
            for (Map.Entry<String, Object> entry : this.builder.notToMap.entrySet()) {
                builder.append(entry.getKey()).append("= ? and ");
            }
            builder = builder.delete(builder.length() - 4, builder.length());
        }
        if (this.builder.notInMap != null && this.builder.notInMap.size() > 0) {
            for (Map.Entry<String, Object> entry : this.builder.notInMap.entrySet()) {
                builder.append(entry.getKey()).append("like ? and ");
            }
            builder = builder.delete(builder.length() - 4, builder.length());
        }
        if (this.builder.notOrMap != null && this.builder.notOrMap.size() > 0) {
            for (Map.Entry<String, Object> entry : this.builder.notOrMap.entrySet()) {
                builder.append(entry.getKey()).append("= ? or ");
            }
            builder = builder.delete(builder.length() - 3, builder.length());
        }
        return builder.toString();
    }

    private String[] conditionValue() {
        StringBuilder builder = new StringBuilder();
        if (this.builder.toMap != null && this.builder.toMap.size() > 0) {
            for (Map.Entry<String, Object> entry : this.builder.toMap.entrySet()) {
                builder.append(entry.getKey()).append("_");
            }
            builder = builder.delete(builder.length() - 1, builder.length());
        }
        if (this.builder.inMap != null && this.builder.inMap.size() > 0) {
            for (Map.Entry<String, Object> entry : this.builder.inMap.entrySet()) {
                builder.append(entry.getKey()).append("_");
            }
            builder = builder.delete(builder.length() - 1, builder.length());
        }
        if (this.builder.orMap != null && this.builder.orMap.size() > 0) {
            for (Map.Entry<String, Object> entry : this.builder.orMap.entrySet()) {
                builder.append(entry.getKey()).append("_");
            }
            builder = builder.delete(builder.length() - 1, builder.length());
        }
        return builder.toString().split("_");
    }

    private String[] conditionNotValue() {
        StringBuilder builder = new StringBuilder();
        if (this.builder.notToMap != null && this.builder.notToMap.size() > 0) {
            for (Map.Entry<String, Object> entry : this.builder.notToMap.entrySet()) {
                builder.append(entry.getKey()).append("_");
            }
            builder = builder.delete(builder.length() - 1, builder.length());
        }
        if (this.builder.notInMap != null && this.builder.notInMap.size() > 0) {
            for (Map.Entry<String, Object> entry : this.builder.notInMap.entrySet()) {
                builder.append(entry.getKey()).append("_");
            }
            builder = builder.delete(builder.length() - 1, builder.length());
        }
        if (this.builder.notOrMap != null && this.builder.notOrMap.size() > 0) {
            for (Map.Entry<String, Object> entry : this.builder.notOrMap.entrySet()) {
                builder.append(entry.getKey()).append("_");
            }
            builder = builder.delete(builder.length() - 1, builder.length());
        }
        return builder.toString().split("_");
    }

    public static class Builder {
        private Map<String, Object> toMap;
        private Map<String, Object> notToMap;
        private Map<String, Object> orMap;
        private Map<String, Object> notOrMap;
        private Map<String, Object> inMap;
        private Map<String, Object> notInMap;

        public Builder setToMap(Map<String, Object> toMap) {
            this.toMap = toMap;
            return this;
        }

        public Builder setNotToMap(Map<String, Object> notToMap) {
            this.notToMap = notToMap;
            return this;
        }

        public Builder setOrMap(Map<String, Object> orMap) {
            this.orMap = orMap;
            return this;
        }

        public Builder setNotOrMap(Map<String, Object> notOrMap) {
            this.notOrMap = notOrMap;
            return this;
        }

        public Builder setInMap(Map<String, Object> inMap) {
            this.inMap = inMap;
            return this;
        }

        public Builder setNotInMap(Map<String, Object> notInMap) {
            this.notInMap = notInMap;
            return this;
        }

        public <T> TabOperation builder(SQLiteDatabase database, Class<T> tClass, TableSort sort, String field) {
            return new TabOperation(database, tClass, sort, field, this);
        }
    }
}
