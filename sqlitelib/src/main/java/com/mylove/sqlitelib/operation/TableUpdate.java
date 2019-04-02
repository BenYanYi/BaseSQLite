package com.mylove.sqlitelib.operation;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.googlecode.openbeans.PropertyDescriptor;
import com.mylove.sqlitelib.exception.TableException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author YanYi
 * @date 2019/4/1 15:42
 * @email ben@yanyi.red
 * @overview
 */
public class TableUpdate {
    private SQLiteDatabase database;
    private Class<?> tClass;
    private TableQuery tableQuery;
    private String conditionKey;
    private String[] conditionValue;

    private TableUpdate(SQLiteDatabase database, Class<?> tClass, Builder builder) {
        this.database = database;
        this.tClass = tClass;
        this.tableQuery = builder.tableQuery;
        this.conditionKey = builder.conditionKey;
        this.conditionValue = builder.conditionValue;
    }

    public <T> int findFirst(T t) {
        Object first = this.tableQuery.findFirst();
        return update(t, first);
    }

    public <T> int findLast(T t) {
        Object last = this.tableQuery.findLast();
        return update(t, last);
    }

    public <T> int[] findAll(T t) {
        if (this.tClass != t.getClass()) {
            throw new TableException("修改的数据与表结构不符");
        }
        List<Object> all = this.tableQuery.findAll();
        int[] l = new int[all.size()];
        for (int i = 0; i < all.size(); i++) {
            l[i] = update(t, all.get(i));
        }
        return l;
    }

    private <T> int update(T t, Object obj) {
        if (this.tClass != t.getClass()) {
            throw new TableException("修改的数据与表结构不符");
        }
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            StringBuilder builder = new StringBuilder();
            List<String> list = new ArrayList<>();
            for (Field field : fields) {
                if (!field.getName().equals("$change") && !field.getName().equals("serialVersionUID") &&
                        !TextUtils.isEmpty(field.getName()) && !"null".equals(field.getName()) && field.getName().trim().length() != 0) {
                    PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), this.tClass);
                    Method method = descriptor.getReadMethod();//获得读方法
                    builder.append(field.getName()).append("= ? ").append(" and ");
                    Object invoke = method.invoke(obj);
                    list.add(invoke + "");
                }
            }
            builder = builder.delete(builder.length() - 4, builder.length());
            String[] value = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                value[i] = list.get(i);
            }
            return this.database.update(this.tClass.getSimpleName(), TableTool.values(t), builder.toString(), value);
        } catch (Exception e) {
            return 0;
        }
    }

    static class Builder {
        private TableQuery tableQuery;
        private String conditionKey;
        private String[] conditionValue;

        Builder setTableQuery(TableQuery tableQuery) {
            this.tableQuery = tableQuery;
            return this;
        }

        Builder setConditionKey(String conditionKey) {
            this.conditionKey = conditionKey;
            return this;
        }

        Builder setConditionValue(String[] conditionValue) {
            this.conditionValue = conditionValue;
            return this;
        }

        <T> TableUpdate builder(SQLiteDatabase database, Class<T> tClass) {
            return new TableUpdate(database, tClass, this);
        }
    }
}
