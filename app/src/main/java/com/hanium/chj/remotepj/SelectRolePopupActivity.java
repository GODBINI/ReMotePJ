package com.hanium.chj.remotepj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class SelectRolePopupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_select_role_popup);

        TextView txtText = (TextView) findViewById(R.id.txtText);
        Button OkButton = (Button) findViewById(R.id.OkButton);

        Intent intent = getIntent();
        final String data = intent.getStringExtra("data");
        txtText.setText(data+"를 선택하신것이 맞습니까?");

        OkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(SelectRolePopupActivity.this , RegisterActivity.class);
                if(data.equals("자녀")) {
                    intent1.putExtra("role","child");
                    startActivityForResult(intent1,1);
                }
                else {
                    intent1.putExtra("role","parent");
                    startActivityForResult(intent1,1);
                }
            }
        });
    }
}
