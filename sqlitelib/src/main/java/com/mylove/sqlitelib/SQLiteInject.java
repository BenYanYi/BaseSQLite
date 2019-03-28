package com.mylove.sqlitelib;

import android.content.Context;

import com.mylove.sqlitelib.annotation.ID;
import com.mylove.sqlitelib.annotation.IsDateTime;
import com.mylove.sqlitelib.annotation.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author YanYi
 * @date 2019/3/27 10:16
 * @email ben@yanyi.red
 * @overview
 */
class SQLiteInject {

    static <T> SQLiteHelper init(Context context, String dbName, int version, T t) {
        return new SQLiteHelper(context, dbName, getTabMsg(t).getId(), getTabMsg(t).getType(), getTabMsg(t).isIncrease(), null, getTabName(t), version);
    }

    private static <T> String getTabName(T t) {
        return t.getClass().getSimpleName();
    }

    private static <T> TabMsg getTabMsg(T t) {
        TabMsg tabMsg = new TabMsg();
        List<FieldMsg> oList = new ArrayList<>();
        try {
            Field[] declaredFields = t.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                ID annotation = field.getAnnotation(ID.class);
                NotNull notNull = field.getAnnotation(NotNull.class);
                IsDateTime isDateTime = field.getAnnotation(IsDateTime.class);
                if (annotation != null) {
                    boolean boo = annotation.increase();
                    tabMsg.setIncrease(boo);
                    tabMsg.setId(field.getName());
                    tabMsg.setType(field.getType().getSimpleName());
                } else {
                    FieldMsg fieldMsg = new FieldMsg();
                    fieldMsg.setKey(field.getName());
                    if (isDateTime != null) {

                    } else {

                    }
                    fieldMsg.setNotNULL(notNull != null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tabMsg;
    }

    private static String isType(String type) {
        if (type.equals("boolean") || type.equals("Boolean")) {
        } else if (type.equals("byte")) {
        } else if (type.equals("int")) {
        } else if (type.equals("long") || type.equals("Long")) {

        }
        return "";
    }
}
