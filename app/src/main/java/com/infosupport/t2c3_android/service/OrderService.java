package com.infosupport.t2c3_android.service;

import com.infosupport.t2c3_android.pojo.Order;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by koen on 1/12/16.
 */
public interface OrderService {
    @GET("order")
    Call<List<Order>> listOrders();

    @GET("employeeorder/{id}")
    Call<Order> getOrder(@Path("id") String id);

    @PUT("employeeorder")
    Call<Order> postOrderStatus(@Body Order order);
}
