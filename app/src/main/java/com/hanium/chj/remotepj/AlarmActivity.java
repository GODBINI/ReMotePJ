package com.hanium.chj.remotepj;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_alarm);

        Intent BeforeIntent = getIntent();

        final String userID = BeforeIntent.getStringExtra("userID");

        final Button A_OkButton = (Button)findViewById(R.id.A_OkButton);
        final EditText AlarmEditText = (EditText)findViewById(R.id.AlarmText);

        final EditText YearText = (EditText)findViewById(R.id.YearText);
        final EditText MonthText = (EditText)findViewById(R.id.MonthText);
        final EditText DayText = (EditText)findViewById(R.id.DayText);
        final EditText HourText = (EditText)findViewById(R.id.HourText);
        final EditText MinuteText = (EditText)findViewById(R.id.MinuteText);

        A_OkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Year = YearText.getText().toString();
                String Month = MonthText.getText().toString();
                String Day = DayText.getText().toString();
                String Hour = HourText.getText().toString();
                String Minute = MinuteText.getText().toString();
                String AlarmText = AlarmEditText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AlarmActivity.this);
                                builder.setMessage("알람설정 성공")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                            }

                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AlarmActivity.this);
                                builder.setMessage("설정 실패!!!")
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
                AlarmRequest alarmRequest = new AlarmRequest(userID,Year,Month,Day,Hour,Minute,AlarmText,responseListener);
                RequestQueue queue = Volley.newRequestQueue(AlarmActivity.this);
                queue.add(alarmRequest);
            }
        });


        /* final Spinner YearSpinner = (Spinner)findViewById(R.id.YearSpinner);
        final Spinner MonthSpinner = (Spinner)findViewById(R.id.MonthSpinner);
        final Spinner DaySpinner = (Spinner)findViewById(R.id.DaySpinner);
        final Spinner HourSpinner = (Spinner)findViewById(R.id.HourSpinner);
        final Spinner MinuteSpinner = (Spinner)findViewById(R.id.MinuteSpinner);

        final Button A_OkButton = (Button)findViewById(R.id.A_OkButton);
        final EditText AlarmText = (EditText)findViewById(R.id.AlarmText);

        final String[] YearItem = new String[]{"0","1","2","3","4","5"};
        final String[] MonthItem = new String[]{"0","1","2","3","4","5","6"};
        final String[] DayItem = new String[]{"0","1","2","3","4","5","10","15"};
        final String[] HourItem = new String[]{"0","1","2","3","4","5","10","12"};
        final String[] MinuteItem = new String[]{"0","1","2","3","4","5","10","15","30","45"};

        ArrayAdapter<String> Yearadapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,YearItem);
        ArrayAdapter<String> Monthadapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,MonthItem);
        ArrayAdapter<String> Dayadapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,DayItem);
        ArrayAdapter<String> Houradapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,HourItem);
        ArrayAdapter<String> Minuteadapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,MinuteItem);

        YearSpinner.setAdapter(Yearadapter);
        MonthSpinner.setAdapter(Monthadapter);
        DaySpinner.setAdapter(Dayadapter);
        HourSpinner.setAdapter(Houradapter);
        MinuteSpinner.setAdapter(Minuteadapter);

        YearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 final String year = YearSpinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }); */
    }
}
