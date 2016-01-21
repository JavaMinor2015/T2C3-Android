package com.infosupport.t2c3_android.service;

import com.infosupport.t2c3_android.pojo.Customer;

import java.math.BigDecimal;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by koen on 1/12/16.
 */
public interface CustomerService {
    @GET("employee/customers")
    Call<List<Customer>> listCustomers();

    @GET("employee/customers/{id}")
    Call<Customer> getCustomer(@Path("id") String id);

    @FormUrlEncoded
    @PUT("/employee/customers/{id}/creditLimit")
    Call<Customer> editCreditLimit(@Path("id") String id, @Field("creditLimit") BigDecimal newLimit);
}
