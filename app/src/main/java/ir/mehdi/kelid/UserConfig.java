package ir.mehdi.kelid;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by iman on 6/8/2016.
 */
public class UserConfig {
    public static int city;
    public static int province;


//    static {
//        loadConfig();
//    }

    public static void loadConfig() {
        SharedPreferences preferences = KelidApplication.applicationContext.getSharedPreferences("userconfing", Context.MODE_PRIVATE);
        province = preferences.getInt("province", -1);
        city = preferences.getInt("city", -1);
    }


    public static void save() {
        SharedPreferences preferences = KelidApplication.applicationContext.getSharedPreferences("userconfing", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putInt("province", province);
        edit.putInt("city", city);
        edit.commit();

    }
}
