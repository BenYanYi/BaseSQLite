package com.mylove.sqlitelib.config;

/**
 * @author YanYi
 * @date 2019/3/29 14:16
 * @email ben@yanyi.red
 * @overview 排序方式 ASCENDING：正序  DESCENDING：倒序
 */
public enum TableSort {
    DETAILS(" "), ASCENDING(" asc"), DESCENDING(" desc");
    private String sort;

    TableSort(String sort) {
        this.sort = sort;
    }

    public String getSort() {
        return sort;
    }
}
