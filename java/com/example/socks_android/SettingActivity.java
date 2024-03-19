package com.example.socks_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.socks_android.ui.fragment.MyPageFragment;

public class SettingActivity extends AppCompatActivity {
    
    Toolbar mToolbar;
    TextView tv_userinfo, tv_changepw, tv_logout, tv_alarmsetting, tv_appsetting;
    private Fragment MyPageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        tv_userinfo = findViewById(R.id.tv_userinfo);
        tv_changepw = findViewById(R.id.tv_changepw);
        tv_logout = findViewById(R.id.tv_logout);


        // 상단 툴바 설정
        mToolbar = (Toolbar) findViewById(R.id.toolbar2);
        mToolbar.setTitle("앱 설정");
        mToolbar.setTitleTextColor(Color.parseColor("#5C4F45"));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼

        
        tv_changepw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, ForgotPassActivity.class);
                startActivity(intent);
            }
        });
        
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //툴바 뒤로가기 키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setTitle("로그아웃")
                .setMessage("로그아웃 하시겠습니까?            ")
                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP); // 로그인 페이지로 돌아가면 id, pw 그대로 남아있음
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // id, pw 남아있지 않음
                        startActivity(intent);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();

    }
}