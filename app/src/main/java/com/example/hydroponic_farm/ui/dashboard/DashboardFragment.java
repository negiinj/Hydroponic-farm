package com.example.hydroponic_farm.ui.dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hydroponic_farm.R;
import com.example.hydroponic_farm.ServiceGenerator;
import com.example.hydroponic_farm.ThingsBoardService;
import com.example.hydroponic_farm.databinding.FragmentDashboardBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    String token;
    SharedPreferences sharedPref;
    ThingsBoardService tsb;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // Fetch telemetry data

        sharedPref = requireContext().getSharedPreferences("DIRECTORY1", Context.MODE_PRIVATE);
        token = sharedPref.getString("token", "");

        tsb = ServiceGenerator.createService(ThingsBoardService.class);
        FloatingActionButton refresh= root.findViewById(R.id.floatingActionButton);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeDashboard();
            }
        });
        writeDashboard();
/*        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void writeDashboard(){
        Call<JsonObject> resp = tsb.getLatestTel("Bearer " + token, "de9837b0-bb8b-11ee-8027-c77be3144608");
        resp.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject telemetry = new JSONObject(response.body().toString());

                        TextView temperatureTextView = getView().findViewById(R.id.temperature);
                        JSONArray temperatureArray = telemetry.getJSONArray("temperature");
                        JSONObject temperatureObject = temperatureArray.getJSONObject(0);
                        temperatureTextView.setText(temperatureObject.getInt("value") + " ºC");

                        // Update humidity
                        TextView humidityTextView = getView().findViewById(R.id.humidity);
                        JSONArray humidityArray = telemetry.getJSONArray("humidity");
                        JSONObject humidityObject = humidityArray.getJSONObject(0);
                        humidityTextView.setText(humidityObject.getInt("value") + " %");

                        // Update pH
                        TextView pHTextView = getView().findViewById(R.id.ph);
                        JSONArray pHArray = telemetry.getJSONArray("ph");
                        JSONObject pHObject = pHArray.getJSONObject(0);
                        pHTextView.setText(pHObject.getString("value"));

                        // Update TDS
                        TextView TDSTextView = getView().findViewById(R.id.TDS);
                        JSONArray TDSArray = telemetry.getJSONArray("tds");
                        JSONObject TDSObject = TDSArray.getJSONObject(0);
                        TDSTextView.setText(TDSObject.getInt("value") + " ppm");

                        // Update light
                        TextView lightTextView = getView().findViewById(R.id.light);
                        JSONArray lightArray = telemetry.getJSONArray("light");
                        JSONObject lightObject = lightArray.getJSONObject(0);
                        lightTextView.setText(lightObject.getInt("value") + " LUX");

                    } catch (Exception e) {
                        Log.d("TELEMETRIES", "Error while parsing the latest telemetry");
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(requireContext(), "Error while getting the telemetry", Toast.LENGTH_SHORT).show();
            }
        });

    }
}