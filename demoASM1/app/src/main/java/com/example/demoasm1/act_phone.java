package com.example.demoasm1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class act_phone extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_phone);

        ImageButton backArrow = findViewById(R.id.imgbutton_backactmain_navigationPhone);
        Intent intentPhonePage = new Intent();

        intentPhonePage.setClass(this,MainActivity.class);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentPhonePage);
                finish();
            }
        });
    }
}