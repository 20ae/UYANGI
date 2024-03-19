package com.example.socks_android.Interfaces;



import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Justice on 05/02/2017.
 */

public class Cart implements Serializable {

    @SerializedName("ProductID")
    public String id;

    @SerializedName("ProductName")
    public String name;

    @SerializedName("option1")
    public String option1;

    @SerializedName("price")
    public String price;

    @SerializedName("img_url")
    public String img_url;

    @SerializedName("quantity")
    public int quantity;

    @SerializedName("customer")
    public String customer;

}
