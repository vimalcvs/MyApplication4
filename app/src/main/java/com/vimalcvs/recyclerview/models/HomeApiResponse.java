package com.vimalcvs.recyclerview.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeApiResponse {

    @SerializedName("results")
    private final List<HomeResult> homeResults = null;

    @SerializedName("info")
    public List<HomeResult> getHomeResults() {
        return homeResults;
    }

}
