package me.hotcode.eatdee.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.hotcode.eatdee.R;
import me.hotcode.eatdee.models.ListFood;

/**
 * Created by Jiravat on 5/12/2559.
 */

public class ListFoodListViewAdapter extends BaseAdapter{
    List<ListFood>  listOfListFood;
    Context context;

    public ListFoodListViewAdapter(Context context,List<ListFood> listOfListFood) {
        this.listOfListFood = listOfListFood;
        this.context = context;
    }

    public void updateListOfListFood(List<ListFood> listOfListFood2) {
        listOfListFood.clear();
        listOfListFood.addAll(listOfListFood2);
        notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        if(listOfListFood==null)return 0;
        return listOfListFood.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = mInflater.inflate(R.layout.list_list_food_row, null);
        if(view!=null){
            TextView type = (TextView) view.findViewById(R.id.name_list_food);
            type.setText(listOfListFood.get(i).getListName());
            TextView description = (TextView) view.findViewById(R.id.description_list_food);
            description.setText(listOfListFood.get(i).getDescription());
        }
        return view;

    }


}
