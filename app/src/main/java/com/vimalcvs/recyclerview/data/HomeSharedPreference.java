package com.vimalcvs.recyclerview.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.vimalcvs.recyclerview.app.HomeConstants;
import com.vimalcvs.recyclerview.models.HomeApiResponse;
import com.vimalcvs.recyclerview.models.HomeResult;
import com.google.gson.Gson;

import java.util.List;

public class HomeSharedPreference {

    private static final String PREFS_NAME = "RecyclerViewButtonsInCardView";
    private static final String RESPONSE = "ApiResponse";

    public HomeSharedPreference() {
        super();
    }

    public void saveResponse(Context context, HomeApiResponse homeApiResponse) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(homeApiResponse);

        editor.putString(RESPONSE, jsonResponse);

        editor.apply();
    }

    public void accept(Context context, HomeResult homeResult) {
        HomeApiResponse homeApiResponse = getResponse(context);
        if (homeApiResponse != null && homeApiResponse.getHomeResults() != null) {
            List<HomeResult> homeResults = homeApiResponse.getHomeResults();
            for (int i = 0; i < homeResults.size(); i++) {
                if (homeResult.getHomeLogin().getUuid().equals(homeResults.get(i).getHomeLogin().getUuid())) {
                    homeResults.get(i).setStatus(HomeConstants.ACCEPT);
                }
            }
        }
        saveResponse(context, homeApiResponse);
    }

    public void decline(Context context, HomeResult homeResult) {
        HomeApiResponse homeApiResponse = getResponse(context);
        if (homeApiResponse != null && homeApiResponse.getHomeResults() != null) {
            List<HomeResult> homeResults = homeApiResponse.getHomeResults();
            for (int i = 0; i < homeResults.size(); i++) {
                if (homeResult.getHomeLogin().getUuid().equals(homeResults.get(i).getHomeLogin().getUuid())) {
                    homeResults.get(i).setStatus(HomeConstants.DECLINE);
                }
            }
        }
        saveResponse(context, homeApiResponse);
    }

    public HomeApiResponse getResponse(Context context) {
        SharedPreferences settings;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        HomeApiResponse homeApiResponse;
        if (settings.contains(RESPONSE)) {
            String jsonResponse = settings.getString(RESPONSE, null);
            Gson gson = new Gson();
            homeApiResponse = gson.fromJson(jsonResponse,
                    HomeApiResponse.class);

        } else
            return null;

        return homeApiResponse;
    }

    public boolean hasResponse(Context context) {
        SharedPreferences settings;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        return settings.contains(RESPONSE);
    }
}
