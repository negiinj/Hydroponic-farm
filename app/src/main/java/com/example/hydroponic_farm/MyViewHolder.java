package com.example.hydroponic_farm;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hydroponic_farm.ui.notifications.Alarm;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyViewHolder extends RecyclerView.ViewHolder {

        // Define member variables for any views that will be set as you render a row
        TextView alarmType;
        TextView alarmSeverity;
        TextView alarmReason;
        Button acknowledgeButton;
        Button clearButton;
       int red = 128;
       int green = 128;
       int blue = 128;


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
    void bindValues(Alarm a, ThingsBoardService service, String token) {
        // give values to the elements contained in the item view.
        // formats the title's text color depending on the "isSelected" argument.
        alarmType.setText(a.getType());
        alarmSeverity.setText(a.getSeverity());
        alarmReason.setText(a.getReason());
        // Set acknowledge button
        if (a.isAck()) {
            acknowledgeButton.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(red, green, blue)));
            acknowledgeButton.setText("Acknowledged");}
        acknowledgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> call=service.ackAlarm("Bearer "+token,a.getId());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Log.d("ALARM","Alarm acknowledged successfully");
                            acknowledgeButton.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(red, green, blue)));
                            acknowledgeButton.setText("Acknowledged");
                        }else{
                            Log.e("ALARM","Error while acknowledging the alarm");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("ALARM","Connection error while acknowledging the alarm");
                    }
                });
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> call= service.clearAlarm("Bearer "+token,a.getId());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Log.d("ALARM","Alarm cleared successfully");
                            clearButton.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(red, green, blue)));
                            clearButton.setText("Cleared");
                            a.setAck(true);
                        }else{
                            Log.e("ALARM","Error while clearing the alarm");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("ALARM","Connection error while clearing the alarm");
                    }
                });
            }
        });
    }
}