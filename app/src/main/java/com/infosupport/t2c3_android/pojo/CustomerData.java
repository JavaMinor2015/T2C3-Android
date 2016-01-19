package com.infosupport.t2c3_android.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class CustomerData {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("version")
    @Expose
    public Integer version;
    @SerializedName("firstName")
    @Expose
    public String firstName;
    @SerializedName("lastName")
    @Expose
    public String lastName;
    @SerializedName("emailAddress")
    @Expose
    public String emailAddress;
    @SerializedName("creditLimit")
    @Expose
    public BigDecimal creditLimit;
    @SerializedName("address")
    @Expose
    public Address address;

}