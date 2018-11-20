package com.hanium.chj.remotepj;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ConnectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_connect);

        Intent BeforeIntent = getIntent();
        final String userID = BeforeIntent.getStringExtra("userID");
        final String userPassword = BeforeIntent.getStringExtra("userPassword");

        final EditText C_IdText = (EditText) findViewById(R.id.C_IdText);
        final EditText C_NumText = (EditText) findViewById(R.id.C_NumText);

        final Button C_OkButton = (Button) findViewById(R.id.C_OkButton);

        C_OkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String parentID = C_IdText.getText().toString();
                String parentNum= C_NumText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ConnectActivity.this);
                                builder.setMessage("연결 성공")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                            }

                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ConnectActivity.this);
                                builder.setMessage("연결 실패!!!")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        }
                        catch (JSONException e) {
                                e.printStackTrace();
                        }

                    }
                };
                ConnectRequest connectRequest = new ConnectRequest(userID,parentID,parentNum,responseListener);
                RequestQueue queue = Volley.newRequestQueue(ConnectActivity.this);
                queue.add(connectRequest);
            }
        });
    }
}
