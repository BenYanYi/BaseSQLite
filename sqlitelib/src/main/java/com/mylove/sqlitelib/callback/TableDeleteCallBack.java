package com.mylove.sqlitelib.callback;

/**
 * @author yangjiewei
 * @time 2019/5/14 17:20
 * 删除逻辑处理
 */
public interface TableDeleteCallBack {
    /**
     * 删除第一条数据
     *
     * @return
     */
    int findFirst();

    /**
     * 删除最后一条数据
     *
     * @return
     */
    int findLast();

    /**
     * 删除所有数据
     *
     * @return
     */
    int findAll();
}
