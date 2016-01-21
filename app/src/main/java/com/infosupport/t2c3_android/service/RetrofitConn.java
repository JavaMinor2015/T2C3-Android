package com.infosupport.t2c3_android.service;

import com.google.gson.Gson;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by koen on 1/12/16.
 */
public enum RetrofitConn {
    INSTANCE;
    //    private static final String BASE_URL = "http://10.32.42.76:6789";
//    private static final String BASE_URL = "http://192.168.178.12:6789";
    private static final String BASE_URL = "http://10.32.41.224:6789";
    public Retrofit RESTWebService = null;

    public Retrofit init() {
        if (this.RESTWebService == null) {
            this.RESTWebService = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .build();
        }

        return RESTWebService;
    }
}
