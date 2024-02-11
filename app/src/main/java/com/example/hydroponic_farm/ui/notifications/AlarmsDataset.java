package com.example.hydroponic_farm.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.hydroponic_farm.ServiceGenerator;
import com.example.hydroponic_farm.ThingsBoardService;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Closeable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmsDataset{
    private ArrayList<Alarm> alarmList;
    String token;
    Context context;
    Handler handler;
    ThingsBoardService service;

    public AlarmsDataset(Context context, String token, Handler handler, ThingsBoardService tsb) {
        alarmList=new ArrayList<>();
        this.token=token;
        this.context=context;
        this.handler=handler;
        this.service=tsb;
    }
    public void fill() {

        Call<JsonObject> call = service.getAlarms("Bearer " +token, "de9837b0-bb8b-11ee-8027-c77be3144608" ,100,0, "createdTime", "DESC"); // Get the 10 latest alerts

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JSONObject alarms;
                    Log.d("ALARMS","Got alarms correctly");
                    try {
                        alarms = new JSONObject(response.body().toString());
                        JSONArray data=alarms.getJSONArray("data");
                        int nElements=data.length();
                        JSONObject element;
                        String aux;
                        for (int i=0;i<nElements;i++){
                            element=data.getJSONObject(i);
                            aux=element.getString("status");
                            //If the alarm got is not cleared and ack'ed, store it to show in the app
                            if(!aux.equals("CLEARED_ACK") && !element.getString("name").equals("General Alarm")
                                    && !element.getString("name").equals("{details.alarmDetails.type}")
                                    && !element.getString("name").equals("$[alarmDetails.type]")){
                                Alarm a=new Alarm();
                                switch (aux){
                                    case "ACTIVE_UNACK":
                                        a.setAck(false);
                                        a.setCleared(false);
                                    break;
                                    case "ACTIVE_ACK":
                                        a.setAck(true);
                                        a.setCleared(false);
                                    break;
                                    case "CLEARED_UNACK":
                                        a.setAck(false);
                                        a.setCleared(true);
                                    break;
                                }
                                a.setId(element.getJSONObject("id").getString("id"));
                                //In case the alarm details contain nothing
                                if (!element.getJSONObject("details").toString().equals("{}")){
                                    element=element.getJSONObject("details");
                                    a.setSeverity(element.getString("severity"));
                                    a.setType(element.getString("type"));
                                    a.setReason(element.getString("details"));
                                    alarmList.add(a);
                                }
                            }
                        }
                    } catch (JSONException e) {
                        Log.d("ALARMS","Error while parsing alarms");
                        throw new RuntimeException(e);
                    }
                    Log.d("ALARMS","Parsing correctly done");
                    Message msg = handler.obtainMessage();
                    Bundle msg_data = msg.getData();
                    msg_data.putChar("ok",'k');
                    msg.sendToTarget();
                } else {
                    Log.e("ALARMS", "Could not get the alarms correctly");
                    Toast.makeText(context, "The app REST token may have expired, log in again", Toast.LENGTH_SHORT);
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("ALARMS", t.getMessage());
            }
        });
    }
    public Alarm getAlarmAtPosition(int i){
        return alarmList.get(i);
    }

    public int getLength(){
        return alarmList.size();
    }

    public void clear(){
        this.alarmList.clear();
    }
}