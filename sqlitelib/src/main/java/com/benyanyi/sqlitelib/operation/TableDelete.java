package com.benyanyi.sqlitelib.operation;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.benyanyi.sqlitelib.callback.TableDeleteCallBack;
import com.benyanyi.sqlitelib.callback.TableQueryCallBack;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author YanYi
 * @date 2019/4/1 12:33
 * @email ben@yanyi.red
 * @overview
 */
public final class TableDelete<T> implements TableDeleteCallBack<T> {
    private SQLiteDatabase database;
    private TableQueryCallBack tableQuery;
    private Class<T> tClass;
    private String conditionKey;
    private String[] conditionValue;

    private TableDelete() {
    }

    private TableDelete(SQLiteDatabase database, Class<T> tClass, Builder builder) {
        this.database = database;
        this.tClass = tClass;
        this.tableQuery = builder.tableQuery;
        this.conditionKey = builder.conditionKey;
        this.conditionValue = builder.conditionValue;
    }

    /**
     * 删除第一条数据
     *
     * @return
     */
    @Override
    public int findFirst() {
        Object first = this.tableQuery.findFirst();
        if (null != first) {
            return delete(first);
        }
        return -1;
    }

    /**
     * 删除最后一条数据
     *
     * @return
     */
    @Override
    public int findLast() {
        Object last = this.tableQuery.findLast();
        if (null != last) {
            return delete(last);
        }
        return -1;
    }

    /**
     * 删除全部数据
     *
     * @return
     */
    @Override
    public int findAll() {
        return this.database.delete(TableTool.getTabName(this.tClass), this.conditionKey, this.conditionValue);
    }

    /**
     * 删除逻辑处理
     *
     * @param obj
     * @return
     */
    private int delete(Object obj) {
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            StringBuilder builder = new StringBuilder();
            List<String> list = new ArrayList<>();
            for (Field field : fields) {
                String change = "$change";
                String uuid = "serialVersionUID";
                boolean boo = !field.getName().equals(change) && !field.getName().equals(uuid) &&
                        !TextUtils.isEmpty(field.getName()) && !"null".equals(field.getName()) &&
                        field.getName().trim().length() != 0;
                if (boo) {
                    field.setAccessible(true);
                    builder.append(field.getName()).append("= ? ").append(" and ");
                    Object invoke = field.get(obj);
                    list.add(invoke + "");
                }
            }
            builder = builder.delete(builder.length() - 4, builder.length());
            String[] value = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                value[i] = list.get(i);
            }
            return this.database.delete(TableTool.getTabName(this.tClass), builder.toString(), value);
        } catch (Exception e) {
            return -1;
        }
    }

    static class Builder {
        private TableQueryCallBack tableQuery;
        private String conditionKey;
        private String[] conditionValue;

        Builder setTableQueryCallBack(TableQueryCallBack tableQueryCallBack) {
            this.tableQuery = tableQueryCallBack;
            return this;
        }

        Builder setConditionKey(String conditionKey) {
            this.conditionKey = conditionKey;
            return this;
        }

        Builder setConditionValue(String[] conditionValue) {
            this.conditionValue = conditionValue;
            return this;
        }

        <T> TableDeleteCallBack<T> builder(SQLiteDatabase database, Class<T> tClass) {
            return new TableDelete(database, tClass, this);
        }
    }
}
