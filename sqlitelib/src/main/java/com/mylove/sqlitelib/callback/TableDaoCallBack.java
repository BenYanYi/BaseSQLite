package com.mylove.sqlitelib.callback;

/**
 * @author YanYi
 * @date 2019/5/17 14:01
 * @email ben@yanyi.red
 * @overview
 */
public interface TableDaoCallBack {

    /**
     * 返回表操作
     *
     * @param tClass 表类
     * @param <T>
     * @return
     */
    <T> TableSessionCallBack<T> getSession(Class<T> tClass);
}
