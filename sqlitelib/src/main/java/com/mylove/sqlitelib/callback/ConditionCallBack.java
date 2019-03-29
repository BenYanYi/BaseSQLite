package com.mylove.sqlitelib.callback;

import com.mylove.sqlitelib.operation.TabOperation;
import com.mylove.sqlitelib.condition.TableCondition;

import java.util.Map;

/**
 * @author YanYi
 * @date 2019/3/29 13:56
 * @email ben@yanyi.red
 * @overview
 */
public interface ConditionCallBack {
    TableCondition equalTo(Map<String, Object> condition);

    TableCondition equalTo(String field, Object value);

    TableCondition or(Map<String, Object> condition);

    TableCondition or(String field, Object value);

    TableCondition in(Map<String, Object> condition);

    TableCondition in(String field, Object value);

    TableCondition notEqualTo(Map<String, Object> condition);

    TableCondition notEqualTo(String field, Object value);

    TableCondition notOr(Map<String, Object> condition);

    TableCondition notOr(String field, Object value);

    TableCondition notIn(Map<String, Object> condition);

    TableCondition notIn(String field, Object value);

    TabOperation operation();
}
