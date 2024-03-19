package com.example.socks_android.ui.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cliff.comparingperformancebar.PercentageProgressBar;
import com.example.socks_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewFragment extends Fragment {

    private static String TAG = "phpreviewtest";

    private static final String TAG_JSON = "user";
    private static final String TAG_ID = "user_id";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_PERCENT = "percent";
    private static final String TAG_COUNT = "count";
    private static final String TAG_COUNTDETAIL = "d_count";
    private static final String TAG_SUMMARY1 = "summary1";
    private static final String TAG_SUMMARY2 = "summary2";
    private static final String TAG_SUMMARY3 = "summary3";

    private TextView mTextViewResult;
    private TextView mTextViewPostive;
    private TextView mTextViewNegative;
    private TextView mTextViewReviewCount;

    private TextView mTextViewName1;
    private TextView mTextViewName2;
    private TextView mTextViewName3;
    private TextView mTextViewName4;
    private TextView mTextViewName5;
    private TextView mTextViewName6;
    private TextView mTextViewName7;
    private TextView mTextViewName8;
    private TextView mTextViewName9;
    private TextView mTextViewName10;
    private TextView mTextViewName11;
    private TextView mTextViewName12;
    private TextView mTextViewName13;
    private TextView mTextViewName14;
    private TextView mTextViewName15;
    private TextView mTextViewName16;
    private TextView mTextViewName17;
    private TextView mTextViewName18;
    private TextView mTextViewName19;
    private TextView mTextViewName20;

    private TextView mTextView_delivery;
    private TextView mTextView_color;
    private TextView mTextView_quality;
    private TextView mTextView_price;

    private TextView mTextView_key3;
    private TextView mTextView_key4;
    private TextView mTextView_key5;

    private TextView mTextViewUserID1;
    private TextView mTextViewReview1;
    private TextView mTextViewUserID2;
    private TextView mTextViewReview2;
    private TextView mTextViewUserID3;
    private TextView mTextViewReview3;
    private TextView mTextViewUserID4;
    private TextView mTextViewReview4;
    private TextView mTextViewUserID5;
    private TextView mTextViewReview5;
    private TextView mTextViewUserID6;
    private TextView mTextViewReview6;
    private TextView mTextViewUserID7;
    private TextView mTextViewReview7;
    private TextView mTextViewUserID8;
    private TextView mTextViewReview8;
    private TextView mTextViewUserID9;
    private TextView mTextViewReview9;
    private TextView mTextViewUserID10;
    private TextView mTextViewReview10;
    private TextView mTextViewUserID11;
    private TextView mTextViewReview11;
    private TextView mTextViewUserID12;
    private TextView mTextViewReview12;
    private TextView mTextViewUserID13;
    private TextView mTextViewReview13;
    private TextView mTextViewUserID14;
    private TextView mTextViewReview14;
    private TextView mTextViewUserID15;
    private TextView mTextViewReview15;
    private TextView mTextViewUserID16;
    private TextView mTextViewReview16;
    private TextView mTextViewUserID17;
    private TextView mTextViewReview17;
    private TextView mTextViewUserID18;
    private TextView mTextViewReview18;
    private TextView mTextViewUserID19;
    private TextView mTextViewReview19;
    private TextView mTextViewUserID20;
    private TextView mTextViewReview20;

    PercentageProgressBar valueProgressBar;


    ArrayList<HashMap<String, String>> mArrayList;
    ListView mListViewList;
    private EditText mEditTextID;
    Button btn_login;
    private String mJsonString;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReviewFragment newInstance(String param1, String param2) {
        ReviewFragment fragment = new ReviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_review, container, false);

        mTextViewResult = (TextView) v.findViewById(R.id.percentText);
        mTextViewPostive = (TextView) v.findViewById(R.id.positive);
        mTextViewNegative = (TextView) v.findViewById(R.id.negative);
        mTextViewReviewCount = (TextView) v.findViewById(R.id.review_count);

        mTextViewName1 = (TextView) v.findViewById(R.id.product_name1);
        mTextViewName2 = (TextView) v.findViewById(R.id.product_name2);
        mTextViewName3 = (TextView) v.findViewById(R.id.product_name3);
        mTextViewName4 = (TextView) v.findViewById(R.id.product_name4);
        mTextViewName5 = (TextView) v.findViewById(R.id.product_name5);
        mTextViewName6 = (TextView) v.findViewById(R.id.product_name6);
        mTextViewName7 = (TextView) v.findViewById(R.id.product_name7);
        mTextViewName8 = (TextView) v.findViewById(R.id.product_name8);
        mTextViewName9 = (TextView) v.findViewById(R.id.product_name9);
        mTextViewName10 = (TextView) v.findViewById(R.id.product_name10);
        mTextViewName11 = (TextView) v.findViewById(R.id.product_name11);
        mTextViewName12 = (TextView) v.findViewById(R.id.product_name12);
        mTextViewName13 = (TextView) v.findViewById(R.id.product_name13);
        mTextViewName14 = (TextView) v.findViewById(R.id.product_name14);
        mTextViewName15 = (TextView) v.findViewById(R.id.product_name15);
        mTextViewName16 = (TextView) v.findViewById(R.id.product_name16);
        mTextViewName17 = (TextView) v.findViewById(R.id.product_name17);
        mTextViewName18 = (TextView) v.findViewById(R.id.product_name18);
        mTextViewName19 = (TextView) v.findViewById(R.id.product_name19);
        mTextViewName20 = (TextView) v.findViewById(R.id.product_name20);

        mTextView_delivery = (TextView) v.findViewById(R.id.delivery);
        mTextView_color = (TextView) v.findViewById(R.id.d_color);
        mTextView_quality = (TextView) v.findViewById(R.id.quality);
        mTextView_price = (TextView) v.findViewById(R.id.price);

        mTextView_key3 = (TextView) v.findViewById(R.id.keyword3);
        mTextView_key4 = (TextView) v.findViewById(R.id.keyword4);
        mTextView_key5 = (TextView) v.findViewById(R.id.keyword5);

        mTextViewUserID1 = (TextView) v.findViewById(R.id.user_id1);
        mTextViewReview1 = (TextView) v.findViewById(R.id.review_content1);
        mTextViewUserID2 = (TextView) v.findViewById(R.id.user_id2);
        mTextViewReview2 = (TextView) v.findViewById(R.id.review_content2);
        mTextViewUserID3 = (TextView) v.findViewById(R.id.user_id3);
        mTextViewReview3 = (TextView) v.findViewById(R.id.review_content3);
        mTextViewUserID4 = (TextView) v.findViewById(R.id.user_id4);
        mTextViewReview4 = (TextView) v.findViewById(R.id.review_content4);
        mTextViewUserID5 = (TextView) v.findViewById(R.id.user_id5);
        mTextViewReview5 = (TextView) v.findViewById(R.id.review_content5);
        mTextViewUserID6 = (TextView) v.findViewById(R.id.user_id6);
        mTextViewReview6 = (TextView) v.findViewById(R.id.review_content6);
        mTextViewUserID7 = (TextView) v.findViewById(R.id.user_id7);
        mTextViewReview7 = (TextView) v.findViewById(R.id.review_content7);
        mTextViewUserID8 = (TextView) v.findViewById(R.id.user_id8);
        mTextViewReview8 = (TextView) v.findViewById(R.id.review_content8);
        mTextViewUserID9 = (TextView) v.findViewById(R.id.user_id9);
        mTextViewReview9 = (TextView) v.findViewById(R.id.review_content9);
        mTextViewUserID10 = (TextView) v.findViewById(R.id.user_id10);
        mTextViewReview10 = (TextView) v.findViewById(R.id.review_content10);
        mTextViewUserID11 = (TextView) v.findViewById(R.id.user_id11);
        mTextViewReview11 = (TextView) v.findViewById(R.id.review_content11);
        mTextViewUserID12 = (TextView) v.findViewById(R.id.user_id12);
        mTextViewReview12 = (TextView) v.findViewById(R.id.review_content12);
        mTextViewUserID13 = (TextView) v.findViewById(R.id.user_id13);
        mTextViewReview13 = (TextView) v.findViewById(R.id.review_content13);
        mTextViewUserID14 = (TextView) v.findViewById(R.id.user_id14);
        mTextViewReview14 = (TextView) v.findViewById(R.id.review_content14);
        mTextViewUserID15 = (TextView) v.findViewById(R.id.user_id15);
        mTextViewReview15 = (TextView) v.findViewById(R.id.review_content15);
        mTextViewUserID16 = (TextView) v.findViewById(R.id.user_id16);
        mTextViewReview16 = (TextView) v.findViewById(R.id.review_content16);
        mTextViewUserID17 = (TextView) v.findViewById(R.id.user_id17);
        mTextViewReview17 = (TextView) v.findViewById(R.id.review_content17);
        mTextViewUserID18 = (TextView) v.findViewById(R.id.user_id18);
        mTextViewReview18 = (TextView) v.findViewById(R.id.review_content18);
        mTextViewUserID19 = (TextView) v.findViewById(R.id.user_id19);
        mTextViewReview19 = (TextView) v.findViewById(R.id.review_content19);
        mTextViewUserID20 = (TextView) v.findViewById(R.id.user_id20);
        mTextViewReview20 = (TextView) v.findViewById(R.id.review_content20);

        //긍정 부정 그래프
        valueProgressBar = v.findViewById(R.id.chart);
        valueProgressBar.setProgress((float) 34);
        //이전 다음 버튼
        ImageButton pre_button = (ImageButton) v.findViewById(R.id.pre_button);
        ImageButton next_button = (ImageButton) v.findViewById(R.id.next_button);
        //긍정 부정 리뷰 버튼
        Button all_review = (Button) v.findViewById(R.id.all_review);
        Button show_positive = (Button) v.findViewById(R.id.show_positive);
        Button show_negative = (Button) v.findViewById(R.id.show_negative);
        //리뷰
        LinearLayout review1 = (LinearLayout) v.findViewById(R.id.review1);
        LinearLayout review2 = (LinearLayout) v.findViewById(R.id.review2);
        LinearLayout review3 = (LinearLayout) v.findViewById(R.id.review3);
        LinearLayout review4 = (LinearLayout) v.findViewById(R.id.review4);
        LinearLayout review5 = (LinearLayout) v.findViewById(R.id.review5);
        LinearLayout review6 = (LinearLayout) v.findViewById(R.id.review6);
        LinearLayout review7 = (LinearLayout) v.findViewById(R.id.review7);
        LinearLayout review8 = (LinearLayout) v.findViewById(R.id.review8);
        LinearLayout review9 = (LinearLayout) v.findViewById(R.id.review9);
        LinearLayout review10 = (LinearLayout) v.findViewById(R.id.review10);
        LinearLayout review11 = (LinearLayout) v.findViewById(R.id.review11);
        LinearLayout review12 = (LinearLayout) v.findViewById(R.id.review12);
        LinearLayout review13 = (LinearLayout) v.findViewById(R.id.review13);
        LinearLayout review14 = (LinearLayout) v.findViewById(R.id.review14);
        LinearLayout review15 = (LinearLayout) v.findViewById(R.id.review15);
        LinearLayout review16 = (LinearLayout) v.findViewById(R.id.review16);
        LinearLayout review17 = (LinearLayout) v.findViewById(R.id.review17);
        LinearLayout review18 = (LinearLayout) v.findViewById(R.id.review18);
        LinearLayout review19 = (LinearLayout) v.findViewById(R.id.review19);
        LinearLayout review20 = (LinearLayout) v.findViewById(R.id.review20);

        LinearLayout percent_linear = (LinearLayout) v.findViewById(R.id.percent_linear);
        LinearLayout value_linear = (LinearLayout) v.findViewById(R.id.keyword_linear);

        GetData task = new GetData();

        SharedPreferences productInfo = ReviewFragment.this.getActivity().getSharedPreferences("prefFile", 0);
        String id = productInfo.getString("ProductID", "5033869899"); // 해당 id 받아오기
        task.execute( id );

        mArrayList = new ArrayList<>();
        //이전 버튼
        pre_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                percent_linear.setVisibility(View.VISIBLE);
                value_linear.setVisibility(View.GONE);
            }
        });
        //다음 버튼
        next_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                percent_linear.setVisibility(View.GONE);
                value_linear.setVisibility(View.VISIBLE);
            }
        });
        //전체리뷰보기 버튼
        all_review.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                review1.setVisibility(View.VISIBLE);
                review2.setVisibility(View.VISIBLE);
                review3.setVisibility(View.VISIBLE);
                review4.setVisibility(View.VISIBLE);
                review5.setVisibility(View.VISIBLE);
                review6.setVisibility(View.VISIBLE);
                review7.setVisibility(View.VISIBLE);
                review8.setVisibility(View.VISIBLE);
                review9.setVisibility(View.VISIBLE);
                review10.setVisibility(View.VISIBLE);
                review11.setVisibility(View.VISIBLE);
                review12.setVisibility(View.VISIBLE);
                review13.setVisibility(View.VISIBLE);
                review14.setVisibility(View.VISIBLE);
                review15.setVisibility(View.VISIBLE);
                review16.setVisibility(View.VISIBLE);
                review17.setVisibility(View.VISIBLE);
                review18.setVisibility(View.VISIBLE);
                review19.setVisibility(View.VISIBLE);
                review20.setVisibility(View.VISIBLE);
            }
        });
        //긍정리뷰보기 버튼
        show_positive.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                review1.setVisibility(View.VISIBLE);
                review2.setVisibility(View.VISIBLE);
                review3.setVisibility(View.VISIBLE);
                review4.setVisibility(View.VISIBLE);
                review5.setVisibility(View.VISIBLE);
                review6.setVisibility(View.VISIBLE);
                review7.setVisibility(View.VISIBLE);
                review8.setVisibility(View.VISIBLE);
                review9.setVisibility(View.VISIBLE);
                review10.setVisibility(View.VISIBLE);
                review11.setVisibility(View.GONE);
                review12.setVisibility(View.GONE);
                review13.setVisibility(View.GONE);
                review14.setVisibility(View.GONE);
                review15.setVisibility(View.GONE);
                review16.setVisibility(View.GONE);
                review17.setVisibility(View.GONE);
                review18.setVisibility(View.GONE);
                review19.setVisibility(View.GONE);
                review20.setVisibility(View.GONE);
            }
        });
        //부정리뷰보기 버튼
        show_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                review1.setVisibility(View.GONE);
                review2.setVisibility(View.GONE);
                review3.setVisibility(View.GONE);
                review4.setVisibility(View.GONE);
                review5.setVisibility(View.GONE);
                review6.setVisibility(View.GONE);
                review7.setVisibility(View.GONE);
                review8.setVisibility(View.GONE);
                review9.setVisibility(View.GONE);
                review10.setVisibility(View.GONE);
                review11.setVisibility(View.VISIBLE);
                review12.setVisibility(View.VISIBLE);
                review13.setVisibility(View.VISIBLE);
                review14.setVisibility(View.VISIBLE);
                review15.setVisibility(View.VISIBLE);
                review16.setVisibility(View.VISIBLE);
                review17.setVisibility(View.VISIBLE);
                review18.setVisibility(View.VISIBLE);
                review19.setVisibility(View.VISIBLE);
                review20.setVisibility(View.VISIBLE);
            }
        });
        // Inflate the layout for this fragment
        return v;
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
            //mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){
                mTextViewResult.setText(errorString);
            }
            else {
                //mTextViewResult.setText(result);
                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String productID = (String)params[0];
            //System.out.println(productID);

            String serverURL = "http://192.168.11.213/phpfiles/review.php";
            String postParameters = "productID=" + productID ;

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

                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }

    private void showResult() {

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            /*
            종합 리뷰 데이터
             */

            List<String> list_count = new ArrayList<>();

            for(int i = 0; i < 4; i++){

                JSONObject item = jsonArray.getJSONObject(i);
                String count = item.getString(TAG_COUNT);

                list_count.add(count);

            }

            int total_count = Integer.parseInt(list_count.get(0));
            int pos_count = Integer.parseInt(list_count.get(1));
            int neg_count = Integer.parseInt(list_count.get(2));

            float pos_per = 100 - (float)total_count/(float)pos_count;

            int pos_per_int = (int) pos_per;
            valueProgressBar.setProgress(pos_per_int);

            mTextViewReviewCount.setText("리뷰 개수 : " + total_count );
            mTextViewResult.setText("리뷰를 작성한 사람 중 " +
                    pos_per_int+ "%가 만족한 상품이에요");
            mTextViewPostive.setText("긍정 : " + pos_per_int+ "%" +
                    "  ("+pos_count+"개)");
            mTextViewNegative.setText("부정 : " + (100-pos_per_int) + "%"
                    +"  ("+neg_count+"개)");

            String reviewName = list_count.get(3);
            mTextViewName1.setText(reviewName);
            mTextViewName2.setText(reviewName);
            mTextViewName3.setText(reviewName);
            mTextViewName4.setText(reviewName);
            mTextViewName5.setText(reviewName);
            mTextViewName6.setText(reviewName);
            mTextViewName7.setText(reviewName);
            mTextViewName8.setText(reviewName);
            mTextViewName9.setText(reviewName);
            mTextViewName10.setText(reviewName);
            mTextViewName11.setText(reviewName);
            mTextViewName12.setText(reviewName);
            mTextViewName13.setText(reviewName);
            mTextViewName14.setText(reviewName);
            mTextViewName15.setText(reviewName);
            mTextViewName16.setText(reviewName);
            mTextViewName17.setText(reviewName);
            mTextViewName18.setText(reviewName);
            mTextViewName19.setText(reviewName);
            mTextViewName20.setText(reviewName);

            /*
            종합 리뷰 데이터
             */

            List<String> d_list_count = new ArrayList<>();

            for(int i = 4; i < 12; i++){

                JSONObject item = jsonArray.getJSONObject(i);
                String count = item.getString(TAG_COUNTDETAIL);

                d_list_count.add(count);

            }

            int qual_p = Integer.parseInt(d_list_count.get(0));
            int qual_n = Integer.parseInt(d_list_count.get(1));
            int color_p = Integer.parseInt(d_list_count.get(2));
            int color_n = Integer.parseInt(d_list_count.get(3));
            int price_p = Integer.parseInt(d_list_count.get(4));
            int price_n = Integer.parseInt(d_list_count.get(5));
            int del_p = Integer.parseInt(d_list_count.get(6));
            int del_n = Integer.parseInt(d_list_count.get(7));
            
            // 가격 품질 배송 색상 - 각 chart 시각화
            
            float pos_per_qual = 100 - (float)(qual_n+qual_p)/(float)qual_p;
            int pos_per_int_qual = (int) pos_per_qual;
            mTextView_quality.setText("좋아요 "+ pos_per_int_qual + "%");

            float pos_per_color = 100 - (float)(color_n+color_p)/(float)color_p;
            int pos_per_int_color = (int) pos_per_color;
            mTextView_color.setText("화면과 같아요 "+ pos_per_int_color + "%");

            float pos_per_price = 100 - (float)(price_n+price_p)/(float)price_p;
            int pos_per_int_price = (int) pos_per_price;
            mTextView_price.setText("저렴해요 "+ pos_per_int_price + "%");

            float pos_per_del = 100 - (float)(del_n+del_p)/(float)del_p;
            int pos_per_int_del = (int) pos_per_del;
            mTextView_delivery.setText("빨라요 "+ pos_per_int_del + "%");

            /*
            개별 리뷰 데이터
             */
            int review_anal_count = 0;

            //for (int i = 0; i < jsonArray.length()-3; i++) {
            if (pos_count < 10 | neg_count < 10) {

                if (pos_count > 10)
                    pos_count = 10;
                if (neg_count > 10)
                    neg_count = 10;

                for (int i = 12; i < pos_count+neg_count+3; i++) {

                    HashMap<String, String> hashMap = new HashMap<>();

                    JSONObject item = jsonArray.getJSONObject(i);

                    String user_id = item.getString(TAG_ID);
                    String content = item.getString(TAG_CONTENT);
                    String percent = item.getString(TAG_PERCENT);

                    hashMap.put(TAG_ID, user_id);
                    hashMap.put(TAG_CONTENT, content);
                    hashMap.put(TAG_PERCENT, percent);

                    mArrayList.add(hashMap);

                    review_anal_count = i;
                }
            } else {
                for (int i = 12; i < 32; i++) {

                    HashMap<String, String> hashMap = new HashMap<>();

                    JSONObject item = jsonArray.getJSONObject(i);

                    System.out.println(item);

                    String user_id = item.getString(TAG_ID);
                    String content = item.getString(TAG_CONTENT);
                    String percent = item.getString(TAG_PERCENT);

                    hashMap.put(TAG_ID, user_id);
                    hashMap.put(TAG_CONTENT, content);
                    hashMap.put(TAG_PERCENT, percent);

                    mArrayList.add(hashMap);

                    review_anal_count = i;
                }
            }

            // 키워드 추출

            System.out.println(review_anal_count);

            for(int i = review_anal_count+1; i < review_anal_count+2; i++){

                JSONObject item = jsonArray.getJSONObject(i);
                String key1 = item.getString(TAG_SUMMARY1);
                String key2 = item.getString(TAG_SUMMARY2);
                String key3 = item.getString(TAG_SUMMARY3);

                mTextView_key3.setText(key1);
                mTextView_key4.setText(key2);
                mTextView_key5.setText(key3);
            }

            // 개별 리뷰 데이터

            for(int i = 0; i<mArrayList.size(); i++) {
                HashMap<String, String> hashMap2 = mArrayList.get(i);
                switch (i) {
                    case 0:
                        mTextViewUserID1.setText(hashMap2.get(TAG_ID));
                        mTextViewReview1.setText(hashMap2.get(TAG_CONTENT));
                        //break;
                    case 1:
                        mTextViewUserID2.setText(hashMap2.get(TAG_ID));
                        mTextViewReview2.setText(hashMap2.get(TAG_CONTENT));
                        //break;
                    case 2:
                        mTextViewUserID3.setText(hashMap2.get(TAG_ID));
                        mTextViewReview3.setText(hashMap2.get(TAG_CONTENT));
                        //break;
                    case 3:
                        mTextViewUserID4.setText(hashMap2.get(TAG_ID));
                        mTextViewReview4.setText(hashMap2.get(TAG_CONTENT));
                        //break;
                    case 4:
                        mTextViewUserID5.setText(hashMap2.get(TAG_ID));
                        mTextViewReview5.setText(hashMap2.get(TAG_CONTENT));
                        //break;
                    case 5:
                        mTextViewUserID6.setText(hashMap2.get(TAG_ID));
                        mTextViewReview6.setText(hashMap2.get(TAG_CONTENT));
                        //break;
                    case 6:
                        mTextViewUserID7.setText(hashMap2.get(TAG_ID));
                        mTextViewReview7.setText(hashMap2.get(TAG_CONTENT));
                        //break;
                    case 7:
                        mTextViewUserID8.setText(hashMap2.get(TAG_ID));
                        mTextViewReview8.setText(hashMap2.get(TAG_CONTENT));
                        //break;
                    case 8:
                        mTextViewUserID9.setText(hashMap2.get(TAG_ID));
                        mTextViewReview9.setText(hashMap2.get(TAG_CONTENT));
                        //break;
                    case 9:
                        mTextViewUserID10.setText(hashMap2.get(TAG_ID));
                        mTextViewReview10.setText(hashMap2.get(TAG_CONTENT));
                        //break;
                    case 10:
                        mTextViewUserID11.setText(hashMap2.get(TAG_ID));
                        mTextViewReview11.setText(hashMap2.get(TAG_CONTENT));
                        //break;
                    case 11:
                        mTextViewUserID12.setText(hashMap2.get(TAG_ID));
                        mTextViewReview12.setText(hashMap2.get(TAG_CONTENT));
                        //break;
                    case 12:
                        mTextViewUserID13.setText(hashMap2.get(TAG_ID));
                        mTextViewReview13.setText(hashMap2.get(TAG_CONTENT));
                        //break;
                    case 13:
                        mTextViewUserID14.setText(hashMap2.get(TAG_ID));
                        mTextViewReview14.setText(hashMap2.get(TAG_CONTENT));
                        //break;
                    case 14:
                        mTextViewUserID15.setText(hashMap2.get(TAG_ID));
                        mTextViewReview15.setText(hashMap2.get(TAG_CONTENT));
                        //break;
                    case 15:
                        mTextViewUserID16.setText(hashMap2.get(TAG_ID));
                        mTextViewReview16.setText(hashMap2.get(TAG_CONTENT));
                        //break;
                    case 16:
                        mTextViewUserID17.setText(hashMap2.get(TAG_ID));
                        mTextViewReview17.setText(hashMap2.get(TAG_CONTENT));
                        //break;
                    case 17:
                        mTextViewUserID18.setText(hashMap2.get(TAG_ID));
                        mTextViewReview18.setText(hashMap2.get(TAG_CONTENT));
                        //break;
                    case 18:
                        mTextViewUserID19.setText(hashMap2.get(TAG_ID));
                        mTextViewReview19.setText(hashMap2.get(TAG_CONTENT));
                        //break;
                    case 19:
                        mTextViewUserID20.setText(hashMap2.get(TAG_ID));
                        mTextViewReview20.setText(hashMap2.get(TAG_CONTENT));
                        //break;
                    default: //break;
                }

            }


        } catch (JSONException e) {
            Log.d(TAG, "showResult: ", e);
        }

    }
}
