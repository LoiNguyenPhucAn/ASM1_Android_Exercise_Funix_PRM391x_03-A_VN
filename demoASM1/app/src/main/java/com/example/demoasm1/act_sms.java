package com.example.demoasm1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;

import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;


import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

public class act_sms extends AppCompatActivity implements View.OnClickListener {

    private final String SEC = "seconds", HOURS = "hours", MINS = "minutes";

    String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED";

    PendingIntent sentPI,deliverPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;

    ImageView ivBackHome;
    Button btnSetup;
    EditText etPhoneNumber, etSenderBox, etTime;
    RadioButton r_Hour, r_Min, r_Sec;
    String stringDelayTime;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Call this before setContentView() is called to enable transition*/
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        // Use the following code for slide animation using XML
        Transition slideAnimation = TransitionInflater.from(this).inflateTransition(R.transition.slide);
        slideAnimation.setDuration(1000);
        getWindow().setEnterTransition(slideAnimation);

        setContentView(R.layout.activity_act_sms);

        ivBackHome = findViewById(R.id.ivBackHome_SMSPage);
        btnSetup = findViewById(R.id.btnSetup_SMSPage);
        etPhoneNumber = findViewById(R.id.etPhoneNumber_SMSPage);
        etSenderBox = findViewById(R.id.etSenderBox_SMSPage);
        etTime = findViewById(R.id.etTime_SMSPage);
        r_Hour = findViewById(R.id.radioHour_SMSPage);
        r_Min = findViewById(R.id.radioMin_SMSPage);
        r_Sec = findViewById(R.id.radioSec_SMSPage);

        if (r_Hour.isChecked()) {
            stringDelayTime = HOURS;
        } else if (r_Min.isChecked()) {
            stringDelayTime = MINS;
        } else {
            stringDelayTime = SEC;
        }

        ivBackHome.setOnClickListener(this);
        r_Sec.setOnClickListener(this);
        r_Min.setOnClickListener(this);
        r_Hour.setOnClickListener(this);
        btnSetup.setOnClickListener(this);

        sentPI = PendingIntent.getBroadcast(this,0,new Intent(SENT),0);
        deliverPI = PendingIntent.getBroadcast(this,0,new Intent(DELIVERED),0);

    }

    @Override
    protected void onResume() {
        super.onResume();

        smsSentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // phần code xử lý khi nhận được BroadcastReceiver từ PendingIntent sentPI
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(getApplicationContext(), "SMS Sent!", Toast.LENGTH_SHORT).show();
                        break;
                        
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getApplicationContext(), "Generic failure!", Toast.LENGTH_SHORT).show();
                        break;
                        
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getApplicationContext(), "No Service!", Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getApplicationContext(), "Null PDU!", Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getApplicationContext(), "Radio off!", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        };

        smsDeliveredReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // phần code xử lý khi nhận được BroadcastReceiver từ PendingIntent deliveredPI
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(getApplicationContext(),"Delivered!", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getApplicationContext(), "SMS not delivered!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        // Đăng ký BroadcastReceiver SMS_SENT và SMS_DELIVERED trong file Manifest
        registerReceiver(smsSentReceiver, new IntentFilter(SENT));
        registerReceiver(smsDeliveredReceiver,new IntentFilter(DELIVERED));

    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(smsSentReceiver);
        unregisterReceiver(smsDeliveredReceiver);

    }

    private void comebackHome(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Apply activity transition
            /*Create an object of activity options to enable scene transition animation*/
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);

            /*Pass it to startActivity() method as the second parameter*/
            startActivity(new Intent(this, MainActivity.class), options.toBundle());
        } else {
            // Swap without transition
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void sendMessage(String phone, String msgContent) {

        // show Announce
        String announce = "Message will be sent after " + etTime.getText().toString() + " " + stringDelayTime;
        Toast.makeText(getApplicationContext(), announce, Toast.LENGTH_SHORT).show();
        int delayTimes = Integer.parseInt(etTime.getText().toString()) * 1000;
        if (stringDelayTime.equals(MINS)) {
            delayTimes = delayTimes * 60;
        } else if (stringDelayTime.equals(HOURS)) {
            delayTimes = delayTimes * 3600;
        }

        //object Handler dùng để tạo thời gian trễ cho việc thực thi lệnh sendMessage
        //READ MORE: https://qastack.vn/programming/15874117/how-to-set-delay-in-android
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Get the SmsManager instance and call the sendTextMessage method to send message
                SmsManager smsManager = SmsManager.getDefault();
                //khi App gửi sms kết quả về PendingIntent sentPI và deliverPI
                smsManager.sendTextMessage(phone, null, msgContent, sentPI, deliverPI);
            }
        }, delayTimes);

        //Comeback MainActivity
        comebackHome();
    }

    @Override
    public void onClick(View view) {

        // thực thi đoạn code tương ứng với component ID đã click thông qua rẽ nhánh switch
        switch (view.getId()) {
            case R.id.ivBackHome_SMSPage://trường hợp click vào nút mũi tên phía trên góc trái màn hình
                comebackHome();
                finish();
                break;
            case R.id.btnSetup_SMSPage:// trường hợp click vào nút setup
                //check filled fields, if not show announce
                if (etPhoneNumber.getText().toString().isEmpty() || etSenderBox.getText().toString().isEmpty()) {
                    Toast.makeText(act_sms.this, "Please check your information!", Toast.LENGTH_SHORT).show();
                }
                //check length of phone number > 8 and < 15 digit. if expression false show announce
                else if (!(etPhoneNumber.getText().toString().trim().length() <= 15 && etPhoneNumber.getText().toString().trim().length() >= 8)) {
                    Toast.makeText(act_sms.this, "The phone is not correct, please check!", Toast.LENGTH_SHORT).show();
                }
                //check time delay field filled
                else if (etTime.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please set time first", Toast.LENGTH_SHORT).show();
                } else {
                    btnSetup.setAlpha((float)0.3);
                    sendMessage(etPhoneNumber.getText().toString().trim(), etSenderBox.getText().toString());
                }
                break;
            case R.id.radioHour_SMSPage:
                if (((RadioButton) view).isChecked()) {
                    stringDelayTime = HOURS;
                }
                break;
            case R.id.radioMin_SMSPage:
                if (((RadioButton) view).isChecked()) {
                    stringDelayTime = MINS;
                }
                break;
            case R.id.radioSec_SMSPage:
                if (((RadioButton) view).isChecked()) {
                    stringDelayTime = SEC;
                }
                break;

        }
    }
}