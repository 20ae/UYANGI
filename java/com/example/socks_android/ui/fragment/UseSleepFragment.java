package com.example.socks_android.ui.fragment;

import android.content.Intent;
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
import com.example.socks_android.ProductDetailActivity;
import com.example.socks_android.R;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UseSleepFragment extends Fragment implements AsyncResponse, AdapterView.OnItemClickListener {

    final static String url = "http://192.168.11.213/phpfiles/use.php?Description=수면양말";

    private ArrayList<Products> productList;
    private GridView gv;
    FunDapter<Products> adapter;

    View view;

    public UseSleepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.gridview, container, false);

        //ImageLoader.getInstance().init(UILConfig.config(StylePlainFragment.this.getActivity()));

        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(UseSleepFragment.this.getActivity(), this);
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
                Picasso.get().load(url).into(img);
                img.setAdjustViewBounds(true);
                img.setScaleType(ImageView.ScaleType.FIT_CENTER);
                img.setPadding(0, 0, 0, 10);
                img.setAdjustViewBounds(true);
            }
        });

        adapter = new FunDapter<>(UseSleepFragment.this.getActivity(), productList, R.layout.fragment_home_row, dic);
        gv = (GridView)view.findViewById(R.id.gridview);
        gv.setAdapter(adapter);

        gv.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Products selectedProduct = productList.get(position);

        Intent intent = new Intent(UseSleepFragment.this.getActivity(), ProductDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("productList", selectedProduct);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

