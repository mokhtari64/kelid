package ir.mehdi.kelid;

import android.content.Context;
import android.content.SharedPreferences;

import ir.mehdi.kelid.model.Property;


/**
 * Created by iman on 6/8/2016.
 */
public class UserConfig {
    public static boolean upgrade,immediate,festival;
    public static boolean versionEnable;

    public static int version=11;
    public static String version_lable="1.1.0";
    public static int newVersion=-1;
    public static String change_log=null;

    public static int city;
    public static int province;
//    public static int grid;
    public static boolean firsttime=true;
    public static boolean initialized=false;


    public static String phone;
    public static String temp_phone;
    private static Property property = null;
    public static String userToken;


//    static {
//        loadConfig();
//    }

    public static void loadConfig() {
        SharedPreferences preferences = KelidApplication.applicationContext.getSharedPreferences("userconfing", Context.MODE_PRIVATE);
        province = preferences.getInt("province", -1);
        city = preferences.getInt("city", -1);
        phone = preferences.getString("phone", "-1");
        userToken = preferences.getString("usertoken", "-1");

        initialized = preferences.getBoolean("initialized", false);
        upgrade = preferences.getBoolean("upgrade", false);
        immediate = preferences.getBoolean("immediate", false);
        festival = preferences.getBoolean("festival", false);
        newVersion = preferences.getInt("version", -1);
        versionEnable=preferences.getBoolean("versionEnable",true);
        change_log = preferences.getString("change_log", "");

    }

    public static void saveUserConfig() {
        SharedPreferences preferences = KelidApplication.applicationContext.getSharedPreferences("userconfing", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        UserConfig.phone = UserConfig.temp_phone;
        edit.putString("phone", UserConfig.phone);
        edit.putString("usertoken", UserConfig.userToken);
        edit.commit();
    }

    public static void saveCity() {
        SharedPreferences preferences = KelidApplication.applicationContext.getSharedPreferences("userconfing", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putInt("province", province);
        edit.putInt("city", city);
        edit.putBoolean("initialized", UserConfig.initialized);

        edit.commit();
    }


    public static Property loadLast() {
        if (property != null)
            return property;
        SharedPreferences preferences = KelidApplication.applicationContext.getSharedPreferences("userjob", Context.MODE_PRIVATE);
        int nodeid = preferences.getInt("nodeid", -1);

        property = new Property();

        property.nodeid = nodeid;
        if (nodeid == -1)
            return property;
        property.city = preferences.getInt("city", -1);
        property.title = preferences.getString("title", null);
        property.qr_code = preferences.getString("qr_code", null);
        property.name = preferences.getString("name", null);
//        String ggg = preferences.getString("logopath", null);
//        if (ggg != null)
//            property.logopath = Uri.parse(ggg);
        property.desc = preferences.getString("desc", null);

        property.email = preferences.getString("email", null);
        String imagespathtext = preferences.getString("imagespathtext", null);
        if (imagespathtext != null) {
            String[] split = imagespathtext.split(";");
            if(split!=null) {
                for (int i = 0; i < split.length; i++) {
                    if (split[i].trim().length() > 0)
                        property.addImage(0,split[i],null,0,0);
                }
            }
        }
        property.tel = preferences.getString("tel", null);
        property.telegram = preferences.getString("telegram", null);
        String businesscardpathlocal = preferences.getString("businesscardpathlocal", null);
        if(businesscardpathlocal!=null && businesscardpathlocal.length()>0)
        {
            property.addImage(0,businesscardpathlocal,null,1,0);
        }
        property.mobile = preferences.getString("mobile", null);
        property.region = preferences.getInt("region", 0);
        return property;
    }

    public static void clear() {
        property = new Property();
        property.nodeid = -1;

        SharedPreferences preferences = KelidApplication.applicationContext.getSharedPreferences("userjob", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putInt("nodeid", -1);
        editor.putInt("city", -1);
        editor.putInt("nodeid", -1);
        editor.putInt("region", -1);
        editor.putString("title", null);
        editor.putString("businesscardpathlocal", null);
        editor.putString("name", null);
        editor.putString("desc", null);
        editor.putString("advers", null);
        editor.putString("qr_code", null);
        editor.putString("email", null);
        editor.putString("tel", null);
        editor.putString("mobile", null);
        editor.putString("imagespathtext", null);
        editor.putBoolean("noon", false);//noon, evening, moorning, boarding, bike, cardReader;
        editor.putBoolean("evening", false);//noon, evening, moorning, boarding, bike, cardReader;
        editor.putBoolean("moorning", false);//noon, evening, moorning, boarding, bike, cardReader;
        editor.putBoolean("boarding", false);//noon, evening, moorning, boarding, bike, cardReader;
        editor.putBoolean("bike", false);//noon, evening, moorning, boarding, bike, cardReader;
        editor.putBoolean("cardReader", false);//noon, evening, moorning, boarding, bike, cardReader;
        editor.commit();
    }

    public static void cacheProperty(ir.mehdi.kelid.model.Property a) {

//        if (a==null || a.nodeid == -1)
//            return;
        SharedPreferences preferences = KelidApplication.applicationContext.getSharedPreferences("userjob", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("city", a.city);
        editor.putInt("nodeid", a.nodeid);
        editor.putInt("region", a.region);
        editor.putString("title", a.title);


        editor.putString("name", a.name);
        editor.putString("desc", a.desc);

        editor.putString("email", a.email);
        editor.putString("telegram", a.telegram);
        editor.putString("qr_code", a.qr_code);
        editor.putString("tel", a.tel);
        editor.putString("mobile", a.mobile);




        StringBuilder imagespathtext = new StringBuilder();
        for (int i = 0; i < a.images.size(); i++) {
            Property.Image image = a.images.get(i);
            if (image != null && image.localname != null && image.localname.length() > 0) {
                if(image.deleted)
                    continue;

                if (image.main) {
                    editor.putString("businesscardpathlocal", image.localname);
                } else {
                    imagespathtext.append(image.localname).append(";");
                }
            }
        }
//        a.imagespathtext = imagespathtext.toString();

        editor.putString("imagespathtext", imagespathtext.toString());
        property = a;
        editor.commit();

    }
}
