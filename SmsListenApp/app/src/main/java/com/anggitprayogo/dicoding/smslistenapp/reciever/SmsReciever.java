package com.anggitprayogo.dicoding.smslistenapp.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.anggitprayogo.dicoding.smslistenapp.SmsRecieverActivity;

public class SmsReciever extends BroadcastReceiver {

    private final String TAG = getClass().getSimpleName();

    final SmsManager smsManager = SmsManager.getDefault();

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle extras = intent.getExtras();
        try {
            if (extras != null) {
                final Object[] pduObj = (Object[]) extras.get("pdus");
                for (int i = 0; i < pduObj.length; i++){
                    SmsMessage currentMessage = getIncomingMessage(pduObj[i], extras);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber;
                    String message = currentMessage.getMessageBody();
                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);
                    Intent showSmsIntent = new Intent(context, SmsRecieverActivity.class);
                    showSmsIntent.putExtra(SmsRecieverActivity.EXTRAS_SMS_NO, senderNum);
                    showSmsIntent.putExtra(SmsRecieverActivity.EXTRAS_SMS_MESSAGE, message);
                    context.startActivity(showSmsIntent);
                }
            }

        }catch (Exception e){
            Log.d(TAG, "onReceive: ");
        }
    }

    private SmsMessage getIncomingMessage(Object anObject, Bundle bundle){
        SmsMessage currentSms;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            String format = bundle.getString("format");
            currentSms = SmsMessage.createFromPdu((byte[]) anObject, format);
        }else{
            currentSms = SmsMessage.createFromPdu((byte[]) anObject);
        }

        return currentSms;
    }
}
