package com.kidd.store.models.response;

import com.kidd.store.models.Category;
import com.kidd.store.models.RateClothesViewModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by KingIT on 4/24/2018.
 */

public class ClothesViewModel implements Serializable{
    private String id;
    private String name;
    private int price;
    private String description;
    private Date createdDate;
    private String logoUrl;
    private Category category;
    private List<RateClothesViewModel> rateClothesViewModels;
    private int numberSave;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<RateClothesViewModel> getRateClothesViewModels() {
        return rateClothesViewModels;
    }

    public void setRateClothesViewModels(List<RateClothesViewModel> rateClothesViewModels) {
        this.rateClothesViewModels = rateClothesViewModels;
    }

    public int getNumberSave() {
        return numberSave;
    }

    public void setNumberSave(int numberSave) {
        this.numberSave = numberSave;
    }
}
