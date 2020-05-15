package com.mylove.basesqlite;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.benyanyi.loglib.Jlog;
import com.benyanyi.sqlitelib.TableDao;
import com.benyanyi.sqlitelib.condition.ConditionMsg;
import com.benyanyi.sqlitelib.impl.TableDaoImpl;
import com.benyanyi.sqlitelib.impl.TableSessionImpl;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TableDaoImpl tableDao = new TableDao.Builder().setDbName("mainDB").builder(this);
        TableSessionImpl<DBBean> session = tableDao.getSession(DBBean.class);
        DBBean dbBean = new DBBean();
        dbBean.setTitle("标题0");
        dbBean.setMsg("内容0");
        session.where().operation().insert().find(dbBean);
        List<DBBean> oList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DBBean bean = new DBBean();
            bean.setTitle("标题" + (i + 1));
            bean.setMsg("内容" + (i + 1));
            oList.add(bean);
        }
        long[] longs = session.where().operation().insert().find(oList);
        for (int i = 0; i < longs.length; i++) {
            Jlog.v("插入", longs[i]);
        }
        DBBean first = session.where().eq(new ConditionMsg("title", "标题3")).operation().query().findFirst();
        Jlog.v(first);
        DBBean bean = new DBBean();
        bean.setTitle("我是标题");
        bean.setMsg("我是内容");
        int[] all = session.where().eq(new ConditionMsg("title", "标题4")).operation().update().findAll(bean);
        for (int i = 0; i < all.length; i++) {
            Jlog.v("修改1", all[i]);
        }
        int[] all1 = session.where(true).eq(new ConditionMsg("title", "标题4")).operation().update().findAll(bean);
        for (int i = 0; i < all1.length; i++) {
            Jlog.v("修改2", all1[i]);
        }
        long[] all2 = session.where().eq(new ConditionMsg("title", "标题5")).operation().changeOrAdd().findAll(bean);
        for (int i = 0; i < all2.length; i++) {
            Jlog.v("修改3", all2[i]);
        }
        long[] all3 = session.where(true).eq(new ConditionMsg("title", "标题5")).operation().changeOrAdd().findAll(bean);
        for (int i = 0; i < all3.length; i++) {
            Jlog.v("修改4", all3[i]);
        }
//        DebugDB.getAddressLog();
    }
}

