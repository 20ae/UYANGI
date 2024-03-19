package com.example.socks_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CartInsertRequest extends StringRequest {
    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://192.168.11.213/phpfiles/cart_insert.php";
    private HashMap postData;


    public CartInsertRequest(String ProductID, String ProductName, String option1, String qty, String price, String img_url, String customer, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        postData = new HashMap();
        postData.put("txtPID", ProductID);
        postData.put("txtName", ProductName);
        postData.put("txtColor", option1);
        postData.put("txtQty", qty);
        postData.put("txtPrice", price);
        postData.put("txtImageUrl", img_url);
        postData.put("txtCustomer", customer);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return postData;
    }
}
