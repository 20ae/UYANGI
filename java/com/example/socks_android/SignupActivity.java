package com.example.socks_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    public EditText name, uN, pW, emailAddress, phoneNum, address;
    String LOG = "SignUpActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name = (EditText) findViewById(R.id.userName);
        uN = (EditText) findViewById(R.id.userID);
        pW = (EditText) findViewById(R.id.password);
        emailAddress = (EditText) findViewById(R.id.email);
        phoneNum = (EditText) findViewById(R.id.phone);
        address= (EditText) findViewById(R.id.address);
        OnButtonClick();

//        //로그인 버튼 클릭
//        Button loginButton = (Button) findViewById(R.id.loginButton);
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        //회원가입 버튼 클릭
//        Button signupButton = (Button) findViewById(R.id.signupButton);
//        signupButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"회원가입 완료",Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public void OnButtonClick() {
        Button loginButton = (Button) findViewById(R.id.loginButton);
        Button signupButton = (Button) findViewById(R.id.signupButton);

        //Upon being pressed, user is taken back to the login page
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap postData = new HashMap();
                postData.put("userID", uN.getText().toString());
                postData.put("password", pW.getText().toString());
                postData.put("Name", name.getText().toString());
                postData.put("pNumber", phoneNum.getText().toString());
                postData.put("address", address.getText().toString());
                postData.put("email", emailAddress.getText().toString());
                PostResponseAsyncTask taskInsert = new PostResponseAsyncTask(SignupActivity.this, postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        if (s.contains("success")) {
                            Log.d(LOG, s);
                            Toast.makeText(SignupActivity.this, "Insert Successful", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        //If the userID (or password) has under 8 characters, or the phone number length is less than 11, then an error box presents itself
                        else if (s.contains("failed") || pW.getText().toString().length() < 8 || phoneNum.getText().toString().length() != 11) {
                            AlertDialog.Builder dialogBox = new AlertDialog.Builder(SignupActivity.this);
                            dialogBox.setMessage("These reasons cause errors..."+"\n1. The userID has been taken"+"\n2. The password are of the wrong length (i.e. under 8 characters)"
                                    + "\n3. The phone number is of the wrong length (i.e under 11 digits)")
                                    .setCancelable(false)
                                    .setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();//closes the dialog box
                                        }
                                    });
                            AlertDialog dialog = dialogBox.create();
                            dialog.setTitle("An Error was Detected!");
                            dialog.show();
                        }

                    }
                });
                taskInsert.execute("http://192.168.11.213/phpfiles/register.php/");
            }
        });
    }
}