package com.benyanyi.sqlitelib.callback;

/**
 * @author YanYi
 * @date 2019/5/16 17:17
 * @email ben@yanyi.red
 * @overview 修改数据
 */
public interface TableUpdateCallBack<T> {
    /**
     * 修改符合条件的第一条数据
     *
     * @param t 修改后的数据
     * @return 修改成功条数
     */
    int findFirst(T t);

    /**
     * 修改符合条件的最后一条数据
     *
     * @param t 修改后的数据
     * @return 修改成功条数
     */
    int findLast(T t);

    /**
     * 修改符合条件的全部数据
     *
     * @param t 修改后的数据
     * @return 修改成功条数
     */
    int[] findAll(T t);
}
