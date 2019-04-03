package com.mylove.sqlitelib.callback;

import com.mylove.sqlitelib.operation.TableAddOrChange;
import com.mylove.sqlitelib.operation.TableDelete;
import com.mylove.sqlitelib.operation.TableInsert;
import com.mylove.sqlitelib.operation.TableQuery;
import com.mylove.sqlitelib.operation.TableUpdate;

/**
 * @author YanYi
 * @date 2019/3/29 14:10
 * @email ben@yanyi.red
 * @overview 逻辑操作
 */
public interface OperationCallBack {
    TableInsert insert();

    TableDelete delete();

    TableQuery query();

    TableUpdate update();

    @Deprecated
    TableAddOrChange addOrChange();
}
