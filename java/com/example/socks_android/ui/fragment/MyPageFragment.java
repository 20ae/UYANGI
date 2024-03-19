package com.example.socks_android.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.socks_android.FavoriteActivity;
import com.example.socks_android.OrdersActivity;
import com.example.socks_android.R;
import com.example.socks_android.SettingActivity;
import com.example.socks_android.UserPreferenceActivity;
import com.example.socks_android.ui.setting.CustomerCenterActivity;
import com.example.socks_android.ui.setting.IntroductionActivity;
import com.example.socks_android.ui.setting.NoticeActivity;
import com.example.socks_android.ui.setting.PrivateInfoActivity;
import com.example.socks_android.ui.setting.ServiceActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPageFragment extends Fragment {

    private ShoppingBasketFragment SBF;
    private FavoriteFragment FF;
    public static final String PREFS = "prefFile";
    public static final String TAG = "MyPageFragment";
    private static final String TAG_JSON = "user";
    private static final String TAG_ID = "user_id";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_COUNT = "count";
    String customer;
    String userName;
    private String mJsonString;
    ArrayList mArrayList;
    TextView tv_username;
    LinearLayout mypageContent;
    SharedPreferences preferences;

    private View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyPageFragment newInstance(String param1, String param2) {
        MyPageFragment fragment = new MyPageFragment();
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
        view = inflater.inflate(R.layout.fragment_my_page, container, false);

        SBF = new ShoppingBasketFragment();
        FF = new FavoriteFragment();

        // 사용자 이름 불러오기
//        MyPageFragment.GetData task = new MyPageFragment.GetData();
//        task.execute(customer);

        mArrayList = new ArrayList<>();

        //mypageContent = (LinearLayout) view.findViewById(R.id.mypageContent);
        tv_username = (TextView) view.findViewById(R.id.tv_username);

        preferences = MyPageFragment.this.getActivity().getSharedPreferences(PREFS, 0);
        customer = preferences.getString("userID", null);
        tv_username.setText(customer);

        LinearLayout b_preference = (LinearLayout) view.findViewById(R.id.b_preference);
        b_preference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserPreferenceActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout b_orderlist = (LinearLayout) view.findViewById(R.id.b_orderlist);
        b_orderlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OrdersActivity.class);
                startActivity(intent);
            }
        });


        LinearLayout b_cart = (LinearLayout) view.findViewById(R.id.b_cart);
        b_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getFragmentManager().beginTransaction().replace(R.id.mypageContent, FF).addToBackStack(null).commit();
                Intent intent = new Intent(getActivity(), FavoriteActivity.class);
                startActivity(intent);
            }
        });

        //앱 소개
        TextView introduction_tv = (TextView) view.findViewById(R.id.introduction_tv);
        introduction_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), IntroductionActivity.class);
                startActivity(intent);
            }
        });

        //공지사항
        TextView notice_tv = (TextView) view.findViewById(R.id.notice_tv);
        notice_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NoticeActivity.class);
                startActivity(intent);
            }
        });
        //개인정보 수집 및 이용
        TextView private_tv = (TextView) view.findViewById(R.id.privateinfo_tv);
        private_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PrivateInfoActivity.class);
                startActivity(intent);
            }
        });

        //서비스 이용약관
        TextView service_tv = (TextView) view.findViewById(R.id.service_tv);
        service_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ServiceActivity.class);
                startActivity(intent);
            }
        });



        //Setting 버튼 클릭
        ImageButton settingButton = (ImageButton) view.findViewById(R.id.settingButton);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

