package com.example.hydroponic_farm;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.hydroponic_farm.databinding.ActivityMainBinding;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        String token;
        SharedPreferences sharedPref = getSharedPreferences("DIRECTORY1", Context.MODE_PRIVATE);
        token=sharedPref.getString("token", "");

        ThingsBoardService tsb = ServiceGenerator.createService(ThingsBoardService.class);

       Call<JsonObject> resp= tsb.getLatestTel(token, "de9837b0-bb8b-11ee-8027-c77be3144608");
       resp.enqueue(new Callback<JsonObject>() {
           @Override
           public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
               if(response.isSuccessful()){
                   try{
                       JSONObject telemetry=new JSONObject(response.body().toString());
                        /*JSON example
                        {
                           "temperature": 24.5,
                           "humidity": 48,
                           "light": 35000,
                           "ph": 6.5,
                           "tds": 1500,
                           "waterLevel":15,
                           "RGB_Red":255,
                           "RGB_Green":25,
                            "RGB_Blue":29
                        }
                        * */
                   }catch (Exception e){
                       Log.d("TELEMETRIES", "Error while parsing the latest telemetry");
                   }
               }
           }

           @Override
           public void onFailure(Call<JsonObject> call, Throwable t) {
               Toast.makeText(MainActivity.this,"Error while getting the telemetry",(short)3);
           }
       });

    }



}