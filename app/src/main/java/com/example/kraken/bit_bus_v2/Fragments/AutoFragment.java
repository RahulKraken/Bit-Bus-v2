package com.example.kraken.bit_bus_v2.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kraken.bit_bus_v2.DataModels.AutoData;
import com.example.kraken.bit_bus_v2.R;
import com.example.kraken.bit_bus_v2.Utils.DataManager;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class AutoFragment extends Fragment {

    private static final String TAG = "AutoFragment";

    private TextView autoName, autoNumber;
    private Button nextAutoBtn;
    private FloatingActionButton fabCall;

    private ArrayList<AutoData> autoList;
    private int index;

    public AutoFragment() {
        // Empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_auto, container, false);

        autoList = new ArrayList<>();
        autoName = v.findViewById(R.id.autoName);
        autoNumber = v.findViewById(R.id.autoPhn);
        fabCall = v.findViewById(R.id.fabCall);
        nextAutoBtn = v.findViewById(R.id.nextContactBtn);

        DataManager manager = new DataManager(getActivity());
        autoList = manager.getAutos();

        Log.d(TAG, "onCreateView: " + autoList.size());

        index = 0;

        autoName.setText(autoList.get(index).getName());
        autoNumber.setText(autoList.get(index).getPhone());

        nextAutoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupNextBtn();
            }
        });

        fabCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupFab();
            }
        });

        return v;
    }

    private void setupNextBtn() {
        if (index == autoList.size() - 1) {
            index = 0;
        } else {
            index = index + 1;
        }
        autoName.setText(autoList.get(index).getName());
        autoNumber.setText(autoList.get(index).getPhone());
    }

    private void setupFab() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + autoList.get(index).getPhone()));
        startActivity(intent);
    }
}
