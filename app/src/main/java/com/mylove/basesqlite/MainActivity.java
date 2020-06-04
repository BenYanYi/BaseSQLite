package com.mylove.basesqlite;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.benyanyi.sqlitelib.TableDao;
import com.benyanyi.sqlitelib.impl.TableDaoImpl;
import com.benyanyi.sqlitelib.impl.TableSessionImpl;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.tv);
        TableDaoImpl tableDao = new TableDao.Builder()
                .setDbName("mainDB")
                .setClasses(DBBean.class, TestBean.class)
                .builder(this);
        TableSessionImpl<DBBean> session = tableDao.getSession(DBBean.class);
        DBBean dbBean = new DBBean();
        dbBean.setTitle("标题0");
        dbBean.setMsg("内容0");
        session.where().operation().insert().find(dbBean);
        tv.setText(session.getDBPath());
        tv.append("\n" + session.tableIsExist());
        TableSessionImpl<TestBean> session1 = tableDao.getSession(TestBean.class);
        TestBean bean = new TestBean();
        bean.setMsg("哈哈");
        session1.where().operation().insert().find(bean);
        tv.append("\n" + session1.getDBPath());
        tv.append("\n" + session1.tableIsExist("TestBean"));
//        DebugDB.getAddressLog();
    }
}

