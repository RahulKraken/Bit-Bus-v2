package com.example.kraken.bit_bus_v2.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kraken.bit_bus_v2.Utils.DataManager;
import com.example.kraken.bit_bus_v2.DataModels.BusData;
import com.example.kraken.bit_bus_v2.R;
import com.example.kraken.bit_bus_v2.Utils.StringManipulation;

import java.util.ArrayList;

public class RVAllBusesAdapter extends RecyclerView.Adapter<RVAllBusesAdapter.AllBusViewHolder> {

    private static final String TAG = "RVAllBusesAdapter";

    private Context context;
    private LayoutInflater inflater;

    private ArrayList<BusData> buses;

    public RVAllBusesAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        buses = new ArrayList<>();
    }

    @NonNull
    @Override
    public AllBusViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.rv_all_buses_item, viewGroup, false);
        return new AllBusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllBusViewHolder allBusViewHolder, int i) {
        String time = StringManipulation.formatString(String.valueOf(buses.get(i).getTime()).replace(".", ":"));
        allBusViewHolder.tv_item.setText(time);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: buses length - " + buses.size());
        return buses.size();
    }

    /*
    =========================================Other Methods==========================================
     */

    public void getData(String from, String type, String day) {
        DataManager manager = new DataManager(context);
        buses = manager.getBusSchedule(from, type, day);
    }

    /*
    =============================================ViewHolder=========================================
     */

    public class AllBusViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_item;

        public AllBusViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_item = itemView.findViewById(R.id.tv_rvAllBusesItem);
        }
    }
}
