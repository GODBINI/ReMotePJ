package com.hanium.chj.remotepj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class RegisterPopUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_pop_up);

        TextView R_TextView = (TextView) findViewById(R.id.R_txtText);
        Button R_OkButton = (Button) findViewById(R.id.R_OkButton);

        Intent BeforeIntent = getIntent();
        final String respones = BeforeIntent.getStringExtra("response");
        final String num = BeforeIntent.getStringExtra("num");
        if(respones.equals("S")) {
            R_TextView.setText("회원가입 성공!");
        }
        else if(respones.equals("P")) {
            R_TextView.setText("회원가입 성공! *인증번호* :"+num);
        }
        else {
            R_TextView.setText("회원가입 실패");
        }


        R_OkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SuccessChild_Intent = new Intent( RegisterPopUpActivity.this, LoginActivity.class);
                Intent Fail_Intent = new Intent(RegisterPopUpActivity.this, LoginActivity.class);
                if(respones.equals("S")) {
                    startActivity(SuccessChild_Intent);
                }
                else {
                    startActivity(Fail_Intent);
                }
            }
        });
    }
}
