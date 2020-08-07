package com.mylove.basesqlite;

import com.benyanyi.sqlitelib.annotation.ID;

/**
 * @author YanYi
 * @date 2020/8/7 15:44
 * @email ben@yanyi.red
 * @overview
 */
@com.benyanyi.sqlitelib.annotation.TableBean
public class TableBean {

    @ID(increase = true)
    private long id;
    private String title;

}
