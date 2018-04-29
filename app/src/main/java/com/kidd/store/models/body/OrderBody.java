package com.kidd.store.models.body;

public class OrderBody {
    private String color;
    private String size;
    private int amount;
    private int price;

    public OrderBody() {
    }

    public OrderBody(String color, String size, int amount, int price) {

        this.color = color;
        this.size = size;
        this.amount = amount;
        this.price = price;
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
}
