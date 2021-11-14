package com.example.demoasm1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

public class act_phone extends AppCompatActivity implements View.OnClickListener {

    private final String SEC = "seconds", HOURS = "hours", MINS = "minutes";

    ImageView ivBackArrow;
    EditText etPhoneNumber,etTime;
    Button btnSetup;
    RadioButton r_Hour,r_Min, r_Sec;
    String stringDelayTime;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_phone);

        etPhoneNumber = findViewById(R.id.etPhoneNumber_PhonePage);
        etTime = findViewById(R.id.etTime_PhonePage);
        btnSetup = findViewById(R.id.btnSetup_PhonePage);
        r_Hour = findViewById(R.id.radioHour_PhonePage);
        r_Min = findViewById(R.id.radioMin_PhonePage);
        r_Sec = findViewById(R.id.radioSec_PhonePage);
        ivBackArrow = findViewById(R.id.ivBackHome_PhonePage);

        if (r_Hour.isChecked()){
            stringDelayTime = HOURS;
        }else if(r_Min.isChecked()){
            stringDelayTime = MINS;
        }else {
            stringDelayTime = SEC;
        }

        ivBackArrow.setOnClickListener(this);

        r_Hour.setOnClickListener(this);
        r_Sec.setOnClickListener(this);
        r_Sec.setOnClickListener(this);


        btnSetup.setOnClickListener(this);

    }

    //Phương thức thực hiện cuộc gọi
    private void makePhoneCall(){

        String announcePhoneCall = "A call will be done after " + etTime.getText().toString() +" "+ stringDelayTime;
        Toast.makeText(getApplicationContext(), announcePhoneCall, Toast.LENGTH_SHORT).show();

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
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+etPhoneNumber.getText().toString().trim()));
                startActivity(intent);
            }
        },delayTimes);
    }

    @Override
    public void onClick(View view) {

        // thực thi đoạn code tương ứng với component ID đã click thông qua rẽ nhánh switch
        switch (view.getId()){
            case R.id.ivBackHome_PhonePage://trường hợp click vào nút mũi tên phía trên góc trái màn hình
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                break;
            case R.id.btnSetup_PhonePage:// trường hợp click vào nút setup
                if (etPhoneNumber.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please input phone first", Toast.LENGTH_SHORT).show();
                } else if (!(etPhoneNumber.getText().toString().trim().length() <= 15 && etPhoneNumber.getText().toString().trim().length() >= 8)) {
                    Toast.makeText(getApplicationContext(), "The phone is not correct, please check!", Toast.LENGTH_SHORT).show();
                } else if (etTime.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please set time first", Toast.LENGTH_SHORT).show();
                } else {
                    makePhoneCall();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                break;
            case R.id.radioHour_PhonePage://trường hợp chọn radio button hours
                if (((RadioButton) view).isChecked()){
                    stringDelayTime = HOURS ;
                }
                break;
            case R.id.radioMin_PhonePage://trường hợp chọn radio button minutes
                if (((RadioButton) view).isChecked()){
                    stringDelayTime = MINS;
                }
                break;
            case R.id.radioSec_PhonePage://trường hợp chọn radio button seconds
                if (((RadioButton) view).isChecked()){
                    stringDelayTime = SEC;
                }
                break;

        }
    }
}