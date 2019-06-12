package com.benyanyi.sqlitelib.config;

/**
 * @author YanYi
 * @date 2019/4/1 13:52
 * @email ben@yanyi.red
 * @overview 条件 and 或者 or,默认为and
 */
public enum TableNexus {
    /**
     * 默认
     */
    DETAILS(true),
    /**
     * 和
     */
    AND(true),
    /**
     * 或者
     */
    OR(false);
    private boolean nexus;

    TableNexus(boolean nexus) {
        this.nexus = nexus;
    }

    public boolean isNexus() {
        return nexus;
    }
}
