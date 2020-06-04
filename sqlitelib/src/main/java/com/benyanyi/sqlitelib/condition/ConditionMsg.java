package com.benyanyi.sqlitelib.condition;

import com.benyanyi.sqlitelib.config.Condition;
import com.benyanyi.sqlitelib.config.TableNexus;

/**
 * @author YanYi
 * @date 2019/4/1 13:58
 * @email ben@yanyi.red
 * @overview 字段需要进行的条件逻辑
 */
public class ConditionMsg {
    /**
     * 列字段名
     */
    private String field;
    /**
     * 值
     */
    private Object value;

    /**
     * 判断条件
     */
    private Condition condition = Condition.DETAILS;

    /**
     * 多条语句条件 and 还是 or，默认and
     */
    private TableNexus nexus = TableNexus.DETAILS;

    public ConditionMsg(String field, Object value) {
        this.field = field;
        this.value = value;
    }

    public ConditionMsg(String field, Object value, Condition condition) {
        this.field = field;
        this.value = value;
        this.condition = condition;
    }

    public ConditionMsg(String field, Object value, TableNexus nexus) {
        this.field = field;
        this.value = value;
        this.nexus = nexus;
    }

    public ConditionMsg(String field, Object value, Condition condition, TableNexus nexus) {
        this.field = field;
        this.value = value;
        this.condition = condition;
        this.nexus = nexus;
    }

    String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    TableNexus getNexus() {
        return nexus;
    }

    public void setNexus(TableNexus nexus) {
        this.nexus = nexus;
    }

    @Override
    public String toString() {
        return "ConditionMsg{" +
                "field='" + field + '\'' +
                ", value='" + value + '\'' +
                ", nexus=" + nexus +
                '}';
    }
}
