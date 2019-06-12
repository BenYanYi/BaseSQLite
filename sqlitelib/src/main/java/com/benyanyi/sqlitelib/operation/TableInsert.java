package com.benyanyi.sqlitelib.operation;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.benyanyi.sqlitelib.callback.TableInsertCallBack;

import java.util.List;

/**
 * @author YanYi
 * @date 2019/3/29 14:47
 * @email ben@yanyi.red
 * @overview
 */
public final class TableInsert<T> implements TableInsertCallBack<T> {
    private SQLiteDatabase database;
    private Class<T> tClass;

    private TableInsert(){}

    private TableInsert(Class<T> tClass, Builder builder) {
        this.tClass = tClass;
        this.database = builder.database;
    }

    @Override
    public long find(T t) {
        return insert(t);
    }

    @Override
    public long[] find(List<T> list) {
        long[] l = new long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            l[i] = insert(list.get(i));
        }
        return l;
    }

    private long insert(T t) {
        ContentValues contentValues = TableTool.values(t);
        if (contentValues == null) {
            return -1;
        } else {
            return this.database.insert(TableTool.getTabName(this.tClass), null, contentValues);
        }
    }

    static class Builder {
        private SQLiteDatabase database;

        Builder setDatabase(SQLiteDatabase database) {
            this.database = database;
            return this;
        }

        <T> TableInsertCallBack<T> builder(Class<T> tClass) {
            return new TableInsert(tClass, this);
        }
    }
}
