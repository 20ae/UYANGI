package com.example.socks_android.Interfaces;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Justice on 17/02/2017.
 */

public class Users implements Serializable {

    @SerializedName ("CustomerID")
    public int id;

    @SerializedName("Name")
    public String name;

    @SerializedName("userID")
    public String userID;

    @SerializedName("Email")
    public String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
