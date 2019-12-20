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
public final class TableCondition<T> implements ConditionImpl<T> {
    private Class<T> tClass;
    private TableSort sort = TableSort.DETAILS;
    private String field;
    private List<ConditionMsg> eqList;
    private List<ConditionMsg> notEqList;
    private List<ConditionMsg> greaterList;
    private List<ConditionMsg> lessList;
    private List<ConditionMsg> inList;

    private SQLiteDatabase database;

    private TableCondition() {
    }

    private TableCondition(SQLiteDatabase database, Class<T> tClass, Builder builder) {
        this.database = database;
        this.tClass = tClass;
        this.eqList = builder.eqList;
        this.notEqList = builder.notEqList;
        this.greaterList = builder.greaterList;
        this.lessList = builder.lessList;
        this.inList = builder.inList;
    }

    /**
     * 相等
     *
     * @param list
     * @return
     */
    @Override
    public ConditionImpl<T> eq(List<ConditionMsg> list) {
        this.eqList.addAll(list);
        return this;
    }

    /**
     * 相等
     *
     * @param conditionMsg
     * @return
     */
    @Override
    public ConditionImpl<T> eq(ConditionMsg conditionMsg) {
        this.eqList.add(conditionMsg);
        return this;
    }

    /**
     * 不等
     *
     * @param list
     * @return
     */
    @Override
    public ConditionImpl<T> notEq(List<ConditionMsg> list) {
        this.notEqList.addAll(list);
        return this;
    }

    /**
     * 不等
     *
     * @param conditionMsg
     * @return
     */
    @Override
    public ConditionImpl<T> notEq(ConditionMsg conditionMsg) {
        this.notEqList.add(conditionMsg);
        return this;
    }

    /**
     * 大于
     *
     * @param list
     * @return
     */
    @Override
    public ConditionImpl<T> greater(List<ConditionMsg> list) {
        this.greaterList.addAll(list);
        return this;
    }

    /**
     * 大于
     *
     * @param conditionMsg
     * @return
     */
    @Override
    public ConditionImpl<T> greater(ConditionMsg conditionMsg) {
        this.greaterList.add(conditionMsg);
        return this;
    }

    /**
     * 小于
     *
     * @param list
     * @return
     */
    @Override
    public ConditionImpl<T> less(List<ConditionMsg> list) {
        this.lessList.addAll(list);
        return this;
    }

    /**
     * 小于
     *
     * @param conditionMsg
     * @return
     */
    @Override
    public ConditionImpl<T> less(ConditionMsg conditionMsg) {
        this.lessList.add(conditionMsg);
        return this;
    }

    /**
     * 包含（模糊查询）
     *
     * @param list
     * @return
     */
    @Override
    public ConditionImpl<T> in(List<ConditionMsg> list) {
        this.inList.addAll(list);
        return this;
    }

    /**
     * 包含（模糊查询）
     *
     * @param conditionMsg
     * @return
     */
    @Override
    public ConditionImpl<T> in(ConditionMsg conditionMsg) {
        this.inList.add(conditionMsg);
        return this;
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
     * @return
     */
    @Override
    public OperationImpl<T> operation() {
        return new TableOperation.Builder()
                .setConditionKey(conditionKey())
                .setConditionValue(conditionValue())
                .setField(this.field)
                .setSort(this.sort)
                .builder(this.database, tClass);
    }

    /**
     * 条件键处理
     *
     * @return
     */
    private String conditionKey() {
        StringBuilder builder = new StringBuilder();
        if (this.eqList != null && !this.eqList.isEmpty()) {
            for (int i = 0; i < this.eqList.size(); i++) {
                if (this.eqList.get(i).getNexus().isNexus()) {
                    builder.append("and ");
                } else {
                    builder.append("or ");
                }
                builder.append(this.eqList.get(i).getField()).append(" = ? ");
            }
        }
        if (this.notEqList != null && !this.notEqList.isEmpty()) {
            for (int i = 0; i < this.notEqList.size(); i++) {
                if (this.notEqList.get(i).getNexus().isNexus()) {
                    builder.append("and ");
                } else {
                    builder.append("or ");
                }
                builder.append(this.notEqList.get(i).getField()).append(" != ? ");
            }
        }
        if (this.greaterList != null && !this.greaterList.isEmpty()) {
            for (int i = 0; i < this.greaterList.size(); i++) {
                if (this.greaterList.get(i).getNexus().isNexus()) {
                    builder.append("and ");
                } else {
                    builder.append("or ");
                }
                builder.append(this.greaterList.get(i).getField()).append(" > ? ");
            }
        }
        if (this.lessList != null && !this.lessList.isEmpty()) {
            for (int i = 0; i < this.lessList.size(); i++) {
                if (this.lessList.get(i).getNexus().isNexus()) {
                    builder.append("and ");
                } else {
                    builder.append("or ");
                }
                builder.append(this.lessList.get(i).getField()).append(" < ? ");
            }
        }
        if (this.inList != null && !this.inList.isEmpty()) {
            for (int i = 0; i < this.inList.size(); i++) {
                if (this.inList.get(i).getNexus().isNexus()) {
                    builder.append("and ");
                } else {
                    builder.append("or ");
                }
                builder.append(this.inList.get(i).getField()).append(" like ? ");
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
    private String[] conditionValue() {
        List<String> oList = new ArrayList<>();
        if (this.eqList != null && !this.eqList.isEmpty()) {
            for (int i = 0; i < this.eqList.size(); i++) {
                oList.add(this.eqList.get(i).getValue().toString());
            }
        }
        if (this.notEqList != null && !this.notEqList.isEmpty()) {
            for (int i = 0; i < this.notEqList.size(); i++) {
                oList.add(this.notEqList.get(i).getValue().toString());
            }
        }
        if (this.greaterList != null && !this.greaterList.isEmpty()) {
            for (int i = 0; i < this.greaterList.size(); i++) {
                oList.add(this.greaterList.get(i).getValue().toString());
            }
        }
        if (this.lessList != null && !this.lessList.isEmpty()) {
            for (int i = 0; i < this.lessList.size(); i++) {
                oList.add(this.lessList.get(i).getValue().toString());
            }
        }
        if (this.inList != null && !this.inList.isEmpty()) {
            for (int i = 0; i < this.inList.size(); i++) {
                oList.add("'%" + this.inList.get(i).getValue() + "%'");
            }
        }
        if (!oList.isEmpty()) {
            return oList.toArray(new String[0]);
        } else {
            return null;
        }
    }

    public static class Builder {
        private List<ConditionMsg> eqList;
        private List<ConditionMsg> notEqList;
        private List<ConditionMsg> greaterList;
        private List<ConditionMsg> lessList;
        private List<ConditionMsg> inList;

        public Builder setEqList(List<ConditionMsg> eqList) {
            this.eqList = eqList;
            return this;
        }

        public Builder setNotEqList(List<ConditionMsg> notEqList) {
            this.notEqList = notEqList;
            return this;
        }

        public Builder setGreaterList(List<ConditionMsg> greaterList) {
            this.greaterList = greaterList;
            return this;
        }

        public Builder setLessList(List<ConditionMsg> lessList) {
            this.lessList = lessList;
            return this;
        }

        public Builder setInList(List<ConditionMsg> inList) {
            this.inList = inList;
            return this;
        }

        public <T> ConditionImpl<T> builder(SQLiteDatabase database, Class<T> tClass) {
            return new TableCondition(database, tClass, this);
        }
    }
}
