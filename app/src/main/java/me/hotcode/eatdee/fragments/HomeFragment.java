package me.hotcode.eatdee.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.Date;

import me.hotcode.eatdee.R;
import me.hotcode.eatdee.models.Profile;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    public TextView goal_calories_textview;
    PieChart calories_pic_chart;
    Profile currentProfile;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("canGetCurrentProfile",""+getArguments().getInt("canGetCurrentProfile"));
        if(getArguments().getInt("canGetCurrentProfile")!=0) {
            currentProfile = (Profile) getArguments().getSerializable("currentProfile");
        }else{
            currentProfile = new Profile(new Date(1996,1,1),170,50,"male", 2);
        }
        int goal_calories = currentProfile.getGoal_calories();

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        goal_calories_textview = (TextView) view.findViewById(R.id.goal_calories_textview);

        goal_calories_textview.setText(""+goal_calories);
        calories_pic_chart = (PieChart) view.findViewById(R.id.calories_pic_chart);
        calories_pic_chart.addPieSlice(new PieModel("Goal calories", goal_calories, Color.parseColor("#FE6DA8")));
        calories_pic_chart.addPieSlice(new PieModel("Remainning calorie", 2000, Color.parseColor("#56B7F1")));
        calories_pic_chart.addPieSlice(new PieModel("Current calorie", 1000, Color.parseColor("#FED70E")));
        calories_pic_chart.startAnimation();
        return view;
    }

}
