package com.voc.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static SQLiteHelper instance;
    private static final int VERSION = 1;
    private static final String DB_NAME = "voc.db";

    private SQLiteHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public static synchronized SQLiteHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SQLiteHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SqliteStatements.TBL_DISTRICTS);
        sqLiteDatabase.execSQL(SqliteStatements.TBL_GRAMA);
        sqLiteDatabase.execSQL(SqliteStatements.TBL_TALUK);
        sqLiteDatabase.execSQL(SqliteStatements.TBL_PANCHAYATH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
