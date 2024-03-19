package com.example.socks_android.Interfaces;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Justice on 22/01/2017.
 */

public class Products implements Serializable {

    @SerializedName("ProductID")
    public String id;

    @SerializedName("ProductName")
    public String name;

    @SerializedName("Description")
    public String description;

    @SerializedName("Price")
    public int price;

    @SerializedName("IMG_URL")
    public String img_url;

    @SerializedName("catid")
    public String catid;

    @SerializedName("length")
    public String length;

    @SerializedName("style")
    public String style;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

}
