package com.benyanyi.sqlitelib.callback;

/**
 * @author YanYi
 * @date 2019/5/16 17:17
 * @email ben@yanyi.red
 * @overview 删除逻辑处理
 */
public interface TableDeleteCallBack<T> {
    /**
     * 删除符合条件的第一条数据
     *
     * @return 成功执行条数
     */
    int findFirst();

    /**
     * 删除符合条件的最后一条数据
     *
     * @return 成功执行条数
     */
    int findLast();

    /**
     * 删除符合条件的所有数据
     *
     * @return 成功执行条数
     */
    int findAll();
}
