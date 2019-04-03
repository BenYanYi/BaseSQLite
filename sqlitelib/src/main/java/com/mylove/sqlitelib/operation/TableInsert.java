package com.mylove.sqlitelib.operation;

import android.database.sqlite.SQLiteDatabase;

import com.mylove.sqlitelib.exception.TableException;

import java.util.List;

/**
 * @author YanYi
 * @date 2019/3/29 14:47
 * @email ben@yanyi.red
 * @overview
 */
public class TableInsert {
    private SQLiteDatabase database;
    private Class<?> tClass;

    private TableInsert(Class<?> tClass, Builder builder) {
        this.tClass = tClass;
        this.database = builder.database;
    }

    public <T> long find(T t) {
        if (this.tClass != t.getClass()) {
            throw new TableException("添加的数据与表不符");
        }
        return insert(t);
    }

    public <T> long[] find(List<T> list) {
        if (list == null || list.size() <= 0) {
            throw new TableException("添加的数据列表不能为空");
        }
        if (this.tClass != list.get(0).getClass()) {
            throw new TableException("添加的数据与表不符");
        }
        long[] l = new long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            l[i] = insert(list.get(i));
        }
        return l;
    }

    private <T> long insert(T t) {
        if (TableTool.values(t) == null) {
            return -1;
        } else {
            return this.database.insert(TableTool.getTabName(this.tClass), null, TableTool.values(t));
        }
    }

    static class Builder {
        private SQLiteDatabase database;

        Builder setDatabase(SQLiteDatabase database) {
            this.database = database;
            return this;
        }

        <T> TableInsert builder(Class<T> tClass) {
            return new TableInsert(tClass, this);
        }
    }
}
