package me.hotcode.eatdee.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.hotcode.eatdee.MainActivity;
import me.hotcode.eatdee.R;
import me.hotcode.eatdee.activitys.ListFoodActivity;
import me.hotcode.eatdee.activitys.ListSearchFoodActivity;
import me.hotcode.eatdee.adapters.ListFoodListViewAdapter;
import me.hotcode.eatdee.models.ListFood;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodListFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener{
    ListView listFoodListView;
    List<ListFood> listOfListFood = new ArrayList<>();
    final int ADD_LISTFOOD_RESULT_CODE = 55;
    final int EDIT_LISTFOOD_RESULT_CODE = 66;
    ListFoodListViewAdapter arrayAdapter;
    View view;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == ADD_LISTFOOD_RESULT_CODE){
               // Log.d("ADD_LISTFOOD_RESULT","ADD_LISTFOOD_RESULT");
                ListFood listFood = (ListFood) data.getSerializableExtra("listFood");
                listFood.setId(listOfListFood.size());
                ((MainActivity)getActivity()).editFoodList(""+listFood.getId(), listFood);
//                listOfListFood.add(listFood);
//                arrayAdapter.updateListOfListFood(listOfListFood);
//                arrayAdapter.notifyDataSetChanged();


            }
            if(requestCode == EDIT_LISTFOOD_RESULT_CODE){
                // Log.d("ADD_LISTFOOD_RESULT","ADD_LISTFOOD_RESULT");
                ListFood listFood = (ListFood) data.getSerializableExtra("listFood");
                ((MainActivity)getActivity()).editFoodList(""+listFood.getId(),listFood);
//                listOfListFood.set(listFood.getId(), listFood);
//                arrayAdapter.updateListOfListFood(listOfListFood);
//                arrayAdapter.notifyDataSetChanged();
            }
        }
    }

    public FoodListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_food_list, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addListFoodIntent = new Intent(getContext(), ListFoodActivity.class);
                startActivityForResult(addListFoodIntent, ADD_LISTFOOD_RESULT_CODE);
            }
        });
        listOfListFood = (List<ListFood>) getArguments().getSerializable("listOfListFood");
        //Log.d("listOfListFood", ""+listOfListFood.size());
        arrayAdapter = new ListFoodListViewAdapter(getContext(), listOfListFood);

        listFoodListView = (ListView)view.findViewById(R.id.listFoodListView);
        listFoodListView.setAdapter(arrayAdapter);
        listFoodListView.setOnItemClickListener(this);



        ((MainActivity)getActivity()).foodListsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listOfListFood = new ArrayList<ListFood>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ListFood listFood = postSnapshot.getValue(ListFood.class);
                    listOfListFood.add(listFood);
                }
                arrayAdapter.updateListOfListFood(listOfListFood);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent editListFoodIntent = new Intent(getContext(), ListFoodActivity.class);
        editListFoodIntent.putExtra("listFood", listOfListFood.get(i));
        startActivityForResult(editListFoodIntent, EDIT_LISTFOOD_RESULT_CODE);
    }
}
