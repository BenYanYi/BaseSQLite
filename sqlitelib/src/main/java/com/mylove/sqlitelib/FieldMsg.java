package com.mylove.sqlitelib;

/**
 * @author YanYi
 * @date 2019/3/28 17:16
 * @email ben@yanyi.red
 * @overview
 */
class FieldMsg {
    private String key;
    private String type;
    private boolean isNotNULL = false;

    String getKey() {
        return key;
    }

    void setKey(String key) {
        this.key = key;
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
}
