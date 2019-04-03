package com.mylove.sqlitelib.condition;

import com.mylove.sqlitelib.config.TableNexus;

/**
 * @author YanYi
 * @date 2019/4/1 13:58
 * @email ben@yanyi.red
 * @overview 字段需要进行的条件逻辑
 */
public class ConditionMsg {
    private String field;//列字段名
    private String value;//值
    private TableNexus nexus = TableNexus.DETAILS;//条件 and 还是 or，默认and

    public ConditionMsg(String field, String value) {
        this.field = field;
        this.value = value;
    }

    public ConditionMsg(String field, String value, TableNexus nexus) {
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

    String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    TableNexus getNexus() {
        return nexus;
    }

    public void setNexus(TableNexus nexus) {
        this.nexus = nexus;
    }
}
