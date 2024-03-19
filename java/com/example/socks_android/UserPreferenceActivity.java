package com.example.socks_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

public class UserPreferenceActivity extends AppCompatActivity {
    private SharedPreferences userPreference;
    private RadioGroup genderGroup;
    private RadioGroup lengthGroup;
    private RadioGroup styleCheck;

    String gender_state; //성별&나이 상태
    String length_state; // 길이 상태
    String style_state; // 스타일 상태


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preference);

        userPreference = getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = userPreference.edit();

        //메인화면 버튼 클릭
        ImageButton goMainButton = (ImageButton) findViewById(R.id.goMainButton);
        goMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //성별&나이 선택
        genderGroup = (RadioGroup) findViewById(R.id.genderCheck);
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.child) {
                    gender_state = "3"; //아동
                } else if (checkedId == R.id.man) {
                    gender_state = "1"; //남성
                } else if (checkedId == R.id.woman) {
                    gender_state = "2"; //여성
                }

                editor.putString("gender", gender_state);
                editor.commit();
                Log.d("gender_state", String.valueOf(gender_state));

                String gender = userPreference.getString("gender", "");
                Log.d("gender", String.valueOf(gender));
                //Toast.makeText(getApplicationContext(), "성별/나이: " + gender, Toast.LENGTH_SHORT).show();
            }
        });

        //양말 길이 선택
        lengthGroup = (RadioGroup) findViewById(R.id.lengthGroup);
        lengthGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.fake) {
                    length_state = "덧신";
                } else if (checkedId == R.id.ankle) {
                    length_state = "발목";
                } else if (checkedId == R.id.middle) {
                    length_state = "중목";
                } else if (checkedId == R.id.crew) {
                    length_state = "장목";
                } else if (checkedId == R.id.knee) {
                    length_state = "니삭스";
                }
                editor.putString("length", length_state);
                editor.commit();

                String length = userPreference.getString("length", "");
                Log.d("length", length);
                //Toast.makeText(getApplicationContext(), "양말 길이: "+length ,Toast.LENGTH_SHORT).show();
            }
        });

        // 양말 스타일 선택
        styleCheck = (RadioGroup) findViewById(R.id.styleCheck);
        styleCheck.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.plain) {
                    style_state = "무지";
                } else if (checkedId == R.id.golgi) {
                    style_state = "골지";
                } else if (checkedId == R.id.pattern) {
                    style_state = "무늬/패턴";
                }
                editor.putString("style", style_state);
                editor.commit();

                String style = userPreference.getString("style", "");
                Log.d("style", style);
                //Toast.makeText(getApplicationContext(), "선호 스타일: "+style ,Toast.LENGTH_SHORT).show();
            }
        });

//        //(사용자 취향) 다음에 선택하기 버튼 클릭
//        Button laterButton = (Button) findViewById(R.id.laterButton);
//        laterButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                editor.clear();
//                editor.commit();
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.MyAlertDialogStyle);
        builder.setTitle("종료"); // 다이얼로그 제목
        builder.setMessage("취향 설정을 종료하시겠습니까?                                   ");
        builder.setCancelable(false);   // 다이얼로그 화면 밖 터치 방지
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("취소",null);
        builder.show();

    }
}