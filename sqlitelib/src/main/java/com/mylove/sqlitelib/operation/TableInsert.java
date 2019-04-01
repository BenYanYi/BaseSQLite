package com.mylove.sqlitelib.operation;

import android.database.sqlite.SQLiteDatabase;

import com.mylove.sqlitelib.annotation.TableBean;
import com.mylove.sqlitelib.db.TableCallBack;
import com.mylove.sqlitelib.db.TableObject;

/**
 * @author YanYi
 * @date 2019/3/29 14:47
 * @email ben@yanyi.red
 * @overview
 */
class TableInsert {
    private <T> boolean isTabBean(T t) {
        TableBean annotation = t.getClass().getAnnotation(TableBean.class);
        if (annotation != null) {
            return true;
        } else if (t instanceof TableObject) {
            return true;
        } else if (TableCallBack.class.isAssignableFrom(t.getClass())) {
            return true;
        } else {
            return false;
        }
    }

    static <T> long insert(T t, SQLiteDatabase database) {
        return database.insert(t.getClass().getSimpleName(), null, TableTool.values(t));
    }

}
