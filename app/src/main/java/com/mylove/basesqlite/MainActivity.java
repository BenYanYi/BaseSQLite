package com.mylove.basesqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.benyanyi.sqlitelib.TableDao;
import com.benyanyi.sqlitelib.callback.TableDaoCallBack;
import com.benyanyi.sqlitelib.callback.TableSessionCallBack;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        tt(new DBBean());
        TableDaoCallBack tableDao = new TableDao.Builder().setDbName("mainDB").builder(this);
        TableSessionCallBack<DBBean> session = tableDao.getSession(DBBean.class);
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

//    private <T> void tt(T t) {
//        Field[] fields = t.getClass().getDeclaredFields();
//        try {
//            Object o = t.getClass().newInstance();
//            int a = 2;
//            for (Field field : fields) {
//                if (!TextUtils.isEmpty(field.getName()) && !"null".equals(field.getName().toLowerCase().trim())
//                        && field.getName().trim().length() != 0 && !field.getName().trim().equals("$change")
//                        && !field.getName().trim().equals("serialVersionUID")) {
//                    field.setAccessible(true);
//                    try {
//                        field.set(o, a + "");
//                        a++;
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            JLog.d(o);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }
//    }
}

