package com.hanium.chj.remotepj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChildMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_menu);

        Button MessageButton = (Button) findViewById(R.id.MessageButton);

        MessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ChildMenuActivity.this, MessageMenuActivity.class);
                ChildMenuActivity.this.startActivity(intent1);
            }
        });
    }
}
