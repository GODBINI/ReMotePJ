package com.hanium.chj.remotepj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MsgBoxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_box);

        Button HomeButton = (Button) findViewById(R.id.HomeButton2);

        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent GoHomeIntent = new Intent(MsgBoxActivity.this , ChildMenuActivity.class);
                MsgBoxActivity.this.startActivity(GoHomeIntent);
            }
        });
    }
}
