package com.example.kraken.bit_bus_v2.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.kraken.bit_bus_v2.Database.SQLiteContract;
import com.example.kraken.bit_bus_v2.Database.SQLiteHelper;
import com.example.kraken.bit_bus_v2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseHelper {

    // Log TAG
    private static final String TAG = "FirebaseHelper";

    // Class variables
    private Context context;
    private ArrayList<String> list = new ArrayList<>();

    private DatabaseReference myRef;
    private FirebaseDatabase database;

    public static boolean FLAG = false;

    /**
     * Constructor with temporary static query
     * @param context
     */
    public FirebaseHelper(Context context) {
        this.context = context;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        retrieveData(myRef);
    }

    /**
     * Retrieve data at the database ref node
     * @param reference
     */
    public void retrieveData(DatabaseReference reference) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                SQLiteHelper mHelper = new SQLiteHelper(context);
                SQLiteDatabase database = mHelper.getWritableDatabase();

                database.execSQL("delete from bus_table");

                ContentValues values = new ContentValues();

                for (DataSnapshot majornode : dataSnapshot.child(context.getString(R.string.busDatabaseNode)).getChildren()) {
                    for (DataSnapshot data : majornode.getChildren()) {
                        for (DataSnapshot week : data.getChildren()) {
                            for (DataSnapshot day : week.getChildren()) {
                                for (DataSnapshot time : day.getChildren()) {
                                    Log.d(TAG, "onDataChange: data - " +
                                            majornode.getKey() + " - " +
                                            data.getKey() + " - " +
                                            day.getKey() + " - " +
                                            time.getValue());

                                    values.put(SQLiteContract.BusInfoEntry.COLUMN_BUS_FROM, majornode.getKey());
                                    values.put(SQLiteContract.BusInfoEntry.COLUMN_BUS_TYPE, data.getKey());
                                    values.put(SQLiteContract.BusInfoEntry.COLUMN_DAY, day.getKey());
                                    values.put(SQLiteContract.BusInfoEntry.COLUMN_TIME, time.getValue().toString());

                                    database.insert(SQLiteContract.BusInfoEntry.TABLE_NAME, null, values);
                                }
                            }
                        }
                    }
                }
                values.clear();

                database.execSQL("delete from auto_table");

                for (DataSnapshot majornode : dataSnapshot.child(context.getString(R.string.autoDatabaseNode)).getChildren()) {

                    Log.d(TAG, "onDataChange: auto driver - " + majornode.getKey());
                    Log.d(TAG, "onDataChange: driver phn - " + majornode.child(context.getString(R.string.autophone)).getValue());
                    Log.d(TAG, "onDataChange: auto number - " + majornode.child(context.getString(R.string.autonumber)).getValue());

                    values.put(SQLiteContract.AutoInfoEntry.COLUMN_NAME,
                            majornode.getKey());
                    values.put(SQLiteContract.AutoInfoEntry.COLUMN_NUMBER,
                            majornode.child(context.getString(R.string.autonumber)).getValue().toString());
                    values.put(SQLiteContract.AutoInfoEntry.COLUMN_PHONE,
                            majornode.child(context.getString(R.string.autophone)).getValue().toString());

                    database.insert(SQLiteContract.AutoInfoEntry.TABLE_NAME, null, values);
                }
                values.clear();

                Log.d(TAG, "onDataChange: " + list);
                database.close();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "Unable to get updated time", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
