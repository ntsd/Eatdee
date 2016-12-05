package me.hotcode.eatdee.models;

import java.util.Date;
import java.util.List;

import me.hotcode.eatdee.fatsecret.model.CompactFood;

/**
 * Created by Jiravat on 2/12/2559.
 */

public class Diary {
    Date date;
    List<CompactFood> foodsBreakfast;
    List<CompactFood> foodsLunch;
    List<CompactFood> foodsDinner;
    int currentCal;
    int currentProtein;
    int currentCarb;
    int currentFat;
}
