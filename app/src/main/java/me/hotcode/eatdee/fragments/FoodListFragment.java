package me.hotcode.eatdee.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.hotcode.eatdee.R;
import me.hotcode.eatdee.activitys.ListSearchFoodActivity;
import me.hotcode.eatdee.adapters.ListFoodListViewAdapter;
import me.hotcode.eatdee.models.ListFood;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodListFragment extends Fragment {
    ListView listFoodListView;
    List<ListFood> listOfListFood;

    public FoodListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food_list, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        listOfListFood = (List<ListFood>) getArguments().getSerializable("listOfListFood");
        listFoodListView = (ListView)view.findViewById(R.id.listFoodListView);
        listFoodListView.setAdapter(new ListFoodListViewAdapter(getContext(), listOfListFood));
        listFoodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listOfListFood.get(i).getListFood();
                Intent intent = new Intent(getContext(), ListSearchFoodActivity.class);
            }
        });
        return view;
    }

}
