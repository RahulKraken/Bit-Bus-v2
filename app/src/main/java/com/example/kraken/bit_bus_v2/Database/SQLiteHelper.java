package com.example.kraken.bit_bus_v2.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLiteHelper";

    public static final String DATABASE_NAME = "BitBus.db";
    public static final int DATABASE_VERSION = 3;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLiteContract.BusInfoEntry.SQL_CREATE_TABLE);
        db.execSQL(SQLiteContract.AutoInfoEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
