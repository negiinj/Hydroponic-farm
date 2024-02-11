package com.example.hydroponic_farm.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hydroponic_farm.MyAdapter;
import com.example.hydroponic_farm.ServiceGenerator;
import com.example.hydroponic_farm.ThingsBoardService;
import com.example.hydroponic_farm.ui.notifications.Alarm;
import com.example.hydroponic_farm.R;
import com.example.hydroponic_farm.databinding.FragmentNotificationsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    AlarmsDataset dataset;
    private RecyclerView alarmsListView;
    private MyAdapter alarmAdapter;
    SharedPreferences sharedPref;
    FloatingActionButton refreshAlarms;
    String token;
    public NotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_notifications, container, false);
        sharedPref = requireContext().getSharedPreferences("DIRECTORY1", Context.MODE_PRIVATE);
        token = sharedPref.getString("token", "");
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                // message received from background thread: load complete (or failure)
                super.handleMessage(msg);
                alarmAdapter.notifyDataSetChanged();
                Log.d("HANDLER", "Dataset length="+dataset.getLength());
            }
        };
        ThingsBoardService service = ServiceGenerator.createService(ThingsBoardService.class);
        dataset = new AlarmsDataset(getContext(),token,handler, service);
        alarmsListView = view.findViewById(R.id.recyclerView);
        alarmAdapter = new MyAdapter(dataset, service, token);
        alarmsListView.setAdapter(alarmAdapter);
        alarmsListView.setItemAnimator(new DefaultItemAnimator());
        alarmsListView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        refreshAlarms = view.findViewById(R.id.floatingActionButtonforAlarms);
        refreshAlarms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataset.clear();
                dataset.fill();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataset.fill();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}