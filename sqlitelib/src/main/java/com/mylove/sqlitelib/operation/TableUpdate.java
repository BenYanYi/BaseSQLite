package com.mylove.sqlitelib.operation;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.mylove.sqlitelib.annotation.ColumnName;
import com.mylove.sqlitelib.annotation.NotColumn;
import com.mylove.sqlitelib.annotation.NotNull;
import com.mylove.sqlitelib.callback.TableQueryCallBack;
import com.mylove.sqlitelib.callback.TableUpdateCallBack;
import com.mylove.sqlitelib.exception.TableException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author YanYi
 * @date 2019/4/1 15:42
 * @email ben@yanyi.red
 * @overview
 */
public final class TableUpdate<T> implements TableUpdateCallBack<T> {
    private SQLiteDatabase database;
    private Class<T> tClass;
    private TableQueryCallBack<T> tableQuery;

    private TableUpdate() {
    }

    private TableUpdate(SQLiteDatabase database, Class<T> tClass, Builder builder) {
        this.database = database;
        this.tClass = tClass;
        this.tableQuery = builder.tableQuery;
    }


    public int findFirst(T t) {
        T first = this.tableQuery.findFirst();
        return update(t, first);
    }

    public int findLast(T t) {
        T last = this.tableQuery.findLast();
        return update(t, last);
    }

    public int[] findAll(T t) {
        if (this.tClass != t.getClass()) {
            throw new TableException("修改的数据与表结构不符");
        }
        List<T> all = this.tableQuery.findAll();
        int[] l = new int[all.size()];
        for (int i = 0; i < all.size(); i++) {
            l[i] = update(t, all.get(i));
        }
        return l;
    }

    private int update(T t, T oldData) {
        Field[] fields = oldData.getClass().getDeclaredFields();
        StringBuilder builder = new StringBuilder();
        List<String> list = new ArrayList<>();
        for (Field field : fields) {
            NotColumn notColumn = field.getAnnotation(NotColumn.class);
            if (notColumn != null) {
                boolean b = notColumn.notColumn();
                if (b) {
                    continue;
                }
            }
            if (!TextUtils.isEmpty(field.getName()) && !"null".equals(field.getName().toLowerCase().trim())
                    && field.getName().trim().length() != 0 && !field.getName().trim().equals("$change")
                    && !field.getName().trim().equals("serialVersionUID")) {
                ColumnName columnName = field.getAnnotation(ColumnName.class);
                String columnNameStr = field.getName();
                if (columnName != null) {
                    String value = columnName.value();
                    if (!TextUtils.isEmpty(value) && !value.toLowerCase().trim().equals("null")
                            && value.trim().length() > 0) {
                        columnNameStr = value;
                    }
                }
                try {
//                    PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), this.tClass);
//                    Method method = descriptor.getReadMethod();//获得读方法
                    field.setAccessible(true);
                    builder.append(columnNameStr);
                    builder.append("= ? ").append(" and ");
//                    Object invoke = method.invoke(oldData);
                    Object invoke = field.get(oldData);
                    NotNull notNull = field.getAnnotation(NotNull.class);
                    if (notNull != null) {
                        boolean b = notNull.notNull();
                        if (b) {
                            if (invoke == null) {
                                throw new TableException(columnNameStr + "的值不能为null");
                            } else {
                                String str = (String) invoke;
                                if (TextUtils.isEmpty(str) || str.toLowerCase().trim().equals("null") || str.trim().length() <= 0) {
                                    throw new TableException(columnNameStr + "的值不能为null");
                                }
                            }
                        }
                    }
                    list.add(TableTool.where(field, invoke));
                } catch (Exception e) {
                    String msgMethod;
                    if (e.getMessage().contains("set")) {
                        msgMethod = "set";
                    } else if (e.getMessage().contains("get")) {
                        msgMethod = "get";
                    } else {
                        msgMethod = "set或get";
                    }
                    String errorMsg = TableTool.getTabName(t.getClass()) + "表中" + columnNameStr + "列对应的"
                            + t.getClass().getSimpleName() + "类中" + field.getName() + "变量缺少"
                            + msgMethod + "方法";
                    throw new TableException(errorMsg);
                }
            }
        }
        builder = builder.delete(builder.length() - 4, builder.length());
        String[] value = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            value[i] = list.get(i);
        }
        return this.database.update(TableTool.getTabName(this.tClass), TableTool.values(t), builder.toString(), value);

    }

    static class Builder<T> {
        private TableQueryCallBack<T> tableQuery;

        Builder setTableQueryCallBack(TableQueryCallBack<T> tableQueryCallBack) {
            this.tableQuery = tableQueryCallBack;
            return this;
        }

        TableUpdateCallBack<T> builder(SQLiteDatabase database, Class<T> tClass) {
            return new TableUpdate(database, tClass, this);
        }
    }
}
