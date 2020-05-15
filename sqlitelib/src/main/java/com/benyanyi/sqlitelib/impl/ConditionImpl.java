package com.benyanyi.sqlitelib.impl;

import com.benyanyi.sqlitelib.condition.ConditionMsg;
import com.benyanyi.sqlitelib.config.TableSort;

import java.util.List;

/**
 * @author YanYi
 * @date 2019/3/29 13:56
 * @email ben@yanyi.red
 * @overview 条件处理
 */
public interface ConditionImpl<T> {

    /**
     * 清空条件
     *
     * @return
     */
    ConditionImpl<T> cleanCondition();

    /**
     * 相等
     *
     * @param list 多相等条件
     * @return
     */
    ConditionImpl<T> eq(List<ConditionMsg> list);

    /**
     * 相等
     *
     * @param conditionMsg 单相等条件
     * @return
     */
    ConditionImpl<T> eq(ConditionMsg conditionMsg);

    /**
     * 不等
     *
     * @param list 多不等条件
     * @return
     */
    ConditionImpl<T> notEq(List<ConditionMsg> list);

    /**
     * 不等
     *
     * @param conditionMsg 单条不等条件
     * @return
     */
    ConditionImpl<T> notEq(ConditionMsg conditionMsg);

    /**
     * 大于
     *
     * @param list 多条大于条件
     * @return
     */
    ConditionImpl<T> greater(List<ConditionMsg> list);

    /**
     * 大于
     *
     * @param conditionMsg 单条大于条件
     * @return
     */
    ConditionImpl<T> greater(ConditionMsg conditionMsg);

    /**
     * 小于
     *
     * @param list 多条小于条件
     * @return
     */
    ConditionImpl<T> less(List<ConditionMsg> list);

    /**
     * 小于
     *
     * @param conditionMsg 单条小于条件
     * @return
     */
    ConditionImpl<T> less(ConditionMsg conditionMsg);

    /**
     * 部分存在（模糊）
     *
     * @param list 多条部分存在条件
     * @return
     */
    ConditionImpl<T> in(List<ConditionMsg> list);

    /**
     * 部分存在（模糊)
     *
     * @param conditionMsg 单条部分存在条件
     * @return
     */
    ConditionImpl<T> in(ConditionMsg conditionMsg);

    /**
     * 排序
     *
     * @param field 排序依据的列名
     * @param sort  排序规则
     * @return
     */
    ConditionImpl<T> sort(String field, TableSort sort);

    /**
     * 逻辑处理（增删查改）
     *
     * @return
     */
    OperationImpl<T> operation();
}
