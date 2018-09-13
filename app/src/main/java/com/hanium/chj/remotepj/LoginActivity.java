package com.hanium.chj.remotepj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button RegisterButton = (Button) findViewById(R.id.RegisterButton); // 버튼선언

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SelectRoleActivity.class);
                LoginActivity.this.startActivity(intent); // RegisterActivity로 넘어감
            }
        });
    }
}
