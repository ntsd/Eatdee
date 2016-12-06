package me.hotcode.eatdee.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import me.hotcode.eatdee.R;

public class SearchFoodActivity extends AppCompatActivity implements View.OnClickListener{
    String foodName;
    final int SEARCH_RESULT_CODE = 69;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == SEARCH_RESULT_CODE){
                setResult(RESULT_OK, data);
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food);
        ((Button)findViewById(R.id.foodSearchButton)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        foodName = ((EditText)findViewById(R.id.foodNameEditText)).getText().toString();
        Intent foodListIntent  = new Intent(getApplicationContext(), ListSearchFoodActivity.class);
        foodListIntent.putExtra("foodName", foodName);
        startActivityForResult(foodListIntent, SEARCH_RESULT_CODE);
    }
}
