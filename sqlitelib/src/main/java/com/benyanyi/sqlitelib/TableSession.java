package com.benyanyi.sqlitelib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.benyanyi.sqlitelib.annotation.TableBean;
import com.benyanyi.sqlitelib.impl.TableSessionImpl;
import com.benyanyi.sqlitelib.condition.ConditionMsg;
import com.benyanyi.sqlitelib.condition.TableCondition;
import com.benyanyi.sqlitelib.exception.TableException;
import com.benyanyi.sqlitelib.impl.ConditionImpl;

import java.util.ArrayList;

/**
 * @author YanYi
 * @date 2019/5/17 15:29
 * @email ben@yanyi.red
 * @overview
 */
public final class TableSession<T> implements TableSessionImpl<T> {
    private String dbName;
    private int version;
    private Context context;
    private Class<T> tClass;
    private TableInjectImpl injectImpl;
    private SQLiteDatabase database;
    private TableCondition.Builder builder;

    TableSession() {

    }

    TableSessionImpl<T> init(String dbName, int version, Context context, Class<T> tClass) {
        this.dbName = dbName;
        this.version = version;
        this.context = context;
        this.tClass = tClass;
        this.injectImpl = new TableInject();
        this.database = getDB();
        this.builder = getTableCondition();
        return this;
    }

    private boolean isTabBean() {
        TableBean annotation = tClass.getAnnotation(TableBean.class);
        return annotation != null;
    }

    private SQLiteDatabase getDB() {
        if (isTabBean()) {
            injectImpl = this.injectImpl.init(context, dbName, version, tClass);
            return injectImpl.getHelperWritableDatabase();
        } else {
            throw new TableException("当前类没有定义成表结构类。(The current class is not defined as a table structure class.)");
        }
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

    @Override
    public boolean tableIsExist(String tableName) {
        boolean boo = TextUtils.isEmpty(tableName) || "null".equals(tableName.toLowerCase().trim())
                || tableName.trim().length() <= 0;
        if (boo) {
            return false;
        }
        return this.injectImpl.tableIsExist(tableName);
    }

    @Override
    public String getDBPath() {
        return this.injectImpl.getDBPath();
    }

    @Override
    public void close() {
        this.injectImpl.close();
    }
}
