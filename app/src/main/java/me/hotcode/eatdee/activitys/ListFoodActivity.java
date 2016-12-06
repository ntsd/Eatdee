package me.hotcode.eatdee.activitys;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import me.hotcode.eatdee.R;
import me.hotcode.eatdee.adapters.FoodListViewAdapter;
import me.hotcode.eatdee.adapters.ListFoodListViewAdapter;
import me.hotcode.eatdee.fatsecret.model.CompactFood;
import me.hotcode.eatdee.models.ListFood;

public class ListFoodActivity extends AppCompatActivity implements View.OnClickListener{
    final int SEARCH_RESULT_CODE = 69;
    EditText nameEditText;
    EditText descriptionEditText;
    ListView listFoodListView;
    Button saveButton;
    FloatingActionButton addButton;
    List<CompactFood> listCompactFood = new ArrayList<>();
    ListFood listFood = new ListFood();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if (requestCode == SEARCH_RESULT_CODE) {
                if(listCompactFood == null){
                    listCompactFood = new ArrayList<>();
                }
                listCompactFood.add((CompactFood)data.getSerializableExtra("compactFood"));
                listFoodListView.setAdapter(new FoodListViewAdapter(getApplicationContext(), listCompactFood));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        nameEditText = (EditText) findViewById(R.id.nameEditText);

        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);

        listFoodListView = (ListView) findViewById(R.id.listFoodListView);

        Intent intent = getIntent();
        if(intent.getSerializableExtra("listFood")!=null){
            listFood = (ListFood)intent.getSerializableExtra("listFood");
            listCompactFood = listFood.getListFood();
            descriptionEditText.setText(listFood.getDescription());
            nameEditText.setText(listFood.getListName());
        }


        listFoodListView.setAdapter(new FoodListViewAdapter(getApplicationContext(), listCompactFood));

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);

        addButton = (FloatingActionButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.saveButton)){
            Intent resultIntent = new Intent();
            listFood.setListName(nameEditText.getText().toString());
            listFood.setDescription(descriptionEditText.getText().toString());
            listFood.setListFood(listCompactFood);
            resultIntent.putExtra("listFood", listFood);
            setResult(RESULT_OK, resultIntent);
            onBackPressed();
        }if(view == findViewById(R.id.addButton)){
            Intent searchIntent = new Intent(getApplicationContext(), SearchFoodActivity.class);
            startActivityForResult(searchIntent, SEARCH_RESULT_CODE);
        }
    }
}
