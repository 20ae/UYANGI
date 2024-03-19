package com.example.socks_android.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.amigold.fundapter.interfaces.DynamicImageLoader;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.socks_android.AppHelper;
import com.example.socks_android.ColorActivity;
import com.example.socks_android.Interfaces.Products;
import com.example.socks_android.Interfaces.UILConfig;
import com.example.socks_android.LoginActivity;
import com.example.socks_android.ProductDetailActivity;
import com.example.socks_android.R;
import com.example.socks_android.SplashActivity;
import com.example.socks_android.ui.search.SearchFragment2;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HomeFragment extends Fragment implements Response.Listener<String>{

    String url = "";
    private static String TAG = "HomeFragment";

    private ArrayList<Products> productList;
    private GridView grid_preference;
    FunDapter<Products> adapter;

    boolean position_flag=true;
    private View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("앱 종료 알림");
                builder.setMessage("애플리케이션을 종료하시겠습니까?                              ");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(), SplashActivity.class); //fragment라서 activity intent와는 다른 방식
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);

                    }
                });
                builder.setNegativeButton("취소",null);
                builder.show();
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        HorizontalScrollView sroll = (HorizontalScrollView) view.findViewById(R.id.event_img_scroll);
        sroll.setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);

        grid_preference = (GridView) view.findViewById(R.id.grid_preference);

        SharedPreferences userPreference = HomeFragment.this.getActivity().getSharedPreferences("UserInfo", 0);
        String gender = userPreference.getString("gender","");
        String length = userPreference.getString("length","");
        String style = userPreference.getString("style","");

        //스크롤 제어
        HorizontalScrollView sroll1 = (HorizontalScrollView) view.findViewById(R.id.event_img_scroll);

        grid_preference.setOnScrollListener(new GridView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (!grid_preference.canScrollVertically(-1)) {
                    Log.i(TAG, "Top of list");
                    sroll1.setVisibility(View.VISIBLE);
                } else if (!grid_preference.canScrollVertically(1)) {
                    Log.i(TAG, "End of list");
                } else {
                    sroll1.setVisibility(View.GONE);
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });



