package com.example.kraken.bit_bus_v2.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;

import com.example.kraken.bit_bus_v2.DataModels.AutoData;
import com.example.kraken.bit_bus_v2.DataModels.BusData;
import com.example.kraken.bit_bus_v2.Database.SQLiteContract;
import com.example.kraken.bit_bus_v2.Database.SQLiteContract.BusInfoEntry;
import com.example.kraken.bit_bus_v2.Database.SQLiteHelper;
import com.example.kraken.bit_bus_v2.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.example.kraken.bit_bus_v2.Database.SQLiteContract.*;

public class DataManager {

    private static final String TAG = "DataManager";

    private Context context;
    public static SQLiteDatabase database;

    private ArrayList<BusData> busList;

    private SQLiteHelper helper;

    public DataManager(Context context) {
        this.context = context;
        busList = new ArrayList<>();

        helper = new SQLiteHelper(context);
    }

    public ArrayList<BusData> getBusSchedule(String from, String type, String day) {

        database = helper.getReadableDatabase();

        String[] columns = {BusInfoEntry.COLUMN_BUS_FROM, BusInfoEntry.COLUMN_BUS_TYPE, BusInfoEntry.COLUMN_DAY, BusInfoEntry.COLUMN_TIME};

        String selector = BusInfoEntry.COLUMN_BUS_FROM + " = ? AND "
                + BusInfoEntry.COLUMN_BUS_TYPE + " = ? AND "
                + BusInfoEntry.COLUMN_DAY + " = ?";

        String[] selectionArguments = {from, type, day};

        Cursor busCursor = database.query(BusInfoEntry.TABLE_NAME, columns,
                selector, selectionArguments, null, null, null);

        while (busCursor.moveToNext()) {
            Log.d(TAG, "getBusSchedule: from - " + busCursor.getString(busCursor.getColumnIndex(BusInfoEntry.COLUMN_BUS_FROM)));
            Log.d(TAG, "getBusSchedule: type - " + busCursor.getString(busCursor.getColumnIndex(BusInfoEntry.COLUMN_BUS_TYPE)));
            Log.d(TAG, "getBusSchedule: day - " + busCursor.getString(busCursor.getColumnIndex(BusInfoEntry.COLUMN_DAY)));
            Log.d(TAG, "getBusSchedule: time - " + busCursor.getString(busCursor.getColumnIndex(BusInfoEntry.COLUMN_TIME)));

            busList.add(new BusData(from, type, day,
                    Double.parseDouble(busCursor.getString(busCursor.getColumnIndex(BusInfoEntry.COLUMN_TIME)))));
        }

        busCursor.close();
        database.close();
        return busList;
    }

    public String getNextBus(String from, String type, String day, Double time) {

        database = helper.getReadableDatabase();
        String nextTime = "Sorry, no buses today.";

        String[] columns = {BusInfoEntry.COLUMN_TIME};

        String selector = BusInfoEntry.COLUMN_BUS_FROM + " = ? AND "
                + BusInfoEntry.COLUMN_BUS_TYPE + " = ? AND "
                + BusInfoEntry.COLUMN_DAY + " = ?";

        String[] selectionArguments = {from, type, day};

        Cursor busCursor = database.query(BusInfoEntry.TABLE_NAME, columns,
                selector, selectionArguments, null, null, null);

        Double currItemTime;
        while (busCursor.moveToNext()) {
            currItemTime = Double.parseDouble(busCursor.getString(busCursor.getColumnIndex(BusInfoEntry.COLUMN_TIME)));
            Log.d(TAG, "getNextBus: curr-time : " + currItemTime);
            if (currItemTime >= time) {
                nextTime = String.valueOf(currItemTime).replace(".", ":");
                break;
            }
        }
        busCursor.close();
        database.close();

        return StringManipulation.formatString(nextTime);
    }

    public ArrayList<AutoData> getAutos() {
        database = helper.getReadableDatabase();
        ArrayList<AutoData> autos = new ArrayList<>();

        String[] columns = {AutoInfoEntry.COLUMN_NAME, AutoInfoEntry.COLUMN_PHONE, AutoInfoEntry.COLUMN_NUMBER};

        Cursor autoCursor = database.query(AutoInfoEntry.TABLE_NAME, columns,
                null, null, null, null, null);
        while (autoCursor.moveToNext()) {

            Log.d(TAG, "getAutos: - " + autoCursor.getString(autoCursor.getColumnIndex(AutoInfoEntry.COLUMN_NAME)) + " - " +
                    autoCursor.getString(autoCursor.getColumnIndex(AutoInfoEntry.COLUMN_PHONE)) + " - " +
                    autoCursor.getString(autoCursor.getColumnIndex(AutoInfoEntry.COLUMN_NUMBER)));

            autos.add(new AutoData(autoCursor.getString(autoCursor.getColumnIndex(AutoInfoEntry.COLUMN_NAME)),
                    autoCursor.getString(autoCursor.getColumnIndex(AutoInfoEntry.COLUMN_PHONE)),
                    autoCursor.getString(autoCursor.getColumnIndex(AutoInfoEntry.COLUMN_NUMBER))));
        }

        autoCursor.close();
        database.close();

        return autos;
    }
}
