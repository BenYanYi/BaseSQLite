package com.mylove.basesqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.benyanyi.sqlitelib.TableDao;
import com.benyanyi.sqlitelib.impl.TableDaoImpl;
import com.benyanyi.sqlitelib.impl.TableSessionImpl;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TableDaoImpl tableDao = new TableDao.Builder().setDbName("mainDB").builder(this);
        TableSessionImpl<DBBean> session = tableDao.getSession(DBBean.class);
        DBBean dbBean = new DBBean();
        boolean tableIsExist = session.tableIsExist("DBBean");
//        JLog.d(tableIsExist);
        long l = session.where().operation().insert().find(dbBean);
//        JLog.d(l);
        List<DBBean> beanList = session.where().operation().query().findAll();
        for (DBBean dbBean1 : beanList) {
//            JLog.d(dbBean1);
        }
//        DebugDB.getAddressLog();
    }
}

