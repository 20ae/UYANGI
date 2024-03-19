package com.example.socks_android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ColorActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "ColorActivity";
    private static final String TAG_JSON="result";
    private static final String TAG_Color = "product_color";
    String mJsonString = null;
    //String[] combi;

    Intent intent;
    public static String URL_Combi = null;

    TextView comb_name; // 상품명
    Spinner comb_color_spinner; // 상품 색상 선택하는 스피너
    public String product_color = ""; // 상품 색상
    Button c_pair_b1, c_pair_b2, c_pair_b3, t_pair_b1, t_pair_b2, t_pair_b3; // 조합 색상 나타내는 버튼
    public static String c_pair_1, c_pair_2, c_pair_3, t_pair_1, t_pair_2, t_pair_3; // 조합 색상 코드 저장함
    public static String HEX; // 상품 색상 색상코드(HEX) 저장

    TextView CCT1;
    TextView CCT2;
    int bgColor;
    ColorDrawable color;
    ImageButton btn_ex_1_1;
    ImageButton btn_ex_1_2;
    ImageView imv1 = null;
    ImageView imv2 = null;
    String s = "c1";
    Integer a = 1;




    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        //this.InitializeView();
        //this.SetListener();


        c_pair_b1 = (Button) findViewById(R.id.c_pair_b1);
        c_pair_b2 = (Button) findViewById(R.id.c_pair_b2);
        c_pair_b3 = (Button) findViewById(R.id.c_pair_b3);
        t_pair_b1 = (Button) findViewById(R.id.t_pair_b1);
        t_pair_b2 = (Button) findViewById(R.id.t_pair_b2);
        t_pair_b3 = (Button) findViewById(R.id.t_pair_b3);
        CCT1 = (TextView) findViewById(R.id.CCT1);
        CCT2 = (TextView) findViewById(R.id.CCT2);
        btn_ex_1_1 = (ImageButton) findViewById(R.id.btn_ex_1_1);
        btn_ex_1_2 = (ImageButton) findViewById(R.id.btn_ex_1_2);
        imv1 = (ImageView) findViewById(R.id.imageView1);
        imv2 = (ImageView) findViewById(R.id.imageView2);


        c_pair_b1.setOnClickListener(this);
        c_pair_b2.setOnClickListener(this);
        c_pair_b3.setOnClickListener(this);
        t_pair_b1.setOnClickListener(this);
        t_pair_b2.setOnClickListener(this);
        t_pair_b3.setOnClickListener(this);
        btn_ex_1_1.setOnClickListener(this);
        btn_ex_1_2.setOnClickListener(this);


        intent = getIntent();
        String ProductName = intent.getStringExtra("ProductName");
        String length = intent.getStringExtra("length");

        switch (length) {
            case "덧신":
                imv2.setImageResource(R.drawable.f1);
                break;
            case "스니커즈":
                imv2.setImageResource(R.drawable.f2);
                break;
            case "발목":
                imv2.setImageResource(R.drawable.f3);
                break;
            case "중목":
                imv2.setImageResource(R.drawable.f4);
                break;
            case "장목":
                imv2.setImageResource(R.drawable.f5);
                break;
            case "니삭스":
                imv2.setImageResource(R.drawable.f6);
                break;
        }

        ArrayList<String> colorList = intent.getStringArrayListExtra("product_color");

        comb_name = (TextView) findViewById(R.id.comb_name);
        comb_name.setText(ProductName);


        comb_color_spinner = (Spinner) findViewById(R.id.comb_color_spinner);
        //arrayAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.spinner_item,colorList);
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_item,colorList);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        comb_color_spinner.setAdapter(arrayAdapter);
        comb_color_spinner.setSelection(0); // 초기 스피너 메뉴 항목 지정
        product_color = (String)comb_color_spinner.getSelectedItem();
        URL_Combi = String.format("http://192.168.11.213/phpfiles/color_combination.php?product_color=%s",product_color);


        //event listener
        comb_color_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"선택된 아이템 : "+comb_color_spinner.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
                product_color = (String)comb_color_spinner.getSelectedItem(); // 상품 색상 가져오기
                Log.i(TAG, "Spinner selected item = "+product_color);
                URL_Combi = String.format("http://192.168.11.213/phpfiles/color_combination.php?product_color=%s",product_color); // 상품 색상에 맞는 색상 조합 출력하는 쿼리
                GetData task = new GetData();
                task.execute(URL_Combi);
                ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);
            }
        });


    }

    class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ColorActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
//            mTextViewResult.setText(result);
            Log.d(TAG, URL_Combi);
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

            //combi = new String[jsonArray.length()];

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                HEX = item.getString("HEX");
                c_pair_1 = item.getString("c_pair_1"); // 보색 1번 색상코드 가져옴
                c_pair_2 = item.getString("c_pair_2");
                c_pair_3 = item.getString("c_pair_3");
                t_pair_1 = item.getString("t_pair_1"); // 유사색 1번 색상코드 가져옴
                t_pair_2 = item.getString("t_pair_2");
                t_pair_3 = item.getString("t_pair_3");

            }


            CCT2.setBackgroundColor(Color.parseColor(HEX));
            c_pair_b1.setBackgroundColor(Color.parseColor(c_pair_1)); // 버튼에 색상넣기
            c_pair_b2.setBackgroundColor(Color.parseColor(c_pair_2));
            c_pair_b3.setBackgroundColor(Color.parseColor(c_pair_3));

            t_pair_b1.setBackgroundColor(Color.parseColor(t_pair_1));
            t_pair_b2.setBackgroundColor(Color.parseColor(t_pair_2));
            t_pair_b3.setBackgroundColor(Color.parseColor(t_pair_3));

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.c_pair_b1:
                CCT1.setBackgroundColor(Color.parseColor(c_pair_1));
                break;
            case R.id.c_pair_b2:
                CCT1.setBackgroundColor(Color.parseColor(c_pair_2));
                break;
            case R.id.c_pair_b3:
                CCT1.setBackgroundColor(Color.parseColor(c_pair_3));
                break;
            case R.id.t_pair_b1:
                CCT1.setBackgroundColor(Color.parseColor(t_pair_1));
                break;
            case R.id.t_pair_b2:
                CCT1.setBackgroundColor(Color.parseColor(t_pair_2));
                break;
            case R.id.t_pair_b3:
                CCT1.setBackgroundColor(Color.parseColor(t_pair_3));
                break;
            case R.id.btn_ex_1_1:
                CCN();
                break;
            case R.id.btn_ex_1_2:
                CCP();
                break;

        }
    }


    public void CCP() {
        switch (a) {
            case 1:
                imv1.setImageResource(R.drawable.c2);
                a = a + 1;
                break;
            case 2:
                a = a + 1;
                imv1.setImageResource(R.drawable.c3);
                break;
            case 3:
                a = 1;
                imv1.setImageResource(R.drawable.c1);
                break;
        }
    }

    public void CCN() {
        switch (a) {
            case 1:
                a = 3;
                imv1.setImageResource(R.drawable.c3);
                break;
            case 2:
                a = a - 1;
                imv1.setImageResource(R.drawable.c1);
                break;
            case 3:
                a = a - 1;
                imv1.setImageResource(R.drawable.c2);
                break;
        }
    }

}
