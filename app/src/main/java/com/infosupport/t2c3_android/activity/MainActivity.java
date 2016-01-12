package com.infosupport.t2c3_android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.infosupport.t2c3_android.R;
import com.infosupport.t2c3_android.adapter.OrderAdapter;
import com.infosupport.t2c3_android.converter.JSONToOrder;
import com.infosupport.t2c3_android.pojo.Order;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private ListView orderList;
    private OrderAdapter orderAdapter;

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
        //TODO: implement REST call

        String object = loadJSONFromAsset();
        Order[] test = JSONToOrder.INSTANCE.convert(object);
        for (Order order : test) {
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
