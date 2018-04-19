package com.kidd.store.models.response;

import java.io.Serializable;

public class Profile implements Serializable {
    private String fullName;
    private String phone;
    private String address;
    private String identityCard;
    private String description;
    private String avatarUrl;
    private int gender;
    private long birthday;

    public Profile(String fullName, String phone, String address, String identityCard, String description, String avatarUrl, int gender, long birthday) {
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.identityCard = identityCard;
        this.description = description;
        this.avatarUrl = avatarUrl;
        this.gender = gender;
        this.birthday = birthday;
    }

    public String getFullName() {

        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }
}
