package me.hotcode.eatdee.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.hotcode.eatdee.R;

/**
 * Created by Jiravat on 7/12/2559.
 */

public class TipsListViewAdapter extends BaseAdapter{
    Context context;
    List<String> tips;
    TextView text;

    public TipsListViewAdapter(Context context, List<String> tips) {
        this.context = context;
        this.tips = tips;
    }

    public void updateTips(List<String> newtips){
        this.tips = newtips;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        if(tips == null)return 0;
        return tips.size();
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
        view = mInflater.inflate(R.layout.tips_row, null);
        if(view!=null){
            TextView tip = (TextView) view.findViewById(R.id.tip);
            tip.setText(tips.get(i));
        }
        return view;
    }
}
