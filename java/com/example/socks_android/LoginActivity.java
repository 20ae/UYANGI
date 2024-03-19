package com.example.socks_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    public static final String PREFS = "prefFile";

    EditText userIDEt, userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        onButtonClick();
        //userID = (EditText) findViewById(R.id.userID);
        userPassword = (EditText) findViewById(R.id.userPassword);
        userIDEt = (EditText) findViewById(R.id.userID);

//        //로그인 버튼 클릭
//        Button loginButton = (Button) findViewById(R.id.loginButton);
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), UserPreferenceActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        //회원가입 버튼 클릭
//        Button signupButton = (Button) findViewById(R.id.signupButton);
//        signupButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        // finish();

        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    public void onLogin(View view) {
        final String userID = userIDEt.getText().toString();
        String password = userPassword.getText().toString();

        HashMap postData = new HashMap();
        postData.put("txtuserID", userID);
        postData.put("txtPassword", password);

        PostResponseAsyncTask task = new PostResponseAsyncTask(LoginActivity.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if (s.contains("success")) {
                    //if the userID and password match the corresponding contents, then the user
                    //navigates to the appropriate page

                    SharedPreferences preferences = getSharedPreferences(PREFS, 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("userID", userID);
                    editor.commit();

                    //Toast.makeText(LoginActivity.this, "Successful login", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), UserPreferenceActivity.class);
                    startActivity(intent);
                } else {
                    //if the userID and/or password is invalid, a dialog box appears informs them of possible errors
                    AlertDialog.Builder dialogBox = new AlertDialog.Builder(LoginActivity.this);
                    dialogBox.setMessage("아이디 또는 비밀번호를 확인해주세요.")
                            .setCancelable(false)
                            .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();//closes the dialog box
                                }
                            });

                    AlertDialog dialog = dialogBox.create();
                    dialog.setTitle("로그인 실패                       ");
                    dialog.show();
                }
            }
        });
        task.execute("http://192.168.11.213/phpfiles/login.php/");
        //task.execute("http://192.168.11.213/phpfiles/login.php/");
    }

    public void onButtonClick() {
        Button loginButton = (Button) findViewById(R.id.loginButton);
        Button signUp = (Button) findViewById(R.id.signupButton);
        Button FMP = (Button) findViewById(R.id.forgotPasswordButton);

        //By pressing the Sign-Up button, the Sign-up page appears
        signUp.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(i);
            }
        });

        //By pressing the forgot my password button, the forgot my password page opens
        FMP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ForgotPassActivity.class);
                startActivity(i);
            }
        });
    }
}