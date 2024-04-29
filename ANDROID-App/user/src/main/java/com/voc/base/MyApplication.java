package com.voc.base;

import android.app.Application;

import com.voc.sqlite.SQLiteHelper;

public class MyApplication extends Application {

    private static SQLiteHelper instance = null;

    @Override
    public void onCreate() {
        super.onCreate();

        if (instance == null) {
            instance = SQLiteHelper.getInstance(this); // create database
        }
    }

    public static SQLiteHelper getDBInstance() {
        return instance;
    }
}
