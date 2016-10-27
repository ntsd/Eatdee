package me.hotcode.eatdee.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import me.hotcode.eatdee.R;
import me.hotcode.eatdee.models.ListFood;

/**
 * Created by Jiravat on 26/10/2559.
 */

public class FoodListViewAdapter extends BaseAdapter {
    ListFood[] listFoodsArray;
    Context context;
    ViewHolder holder;

    private class ViewHolder {
        ImageView imageView;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return listFoodsArray.length;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = mInflater.inflate(R.layout.list_food_row, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) view.findViewById(R.id.image_list_food);
            view.setTag(holder);
            TextView name = (TextView) view.findViewById(R.id.name_list_food);
            name.setText(listFoodsArray[i].getListName());
        } else {
            holder = (ViewHolder) view.getTag();
        }
        return view;
    }
}
