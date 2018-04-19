package com.kidd.store.models.body;

public class RateBody {
    private int rating;
    private String cmt;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getCmt() {
        return cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }

    public RateBody(int rating, String cmt) {
        this.rating = rating;
        this.cmt = cmt;
    }
}
