package com.example.kraken.bit_bus_v2.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kraken.bit_bus_v2.Adapters.RVAllBusesAdapter;
import com.example.kraken.bit_bus_v2.DataModels.DayChooser;
import com.example.kraken.bit_bus_v2.R;
import com.example.kraken.bit_bus_v2.Utils.DateProvider;

import java.util.ArrayList;
import java.util.Objects;


public class AllBusFragment extends Fragment {

    private static final String TAG = "AllBusFragment";

    private SharedPreferences preferences;
    private SharedPreferences.Editor preferenceEditor;

    private TabLayout tabLayout;
    private ArrayList<DayChooser> days;
    private RVAllBusesAdapter adapter;

    private TextView tv_title, bitLocationTv, rncLocationTv;
    private Button staffBusBtn, studentBusBtn;

    private String from, type, day;

    public AllBusFragment() {
        // Required empty public constructor
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_all_bus, container, false);

        //noinspection ConstantConditions
        preferences = getActivity().getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        preferenceEditor = preferences.edit();

        tv_title = v.findViewById(R.id.title_tv);
        bitLocationTv = v.findViewById(R.id.bitLocationLabel);
        rncLocationTv = v.findViewById(R.id.rncLocationLabel);
        staffBusBtn = v.findViewById(R.id.toggleStaffBus);
        studentBusBtn = v.findViewById(R.id.toggleStudent);

        setOnClickBtn(staffBusBtn);
        setOnClickBtn(studentBusBtn);

        setOnClickTv(bitLocationTv);
        setOnClickTv(rncLocationTv);

        DateProvider dateProvider = new DateProvider();

        days = new ArrayList<>();
        setupDays();

        day = dateProvider.getDay();
        Log.d(TAG, "onCreateView: " + day);

        if (preferences != null) {
            from = ((preferences.getInt("location", 0) == 0) ? getString(R.string.frombit) : getString(R.string.fromrnc));
            type = ((preferences.getInt("busType", 0) == 0) ? getString(R.string.studentbus) : getString(R.string.staffbus));
        } else {
            from = getString(R.string.frombit);
            type = getString(R.string.studentbus);
        }

        setTitle(day);

        RecyclerView allBusesRV = v.findViewById(R.id.rv_allBuses);
        tabLayout = v.findViewById(R.id.bottomDayChooserTab);
        setTabs(tabLayout);

        setupTablayoutForClicks(tabLayout);
        setupRecyclerView(allBusesRV);

        setTabWithDay();

        putBgBtn();
        putBgTv();

        return v;
    }

    @SuppressWarnings("ConstantConditions")
    private void setTabWithDay() {
        switch (day) {
            case "sunday":
                tabLayout.getTabAt(0).select();
                break;
            case "monday":
                tabLayout.getTabAt(1).select();
                break;
            case "tuesday":
                tabLayout.getTabAt(2).select();
                break;
            case "wednesday":
                tabLayout.getTabAt(3).select();
                break;
            case "thursday":
                tabLayout.getTabAt(4).select();
                break;
            case "friday":
                tabLayout.getTabAt(5).select();
                break;
            case "saturday":
                tabLayout.getTabAt(6).select();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateRecyclerView();
    }

    private void setOnClickTv(TextView view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceEditor.putInt("location", Integer.parseInt(v.getTag().toString()));
                preferenceEditor.commit();
                putBgTv();
            }
        });
    }

    private void putBgTv() {
        int tag = preferences.getInt("location", 0);
        clearTv();
        switch (tag) {
            case 0:
                bitLocationTv.setTextColor(getResources().getColor(R.color.md_pink_400));
                from = getString(R.string.frombit);
                updateRecyclerView();
                break;
            case 1:
                rncLocationTv.setTextColor(getResources().getColor(R.color.md_pink_400));
                from = getString(R.string.fromrnc);
                updateRecyclerView();
                break;
        }
    }

    private void clearTv() {
        bitLocationTv.setTextColor(getResources().getColor(android.R.color.black));
        rncLocationTv.setTextColor(getResources().getColor(android.R.color.black));
    }

    private void setOnClickBtn(Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceEditor.putInt("busType", Integer.parseInt(v.getTag().toString()));
                preferenceEditor.commit();
                putBgBtn();
            }
        });
    }

    private void putBgBtn() {
        int tag = preferences.getInt("busType", 0);
        clearBtn();
        switch (tag) {
            case 0:
                studentBusBtn.setBackground(getResources().getDrawable(R.drawable.rounded_rectangle));
                studentBusBtn.setTextColor(getResources().getColor(android.R.color.white));
                type = getString(R.string.studentbus);
                updateRecyclerView();
                break;
            case 1:
                staffBusBtn.setBackground(getResources().getDrawable(R.drawable.rounded_rectangle));
                staffBusBtn.setTextColor(getResources().getColor(android.R.color.white));
                type = getString(R.string.staffbus);
                updateRecyclerView();
                break;
        }
    }

    private void clearBtn() {
        staffBusBtn.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        staffBusBtn.setTextColor(getResources().getColor(android.R.color.black));
        studentBusBtn.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        studentBusBtn.setTextColor(getResources().getColor(android.R.color.black));
    }

    private void setTitle(String day) {
        tv_title.setText(day);
    }

    private void setupRecyclerView(RecyclerView rv) {
        adapter = new RVAllBusesAdapter(getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv.setAdapter(adapter);
        rv.setLayoutManager(layoutManager);
    }

    private void setupDays() {
        days.add(new DayChooser(getString(R.string.sunday), getString(R.string.nav_sunday)));
        days.add(new DayChooser(getString(R.string.monday), getString(R.string.nav_monday)));
        days.add(new DayChooser(getString(R.string.tuesday), getString(R.string.nav_tuesday)));
        days.add(new DayChooser(getString(R.string.wednesday), getString(R.string.nav_wednesday)));
        days.add(new DayChooser(getString(R.string.thursday), getString(R.string.nav_thursday)));
        days.add(new DayChooser(getString(R.string.friday), getString(R.string.nav_friday)));
        days.add(new DayChooser(getString(R.string.saturday), getString(R.string.nav_saturday)));
    }

    private void setTabs(TabLayout tabLayout) {
        tabLayout.addTab(tabLayout.newTab().setText(days.get(0).getLabel()).setContentDescription(days.get(0).getDay()));
        tabLayout.addTab(tabLayout.newTab().setText(days.get(1).getLabel()).setContentDescription(days.get(1).getDay()));
        tabLayout.addTab(tabLayout.newTab().setText(days.get(2).getLabel()).setContentDescription(days.get(2).getDay()));
        tabLayout.addTab(tabLayout.newTab().setText(days.get(3).getLabel()).setContentDescription(days.get(3).getDay()));
        tabLayout.addTab(tabLayout.newTab().setText(days.get(4).getLabel()).setContentDescription(days.get(4).getDay()));
        tabLayout.addTab(tabLayout.newTab().setText(days.get(5).getLabel()).setContentDescription(days.get(5).getDay()));
        tabLayout.addTab(tabLayout.newTab().setText(days.get(6).getLabel()).setContentDescription(days.get(6).getDay()));
    }

    private void setupTablayoutForClicks(TabLayout tabLayout) {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                day = Objects.requireNonNull(tab.getContentDescription()).toString();
                setTitle(day);
                updateRecyclerView();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void updateRecyclerView() {
        adapter.getData(from, type, day);
        adapter.notifyDataSetChanged();
    }
}
