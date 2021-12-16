package com.example.mealplanner.service;

import retrofit2.Retrofit;

public class APIClient {

    private static Retrofit retrofit = null;
    private static String baseUrl = "http://192.168.1.112:8080";

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .build();
        }
        return retrofit;
    }


}
