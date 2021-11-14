package com.example.demoasm1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getName();
    static final int PERMISSION_SMS = 1;
    static final int PERMISSION_PHONE = 2;
    static final int PERMISSION_READ_STATE_AND_SMS = 3;
    static final int PERMISSION_READ_STATE_AND_PHONE = 4;
    Button btnSMS, btnPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Khai báo biến smsButton kiểu dữ liệu Button
//        và gán giá trị bằng id của button sms trong layout activity_main.xml
        btnSMS = findViewById(R.id.button_sms_ActMain);
        btnSMS.setAlpha((float) 1.0);

//        Khai báo biến phoneButton kiểu dữ liệu Button
//        và gán giá trị bằng id của button phone trong layout activity_main.xml
        btnPhone = findViewById(R.id.button_phone_ActMain);
        btnPhone.setAlpha((float) 1.0);

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

    //phương thức check PERMISSION_SMS
    private void checkPermissionSMS() {
        if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "SEND_SMS permission granted", Toast.LENGTH_SHORT).show();
            sendMessage();
        } else {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISSION_SMS);
        }
    }

    //phương thức check PERMISSION_PHONE_CALL
    private void checkPermissionPhoneCall() {
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "CALL_PHONE permission granted", Toast.LENGTH_SHORT).show();
            makePhone();
        } else {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_PHONE);
        }
    }

    // When user click on button, this code is run
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_sms_ActMain) {
            btnSMS.setAlpha((float) 0.3);
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.SEND_SMS}, PERMISSION_READ_STATE_AND_SMS);
            } else {
                checkPermissionSMS();
            }
        } else if (v.getId() == R.id.button_phone_ActMain) {
            btnPhone.setAlpha((float) 0.3);
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_STATE_AND_PHONE);
            } else {
                checkPermissionPhoneCall();
            }
        }

    }

    @Override
//    When user click on permission announcement, this code is executed
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_SMS:
                if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendMessage();
                } else {
                    Toast.makeText(this, "Please allow permission for using it", Toast.LENGTH_SHORT).show();
                }
                break;
            case PERMISSION_PHONE:
                if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makePhone();
                } else {
                    Toast.makeText(this, "Please allow permission for using it", Toast.LENGTH_SHORT).show();
                }
                break;
            case PERMISSION_READ_STATE_AND_SMS:
                if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "Please allow permission READ_PHONE_STATE for using it", Toast.LENGTH_SHORT).show();
                    btnSMS.setAlpha((float) 1.0);
                }else  if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED){
                    sendMessage();
                }else{
                    Toast.makeText(this, "Please allow permission for using it", Toast.LENGTH_SHORT).show();
                    btnSMS.setAlpha((float) 1.0);
                }
                break;
            case PERMISSION_READ_STATE_AND_PHONE:
                if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "Please allow permission READ_PHONE_STATE for using it", Toast.LENGTH_SHORT).show();
                    btnPhone.setAlpha((float) 1.0);
                }else  if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED){
                    makePhone();
                }else{
                    Toast.makeText(this, "Please allow permission for using it", Toast.LENGTH_SHORT).show();
                    btnPhone.setAlpha((float) 1.0);
                }
                break;
        }
    }
}