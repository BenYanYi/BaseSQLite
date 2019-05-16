package com.mylove.sqlitelib.config;

/**
 * @author YanYi
 * @date 2019/4/1 13:52
 * @email ben@yanyi.red
 * @overview 条件 and 或者 or,默认为and
 */
public enum TableNexus {
    DETAILS(true), AND(true), OR(false);
    private boolean nexus;

    TableNexus(boolean nexus) {
        this.nexus = nexus;
    }

    public boolean isNexus() {
        return nexus;
    }
}
