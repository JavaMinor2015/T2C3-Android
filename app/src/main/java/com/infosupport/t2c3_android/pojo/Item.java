package com.infosupport.t2c3_android.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("version")
    @Expose
    public Integer version;
    @SerializedName("price")
    @Expose
    public Float price;
    @SerializedName("amount")
    @Expose
    public Integer amount;
//    @SerializedName("product")
//    @Expose
//    public Product product;

}