//        if (gender != -1 || length !=null){
//            if(gender==1){
//                if(length.equals("덧신")){
//                    switch (style) {
//                        case "무지":
//                            url = "http://192.168.11.213/phpfiles/userPreference.php?catid=1&length=덧신&style=무지";
//                            break;
//                        case "골지":
//                            url = "http://192.168.11.213/phpfiles/userPreference.php?catid=1&length=덧신&style=골지";
//                            break;
//                        case "무늬/패턴":
//                            url = "http://192.168.11.213/phpfiles/userPreference.php?catid=1&length=덧신&style=무늬/패턴";
//                            break;
//                    }
//                }
//                else if(length.equals("발목")){
//                    switch (style) {
//                        case "무지":
//                            url = "http://192.168.11.213/phpfiles/userPreference.php?catid=1&length=발목&style=무지";
//                            break;
//                        case "골지":
//                            //url = "http://192.168.11.213/phpfiles/userPreference.php?catid=1&length=발목&style=골지"; -> 결과 없음
//                            url = "http://192.168.11.213/phpfiles/mens_ankle.php";
//                            break;
//                        case "무늬/패턴":
//                            url = "http://192.168.11.213/phpfiles/userPreference.php?catid=1&length=발목&style=무늬/패턴";
//                            break;
//                    }
//                }
//                else if(length.equals("중목")){
//                    switch (style) {
//                        case "무지":
//                            url = "http://192.168.11.213/phpfiles/userPreference.php?catid=1&length=중목&style=무지";
//                            break;
//                        case "골지":
//                            url = "http://192.168.11.213/phpfiles/userPreference.php?catid=1&length=중목&style=골지";
//                            break;
//                        case "무늬/패턴":
//                            url = "http://192.168.11.213/phpfiles/userPreference.php?catid=1&length=중목&style=무늬/패턴";
//                            break;
//                    }
//                }
//                else if(length.equals("장목")){
//                    url = "http://192.168.11.213/phpfiles/mens_crew.php";
//                }
//                else if(length.equals("니삭스")){
//                    url = "http://192.168.11.213/phpfiles/mens_crew.php";
//                }
//
//            } else if(gender==2){
//                if(length.equals("덧신")){
//                    switch (style) {
//                        case "무지":
//                            url = "http://192.168.11.213/phpfiles/userPreference.php?catid=2&length=덧신&style=무지";
//                            break;
//                        case "골지":
//                            url = "http://192.168.11.213/phpfiles/userPreference.php?catid=2&length=덧신&style=골지";
//                            break;
//                        case "무늬/패턴":
//                            url = "http://192.168.11.213/phpfiles/userPreference.php?catid=2&length=덧신&style=무늬/패턴";
//                            break;
//                    }
//                }
//                else if(length.equals("발목")){
//                    switch (style) {
//                        case "무지":
//                            url = "http://192.168.11.213/phpfiles/userPreference.php?catid=2&length=발목&style=무지";
//                            break;
//                        case "골지":
//                            url = "http://192.168.11.213/phpfiles/userPreference.php?catid=2&length=발목&style=골지";
//                            break;
//                        case "무늬/패턴":
//                            url = "http://192.168.11.213/phpfiles/userPreference.php?catid=2&length=발목&style=무늬/패턴";
//                            break;
//                    }
//                }
//                else if(length.equals("중목")){
//                    switch (style) {
//                        case "무지":
//                            url = "http://192.168.11.213/phpfiles/userPreference.php?catid=2&length=중목&style=무지";
//                            break;
//                        case "골지":
//                            url = "http://192.168.11.213/phpfiles/userPreference.php?catid=2&length=중목&style=골지";
//                            break;
//                        case "무늬/패턴":
//                            url = "http://192.168.11.213/phpfiles/userPreference.php?catid=2&length=중목&style=무늬/패턴";
//                            break;
//                    }
//                }
//                else if(length.equals("장목")){
//                    switch (style) {
//                        case "무지":
//                            url = "http://192.168.11.213/phpfiles/userPreference.php?catid=2&length=장목&style=무지";
//                            break;
//                        case "골지":
//                            url = "http://192.168.11.213/phpfiles/userPreference.php?catid=2&length=장목&style=골지";
//                            break;
//                        case "무늬/패턴":
//                            url = "http://192.168.11.213/phpfiles/womens_crew.php";
//                            break;
//                    }
//                }
//                else if(length.equals("니삭스")){
//                    url = "http://192.168.11.213/phpfiles/womens_knee.php";
//                }
//            } else if(gender == 3){ // 아동
//                url = "http://192.168.11.213/phpfiles/kidsocks.php";
//            }
//        }
//        else{ // 사용자가 취향 체크 안하면 Best 상품 나오게 함
//            url = "http://192.168.11.213/phpfiles/bestProducts.php";
//        }

        AppHelper.requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        url = "http://192.168.11.213/phpfiles/userPreference.php";

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
                params.put("catid",gender);
                params.put("length",length);
                params.put("style",style);
                Log.d("catid",gender);
                Log.d("length",length);
                Log.d("style",style);
                return params;
            }

        };

        stringRequest.setShouldCache(false); //이전 결과 있어도 새로 요청하여 응답을 보여준다.
        AppHelper.requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext()); // requestQueue 초기화 필수
        AppHelper.requestQueue.add(stringRequest);




        //ImageLoader.getInstance().init(UILConfig.config(HomeFragment.this.getActivity()));

