package me.hotcode.eatdee.models;

import com.fatsecret.platform.model.CompactFood;

import java.util.List;

/**
 * Created by Jiravat on 27/10/2559.
 */

public class ListFood {
    String listName;
    List<CompactFood> listFood;
    String imageUrl;
    String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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
}
