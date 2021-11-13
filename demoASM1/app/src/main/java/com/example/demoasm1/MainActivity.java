package com.example.demoasm1;

import static androidx.core.content.PermissionChecker.PERMISSION_DENIED;
import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.security.Permission;
import java.security.PrivilegedAction;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getName();
    static final int PERMISSION_SMS = 1;
    static final int PERMISSION_PHONE = 2;
    static final int PERMISSION_READ_PHONE_STATE = 3;
    Button btnSMS, btnPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Khai báo biến smsButton kiểu dữ liệu Button
//        và gán giá trị bằng id của button sms trong layout activity_main.xml
        btnSMS = findViewById(R.id.button_sms_ActMain);

//        Khai báo biến phoneButton kiểu dữ liệu Button
//        và gán giá trị bằng id của button phone trong layout activity_main.xml
        btnPhone = findViewById(R.id.button_phone_ActMain);

//        Scan click on button event
        btnSMS.setOnClickListener(this);
        btnPhone.setOnClickListener(this);

    }

    // Mở màn hình SMS
    private void sendMessage() {
        startActivity(new Intent(this, act_sms.class));
    }

    // Mở màn hình phone
    private void makePhone() {
        startActivity(new Intent(this, act_phone.class));
    }

    // When user click on button, this code is run
    @Override
    public void onClick(View v) {
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},PERMISSION_READ_PHONE_STATE);
        }else {
            if (v.getId() == R.id.button_sms_ActMain) {
                if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "SEND_SMS permission granted", Toast.LENGTH_SHORT).show();
                    sendMessage();
                } else {
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISSION_SMS);
                }

            } else if (v.getId() == R.id.button_phone_ActMain) {
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "CALL_PHONE permission granted", Toast.LENGTH_SHORT).show();
                    makePhone();
                } else {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_PHONE);
                }
            }
        }

    }

    @Override
//    When user click on permission announcement, this code is executed
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_SMS) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendMessage();
            } else {
                Toast.makeText(this, "Please allow permission for using it", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PERMISSION_PHONE) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhone();
            } else {
                Toast.makeText(this, "Please allow permission for using it", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode == PERMISSION_READ_PHONE_STATE){
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                return;
            }else{
                Toast.makeText(this, "Please allow permission READ_PHONE_STATE for using it", Toast.LENGTH_SHORT).show();
            }
        }
    }
}