package com.example.socks_android.ui.fragment;

import static java.lang.Integer.*;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;

//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.amigold.fundapter.interfaces.DynamicImageLoader;
import com.amigold.fundapter.interfaces.ItemClickListener;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.example.socks_android.Interfaces.Cart;
import com.example.socks_android.Interfaces.UILConfig;
import com.example.socks_android.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingBasketFragment extends Fragment implements AdapterView.OnItemClickListener, AsyncResponse{
    public static final String PREFS = "prefFile";

    final String LOG = "ShoppingBasketFragment";
    final static String url = "http://192.168.11.213/phpfiles/cart_retrieve.php";

    View view;

    private ArrayList<Cart> itemList;
    private ListView lv;
    private ScrollView scrollView;
    FunDapter<Cart> adapter;
    TextView edQty;
    CheckBox checkBox;
    ImageView ivImage;
    TextView tvName, tvPrice1, tvOption1, tvPrice;
    Button btnRemove;
    ImageButton qty_decrease, qty_increase;


    Button btnCheckout;

    int sum = 0;
    int total = 0;
    int deliveryCharge = 0;

    TextView tvTotal;
    TextView tvSum;
    TextView tvDeliveryCharge;
    TextView ordertest;


    public ShoppingBasketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_shopping_basket, container, false);

        LayoutInflater inflater2 = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view2 = inflater2.inflate(R.layout.cart_row, null);

        lv = (ListView)view.findViewById(R.id.lvCart);

        btnCheckout = (Button)view.findViewById(R.id.btnCheckout);
        tvTotal = (TextView)view.findViewById(R.id.tvTotal);
        tvSum = (TextView)view.findViewById(R.id.tvSum);
        tvDeliveryCharge = (TextView)view.findViewById(R.id.tvDeliveryCharge);
        ordertest = (TextView)view.findViewById(R.id.orderTest);

        edQty = (TextView)view2.findViewById(R.id.edQty);
        checkBox = (CheckBox)view2.findViewById(R.id.checkBox);
        ivImage = (ImageView)view2.findViewById(R.id.ivImage);
        tvName = (TextView)view2.findViewById(R.id.tvName);
        tvOption1 = (TextView)view2.findViewById(R.id.tvOption1);
        tvPrice1 = (TextView)view2.findViewById(R.id.tvPrice1);
        tvPrice = (TextView)view2.findViewById(R.id.tvPrice);
        btnRemove = (Button)view2.findViewById(R.id.btnRemove);
        qty_decrease = (ImageButton)view2.findViewById(R.id.qty_decrease);
        qty_increase = (ImageButton)view2.findViewById(R.id.qty_increase);



        // 전체 선택 구현
        CheckBox allSelectBtn = (CheckBox)view.findViewById(R.id.allSelectBtn);
        allSelectBtn.setOnClickListener(new CheckBox.OnClickListener(){
            @Override
            public void onClick(View view) {
                int cnt = adapter.getCount(); // 리스트뷰 리스트 개수 구하기
                for(int i=0; i<cnt; i++) {
                    lv.setItemChecked(i, true);
                    checkBox.setChecked(true);
                }
                adapter.notifyDataSetChanged();
            }
        });

        SharedPreferences preferences = ShoppingBasketFragment.this.getActivity().getSharedPreferences(PREFS, 0);
        String customer = preferences.getString("userID", null);

        HashMap postData = new HashMap();
        postData.put("txtuserID", customer);
        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(ShoppingBasketFragment.this.getActivity(), postData, this);
        taskRead.execute(url);



        return view;
    }

    @Override
    public void processFinish(String s) {
        itemList = new JsonConverter<Cart>().toArrayList(s, Cart.class);

        BindDictionary dic = new BindDictionary();

        dic.addStringField(R.id.tvName, new StringExtractor<Cart>() {
            @Override
            public String getStringValue(Cart item, int position) {
                return item.name;
            }
        });

        dic.addStringField(R.id.tvOption1, new StringExtractor<Cart>() {
            @Override
            public String getStringValue(Cart item, int position) {
                return item.option1;
            }
        });

        dic.addStringField(R.id.tvPrice1, new StringExtractor<Cart>() {
            @Override
            public String getStringValue(Cart item, int position) {
                return String.valueOf(item.price);
            }
        });


        dic.addStringField(R.id.edQty, new StringExtractor<Cart>() {
            @Override
            public String getStringValue(Cart item, int position) {
                return ""+ item.quantity;
            }
        });


        dic.addStringField(R.id.tvPrice, new StringExtractor<Cart>() {
            @Override
            public String getStringValue(Cart item, int position) {
                String price1 = item.price;
                int qty = item.quantity;
                int price = parseInt(price1) * qty;
                return String.valueOf(price);
                //return item.price;
            }
        });


        dic.addDynamicImageField(R.id.ivImage, new StringExtractor<Cart>() {
            @Override
            public String getStringValue(Cart item, int position) {
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

                final Cart selectedItem = itemList.get(position);
                HashMap postData = new HashMap();
                postData.put("ProductID", ""+ selectedItem.id);
                postData.put("userID", ""+ selectedItem.customer);
                postData.put("option1", ""+ selectedItem.option1);


                PostResponseAsyncTask taskRemove = new PostResponseAsyncTask(ShoppingBasketFragment.this.getActivity(),
                        postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(LOG, s);
                        if(s.contains("success"))
                        {
                            itemList.remove(selectedItem);
                            lv.refreshDrawableState();

                            adapter.notifyDataSetChanged();
                            Toast.makeText(ShoppingBasketFragment.this.getActivity(), "Item Removed", Toast.LENGTH_SHORT).show();

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(ShoppingBasketFragment.this).attach(ShoppingBasketFragment.this).commit();
                        }
                    }
                });
                taskRemove.execute("http://192.168.11.213/phpfiles/cart_remove.php");
                totalPrice(lv);
            }
        });

        //increase the item quantity
        dic.addBaseField(R.id.qty_increase).onClick(new ItemClickListener() {
            @Override
            public void onClick(Object item, int position, final View view) {
                final Cart selectedItem = itemList.get(position);
                HashMap postData = new HashMap();
                postData.put("ProductID", ""+ selectedItem.id);
                postData.put("userID", ""+ selectedItem.customer);
                postData.put("option1", ""+ selectedItem.option1);


                final PostResponseAsyncTask incTask = new PostResponseAsyncTask(ShoppingBasketFragment.this.getActivity(),
                        postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {

                        if(s.contains("success"))
                        {
                            lv.refreshDrawableState();
                            adapter.notifyDataSetChanged();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(ShoppingBasketFragment.this).attach(ShoppingBasketFragment.this).commit();
                        }
                    }

                });
                incTask.execute("http://192.168.11.213/phpfiles/cart_qty_inc.php");
                lv.refreshDrawableState();
                adapter.notifyDataSetChanged();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(ShoppingBasketFragment.this).attach(ShoppingBasketFragment.this).commit();
                totalPrice(lv);
            }
        });

        //Decrease the item quantity
        dic.addBaseField(R.id.qty_decrease).onClick(new ItemClickListener() {
            @Override
            public void onClick(Object item, int position, final View view) {
                final Cart selectedItem = itemList.get(position);
                HashMap postData = new HashMap();
                final int[] qty = {selectedItem.quantity};
                final String[] qtyS = new String[1];

                postData.put("ProductID", ""+ selectedItem.id);
                postData.put("userID", ""+ selectedItem.customer);
                postData.put("option1", ""+ selectedItem.option1);

                if(qty[0] > 1)
                {
                    final PostResponseAsyncTask incTask = new PostResponseAsyncTask(ShoppingBasketFragment.this.getActivity(),
                            postData, new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {

                            if(s.contains("success"))
                            {
                                qtyS[0] = String.valueOf(qty[0] + 1);
                                edQty.setText(qtyS[0]);
                                totalPrice(lv);
                                //price = price1 + qty;
                                //parseInt(String.valueOf(edQty.getText())) = parseInt(edQty.getText().toString()) + 1;
                                adapter.notifyDataSetChanged();
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.detach(ShoppingBasketFragment.this).attach(ShoppingBasketFragment.this).commit();
                                lv.refreshDrawableState();
                            }
                        }
                    });
                    incTask.execute("http://192.168.11.213/phpfiles/cart_qty_dec.php");
                    totalPrice(lv);
                }
            }
        });

        adapter = new FunDapter<>(ShoppingBasketFragment.this.getActivity(), itemList, R.layout.cart_row, dic);
        adapter.notifyDataSetChanged();
        lv = (ListView)view.findViewById(R.id.lvCart);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(this);

        scrollView = (ScrollView)view.findViewById(R.id.sv);
        lv.setOnTouchListener(new View.OnTouchListener() {@Override public boolean onTouch(View v, MotionEvent event) {scrollView.requestDisallowInterceptTouchEvent(true); return false; }});

        if(lv.getCount() == 0)
        {
            TextView total = (TextView)view.findViewById(R.id.tvTotal);
            TextView empty = (TextView)view.findViewById(R.id.tvEmpty);

            btnCheckout.setVisibility(View.GONE);
            total.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }

        totalPrice(lv);

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PaymentFragment paymentFragment = new PaymentFragment();
//                FragmentManager manager = getFragmentManager();
//                manager.beginTransaction().replace(R.id.content_main, paymentFragment)
//                        .addToBackStack("Payment").commit();
            }
        });
    }
    private int totalPrice(ListView listView)
    {
//        int sum = 0;
//        int total = 0;
//        int deliveryCharge = 0;
//
//        TextView tvTotal = (TextView)view.findViewById(R.id.tvTotal);
//        TextView tvSum = (TextView)view.findViewById(R.id.tvSum);
//        TextView tvDeliveryCharge = (TextView)view.findViewById(R.id.tvDeliveryCharge);

        int count = listView.getCount();
        for(int i = 0; i < count; i++)
        {
            View v = listView.getAdapter(). getView(i, null, null);
            TextView tv = (TextView) v.findViewById(R.id.tvPrice);
            sum = sum + parseInt(tv.getText().toString());
            if(sum>=15000){
                deliveryCharge = 0;
            }else{
                deliveryCharge = 3000;
            }
            total = sum + deliveryCharge;
        }

        //String s = String.format("%.2f",sum);

        tvTotal.setText("TOTAL PRICE " + total + "원");
        tvSum.setText("상품 금액 " +sum + "원");
        tvDeliveryCharge.setText("배송비 " + deliveryCharge + "원");
        return sum;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

    }
}
