package com.mylove.sqlitelib.operation;

import android.database.sqlite.SQLiteDatabase;

import com.mylove.loglib.JLog;
import com.mylove.sqlitelib.callback.TableInsertCallBack;

import java.util.List;

/**
 * @author YanYi
 * @date 2019/3/29 14:47
 * @email ben@yanyi.red
 * @overview
 */
public class TableInsert<T> implements TableInsertCallBack<T> {
    private SQLiteDatabase database;
    private Class<T> tClass;

    private TableInsert(Class<T> tClass, Builder builder) {
        this.tClass = tClass;
        this.database = builder.database;
    }

    public long find(T t) {
//        if (this.tClass != t.getClass()) {
//            throw new TableException("添加的数据与表不符");
//        }
        return insert(t);
    }

    public long[] find(List<T> list) {
//        if (list == null || list.size() <= 0) {
//            throw new TableException("添加的数据列表不能为空");
//        }
//        if (this.tClass != list.get(0).getClass()) {
//            throw new TableException("添加的数据与表不符");
//        }
        JLog.d(list);
        long[] l = new long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            l[i] = insert(list.get(i));
        }
        JLog.d();
        return l;
    }

    private long insert(T t) {
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

        <T> TableInsertCallBack<T> builder(Class<T> tClass) {
            return new TableInsert(tClass, this);
        }
    }
}
