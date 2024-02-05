package com.example.hydroponic_farm;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

// Define a Retrofit interface for your API
public interface ThingsBoardService {
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("auth/login")
    Call<JsonObject> getUserToken(@Body JsonObject user);
    @Headers({"Accept: application/json"})
    @GET("plugins/telemetry/DEVICE/{device_id}/values/timeseries?keys=humidity,light,ph,RGB_Red,RGB_Blue,RGB_Green,tds,temperature,waterLevel")
    Call<JsonObject> getLatestTel(@Header("X-Authorization") String token, @Path("device_id") String device_id);
    @Headers({"Accept: text/plain", "Content-Type: application/json"})
    @POST("v1/98pZ8Y2TOyMnghqLcqKr/telemetry")
    Call<Void> sendTel(@Body JsonObject tele, @Path("98pZ8Y2TOyMnghqLcqKr")String device_access_token);

}

