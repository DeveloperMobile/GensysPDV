package com.codigosandroid.gensyspdv.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.codigosandroid.utils.utils.LogUtil;

/**
 * Created by Tiago on 22/12/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static String[] CREATE_SCRIPT;

    public DBHelper(Context context, String name, String[] CREATE_SCRIPT, int version) {
        super(context, name, null, version);
        this.CREATE_SCRIPT = CREATE_SCRIPT;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        for (int i = 0; i < CREATE_SCRIPT.length; i++) {
            db.execSQL(CREATE_SCRIPT[i]);
            LogUtil.debug("onCreate():", CREATE_SCRIPT[i]);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        for (int i = 0; i < CREATE_SCRIPT.length; i++) {
            switch (oldVersion) {
                case 1:
                    db.execSQL(CREATE_SCRIPT[i]);
                    LogUtil.debug("onUpgrade():", CREATE_SCRIPT[i]);
                    break;
                case 2:
                    db.execSQL(CREATE_SCRIPT[i]);
                    LogUtil.debug("onUpgrade():", CREATE_SCRIPT[i]);
                    break;
                case 3:
                    db.execSQL(CREATE_SCRIPT[i]);
                    LogUtil.debug("onUpgrade():", CREATE_SCRIPT[i]);
                    break;
            }
        }

    }

}
