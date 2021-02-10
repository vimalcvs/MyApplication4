package com.vimalcvs.recyclerview.models;

import com.google.gson.annotations.SerializedName;

public class HomeResult {

    @SerializedName("name")
    private HomeName homeName;


    @SerializedName("login")
    private HomeLogin homeLogin;


    @SerializedName("picture")
    private HomePicture homePicture;


    @SerializedName("status")
    private int status;

    public HomeName getHomeName() {
        return homeName;
    }

    public HomeLogin getHomeLogin() {
        return homeLogin;
    }
    public HomePicture getHomePicture() {
        return homePicture;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
