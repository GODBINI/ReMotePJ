package com.hanium.chj.remotepj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MessageMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_menu);

        Button HomeButton = (Button) findViewById(R.id.HomeButton1);
        Button SendmsgButton = (Button) findViewById(R.id.SendmsgButton);
        Button MsgboxButton = (Button)findViewById(R.id.MsgboxButton);

        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MessageMenuActivity.this, ChildMenuActivity.class);
                MessageMenuActivity.this.startActivity(intent1);
            }
        });

        SendmsgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MessageMenuActivity.this, SendMsgActivity.class);
                MessageMenuActivity.this.startActivity(intent2);
            }
        });

        MsgboxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MessageMenuActivity.this, MsgBoxActivity.class);
                MessageMenuActivity.this.startActivity(intent3);
            }
        });
    }

}
