package com.rifat.kpit.smshack;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {
    private static final String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private Intent mIntent;

    public void onReceive(Context context, Intent intent) {
        Log.v("SMS HACKER", "Broadcast Receiver Fired Successfully...!!!!");
        mIntent = intent;
        String action = intent.getAction();

        if (action.equals(ACTION_SMS_RECEIVED)) {
            String address = null, str = "";
            SmsMessage[] msgs = getMessagesFromIntent(mIntent);

            if (msgs != null) {
                for (int i = 0; i < msgs.length; i++) {
                    address = msgs[i].getOriginatingAddress();
                    str += msgs[i].getMessageBody().toString();
                    str += "\n";
                }
            }

            if (address != null) {
                Log.v("SMS HACKER",address + "\n" + str);
            } else {
                Log.v("SMS HACKER","No Address Found...!!!");
            }

        } else {
            Log.v("SMS HACKER", "Intent Action Didn't Match....!!!");
        }

    }

    public static SmsMessage[] getMessagesFromIntent(Intent intent) {
        Object[] messages = (Object[]) intent.getSerializableExtra("pdus");
        byte[][] pduObjs = new byte[messages.length][];

        for (int i = 0; i < messages.length; i++) {
            pduObjs[i] = (byte[]) messages[i];
        }

        byte[][] pdus = new byte[pduObjs.length][];
        int pduCount = pdus.length;
        SmsMessage[] msgs = new SmsMessage[pduCount];

        for (int i = 0; i < pduCount; i++) {
            pdus[i] = pduObjs[i];
            msgs[i] = SmsMessage.createFromPdu(pdus[i]);
        }
        return msgs;
    }

}