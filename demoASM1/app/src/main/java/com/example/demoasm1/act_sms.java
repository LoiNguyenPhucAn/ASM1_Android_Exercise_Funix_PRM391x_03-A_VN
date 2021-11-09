package com.example.demoasm1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class act_sms extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_sms);

//        int checkPermissionSMS = ContextCompat.checkSelfPermission(act_sms.this, Manifest.permission.SEND_SMS);
//        if (checkPermissionSMS != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.shouldShowRequestPermissionRationale(act_sms.this, Manifest.permission.SEND_SMS);
//            ActivityCompat.requestPermissions(act_sms.this,new String[]{Manifest.permission.SEND_SMS},1);
//        }

        ImageButton backArrow = findViewById(R.id.imgbutton_backactmain_navigationsms);
        Intent intentSMSPage = new Intent();

        intentSMSPage.setClass(this,MainActivity.class);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentSMSPage);
                finish();
            }
        });
    }

}