package com.mylove.basesqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mylove.loglib.JLog;
import com.mylove.sqlitelib.TableDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TableDao session = new TableDao.Builder()
                .setVersion(2)
                .builder(this);
//        DBBean dbBean = new DBBean();
//        dbBean.setTitle("标题4");
//        dbBean.setMsg("内容4");
//        dbBean.setTt("tt4");
//        long l = session.where(DBBean.class).operation().insert().find(dbBean);
//        JLog.v("插入" + l);
//        List<DBBean> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            DBBean bean = new DBBean();
//            bean.setTitle("标题" + i);
//            bean.setMsg("内容" + i);
//            bean.setTt("tt" + i);
//            list.add(bean);
//        }
//        long[] insert = session.where(DBBean.class).operation().insert().find(list);
//        for (long l : insert) {
//            JLog.v("插入" + l);
//        }
        List<DBBean> all = session.where(DBBean.class).operation().query().findAll();
        JLog.v(all);
//        int last = session.where(DBBean.class).eq(new ConditionMsg("title", "标题2")).operation().delete().findAll();
//        JLog.v(last);
//        DBBean bean = new DBBean();
//        bean.setMsg("内容5");
//        bean.setTitle("标题5");
//        int[] first = session.where(DBBean.class).eq(new ConditionMsg("title", "标题10")).operation().update().findAll(bean);
//        JLog.v(first);
    }
}
