package com.example.socks_android.ui.search;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.socks_android.R;
import com.example.socks_android.ui.fragment.ReviewFragment;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchFragment extends Fragment {

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

    private LinearLayout linear1;
    private LinearLayout linear2;
    private LinearLayout linear3;
    private LinearLayout linear4;
    private LinearLayout linear5;
    private LinearLayout linear6;
    private LinearLayout linear7;
    private LinearLayout linear8;
    private LinearLayout linear9;
    private LinearLayout linear10;

    private TextView name1;
    private TextView price1;
    private ImageView image1;
    private TextView name2;
    private TextView price2;
    private ImageView image2;
    private TextView name3;
    private TextView price3;
    private ImageView image3;
    private TextView name4;
    private TextView price4;
    private ImageView image4;
    private TextView name5;
    private TextView price5;
    private ImageView image5;
    private TextView name6;
    private TextView price6;
    private ImageView image6;
    private TextView name7;
    private TextView price7;
    private ImageView image7;
    private TextView name8;
    private TextView price8;
    private ImageView image8;
    private TextView name9;
    private TextView price9;
    private ImageView image9;
    private TextView name10;
    private TextView price10;
    private ImageView image10;
    String image;

    public SearchFragment() {
    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        //메인에서 검색 키워드 받아오기
        String searchStr;
        Bundle bundle = getArguments();
        TextView searchKey = (TextView) view.findViewById(R.id.search_key);
        noneText = (TextView) view.findViewById(R.id.noneText);

        //양말 이미지
        linear1 = (LinearLayout) view.findViewById(R.id.linear1);
        linear2 = (LinearLayout) view.findViewById(R.id.linear2);
        linear3 = (LinearLayout) view.findViewById(R.id.linear3);
        linear4 = (LinearLayout) view.findViewById(R.id.linear4);
        linear5 = (LinearLayout) view.findViewById(R.id.linear5);
        linear6 = (LinearLayout) view.findViewById(R.id.linear6);
        linear7 = (LinearLayout) view.findViewById(R.id.linear7);
        linear8 = (LinearLayout) view.findViewById(R.id.linear8);
        linear9 = (LinearLayout) view.findViewById(R.id.linear9);
        linear10 = (LinearLayout) view.findViewById(R.id.linear10);

        //양말 세부 사항
        name1 = (TextView) view.findViewById(R.id.socks1_text);
        name2 = (TextView) view.findViewById(R.id.socks2_text);
        name3 = (TextView) view.findViewById(R.id.socks3_text);
        name4 = (TextView) view.findViewById(R.id.socks4_text);
        name5 = (TextView) view.findViewById(R.id.socks5_text);
        name6 = (TextView) view.findViewById(R.id.socks6_text);
        name7 = (TextView) view.findViewById(R.id.socks7_text);
        name8 = (TextView) view.findViewById(R.id.socks8_text);
        name9 = (TextView) view.findViewById(R.id.socks9_text);
        name10 = (TextView) view.findViewById(R.id.socks10_text);

        price1 = (TextView) view.findViewById(R.id.socks1_price);
        price2 = (TextView) view.findViewById(R.id.socks2_price);
        price3 = (TextView) view.findViewById(R.id.socks3_price);
        price4 = (TextView) view.findViewById(R.id.socks4_price);
        price5 = (TextView) view.findViewById(R.id.socks5_price);
        price6 = (TextView) view.findViewById(R.id.socks6_price);
        price7 = (TextView) view.findViewById(R.id.socks7_price);
        price8 = (TextView) view.findViewById(R.id.socks8_price);
        price9 = (TextView) view.findViewById(R.id.socks9_price);
        price10 = (TextView) view.findViewById(R.id.socks10_price);

        image1 = (ImageView) view.findViewById(R.id.socks1_image);
        image2 = (ImageView) view.findViewById(R.id.socks2_image);
        image3 = (ImageView) view.findViewById(R.id.socks3_image);
        image4 = (ImageView) view.findViewById(R.id.socks4_image);
        image5 = (ImageView) view.findViewById(R.id.socks5_image);
        image6 = (ImageView) view.findViewById(R.id.socks6_image);
        image7 = (ImageView) view.findViewById(R.id.socks7_image);
        image8 = (ImageView) view.findViewById(R.id.socks8_image);
        image9 = (ImageView) view.findViewById(R.id.socks9_image);
        image10 = (ImageView) view.findViewById(R.id.socks10_image);

        if (bundle != null) {
            searchStr = bundle.getString("key");
            Log.d("searchKey", searchStr);
            searchKey.setText(searchStr);

            SearchFragment.GetData task = new SearchFragment.GetData();
            task.execute(searchStr);

            mArrayList = new ArrayList<>();




        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getActivity(),
                    "wait", "Please Wait", true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response - " + result);

            if (result == null) {
                linear1.setVisibility(View.VISIBLE);
                linear2.setVisibility(View.VISIBLE);
                linear3.setVisibility(View.VISIBLE);
                linear4.setVisibility(View.VISIBLE);
                linear5.setVisibility(View.VISIBLE);
                linear6.setVisibility(View.VISIBLE);
                linear7.setVisibility(View.VISIBLE);
                linear8.setVisibility(View.VISIBLE);
                linear9.setVisibility(View.VISIBLE);
                linear10.setVisibility(View.VISIBLE);

            } else {
                noneText.setVisibility(View.GONE);
                linear1.setVisibility(View.GONE);
                linear2.setVisibility(View.GONE);
                linear3.setVisibility(View.GONE);
                linear4.setVisibility(View.GONE);
                linear5.setVisibility(View.GONE);
                linear6.setVisibility(View.GONE);
                linear7.setVisibility(View.GONE);
                linear8.setVisibility(View.GONE);
                linear9.setVisibility(View.GONE);
                linear10.setVisibility(View.GONE);

                mJsonString = result;
                showResult();

                if (item_number>10){
                    linear1.setVisibility(View.VISIBLE);
                    linear2.setVisibility(View.VISIBLE);
                    linear3.setVisibility(View.VISIBLE);
                    linear4.setVisibility(View.VISIBLE);
                    linear5.setVisibility(View.VISIBLE);
                    linear6.setVisibility(View.VISIBLE);
                    linear7.setVisibility(View.VISIBLE);
                    linear8.setVisibility(View.VISIBLE);
                    linear9.setVisibility(View.VISIBLE);
                    linear10.setVisibility(View.VISIBLE);
                } else{
                    switch (item_number) {
                        case 10:
                            linear10.setVisibility(View.VISIBLE);
                        case 9:
                            linear9.setVisibility(View.VISIBLE);
                        case 8:
                            linear8.setVisibility(View.VISIBLE);
                        case 7:
                            linear7.setVisibility(View.VISIBLE);
                        case 6:
                            linear6.setVisibility(View.VISIBLE);
                        case 5:
                            linear5.setVisibility(View.VISIBLE);
                        case 4:
                            linear4.setVisibility(View.VISIBLE);
                        case 3:
                            linear3.setVisibility(View.VISIBLE);
                        case 2:
                            linear2.setVisibility(View.VISIBLE);
                        case 1:
                            linear1.setVisibility(View.VISIBLE);
                        case 0:
                            break;
                    }
                }
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String ProductName = (String) params[0];

            String serverURL = "http://192.168.11.213/phpfiles/search.php";
            String postParameters = "ProductName=" + ProductName ;

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }

        private void showResult() {

            try {
                JSONObject jsonObject = new JSONObject(mJsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
                //Log.e("json", jsonArray.toString());
                item_number = jsonArray.length();

                for (int i = 0; i < item_number; i++) {

                    HashMap<String, String> hashMap = new HashMap<>();

                    JSONObject item = jsonArray.getJSONObject(i);

                    String product_id = item.getString(TAG_ID);
                    String product_name = item.getString(TAG_NAME);
                    image = item.getString(TAG_IMG);
                    String price = item.getString(TAG_PRICE);

                    hashMap.put(TAG_ID, product_id);
                    hashMap.put(TAG_NAME, product_name);
                    hashMap.put(TAG_IMG, image);
                    hashMap.put(TAG_PRICE, price);

                    mArrayList.add(hashMap);
                    //Log.d("mArray", mArrayList.get(i).toString());
                }

                for(int i = 0; i<mArrayList.size(); i++) {
                    HashMap<String, String> hashMap2 = mArrayList.get(i);
                    switch (i) {
                        case 0:
                            name1.setText(hashMap2.get(TAG_NAME));
                            price1.setText(hashMap2.get(TAG_PRICE));
                            image = hashMap2.get(TAG_IMG);
                            ImageLoader.getInstance().displayImage(image, image1);
                            // 양말 이미지 출력 안됨
                            /*
                            new Thread(()->{
                                Bitmap bitmap = null;
                                try {
                                    bitmap = BitmapFactory.decodeStream((InputStream)new URL(hashMap2.get(TAG_IMG)).getContent());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                image1.setImageBitmap(bitmap);
                            }).start();
                            */
                            //image1.setImageURI(Uri.parse(hashMap2.get(TAG_IMG)));
                        case 1:
                            name2.setText(hashMap2.get(TAG_NAME));
                            price2.setText(hashMap2.get(TAG_PRICE));
                            //image2.setImageURI(Uri.parse(hashMap2.get(TAG_IMG)));
                            image = hashMap2.get(TAG_IMG);
                            ImageLoader.getInstance().displayImage(image, image2);
                        case 2:
                            name3.setText(hashMap2.get(TAG_NAME));
                            price3.setText(hashMap2.get(TAG_PRICE));
                            //image3.setImageURI(Uri.parse(hashMap2.get(TAG_IMG)));
                            image = hashMap2.get(TAG_IMG);
                            ImageLoader.getInstance().displayImage(image, image3);
                        case 3:
                            name4.setText(hashMap2.get(TAG_NAME));
                            price4.setText(hashMap2.get(TAG_PRICE));
                            //image4.setImageURI(Uri.parse(hashMap2.get(TAG_IMG)));
                            image = hashMap2.get(TAG_IMG);
                            ImageLoader.getInstance().displayImage(image, image4);
                        case 4:
                            name5.setText(hashMap2.get(TAG_NAME));
                            price5.setText(hashMap2.get(TAG_PRICE));
                            //image5.setImageURI(Uri.parse(hashMap2.get(TAG_IMG)));
                            image = hashMap2.get(TAG_IMG);
                            ImageLoader.getInstance().displayImage(image, image5);
                        case 5:
                            name6.setText(hashMap2.get(TAG_NAME));
                            price6.setText(hashMap2.get(TAG_PRICE));
                            //image6.setImageURI(Uri.parse(hashMap2.get(TAG_IMG)));
                            image = hashMap2.get(TAG_IMG);
                            ImageLoader.getInstance().displayImage(image, image6);
                        case 6:
                            name7.setText(hashMap2.get(TAG_NAME));
                            price7.setText(hashMap2.get(TAG_PRICE));
                            //image7.setImageURI(Uri.parse(hashMap2.get(TAG_IMG)));
                            image = hashMap2.get(TAG_IMG);
                            ImageLoader.getInstance().displayImage(image, image7);
                        case 7:
                            name8.setText(hashMap2.get(TAG_NAME));
                            price8.setText(hashMap2.get(TAG_PRICE));
                            //image8.setImageURI(Uri.parse(hashMap2.get(TAG_IMG)));
                            image = hashMap2.get(TAG_IMG);
                            ImageLoader.getInstance().displayImage(image, image8);
                        case 8:
                            name9.setText(hashMap2.get(TAG_NAME));
                            price9.setText(hashMap2.get(TAG_PRICE));
                            //image9.setImageURI(Uri.parse(hashMap2.get(TAG_IMG)));
                            image = hashMap2.get(TAG_IMG);
                            ImageLoader.getInstance().displayImage(image, image9);
                        case 9:
                            name10.setText(hashMap2.get(TAG_NAME));
                            price10.setText(hashMap2.get(TAG_PRICE));
                            //image10.setImageURI(Uri.parse(hashMap2.get(TAG_IMG)));
                            image = hashMap2.get(TAG_IMG);
                            ImageLoader.getInstance().displayImage(image, image10);

                        default: //break;
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                noneText.setVisibility(View.VISIBLE);
                linear1.setVisibility(View.GONE);
                linear2.setVisibility(View.GONE);
                linear3.setVisibility(View.GONE);
                linear4.setVisibility(View.GONE);
                linear5.setVisibility(View.GONE);
                linear6.setVisibility(View.GONE);
                linear7.setVisibility(View.GONE);
                linear8.setVisibility(View.GONE);
                linear9.setVisibility(View.GONE);
                linear10.setVisibility(View.GONE);
            }
        }
    }
}
