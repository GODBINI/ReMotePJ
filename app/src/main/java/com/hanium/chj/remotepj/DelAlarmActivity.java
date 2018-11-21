package com.hanium.chj.remotepj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DelAlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_del_alarm);

        Intent BeforeIntent = getIntent();
        final String userID = BeforeIntent.getStringExtra("userID");
        final Button D_OkButton = (Button)findViewById(R.id.D_OkButton);

        D_OkButton.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View view) {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    Toast.makeText(DelAlarmActivity.this,"알람 삭제 성공!!",Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(DelAlarmActivity.this,"알람삭제 실패! 다시시도해주세요.",Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    DelAlarmRequest delAlarmRequest = new DelAlarmRequest(userID, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(DelAlarmActivity.this);
                    queue.add(delAlarmRequest);
            }
        });
    }
}
