package com.example.hydroponic_farm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class MyAdapter extends ArrayAdapter<Alarm> {
    public MyAdapter(Context context, ArrayList<Alarm> alarms) {
        super(context, 0, alarms);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Alarm alarm = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.alarm_item, parent, false);
        }
        TextView alarmType = convertView.findViewById(R.id.Alarm_type);
        TextView alarmSeverity = convertView.findViewById(R.id.Alarm_severity);
        TextView alarmReason = convertView.findViewById(R.id.Alarm_reason);

        alarmType.setText(alarm.getType());
        alarmSeverity.setText(alarm.getSeverity());
        alarmReason.setText(alarm.getReason());

        return convertView;
    }
}