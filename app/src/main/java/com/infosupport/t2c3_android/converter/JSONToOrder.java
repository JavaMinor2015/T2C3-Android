package com.infosupport.t2c3_android.converter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.infosupport.t2c3_android.pojo.Order;

/**
 * Created by koen on 1/12/16.
 */
public enum JSONToOrder {
    INSTANCE;
    private Gson gson = new Gson();
    public Order[] convert(String object) {
        Order[] orders = null;
        try {
            orders = gson.fromJson(object, Order[].class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
