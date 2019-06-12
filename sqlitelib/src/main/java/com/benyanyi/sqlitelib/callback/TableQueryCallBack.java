package com.benyanyi.sqlitelib.callback;

import java.util.List;

/**
 * @author YanYi
 * @date 2019/5/16 17:17
 * @email ben@yanyi.red
 * @overview 查询数据
 */
public interface TableQueryCallBack<T> {

    /**
     * 查询符合条件的第一条数据
     *
     * @return 数据
     */
    T findFirst();

    /**
     * 查询符合条件的最后一条数据
     *
     * @return 数据
     */
    T findLast();

    /**
     * 查询符合条件的所有数据集合
     *
     * @return 数据
     */
    List<T> findAll();

}
