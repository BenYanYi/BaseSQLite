package com.benyanyi.sqlitelib.operation;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.benyanyi.sqlitelib.annotation.NotColumn;
import com.benyanyi.sqlitelib.config.TableSort;
import com.benyanyi.sqlitelib.annotation.ColumnName;
import com.benyanyi.sqlitelib.callback.TableQueryCallBack;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author YanYi
 * @date 2019/3/29 15:31
 * @email ben@yanyi.red
 * @overview
 */
public final class TableQuery<T> implements TableQueryCallBack<T> {
    private SQLiteDatabase database;
    private Class<T> tClass;
    private Builder builder;

    private TableQuery() {
    }

    private TableQuery(SQLiteDatabase database, Class<T> tClass, Builder builder) {
        this.database = database;
        this.tClass = tClass;
        this.builder = builder;
    }

    private Cursor query() {
        String orderBy = null;
        boolean boo = !TextUtils.isEmpty(builder.field) && !"null".equals(builder.field) && builder.field.trim().length() != 0;
        if (boo) {
            if (builder.sort != TableSort.DETAILS) {
                orderBy = builder.field + builder.sort.getSort();
            }
        }
        return this.database.query(TableTool.getTabName(this.tClass), null, this.builder.conditionKey,
                this.builder.conditionValue, null, null, orderBy);
    }

    @Override
    public List<T> findAll() {
        Cursor cursor = this.query();
        List<T> list = this.getCursor(cursor);
        cursor.close();
        return list;
    }

    @Override
    public T findFirst() {
        Cursor cursor = this.query();
        List<T> list = this.getCursor(cursor);
        cursor.close();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public T findLast() {
        Cursor cursor = this.query();
        List<T> list = this.getCursor(cursor);
        cursor.close();
        if (list.size() > 0) {
            return list.get(list.size() - 1);
        } else {
            return null;
        }
    }

    private List<T> getCursor(Cursor cursor) {
        try {
            List<T> list = new LinkedList<>();
            while (cursor.moveToNext()) {
                T instance = this.tClass.newInstance();
                Field[] fields = this.tClass.getDeclaredFields();
                for (Field field : fields) {
                    NotColumn notColumn = field.getAnnotation(NotColumn.class);
                    if (notColumn != null) {
                        if (notColumn.notColumn()) {
                            continue;
                        }
                    }
                    boolean boo = !"$change".equals(field.getName()) &&
                            !"serialVersionUID".equals(field.getName()) &&
                            !TextUtils.isEmpty(field.getName()) && !"null".equals(field.getName())
                            && field.getName().trim().length() != 0;
                    if (boo) {
                        String columnNameStr = field.getName();
                        ColumnName columnName = field.getAnnotation(ColumnName.class);
                        if (columnName != null) {
                            String value = columnName.value();
                            boolean boo1 = !TextUtils.isEmpty(value)
                                    && !"null".equals(value.toLowerCase().trim())
                                    && value.trim().length() > 0;
                            if (boo1) {
                                columnNameStr = value;
                            }
                        }
                        field.setAccessible(true);
                        cursor.getString(cursor.getColumnIndex(columnNameStr));
                        String simpleName = field.getType().getSimpleName();
                        if ("boolean".equals(simpleName.toLowerCase())) {
                            int i = cursor.getInt(cursor.getColumnIndex(columnNameStr));
                            field.set(instance, i == 1);
                        } else if ("byte".equals(simpleName.toLowerCase())) {
                            String str = cursor.getString(cursor.getColumnIndex(columnNameStr));
                            if (!TextUtils.isEmpty(str) && !"null".equals(str) && str.trim().length() != 0) {
                                field.set(instance, str.getBytes()[0]);
                            } else {
                                field.set(instance, null);
                            }
                        } else if ("int".equals(simpleName) || "Integer".equals(simpleName)) {
                            int i = cursor.getInt(cursor.getColumnIndex(columnNameStr));
                            field.set(instance, i);
                        } else if ("long".equals(simpleName.toLowerCase())) {
                            long aLong = cursor.getLong(cursor.getColumnIndex(columnNameStr));
                            field.set(instance, aLong);
                        } else if ("short".equals(simpleName.toLowerCase())) {
                            short aShort = cursor.getShort(cursor.getColumnIndex(columnNameStr));
                            field.set(instance, aShort);
                        } else if ("float".equals(simpleName.toLowerCase())) {
                            float aFloat = cursor.getFloat(cursor.getColumnIndex(columnNameStr));
                            field.set(instance, aFloat);
                        } else if ("double".equals(simpleName.toLowerCase())) {
                            double aDouble = cursor.getDouble(cursor.getColumnIndex(columnNameStr));
                            field.set(instance, aDouble);
                        } else if ("char".equals(simpleName) || "Character".equals(simpleName)) {
                            String str = cursor.getString(cursor.getColumnIndex(columnNameStr));
                            if (!TextUtils.isEmpty(str) && !"null".equals(str) && str.trim().length() != 0) {
                                field.set(instance, str.charAt(0));
                            } else {
                                field.set(instance, null);
                            }
                        } else {
                            String str = cursor.getString(cursor.getColumnIndex(columnNameStr));
                            field.set(instance, str);
                        }
                    }
                }
                list.add(instance);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
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

        <T> TableQueryCallBack<T> builder(SQLiteDatabase database, Class<T> tClass) {
            return new TableQuery(database, tClass, this);
        }
    }
}
