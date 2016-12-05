package me.hotcode.eatdee.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.fatsecret.platform.model.CompactFood;

import java.util.List;

import me.hotcode.eatdee.R;
import me.hotcode.eatdee.adapters.FoodListViewAdapter;
import me.hotcode.eatdee.utils.FoodUtils;

public class ListSearchFoodActivity extends AppCompatActivity {
    String foodName;
    ListView searchFoodListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_search_food);
        Intent intent = getIntent();
        foodName = intent.getStringExtra("foodName");
        FoodUtils foodUtils = new FoodUtils();
        List<CompactFood> list_food = foodUtils.getFoodList(foodName,0);
//        for(int i=0;i<list_food.size();i++){
//            Log.e("food_print", list_food.get(i).getName());
//        }
        searchFoodListView = (ListView) findViewById(R.id.searchFoodListView);
        searchFoodListView.setAdapter(new FoodListViewAdapter(getApplicationContext(), list_food));

    }
}
