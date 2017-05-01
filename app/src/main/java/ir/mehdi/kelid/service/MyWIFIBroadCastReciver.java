package ir.mehdi.kelid.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Iman on 8/12/2016.
 */
public class MyWIFIBroadCastReciver extends BroadcastReceiver  {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "wifi connected", Toast.LENGTH_LONG).show();
        try {
            context.startService(new Intent(context, VolleyService.class));

        } catch (Exception e) {

        }


    }
}
