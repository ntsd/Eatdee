package me.hotcode.eatdee.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import me.hotcode.eatdee.MainActivity;
import me.hotcode.eatdee.R;
import me.hotcode.eatdee.adapters.ListFoodListViewAdapter;
import me.hotcode.eatdee.models.ListFood;

public class RecommendFoodActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView listOfListFoodListView;
    List<ListFood> listOfListFood = new ArrayList<>();
    ListFoodListViewAdapter arrayAdapter;

    //database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = database.getReference();
    public DatabaseReference foodListsRef = rootRef.child("foodlists");

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            setResult(RESULT_OK, data);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        setContentView(R.layout.activity_recommend_food);
        setTitle("Choose Food List For Recommend");
        listOfListFoodListView = (ListView) findViewById(R.id.listFoodListView);
        arrayAdapter = new ListFoodListViewAdapter(this, listOfListFood);
        listOfListFoodListView.setAdapter(arrayAdapter);
        foodListsRef.addValueEventListener(new ValueEventListener() {
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
        listOfListFoodListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent foodListIntent  = new Intent(getApplicationContext(), RecommendFoodListActivity.class);
        foodListIntent.putExtra("listFood", listOfListFood.get(i));
        startActivityForResult(foodListIntent, 1);
    }
}
