package com.hanium.chj.remotepj;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class StatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        Intent BeforeIntent = getIntent();
        final String userID = BeforeIntent.getStringExtra("userID");
        final TextView TextView = (TextView)findViewById(R.id.TextView);
        final TextView FlameText = (TextView)findViewById(R.id.FlameText);
        final TextView GasText = (TextView)findViewById(R.id.GasText);

        TextView.setVisibility(View.INVISIBLE);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            String sensor = jsonResponse.getString("sensor");

                            if (success) {
                                TextView.setText(sensor);
                                String Gas = sensor.split(";")[0];
                                String Flame = sensor.split(";")[1];
                                if (Gas.equals("0")) {
                                    GasText.setText("안전");
                                    GasText.setTextColor(Color.parseColor("#FF0000FF"));
                                }
                                else if (Gas.equals("1")) {
                                    GasText.setText("가스가 검출됨 *경고*");
                                    GasText.setTextColor(Color.parseColor("#FFFF0000"));
                                }
                                else {
                                    GasText.setText("센서 연결안됨");
                                    GasText.setTextColor(Color.parseColor("#FF000000"));
                                }


                                if (Flame.equals("0")) {
                                    FlameText.setText("안전");
                                    FlameText.setTextColor(Color.parseColor("#FF0000FF"));
                                }
                                else if (Flame.equals("1")) {
                                    FlameText.setText("불꽃 감지됨 *경고*");
                                    FlameText.setTextColor(Color.parseColor("#FFFF0000"));
                                }
                                else {
                                    FlameText.setText("센서 연결안됨");
                                    FlameText.setTextColor(Color.parseColor("#FF000000"));
                                }
                            } else {
                                Toast.makeText(StatusActivity.this,"센서 연동 실패!",Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ShowSensorRequest showSensorRequest = new ShowSensorRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(StatusActivity.this);
                queue.add(showSensorRequest);
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask,0,1000);

    }
}
