package com.example.socks_android.ui.fragment;

import static java.lang.Integer.parseInt;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
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

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.amigold.fundapter.interfaces.DynamicImageLoader;
import com.amigold.fundapter.interfaces.ItemClickListener;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.socks_android.CartInsertRequest;
import com.example.socks_android.ColorActivity;
import com.example.socks_android.Interfaces.Cart;
import com.example.socks_android.OrderInsertRequest;
import com.example.socks_android.ProductDetailActivity;
import com.example.socks_android.R;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingBasketFragment2 extends Fragment {
    public static final String PREFS = "prefFile";
    private static String TAG = "ShoppingBasketFragment2";
    private static final String TAG_JSON="result";
    private static final String TAG_ID = "ProductID";
    private static final String TAG_NAME = "ProductName";
    private static final String TAG_OPTION = "option1";
    private static final String TAG_QUANTITY = "quantity";
    private static final String TAG_PRICE = "price";
    private static final String TAG_IMG = "img_url";
    String mJsonString = null;

    final String LOG = "ShoppingBasketFragment2";
    String url;

    View view;

    private ArrayList<Cart> itemList;
    private ListView lv;
    private ScrollView scrollView;
    FunDapter<Cart> adapter;
    TextView noneText2;

    String customer;

    Button btnCheckout;


    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5;
    ImageView ivImage1, ivImage2, ivImage3, ivImage4, ivImage5;
    TextView tvName1, tvName2, tvName3, tvName4, tvName5;
    TextView tvOption1, tvOption2, tvOption3, tvOption4, tvOption5;
    TextView tvPrice1, tvPrice2, tvPrice3, tvPrice4, tvPrice5, tv_pid1, tv_pid2, tv_pid3, tv_pid4, tv_pid5;
    Button btnRemove1, btnRemove2, btnRemove3, btnRemove4, btnRemove5;
    ImageButton qty_decrease1, qty_decrease2, qty_decrease3, qty_decrease4, qty_decrease5, qty_increase1, qty_increase2, qty_increase3, qty_increase4, qty_increase5;
    TextView edQty1, edQty2, edQty3, edQty4, edQty5;
    TextView tvPriceS1, tvPriceS2, tvPriceS3, tvPriceS4, tvPriceS5, tv;
    CardView cv1, cv2, cv3, cv4, cv5;

    TextView tvTotal, tvSum, tvDeliveryCharge;
    int sum = 0;
    int total = 0;
    int deliveryCharge = 0;

    ArrayList<HashMap<String, String>> mArrayList;

    int item_number = 0;

    public ShoppingBasketFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_shopping_basket2, container, false);

