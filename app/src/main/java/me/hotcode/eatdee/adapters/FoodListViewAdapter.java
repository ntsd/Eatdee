package me.hotcode.eatdee.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.hotcode.eatdee.R;
import me.hotcode.eatdee.fatsecret.model.CompactFood;
import me.hotcode.eatdee.models.ListFood;

/**
 * Created by Jiravat on 26/10/2559.
 */

public class FoodListViewAdapter extends BaseAdapter {
    List<CompactFood> listCompactFood;
    Context context;
    ViewHolder holder;

    public FoodListViewAdapter(Context context, List<CompactFood> listCompactFood) {
        this.context = context;
        this.listCompactFood = listCompactFood;
    }

    private class ViewHolder {
        ImageView imageView;
    }

    @Override
    public int getCount() {
        if(listCompactFood == null){
            return 0;
        }
        return listCompactFood.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = mInflater.inflate(R.layout.list_food_row, null);
//            holder = new ViewHolder();
//            holder.imageView = (ImageView) view.findViewById(R.id.image_list_food);
            //view.setTag(holder);
            TextView name = (TextView) view.findViewById(R.id.name_food);
            name.setText(listCompactFood.get(i).getName());
//            TextView cal = (TextView) view.findViewById(R.id.brandName_list_food);
//            cal.setText(listCompactFood.get(i).getBrandName());
            TextView type = (TextView) view.findViewById(R.id.type_food);
            type.setText(listCompactFood.get(i).getType());
            TextView cal = (TextView) view.findViewById(R.id.cal_food);
            type.setText(""+listCompactFood.get(i).getCalorie());
        } else {
//            holder = (ViewHolder) view.getTag();
        }
        return view;
    }
}
