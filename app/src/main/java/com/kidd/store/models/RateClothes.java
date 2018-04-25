package com.kidd.store.models;

import com.kidd.store.models.response.Customer;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by KingIT on 4/25/2018.
 */

public class RateClothes implements Serializable{
    private String id;
    private Clothes clothes;
    private Customer customer;
    private Date rateDate;
    private String message;
    private int rating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Clothes getClothes() {
        return clothes;
    }

    public void setClothes(Clothes clothes) {
        this.clothes = clothes;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getRateDate() {
        return rateDate;
    }

    public void setRateDate(Date rateDate) {
        this.rateDate = rateDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
