package com.benyanyi.sqlitelib;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.benyanyi.sqlitelib.condition.ConditionMsg;
import com.benyanyi.sqlitelib.condition.TableCondition;
import com.benyanyi.sqlitelib.impl.ConditionImpl;
import com.benyanyi.sqlitelib.impl.TableSessionImpl;

import java.util.ArrayList;

/**
 * @author YanYi
 * @date 2019/5/17 15:29
 * @email ben@yanyi.red
 * @overview
 */
public final class TableSession<T> implements TableSessionImpl<T> {
    private Class<T> tClass;
    private TableInjectImpl injectImpl;
    private SQLiteDatabase database;
    private TableCondition.Builder builder;

    TableSession() {

    }

    TableSessionImpl<T> init(Class<T> tClass, TableInjectImpl injectImpl) {
        this.tClass = tClass;
        this.injectImpl = injectImpl;
        this.database = getDB();
        this.builder = getTableCondition();
        return this;
    }

    private SQLiteDatabase getDB() {
        return this.injectImpl.getHelperWritableDatabase();
    }

    private TableCondition.Builder getTableCondition() {
        return new TableCondition.Builder()
                .setEqList(new ArrayList<ConditionMsg>())
                .setNotEqList(new ArrayList<ConditionMsg>())
                .setGreaterList(new ArrayList<ConditionMsg>())
                .setLessList(new ArrayList<ConditionMsg>())
                .setInList(new ArrayList<ConditionMsg>());
    }

    @Override
    public ConditionImpl<T> where() {
        return this.builder.builder(this.database, this.tClass);
    }

    /**
     * 条件处理类
     *
     * @param isCleanCondition 是否清空条件
     * @return
     */
    @Override
    public ConditionImpl<T> where(boolean isCleanCondition) {
        if (isCleanCondition) {
            return this.builder.setEqList(new ArrayList<ConditionMsg>())
                    .setNotEqList(new ArrayList<ConditionMsg>())
                    .setGreaterList(new ArrayList<ConditionMsg>())
                    .setLessList(new ArrayList<ConditionMsg>())
                    .setInList(new ArrayList<ConditionMsg>())
                    .builder(this.database, this.tClass);
        } else {
            return where();
        }
    }

    @Override
    public SQLiteDatabase getSQLiteDatabase() {
        return this.database;
    }

    /**
     * 判断某张表是否存在
     */
    @Override
    public boolean tableIsExist() {
        return this.injectImpl.tableIsExist(tClass);
    }

    @Override
    public boolean tableIsExist(String tableName) {
        boolean boo = TextUtils.isEmpty(tableName) || "null".equals(tableName.toLowerCase().trim())
                || tableName.trim().length() <= 0;
        if (boo) {
            return false;
        }
        return this.injectImpl.tableIsExist(tableName);
    }

    /**
     * 获取数据库路径
     */
    @Override
    public String getDBPath() {
        return this.injectImpl.getDBPath(tClass);
    }

    @Override
    public String getDBPath(String tabName) {
        return this.injectImpl.getDBPath(tabName);
    }

    @Override
    public void close() {
        this.injectImpl.close();
    }
}
