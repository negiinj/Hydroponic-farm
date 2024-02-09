package com.example.hydroponic_farm;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

        // Define member variables for any views that will be set as you render a row
        TextView alarmType;
        TextView alarmSeverity;
        TextView alarmReason;

        // Constructor that accepts the entire item row and does the view lookups
        public MyViewHolder(View itemView) {
            super(itemView);

            // Find each view by ID from the itemView
            alarmType = itemView.findViewById(R.id.Alarm_type);
            alarmSeverity = itemView.findViewById(R.id.Alarm_severity);
            alarmReason = itemView.findViewById(R.id.Alarm_reason);
        }
}