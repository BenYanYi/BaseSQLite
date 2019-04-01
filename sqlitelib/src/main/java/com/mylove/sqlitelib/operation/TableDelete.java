package com.mylove.sqlitelib.operation;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.googlecode.openbeans.PropertyDescriptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author YanYi
 * @date 2019/4/1 12:33
 * @email ben@yanyi.red
 * @overview
 */
public class TableDelete {
    private SQLiteDatabase database;
    private TableQuery tableQuery;
    private Class<?> tClass;

    private TableDelete(SQLiteDatabase database, Class<?> tClass, Builder builder) {
        this.database = database;
        this.tClass = tClass;
        this.tableQuery = builder.tableQuery;
    }

    public void findFirst() {
        Object first = this.tableQuery.findFirst();
        if (null != first) {
            delete(first);
        }
    }

    public void findLast() {
        Object last = this.tableQuery.findLast();
        if (null != last) {
            delete(last);
        }
    }

    private void delete(Object obj) {
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            StringBuilder builder = new StringBuilder();
            String[] value = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (!field.getName().equals("$change") && !field.getName().equals("serialVersionUID") &&
                        !TextUtils.isEmpty(field.getName()) && !"null".equals(field.getName()) && field.getName().trim().length() != 0) {
                    PropertyDescriptor descriptor = new PropertyDescriptor(fields[i].getName(), this.tClass);
                    Method method = descriptor.getReadMethod();//获得读方法
                    builder.append(fields[i].getName()).append("= ? ").append(" and ");
                    value[i] = (String) method.invoke(obj);
                }
            }
            this.database.delete(this.tClass.getSimpleName(), builder.toString(), value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Builder {
        private TableQuery tableQuery;

        Builder setTableQuery(TableQuery tableQuery) {
            this.tableQuery = tableQuery;
            return this;
        }

        <T> TableDelete builder(SQLiteDatabase database, Class<T> tClass) {
            return new TableDelete(database, tClass, this);
        }
    }
}