//        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(HomeFragment.this.getActivity(), this);
//        taskRead.execute(url);

        // 상품 카테 버튼 -> ShoppingFragment SF = new ShoppingFragment()으로 sf1 sf2 sf3 카테별 연결해야함 (성별, 길이)->각각 ShoppingFragment
        Button btn_cg_1 = (Button) view.findViewById(R.id.btn_cg_1); // 성별
        Button btn_cg_2 = (Button) view.findViewById(R.id.btn_cg_2); // 길이
        Button btn_cg_3 = (Button) view.findViewById(R.id.btn_cg_3); // 무늬
        Button btn_cg_4 = (Button) view.findViewById(R.id.btn_cg_4); // 용도
        Button btn_cg_5 = (Button) view.findViewById(R.id.btn_cg_5); // 두께

        // 상품 버튼 -> Intent intent = new Intent(getActivity(), 상품activity.class)로 변경해야함



        //제품 상세 페이지 이동

        //ProductDetailFragment PDF = new ProductDetailFragment();
        ShoppingFragment SF = new ShoppingFragment();
        LengthFragment LF = new LengthFragment();
        PatternFragment PF = new PatternFragment();
        UseFragment UF = new UseFragment();
        ThicknessFragment TF = new ThicknessFragment();


        /*** 버튼 설정 <------ 카테고리 버튼  -------> */
        //성별
        btn_cg_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_content, SF)
                        .addToBackStack(null)
                        .commit();
            }
        });
        //길이
        btn_cg_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_content, LF)
                        .addToBackStack(null)
                        .commit();

            }
        });
        //무늬
        btn_cg_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_content, PF)
                        .addToBackStack(null)
                        .commit();

            }
        });
        //용도
        btn_cg_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_content, UF)
                        .addToBackStack(null)
                        .commit();

            }
        });
        //두께
        btn_cg_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_content, TF)
                        .addToBackStack(null)
                        .commit();

            }
        });


        return view;
    }

//    @Override
//    public void processFinish(String s) {
//
//        productList = new JsonConverter<Products>().toArrayList(s, Products.class);
//
//        BindDictionary dic = new BindDictionary();
//
//        dic.addStringField(R.id.tvName2, new StringExtractor<Products>() {
//            @Override
//            public String getStringValue(Products item, int position) {
//                return item.name;
//            }
//        });
//
//
//        dic.addStringField(R.id.tvPrice2, new StringExtractor<Products>() {
//            @Override
//            public String getStringValue(Products item, int position) {
//                return ""+item.price;
//            }
//        });
//
//        dic.addDynamicImageField(R.id.ivImage2, new StringExtractor<Products>() {
//            @Override
//            public String getStringValue(Products item, int position) {
//                return item.img_url;
//            }
//        }, new DynamicImageLoader() {
//            @Override
//            public void loadImage(String url, ImageView img) {
//                //Set image
//                //ImageLoader.getInstance().displayImage(url, img);
//                Picasso.get().load(url).into(img);
//            }
//        });
//
//        adapter = new FunDapter<>(HomeFragment.this.getActivity(), productList, R.layout.fragment_home_row, dic);
//        //grid_preference = (GridView)view.findViewById(R.id.grid_preference);
//        grid_preference.setAdapter(adapter);
//
//        grid_preference.setOnItemClickListener(this);
//
//
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Products selectedProduct = productList.get(position);
//
//        Intent intent = new Intent(HomeFragment.this.getActivity(), ProductDetailActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("productList", selectedProduct);
//        intent.putExtras(bundle);
//        startActivity(intent);
//    }


    @Override
    public void onResponse(String response) {
        Log.d(TAG,response);

        if (response.equals("null")){

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
                //ImageLoader.getInstance().displayImage(url, img);
                //Glide.with(getActivity().getApplicationContext()).load(url).into(img);
                Picasso.get().load(url).into(img);
                img.setAdjustViewBounds(true);
                img.setScaleType(ImageView.ScaleType.FIT_CENTER);
                img.setPadding(0, 0, 0, 10);
                img.setAdjustViewBounds(true);
            }
        });

            FunDapter<Products> adapter = new FunDapter<>(HomeFragment.this.getActivity(), productList, R.layout.fragment_home_row, dic);
            grid_preference.setAdapter(adapter);

            grid_preference.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Products selectedProduct = productList.get(position);

                    Intent intent = new Intent(HomeFragment.this.getActivity(), ProductDetailActivity.class);
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
