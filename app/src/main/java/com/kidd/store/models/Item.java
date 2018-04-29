package com.kidd.store.models;

import java.io.Serializable;

/**
 * Created by KingIT on 4/29/2018.
 */

public class Item implements Serializable{
    private Clothes clothes;
    private int count;
    private String color;
    private String size;

    public Item() {
    }

    public Item(Clothes clothes, int count, String color, String size) {
        this.clothes = clothes;
        this.count = count;
        this.color = color;
        this.size = size;
    }

    public Clothes getClothes() {
        return clothes;
    }

    public void setClothes(Clothes clothes) {
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
}
