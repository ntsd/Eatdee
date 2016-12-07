package me.hotcode.eatdee.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import me.hotcode.eatdee.fatsecret.model.CompactFood;

/**
 * Created by Jiravat on 2/12/2559.
 */

public class Diary implements Serializable{
    Date date;
    List<CompactFood> foodsBreakfast;
    List<CompactFood> foodsLunch;
    List<CompactFood> foodsDinner;
    int currentCal;
    int currentProtein;
    int currentCarb;
    int currentFat;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<CompactFood> getFoodsBreakfast() {
        return foodsBreakfast;
    }

    public void setFoodsBreakfast(List<CompactFood> foodsBreakfast) {
        this.foodsBreakfast = foodsBreakfast;
    }

    public List<CompactFood> getFoodsLunch() {
        return foodsLunch;
    }

    public void setFoodsLunch(List<CompactFood> foodsLunch) {
        this.foodsLunch = foodsLunch;
    }

    public List<CompactFood> getFoodsDinner() {
        return foodsDinner;
    }

    public void setFoodsDinner(List<CompactFood> foodsDinner) {
        this.foodsDinner = foodsDinner;
    }

    public int getCurrentCal() {
        return currentCal;
    }

    public void setCurrentCal(int currentCal) {
        this.currentCal = currentCal;
    }

    public int getCurrentProtein() {
        return currentProtein;
    }

    public void setCurrentProtein(int currentProtein) {
        this.currentProtein = currentProtein;
    }

    public int getCurrentCarb() {
        return currentCarb;
    }

    public void setCurrentCarb(int currentCarb) {
        this.currentCarb = currentCarb;
    }

    public int getCurrentFat() {
        return currentFat;
    }

    public void setCurrentFat(int currentFat) {
        this.currentFat = currentFat;
    }
}
