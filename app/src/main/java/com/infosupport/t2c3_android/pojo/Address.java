package com.infosupport.t2c3_android.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("version")
    @Expose
    public Integer version;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("street")
    @Expose
    public String street;
    @SerializedName("streetNumber")
    @Expose
    public String streetNumber;
    @SerializedName("zipcode")
    @Expose
    public String zipcode;

}
