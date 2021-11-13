package com.example.demoasm1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class act_sms extends AppCompatActivity {

    ImageView ivBackHome;
    Button btnSetup;
    EditText etPhoneNumber, etSenderBox, etTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_sms);

        ivBackHome = findViewById(R.id.ivBackHome_SMSPage);
        btnSetup = findViewById(R.id.btnSetup_SMSPage);
        etPhoneNumber = findViewById(R.id.etPhoneNumber_SMSPage);
        etSenderBox = findViewById(R.id.etSenderBox_SMSPage);
        etTime = findViewById(R.id.etTime_SMSPage);

        btnSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check filled all fields, if not show announce
                if (etPhoneNumber.getText().toString().isEmpty() || etSenderBox.getText().toString().isEmpty() || etTime.getText().toString().isEmpty()) {
                    Toast.makeText(act_sms.this, "Please check your information!", Toast.LENGTH_SHORT).show();
                }
                //check length of phone number > 8 and < 15 digit. if expression false show announce
                else if (!(etPhoneNumber.getText().toString().trim().length() <= 15 && etPhoneNumber.getText().toString().trim().length() >=8)) {
                    Toast.makeText(act_sms.this, "The phone is not correct, please check!", Toast.LENGTH_SHORT).show();
                } else {
                    sendMessage(etPhoneNumber.getText().toString().trim(),etSenderBox.getText().toString());
                }
            }
        });

        ivBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(act_sms.this, MainActivity.class));
                finish();
            }
        });
    }

    private void sendMessage(String phone,String msgContent){
       //TODO
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phone, null, msgContent, null, null);
    }

}