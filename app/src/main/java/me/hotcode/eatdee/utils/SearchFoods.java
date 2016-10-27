package me.hotcode.eatdee.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.fatsecret.platform.model.CompactFood;
import com.fatsecret.platform.services.FoodService;
import com.fatsecret.platform.services.Response;

import java.util.List;

/**
 * Created by Jiravat on 27/10/2559.
 */

public class SearchFoods extends AsyncTask<Void, Void, List<CompactFood>> {
    List<CompactFood> list_food;
    String query;
    int page;
    String food;

    public SearchFoods() {

    }

    public SearchFoods(String query, int page) {
        this.query = query;
        this.page = page;
        this.execute();
    }

    public List<CompactFood> getList_food() {
        return list_food;
    }

    public void setList_food(List<CompactFood> list_food) {
        this.list_food = list_food;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    protected List<CompactFood> doInBackground(Void... params) {
        try {
            String key = "458967deb4cf44579640437b60db5289";
            String secret = "b89e524192ae478398727271554cd0fd";
            FoodService foodService = new FoodService(key, secret);
            Log.d("food test", query+page);
            Response<CompactFood> response = foodService.searchFoods(query);
            List<CompactFood> results = response.getResults();
            return results;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<CompactFood> result) {
        Log.d("food test", result.get(0).getName());
        this.list_food = result;
        this.food = result.get(0).getName();
    }
}
