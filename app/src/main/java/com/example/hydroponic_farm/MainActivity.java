package com.example.hydroponic_farm;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.hydroponic_farm.databinding.ActivityMainBinding;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
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

       Call<JsonObject> resp= tsb.getLatestTel("Bearer "+token, "de9837b0-bb8b-11ee-8027-c77be3144608");
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

                       TextView value= findViewById(R.id.temperature);
                       JSONArray aux=(JSONArray) telemetry.get("temperature");
                       JSONObject aux2= aux.getJSONObject(0);
                       value.setText(aux2.getInt("value")+" ºC");

                       value= findViewById(R.id.humidity);
                       aux=(JSONArray) telemetry.get("humidity");
                       aux2= aux.getJSONObject(0);
                       value.setText(aux2.getInt("value")+" %");

                       value= findViewById(R.id.ph);
                       aux=(JSONArray) telemetry.get("ph");
                       aux2= aux.getJSONObject(0);
                       value.setText(aux2.getString("value"));

                       value= findViewById(R.id.TDS);
                       aux=(JSONArray) telemetry.get("tds");
                       aux2= aux.getJSONObject(0);
                       value.setText(aux2.getInt("value")+" ppm");

                       value= findViewById(R.id.light);
                       aux=(JSONArray) telemetry.get("light");
                       aux2= aux.getJSONObject(0);
                       value.setText(aux2.getInt("value")+" LUX");

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

    public void showPopup(View view) {
        String tag = view.getTag().toString();

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_layout, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;  // Set your desired width
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;

        PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // Set up EditText and Button
        EditText minEditText = popupView.findViewById(R.id.minEditText);
        EditText maxEditText = popupView.findViewById(R.id.maxEditText);
        Button submitButton = popupView.findViewById(R.id.submitButton);

        // Customize the hint based on the parameter
        String minHint = "Enter Min " + tag;
        String maxHint = "Enter Max " + tag;

        minEditText.setHint(minHint);
        maxEditText.setHint(maxHint);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the values from the EditText fields
                int minValue = Integer.parseInt(minEditText.getText().toString());
                int maxValue = Integer.parseInt(maxEditText.getText().toString());

                // Create a JSON object
                JSONObject jsonThresholds = new JSONObject();

                try {
                    // Put values into the JSON object
                    jsonThresholds.put("tag", tag);
                    jsonThresholds.put("minValue", minValue);
                    jsonThresholds.put("maxValue", maxValue);

                    // Print the JSON object (for demonstration purposes)
                    System.out.println(jsonThresholds.toString());

                    //TODO: You can now use the 'jsonThresholds' object for your specific logic

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // For now, let's just dismiss the popup
                popupWindow.dismiss();
            }
        });
    }





}