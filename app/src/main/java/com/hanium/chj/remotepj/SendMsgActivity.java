package com.hanium.chj.remotepj;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SendMsgActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg);

        final Intent BeforeIntent = getIntent();
        final String userID = BeforeIntent.getStringExtra("userID");
        final String userPassword = BeforeIntent.getStringExtra("userPassword");

        final EditText MsgText = (EditText) findViewById(R.id.MsgText);
        final Button SendmsgOkButton = (Button) findViewById(R.id.SendmsgOkButton);
        final Button SendmsgCloseButton = (Button) findViewById(R.id.SendmsgCloseButton);

        MsgText.setText("1.0");

        SendmsgCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent CloseIntent = new Intent(SendMsgActivity.this, MessageMenuActivity.class);
                CloseIntent.putExtra("userID",userID);
                CloseIntent.putExtra("userPassword",userPassword);
                SendMsgActivity.this.startActivity(CloseIntent);
            }
        });

        SendmsgOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msgText = MsgText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SendMsgActivity.this);
                                builder.setMessage("쪽지 전송 성공!")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                            }

                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SendMsgActivity.this);
                                builder.setMessage("*전송 실패*")
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
                SendMsgRequest sendMsgRequest = new SendMsgRequest(userID,msgText,responseListener);
                RequestQueue queue = Volley.newRequestQueue(SendMsgActivity.this);
                queue.add(sendMsgRequest);
            }
        });
    }
}
