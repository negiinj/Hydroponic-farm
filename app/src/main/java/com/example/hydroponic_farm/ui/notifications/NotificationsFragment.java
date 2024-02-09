package com.example.hydroponic_farm.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hydroponic_farm.MyAdapter;
import com.example.hydroponic_farm.ui.notifications.Alarm;
import com.example.hydroponic_farm.R;
import com.example.hydroponic_farm.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    AlarmsDataset dataset;

    private ListView alarmsListView;
    private MyAdapter alarmAdapter;
    SharedPreferences sharedPref;
    String token;
    private ArrayList<Alarm> alarmList = new ArrayList<>();

    public NotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_notifications, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPref = requireContext().getSharedPreferences("DIRECTORY1", Context.MODE_PRIVATE);
        token = sharedPref.getString("token", "");
        dataset = new AlarmsDataset(getContext(),token);
        dataset.fill();

        alarmsListView = view.findViewById(R.id.alarmsListView);
        MyAdapter a = new MyAdapter(getContext(), alarmList);
        alarmsListView.setAdapter(a);
        a.notifyDataSetChanged();

        //fetchAlarms(); // You'll implement this method to fetch alarms from ThingsBoard
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}