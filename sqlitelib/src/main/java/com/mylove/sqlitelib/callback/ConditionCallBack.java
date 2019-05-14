package com.mylove.sqlitelib.callback;

import com.mylove.sqlitelib.condition.ConditionMsg;
import com.mylove.sqlitelib.config.TableSort;

import java.util.List;

/**
 * @author YanYi
 * @date 2019/3/29 13:56
 * @email ben@yanyi.red
 * @overview 条件处理
 */
public interface ConditionCallBack {

    ConditionCallBack eq(List<ConditionMsg> list);

    ConditionCallBack eq(ConditionMsg conditionMsg);

    ConditionCallBack notEq(List<ConditionMsg> list);

    ConditionCallBack notEq(ConditionMsg conditionMsg);

    ConditionCallBack greater(List<ConditionMsg> list);

    ConditionCallBack greater(ConditionMsg conditionMsg);

    ConditionCallBack less(List<ConditionMsg> list);

    ConditionCallBack less(ConditionMsg conditionMsg);

    ConditionCallBack in(List<ConditionMsg> list);

    ConditionCallBack in(ConditionMsg conditionMsg);

    ConditionCallBack sort(String field, TableSort sort);

    OperationCallBack operation();
}
