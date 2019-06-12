package com.benyanyi.sqlitelib.operation;

import com.benyanyi.sqlitelib.callback.TableChangeOrAddCallBack;
import com.benyanyi.sqlitelib.callback.TableUpdateCallBack;
import com.benyanyi.sqlitelib.callback.TableInsertCallBack;
import com.benyanyi.sqlitelib.callback.TableQueryCallBack;

import java.util.List;

/**
 * @author YanYi
 * @date 2019/4/3 15:27
 * @email ben@yanyi.red
 * @overview
 */
public final class TableChangeOrAdd<T> implements TableChangeOrAddCallBack<T> {
    private TableQueryCallBack<T> tableQuery;
    private TableInsertCallBack<T> tableInsert;
    private TableUpdateCallBack<T> tableUpdate;

    private TableChangeOrAdd() {
    }

    private TableChangeOrAdd(Builder builder) {
        this.tableQuery = builder.tableQuery;
        this.tableInsert = builder.tableInsert;
        this.tableUpdate = builder.tableUpdate;
    }

    @Override
    public long[] findAll(T t) {
        List<T> all = this.tableQuery.findAll();
        if (all != null && all.size() > 0) {
            int[] i = this.tableUpdate.findAll(t);
            long[] l = new long[i.length];
            for (int j = 0; j < i.length; j++) {
                l[j] = i[j];
            }
            return l;
        } else {
            return new long[]{this.tableInsert.find(t)};
        }
    }

    @Override
    public long findFirst(T t) {
        T t1 = this.tableQuery.findFirst();
        if (t1 != null) {
            return this.tableUpdate.findFirst(t);
        } else {
            return this.tableInsert.find(t);
        }
    }

    @Override
    public long findLast(T t) {
        T t1 = this.tableQuery.findLast();
        if (t1 != null) {
            return this.tableUpdate.findFirst(t);
        } else {
            return this.tableInsert.find(t);
        }
    }

    static class Builder<T> {
        private TableQueryCallBack<T> tableQuery;
        private TableInsertCallBack<T> tableInsert;
        private TableUpdateCallBack<T> tableUpdate;

        Builder setTableQueryCallBack(TableQueryCallBack<T> tableQueryCallBack) {
            this.tableQuery = tableQueryCallBack;
            return this;
        }

        Builder setTableInsertCallBack(TableInsertCallBack<T> tableInsert) {
            this.tableInsert = tableInsert;
            return this;
        }

        Builder setTableUpdateCallBack(TableUpdateCallBack<T> tableUpdateCallBack) {
            this.tableUpdate = tableUpdateCallBack;
            return this;
        }

        TableChangeOrAddCallBack<T> builder() {
            return new TableChangeOrAdd(this);
        }
    }
}
