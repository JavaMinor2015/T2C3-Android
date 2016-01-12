package com.infosupport.t2c3_android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.infosupport.t2c3_android.R;
import com.infosupport.t2c3_android.adapter.OrderAdapter;
import com.infosupport.t2c3_android.pojo.Order;
import com.infosupport.t2c3_android.service.OrderService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends Activity {

    private ListView orderList;
    private OrderAdapter orderAdapter;

    private static final String BASE_URL = "http://10.32.42.76:6789";
    private Retrofit retrofit;

    private static int statusCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connect to REST endpoint with basic Gson configuration
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        ArrayList<Order> orders = new ArrayList<Order>();
        orderAdapter = new OrderAdapter(this, orders);

        ListView listView = (ListView) findViewById(R.id.orderListView);
        listView.setAdapter(orderAdapter);

        retrieveOrders();
    }

    private void retrieveOrders() {

        OrderService orderService = retrofit.create(OrderService.class);
        Call<List<Order>> callOrdersGetRequest = orderService.listOrders();
        callOrdersGetRequest.enqueue(new Callback<List<Order>>() {

            @Override
            public void onResponse(Response<List<Order>> response) {
                statusCode = response.code();
                System.out.println(statusCode);
                List<Order> orders = response.body();
                addToAdapter(orders);
            }

            @Override
            public void onFailure(Throwable t) {
                //TODO: log failed REST Call
            }
        });

    }

    private void addToAdapter(List<Order> orders) {
        for (Order order : orders) {
            orderAdapter.add(order);
        }
    }

    public String loadJSONFromAsset() {
        String json;
        try {

            InputStream is = getAssets().open("bla.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }


}
