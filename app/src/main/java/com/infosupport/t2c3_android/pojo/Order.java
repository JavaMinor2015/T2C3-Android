package com.infosupport.t2c3_android.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Order {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("version")
    @Expose
    public Integer version;
    @SerializedName("totalPrice")
    @Expose
    public Float totalPrice;
    @SerializedName("status")
    @Expose
    public Object status;
    @SerializedName("items")
    @Expose
    public List<Item> items = new ArrayList<Item>();
    @SerializedName("customerData")
    @Expose
    public CustomerData customerData;

}