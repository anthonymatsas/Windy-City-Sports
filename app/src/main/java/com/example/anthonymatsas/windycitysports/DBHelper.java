package com.example.anthonymatsas.windycitysports;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by anthonymatsas on 4/12/16.
 */
public class DBHelper extends SQLiteOpenHelper {
    //Create or update DB
    private static final String DB_NAME = "wcs.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        TeamTable.onCreate(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        TeamTable.onUpgrade(db, oldVersion, newVersion);
    }
}
