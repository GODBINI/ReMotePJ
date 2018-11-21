package com.hanium.chj.remotepj;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

public class ParentActivity extends AppCompatActivity {
    Context context;
    private BluetoothSPP bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_menu);

        Intent BeforeIntent = getIntent();
        bt = new BluetoothSPP(this); //Initializing
        this.context = this;
        final Intent mAlarmIntent = new Intent(this.context, AlarmReceiver.class);
        final String parentID = BeforeIntent.getStringExtra("parentID");

        final TextView ShowMsgText = (TextView)findViewById(R.id.ShowMsgText);
        final TextView YearText = (TextView)findViewById(R.id.YearText);
        final TextView MonthText = (TextView)findViewById(R.id.MonthText);
        final TextView DayText = (TextView)findViewById(R.id.DayText);
        final TextView TimeText = (TextView)findViewById(R.id.TimeText);
        final TextView MinuteText = (TextView)findViewById(R.id.MinuteText);
        final TextView AlarmView = (TextView)findViewById(R.id.AlarmText);
        final TextView SensorText = (TextView)findViewById(R.id.SensorText);

        SensorText.setVisibility(View.INVISIBLE);


        if (!bt.isBluetoothAvailable()) { //블루투스 사용 불가
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //데이터 수신
            public void onDataReceived(byte[] data, String message) {
                Toast.makeText(ParentActivity.this, message, Toast.LENGTH_SHORT).show();
                SensorText.setText(message);
            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() { //연결됐을 때
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext()
                        , "Connected to " + name + "\n" + address
                        , Toast.LENGTH_SHORT).show();
            }

            public void onDeviceDisconnected() { //연결해제
                Toast.makeText(getApplicationContext()
                        , "Connection lost", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() { //연결실패
                Toast.makeText(getApplicationContext()
                        , "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });

        Button Connect = findViewById(R.id.Connect); //연결시도
        Connect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                } else {
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }
            }
        });


        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                String sensor = SensorText.getText().toString();

                // 쪽지 불러오기

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            String MsgText = jsonResponse.getString("msg");

                            if (success) {
                                ShowMsgText.setText(MsgText);
                            } else {
                                Toast.makeText(ParentActivity.this,"도착한 메세지가 없습니다.",Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ShowMsgRequest showMsgRequest = new ShowMsgRequest(parentID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ParentActivity.this);
                queue.add(showMsgRequest);

                // 알람설정 불러오기
                Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            String AlarmText = jsonResponse.getString("alarmtext");
                            String year = jsonResponse.getString("year");
                            String month = jsonResponse.getString("month");
                            String day = jsonResponse.getString("day");
                            String time = jsonResponse.getString("time");
                            String minute = jsonResponse.getString("minute");

                            if (success) {
                                int yearint = Integer.parseInt(year);
                                int monthint = Integer.parseInt(month);
                                int dayint = Integer.parseInt(day);
                                int timeint = Integer.parseInt(time);
                                int minuteint = Integer.parseInt(minute);
                                YearText.setText(year + "년");
                                MonthText.setText(month + "월");
                                DayText.setText(day + "일");
                                TimeText.setText(time + "시");
                                MinuteText.setText(minute + "분");
                                AlarmView.setText("알람 내용 :"+ AlarmText);

                                Calendar mCalendar = Calendar.getInstance();
                                mCalendar.setTimeInMillis(System.currentTimeMillis());
                                mCalendar.set(yearint,monthint-1,dayint,timeint,minuteint,0);

                                PendingIntent mPendingIntent = PendingIntent.getBroadcast(ParentActivity.this, 0,mAlarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                                AlarmManager mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                                if(Build.VERSION.SDK_INT >= 23)
                                    mAlarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,mCalendar.getTimeInMillis(),mPendingIntent);
                                else {
                                    if(Build.VERSION.SDK_INT >= 19) {
                                        mAlarmManager.setExact(AlarmManager.RTC_WAKEUP,mCalendar.getTimeInMillis(),mPendingIntent);
                                    } else {
                                        mAlarmManager.set(AlarmManager.RTC_WAKEUP,mCalendar.getTimeInMillis(),mPendingIntent);
                                    }
                                }
                            } else {
                                Toast.makeText(ParentActivity.this,"현재 설정된 알람이 없습니다.",Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ShowAlarmRequest showAlarmRequest = new ShowAlarmRequest(parentID, responseListener2);
                RequestQueue queue2 = Volley.newRequestQueue(ParentActivity.this);
                queue2.add(showAlarmRequest);

                // 센서값 저장
                    Response.Listener<String> responseListener3 = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                String MsgText = jsonResponse.getString("msg");

                                if (success) {
                                    ShowMsgText.setText(MsgText);
                                } else {
                                    Toast.makeText(ParentActivity.this, "DB서버 연결 실패.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    SensorRequest SensorRequest = new SensorRequest(parentID, sensor, responseListener3);
                    RequestQueue queue3 = Volley.newRequestQueue(ParentActivity.this);
                    queue3.add(SensorRequest);
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask,0,60000);  // 10초마다 반복
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
    public void onDestroy () {
        super.onDestroy();
        bt.stopService(); //블루투스 중지
    }
    public void onStart () {
        super.onStart();
        if (!bt.isBluetoothEnabled()) { //
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER); //DEVICE_ANDROID는 안드로이드 기기 끼리
                //setup();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                //setup();
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
