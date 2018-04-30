package com.kidd.store.models.body;

public class SetOrderBody {
    private String color;
    private String size;
    private int amount;
    private int price;
    private String clothesID;

    public SetOrderBody(String color, String size, int amount, int price, String clothesID) {
        this.color = color;
        this.size = size;
        this.amount = amount;
        this.price = price;
        this.clothesID = clothesID;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getClothesID() {
        return clothesID;
    }

    public void setClothesID(String clothesID) {
        this.clothesID = clothesID;
    }
}
