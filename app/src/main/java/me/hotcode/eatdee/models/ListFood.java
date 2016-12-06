package me.hotcode.eatdee.models;



import java.io.Serializable;
import java.util.List;

import me.hotcode.eatdee.fatsecret.model.CompactFood;

/**
 * Created by Jiravat on 27/10/2559.
 */

public class ListFood implements Serializable {
    int id;
    String listName;
    List<CompactFood> listFood;
    String description;
    String imageUrl;

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public List<CompactFood> getListFood() {
        return listFood;
    }

    public void setListFood(List<CompactFood> listFood) {
        this.listFood = listFood;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
