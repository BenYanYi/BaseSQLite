package com.benyanyi.sqlitelib.condition;

import com.benyanyi.sqlitelib.config.TableNexus;

/**
 * @author YanYi
 * @date 2019/4/1 13:58
 * @email ben@yanyi.red
 * @overview 字段需要进行的条件逻辑
 */
public final class ConditionMsg {
    /**
     * 列字段名
     */
    private String field;
    /**
     * 值
     */
    private Object value;
    /**
     * 条件 and 还是 or，默认and
     */
    private TableNexus nexus = TableNexus.DETAILS;

    public ConditionMsg(String field, Object value) {
        this.field = field;
        this.value = value;
    }

    public ConditionMsg(String field, Object value, TableNexus nexus) {
        this.field = field;
        this.value = value;
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
