package com.example.kraken.bit_bus_v2.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kraken.bit_bus_v2.Utils.DataManager;
import com.example.kraken.bit_bus_v2.R;
import com.example.kraken.bit_bus_v2.Utils.DateProvider;

public class NextStudentBusFragment extends Fragment {

    private static final String TAG = "NextStudentBusFragment";

    private TextView tvNextStudentBus;
    private DateProvider provider;
    private SharedPreferences preferences;

    private String from, type, day, nextBus;


    public NextStudentBusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_next_student_bus, container, false);
        tvNextStudentBus = v.findViewById(R.id.nextStudentBusTime);

        returnTime();
        tvNextStudentBus.setText(String.valueOf(nextBus));

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
//        returnTime();
//        tvNextStudentBus.setText(String.valueOf(nextBus));
    }

    @Override
    public void onResume() {
        super.onResume();
//        returnTime();
//        tvNextStudentBus.setText(String.valueOf(nextBus));
    }

    private void returnTime() {
        provider = new DateProvider();
        day = provider.getDay();

        type = getString(R.string.studentbus);

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
