package com.mylove.sqlitelib;

import android.content.Context;
import android.text.TextUtils;

import com.mylove.sqlitelib.callback.TableDaoCallBack;
import com.mylove.sqlitelib.callback.TableSessionCallBack;

/**
 * @author YanYi
 * @date 2019/5/17 16:14
 * @email ben@yanyi.red
 * @overview
 */
public final class TableDao implements TableDaoCallBack {
    private String dbName;
    private int version;
    private Context mContext;
    private TableSession session;

    public static final String DB_NAME = "base_db_db_name";

    private TableDao() {
    }

    private TableDao(Context context, Builder builder) {
        this.mContext = context;
        this.dbName = builder.dbName;
        this.version = builder.version;
        this.session = new TableSession();
    }

    @Override
    public <T> TableSessionCallBack<T> getSession(Class<T> tClass) {
        return this.session.init(this.dbName, this.version, this.mContext, tClass);
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

        public TableDaoCallBack builder(Context context) {
            if (TextUtils.isEmpty(dbName) || dbName.trim().equals("")
                    || dbName.toLowerCase().trim().equals("null")) {
                dbName = context.getPackageName() + "_TABLE_DB";
            }
            if (this.version < 1) {
                this.version = 1;
            }
            return new TableDao(context, this);
        }
    }
}
