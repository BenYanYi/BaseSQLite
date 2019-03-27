package com.mylove.sqlitelib;

import android.content.Context;

import com.mylove.sqlitelib.init.SQLiteHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YanYi
 * @date 2019/3/27 10:16
 * @email ben@yanyi.red
 * @overview
 */
class SQLiteInject {

    static SQLiteHelper init(Context context, String dbName, int version) {
        return null;
    }

    private static String getTabName() {
        return "";
    }

    private static Map<String, String> getFieldMap() {
        return new HashMap<>();
    }
}
