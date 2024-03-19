package com.example.socks_android.ui.fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.socks_android.ColorActivity;
import com.example.socks_android.R;
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



public class DetailExplainFragment extends Fragment {

    View view;
    static String url = null;
    private ImageView detailImageView;
    private String ProductID;
    private String detailImage1;

    private static String TAG = "phpDetailImageTest";
    private static final String TAG_JSON = "result";
    private String mJsonString;


    public DetailExplainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail_explain, container, false);

        detailImageView = (ImageView) view.findViewById(R.id.detail_image);

        Bundle bundle = getArguments();

        if (bundle != null) {
            ProductID = bundle.getString("ProductID");
            Log.d("ProductID", ProductID);

            url = "http://192.168.11.213/phpfiles/getDetailImage.php?ProductID="+ProductID;

            DetailExplainFragment.GetData task = new DetailExplainFragment.GetData();
            task.execute(url);
        }

        return view;
    }

    class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(DetailExplainFragment.this.getActivity(),
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();

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


            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                detailImage1 = item.getString("detailImg1");
                //ImageLoader.getInstance().displayImage(detailImage1, detailImageView);
                //Picasso.get().load(detailImage1).into(detailImageView);
                Glide.with(this).load(detailImage1).override(Target.SIZE_ORIGINAL).into(detailImageView);

            }

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }
}