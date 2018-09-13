package com.hanium.chj.remotepj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(2000); // 자기가 원하는 시간
        } catch (InterruptedException e ) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, LoginActivity.class); // 다음화면으로 넘어갈 Activity 지정
        startActivity(intent);
        finish();
    }
}
