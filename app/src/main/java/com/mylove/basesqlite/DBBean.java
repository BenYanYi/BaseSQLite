package com.mylove.basesqlite;

import com.mylove.sqlitelib.annotation.ColumnName;
import com.mylove.sqlitelib.annotation.ID;
import com.mylove.sqlitelib.annotation.NotNull;
import com.mylove.sqlitelib.annotation.TableBean;

/**
 * @author YanYi
 * @date 2019/4/1 16:17
 * @email ben@yanyi.red
 * @overview
 */
@TableBean
public class DBBean {
    @ID(increase = true)
    private long id;
    @NotNull
    private String title;
    private String msg;
    @ColumnName("tt")
    private String tt;

    //    private String hh;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTt() {
        return tt;
    }

    public void setTt(String tt) {
        this.tt = tt;
    }

//    public String getHh() {
//        return hh;
//    }
//
//    public void setHh(String hh) {
//        this.hh = hh;
//    }

    @Override
    public String toString() {
        return "DBBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", msg='" + msg + '\'' +
                ", tt='" + tt + '\'' +
//                ",hh=" + hh + '\'' +
                '}';
    }
}
