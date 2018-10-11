package com.example.kraken.bit_bus_v2.Database;

import android.provider.BaseColumns;

public class SQLiteContract implements BaseColumns {

    private SQLiteContract() {
        // Empty private constructor.
    }

    public static final class BusInfoEntry {
        public static final String TABLE_NAME = "bus_table";
        public static final String COLUMN_BUS_FROM = "bus_from";
        public static final String COLUMN_BUS_TYPE = "bus_type";
        public static final String COLUMN_TIME = "bus_time";
        public static final String COLUMN_DAY = "day";

        /*
            CREATE TABLE bus_table (_id INTEGER PRIMARY KEY, from TEXT NOT NULL, type TEXT NOT NULL, time TEXT NOT NULL, day TEXT NOT NULL)
         */

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_BUS_FROM + " TEXT NOT NULL, " +
                        COLUMN_BUS_TYPE + " TEXT NOT NULL, " +
                        COLUMN_TIME + " TEXT NOT NULL, " +
                        COLUMN_DAY + " TEXT NOT NULL)";
    }

    public static final class AutoInfoEntry {
        public static final String TABLE_NAME = "auto_table";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_NUMBER = "number";

        /*
        CREATE TABLE auto_table (name, phone, number)
         */
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_NAME + " TEXT NOT NULL, " +
                        COLUMN_PHONE + " TEXT NOT NULL, " +
                        COLUMN_NUMBER + " TEXT NOT NULL)";
    }
}
