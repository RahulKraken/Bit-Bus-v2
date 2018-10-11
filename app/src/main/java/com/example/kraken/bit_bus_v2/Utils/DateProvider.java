package com.example.kraken.bit_bus_v2.Utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateProvider {

    private static final String TAG = "DateProvider";

    String day, time;

    public DateProvider() {
        Calendar calendar = Calendar.getInstance();

        Date currDate = calendar.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm");
        String rawTime = date.format(currDate);
        Log.d(TAG, "onCreateView: " + rawTime);
        time = rawTime.replace(":", ".");
        Log.d(TAG, "onCreateView: " + time);

        day = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        day = day.toLowerCase();
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
