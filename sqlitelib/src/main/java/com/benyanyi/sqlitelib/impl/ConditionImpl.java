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
     * 排序
     *
     * @param field 排序依据的列名
     * @param sort  排序规则
     * @return
     */
    ConditionImpl<T> sort(String field, TableSort sort);

    OperationImpl<T> operation(ConditionMsg conditionMsg);

    OperationImpl<T> operation(List<ConditionMsg> list);

    /**
     * 逻辑处理（增删查改）
     *
     * @return
     */
    OperationImpl<T> operation();
}
