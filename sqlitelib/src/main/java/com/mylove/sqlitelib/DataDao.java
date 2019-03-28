package com.mylove.sqlitelib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mylove.sqlitelib.annotation.TableBean;
import com.mylove.sqlitelib.db.TableCallBack;
import com.mylove.sqlitelib.db.TableObject;

/**
 * @author YanYi
 * @date 2019/3/27 10:12
 * @email ben@yanyi.red
 * @overview
 */
public class DataDao {
    public static final String DB_NAME = "base_db_db_name";
    private Context context;
    private String dbName;
    private int version;

    DataDao(Context context, Builder builder) {
        this.context = context;
        this.dbName = builder.dbName;
        this.version = builder.version;
    }

    private <T> boolean isTabBean(T t) {
        TableBean annotation = t.getClass().getAnnotation(TableBean.class);
        if (annotation != null) {
            return true;
        } else if (t instanceof TableObject) {
            return true;
        } else if (TableCallBack.class.isAssignableFrom(t.getClass())) {
            return true;
        } else {
            return false;
        }
    }

    private <T> SQLiteDatabase getDB(T t) {
        SQLiteHelper sqLiteHelper = SQLiteInject.init(context, dbName, version, t);
        return sqLiteHelper.getWritableDatabase();
    }

    public <T> void insert(T t) {
        if (!isTabBean(t)) {
            SQLiteDatabase db = getDB(t);
        }
    }

    public static class Builder {
        private String dbName = DB_NAME;
        private int version = 1;

        public void setDbName(String dbName) {
            this.dbName = dbName;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public DataDao builder(Context context) {
            return new DataDao(context, this);
        }
    }
}
