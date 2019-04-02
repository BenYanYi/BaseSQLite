package com.mylove.sqlitelib.callback;

import com.mylove.sqlitelib.config.TableSort;
import com.mylove.sqlitelib.condition.ConditionMsg;
import com.mylove.sqlitelib.condition.TableCondition;
import com.mylove.sqlitelib.operation.TableOperation;

import java.util.List;

/**
 * @author YanYi
 * @date 2019/3/29 13:56
 * @email ben@yanyi.red
 * @overview 条件处理
 */
public interface ConditionCallBack {
    TableCondition eq(List<ConditionMsg> list);

    TableCondition eq(ConditionMsg conditionMsg);

    TableCondition notEq(List<ConditionMsg> list);

    TableCondition notEq(ConditionMsg conditionMsg);

    TableCondition greater(List<ConditionMsg> list);

    TableCondition greater(ConditionMsg conditionMsg);

    TableCondition less(List<ConditionMsg> list);

    TableCondition less(ConditionMsg conditionMsg);

    TableCondition in(List<ConditionMsg> list);

    TableCondition in(ConditionMsg conditionMsg);

    TableCondition sort(String field, TableSort sort);

    TableOperation operation();
}
