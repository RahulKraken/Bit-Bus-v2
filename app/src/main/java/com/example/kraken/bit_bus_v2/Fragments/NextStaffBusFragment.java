package com.example.kraken.bit_bus_v2.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kraken.bit_bus_v2.Utils.DataManager;
import com.example.kraken.bit_bus_v2.R;
import com.example.kraken.bit_bus_v2.Utils.DateProvider;

public class NextStaffBusFragment extends Fragment {

    private static final String TAG = "NextStaffBusFragment";

    private TextView tvNextStaffBusTime;
    private DateProvider provider;

    private SharedPreferences preferences;

    private String from, type, day, nextBus;

    public NextStaffBusFragment() {
        // empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_next_staff_bus, container, false);
        tvNextStaffBusTime = v.findViewById(R.id.nextStaffBusTime);

        returnTime();

        tvNextStaffBusTime.setText(String.valueOf(nextBus));
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void returnTime() {
        provider = new DateProvider();
        day = provider.getDay();

        type = getString(R.string.staffbus);

        preferences = getActivity().getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        if (preferences != null) {
            from = ((preferences.getInt("location", 0) == 0) ? getString(R.string.frombit) : getString(R.string.fromrnc));
        } else {
            from = getString(R.string.frombit);
        }

        DataManager manager = new DataManager(getActivity());
        nextBus = manager.getNextBus(from, type, day, Double.parseDouble(provider.getTime()));
    }
}
