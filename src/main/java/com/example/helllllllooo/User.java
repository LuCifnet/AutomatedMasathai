package com.example.helllllllooo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

// User.java
public class User {
    private String name;
    private String email;
    private String mobile;
    private String gender;
    private String userFlag;
    private String username;

    public User(String name, String email, String mobile, String gender, String userFlag, String username ) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.gender = gender;
        this.userFlag=userFlag;
        this.username=username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getGender() {
        return gender;
    }

    public String getUsername() {
        return username;}
    public String getUserFlag() {
        return userFlag;}
}
