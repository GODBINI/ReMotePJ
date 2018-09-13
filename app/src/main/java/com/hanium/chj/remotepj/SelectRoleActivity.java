package com.hanium.chj.remotepj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectRoleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);

        Button ChildButton = (Button) findViewById(R.id.ChildButton);
        Button ParentButton  = (Button) findViewById(R.id.ParentButton);

        ChildButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectRoleActivity.this, SelectRolePopupActivity.class);
                intent.putExtra("data", "자녀");
                startActivityForResult(intent, 1);
            }
        });

        ParentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectRoleActivity.this, SelectRolePopupActivity.class);
                intent.putExtra("data", "부모");
                startActivityForResult(intent, 1);
            }
        });
    }
}
