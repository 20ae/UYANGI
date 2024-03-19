package com.example.socks_android.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.socks_android.Interfaces.Favorite;
import com.example.socks_android.Interfaces.Orders;
import com.example.socks_android.R;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class OrdersFragment extends Fragment implements AdapterView.OnItemClickListener, AsyncResponse{
    public static final String PREFS = "prefFile";

    final String LOG = "OrdersFragment";
    final static String url = "http://192.168.11.213/phpfiles/order_retrieve.php";

    View view;
    View view2;

    private ArrayList<Orders> ordersList;
    private ListView lv;

    FunDapter<Orders> adapter;
    TextView txtCancel;


    public OrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_orders, container, false);

        LayoutInflater inflater2 = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view2 = inflater2.inflate(R.layout.orders_row, null);

        lv = (ListView)view.findViewById(R.id.lvOrders);
        txtCancel = (TextView)view2.findViewById(R.id.tvCancel);


        //ImageLoader.getInstance().init(UILConfig.config(FavoriteFragment.this.getActivity()));


        SharedPreferences preferences = OrdersFragment.this.getActivity().getSharedPreferences(PREFS, 0);
        String customer = preferences.getString("userID", null);

        HashMap postData = new HashMap();
        postData.put("txtuserID", customer);
        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(OrdersFragment.this.getActivity(), postData, this);
        taskRead.execute(url);

        return view;
    }

    @Override
    public void processFinish(String s) {
        ordersList = new JsonConverter<Orders>().toArrayList(s, Orders.class);

        BindDictionary dic = new BindDictionary();

        dic.addStringField(R.id.tvName, new StringExtractor<Orders>() {
            @Override
            public String getStringValue(Orders item, int position) {
                return item.name;
            }
        });

        dic.addStringField(R.id.tvOrderDate, new StringExtractor<Orders>() {
            @Override
            public String getStringValue(Orders item, int position) { return item.order_date; }
        });

        dic.addStringField(R.id.tvStatus, new StringExtractor<Orders>() {
            @Override
            public String getStringValue(Orders item, int position) {
                return item.order_status;
            }
        });

        dic.addStringField(R.id.tvPaymentMethod, new StringExtractor<Orders>() {
            @Override
            public String getStringValue(Orders item, int position) {
                return item.payment_method;
            }
        });

        dic.addStringField(R.id.tvPrice1, new StringExtractor<Orders>() {
            @Override
            public String getStringValue(Orders item, int position) {
                return String.valueOf(item.price);
            }
        });

        dic.addStringField(R.id.tvOrderNumber, new StringExtractor<Orders>() {
            @Override
            public String getStringValue(Orders item, int position) {
                return item.order_number;
            }
        });


        //주문취소 요청
        dic.addBaseField(R.id.tvCancel).onClick(new ItemClickListener() {

            @Override
            public void onClick(Object item, int position, View view) {

                final Orders selectedItem = ordersList.get(position);
                HashMap postData = new HashMap();
                postData.put("CustomerID", ""+ selectedItem.id);
                postData.put("OrderNumber", ""+ selectedItem.order_number);


                PostResponseAsyncTask taskRemove = new PostResponseAsyncTask(OrdersFragment.this.getActivity(),
                        postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(LOG, s);
                        if(s.contains("success"))
                        {
                            selectedItem.order_status = "취소요청";
                            lv.refreshDrawableState();

                            adapter.notifyDataSetChanged();
                            Toast.makeText(OrdersFragment.this.getActivity(), "취소요청 완료", Toast.LENGTH_SHORT).show();

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(OrdersFragment.this).attach(OrdersFragment.this).commit();
                        }
                    }
                });
                taskRemove.execute("http://192.168.11.213/phpfiles/order_cancel.php");
            }
        });


        adapter = new FunDapter<>(OrdersFragment.this.getActivity(), ordersList, R.layout.orders_row, dic);
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
