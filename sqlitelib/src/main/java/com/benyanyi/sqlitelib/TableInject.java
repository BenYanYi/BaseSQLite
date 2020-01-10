package com.benyanyi.sqlitelib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.benyanyi.sqlitelib.annotation.ID;
import com.benyanyi.sqlitelib.annotation.NotColumn;
import com.benyanyi.sqlitelib.annotation.NotNull;
import com.benyanyi.sqlitelib.annotation.TableBean;
import com.benyanyi.sqlitelib.exception.TableException;
import com.benyanyi.sqlitelib.annotation.ColumnName;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author YanYi
 * @date 2019/3/27 10:16
 * @email ben@yanyi.red
 * @overview
 */
final class TableInject implements TableInjectImpl {

    private TableHelperImpl helperImpl;

    @Override
    public <T> TableInjectImpl init(Context context, String dbName, int version, Class<T> tClass) {
        this.helperImpl = new TableHelper(context, dbName, getTabColumnMsg(tClass), getTabName(tClass), version);
        return this;
    }

    @Override
    public SQLiteDatabase getHelperWritableDatabase() {
        return this.helperImpl.getHelperWritableDatabase();
    }

    @Override
    public boolean tableIsExist(String tableName) {
        return this.helperImpl.tabIsExist(tableName);
    }

    @Override
    public String getDBPath() {
        return this.helperImpl.getDBPath();
    }

    @Override
    public void close() {
        this.helperImpl.tableClose();
    }

    private static <T> String getTabName(Class<T> tClass) {
        TableBean annotation = tClass.getAnnotation(TableBean.class);
        if (annotation != null) {
            String value = annotation.value();
            boolean boo = !TextUtils.isEmpty(value) && !"null".equals(value.toLowerCase().trim()) && !"".equals(value.trim());
            if (boo) {
                return value;
            }
        }
        return tClass.getSimpleName();
    }

    private static <T> TableMsg getTabColumnMsg(Class<T> tClass) {
        TableMsg tableMsg = new TableMsg();
        List<FieldMsg> oList = new ArrayList<>();
        int idSize = 0;
        try {
            Field[] declaredFields = tClass.getDeclaredFields();
            for (Field field : declaredFields) {
                String change = "$change";
                boolean boo = !field.getName().equals(change) && !"serialVersionUID".equals(field.getName())
                        && !TextUtils.isEmpty(field.getName())
                        && !"null".equals(field.getName().toLowerCase().trim())
                        && !"".equals(field.getName().trim());
                if (boo) {
                    ID annotation = field.getAnnotation(ID.class);
                    NotNull notNull = field.getAnnotation(NotNull.class);
                    ColumnName columnName = field.getAnnotation(ColumnName.class);
                    NotColumn notColumn = field.getAnnotation(NotColumn.class);
                    if (notColumn != null) {
                        boolean b = notColumn.notColumn();
                        if (b) {
                            continue;
                        }
                    }
                    if (annotation != null) {
                        ++idSize;
                        boolean boo1 = annotation.increase();
                        tableMsg.setIncrease(boo1);
                        if (columnName != null) {
                            String idName = columnName.value();
                            boolean boo2 = !TextUtils.isEmpty(idName)
                                    && !"null".equals(idName.toLowerCase().trim())
                                    && !"".equals(idName.trim());
                            if (boo2) {
                                tableMsg.setId(idName);
                            }
                        } else {
                            tableMsg.setId(field.getName());
                        }
                        tableMsg.setType(field.getType().getSimpleName());
                        if (notNull != null) {
                            tableMsg.setNotNULL(notNull.notNull());
                        } else {
                            tableMsg.setNotNULL(false);
                        }

                    } else {
                        FieldMsg fieldMsg = new FieldMsg();
                        boolean boo3 = columnName != null && !TextUtils.isEmpty(columnName.value())
                                && !"".equals(columnName.value().trim())
                                && !"NULL".equals(columnName.value().trim().toUpperCase());
                        if (boo3) {
                            fieldMsg.setKey(columnName.value());
                        } else {
                            fieldMsg.setKey(field.getName());
                        }
                        fieldMsg.setType(isType(field.getType().getSimpleName()));
                        fieldMsg.setNotNULL(notNull != null);
                        oList.add(fieldMsg);
                    }
                }
            }
        } catch (Exception e) {
            throw new TableException("创建数据库失败(failed to create database):" + "\n" + e.getMessage());
        }
        if (idSize > 1) {
            throw new TableException("创建数据库失败，该表存在多个ID");
        }
        tableMsg.setList(oList);
        return tableMsg;
    }

    private static String isType(String type) {
        switch (type) {
            case "boolean":
            case "Boolean":
            case "byte":
            case "Byte":
            case "int":
            case "Integer":
            case "long":
            case "Long":
            case "short":
            case "Short":
                return "INTEGER";
            case "float":
            case "Float":
//                return "REAL";
                return "FLOAT";
            case "double":
            case "Double":
//                return "REAL";
                return "DOUBLE";
            case "char":
            case "Character":
                return "CHARACTER(20)";
            default:
                return "text";
        }
    }

}
