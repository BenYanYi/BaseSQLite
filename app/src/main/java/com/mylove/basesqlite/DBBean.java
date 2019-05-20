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
    private String title = "titleStr";
    @ColumnName("Message")
    private String msg = "msgStr";

    @Override
    public String toString() {
        return "DBBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
