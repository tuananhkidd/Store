package com.kidd.store.models.body;

public class RateClothesBody {
    private String message;
    private int rating;

    public RateClothesBody(String message, int rating) {
        this.message = message;
        this.rating = rating;
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
