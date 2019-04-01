package com.mylove.sqlitelib.config;

/**
 * @author YanYi
 * @date 2019/3/29 14:16
 * @email ben@yanyi.red
 * @overview
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
