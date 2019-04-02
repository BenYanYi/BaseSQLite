package com.mylove.sqlitelib.condition;

import com.mylove.sqlitelib.config.TableNexus;

/**
 * @author YanYi
 * @date 2019/4/1 13:58
 * @email ben@yanyi.red
 * @overview 字段需要进行的条件逻辑
 */
public class ConditionMsg {
    private String field;
    private String value;
    private TableNexus nexus = TableNexus.DETAILS;

    public ConditionMsg(String field, String value) {
        this.field = field;
        this.value = value;
    }

    public ConditionMsg(String field, String value, TableNexus nexus) {
        this.field = field;
        this.value = value;
        this.nexus = nexus;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TableNexus getNexus() {
        return nexus;
    }

    public void setNexus(TableNexus nexus) {
        this.nexus = nexus;
    }
}
