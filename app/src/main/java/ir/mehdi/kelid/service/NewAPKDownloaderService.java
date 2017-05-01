package ir.mehdi.kelid.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.io.File;

import app.fanoos.ir.fanoosapp.Constant;
import app.fanoos.ir.fanoosapp.FanoosApplication;
import app.fanoos.ir.fanoosapp.R;
import app.fanoos.ir.fanoosapp.collage.FileUtils;

/**
 * Created by Iman on 8/21/2016.
 */
public class NewAPKDownloaderService extends Service {




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        try {
            SharedPreferences preferences = FanoosApplication.applicationContext.getSharedPreferences("userconfing", Context.MODE_PRIVATE);
            String url = preferences.getString("link", "-1");
            if (url == null || url.equals("-1")) {
                stopSelf();
                return START_NOT_STICKY;
            }
            url = VolleyService.ServerIP + url;


            String destination = FileUtils.getInstance().getAppDirPath();


            destination += url.substring(url.lastIndexOf("/") + 1);
            ;
            final Uri uri = Uri.parse("file://" + destination);

            //Delete update file if exists
            File file = new File(destination);
            if (file.exists()) {
                LocalBroadcastManager instance = LocalBroadcastManager.getInstance(NewAPKDownloaderService.this);
                Intent intent3 = new Intent(Constant.FANOOS_NEW_APP_DOWNLOADED);
                intent3.putExtra(Constant.FANOOS_NEW_DOWNLOADED_MSG, "sss");
                instance.sendBroadcast(intent3);
//                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
//                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent1.putExtra("EXIT", true);
//                startActivity(intent1);
                Intent install = new Intent(Intent.ACTION_VIEW);
                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                install.setDataAndType(uri, "application/vnd.android.package-archive");
                startActivity(install);


                return START_NOT_STICKY;
            }


            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

            request.setTitle(FanoosApplication.applicationContext.getString(R.string.app_name_fa));


            request.setDestinationUri(uri);

            final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            final long downloadId = manager.enqueue(request);


            BroadcastReceiver onComplete = new BroadcastReceiver() {
                public void onReceive(Context ctxt, Intent intent) {
                    LocalBroadcastManager instance = LocalBroadcastManager.getInstance(NewAPKDownloaderService.this);
                    Intent intent3 = new Intent(Constant.FANOOS_NEW_APP_DOWNLOADED);
                    intent3.putExtra(Constant.FANOOS_NEW_DOWNLOADED_MSG, "");
                    instance.sendBroadcast(intent3);

//                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
//                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent1.putExtra("EXIT", true);
//                    startActivity(intent1);
                    Intent install = new Intent(Intent.ACTION_VIEW);
                    install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    install.setDataAndType(uri, manager.getMimeTypeForDownloadedFile(downloadId));
                    startActivity(install);

                    try {
                        unregisterReceiver(this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            };

            registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        } catch (Exception e) {
            e.printStackTrace();

        }


        return START_NOT_STICKY;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