//        checkBox1 = view.findViewById(R.id.checkBox1);
//        checkBox2 = view.findViewById(R.id.checkBox2);
//        checkBox3 = view.findViewById(R.id.checkBox3);
//        checkBox4 = view.findViewById(R.id.checkBox4);
//        checkBox5 = view.findViewById(R.id.checkBox5);

        ivImage1 = view.findViewById(R.id.ivImage1);
        ivImage2 = view.findViewById(R.id.ivImage2);
        ivImage3 = view.findViewById(R.id.ivImage3);
        ivImage4 = view.findViewById(R.id.ivImage4);
        ivImage5 = view.findViewById(R.id.ivImage5);

        tvName1 = view.findViewById(R.id.tvName1);
        tvName2 = view.findViewById(R.id.tvName2);
        tvName3 = view.findViewById(R.id.tvName3);
        tvName4 = view.findViewById(R.id.tvName4);
        tvName5 = view.findViewById(R.id.tvName5);

        tvOption1 = view.findViewById(R.id.tvOption1);
        tvOption2 = view.findViewById(R.id.tvOption2);
        tvOption3 = view.findViewById(R.id.tvOption3);
        tvOption4 = view.findViewById(R.id.tvOption4);
        tvOption5 = view.findViewById(R.id.tvOption5);

        tvPrice1 = view.findViewById(R.id.tvPrice1);
        tvPrice2 = view.findViewById(R.id.tvPrice2);
        tvPrice3 = view.findViewById(R.id.tvPrice3);
        tvPrice4 = view.findViewById(R.id.tvPrice4);
        tvPrice5 = view.findViewById(R.id.tvPrice5);

        tvPriceS1 = view.findViewById(R.id.tvPriceS1);
        tvPriceS2 = view.findViewById(R.id.tvPriceS2);
        tvPriceS3 = view.findViewById(R.id.tvPriceS3);
        tvPriceS4 = view.findViewById(R.id.tvPriceS4);
        tvPriceS5 = view.findViewById(R.id.tvPriceS5);

        tv_pid1 = view.findViewById(R.id.tv_pid1);
        tv_pid2 = view.findViewById(R.id.tv_pid2);
        tv_pid3 = view.findViewById(R.id.tv_pid3);
        tv_pid4 = view.findViewById(R.id.tv_pid4);
        tv_pid5 = view.findViewById(R.id.tv_pid5);


        btnRemove1 = view.findViewById(R.id.btnRemove1);
        btnRemove2 = view.findViewById(R.id.btnRemove2);
        btnRemove3 = view.findViewById(R.id.btnRemove3);
        btnRemove4 = view.findViewById(R.id.btnRemove4);
        btnRemove5 = view.findViewById(R.id.btnRemove5);

        edQty1 = view.findViewById(R.id.edQty1);
        edQty2 = view.findViewById(R.id.edQty2);
        edQty3 = view.findViewById(R.id.edQty3);
        edQty4 = view.findViewById(R.id.edQty4);
        edQty5 = view.findViewById(R.id.edQty5);

        qty_decrease1 = view.findViewById(R.id.qty_decrease1);
        qty_decrease2 = view.findViewById(R.id.qty_decrease2);
        qty_decrease3 = view.findViewById(R.id.qty_decrease3);
        qty_decrease4 = view.findViewById(R.id.qty_decrease4);
        qty_decrease5 = view.findViewById(R.id.qty_decrease5);

        qty_increase1 = view.findViewById(R.id.qty_increase1);
        qty_increase2 = view.findViewById(R.id.qty_increase2);
        qty_increase3 = view.findViewById(R.id.qty_increase3);
        qty_increase4 = view.findViewById(R.id.qty_increase4);
        qty_increase5 = view.findViewById(R.id.qty_increase5);

        cv1 = view.findViewById(R.id.cv1);
        cv2 = view.findViewById(R.id.cv2);
        cv3 = view.findViewById(R.id.cv3);
        cv4 = view.findViewById(R.id.cv4);
        cv5 = view.findViewById(R.id.cv5);


        btnCheckout = (Button) view.findViewById(R.id.btnCheckout);
        tvTotal = (TextView) view.findViewById(R.id.tvTotal);
        tvSum = (TextView) view.findViewById(R.id.tvSum);
        tvDeliveryCharge = (TextView) view.findViewById(R.id.tvDeliveryCharge);
        tv = (TextView) view.findViewById(R.id.tvPrice);
        noneText2 = (TextView) view.findViewById(R.id.noneText2);


        // 전체 선택 구현
