package com.example.kraken.bit_bus_v2.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kraken.bit_bus_v2.R;

public class BusFragment extends Fragment {

    private static final String TAG = "BusFragment";

    private ViewPager busPager;
    private FragmentPagerAdapter adapter;

    public BusFragment() {
        // empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bus, container, false);

        busPager = v.findViewById(R.id.busViewPager);
        adapter = new MyPagerAdapter(getChildFragmentManager());
        busPager.setAdapter(adapter);
        busPager.setCurrentItem(1);

        return v;
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new AllBusFragment();
                case 1:
                    return new NextBusFragment();
            }
            Toast.makeText(getActivity(), "error getting fragments", Toast.LENGTH_SHORT).show();
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
