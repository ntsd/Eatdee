package me.hotcode.eatdee.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutionException;

import me.hotcode.eatdee.fatsecret.model.CompactFood;
import me.hotcode.eatdee.fatsecret.services.FoodService;
import me.hotcode.eatdee.fatsecret.services.Response;

/**
 * Created by Jiravat on 27/10/2559.
 */

public class FoodUtils {
    public List<CompactFood> listFood;
    public String query;
    public int page;

    public List<CompactFood> getFoodList(String query,int page){
        this.query = query;
        this.page = page;
        SearchFood searchFood = new SearchFood();
        searchFood.execute();
        try {
            listFood = searchFood.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return listFood;
    }

    private class SearchFood extends AsyncTask<String, Void, List<CompactFood>> {

        @Override
        protected List<CompactFood> doInBackground(String... params) {
            String key = "458967deb4cf44579640437b60db5289";
            String secret = "b89e524192ae478398727271554cd0fd";
            FoodService foodService = new FoodService(key, secret);
            Response<CompactFood> response = foodService.searchFoods(query, page);
            List<CompactFood> results = response.getResults();
            return results;
        }

        @Override
        protected void onPostExecute(List<CompactFood> result) {

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }
}
