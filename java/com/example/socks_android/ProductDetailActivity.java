package com.example.socks_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.socks_android.Interfaces.Products;
import com.example.socks_android.ui.fragment.DetailExplainFragment;
import com.example.socks_android.ui.fragment.HomeFragment;
import com.example.socks_android.ui.fragment.MyPageFragment;
import com.example.socks_android.ui.fragment.ReviewFragment;
import com.example.socks_android.ui.fragment.ShoppingBasketFragment;
import com.example.socks_android.ui.fragment.ShoppingFragment;
import com.example.socks_android.ui.search.SearchFragment;
import com.nostra13.universalimageloader.core.ImageLoader;

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

import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {

    public static final String PREFS = "prefFile";
//    private Context context;

    private static String TAG = "ProductDetailActivity";

    private static final String TAG_JSON="result";
    private static final String TAG_Color = "product_color";
    private static final String TAG_ID = "ProductID";

    //프래그먼트 설정
    private HomeFragment HF;
    private ShoppingBasketFragment SBF;
    private ShoppingFragment SF;
    private MyPageFragment MF;
    private SearchFragment SearchF;


    String mJsonString = null;

    TextView tvName;
    TextView tvDesc;
    TextView tvPrice;
    String img_url;
    ImageView imageView;
    Button purchaseButton;
    ImageButton bt_favorite;

    Fragment detail = new DetailExplainFragment();

    Spinner colorOption;
    // array list for spinner adapter
    ArrayList<String> colorList;
    //    private JSONArray result;
    public static String URL_Colors = null;
    String ProductID;
    String length;
    String ProductName;
    int price = 0;
    public static String customer = null;
    String[] color;
    private TextView mTextViewResult;
    static RequestQueue requestQueue;

    HashMap colorMap;

    Intent intent;

    public ProductDetailActivity() {
        // Required empty public constructor
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        intent = getIntent();

        Products product = (Products) intent.getSerializableExtra("productList");

        tvName = (TextView) findViewById(R.id.tvName);
        //tvDesc = (TextView) findViewById(R.id.tvDesc);
        tvPrice = (TextView) findViewById(R.id.tvPrice1);
        imageView = (ImageView) findViewById(R.id.ivImage);
        purchaseButton = (Button) findViewById(R.id.purchaseButton);
        bt_favorite = (ImageButton) findViewById(R.id.bt_favorite);
        //OnButtonClick();
        //mTextViewResult = (TextView)findViewById(R.id.mTextViewResult);

        colorList = new ArrayList<String>();

        colorOption = (Spinner) findViewById(R.id.colorOption);


        if(product !=null)
        {
            ProductID = product.getId();
            ProductName = product.getName();
            price = product.getPrice();
            tvName.setText(ProductName);
            //tvDesc.setText(product.description);
            tvPrice.setText("" + product.price);
            length = product.getLength();
            img_url = product.img_url;
            Picasso.get().load(img_url).into(imageView);
            //ImageLoader.getInstance().displayImage(img_url, imageView);

        }
//        else{ //SearchFragment에서 받아옴
//            ProductID = intent.getStringExtra("product_id");
//            price = Integer.parseInt(intent.getStringExtra("price"));
//            img_url = intent.getStringExtra("image");
//            ImageLoader.getInstance().displayImage(img_url, imageView);
//            ProductName = intent.getStringExtra("product_name");
//            length = intent.getStringExtra("length");
//            tvPrice.setText(intent.getStringExtra("price"));
//            tvName.setText(ProductName);
//        }

        SharedPreferences productInfo = getSharedPreferences(PREFS, 0);
        SharedPreferences.Editor editor = productInfo.edit();
        editor.putString("ProductID", ProductID);
        //editor.putString("length", length);
        editor.commit();
        SharedPreferences preferences = getSharedPreferences(PREFS, 0);
        customer = preferences.getString("userID", null);

        URL_Colors = String.format("http://192.168.11.213/phpfiles/products_color.php?ProductID=%s",ProductID); // 상품 색상 받아옴

        GetData task = new GetData();
        task.execute(URL_Colors);

        /* 색조합추천으로 이동 */
        Button color_button = (Button) findViewById(R.id.color_button);
        color_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ColorActivity.class);
                intent.putExtra("ProductName", ProductName);
                intent.putExtra("length", length);
                intent.putStringArrayListExtra("product_color", colorList);
                startActivity(intent);
            }
        });


        DetailExplainFragment DEF = new DetailExplainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ProductID",ProductID);
        DEF.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.product_content, DEF).commit();

        //상품설명 버튼 클릭
        Button productDetailButton = (Button) findViewById(R.id.product_detail);
        productDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DetailExplainFragment DEF = new DetailExplainFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("ProductID",ProductID);
