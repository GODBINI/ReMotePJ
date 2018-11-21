package com.hanium.chj.remotepj;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class ChildMenuActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_menu);

        this.context = this;
        final Intent mAlarmIntent = new Intent(this.context, AlarmReceiver.class);
        Intent BeforeIntent = getIntent();
        final String userID = BeforeIntent.getStringExtra("userID");
        final String userPassword = BeforeIntent.getStringExtra("userPassword");
        final Button MessageButton = (Button) findViewById(R.id.MessageButton);
        final Button ConnectButton = (Button)findViewById(R.id.ConnectButton);
        final Button AlarmButton = (Button)findViewById(R.id.AlarmButton);
        final Button DelAlarmButton = (Button)findViewById(R.id.DelAlarmButton);
        final Button StatusButton = (Button)findViewById(R.id.StatusButton);

        MessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ChildMenuActivity.this, MessageMenuActivity.class);
                intent1.putExtra("userID",userID);
                intent1.putExtra("userPassword",userPassword);
                ChildMenuActivity.this.startActivity(intent1);
            }
        });

        ConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ConnectIntent = new Intent(ChildMenuActivity.this, ConnectActivity.class);
                ConnectIntent.putExtra("userID",userID);
                ConnectIntent.putExtra("userPassword",userPassword);
                ChildMenuActivity.this.startActivity(ConnectIntent);
            }
        });

        AlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AlarmIntent = new Intent(ChildMenuActivity.this, AlarmActivity.class);
                AlarmIntent.putExtra("userID",userID);
                AlarmIntent.putExtra("userPassword",userPassword);
                ChildMenuActivity.this.startActivity(AlarmIntent);
            }
        });

         DelAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent DelAlarmIntent = new Intent(ChildMenuActivity.this, DelAlarmActivity.class);
                DelAlarmIntent.putExtra("userID",userID);
                DelAlarmIntent.putExtra("userPassword",userPassword);
                ChildMenuActivity.this.startActivity(DelAlarmIntent);
            }
        });

        StatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent StatusIntent = new Intent(ChildMenuActivity.this, StatusActivity.class);
                StatusIntent.putExtra("userID",userID);
                StatusIntent.putExtra("userPassword",userPassword);
                ChildMenuActivity.this.startActivity(StatusIntent);
            }
        });


    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
