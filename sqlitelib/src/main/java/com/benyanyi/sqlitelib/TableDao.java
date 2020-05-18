package com.benyanyi.sqlitelib;

import android.content.Context;
import android.text.TextUtils;

import com.benyanyi.sqlitelib.annotation.TableBean;
import com.benyanyi.sqlitelib.exception.TableException;
import com.benyanyi.sqlitelib.impl.TableDaoImpl;
import com.benyanyi.sqlitelib.impl.TableSessionImpl;

/**
 * @author YanYi
 * @date 2019/5/17 16:14
 * @email ben@yanyi.red
 * @overview
 */
public final class TableDao implements TableDaoImpl {
    private String dbName;
    private int version;
    private Context mContext;
    private Class<?>[] classes;
    private TableInjectImpl inject;
    private TableSession session;

    public static final String DB_NAME = "base_db_db_name";

    private TableDao() {
    }

    private TableDao(Context context, Builder builder) {
        this.mContext = context;
        this.dbName = builder.dbName;
        this.version = builder.version;
        this.classes = builder.classes;
        this.inject = initInject();
        this.session = new TableSession();
    }

    private TableInjectImpl initInject() {
        return new TableInject().init(this.mContext, this.dbName, this.version, this.classes);
    }


    @Override
    public <T> TableSessionImpl<T> getSession(Class<T> tClass) {
        return this.session.init(tClass, this.inject);
    }

    public static class Builder {
        private String dbName = null;
        private int version = 1;
        private Class<?>[] classes;

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

        public Builder setClasses(Class<?>... classes) {
            this.classes = classes;
            return this;
        }

        public TableDaoImpl builder(Context context) {
            boolean boo = TextUtils.isEmpty(dbName) || "".equals(dbName.trim())
                    || "null".equals(dbName.toLowerCase().trim());
            if (boo) {
                dbName = context.getPackageName() + "_TABLE_DB";
            }
            if (this.version < 1) {
                this.version = 1;
            }
            if (this.classes.length <= 0) {
                throw new NullPointerException("setClasses is not null");
            } else {
                for (Class<?> tClass : classes) {
                    if (!isTabBean(tClass)) {
                        String simpleName = tClass.getSimpleName();
                        throw new TableException(simpleName + " is missing TableBean annotation(" + simpleName + "缺少TableBean注解");
                    }
                }
            }
            return new TableDao(context, this);
        }

        private boolean isTabBean(Class<?> tClass) {
            TableBean annotation = tClass.getAnnotation(TableBean.class);
            return annotation != null;
        }
    }
}
