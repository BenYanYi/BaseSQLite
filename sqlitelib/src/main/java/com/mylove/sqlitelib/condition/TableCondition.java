package com.mylove.sqlitelib.condition;

import android.database.sqlite.SQLiteDatabase;

import com.mylove.sqlitelib.config.TableSort;
import com.mylove.sqlitelib.callback.ConditionCallBack;
import com.mylove.sqlitelib.operation.TableOperation;

import java.util.List;

/**
 * @author YanYi
 * @date 2019/3/29 13:55
 * @email ben@yanyi.red
 * @overview
 */
public class TableCondition implements ConditionCallBack {
    private Class<?> tClass;
    private TableSort sort = TableSort.DETAILS;
    private String field;
    private List<ConditionMsg> eqList;
    private List<ConditionMsg> notEqList;
    private List<ConditionMsg> greaterList;
    private List<ConditionMsg> lessList;
    private List<ConditionMsg> inList;

    private SQLiteDatabase database;

    private TableCondition(SQLiteDatabase database, Class<?> tClass, Builder builder) {
        this.database = database;
        this.tClass = tClass;
        this.eqList = builder.eqList;
        this.notEqList = builder.notEqList;
        this.greaterList = builder.greaterList;
        this.lessList = builder.lessList;
        this.inList = builder.inList;
    }

    @Override
    public TableCondition eq(List<ConditionMsg> list) {
        this.eqList.addAll(list);
        return this;
    }

    @Override
    public TableCondition eq(ConditionMsg conditionMsg) {
        this.eqList.add(conditionMsg);
        return this;
    }

    @Override
    public TableCondition notEq(List<ConditionMsg> list) {
        this.notEqList.addAll(list);
        return this;
    }

    @Override
    public TableCondition notEq(ConditionMsg conditionMsg) {
        this.notEqList.add(conditionMsg);
        return this;
    }

    @Override
    public TableCondition greater(List<ConditionMsg> list) {
        this.greaterList.addAll(list);
        return this;
    }

    @Override
    public TableCondition greater(ConditionMsg conditionMsg) {
        this.greaterList.add(conditionMsg);
        return this;
    }

    @Override
    public TableCondition less(List<ConditionMsg> list) {
        this.lessList.addAll(list);
        return this;
    }

    @Override
    public TableCondition less(ConditionMsg conditionMsg) {
        this.lessList.add(conditionMsg);
        return this;
    }

    @Override
    public TableCondition in(List<ConditionMsg> list) {
        this.inList.addAll(list);
        return this;
    }

    @Override
    public TableCondition in(ConditionMsg conditionMsg) {
        this.inList.add(conditionMsg);
        return this;
    }

    @Override
    public TableCondition sort(String field, TableSort sort) {
        this.field = field;
        this.sort = sort;
        return this;
    }

    @Override
    public TableOperation operation() {
        TableOperation.Builder builder = new TableOperation.Builder()
                .setConditionKey(conditionKey())
                .setConditionValue(conditionValue())
                .setField(this.field)
                .setSort(this.sort);
        return builder.builder(this.database, tClass);
    }

    private String conditionKey() {
        if (this.eqList.size() > 0 || this.notEqList.size() > 0 || this.greaterList.size() > 0 || this.lessList.size() > 0 || this.inList.size() > 0) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < this.eqList.size(); i++) {
                if (this.eqList.get(i).getNexus().isNexus()) {
                    builder.append("and ");
                } else {
                    builder.append("or ");
                }
                builder.append(this.eqList.get(i).getField()).append(" = ? ");
            }
            for (int i = 0; i < this.notEqList.size(); i++) {
                if (this.notEqList.get(i).getNexus().isNexus()) {
                    builder.append("and ");
                } else {
                    builder.append("or ");
                }
                builder.append(this.notEqList.get(i).getField()).append(" != ? ");
            }
            for (int i = 0; i < this.greaterList.size(); i++) {
                if (this.greaterList.get(i).getNexus().isNexus()) {
                    builder.append("and ");
                } else {
                    builder.append("or ");
                }
                builder.append(this.greaterList.get(i).getField()).append(" > ? ");
            }
            for (int i = 0; i < this.lessList.size(); i++) {
                if (this.lessList.get(i).getNexus().isNexus()) {
                    builder.append("and ");
                } else {
                    builder.append("or ");
                }
                builder.append(this.lessList.get(i).getField()).append(" < ? ");
            }
            for (int i = 0; i < this.inList.size(); i++) {
                if (this.inList.get(i).getNexus().isNexus()) {
                    builder.append("and ");
                } else {
                    builder.append("or ");
                }
                builder.append(this.inList.get(i).getField()).append(" like ? ");
            }
            builder = builder.delete(0, 3);
            return builder.toString();
        } else {
            return null;
        }
    }

    private String[] conditionValue() {
        if (this.eqList.size() > 0 || this.notEqList.size() > 0 || this.greaterList.size() > 0 || this.lessList.size() > 0 || this.inList.size() > 0) {
            int size = this.eqList.size() + this.notEqList.size() + this.greaterList.size() + this.lessList.size() + this.inList.size();
            String[] values = new String[size];
            for (int i = 0; i < this.eqList.size(); i++) {
                values[i] = this.eqList.get(i).getValue();
            }
            for (int i = 0; i < this.notEqList.size(); i++) {
                int index = this.eqList.size() + i;
                values[index] = notEqList.get(i).getValue();
            }
            for (int i = 0; i < this.greaterList.size(); i++) {
                int index = this.eqList.size() + this.notEqList.size() + i;
                values[index] = this.greaterList.get(i).getValue();
            }
            for (int i = 0; i < this.lessList.size(); i++) {
                int index = this.eqList.size() + this.notEqList.size() + this.greaterList.size() + i;
                values[index] = this.lessList.get(i).getValue();
            }
            for (int i = 0; i < this.inList.size(); i++) {
                int index = this.eqList.size() + this.notEqList.size() + this.greaterList.size() + this.lessList.size() + i;
                values[index] = "'%" + this.inList.get(i).getValue() + "%'";
            }
            return values;
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

        public TableCondition builder(SQLiteDatabase database, Class<?> tClass) {
            return new TableCondition(database, tClass, this);
        }
    }
}
