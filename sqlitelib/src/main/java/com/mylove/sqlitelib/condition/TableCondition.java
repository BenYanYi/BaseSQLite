package com.mylove.sqlitelib.condition;

import android.database.sqlite.SQLiteDatabase;

import com.mylove.sqlitelib.TableSort;
import com.mylove.sqlitelib.callback.ConditionCallBack;
import com.mylove.sqlitelib.operation.TabOperation;

import java.util.Map;

/**
 * @author YanYi
 * @date 2019/3/29 13:55
 * @email ben@yanyi.red
 * @overview
 */
public class TableCondition implements ConditionCallBack {
    private Map<String, Object> toMap;
    private Map<String, Object> notToMap;
    private Map<String, Object> orMap;
    private Map<String, Object> notOrMap;
    private Map<String, Object> inMap;
    private Map<String, Object> notInMap;
    private Class<?> tClass;
    private TableSort sort = TableSort.DETAILS;
    private String field;

    private SQLiteDatabase database;

    TableCondition(SQLiteDatabase database, Class<?> tClass, Builder builder) {
        this.database = database;
        this.tClass = tClass;
        this.toMap = builder.toMap;
        this.notToMap = builder.notToMap;
        this.orMap = builder.orMap;
        this.notOrMap = builder.notOrMap;
        this.inMap = builder.inMap;
        this.notInMap = builder.notInMap;
    }

    @Override
    public TableCondition equalTo(Map<String, Object> condition) {
        for (Map.Entry<String, Object> entry : condition.entrySet()) {
            this.toMap.put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    @Override
    public TableCondition equalTo(String field, Object value) {
        this.toMap.put(field, value);
        return this;
    }

    @Override
    public TableCondition or(Map<String, Object> condition) {
        for (Map.Entry<String, Object> entry : condition.entrySet()) {
            this.orMap.put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    @Override
    public TableCondition or(String field, Object value) {
        this.orMap.put(field, value);
        return this;
    }

    @Override
    public TableCondition in(Map<String, Object> condition) {
        for (Map.Entry<String, Object> entry : condition.entrySet()) {
            this.inMap.put(entry.getKey(), "'%" + entry.getValue() + "%'");
        }
        return this;
    }

    @Override
    public TableCondition in(String field, Object value) {
        this.inMap.put(field, "'%" + value + "%'");
        return this;
    }

    @Override
    public TableCondition notEqualTo(Map<String, Object> condition) {
        for (Map.Entry<String, Object> entry : condition.entrySet()) {
            this.notToMap.put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    @Override
    public TableCondition notEqualTo(String field, Object value) {
        this.notToMap.put(field, value);
        return this;
    }

    @Override
    public TableCondition notOr(Map<String, Object> condition) {
        for (Map.Entry<String, Object> entry : condition.entrySet()) {
            this.notOrMap.put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    @Override
    public TableCondition notOr(String field, Object value) {
        this.notOrMap.put(field, value);
        return this;
    }

    @Override
    public TableCondition notIn(Map<String, Object> condition) {
        for (Map.Entry<String, Object> entry : condition.entrySet()) {
            this.notInMap.put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    @Override
    public TableCondition notIn(String field, Object value) {
        this.notInMap.put(field, value);
        return this;
    }

    public TableCondition sort(TableSort sort, String field) {
        this.sort = sort;
        return this;
    }

    @Override
    public TabOperation operation() {
        TabOperation.Builder builder = new TabOperation.Builder()
                .setInMap(this.inMap)
                .setNotInMap(this.notInMap)
                .setToMap(this.toMap)
                .setNotToMap(this.notToMap)
                .setOrMap(this.orMap)
                .setNotOrMap(this.notOrMap);
        return builder.builder(this.database, tClass, this.sort, this.field);
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

        public TableCondition builder(SQLiteDatabase database, Class<?> tClass) {
            return new TableCondition(database, tClass, this);
        }
    }
}
