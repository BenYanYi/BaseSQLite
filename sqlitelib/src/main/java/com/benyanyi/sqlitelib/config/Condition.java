package com.benyanyi.sqlitelib.config;

/**
 * @author YanYi
 * @date 2020/06/04 10:28
 * @email ben@yanyi.red
 * @overview
 */
public enum Condition {

    DETAILS(0),//默认为eq相等
    EQ(1),//相等
    NOT_EQ(2),//不等
    GREATER(3),//大于
    LESS(4),//小于
    LIKE(5);//包含

    private int condition;

    Condition(int condition) {
        this.condition = condition;
    }

    public int getCondition() {
        return condition;
    }
}
