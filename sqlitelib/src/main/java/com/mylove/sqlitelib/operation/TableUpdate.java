package com.mylove.sqlitelib.operation;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.googlecode.openbeans.PropertyDescriptor;
import com.mylove.sqlitelib.exception.TableException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

    private TableUpdate(SQLiteDatabase database, Class<?> tClass, Builder builder) {
        this.database = database;
        this.tClass = tClass;
        this.tableQuery = builder.tableQuery;
    }

    public <T> int findFirst(T t) {
        Object first = this.tableQuery.findFirst();
        return update(t, first);
    }

    public <T> int findLast(T t) {
        Object last = this.tableQuery.findLast();
        return update(t, last);
    }

    private <T> int update(T t, Object obj) {
        if (this.tClass != t.getClass()) {
            throw new TableException("修改的数据与表不符");
        }
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
            return this.database.update(this.tClass.getSimpleName(), TableTool.values(t), builder.toString(), value);
        } catch (Exception e) {
            e.fillInStackTrace();
            return 0;
        }
    }

    static class Builder {
        private TableQuery tableQuery;

        Builder setTableQuery(TableQuery tableQuery) {
            this.tableQuery = tableQuery;
            return this;
        }

        <T> TableUpdate builder(SQLiteDatabase database, Class<T> tClass) {
            return new TableUpdate(database, tClass, this);
        }
    }
}
