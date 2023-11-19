package com.example.helllllllooo;

public class UserData {
    private String name;
    private String email;
    private String mobile;
    private String gender;
    private String imageUrl;

    // Constructors, getters, and setters

    public UserData(String name, String email, String mobile, String gender, String imageUrl) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.gender = gender;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
