package me.hotcode.eatdee.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.hotcode.eatdee.MainActivity;
import me.hotcode.eatdee.R;
import me.hotcode.eatdee.activitys.RecommendFoodActivity;
import me.hotcode.eatdee.activitys.SearchFoodActivity;
import me.hotcode.eatdee.adapters.FoodListViewAdapter;
import me.hotcode.eatdee.adapters.ListFoodListViewAdapter;
import me.hotcode.eatdee.adapters.TipsListViewAdapter;
import me.hotcode.eatdee.fatsecret.model.CompactFood;
import me.hotcode.eatdee.models.Diary;
import me.hotcode.eatdee.models.ListFood;
import me.hotcode.eatdee.models.Profile;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment{
    public TextView goal_calories_textview;
    PieChart calories_pic_chart;
    PieChart pro_pic_chart;
    PieChart fat_pic_chart;
    PieChart carb_pic_chart;


    final int breakfast_ADD_RESULT_CODE =11;
    final int lunch_ADD_RESULT_CODE = 22;
    final int dinner_ADD_RESULT_CODE = 33;

    final int breakfast_RECOMMEND_RESULT_CODE =44;
    final int lunch_RECOMMEND_RESULT_CODE = 55;
    final int dinner_RECOMMEND_RESULT_CODE = 66;

    ListView tipsListView;
    TipsListViewAdapter tipAdapter;
    List<String> tips = new ArrayList<>();

    //layout
    ListView breakfastFoodListView;
    Button breakfastRecommendButton;
    Button breakfastAddButton;
    FoodListViewAdapter breakfastArrayAdapter;

    ListView lunchFoodListView;
    Button lunchRecommendButton;
    Button lunchAddButton;
    FoodListViewAdapter lunchArrayAdapter;

    ListView dinnerFoodListView;
    Button dinnerRecommendButton;
    Button dinnerAddButton;
    FoodListViewAdapter dinnerArrayAdapter;

    //diary
    DatabaseReference currentProfileRef;
    Profile currentProfile;
    Diary diary;
    List<CompactFood> FoodsBreakfast = new ArrayList<>();
    List<CompactFood> FoodsLunch = new ArrayList<>();
    List<CompactFood> FoodsDinner =  new ArrayList<>();

    public int rdiProtein = 50;
    public int rdiFat = 65;
    public int rdiCarb = 300;
    int goal_calories;

    public int curProtein = 0;
    public int curFat = 0;
    public int curCarb = 0;
    int currentCalories = 0;


    public HomeFragment() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == breakfast_ADD_RESULT_CODE){
                if(diary.getFoodsBreakfast() == null){
                    List<CompactFood> food = new ArrayList<CompactFood>();
                    food.add((CompactFood)data.getSerializableExtra("compactFood"));
                    diary.setFoodsBreakfast(food);
                }
                else{
                    diary.getFoodsBreakfast().add((CompactFood)data.getSerializableExtra("compactFood"));
                }
                currentProfile.setDiary(diary);
                currentProfileRef.setValue(currentProfile);
            }
            else if(requestCode == lunch_ADD_RESULT_CODE){
                if(diary.getFoodsLunch() == null){
                    List<CompactFood> food = new ArrayList<CompactFood>();
                    food.add((CompactFood)data.getSerializableExtra("compactFood"));
                    diary.setFoodsLunch(food);
                }
                else{
                    diary.getFoodsLunch().add((CompactFood)data.getSerializableExtra("compactFood"));
                }
                currentProfile.setDiary(diary);
                currentProfileRef.setValue(currentProfile);
            }
            else if(requestCode == dinner_ADD_RESULT_CODE){
                if(diary.getFoodsDinner() == null){
                    List<CompactFood> food = new ArrayList<CompactFood>();
                    food.add((CompactFood)data.getSerializableExtra("compactFood"));
                    diary.setFoodsDinner(food);
                }
                else{
                    diary.getFoodsDinner().add((CompactFood)data.getSerializableExtra("compactFood"));
                }
                currentProfile.setDiary(diary);
                currentProfileRef.setValue(currentProfile);
            }
            else if(requestCode == breakfast_RECOMMEND_RESULT_CODE){
                if(diary.getFoodsBreakfast() == null){
                    List<CompactFood> food = new ArrayList<CompactFood>();
                    food.add((CompactFood)data.getSerializableExtra("compactFood"));
                    diary.setFoodsBreakfast(food);
                }
                else{
                    diary.getFoodsBreakfast().add((CompactFood)data.getSerializableExtra("compactFood"));
                }
                currentProfile.setDiary(diary);
                currentProfileRef.setValue(currentProfile);
            }
            else if(requestCode == lunch_RECOMMEND_RESULT_CODE){
                if(diary.getFoodsLunch() == null){
                    List<CompactFood> food = new ArrayList<CompactFood>();
                    food.add((CompactFood)data.getSerializableExtra("compactFood"));
                    diary.setFoodsLunch(food);
                }
                else{
                    diary.getFoodsLunch().add((CompactFood)data.getSerializableExtra("compactFood"));
                }
                currentProfile.setDiary(diary);
                currentProfileRef.setValue(currentProfile);
            }
            else if(requestCode == dinner_RECOMMEND_RESULT_CODE){
                if(diary.getFoodsDinner() == null){
                    List<CompactFood> food = new ArrayList<CompactFood>();
                    food.add((CompactFood)data.getSerializableExtra("compactFood"));
                    diary.setFoodsDinner(food);
                }
                else{
                    diary.getFoodsDinner().add((CompactFood)data.getSerializableExtra("compactFood"));
                }
                currentProfile.setDiary(diary);
                currentProfileRef.setValue(currentProfile);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //Log.d("canGetCurrentProfile",""+getArguments().getInt("canGetCurrentProfile"));
        if(getArguments().getInt("canGetCurrentProfile")!=0) {
            currentProfile = (Profile) getArguments().getSerializable("currentProfile");
        }else{
            currentProfile = new Profile(new Date(1996,1,1),170,50,"male", 2);
        }
        goal_calories = currentProfile.getGoal_calories();

        tipsListView= (ListView)view.findViewById(R.id.tipsListView);
        tipAdapter = new TipsListViewAdapter(getContext(), tips);
        tipsListView.setAdapter(tipAdapter);

        //Food Layout
        breakfastFoodListView = (ListView)view.findViewById(R.id.breakfastFoodListView) ;
        breakfastArrayAdapter = new FoodListViewAdapter(getContext(), FoodsBreakfast);
        breakfastFoodListView.setAdapter(breakfastArrayAdapter);

        breakfastRecommendButton = (Button)view.findViewById(R.id.breakfastRecommendButton);
        breakfastRecommendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addFood = new Intent(getContext(), RecommendFoodActivity.class);
                startActivityForResult(addFood, breakfast_RECOMMEND_RESULT_CODE);
            }
        });
        breakfastAddButton = (Button)view.findViewById(R.id.breakfastAddButton);
        breakfastAddButton.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View view) {
                                                      Intent addFood = new Intent(getContext(), SearchFoodActivity.class);
                                                      startActivityForResult(addFood, breakfast_ADD_RESULT_CODE);
                                                  }
                                              }
        );

        lunchFoodListView  = (ListView)view.findViewById(R.id.lunchFoodListView) ;
        lunchArrayAdapter = new FoodListViewAdapter(getContext(), FoodsLunch);
        lunchFoodListView.setAdapter(lunchArrayAdapter);

        lunchRecommendButton = (Button)view.findViewById(R.id.lunchRecommendButton);
        lunchRecommendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addFood = new Intent(getContext(), RecommendFoodActivity.class);
                startActivityForResult(addFood, lunch_RECOMMEND_RESULT_CODE);
            }
        });
        lunchAddButton = (Button)view.findViewById(R.id.lunchAddButton);
        lunchAddButton.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view) {
                                                  Intent addFood = new Intent(getContext(), SearchFoodActivity.class);
                                                  startActivityForResult(addFood, lunch_ADD_RESULT_CODE);
                                              }
                                          }
        );

        dinnerFoodListView  = (ListView)view.findViewById(R.id.dinnerFoodListView) ;
        dinnerArrayAdapter = new FoodListViewAdapter(getContext(), FoodsDinner);
        dinnerFoodListView.setAdapter(dinnerArrayAdapter);

        dinnerRecommendButton = (Button)view.findViewById(R.id.dinnerRecommendButton);
        dinnerRecommendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addFood = new Intent(getContext(), RecommendFoodActivity.class);
                startActivityForResult(addFood, dinner_RECOMMEND_RESULT_CODE);
            }
        });

        dinnerAddButton = (Button)view.findViewById(R.id.dinnerAddButton);
        dinnerAddButton.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   Intent addFood = new Intent(getContext(), SearchFoodActivity.class);
                                                   startActivityForResult(addFood, dinner_ADD_RESULT_CODE);
                                               }
                                           }
        );

        currentProfileRef = ((MainActivity)getActivity()).profilesRef.child(((MainActivity)getActivity()).auth.getCurrentUser().getUid());
        currentProfileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentProfile = dataSnapshot.getValue(Profile.class);
                diary = currentProfile.getDiary();
                if(diary == null){
                    Log.d("diary==null","Ture");
                    diary = new Diary();
                    diary.setDate(new Date());
                    diary.setCurrentCal(0);
                    diary.setCurrentCarb(0);
                    diary.setCurrentProtein(0);
                    diary.setCurrentFat(0);
                    diary.setFoodsBreakfast(new ArrayList<CompactFood>());
                    diary.setFoodsLunch(new ArrayList<CompactFood>());
                    diary.setFoodsDinner(new ArrayList<CompactFood>());
                    currentProfile.setDiary(diary);
                    currentProfileRef.setValue(currentProfile);
                }
                FoodsBreakfast = diary.getFoodsBreakfast();
                FoodsLunch = diary.getFoodsLunch();
                FoodsDinner = diary.getFoodsDinner();

                breakfastArrayAdapter.updateFood(FoodsBreakfast);
                lunchArrayAdapter.updateFood(FoodsLunch);
                dinnerArrayAdapter.updateFood(FoodsDinner);

                curProtein = 0;
                curFat = 0;
                curCarb = 0;
                currentCalories = 0;
                if (FoodsBreakfast == null){
                    FoodsBreakfast = new ArrayList<CompactFood>();
                }
                if (FoodsLunch == null){
                    FoodsLunch = new ArrayList<CompactFood>();
                }
                if (FoodsDinner == null){
                    FoodsDinner = new ArrayList<CompactFood>();
                }
                for(int i=0;i<FoodsBreakfast.size();i++){
                    curProtein += FoodsBreakfast.get(i).getProtein();
                    curCarb += FoodsBreakfast.get(i).getCarbohydrates();
                    curFat += FoodsBreakfast.get(i).getFat();
                    currentCalories += FoodsBreakfast.get(i).getCalorie();
                }
                for(int i=0;i<FoodsLunch.size();i++){
                    curProtein += FoodsLunch.get(i).getProtein();
                    curCarb += FoodsLunch.get(i).getCarbohydrates();
                    curFat += FoodsLunch.get(i).getFat();
                    currentCalories += FoodsLunch.get(i).getCalorie();
                }
                for(int i=0;i<FoodsDinner.size();i++){
                    curProtein += FoodsDinner.get(i).getProtein();
                    curCarb += FoodsDinner.get(i).getCarbohydrates();
                    curFat += FoodsDinner.get(i).getFat();
                    currentCalories += FoodsDinner.get(i).getCalorie();
                }

                tips.clear();;
                if(currentCalories < goal_calories*0.8){
                    tips.add("ควรรับประทานอาหารในแต่ละมื้อให้เพียงพอและครบ 5 หมู่ในแต่ละวัน");
                    //print("ควรรับประทานอาหารในแต่ละมื้อให้เพียงพอและครบ 5 หมู่ในแต่ละวัน");
                    if(curFat > rdiFat){
                        tips.add("ได้รับไขมันจนเกินความจำเป็นแล้ว ควรลดลง เพราะอาจทำให้อ้วนได้");
                      //  print("ได้รับไขมันจนเกินความจำเป็นแล้ว ควรลดลง เพราะอาจทำให้อ้วนได้");
                    }
                    if(curCarb > rdiCarb){
                        tips.add("ถ้าได้รับคาร์โบไฮเดรตที่เกินความจำเป็น ส่วนเกินจะถูกเปลี่ยนไปเป็นไขมันและทำให้อ้วนได้");
                      //  print("ถ้าได้รับคาร์โบไฮเดรตที่เกินความจำเป็น ส่วนเกินจะถูกเปลี่ยนไปเป็นไขมันและทำให้อ้วนได้");
                    }
                    if(curProtein > rdiProtein){
                        tips.add("โปรตีนนั้นก็ให้พลังงานที่สูงเทียบเท่ากับคาร์โปไฮเดรต การบริโภคมากเกินไปก็อาจทำให้อ้วนได้");
                     //   print("โปรตีนนั้นก็ให้พลังงานที่สูงเทียบเท่ากับคาร์โปไฮเดรต การบริโภคมากเกินไปก็อาจทำให้อ้วนได้");
                    }
                }
                else if(currentCalories < goal_calories){
                    tips.add("การรับประทานอาหารไม่ควรทานตามใจ ทานเมื่อจำเป็นจะดีที่สุด");
                   // print("การรับประทานอาหารไม่ควรทานตามใจ ทานเมื่อจำเป็นจะดีที่สุด");
                }
                else if(currentCalories > goal_calories){
                    tips.add("ควรมีการควบคุมการบริโภค ไม่ให้ทานมากเกินไป เนื่องจากจะทำให้อ้วนได้");
                   // print("ควรมีการควบคุมการบริโภค ไม่ให้ทานมากเกินไป เนื่องจากจะทำให้อ้วนได้");
                }
                tipAdapter.updateTips(tips);


                calories_pic_chart.clearChart();
                calories_pic_chart.addPieSlice(new PieModel("Remaining Calories", goal_calories-currentCalories, Color.parseColor("#56B7F1")));
                calories_pic_chart.addPieSlice(new PieModel("Current Calories", currentCalories, Color.parseColor("#FED70E")));
                calories_pic_chart.startAnimation();

                fat_pic_chart.clearChart();
                fat_pic_chart.addPieSlice(new PieModel("Remaining Fat", rdiFat-curFat, Color.parseColor("#56B7F1")));
                fat_pic_chart.addPieSlice(new PieModel("Current Fat", curFat, Color.parseColor("#FED70E")));
                fat_pic_chart.startAnimation();

                pro_pic_chart.addPieSlice(new PieModel("Remaining Protein", rdiProtein-curProtein, Color.parseColor("#56B7F1")));
                pro_pic_chart.addPieSlice(new PieModel("Current Protein", curProtein, Color.parseColor("#FED70E")));
                pro_pic_chart.startAnimation();

                carb_pic_chart.addPieSlice(new PieModel("Remaining Carb", rdiCarb-curCarb, Color.parseColor("#56B7F1")));
                carb_pic_chart.addPieSlice(new PieModel("Current Carb", curCarb, Color.parseColor("#FED70E")));
                carb_pic_chart.startAnimation();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






        //Statistic Layout
        goal_calories_textview = (TextView) view.findViewById(R.id.goal_calories_textview);

        goal_calories_textview.setText(""+goal_calories);

        pro_pic_chart = (PieChart) view.findViewById(R.id.pro_pic_chart);
        carb_pic_chart = (PieChart) view.findViewById(R.id.carb_pic_chart);
        fat_pic_chart = (PieChart) view.findViewById(R.id.fat_pic_chart);

        calories_pic_chart = (PieChart) view.findViewById(R.id.calories_pic_chart);
        //calories_pic_chart.addPieSlice(new PieModel("Goal calories", goal_calories, Color.parseColor("#FE6DA8")));
        calories_pic_chart.addPieSlice(new PieModel("Remaining calorie", goal_calories-currentCalories, Color.parseColor("#56B7F1")));
        calories_pic_chart.addPieSlice(new PieModel("Current calorie", currentCalories, Color.parseColor("#FED70E")));
        calories_pic_chart.startAnimation();


        return view;
    }

}
