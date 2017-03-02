package ir.mehdi.kelid;

import android.app.Application;
import android.content.Context;

import ir.mehdi.kelid.db.Database;


/**
 * Created by iman on 6/8/2016.
 */
public class KelidApplication extends Application {

    public static volatile Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        UserConfig.loadConfig();
        Database.getInstance().getAllProvince();
        Database.getInstance().loadcity();
        Database.getInstance().loadNode();

    }
}
