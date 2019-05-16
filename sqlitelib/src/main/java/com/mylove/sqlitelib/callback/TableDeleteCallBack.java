package com.mylove.sqlitelib.callback;

/**
 * @author YanYi
 * @date 2019/5/16 17:17
 * @email ben@yanyi.red
 * @overview 删除逻辑处理
 */
public interface TableDeleteCallBack<T> {
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
