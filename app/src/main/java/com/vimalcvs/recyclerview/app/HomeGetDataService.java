package com.vimalcvs.recyclerview.app;

import com.vimalcvs.recyclerview.models.HomeApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HomeGetDataService {

    @GET("apps/fast-english/home.json")

    Call<HomeApiResponse> getResponse(@Query("results") int results);
}
