package com.example.socks_android.Interfaces;



import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Justice on 05/02/2017.
 */

public class Orders implements Serializable {

    @SerializedName("OrderNumber")
    public String order_number;

    @SerializedName("OrderDate")
    public String order_date;

    @SerializedName("CustomerID")
    public String id;

    @SerializedName("OrderName")
    public String name;

    @SerializedName("OrderStatus")
    public String order_status;

    @SerializedName("TotalPrice")
    public String price;

    @SerializedName("PaymentMethod")
    public String payment_method;

}
