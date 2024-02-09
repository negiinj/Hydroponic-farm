package com.example.hydroponic_farm;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

// Define a Retrofit interface for your API
public interface ThingsBoardService {
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("auth/login")
    Call<JsonObject> getUserToken(@Body JsonObject user);
    @Headers({"Accept: application/json"})
    @GET("plugins/telemetry/DEVICE/{device_id}/values/timeseries?keys=humidity,light,ph,RGB_Red,RGB_Blue,RGB_Green,tds,temperature,waterLevel")
    Call<JsonObject> getLatestTel(@Header("X-Authorization") String token, @Path("device_id") String device_id);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("v1/{device_token}/telemetry")
    Call<Void> sendThresholds(@Body JsonObject command, @Header("X-Authoritation") String token,  @Path("device_token") String device_token);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET("alarm/DEVICE/{device_id}")
    Call<JsonObject> getAlarms(@Header("X-Authorization") String token, @Path("device_id") String device_id, @Query("pageSize")int pgSize, @Query("page")int page, @Query("sortProperty")String sortProperty, @Query("sortOrder")String sortOrder);

    @POST("alarm/{alarmId}/clear")
    Call<Void> clearAlarm(@Header("X-Authorization") String token, @Path("alarmId") String alarmid);
}

