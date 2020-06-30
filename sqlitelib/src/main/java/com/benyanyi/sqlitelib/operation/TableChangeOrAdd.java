package com.benyanyi.sqlitelib.operation;

import com.benyanyi.sqlitelib.impl.TableChangeOrAddImpl;
import com.benyanyi.sqlitelib.impl.TableUpdateImpl;
import com.benyanyi.sqlitelib.impl.TableInsertImpl;
import com.benyanyi.sqlitelib.impl.TableQueryImpl;

import java.util.List;

/**
 * @author YanYi
 * @date 2019/4/3 15:27
 * @email ben@yanyi.red
 * @overview
 */
public class TableChangeOrAdd<T> implements TableChangeOrAddImpl<T> {
    private TableQueryImpl<T> tableQuery;
    private TableInsertImpl<T> tableInsert;
    private TableUpdateImpl<T> tableUpdate;

    private TableChangeOrAdd() {
    }

    private TableChangeOrAdd(Builder builder) {
        this.tableQuery = builder.tableQuery;
        this.tableInsert = builder.tableInsert;
        this.tableUpdate = builder.tableUpdate;
    }

    /**
     * 修改符合条件的第一条数据，没找到符合条件的数据时添加数据
     *
     * @param t 数据
     * @return 操作数据的行号
     */
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

    /**
     * 修改符合条件的最后一条数据，没找到符合条件的数据时添加数据
     *
     * @param t 数据
     * @return 操作数据的行号
     */
    @Override
    public long findFirst(T t) {
        T t1 = this.tableQuery.findFirst();
        if (t1 != null) {
            return this.tableUpdate.findFirst(t);
        } else {
            return this.tableInsert.find(t);
        }
    }

    /**
     * 修改符合条件的全部数据，没找到符合条件的数据时添加数据
     *
     * @param t 数据
     * @return 操作数据的行号数组
     */
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
        private TableQueryImpl<T> tableQuery;
        private TableInsertImpl<T> tableInsert;
        private TableUpdateImpl<T> tableUpdate;

        Builder setTableQueryCallBack(TableQueryImpl<T> tableQueryImpl) {
            this.tableQuery = tableQueryImpl;
            return this;
        }

        Builder setTableInsertCallBack(TableInsertImpl<T> tableInsert) {
            this.tableInsert = tableInsert;
            return this;
        }

        Builder setTableUpdateCallBack(TableUpdateImpl<T> tableUpdateImpl) {
            this.tableUpdate = tableUpdateImpl;
            return this;
        }

        TableChangeOrAddImpl<T> builder() {
            return new TableChangeOrAdd(this);
        }
    }
}
