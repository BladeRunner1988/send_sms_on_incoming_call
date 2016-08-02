package com.rifat.kpit.smshack;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showToast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
    }

    private static String SENT = "SMS_SENT";
    private static String DELIVERED = "SMS_DELIVERED";
    private static int MAX_SMS_MESSAGE_LENGTH = 160;

    public void sendSMS(String phoneNumber, String message) {
        PendingIntent piSent = PendingIntent.getBroadcast(getApplicationContext(),
                0, new Intent(SENT), 0);
        PendingIntent piDelivered = PendingIntent.getBroadcast(getApplicationContext(),
                0, new Intent(DELIVERED), 0);
        SmsManager smsManager = SmsManager.getDefault();
        int length = message.length();

        if (length > MAX_SMS_MESSAGE_LENGTH) {
            ArrayList<String> messagelist = smsManager.divideMessage(message);
            smsManager.sendMultipartTextMessage(phoneNumber, null,
                    messagelist, null, null);
        } else {
            smsManager.sendTextMessage(phoneNumber, null, message,
                    piSent, piDelivered);
        }
    }
}
