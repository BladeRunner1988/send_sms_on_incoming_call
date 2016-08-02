package com.rifat.kpit.smshack;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.ArrayList;

public class MyPhoneReceiver extends BroadcastReceiver {

    Context context;

    private static String SENT = "SMS_SENT";
    private static String DELIVERED = "SMS_DELIVERED";
    private static int MAX_SMS_MESSAGE_LENGTH = 160;

    public void sendSMS(String phoneNumber, String message) {
        Log.i("MY_DEBUG_TAG","sendSMS initiated....!!!");
        PendingIntent piSent = PendingIntent.getBroadcast(context,
                0, new Intent(SENT), 0);
        PendingIntent piDelivered = PendingIntent.getBroadcast(context,
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

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String state = extras.getString(TelephonyManager.EXTRA_STATE);
            Log.v("MY_DEBUG_TAG", state);
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                String phoneNumber = extras
                        .getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.v("MY_DEBUG_TAG", phoneNumber);

                //change the parameters for expected results
                //first parameter is phone number and the second one is the sms to be sent
                sendSMS("+8801914265294","I just sent  you a message....!!!\nYEPPPYY.......!!!");
            }
        }
    }
}