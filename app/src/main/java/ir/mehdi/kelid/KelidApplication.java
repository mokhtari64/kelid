package ir.mehdi.kelid;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import ir.mehdi.kelid.db.Database;


/**
 * Created by iman on 6/8/2016.
 */
public class KelidApplication extends Application {

    public static volatile Context applicationContext;
//    public static Context context;
//    SharedPreferences app = getSharedPreferences("app", MODE_PRIVATE);

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
