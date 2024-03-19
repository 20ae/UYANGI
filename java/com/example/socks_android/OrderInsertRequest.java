package com.example.socks_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class OrderInsertRequest extends StringRequest {
    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://192.168.11.213/phpfiles/order_insert.php";
    private HashMap postData;


    public OrderInsertRequest(String CustomerID, String OrderName, String TotalPrice, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        postData = new HashMap();
        postData.put("CustomerID", CustomerID);
        postData.put("OrderName", OrderName);
        postData.put("TotalPrice", TotalPrice);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return postData;
    }
}
