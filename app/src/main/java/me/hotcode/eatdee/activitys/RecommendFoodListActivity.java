package me.hotcode.eatdee.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.hotcode.eatdee.R;
import me.hotcode.eatdee.adapters.FoodListViewAdapter;
import me.hotcode.eatdee.fatsecret.model.CompactFood;
import me.hotcode.eatdee.models.ListFood;

public class RecommendFoodListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView recommendFoodListView;
    List<CompactFood> listCompactFood = new ArrayList<>();
    ListFood listFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_food_list);
        Intent intent = getIntent();
        listFood = (ListFood)intent.getSerializableExtra("listFood");
        listCompactFood= listFood.getListFood();
        Collections.sort(listCompactFood, new Comparator<CompactFood>(){
            @Override
            public int compare(CompactFood compactFood, CompactFood t1) {
                double num1 = (compactFood.getProtein()/(t1.getCalorie()/t1.getPer())*0.6)+(compactFood.getCarbohydrates()/(t1.getCalorie()/t1.getPer())*0.4);
                double num2 = (t1.getProtein()/(t1.getCalorie()/t1.getPer())*0.6)+(t1.getCarbohydrates()/(t1.getCalorie()/t1.getPer())*0.4);
                if(num1 > num2){
                    return -1;
                }if(num1 < num2){
                    return 1;
                }
                return 0;
            }
        });
        recommendFoodListView = (ListView)findViewById(R.id.recommendFoodListView);
        recommendFoodListView.setAdapter(new FoodListViewAdapter(getApplicationContext(),listCompactFood));
        recommendFoodListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("compactFood", listCompactFood.get(i));
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
