package com.infosupport.t2c3_android.service;

import com.infosupport.t2c3_android.pojo.CustomerData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by koen on 1/12/16.
 */
public interface CustomerService {
    @GET("customers")
    Call<List<CustomerData>> listCustomers();

    @GET("customers/{id}")
    Call<CustomerData> getCustomer(@Path("id") String id);

}
