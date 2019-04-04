package com.mylove.sqlitelib.operation;

import java.util.List;

/**
 * @author YanYi
 * @date 2019/4/3 15:27
 * @email ben@yanyi.red
 * @overview
 */
public class TableChangeOrAdd {
    private TableQuery tableQuery;
    private TableInsert tableInsert;
    private TableUpdate tableUpdate;

    private TableChangeOrAdd(Builder builder) {
        this.tableQuery = builder.tableQuery;
        this.tableInsert = builder.tableInsert;
        this.tableUpdate = builder.tableUpdate;
    }

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

    public <T> long findFirst(T t) {
        T t1 = this.tableQuery.findFirst();
        if (t1 != null) {
            return this.tableUpdate.findFirst(t);
        } else {
            return this.tableInsert.find(t);
        }
    }

    public <T> long findLast(T t) {
        T t1 = this.tableQuery.findLast();
        if (t1 != null) {
            return this.tableUpdate.findFirst(t);
        } else {
            return this.tableInsert.find(t);
        }
    }

    static class Builder {
        private TableQuery tableQuery;
        private TableInsert tableInsert;
        private TableUpdate tableUpdate;

        Builder setTableQuery(TableQuery tableQuery) {
            this.tableQuery = tableQuery;
            return this;
        }

        Builder setTableInsert(TableInsert tableInsert) {
            this.tableInsert = tableInsert;
            return this;
        }

        Builder setTableUpdate(TableUpdate tableUpdate) {
            this.tableUpdate = tableUpdate;
            return this;
        }

        TableChangeOrAdd builder() {
            return new TableChangeOrAdd(this);
        }
    }
}
