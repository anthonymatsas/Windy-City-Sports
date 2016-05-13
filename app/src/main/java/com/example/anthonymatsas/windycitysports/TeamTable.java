package com.example.anthonymatsas.windycitysports;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by anthonymatsas on 4/12/16.
 */
public class TeamTable {
    //Class level variables
    public static final String TABLE_TEAM = "teams";
    public static final String COL_ID = "_id";
    public static final String COL_UUID = "uuid";
    public static final String COL_NAME = "name";
    public static final String COL_IMAGE = "image";
    public static final String COL_HISTORY = "history";
    public static final String COL_TWITTERHANDLE = "twitterName";
    public static final String COL_YOUTUBEUSRNAME = "youtubeUsrName";

    //create DB table
    private static final String DB_CREATE =
            "CREATE table " +
                    TABLE_TEAM + "(" +
                    COL_ID + " integer primary key autoincrement, " +
                    COL_UUID + " text not null, " +
                    COL_NAME + " text not null, " +
                    COL_IMAGE + " text not null, " +
                    COL_TWITTERHANDLE + " char(50) not null, " +
                    COL_YOUTUBEUSRNAME + " char(50) not null, " +
                    COL_HISTORY + " text not null " + ");";

    //create
    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
        //FOR DEBUG ONLY
        //Log.d(TeamTable.class.getName(), DB_CREATE);
    }

    //upgrade
    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //FOR DEBUG ONLY
        //Log.w(TeamTable.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + "which will destroy all data.");
        db.execSQL("DROP TABLE IF EXISTS " + TeamTable.TABLE_TEAM);
        onCreate(db);
    }

}
