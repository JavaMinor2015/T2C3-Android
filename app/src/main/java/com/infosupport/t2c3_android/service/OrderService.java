package com.infosupport.t2c3_android.service;

import com.infosupport.t2c3_android.pojo.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by koen on 1/12/16.
 */
public interface OrderService {
    @GET("order")
    Call<List<Order>> listOrders();
}
