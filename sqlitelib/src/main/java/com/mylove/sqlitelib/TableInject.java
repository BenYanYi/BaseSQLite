package com.mylove.sqlitelib;

import android.content.Context;
import android.text.TextUtils;

import com.mylove.sqlitelib.annotation.ColumnName;
import com.mylove.sqlitelib.annotation.ID;
import com.mylove.sqlitelib.annotation.NotNull;
import com.mylove.sqlitelib.annotation.TableBean;
import com.mylove.sqlitelib.exception.TableException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author YanYi
 * @date 2019/3/27 10:16
 * @email ben@yanyi.red
 * @overview
 */
class TableInject {

    static <T> TableHelper init(Context context, String dbName, int version, Class<T> tClass) {
        return new TableHelper(context, dbName, getTabColumnMsg(tClass), getTabName(tClass), version);
    }

    private static <T> String getTabName(Class<T> tClass) {
        TableBean annotation = tClass.getAnnotation(TableBean.class);
        if (annotation != null) {
            String value = annotation.value();
            if (!TextUtils.isEmpty(value) && !"null".equals(value.toLowerCase().trim()) && !value.trim().equals("")) {
                return value;
            }
        }
        return tClass.getSimpleName();
    }

    private static <T> TableMsg getTabColumnMsg(Class<T> tClass) {
        TableMsg tableMsg = new TableMsg();
        List<FieldMsg> oList = new ArrayList<>();
        try {
            Field[] declaredFields = tClass.getDeclaredFields();
            for (Field field : declaredFields) {
                if (!field.getName().equals("$change") && !field.getName().equals("serialVersionUID")
                        && !TextUtils.isEmpty(field.getName())
                        && !"null".equals(field.getName().toLowerCase().trim())
                        && field.getName().trim().equals("")) {
                    ID annotation = field.getAnnotation(ID.class);
                    NotNull notNull = field.getAnnotation(NotNull.class);
                    ColumnName columnName = field.getAnnotation(ColumnName.class);
                    if (annotation != null) {
                        boolean boo = annotation.increase();
                        tableMsg.setIncrease(boo);
                        String idName = annotation.idName();
                        if (!TextUtils.isEmpty(idName)
                                && !idName.toLowerCase().trim().equals("null")
                                && !idName.trim().equals("")) {
                            tableMsg.setId(idName);
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
                        if (columnName != null && !TextUtils.isEmpty(columnName.value())
                                && !columnName.value().trim().equals("")
                                && !columnName.value().trim().toUpperCase().equals("NULL")) {
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
        tableMsg.setList(oList);
        return tableMsg;
    }

    private static String isType(String type) {
        switch (type) {
            case "boolean":
            case "Boolean":
                return "INTEGER";
            case "byte":
            case "Byte":
                return "INTEGER";
            case "int":
            case "Integer":
                return "INTEGER";
            case "long":
            case "Long":
                return "INTEGER";
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
