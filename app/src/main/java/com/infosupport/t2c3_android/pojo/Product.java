package com.infosupport.t2c3_android.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("version")
    @Expose
    public Integer version;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("price")
    @Expose
    public Float price;
    @SerializedName("category")
    @Expose
    public String category;
    @SerializedName("supplier")
    @Expose
    public String supplier;
    @SerializedName("available")
    @Expose
    public Boolean available;
    @SerializedName("imageURL")
    @Expose
    public String imageURL;

}
