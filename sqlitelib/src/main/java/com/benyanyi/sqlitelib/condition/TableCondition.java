package com.benyanyi.sqlitelib.condition;

import android.database.sqlite.SQLiteDatabase;

import com.benyanyi.sqlitelib.impl.OperationImpl;
import com.benyanyi.sqlitelib.config.TableSort;
import com.benyanyi.sqlitelib.impl.ConditionImpl;
import com.benyanyi.sqlitelib.operation.TableOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YanYi
 * @date 2019/3/29 13:55
 * @email ben@yanyi.red
 * @overview
 */
public class TableCondition<T> implements ConditionImpl<T> {
    private Class<T> tClass;
    private TableSort sort = TableSort.DETAILS;
    private String field;

    private SQLiteDatabase database;

    private TableCondition() {
    }

    private TableCondition(Class<T> tClass, Builder builder) {
        this.tClass = tClass;
        this.database = builder.database;
    }

    /**
     * 排序方式
     *
     * @param field 排序依据的字段
     * @param sort
     * @return
     */
    @Override
    public ConditionImpl<T> sort(String field, TableSort sort) {
        this.field = field;
        this.sort = sort;
        return this;
    }

    /**
     * 增删查改处理
     *
     * @param conditionMsg 判断条件
     */
    @Override
    public OperationImpl<T> operation(ConditionMsg conditionMsg) {
        List<ConditionMsg> list = new ArrayList<>();
        list.add(conditionMsg);
        return new TableOperation.Builder()
                .setConditionKey(conditionKey(list))
                .setConditionValue(conditionValue(list))
                .setField(this.field)
                .setSort(this.sort)
                .builder(this.database, tClass);
    }

    /**
     * 增删查改处理
     *
     * @param list 多个判断条件集合
     */
    @Override
    public OperationImpl<T> operation(List<ConditionMsg> list) {
        return new TableOperation.Builder()
                .setConditionKey(conditionKey(list))
                .setConditionValue(conditionValue(list))
                .setField(this.field)
                .setSort(this.sort)
                .builder(this.database, tClass);
    }

    /**
     * 增删查改处理
     */
    @Override
    public OperationImpl<T> operation() {
        return new TableOperation.Builder()
                .setConditionKey(conditionKey(new ArrayList<ConditionMsg>()))
                .setConditionValue(conditionValue(new ArrayList<ConditionMsg>()))
                .setField(this.field)
                .setSort(this.sort)
                .builder(this.database, tClass);
    }

    /**
     * 条件键处理
     *
     * @return
     */
    private String conditionKey(List<ConditionMsg> list) {
        StringBuilder builder = new StringBuilder();
        for (ConditionMsg conditionMsg : list) {
            if (conditionMsg.getNexus().isNexus()) {
                builder.append("and ");
            } else {
                builder.append("or ");
            }
            switch (conditionMsg.getCondition()) {
                case NOT_EQ:
                    builder.append(conditionMsg.getField()).append(" != ? ");
                    break;
                case GREATER:
                    builder.append(conditionMsg.getField()).append(" > ? ");
                    break;
                case LESS:
                    builder.append(conditionMsg.getField()).append(" < ? ");
                    break;
                case LIKE:
                    builder.append(conditionMsg.getField()).append(" like ? ");
                    break;
                case DETAILS:
                case EQ:
                default:
                    builder.append(conditionMsg.getField()).append(" = ? ");
                    break;
            }
        }
        if (builder.length() > 3) {
            builder = builder.delete(0, 3);
        }
        return builder.toString();
    }

    /**
     * 条件值处理
     *
     * @return
     */
    private String[] conditionValue(List<ConditionMsg> list) {
        List<String> oList = new ArrayList<>();
        for (ConditionMsg conditionMsg : list) {
            switch (conditionMsg.getCondition()) {
                case LIKE:
                    oList.add("'%" + conditionMsg.getValue() + "%'");
                    break;
                case DETAILS:
                case EQ:
                case NOT_EQ:
                case GREATER:
                case LESS:
                default:
                    oList.add(conditionMsg.getValue().toString());
                    break;
            }
        }
        if (!oList.isEmpty()) {
            return oList.toArray(new String[0]);
        } else {
            return null;
        }
    }

    public static class Builder {

        private SQLiteDatabase database;

        public Builder setDatabase(SQLiteDatabase database) {
            this.database = database;
            return this;
        }

        public <T> ConditionImpl<T> builder(Class<T> tClass) {
            return new TableCondition(tClass, this);
        }
    }
}
