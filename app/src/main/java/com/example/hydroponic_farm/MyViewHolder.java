package com.example.hydroponic_farm;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hydroponic_farm.ui.notifications.Alarm;

public class MyViewHolder extends RecyclerView.ViewHolder {

        // Define member variables for any views that will be set as you render a row
        TextView alarmType;
        TextView alarmSeverity;
        TextView alarmReason;
        Button acknowledgeButton;
        Button clearButton;

        // Constructor that accepts the entire item row and does the view lookups
        public MyViewHolder(View itemView) {
            super(itemView);

            // Find each view by ID from the itemView
            alarmType = itemView.findViewById(R.id.Alarm_type);
            alarmSeverity = itemView.findViewById(R.id.Alarm_severity);
            alarmReason = itemView.findViewById(R.id.Alarm_reason);
            acknowledgeButton =itemView.findViewById(R.id.ack_button);
            clearButton= itemView.findViewById(R.id.clear_button);
        }
    void bindValues(Alarm a) {
        // give values to the elements contained in the item view.
        // formats the title's text color depending on the "isSelected" argument.
        alarmType.setText(a.getType());
        alarmSeverity.setText(a.getSeverity());
        alarmReason.setText(a.getReason());
    }
}