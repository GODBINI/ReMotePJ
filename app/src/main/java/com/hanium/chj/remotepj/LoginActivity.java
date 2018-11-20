package com.hanium.chj.remotepj;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText pwText = (EditText) findViewById(R.id.pwText);
        final Button RegisterButton = (Button) findViewById(R.id.RegisterButton); // 버튼선언
        final Button LoginButton = (Button) findViewById(R.id.LoginButton);

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SelectRoleActivity.class);
                LoginActivity.this.startActivity(intent); // RegisterActivity로 넘어감
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = idText.getText().toString();
                final String userPassword = pwText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            boolean parent = jsonResponse.getBoolean("parent");
                            if (success) {
                                String userID = jsonResponse.getString("userID");
                                String userPassword = jsonResponse.getString("userPassword");
                                if(parent) {
                                    Intent ParentLoginIntent = new Intent(LoginActivity.this, ParentActivity.class);
                                    ParentLoginIntent.putExtra("parentID",userID);
                                    ParentLoginIntent.putExtra("userPassword",userPassword);
                                    LoginActivity.this.startActivity(ParentLoginIntent);
                                }
                                else {
                                    Intent ChildLoginIntent = new Intent(LoginActivity.this, ChildMenuActivity.class);
                                    ChildLoginIntent.putExtra("userID",userID);
                                    ChildLoginIntent.putExtra("userPassword",userPassword);
                                    LoginActivity.this.startActivity(ChildLoginIntent);
                                }
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("로그인에 실패하였습니다.")
                                        .setNegativeButton("다시시도", null)
                                        .create()
                                        .show();
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(userID, userPassword,responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}
