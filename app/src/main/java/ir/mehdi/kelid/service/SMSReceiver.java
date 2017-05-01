package ir.mehdi.kelid.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;

/**
 * The class is called when SMS is received.
 *
 * @author Prashant Kalkar
 *         <p/>
 *         Create Date : Nov 23, 2008
 */
public class SMSReceiver extends BroadcastReceiver {
    SMSDelegate delegate;
    public SMSReceiver(SMSDelegate s)
    {        delegate=s;

    }


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        Object messages[] = (Object[]) bundle.get("pdus");
        SmsMessage smsMessage[] = new SmsMessage[messages.length];
        if (Build.VERSION.SDK_INT >= 19) { //KITKAT
            smsMessage = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        } else {
            Object pdus[] = (Object[]) bundle.get("pdus");

            for (int n = 0; n < pdus.length; n++) {
                smsMessage[n] = SmsMessage.createFromPdu((byte[]) pdus[0]);
            }
        }
        if(delegate!=null)
            delegate.recivedSMS(smsMessage[0].getMessageBody());


//        Toast toast = Toast.makeText(context, "Received SMS: " + smsMessage[0].getMessageBody(), Toast.LENGTH_LONG);
//        toast.show();
    }
}