package com.mylove.sqlitelib.operation;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.googlecode.openbeans.PropertyDescriptor;
import com.mylove.sqlitelib.callback.TableQueryCallBack;
import com.mylove.sqlitelib.config.TableSort;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author YanYi
 * @date 2019/3/29 15:31
 * @email ben@yanyi.red
 * @overview
 */
public class TableQuery implements TableQueryCallBack {
    private SQLiteDatabase database;
    private Class<?> tClass;
    private Builder builder;

    private TableQuery(SQLiteDatabase database, Class<?> tClass, Builder builder) {
        this.database = database;
        this.tClass = tClass;
        this.builder = builder;
    }

    private Cursor query() {
        String orderBy = null;
        if (!TextUtils.isEmpty(builder.field) && !"null".equals(builder.field) && builder.field.trim().length() != 0) {
            if (builder.sort != TableSort.DETAILS) {
                orderBy = builder.field + builder.sort.getSort();
            }
        }
        return this.database.query(TableTool.getTabName(this.tClass), null, this.builder.conditionKey,
                this.builder.conditionValue, null, null, orderBy);
    }

    public <T> List<T> findAll() {
        Cursor cursor = query();
        List<T> list = getCursor(cursor);
        cursor.close();
        return list;
    }

    public <T> T findFirst() {
        Cursor cursor = query();
        List<T> list = getCursor(cursor);
        cursor.close();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public <T> T findLast() {
        Cursor cursor = query();
        List<T> list = getCursor(cursor);
        cursor.close();
        if (list.size() > 0) {
            return list.get(list.size() - 1);
        } else {
            return null;
        }
    }

    private <T> List<T> getCursor(Cursor cursor) {
        try {
            List<T> list = new LinkedList<>();
            while (cursor.moveToNext()) {
                Object instance = tClass.newInstance();
                Field[] fields = tClass.getDeclaredFields();
                for (Field field : fields) {
                    if (!field.getName().equals("$change") && !field.getName().equals("serialVersionUID") &&
                            !TextUtils.isEmpty(field.getName()) && !"null".equals(field.getName()) && field.getName().trim().length() != 0) {
                        PropertyDescriptor pd = new PropertyDescriptor(field.getName(), tClass);
                        Method method = pd.getWriteMethod();//获取写方法
                        cursor.getString(cursor.getColumnIndex(field.getName()));
                        String simpleName = field.getType().getSimpleName();
                        if (simpleName.toLowerCase().equals("boolean")) {
                            int i = cursor.getInt(cursor.getColumnIndex(field.getName()));
                            method.invoke(instance, i == 1);
                        } else if (simpleName.toLowerCase().equals("byte")) {
                            String str = cursor.getString(cursor.getColumnIndex(field.getName()));
                            if (!TextUtils.isEmpty(str) && !"null".equals(str) && str.trim().length() != 0) {
                                method.invoke(instance, str.getBytes()[0]);
                            } else {
                                method.invoke(instance, null);
                            }
                        } else if (simpleName.equals("int") || simpleName.equals("Integer")) {
                            int i = cursor.getInt(cursor.getColumnIndex(field.getName()));
                            method.invoke(instance, i);
                        } else if (simpleName.toLowerCase().equals("long")) {
                            long aLong = cursor.getLong(cursor.getColumnIndex(field.getName()));
                            method.invoke(instance, aLong);
                        } else if (simpleName.toLowerCase().equals("short")) {
                            short aShort = cursor.getShort(cursor.getColumnIndex(field.getName()));
                            method.invoke(instance, aShort);
                        } else if (simpleName.toLowerCase().equals("float")) {
                            float aFloat = cursor.getFloat(cursor.getColumnIndex(field.getName()));
                            method.invoke(instance, aFloat);
                        } else if (simpleName.toLowerCase().equals("double")) {
                            double aDouble = cursor.getDouble(cursor.getColumnIndex(field.getName()));
                            method.invoke(instance, aDouble);
                        } else if (simpleName.equals("char") || simpleName.equals("Character")) {
                            String str = cursor.getString(cursor.getColumnIndex(field.getName()));
                            if (!TextUtils.isEmpty(str) && !"null".equals(str) && str.trim().length() != 0) {
                                method.invoke(instance, str.charAt(0));
                            } else {
                                method.invoke(instance, null);
                            }
                        } else {
                            String str = cursor.getString(cursor.getColumnIndex(field.getName()));
                            method.invoke(instance, str);
                        }
                    }
                }
                list.add((T) instance);
            }
            return list;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    static class Builder {
        private String conditionKey;
        private String[] conditionValue;
        private TableSort sort = TableSort.DETAILS;
        private String field;

        Builder setConditionKey(String conditionKey) {
            this.conditionKey = conditionKey;
            return this;
        }

        Builder setConditionValue(String[] conditionValue) {
            this.conditionValue = conditionValue;
            return this;
        }

        Builder setSort(TableSort sort) {
            this.sort = sort;
            return this;
        }

        Builder setField(String field) {
            this.field = field;
            return this;
        }

        <T> TableQueryCallBack builder(SQLiteDatabase database, Class<T> tClass) {
            return new TableQuery(database, tClass, this);
        }
    }
}
