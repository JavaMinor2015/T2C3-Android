package com.infosupport.t2c3_android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    Gson gson = new Gson();
    public static final String BASE_URL = "http://localhost:6789";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                int statusCode = response.code();
                List<Order> orders = response.body();

                //TODO: foreach loop
//                for (Order order : orders) {
//                    orderAdapter.add(order);
//                }
            }

            @Override
            public void onFailure(Throwable t) {
                // Log error here since request failed
            }
        });
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
