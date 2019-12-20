package com.benyanyi.sqlitelib.impl;

/**
 * @author YanYi
 * @date 2019/5/17 14:01
 * @email ben@yanyi.red
 * @overview
 */
public interface TableDaoImpl {

    /**
     * 返回表操作
     *
     * @param tClass 表类
     * @param <T>
     * @return
     */
    <T> TableSessionImpl<T> getSession(Class<T> tClass);
}
