package com.mylove.basesqlite;

import com.benyanyi.sqlitelib.annotation.ID;
import com.benyanyi.sqlitelib.annotation.TableBean;

/**
 * @author YanYi
 * @date 2020/05/18 16:47
 * @email ben@yanyi.red
 * @overview
 */
@TableBean
public class TestBean {

    @ID(increase = true)
    private long id = 0;

    private String msg = "";

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "id=" + id +
                ", msg='" + msg + '\'' +
                '}';
    }
}
