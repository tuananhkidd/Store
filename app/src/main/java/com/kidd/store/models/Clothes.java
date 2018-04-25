package com.kidd.store.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by KingIT on 4/22/2018.
 */

public class Clothes implements Serializable{
    private String id;
    private String name;
    private int price;
    private String description;
    private Date createdDate;
    private String logoUrl;
    private int totalSave;
    private Category category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public int getTotalSave() {
        return totalSave;
    }

    public void setTotalSave(int totalSave) {
        this.totalSave = totalSave;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
