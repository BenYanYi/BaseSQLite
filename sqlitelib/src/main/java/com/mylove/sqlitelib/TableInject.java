package com.mylove.sqlitelib;

import android.content.Context;

import com.mylove.sqlitelib.annotation.ID;
import com.mylove.sqlitelib.annotation.NotNull;
import com.mylove.sqlitelib.exception.TabException;

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
        return new TableHelper(context, dbName, getTabMsg(tClass), getTabName(tClass), version);
    }

    private static <T> String getTabName(Class<T> tClass) {
        return tClass.getSimpleName();
    }

    private static <T> TabMsg getTabMsg(Class<T> tClass) {
        TabMsg tabMsg = new TabMsg();
        List<FieldMsg> oList = new ArrayList<>();
        try {
            Field[] declaredFields = tClass.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                ID annotation = field.getAnnotation(ID.class);
                NotNull notNull = field.getAnnotation(NotNull.class);
                if (annotation != null) {
                    boolean boo = annotation.increase();
                    tabMsg.setIncrease(boo);
                    tabMsg.setId(field.getName());
                    tabMsg.setType(field.getType().getSimpleName());
                } else {
                    FieldMsg fieldMsg = new FieldMsg();
                    fieldMsg.setKey(field.getName());
                    fieldMsg.setType(isType(field.getType().getSimpleName()));
                    fieldMsg.setNotNULL(notNull != null);
                    oList.add(fieldMsg);
                }
            }
        } catch (Exception e) {
            throw new TabException("创建数据库失败:" + "\n" + e.getMessage());
        }
        return tabMsg;
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
