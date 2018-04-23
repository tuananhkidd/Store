package com.kidd.store.models.body;

public class ProfileBody {
    private String fullName;
    private String phone;
    private String address;
    private String identityCard;
    private String avatarUrl;
    private int gender;
    private long birthday;
    private String email;

    public ProfileBody() {
    }

    public ProfileBody(String fullName,
                       String phone,
                       String address,
                       String identityCard,
                       int gender,
                       long birthday,
                       String email) {

        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.identityCard = identityCard;
        this.avatarUrl = avatarUrl;
        this.gender = gender;
        this.birthday = birthday;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
