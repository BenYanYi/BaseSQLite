package com.mylove.basesqlite;

import com.benyanyi.sqlitelib.annotation.ColumnName;
import com.benyanyi.sqlitelib.annotation.ID;
import com.benyanyi.sqlitelib.annotation.NotNull;
import com.benyanyi.sqlitelib.annotation.TableBean;

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
    private String title = "titleStr1";
    @ColumnName("Message")
    private String msg = "msgStr1";

    private String content = "";

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

    @Override
    public String toString() {
        return "DBBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
