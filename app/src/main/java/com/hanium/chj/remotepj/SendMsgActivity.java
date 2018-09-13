package com.hanium.chj.remotepj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SendMsgActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg);

        Button SendmsgOkButton = (Button) findViewById(R.id.SendmsgOkButton);
        Button SendmsgCloseButton = (Button) findViewById(R.id.SendmsgCloseButton);

        SendmsgCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent CloseIntent = new Intent(SendMsgActivity.this, MessageMenuActivity.class);
                SendMsgActivity.this.startActivity(CloseIntent);
            }
        });
    }
}
