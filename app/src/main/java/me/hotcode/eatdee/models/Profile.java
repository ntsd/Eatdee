package me.hotcode.eatdee.models;

import android.util.Log;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jiravat on 5/10/2559.
 */

public class Profile implements Serializable {
    private Date birth;
    private int height;
    private int weight;
    private String sex;
    private int goal_calories;
    private int bmr;
    private int exercise_per_week;

    public Profile() {
    }

    public Profile(Date birth, int height, int weight, String sex,int exercise_per_week) {
        this.birth = birth;
        this.height = height;
        this.weight = weight;
        this.sex = sex;
        this.exercise_per_week = exercise_per_week;
        int age =  (new Date().getYear()+1900) - birth.getYear();
        Log.d("age = ", ""+age);
        double[] multiply = {1.2,1.375,1.55,1.725,1.9};
        if(sex == "male"){
            this.bmr = (int)(66 + (13.7 * weight) + (5 * height) - (6.8 * age));
        }else{
            this.bmr = (int)(655 + (9.6 * weight) + (1.8 * height) - (4.7 * age));
        }
        this.goal_calories = (int)(bmr*multiply[exercise_per_week]);
    }

    public int getGoal_calories() {
        return goal_calories;
    }

    public void setGoal_calories(int goal_calories) {
        this.goal_calories = goal_calories;
    }

    public int getBmr() {
        return bmr;
    }

    public void setBmr(int bmr) {
        this.bmr = bmr;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Date:"+this.birth.toString()+" height:"+height+" weight"+weight+" sex:"+sex+"goal_calories:"+goal_calories+"bmr:"+bmr;
    }
}
