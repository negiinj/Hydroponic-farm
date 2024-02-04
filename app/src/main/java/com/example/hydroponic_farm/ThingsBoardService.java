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
    @GET("plugins/telemetry/DEVICE/de9837b0-bb8b-11ee-8027-c77be3144608/values/timeseries?keys=humidity,light,ph,RGB_red,RGB_blue,RGB_green,tds,temperature,waterLevel")
    Call<JsonObject> getLatestTel(@Header("X-Authorization") String token, @Path("de9837b0-bb8b-11ee-8027-c77be3144608") String device_id);
    @Headers({"Accept: text/plain", "Content-Type: application/json"})
    @POST("v1/98pZ8Y2TOyMnghqLcqKr/telemetry")
    Call<Void> sendTel(@Body JsonObject tele, @Path("98pZ8Y2TOyMnghqLcqKr")String device_access_token);
}

