package com.kidd.store.models;

import android.os.Parcelable;

import com.kidd.store.models.response.ClothesViewModel;

import java.io.Serializable;

/**
 * Created by KingIT on 4/29/2018.
 */

public class Item implements Serializable{
    private ClothesViewModel clothes;
    private int count;
    private String color;
    private String size;

    public Item() {
    }

    public Item(ClothesViewModel clothes, int count, String color, String size) {
        this.clothes = clothes;
        this.count = count;
        this.color = color;
        this.size = size;
    }

    public ClothesViewModel getClothes() {
        return clothes;
    }

    public void setClothes(ClothesViewModel clothes) {
        this.clothes = clothes;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Item && ((Item) obj).getClothes().getId().equals(this.getClothes().getId()) && ((Item) obj).getSize().equals(this.getSize())
                && ((Item) obj).getColor().equals(this.getColor());
    }
}