//        CheckBox allSelectBtn = (CheckBox) view.findViewById(R.id.allSelectBtn);
//        allSelectBtn.setOnClickListener(new CheckBox.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               checkBox1.setChecked(true);
//               checkBox2.setChecked(true);
//               checkBox3.setChecked(true);
//               checkBox4.setChecked(true);
//               checkBox5.setChecked(true);
//            }
//        });

        SharedPreferences preferences = ShoppingBasketFragment2.this.getActivity().getSharedPreferences(PREFS, 0);
        customer = preferences.getString("userID", null);

        url = "http://192.168.11.213/phpfiles/cart_retrieve2.php?customer=" + customer;

        ShoppingBasketFragment2.GetData task = new GetData();
        task.execute(url);

        //totalPrice();

        mArrayList = new ArrayList<>();

        // remove 버튼 눌렀을 때 이벤트
        btnRemove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap postData = new HashMap();
                postData.put("ProductID", ""+ tv_pid1.getText());
                postData.put("userID", ""+ customer);
                postData.put("option1", ""+ tvOption1.getText());


                PostResponseAsyncTask taskRemove = new PostResponseAsyncTask(ShoppingBasketFragment2.this.getActivity(),
                        postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(LOG, s);
                        if(s.contains("success"))
                        {
                            tvPriceS1.setText("0");
                            cv1.setVisibility(view.GONE);
                            totalPrice();
                            //Toast.makeText(ShoppingBasketFragment2.this.getActivity(), "Item Removed", //Toast.LENGTH_SHORT).show();

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(ShoppingBasketFragment2.this).attach(ShoppingBasketFragment2.this).commit();
                        }
                    }
                });
                taskRemove.execute("http://192.168.11.213/phpfiles/cart_remove.php");
                totalPrice();
            }
        });

        btnRemove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap postData = new HashMap();
                postData.put("ProductID", ""+ tv_pid2.getText());
                postData.put("userID", ""+ customer);
                postData.put("option1", ""+ tvOption2.getText());


                PostResponseAsyncTask taskRemove = new PostResponseAsyncTask(ShoppingBasketFragment2.this.getActivity(),
                        postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(LOG, s);
                        if(s.contains("success"))
                        {
                            tvPriceS2.setText("0");
                            cv2.setVisibility(view.GONE);
                            totalPrice();
                            //Toast.makeText(ShoppingBasketFragment2.this.getActivity(), "Item Removed", //Toast.LENGTH_SHORT).show();

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(ShoppingBasketFragment2.this).attach(ShoppingBasketFragment2.this).commit();
                        }
                    }
                });
                taskRemove.execute("http://192.168.11.213/phpfiles/cart_remove.php");
                totalPrice();
            }
        });

        btnRemove3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap postData = new HashMap();
                postData.put("ProductID", ""+ tv_pid3.getText());
                postData.put("userID", ""+ customer);
                postData.put("option1", ""+ tvOption3.getText());


                PostResponseAsyncTask taskRemove = new PostResponseAsyncTask(ShoppingBasketFragment2.this.getActivity(),
                        postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(LOG, s);
                        if(s.contains("success"))
                        {
                            tvPriceS3.setText("0");
                            cv3.setVisibility(view.GONE);
                            totalPrice();
                            //Toast.makeText(ShoppingBasketFragment2.this.getActivity(), "Item Removed", //Toast.LENGTH_SHORT).show();

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(ShoppingBasketFragment2.this).attach(ShoppingBasketFragment2.this).commit();
                        }
                    }
                });
                taskRemove.execute("http://192.168.11.213/phpfiles/cart_remove.php");
                totalPrice();
            }
        });

        btnRemove4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap postData = new HashMap();
                postData.put("ProductID", ""+ tv_pid4.getText());
                postData.put("userID", ""+ customer);
                postData.put("option1", ""+ tvOption4.getText());


                PostResponseAsyncTask taskRemove = new PostResponseAsyncTask(ShoppingBasketFragment2.this.getActivity(),
                        postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(LOG, s);
                        if(s.contains("success"))
                        {
                            tvPriceS4.setText("0");
                            cv4.setVisibility(view.GONE);
                            totalPrice();
                            //Toast.makeText(ShoppingBasketFragment2.this.getActivity(), "Item Removed", //Toast.LENGTH_SHORT).show();

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(ShoppingBasketFragment2.this).attach(ShoppingBasketFragment2.this).commit();
                        }
                    }
                });
                taskRemove.execute("http://192.168.11.213/phpfiles/cart_remove.php");
                totalPrice();
            }
        });

        btnRemove5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap postData = new HashMap();
                postData.put("ProductID", ""+ tv_pid5.getText());
                postData.put("userID", ""+ customer);
                postData.put("option1", ""+ tvOption5.getText());


                PostResponseAsyncTask taskRemove = new PostResponseAsyncTask(ShoppingBasketFragment2.this.getActivity(),
                        postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(LOG, s);
                        if(s.contains("success"))
                        {
                            tvPriceS5.setText("0");
                            cv5.setVisibility(view.GONE);
                            totalPrice();
                            //Toast.makeText(ShoppingBasketFragment2.this.getActivity(), "Item Removed", //Toast.LENGTH_SHORT).show();

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(ShoppingBasketFragment2.this).attach(ShoppingBasketFragment2.this).commit();
                        }
                    }
                });
                taskRemove.execute("http://192.168.11.213/phpfiles/cart_remove.php");
                totalPrice();
            }
        });

        // - 버튼 눌렀을 때 이벤트
        qty_decrease1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap postData = new HashMap();
                postData.put("ProductID", ""+ tv_pid1.getText());
                postData.put("userID", ""+ customer);
                postData.put("option1", ""+ tvOption1.getText());

                PostResponseAsyncTask decTask = new PostResponseAsyncTask(ShoppingBasketFragment2.this.getActivity(),
                        postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(LOG, s);
                        if(s.contains("success"))
                        {
                            edQty1.setText(String.valueOf(Integer.parseInt(edQty1.getText().toString()) - 1));
                            tvPriceS1.setText(String.valueOf(Integer.parseInt(edQty1.getText().toString()) * Integer.parseInt(tvPrice1.getText().toString())));
                            totalPrice();
                            //Toast.makeText(ShoppingBasketFragment2.this.getActivity(), "상품 수량 -1", //Toast.LENGTH_SHORT).show();

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(ShoppingBasketFragment2.this).attach(ShoppingBasketFragment2.this).commit();
                        }
                    }
                });
                decTask.execute("http://192.168.11.213/phpfiles/cart_qty_dec.php");
                tvPriceS1.setText(String.valueOf(Integer.parseInt(edQty1.getText().toString()) * Integer.parseInt(tvPrice1.getText().toString())));
                totalPrice();
            }
        });

        qty_decrease2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap postData = new HashMap();
                postData.put("ProductID", ""+ tv_pid2.getText());
                postData.put("userID", ""+ customer);
                postData.put("option1", ""+ tvOption2.getText());

                PostResponseAsyncTask decTask = new PostResponseAsyncTask(ShoppingBasketFragment2.this.getActivity(),
                        postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(LOG, s);
                        if(s.contains("success"))
                        {
                            edQty2.setText(String.valueOf(Integer.parseInt(edQty2.getText().toString()) - 1));
                            tvPriceS2.setText(String.valueOf(Integer.parseInt(edQty2.getText().toString()) * Integer.parseInt(tvPrice2.getText().toString())));
                            totalPrice();
                            //Toast.makeText(ShoppingBasketFragment2.this.getActivity(), "상품 수량 -1", //Toast.LENGTH_SHORT).show();

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(ShoppingBasketFragment2.this).attach(ShoppingBasketFragment2.this).commit();
                        }
                    }
                });
                decTask.execute("http://192.168.11.213/phpfiles/cart_qty_dec.php");
                tvPriceS2.setText(String.valueOf(Integer.parseInt(edQty2.getText().toString()) * Integer.parseInt(tvPrice2.getText().toString())));
                totalPrice();
            }
        });

        qty_decrease3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap postData = new HashMap();
                postData.put("ProductID", ""+ tv_pid3.getText());
                postData.put("userID", ""+ customer);
                postData.put("option1", ""+ tvOption3.getText());

                PostResponseAsyncTask decTask = new PostResponseAsyncTask(ShoppingBasketFragment2.this.getActivity(),
                        postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(LOG, s);
                        if(s.contains("success"))
                        {
                            edQty3.setText(String.valueOf(Integer.parseInt(edQty3.getText().toString()) - 1));
                            tvPriceS3.setText(String.valueOf(Integer.parseInt(edQty3.getText().toString()) * Integer.parseInt(tvPrice3.getText().toString())));
                            totalPrice();
                            //Toast.makeText(ShoppingBasketFragment2.this.getActivity(), "상품 수량 -1", //Toast.LENGTH_SHORT).show();

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(ShoppingBasketFragment2.this).attach(ShoppingBasketFragment2.this).commit();
                        }
                    }
                });
                decTask.execute("http://192.168.11.213/phpfiles/cart_qty_dec.php");
                tvPriceS3.setText(String.valueOf(Integer.parseInt(edQty3.getText().toString()) * Integer.parseInt(tvPrice3.getText().toString())));
                totalPrice();
            }
        });

        qty_decrease4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap postData = new HashMap();
                postData.put("ProductID", ""+ tv_pid4.getText());
                postData.put("userID", ""+ customer);
                postData.put("option1", ""+ tvOption4.getText());

                PostResponseAsyncTask decTask = new PostResponseAsyncTask(ShoppingBasketFragment2.this.getActivity(),
                        postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(LOG, s);
                        if(s.contains("success"))
                        {
                            edQty4.setText(String.valueOf(Integer.parseInt(edQty4.getText().toString()) - 1));
                            tvPriceS4.setText(String.valueOf(Integer.parseInt(edQty4.getText().toString()) * Integer.parseInt(tvPrice4.getText().toString())));
                            totalPrice();
                            //Toast.makeText(ShoppingBasketFragment2.this.getActivity(), "상품 수량 -1", //Toast.LENGTH_SHORT).show();

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(ShoppingBasketFragment2.this).attach(ShoppingBasketFragment2.this).commit();
                        }
                    }
                });
                decTask.execute("http://192.168.11.213/phpfiles/cart_qty_dec.php");
                tvPriceS4.setText(String.valueOf(Integer.parseInt(edQty4.getText().toString()) * Integer.parseInt(tvPrice4.getText().toString())));
                totalPrice();
            }
        });

        qty_decrease5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap postData = new HashMap();
                postData.put("ProductID", ""+ tv_pid5.getText());
                postData.put("userID", ""+ customer);
                postData.put("option1", ""+ tvOption5.getText());

                PostResponseAsyncTask decTask = new PostResponseAsyncTask(ShoppingBasketFragment2.this.getActivity(),
                        postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(LOG, s);
                        if(s.contains("success"))
                        {
                            edQty5.setText(String.valueOf(Integer.parseInt(edQty5.getText().toString()) - 1));
                            tvPriceS5.setText(String.valueOf(Integer.parseInt(edQty5.getText().toString()) * Integer.parseInt(tvPrice5.getText().toString())));
                            totalPrice();
                            //Toast.makeText(ShoppingBasketFragment2.this.getActivity(), "상품 수량 -1", //Toast.LENGTH_SHORT).show();

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(ShoppingBasketFragment2.this).attach(ShoppingBasketFragment2.this).commit();
                        }
                    }
                });
                decTask.execute("http://192.168.11.213/phpfiles/cart_qty_dec.php");
                tvPriceS5.setText(String.valueOf(Integer.parseInt(edQty5.getText().toString()) * Integer.parseInt(tvPrice5.getText().toString())));
                totalPrice();
            }
        });

        // + 버튼 눌렀을 때 이벤트
        qty_increase1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap postData = new HashMap();
                postData.put("ProductID", ""+ tv_pid1.getText());
                postData.put("userID", ""+ customer);
                postData.put("option1", ""+ tvOption1.getText());

                PostResponseAsyncTask incTask = new PostResponseAsyncTask(ShoppingBasketFragment2.this.getActivity(),
                        postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(LOG, s);
                        if(s.contains("success"))
                        {
                            edQty1.setText(String.valueOf(Integer.parseInt(edQty1.getText().toString()) + 1));
                            tvPriceS1.setText(String.valueOf(Integer.parseInt(edQty1.getText().toString()) * Integer.parseInt(tvPrice1.getText().toString())));
                            totalPrice();
                            //Toast.makeText(ShoppingBasketFragment2.this.getActivity(), "상품 수량 +1", //Toast.LENGTH_SHORT).show();

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(ShoppingBasketFragment2.this).attach(ShoppingBasketFragment2.this).commit();
                        }
                    }
                });
                incTask.execute("http://192.168.11.213/phpfiles/cart_qty_inc.php");
                tvPriceS1.setText(String.valueOf(Integer.parseInt(edQty1.getText().toString()) * Integer.parseInt(tvPrice1.getText().toString())));
                totalPrice();
            }
        });

        qty_increase2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap postData = new HashMap();
                postData.put("ProductID", ""+ tv_pid2.getText());
                postData.put("userID", ""+ customer);
                postData.put("option1", ""+ tvOption2.getText());

                PostResponseAsyncTask incTask = new PostResponseAsyncTask(ShoppingBasketFragment2.this.getActivity(),
                        postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(LOG, s);
                        if(s.contains("success"))
                        {
                            edQty2.setText(String.valueOf(Integer.parseInt(edQty2.getText().toString()) + 1));
                            tvPriceS2.setText(String.valueOf(Integer.parseInt(edQty2.getText().toString()) * Integer.parseInt(tvPrice2.getText().toString())));
                            totalPrice();
                            //Toast.makeText(ShoppingBasketFragment2.this.getActivity(), "상품 수량 +1", //Toast.LENGTH_SHORT).show();

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(ShoppingBasketFragment2.this).attach(ShoppingBasketFragment2.this).commit();
                        }
                    }
                });
                incTask.execute("http://192.168.11.213/phpfiles/cart_qty_inc.php");
                tvPriceS2.setText(String.valueOf(Integer.parseInt(edQty2.getText().toString()) * Integer.parseInt(tvPrice2.getText().toString())));
                totalPrice();
            }
        });

        qty_increase3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap postData = new HashMap();
                postData.put("ProductID", ""+ tv_pid3.getText());
                postData.put("userID", ""+ customer);
                postData.put("option1", ""+ tvOption3.getText());

                PostResponseAsyncTask incTask = new PostResponseAsyncTask(ShoppingBasketFragment2.this.getActivity(),
                        postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(LOG, s);
                        if(s.contains("success"))
                        {
                            edQty3.setText(String.valueOf(Integer.parseInt(edQty3.getText().toString()) + 1));
                            tvPriceS3.setText(String.valueOf(Integer.parseInt(edQty3.getText().toString()) * Integer.parseInt(tvPrice3.getText().toString())));
                            totalPrice();
                            //Toast.makeText(ShoppingBasketFragment2.this.getActivity(), "상품 수량 +1", //Toast.LENGTH_SHORT).show();

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(ShoppingBasketFragment2.this).attach(ShoppingBasketFragment2.this).commit();
                        }
                    }
                });
                incTask.execute("http://192.168.11.213/phpfiles/cart_qty_inc.php");
                tvPriceS3.setText(String.valueOf(Integer.parseInt(edQty3.getText().toString()) * Integer.parseInt(tvPrice3.getText().toString())));
                totalPrice();
            }
        });

        qty_increase4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap postData = new HashMap();
                postData.put("ProductID", ""+ tv_pid4.getText());
                postData.put("userID", ""+ customer);
                postData.put("option1", ""+ tvOption4.getText());

                PostResponseAsyncTask incTask = new PostResponseAsyncTask(ShoppingBasketFragment2.this.getActivity(),
                        postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(LOG, s);
                        if(s.contains("success"))
                        {
                            edQty4.setText(String.valueOf(Integer.parseInt(edQty4.getText().toString()) + 1));
                            tvPriceS4.setText(String.valueOf(Integer.parseInt(edQty4.getText().toString()) * Integer.parseInt(tvPrice4.getText().toString())));
                            totalPrice();
                            //Toast.makeText(ShoppingBasketFragment2.this.getActivity(), "상품 수량 +1", //Toast.LENGTH_SHORT).show();

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(ShoppingBasketFragment2.this).attach(ShoppingBasketFragment2.this).commit();
                        }
                    }
                });
                incTask.execute("http://192.168.11.213/phpfiles/cart_qty_inc.php");
                tvPriceS4.setText(String.valueOf(Integer.parseInt(edQty4.getText().toString()) * Integer.parseInt(tvPrice4.getText().toString())));
                totalPrice();
            }
        });

        qty_increase5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap postData = new HashMap();
                postData.put("ProductID", ""+ tv_pid5.getText());
                postData.put("userID", ""+ customer);
                postData.put("option1", ""+ tvOption5.getText());

                PostResponseAsyncTask incTask = new PostResponseAsyncTask(ShoppingBasketFragment2.this.getActivity(),
                        postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(LOG, s);
                        if(s.contains("success"))
                        {
                            edQty5.setText(String.valueOf(Integer.parseInt(edQty5.getText().toString()) + 1));
                            tvPriceS5.setText(String.valueOf(Integer.parseInt(edQty5.getText().toString()) * Integer.parseInt(tvPrice5.getText().toString())));
                            totalPrice();
                            //Toast.makeText(ShoppingBasketFragment2.this.getActivity(), "상품 수량 +1", //Toast.LENGTH_SHORT).show();

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(ShoppingBasketFragment2.this).attach(ShoppingBasketFragment2.this).commit();
                        }
                    }
                });
                incTask.execute("http://192.168.11.213/phpfiles/cart_qty_inc.php");
                tvPriceS5.setText(String.valueOf(Integer.parseInt(edQty5.getText().toString()) * Integer.parseInt(tvPrice5.getText().toString())));
                totalPrice();
            }
        });

        // 결제하기 버튼 누르면 order 테이블로 주문 내역 보냄
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                //Toast.makeText(ShoppingBasketFragment2.this.getActivity(), "주문 완료", //Toast.LENGTH_SHORT).show();
                            } else {
                                //Toast.makeText(ShoppingBasketFragment2.this.getActivity(), "주문에 실패했습니다.", //Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                String order1, order2, order3, order4, order5, orderList = "";
                if(tvName1.getText()!= null){
                    order1 = tvName1.getText().toString() + " 옵션: " + tvOption1.getText().toString() + " 수량: " + edQty1.getText().toString() + "개/";
                }else {order1 = "";}
                if(tvName2.getText()!= null){
                    order2 = tvName2.getText().toString() + " 옵션: " + tvOption2.getText().toString() + " 수량: " + edQty2.getText().toString() + "개/";
                }else {order2 = "";}
                if(tvName3.getText()!= null){
                    order3 = tvName3.getText().toString() + " 옵션: " + tvOption3.getText().toString() + " 수량: " + edQty3.getText().toString() + "개/";
                }else {order3 = "";}
                if(tvName4.getText()!= null){
                    order4 = tvName4.getText().toString() + " 옵션: " + tvOption4.getText().toString() + " 수량: " + edQty4.getText().toString() + "개/";
                }else {order4 = "";}
                if(tvName5.getText()!= null){
                    order5 = tvName5.getText().toString() + " 옵션: " + tvOption5.getText().toString() + " 수량: " + edQty5.getText().toString() + "개/";
                }else {order5 = "";}

                //orderList = order1 + order2 + order3 + order4 + order5;

                if (item_number == 0) {
                    orderList = "";
                }else if (item_number == 1){
                    orderList = order1;
                }else if (item_number == 2){
                    orderList = order1 + order2;
                }else if (item_number == 3){
                    orderList = order1 + order2 + order3 ;
                }else if (item_number == 4){
                    orderList = order1 + order2 + order3 + order4;
                }else if (item_number >= 5){
                    orderList = order1 + order2 + order3 + order4 + order5;
                }

                Log.d("SBF2", orderList);

                // 서버로 Volley를 이용해서 요청을 함.
                OrderInsertRequest OrderInsertRequest = new OrderInsertRequest(customer, orderList, String.valueOf(total), responseListener);
                RequestQueue queue = Volley.newRequestQueue(ShoppingBasketFragment2.this.getActivity());
                queue.add(OrderInsertRequest);
            }
        });


        return view;
    }

    class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ShoppingBasketFragment2.this.getActivity(),
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
//            mTextViewResult.setText(result);
            Log.d(TAG, "response  - " + result);
            mJsonString = result;
            showResult();
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            item_number = jsonArray.length();
            Log.d("jsonArray.length", String.valueOf(item_number));

            if (item_number == 0) {
                noneText2.setVisibility(View.VISIBLE);
                cv1.setVisibility(View.GONE);
                cv2.setVisibility(View.GONE);
                cv3.setVisibility(View.GONE);
                cv4.setVisibility(View.GONE);
                cv5.setVisibility(View.GONE);
            }else if (item_number == 1) {
                cv1.setVisibility(View.VISIBLE);
                cv2.setVisibility(View.GONE);
                cv3.setVisibility(View.GONE);
                cv4.setVisibility(View.GONE);
                cv5.setVisibility(View.GONE);
            }
            else if (item_number == 2) {
                cv1.setVisibility(View.VISIBLE);
                cv2.setVisibility(View.VISIBLE);
                cv3.setVisibility(View.GONE);
                cv4.setVisibility(View.GONE);
                cv5.setVisibility(View.GONE);
            }
            else if (item_number == 3) {
                cv1.setVisibility(View.VISIBLE);
                cv2.setVisibility(View.VISIBLE);
                cv3.setVisibility(View.VISIBLE);
                cv4.setVisibility(View.GONE);
                cv5.setVisibility(View.GONE);
            }
            else if (item_number == 4) {
                cv1.setVisibility(View.VISIBLE);
                cv2.setVisibility(View.VISIBLE);
                cv3.setVisibility(View.VISIBLE);
                cv4.setVisibility(View.VISIBLE);
                cv5.setVisibility(View.GONE);
            }
            else if (item_number >= 5) {
                cv1.setVisibility(View.VISIBLE);
                cv2.setVisibility(View.VISIBLE);
                cv3.setVisibility(View.VISIBLE);
                cv4.setVisibility(View.VISIBLE);
                cv5.setVisibility(View.VISIBLE);
            }


            for (int i = 0; i < item_number; i++) {

                HashMap<String, String> hashMap = new HashMap<>();

                JSONObject item = jsonArray.getJSONObject(i);

                String ProductID = item.getString(TAG_ID);
                String ProductName = item.getString(TAG_NAME);
                String option1 = item.getString(TAG_OPTION);
                //int quantity = item.getInt("quantity");
                String quantity = String.valueOf(item.getInt(TAG_QUANTITY));
                String price = item.getString(TAG_PRICE);
                String img_url = item.getString(TAG_IMG);


                hashMap.put(TAG_ID, ProductID);
                hashMap.put(TAG_NAME, ProductName);
                hashMap.put(TAG_OPTION, option1);
                hashMap.put(TAG_QUANTITY, quantity);
                hashMap.put(TAG_PRICE, price);
                hashMap.put(TAG_IMG, img_url);


                mArrayList.add(hashMap);
                //Log.d("mArray", mArrayList.get(i).toString());
                Log.d("mArrayList size", String.valueOf(mArrayList.size()));
            }

            for(int i = 0; i<mArrayList.size(); i++) { //mArrayList.size()
                HashMap<String, String> hashMap2 = mArrayList.get(i);
                switch (i) {
                    case 0:
                        if(hashMap2.get(TAG_NAME) != null){
                            tvName1.setText(hashMap2.get(TAG_NAME));
                            tvOption1.setText(hashMap2.get(TAG_OPTION));
                            edQty1.setText(hashMap2.get(TAG_QUANTITY));
                            tvPrice1.setText(hashMap2.get(TAG_PRICE));
                            tv_pid1.setText(hashMap2.get(TAG_ID));
                            Picasso.get().load(hashMap2.get(TAG_IMG)).into(ivImage1);
//                        ivImage1.setAdjustViewBounds(true);
//                        ivImage1.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                        ivImage1.setPadding(0, 0, 0, 10);
//                        ivImage1.setAdjustViewBounds(true);
                            int sumS1 = Integer.parseInt(edQty1.getText().toString()) * Integer.parseInt(tvPrice1.getText().toString());
                            tvPriceS1.setText(String.valueOf(sumS1));
                        }else {
                            cv1.setVisibility(View.GONE);
                        }


                    case 1:
                        if(hashMap2.get(TAG_NAME) != null){
                            tvName2.setText(hashMap2.get(TAG_NAME));
                            tvOption2.setText(hashMap2.get(TAG_OPTION));
                            edQty2.setText(hashMap2.get(TAG_QUANTITY));
                            tvPrice2.setText(hashMap2.get(TAG_PRICE));
                            tv_pid2.setText(hashMap2.get(TAG_ID));
                            Picasso.get().load(hashMap2.get(TAG_IMG)).into(ivImage2);
                            int sumS2 = Integer.parseInt(edQty2.getText().toString()) * Integer.parseInt(tvPrice2.getText().toString());
                            tvPriceS2.setText(String.valueOf(sumS2));
                        }else {
                            cv2.setVisibility(View.GONE);
                        }

                    case 2:
                        if(hashMap2.get(TAG_NAME) != null){
                            tvName3.setText(hashMap2.get(TAG_NAME));
                            tvOption3.setText(hashMap2.get(TAG_OPTION));
                            edQty3.setText(hashMap2.get(TAG_QUANTITY));
                            tvPrice3.setText(hashMap2.get(TAG_PRICE));
                            tv_pid3.setText(hashMap2.get(TAG_ID));
                            Picasso.get().load(hashMap2.get(TAG_IMG)).into(ivImage3);
                            int sumS3 = Integer.parseInt(edQty3.getText().toString()) * Integer.parseInt(tvPrice3.getText().toString());
                            tvPriceS3.setText(String.valueOf(sumS3));
                        }else {
                            cv3.setVisibility(View.GONE);
                        }

                    case 3:
                        if(hashMap2.get(TAG_NAME) != null){
                            tvName4.setText(hashMap2.get(TAG_NAME));
                            tvOption4.setText(hashMap2.get(TAG_OPTION));
                            edQty4.setText(hashMap2.get(TAG_QUANTITY));
                            tvPrice4.setText(hashMap2.get(TAG_PRICE));
                            tv_pid4.setText(hashMap2.get(TAG_ID));
                            Picasso.get().load(hashMap2.get(TAG_IMG)).into(ivImage4);
                            int sumS4 = Integer.parseInt(edQty4.getText().toString()) * Integer.parseInt(tvPrice4.getText().toString());
                            tvPriceS4.setText(String.valueOf(sumS4));
                        }else {
                            cv4.setVisibility(View.GONE);
                        }

                    case 4:
                        if(hashMap2.get(TAG_NAME) != null){
                            tvName5.setText(hashMap2.get(TAG_NAME));
                            tvOption5.setText(hashMap2.get(TAG_OPTION));
                            edQty5.setText(hashMap2.get(TAG_QUANTITY));
                            tvPrice5.setText(hashMap2.get(TAG_PRICE));
                            tv_pid5.setText(hashMap2.get(TAG_ID));
                            Picasso.get().load(hashMap2.get(TAG_IMG)).into(ivImage5);
                            int sumS5 = Integer.parseInt(edQty5.getText().toString()) * Integer.parseInt(tvPrice5.getText().toString());
                            tvPriceS5.setText(String.valueOf(sumS5));
                        }else {
                            cv5.setVisibility(View.GONE);
                        }

                    default:
                        break;
                }
            }

            totalPrice();

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
            noneText2.setVisibility(View.VISIBLE);
            cv1.setVisibility(View.GONE);
            cv2.setVisibility(View.GONE);
            cv3.setVisibility(View.GONE);
            cv4.setVisibility(View.GONE);
            cv5.setVisibility(View.GONE);
        }

    }

    private void totalPrice() {

        if (item_number == 0) {
            sum = 0;
        }else if (item_number == 1) {
            sum = Integer.parseInt((String) tvPriceS1.getText());
        }
        else if (item_number == 2) {
            sum = Integer.parseInt((String) tvPriceS1.getText()) + Integer.parseInt((String) tvPriceS2.getText());
        }
        else if (item_number == 3) {
            sum = Integer.parseInt((String) tvPriceS1.getText()) + Integer.parseInt((String) tvPriceS2.getText()) + Integer.parseInt((String) tvPriceS3.getText());
        }
        else if (item_number == 4) {
            sum = Integer.parseInt((String) tvPriceS1.getText()) + Integer.parseInt((String) tvPriceS2.getText()) + Integer.parseInt((String) tvPriceS3.getText()) + Integer.parseInt((String) tvPriceS4.getText());
        }
        else if (item_number >= 5) {
            sum = Integer.parseInt((String) tvPriceS1.getText()) + Integer.parseInt((String) tvPriceS2.getText()) + Integer.parseInt((String) tvPriceS3.getText()) + Integer.parseInt((String) tvPriceS4.getText()) + Integer.parseInt((String) tvPriceS5.getText());
        }

        //sum = Integer.parseInt((String) tvPriceS1.getText()) + Integer.parseInt((String) tvPriceS2.getText()) + Integer.parseInt((String) tvPriceS3.getText()) + Integer.parseInt((String) tvPriceS4.getText()) + Integer.parseInt((String) tvPriceS5.getText());

        // 15000원 이상 무료배송
        if(sum>=15000 || sum == 0){
            deliveryCharge = 0;
        }else{
            deliveryCharge = 3000;
        }
        total = sum + deliveryCharge;

        tvTotal.setText("TOTAL PRICE " + total + "원");
        tvSum.setText("상품 금액 " +sum + "원");
        tvDeliveryCharge.setText("배송비 " + deliveryCharge + "원");
    }



}
