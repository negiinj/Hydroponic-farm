package com.example.hydroponic_farm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hydroponic_farm.ui.notifications.Alarm;
import com.example.hydroponic_farm.ui.notifications.AlarmsDataset;

import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    AlarmsDataset dataset;
    public MyAdapter(AlarmsDataset dataset){
        super();
        this.dataset=dataset;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Alarm alarm = dataset.getAlarmAtPosition(position);
        holder.bindValues(alarm);
    }

    @Override
    public int getItemCount() {
        return dataset.getLength();
    }
}