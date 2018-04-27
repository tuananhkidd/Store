package com.kidd.store.models.response;

import java.io.Serializable;

public class HeaderProfile implements Serializable{
    private String customerID;
    private String fullName;
    private String avatarUrl;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public HeaderProfile(String fullName, String avatarUrl) {
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;

    }

    public HeaderProfile(String customerID, String fullName, String avatarUrl, String email) {
        this.customerID = customerID;
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
        this.email = email;
    }

    public HeaderProfile() {

    }

}
