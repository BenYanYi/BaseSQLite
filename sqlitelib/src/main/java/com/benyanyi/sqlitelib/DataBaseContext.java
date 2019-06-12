package com.benyanyi.sqlitelib;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * @author YanYi
 * @date 2019/5/20 11:43
 * @email ben@yanyi.red
 * @overview 用于修改sqlite保存地址
 */
final class DataBaseContext extends ContextWrapper {
    private Context mContext;

    public DataBaseContext(Context base) {
        super(base);
        this.mContext = base;
    }

    @Override
    public File getDatabasePath(String name) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AAA";
        File pathFile = new File(path);
        File file = new File(path + "/aa.db");
        try {
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        return super.openOrCreateDatabase(name, mode, factory);
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return super.openOrCreateDatabase(name, mode, factory, errorHandler);
    }
}
