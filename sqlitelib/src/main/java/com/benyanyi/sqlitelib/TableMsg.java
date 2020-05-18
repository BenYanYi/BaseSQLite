package com.benyanyi.sqlitelib;

import java.util.List;

/**
 * @author YanYi
 * @date 2019/3/28 14:53
 * @email ben@yanyi.red
 * @overview
 */
final class TableMsg {
    private String id;
    private String type;
    private boolean isNotNULL = false;
    private boolean increase = false;
    private String tableName;

    private List<FieldMsg> list;

    String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    boolean isNotNULL() {
        return isNotNULL;
    }

    void setNotNULL(boolean notNULL) {
        isNotNULL = notNULL;
    }

    boolean isIncrease() {
        return increase;
    }

    void setIncrease(boolean increase) {
        this.increase = increase;
    }

    List<FieldMsg> getList() {
        return list;
    }

    void setList(List<FieldMsg> list) {
        this.list = list;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
