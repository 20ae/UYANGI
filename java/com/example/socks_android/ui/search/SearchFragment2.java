package com.example.socks_android.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.amigold.fundapter.interfaces.DynamicImageLoader;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.socks_android.AppHelper;
import com.example.socks_android.Interfaces.Products;
import com.example.socks_android.ProductDetailActivity;
import com.example.socks_android.R;
import com.kosalgeek.android.json.JsonConverter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchFragment2 extends Fragment implements Response.Listener<String> {

    private static String TAG = "phpsearchtest";
    private static final String TAG_JSON = "search";
    private static final String TAG_ID = "ProductID";
    private static final String TAG_NAME = "ProductName";
    private static final String TAG_IMG = "IMG_URL";
    private static final String TAG_PRICE = "Price";

    private String mJsonString;
    ArrayList<HashMap<String, String>> mArrayList;
    private int item_number;
    private TextView noneText;

    private GridView gv;
    private ArrayList<Products> productList;
    //FunDapter<Products> adapter;

    private RequestQueue queue;


    public SearchFragment2() {
    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_search2, container, false);

        //메인에서 검색 키워드 받아오기
        String searchStr;
        Bundle bundle = getArguments();
        TextView searchKey = (TextView) view.findViewById(R.id.search_key);
        noneText = (TextView) view.findViewById(R.id.noneText);


        if (bundle != null) {
            searchStr = bundle.getString("key");
            Log.d("searchKey", searchStr);
            searchKey.setText(searchStr);

//            SearchFragment2.GetData task = new SearchFragment2.GetData();
//            task.execute(searchStr);

            mArrayList = new ArrayList<>();

            //

            AppHelper.requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

            String url = "http://192.168.11.213/phpfiles/search.php";

            final StringRequest stringRequest = new StringRequest(Request.Method.POST, url, (Response.Listener<String>) this, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity().getApplicationContext(),"error while reading",Toast.LENGTH_SHORT).show();
                }
            }){
                @Override //response를 UTF8로 변경해주는 소스코드
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    try {
                        String utf8String = new String(response.data, "UTF-8");
                        return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                    } catch (UnsupportedEncodingException e) {
                        // log error
                        return Response.error(new ParseError(e));
                    } catch (Exception e) {
                        // log error
                        return Response.error(new ParseError(e));
                    }
                }
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("ProductName",searchStr);
                    Log.d(TAG,searchStr);
                    return params;
                }

            };

            stringRequest.setShouldCache(false); //이전 결과 있어도 새로 요청하여 응답을 보여준다.
            AppHelper.requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext()); // requestQueue 초기화 필수
            AppHelper.requestQueue.add(stringRequest);
            gv = (GridView) view.findViewById(R.id.gv_search);

        }
        return view;
    }

    @Override
    public void onResponse(String response) {
        Log.d(TAG,response);

        if (response.equals("no_data")){

            Toast.makeText(getActivity().getApplicationContext(),"No data to show",Toast.LENGTH_SHORT).show();



        }else {

            productList = new JsonConverter<Products>().toArrayList(response, Products.class);

            BindDictionary dic = new BindDictionary();

            dic.addStringField(R.id.tvName2, new StringExtractor<Products>() {
                @Override
                public String getStringValue(Products item, int position) {
                    return item.name;
                }
            });

//        dic.addStringField(R.id.tvDesc, new StringExtractor<Products>() {
//            @Override
//            public String getStringValue(Products item, int position) {
//                return item.description;
//            }
//        }).visibilityIfNull(View.GONE);

            dic.addStringField(R.id.tvPrice2, new StringExtractor<Products>() {
                @Override
                public String getStringValue(Products item, int position) {
                    return ""+item.price;
                }
            });

            dic.addDynamicImageField(R.id.ivImage2, new StringExtractor<Products>() {
                @Override
                public String getStringValue(Products item, int position) {
                    return item.img_url;
                }
            }, new DynamicImageLoader() {
                @Override
                public void loadImage(String url, ImageView img) {
                    //Set image
                    Picasso.get().load(url).into(img);
                    img.setAdjustViewBounds(true);
                    img.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    img.setPadding(0, 0, 0, 10);
                    img.setAdjustViewBounds(true);
                }
            });

            FunDapter<Products> adapter = new FunDapter<>(SearchFragment2.this.getActivity(), productList, R.layout.fragment_home_row, dic);
            gv.setAdapter(adapter);

            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Products selectedProduct = productList.get(position);

                    Intent intent = new Intent(SearchFragment2.this.getActivity(), ProductDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("productList", selectedProduct);
                    intent.putExtras(bundle);
                    startActivity(intent);


                }
            });

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
