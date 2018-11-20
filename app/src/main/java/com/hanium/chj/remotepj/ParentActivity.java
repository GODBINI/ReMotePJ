package com.hanium.chj.remotepj;

import android.app.AlarmManager;
import android.app.PendingIntent;
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

public class ParentActivity extends AppCompatActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_menu);

        Intent BeforeIntent = getIntent();
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(ParentActivity.this);
                        builder.setMessage("도착한 메세지가 없습니다.")
                                .setNegativeButton("확인", null)
                                .create()
                                .show();
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
                    int yearint = Integer.parseInt(year);
                    int monthint = Integer.parseInt(month);
                    int dayint = Integer.parseInt(day);
                    int timeint = Integer.parseInt(time);
                    int minuteint = Integer.parseInt(minute);

                    if (success) {
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
                        Toast.makeText(ParentActivity.this,"설정 완료!",Toast.LENGTH_SHORT).show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ParentActivity.this);
                        builder.setMessage("현재 설정된 알람이없습니다.")
                                .setNegativeButton("확인", null)
                                .create()
                                .show();
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
    }
}
