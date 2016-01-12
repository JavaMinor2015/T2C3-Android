package com.infosupport.t2c3_android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TableLayout;

import com.infosupport.t2c3_android.R;
import com.infosupport.t2c3_android.adapter.OrderAdapter;
import com.infosupport.t2c3_android.pojo.Order;
import com.infosupport.t2c3_android.service.OrderService;
import com.infosupport.t2c3_android.service.RetrofitConn;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends Activity {

    private OrderAdapter orderAdapter;

    //Change this to your local IP-Networking address to use the Spring REST implementation on your mobile phone
//    private static final String BASE_URL = "http://10.32.42.76:6789";
    private static final String BASE_URL = "http://192.168.178.12:6789";
    private Retrofit retrofit;

    private static int statusCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Singleton for connecting to REST API
        retrofit = RetrofitConn.INSTANCE.init(BASE_URL);


        ArrayList<Order> orders = new ArrayList<Order>();
        orderAdapter = new OrderAdapter(this, orders);

        //Add orders to adapter
        retrieveOrders();
    }

    private void retrieveOrders() {

        OrderService orderService = retrofit.create(OrderService.class);
        Call<List<Order>> callOrdersGetRequest = orderService.listOrders();
        callOrdersGetRequest.enqueue(new Callback<List<Order>>() {

            @Override
            public void onResponse(Response<List<Order>> response) {
                if (response.code() == 200) {
                    System.out.println(statusCode);
                    List<Order> orders = response.body();
                    addToAdapter(orders);
                    populateOrderTable();
                } else {
                    //TODO: log failed - show response code
                }
            }

            @Override
            public void onFailure(Throwable t) {
                //TODO: log failed to run REST Call
            }
        });

    }

    private void populateOrderTable() {
        //Append adapter to orderTableLayout
        TableLayout tableLayout = (TableLayout) findViewById(R.id.orderTableLayout);
        for (int i = 0; i < orderAdapter.getCount(); i++) {
            tableLayout.addView(orderAdapter.getView(i, null, tableLayout));
        }
    }

    private void addToAdapter(List<Order> orders) {
        for (Order order : orders) {
            orderAdapter.add(order);
        }
    }

}