//                DEF.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.product_content, DEF).commit();
            }
        });

        //리뷰 버튼 클릭
        Button reviewButton = (Button) findViewById(R.id.review);
        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReviewFragment RF = new ReviewFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.product_content, RF).commit();
            }
        });

        // 구매 버튼 클릭
        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int qty = 1;
                //Toast.makeText(ProductDetailActivity.this, "상품이 장바구니에 담겼습니다.", Toast.LENGTH_LONG).show();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("success")){
                            Toast.makeText(ProductDetailActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(ProductDetailActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ProductDetailActivity.this, "An Error was Detected!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                // 서버로 Volley를 이용해서 요청을 함.
                String option1 = (String)colorOption.getSelectedItem();
                CartInsertRequest CartInsertRequest = new CartInsertRequest(ProductID,ProductName, option1, String.valueOf(qty), String.valueOf(price), img_url, customer, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ProductDetailActivity.this);
                queue.add(CartInsertRequest);

            }
        });


        // 찜 버튼 클릭
        bt_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("success")){
                            Toast.makeText(ProductDetailActivity.this, "Added to Favorite", Toast.LENGTH_SHORT).show();
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(ProductDetailActivity.this, "Added to Favorite", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ProductDetailActivity.this, "An Error was Detected!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                // 서버로 Volley를 이용해서 요청을 함.
                String option1 = (String)colorOption.getSelectedItem();
                FavoriteInsertRequest FavoriteInsertRequest = new FavoriteInsertRequest(ProductID, ProductName, option1, String.valueOf(price), img_url, customer, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ProductDetailActivity.this);
                queue.add(FavoriteInsertRequest);

            }
        });



//        BottomNavigationView bottom_menu = findViewById(R.id.nav_view_Home);
//
//        HF = new HomeFragment();
//        SBF = new ShoppingBasketFragment();
//        SF = new ShoppingFragment();
//        MF = new MyPageFragment();
//
//        bottom_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem nav_item) {
//                switch(nav_item.getItemId()) {
//                    case R.id.navigation_1: //홈
//                        getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, HF).addToBackStack(null).commit();
//                        return true;
//                    case R.id.navigation_2: //쇼핑
//                        //getSupportFragmentManager().beginTransaction().replace(R.id.detail_content, SF).addToBackStack(null).commit();
//                        return true;
//                    case R.id.navigation_3: //장바구니
//                        //getSupportFragmentManager().beginTransaction().replace(R.id.detail_content, SBF).addToBackStack(null).commit();
//                        return true;
//                }
//                return false;
//            }
//        });

    }

    class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ProductDetailActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
//            mTextViewResult.setText(result);
            Log.d(TAG, URL_Colors);
            Log.d(TAG, "response  - " + result);
            mJsonString = result;
            showResult();

//            if (result == null){
//
//                mTextViewResult.setText(errorString);
//            }
//            else {
//
//                mJsonString = result;
//                showResult();
//            }
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


    private void showResult(){ // 상품 색상 스피너로 받아옴
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            color = new String[jsonArray.length()];

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                color[i]=item.getString(TAG_Color);

            }

            for(int i = 0; i<color.length; i++){
                colorList.add(color[i]);
            }

            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, colorList);
            dataAdapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            colorOption.setAdapter(dataAdapter1);



            colorOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);
                }
            });

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

//    public void OnButtonClick() {
//        Button purchaseButton = (Button) findViewById(R.id.purchaseButton);
//
//        purchaseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //final int qty = 1;
//                HashMap postData = new HashMap();
//                postData.put("txtPID", ProductID);
//                postData.put("txtName", ProductName);
//                //postData.put("txtQty", qty);
//                postData.put("txtPrice", price);
//                postData.put("txtImageUrl", img_url);
//                postData.put("txtCustomer", customer);
//
//                PostResponseAsyncTask cartInsert = new PostResponseAsyncTask(ProductDetailActivity.this, postData, new AsyncResponse() {
//                    @Override
//                    public void processFinish(String s) {
//                        if (s.contains("success")) {
//                            Toast.makeText(ProductDetailActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
//                        }
//                        else if (s.contains("failed")) {
//                            Toast.makeText(ProductDetailActivity.this, "An Error was Detected!", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                });
//                cartInsert.execute("http://192.168.11.213/phpfiles/cart_insert.php");
//            }
//        });
//    }

}
