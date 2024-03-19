package com.example.socks_android.ui.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.amigold.fundapter.interfaces.DynamicImageLoader;
import com.example.socks_android.Interfaces.Products;
import com.example.socks_android.Interfaces.UILConfig;
import com.example.socks_android.ProductDetailActivity;
import com.example.socks_android.R;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserPreferenceFragment extends Fragment implements AsyncResponse, AdapterView.OnItemClickListener {
//    final static String url = "";

    //final static String url = "http://192.168.11.213/phpfiles/userPreference.php?catid=1";

    private ArrayList<Products> productList;
    private GridView gv;
    FunDapter<Products> adapter;

    View view;

    public UserPreferenceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String url = "";
        SharedPreferences userPreference = UserPreferenceFragment.this.getActivity().getSharedPreferences("UserInfo", 0);
        ///SharedPreferences lengthPreference = UserPreferenceFragment.this.getActivity().getSharedPreferences("LengthInfo", 0);
        int gender = userPreference.getInt("gender",-1);
        String length = userPreference.getString("length","");

        if(gender==1){
            if(length.equals("덧신")){
                url = "http://192.168.11.213/phpfiles/userPreference.php?catid=1&length=덧신";
            }
            else if(length.equals("발목")){
                url = "http://192.168.11.213/phpfiles/userPreference.php?catid=1&length=발목";
            }
            else if(length.equals("중목")){
                url = "http://192.168.11.213/phpfiles/userPreference.php?catid=1&length=중목";
            }
            else if(length.equals("장목")){
                url = "http://192.168.11.213/phpfiles/userPreference.php?catid=1&length=장목";
            }

        } else if(gender==2){
            if(length.equals("덧신")){
                url = "http://192.168.11.213/phpfiles/userPreference.php?catid=2&length=덧신";
            }
            else if(length.equals("발목")){
                url = "http://192.168.11.213/phpfiles/userPreference.php?catid=2&length=발목";
            }
            else if(length.equals("중목")){
                url = "http://192.168.11.213/phpfiles/userPreference.php?catid=2&length=중목";
            }
            else if(length.equals("장목")){
                url = "http://192.168.11.213/phpfiles/userPreference.php?catid=2&length=장목";
            }
            else if(length.equals("니삭스")){
                url = "http://192.168.11.213/phpfiles/userPreference.php?catid=2&length=니삭스";
            }
        } else if(gender == 3) // 아동
            url = "http://192.168.11.213/phpfiles/userPreference.php?catid=3";


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.gridview, container, false);

        //ImageLoader.getInstance().init(UILConfig.config(UserPreferenceFragment.this.getActivity()));

        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(UserPreferenceFragment.this.getActivity(), this);
        taskRead.execute(url);

        return view;
    }


    @Override
    public void processFinish(String s) {

        productList = new JsonConverter<Products>().toArrayList(s, Products.class);

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
                ImageLoader.getInstance().displayImage(url, img);
            }
        });

        adapter = new FunDapter<>(UserPreferenceFragment.this.getActivity(), productList, R.layout.fragment_home_row, dic);
        gv = (GridView)view.findViewById(R.id.gridview);
        gv.setAdapter(adapter);

        gv.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Products selectedProduct = productList.get(position);

        Intent intent = new Intent(UserPreferenceFragment.this.getActivity(), ProductDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("productList", selectedProduct);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