//    class GetData extends AsyncTask<String, Void, String> {
//
//
//        ProgressDialog progressDialog;
//        String errorString = null;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            progressDialog = ProgressDialog.show(getActivity(),
//                    "wait", "Please Wait", true, true);
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//
//            customer = (String)params[0];
//
//            String serverURL = "http://192.168.11.213/phpfiles/getUserName.php";
//            String postParameters = "userID=" + customer;
//
//            try {
//
//                URL url = new URL(serverURL);
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//
//
//                httpURLConnection.setReadTimeout(5000);
//                httpURLConnection.setConnectTimeout(5000);
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.connect();
//
//
//                OutputStream outputStream = httpURLConnection.getOutputStream();
//                outputStream.write(postParameters.getBytes("UTF-8"));
//                outputStream.flush();
//                outputStream.close();
//
//
//                int responseStatusCode = httpURLConnection.getResponseCode();
//                Log.d(TAG, "POST response code - " + responseStatusCode);
//
//                InputStream inputStream;
//                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
//                    inputStream = httpURLConnection.getInputStream();
//                } else {
//                    inputStream = httpURLConnection.getErrorStream();
//                }
//
//
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//                StringBuilder sb = new StringBuilder();
//                String line;
//
//                while ((line = bufferedReader.readLine()) != null) {
//                    sb.append(line);
//                }
//
//                bufferedReader.close();
//
//                return sb.toString();
//
//
//            } catch (Exception e) {
//
//                Log.d(TAG, "InsertData: Error ", e);
//
//                return new String("Error: " + e.getMessage());
//            }
//
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            progressDialog.dismiss();
//            //mTextViewResult.setText(result);
//            Log.d(TAG, "response - " + result);
//
//            if (result == null){
//
//                tv_username.setText(errorString);
//            }
//            else {
//                mJsonString = result;
//                showResult();
//            }
//        }
//    }
//
//    private void showResult(){
//        try {
//            JSONObject jsonObject = new JSONObject(mJsonString);
//            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
//
//
//            for(int i=0;i<jsonArray.length();i++){
//
//                JSONObject item = jsonArray.getJSONObject(i);
//
//                userName = item.getString("Name");
//            }
//            tv_username.setText(userName);
//
//
//        } catch (JSONException e) {
//
//            Log.d(TAG, "showResult : ", e);
//        }
//
//    }



//    private class GetData extends AsyncTask<String, Void, String> {
//
//        ProgressDialog progressDialog;
//        String errorString = null;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            progressDialog = ProgressDialog.show(getActivity(),
//                    "wait", "Please Wait", true, true);
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//
//            progressDialog.dismiss();
//            //mTextViewResult.setText(result);
//            Log.d(TAG, "response - " + result);
//
//            if (result == null){
//
//                tv_username.setText(errorString);
//            }
//            else {
//                mJsonString = result;
//                tv_username.setText(result);
//                showResult();
//            }
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//
//            String serverURL = "http://192.168.11.213/phpfiles/getUserName.php";
//            String postParameters = "userID=" + customer;
//
//            try {
//
//                URL url = new URL(serverURL);
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//
//
//                httpURLConnection.setReadTimeout(5000);
//                httpURLConnection.setConnectTimeout(5000);
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.connect();
//
//
//                OutputStream outputStream = httpURLConnection.getOutputStream();
//                outputStream.write(postParameters.getBytes("UTF-8"));
//                outputStream.flush();
//                outputStream.close();
//
//
//                int responseStatusCode = httpURLConnection.getResponseCode();
//                Log.d(TAG, "POST response code - " + responseStatusCode);
//
//                InputStream inputStream;
//                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
//                    inputStream = httpURLConnection.getInputStream();
//                } else {
//                    inputStream = httpURLConnection.getErrorStream();
//                }
//
//
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//                StringBuilder sb = new StringBuilder();
//                String line;
//
//                while ((line = bufferedReader.readLine()) != null) {
//                    sb.append(line);
//                }
//
//                bufferedReader.close();
//
//                return sb.toString();
//
//
//            } catch (Exception e) {
//
//                Log.d(TAG, "InsertData: Error ", e);
//
//                return new String("Error: " + e.getMessage());
//            }
//
//        }
//    }
//
//    private void showResult() {
//
//        try {
//            JSONObject jsonObject = new JSONObject(mJsonString);
//            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
//
//            for (int i = 0; i < jsonArray.length() - 3; i++) {
//
//                HashMap<String, String> hashMap = new HashMap<>();
//
//                JSONObject item = jsonArray.getJSONObject(i);
//
//                userName = item.getString(TAG_USERNAME);
//
//
//                hashMap.put(TAG_USERNAME, userName);
//
//                mArrayList.add(hashMap);
//            }
//
//            List<String> list_count = new ArrayList<>();
//
//            for (int i = jsonArray.length() - 3; i < jsonArray.length(); i++) {
//
//                JSONObject item = jsonArray.getJSONObject(i);
//                String count = item.getString(TAG_COUNT);
//
//                list_count.add(count);
//
//            }
//
//            for(int i = 0; i<mArrayList.size(); i++) {
//                HashMap<String, String> hashMap2 = (HashMap<String, String>) mArrayList.get(i);
//                tv_username.setText(userName);
//            }
//
//        } catch (JSONException e) {
//            Log.d(TAG, "showResult: ", e);
//        }
//
//    }
}

