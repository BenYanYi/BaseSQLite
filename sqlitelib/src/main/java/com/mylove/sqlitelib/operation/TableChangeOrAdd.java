package com.mylove.sqlitelib.operation;

import com.mylove.sqlitelib.callback.TableChangeOrAddCallBack;
import com.mylove.sqlitelib.callback.TableInsertCallBack;
import com.mylove.sqlitelib.callback.TableQueryCallBack;
import com.mylove.sqlitelib.callback.TableUpdateCallBack;

import java.util.List;

/**
 * @author YanYi
 * @date 2019/4/3 15:27
 * @email ben@yanyi.red
 * @overview
 */
public class TableChangeOrAdd implements TableChangeOrAddCallBack {
    private TableQueryCallBack tableQuery;
    private TableInsertCallBack tableInsert;
    private TableUpdateCallBack tableUpdate;

    private TableChangeOrAdd(Builder builder) {
        this.tableQuery = builder.tableQuery;
        this.tableInsert = builder.tableInsert;
        this.tableUpdate = builder.tableUpdate;
    }

    @Override
    public <T> long[] findAll(T t) {
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
    public <T> long findFirst(T t) {
        T t1 = this.tableQuery.findFirst();
        if (t1 != null) {
            return this.tableUpdate.findFirst(t);
        } else {
            return this.tableInsert.find(t);
        }
    }

    @Override
    public <T> long findLast(T t) {
        T t1 = this.tableQuery.findLast();
        if (t1 != null) {
            return this.tableUpdate.findFirst(t);
        } else {
            return this.tableInsert.find(t);
        }
    }

    static class Builder {
        private TableQueryCallBack tableQuery;
        private TableInsertCallBack tableInsert;
        private TableUpdateCallBack tableUpdate;

        Builder setTableQueryCallBack(TableQueryCallBack tableQueryCallBack) {
            this.tableQuery = tableQueryCallBack;
            return this;
        }

        Builder setTableInsertCallBack(TableInsertCallBack tableInsert) {
            this.tableInsert = tableInsert;
            return this;
        }

        Builder setTableUpdateCallBack(TableUpdateCallBack tableUpdateCallBack) {
            this.tableUpdate = tableUpdateCallBack;
            return this;
        }

        TableChangeOrAddCallBack builder() {
            return new TableChangeOrAdd(this);
        }
    }
}
