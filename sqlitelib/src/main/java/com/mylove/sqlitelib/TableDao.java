package com.mylove.sqlitelib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.mylove.sqlitelib.annotation.TableBean;
import com.mylove.sqlitelib.callback.ConditionCallBack;
import com.mylove.sqlitelib.condition.ConditionMsg;
import com.mylove.sqlitelib.condition.TableCondition;
import com.mylove.sqlitelib.exception.TableException;

import java.util.ArrayList;

/**
 * @author YanYi
 * @date 2019/3/27 10:12
 * @email ben@yanyi.red
 * @overview
 */
public class TableDao {
    private Context context;
    public static final String DB_NAME = "base_db_db_name";
    private String dbName;
    private int version;
    private TableCondition.Builder builder;


    private TableDao(Context context, Builder builder) {
        this.context = context;
        this.dbName = builder.dbName;
        this.version = builder.version;
        this.builder = init();
    }

    private TableCondition.Builder init() {
        return new TableCondition.Builder()
                .setEqList(new ArrayList<ConditionMsg>())
                .setNotEqList(new ArrayList<ConditionMsg>())
                .setGreaterList(new ArrayList<ConditionMsg>())
                .setLessList(new ArrayList<ConditionMsg>())
                .setInList(new ArrayList<ConditionMsg>());
    }

    private <T> boolean isTabBean(Class<T> tClass) {
        TableBean annotation = tClass.getAnnotation(TableBean.class);
        return annotation != null;
    }

    private <T> SQLiteDatabase getDB(Class<T> tClass) {
        if (isTabBean(tClass)) {
            TableHelper tableHelper = TableInject.init(context, dbName, version, tClass);
            return tableHelper.getWritableDatabase();
        } else {
            throw new TableException("当前类没有定义成表结构类。(The current class is not defined as a table structure class.)");
        }
    }

    public <T> ConditionCallBack<T> where(Class<T> tClass) {
//        return new TableCondition.Builder()
//                .setEqList(new ArrayList<ConditionMsg>())
//                .setNotEqList(new ArrayList<ConditionMsg>())
//                .setGreaterList(new ArrayList<ConditionMsg>())
//                .setLessList(new ArrayList<ConditionMsg>())
//                .setInList(new ArrayList<ConditionMsg>())
//                .builder(getDB(tClass), tClass);
        return this.builder.builder(getDB(tClass), tClass);
    }

    public static class Builder {
        private String dbName = null;
        private int version = 1;

        public Builder setDbName(String dbName) {
            this.dbName = dbName;
            return this;
        }

        public Builder setVersion(int version) {
            this.version = version;
            if (this.version < 1) {
                this.version = 1;
            }
            return this;
        }

        public TableDao builder(Context context) {
            if (TextUtils.isEmpty(dbName) || dbName.trim().equals("")
                    || dbName.toLowerCase().trim().equals("null")) {
                dbName = context.getPackageName() + "_TABLE_DB";
            }
            return new TableDao(context, this);
        }
    }
}
