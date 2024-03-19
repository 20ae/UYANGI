package com.example.socks_android.ui.fragment;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.amigold.fundapter.interfaces.DynamicImageLoader;
import com.amigold.fundapter.interfaces.ItemClickListener;
import com.example.socks_android.Interfaces.Cart;
import com.example.socks_android.Interfaces.Favorite;
import com.example.socks_android.Interfaces.Products;
import com.example.socks_android.Interfaces.UILConfig;
import com.example.socks_android.R;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class FavoriteFragment extends Fragment implements AdapterView.OnItemClickListener, AsyncResponse{
    public static final String PREFS = "prefFile";

    final String LOG = "FavoriteFragment";
    final static String url = "http://192.168.11.213/phpfiles/favorite_retrieve.php";

    View view;

    private ArrayList<Favorite> favoriteList;
    private ListView lv;
    private ScrollView scrollView;
    FunDapter<Favorite> adapter;
    TextView edQty;


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favorite, container, false);

        LayoutInflater inflater2 = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view2 = inflater2.inflate(R.layout.favorite_row, null);

        lv = (ListView)view.findViewById(R.id.lvFavorite);


        //ImageLoader.getInstance().init(UILConfig.config(FavoriteFragment.this.getActivity()));


        SharedPreferences preferences = FavoriteFragment.this.getActivity().getSharedPreferences(PREFS, 0);
        String customer = preferences.getString("userID", null);

        HashMap postData = new HashMap();
        postData.put("txtuserID", customer);
        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(FavoriteFragment.this.getActivity(), postData, this);
        taskRead.execute(url);

        return view;
    }

    @Override
    public void processFinish(String s) {
        favoriteList = new JsonConverter<Favorite>().toArrayList(s, Favorite.class);

        BindDictionary dic = new BindDictionary();

        dic.addStringField(R.id.tvName, new StringExtractor<Favorite>() {
            @Override
            public String getStringValue(Favorite item, int position) {
                return item.name;
            }
        });

        dic.addStringField(R.id.tvOption1, new StringExtractor<Favorite>() {
            @Override
            public String getStringValue(Favorite item, int position) {
                return item.option1;
            }
        });

        dic.addStringField(R.id.tvPrice1, new StringExtractor<Favorite>() {
            @Override
            public String getStringValue(Favorite item, int position) {
                return String.valueOf(item.price);
            }
        });


        dic.addDynamicImageField(R.id.ivImage, new StringExtractor<Favorite>() {
            @Override
            public String getStringValue(Favorite item, int position) {
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

        //Remove Item from the basket
        dic.addBaseField(R.id.btnRemove).onClick(new ItemClickListener() {

            @Override
            public void onClick(Object item, int position, View view) {

                final Favorite selectedItem = favoriteList.get(position);
                HashMap postData = new HashMap();
                postData.put("ProductID", ""+ selectedItem.id);
                postData.put("userID", ""+ selectedItem.customer);
                postData.put("option1", ""+ selectedItem.option1);


                PostResponseAsyncTask taskRemove = new PostResponseAsyncTask(FavoriteFragment.this.getActivity(),
                        postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(LOG, s);
                        if(s.contains("success"))
                        {
                            favoriteList.remove(selectedItem);
                            lv.refreshDrawableState();

                            adapter.notifyDataSetChanged();
                            Toast.makeText(FavoriteFragment.this.getActivity(), "Item Removed", Toast.LENGTH_SHORT).show();

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(FavoriteFragment.this).attach(FavoriteFragment.this).commit();
                        }
                    }
                });
                taskRemove.execute("http://192.168.11.213/phpfiles/favorite_remove.php");
            }
        });


        adapter = new FunDapter<>(FavoriteFragment.this.getActivity(), favoriteList, R.layout.favorite_row, dic);
        adapter.notifyDataSetChanged();
        //lv = (ListView)view.findViewById(R.id.lvFavorite);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(this);

        //lv.setOnTouchListener(new View.OnTouchListener() {@Override public boolean onTouch(View v, MotionEvent event) {scrollView.requestDisallowInterceptTouchEvent(true); return false; }});

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

    }
}
