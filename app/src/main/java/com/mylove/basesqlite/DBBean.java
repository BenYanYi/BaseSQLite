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
