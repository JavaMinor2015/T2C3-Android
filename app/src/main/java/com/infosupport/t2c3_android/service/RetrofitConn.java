package com.infosupport.t2c3_android.service;

import com.google.gson.Gson;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by koen on 1/12/16.
 */
public enum RetrofitConn {
    INSTANCE;
    public Retrofit RESTWebService = null;

    public Retrofit init(String BASE_URL) {
        if(this.RESTWebService == null) {
        this.RESTWebService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        }

        return RESTWebService;
    }
}
