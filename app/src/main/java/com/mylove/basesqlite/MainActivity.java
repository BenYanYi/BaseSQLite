package com.mylove.basesqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mylove.loglib.JLog;
import com.mylove.sqlitelib.TableSession;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TableSession session = new TableSession.Builder().builder(this);
        DBBean dbBean = new DBBean();
        dbBean.setTitle("标题1");
        dbBean.setMsg("内容1");
//        long insert = session.where(DBBean.class).operation().insert(dbBean);
//        JLog.v("插入" + insert);
        List<DBBean> all = session.where(DBBean.class).operation().query().findAll();
        JLog.v(all);
    }
}
