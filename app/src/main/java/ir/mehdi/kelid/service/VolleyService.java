package ir.mehdi.kelid.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.Consts;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import ir.mehdi.kelid.Constant;
import ir.mehdi.kelid.KelidApplication;
import ir.mehdi.kelid.MainActivity;
import ir.mehdi.kelid.R;
import ir.mehdi.kelid.UserConfig;
import ir.mehdi.kelid.db.DBAdapter;
import ir.mehdi.kelid.db.MySqliteOpenHelper;
import ir.mehdi.kelid.model.Node;
import ir.mehdi.kelid.model.Property;
import ir.mehdi.kelid.ui.AddPropetyActivity;
import ir.mehdi.kelid.ui.fragment.TestFragment;
import ir.mehdi.kelid.utils.FileUtils;
import ir.mehdi.kelid.utils.Utils;


public class VolleyService extends Service implements Constant {


    static VolleyService instance;


    public VolleyService() {

    }


    public static VolleyService getInstance() {
        if (instance == null) {
            instance = new VolleyService();
        }
        return instance;

    }


    public static final String ServerIP = "http://5.9.134.8/api/";
    //    public static final String ServerIP = "http://fanoosiran.ir/";
    //        public static final String ServerIP = "http://192.168.1.33/fanoos/";
    public static final String JOB_DETAIL_URL = ServerIP + "info/";
    public static final String Fanoos_CHANNEL = "http://t.me/fanoos_iran";
    public static String CHECK_VERSION = ServerIP + "api/v1/android-version";
    public static String MY_ADVERS = ServerIP + "api/v1/get-business-on-mobile";
    public static String SEND_ADVERS = ServerIP + "api/v1/business-register/store";
    public static String SEND_NEW_FILE = ServerIP + "pictures/";
    public static String SEND_ADVERS_WITH_TOKEN = ServerIP + "api/v1/business-register/store-auth";
    public static String EDIT_ADVERS_WITH_TOKEN = ServerIP + "api/v1/edit-business";
    public static String CHECK_MY_ADVERS = ServerIP + "api/v1/business-register/check-status-job";
    public static String SEND_ACTIVATION_CODE = ServerIP + "api/v1/business-register/send-code/";
    public static String SEND_JOB_REPORT = ServerIP + "api/v1/report";
    public static String SEND_SUGGESTION = ServerIP + "api/v1/contact-us";
    public static String CHECK_ACTIVATION_CODE = ServerIP + "api/v1/business-register/enter-active-code/";

    public static String JOB_LIST = ServerIP + "api/v1/job-list";
    public static String JOB_DETAIL = ServerIP + "api/v1/show-info/";
    public static String FESTIVAL_PAYMENT = ServerIP + "payment/festival/%s/%s";
    public static String IMMEDIATE_PAYMENT = ServerIP + "payment/immediate/";
    public static String UPGRADE_PAYMENT = ServerIP + "payment/upgrade/";
    public static String JOB_DELETE = ServerIP + "api/v1/job-delete";
    public static String FESTIVAL_LIST = ServerIP + "api/v1/festival";
    public static String LOGIN_REQ_CODE = ServerIP + "api/v1/login-user-api";
    public static String LOGIN_CHECK_CODE = ServerIP + "api/v1/get-api-token";
    public static String IMAGE_PATH = ServerIP + "images_user/large/";
    public static String IMAGE_thumbnail_PATH = ServerIP + "images_user/thumbnail/";
    public static String BUSINESS_PATH = ServerIP + "api/v1/business-card";


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (Utils.isNetworkConnected()) {
//            CheckVersionAsync();
            if (UserConfig.userToken != null && !UserConfig.userToken.equals("-1")) {
                CheckMyAdvers();
            }


        }
//        else {
//
//            checkMyAdversTimerActive = false;
//            automaticServiceTimer.cancel();
//            stopSelf();
//        }

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    void displayAcceptJobNotification(Vector<Property> jobs) {
        if (jobs == null || jobs.size() == 0)
            return;
        int i = 0;
        for (Property property : jobs) {
            int id = (int) System.currentTimeMillis();
            Intent intent = new Intent(KelidApplication.applicationContext, MainActivity.class);
            intent.putExtra("local_id", property.local_id);
            intent.putExtra("notification_id", id);
            intent.setAction(Long.toString(id));
            PendingIntent pIntent = PendingIntent.getActivity(KelidApplication.applicationContext, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_ONE_SHOT);

            Notification.Builder n = new Notification.Builder(this)
                    .setContentTitle(KelidApplication.applicationContext.getString(R.string.app_name))
                    .setContentText((property.status == 1) ? KelidApplication.applicationContext.getString(R.string.accepted_job) : KelidApplication.applicationContext.getString(R.string.reject_job))
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setAutoCancel(true).setContentIntent(pIntent);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                n = n.addAction(R.drawable.done, getString(R.string.showjob), pIntent);
            }

            Notification notification = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                notification = n.build();
            } else {
                notification = n.getNotification();
            }

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(id, notification);
        }
    }


//    private void setUpNotification(int cnt, int fixnum){
//        SharedPreferences preferences = KelidApplication.applicationContext.getSharedPreferences("userconfing", Context.MODE_PRIVATE);
//        int local_fix_num = preferences.getInt("local_fix_num", -1);
//        int next_cnt = preferences.getInt("next_cnt", -1);
//        boolean notif = false;
//        if (local_fix_num == -1) {
//            local_fix_num = fixnum;
//            if (cnt > fixnum)
//                notif = true;
//            next_cnt = cnt / fixnum + 1;
//        } else {
//            if (next_cnt * local_fix_num < cnt) {
//                notif = true;
//            }
//            local_fix_num = fixnum;
//            next_cnt = cnt / fixnum + 1;
//        }
//        SharedPreferences.Editor edit = preferences.edit();
//        edit.putInt("local_fix_num", local_fix_num);
//        edit.putInt("next_cnt", next_cnt);
//        edit.commit();
//        if (!notif)
//            return;
//
//        String s = KelidApplication.applicationContext.getString(R.string.job_cnt_message);
//        s = String.format(s, cnt);
//
//        NotificationManager  mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//
//        Intent intentNotif = new Intent(this,CountNotificationActivity.class);
//        intentNotif.putExtra("msg", s);
//        intentNotif.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent pendIntent = PendingIntent.getActivity(this, 579, intentNotif, PendingIntent.FLAG_UPDATE_CURRENT);
//
//
//        RemoteViews  mRemoteViews = new RemoteViews(getPackageName(), R.layout.limit_notification);
//
//
//        mRemoteViews.setTextViewText(R.id.title, getResources().getString(R.string.app_name_fa));
//        // notification's content
//        mRemoteViews.setTextViewText(R.id.text, s);
//
//        NotificationCompat.Builder  mBuilder = new NotificationCompat.Builder(this);
//        int l = (int) System.currentTimeMillis();
//        CharSequence ticker = getResources().getString(R.string.app_name_fa);
//        int apiVersion = Build.VERSION.SDK_INT;
//        Notification mNotification=null;
//        if (apiVersion < Build.VERSION_CODES.HONEYCOMB) {
//            mNotification = new Notification(R.drawable.ic_launcher, ticker, System.currentTimeMillis());
//            mNotification.contentView = mRemoteViews;
//            mNotification.contentIntent = pendIntent;
//
//            mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
//
//            mNotification.defaults |= Notification.DEFAULT_LIGHTS;
//
//
////            startForeground(l, mNotification);
//
//        }else if (apiVersion >= Build.VERSION_CODES.HONEYCOMB) {
//            mBuilder.setSmallIcon(R.drawable.ic_launcher)
//                    .setAutoCancel(false)
//                    .setOngoing(true)
//                    .setContentIntent(pendIntent)
//                    .setContent(mRemoteViews).setAutoCancel(true);
////                    .setTicker(ticker);
//            mNotification=mBuilder.build();
//
////            startForeground(l, mNotification);
//
//        }
//        int api = Build.VERSION.SDK_INT;
//        if (api < Build.VERSION_CODES.HONEYCOMB) {
//            mNotificationManager.notify(0, mNotification);
//        }else if (api >= Build.VERSION_CODES.HONEYCOMB) {
//            mNotificationManager.notify(l,mNotification);
//        }
//    }


//    void displayJobCountNotification(int cnt, int fixnum) {
//
//
//
//        Notification.Builder n = new Notification.Builder(this)
//
//                .setAutoCancel(true).setContentIntent(pIntent);
//
//
//        Notification notification = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
//            notification = n.build();
//        } else {
//            notification = n.getNotification();
//        }
//
//        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.limit_notification);
//
//        contentView.setTextViewText(R.id.title,KelidApplication.applicationContext.getString(R.string.app_name_fa));
//        contentView.setTextViewText(R.id.text, s);
//        notification.contentView = contentView;
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0, notification);
//
//
//    }


//    public void CheckVersionAsync() {
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
//                CHECK_VERSION, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject jsonObject) {
//                        try {
//                            int count = jsonObject.getInt("count_job");
//                            JSONObject b = jsonObject.getJSONObject("setting_fanoos");
//                            int fixnum = 10000;
//                            try {
//                                fixnum = b.getInt("default_count");
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            setUpNotification(count, fixnum);
//                            JSONArray a = jsonObject.getJSONArray("andoird");
//                            int newVersion =-1;
//                            int lastid=0;
//                            for (int i = 0; i < a.length(); i++) {
//                                int newVersion2=((JSONObject) a.get(i)).getInt("api_level");
//
//                                if(newVersion2>newVersion)
//                                {
//                                    lastid=i;
//                                    newVersion=newVersion2;
//                                }
//                            }
//                            String link = ((JSONObject) a.get(lastid)).getString("link");
//                            String change_log = ((JSONObject) a.get(lastid)).getString("change_log");
//                            SharedPreferences preferences = KelidApplication.applicationContext.getSharedPreferences("userconfing", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor edit = preferences.edit();
//                            edit.putInt("version", newVersion);
//                            UserConfig.newVersion=newVersion;
//                            UserConfig.change_log=change_log;
//
//                            edit.putString("link", link);
//                            edit.putString("change_log", change_log);
//
//                            edit.commit();
//
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                if (error != null)
//                    error.getNetworkTimeMs();
//
//            }
//        });
//
//        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(60 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        KelidApplication.getInstance().addToRequestQueue(jsonObjReq, "1");
//
//    }

    public void CheckMyAdvers() {

        try {
            String data = CHECK_MY_ADVERS + "?" + URLEncoder.encode("api_token", "UTF8") + "=" + UserConfig.userToken;
            StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                    data,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String aaaa) {
                            boolean showNotif = false;
                            Vector<Property> jobs = new Vector<>();
                            try {
                                JSONArray jsonArray = new JSONArray(aaaa);
                                Collection<Property> values = MySqliteOpenHelper.getInstance().myPropertys.values();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    int id = jsonObject.getInt("id");
                                    int status1 = jsonObject.getInt("status");
                                    for (Property userJob : values) {
                                        if (userJob.myproperty == 1 && userJob.remote_id == id) {
                                            if (userJob.status != status1) {
                                                userJob.status = status1;
                                                if (status1 == 1 || status1 == 2) {
                                                    showNotif = true;
                                                    jobs.add(userJob);
                                                }
                                                MySqliteOpenHelper.getInstance().insertORUpdateProperty(userJob);
                                            }


                                        }
                                    }

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            if (showNotif) {
                                displayAcceptJobNotification(jobs);
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error != null)
                        error.getNetworkTimeMs();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    Collection<Property> values = MySqliteOpenHelper.getInstance().myPropertys.values();
                    JSONArray jsonArray = new JSONArray();
                    for (Property userJob : values) {
                        if (userJob.myproperty == 1 && userJob.remote_id != 0) {
                            jsonArray.put("" + userJob.remote_id);
                        }
                    }
                    if (jsonArray.length() <= 0)
                        return null;
//                    JSONObject student2 = new JSONObject();
                    params.put("id", jsonArray.toString());

                    return params;
                }

            };
            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(3 * 60 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            KelidApplication.getInstance().addToRequestQueue(jsonObjReq, "2");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


// Adding request to request queue


    }

    public String SendActivationCode(final ServiceDelegate delegate, final int request, final Property userJob, final String mobile) {


        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                SEND_ACTIVATION_CODE + userJob.token,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String aa) {
                        if (aa == null) {
                            delegate.onObjectReslut(request, ServiceDelegate.ERROR_CODE, null, null);
                        }
                        try {

                            delegate.onObjectReslut(request, ServiceDelegate.OK_CODE, null, new JSONObject(aa));
                        } catch (Exception e) {
                            e.printStackTrace();
                            delegate.onObjectReslut(request, ServiceDelegate.ERROR_CODE, null, null);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                delegate.onObjectReslut(request, ServiceDelegate.ERROR_CODE, null, null);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", mobile);

                return params;
            }

        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(15 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        String code = "" + System.currentTimeMillis();
        KelidApplication.getInstance().addToRequestQueue(jsonObjReq, code);
        return code;

    }

    public String Login(final ServiceDelegate delegate, final int request, final Property userJob, final String mobile) {


        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                LOGIN_REQ_CODE,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if (response == null) {
                            delegate.onObjectReslut(request, ServiceDelegate.ERROR_CODE, null, null);
                        }
                        try {
                            JSONObject a = new JSONObject(response);
                            delegate.onObjectReslut(request, ServiceDelegate.OK_CODE, null, a);
                        } catch (Exception e) {
                            e.printStackTrace();
                            delegate.onObjectReslut(request, ServiceDelegate.ERROR_CODE, null, null);
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                delegate.onObjectReslut(request, ServiceDelegate.ERROR_CODE, null, null);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", mobile);

                return params;
            }

        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(15 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        String code = "" + System.currentTimeMillis();
        KelidApplication.getInstance().addToRequestQueue(jsonObjReq, code);
        return code;

    }

    public String LoginCheckCode(final ServiceDelegate delegate, final int request, final Property userJob, final String mobile, final String activate_code) {


        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                LOGIN_CHECK_CODE,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String jsonArray) {
                        if (jsonArray == null) {
                            delegate.onObjectReslut(request, ServiceDelegate.ERROR_CODE, null, null);
                        }
                        try {
                            JSONObject a = new JSONObject(jsonArray);
                            delegate.onObjectReslut(request, ServiceDelegate.OK_CODE, null, a);
                        } catch (Exception e) {
                            e.printStackTrace();
                            delegate.onObjectReslut(request, ServiceDelegate.ERROR_CODE, null, null);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                delegate.onObjectReslut(request, ServiceDelegate.ERROR_CODE, null, null);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("activate_code", activate_code);
                params.put("mobile", mobile);

                return params;
            }

        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(15 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        String code = "" + System.currentTimeMillis();
        KelidApplication.getInstance().addToRequestQueue(jsonObjReq, code);
        return code;

    }

    public void SendjobReport(final ServiceDelegate delegate, final int request, final Property userJob) {


        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                SEND_JOB_REPORT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                delegate.onObjectReslut(request, ServiceDelegate.OK_CODE, null, null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                delegate.onObjectReslut(request, ServiceDelegate.ERROR_CODE, null, null);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", "" + userJob.remote_id);
                params.put("code", "code_" + userJob.reportindex);
                if (userJob.reportindex == 7) {
                    params.put("comment", userJob.reporttext);
                }
                return params;
            }

        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(15 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        KelidApplication.getInstance().addToRequestQueue(jsonObjReq, "2");

    }

    public String PropertyDetail(final ServiceDelegate delegate, final int req_code, final Property userJob) {


        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                JOB_DETAIL + userJob.remote_id, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        delegate.onObjectReslut(req_code, ServiceDelegate.OK_CODE, userJob, jsonArray);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                delegate.onObjectReslut(req_code, ServiceDelegate.ERROR_CODE, null, null);
            }
        });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        String code = "" + System.currentTimeMillis();
        KelidApplication.getInstance().addToRequestQueue(jsonObjReq, code);
        return code;
    }

    public String MyJobList(final ServiceDelegate delegate, final int request) {
        try {
            String data = URLEncoder.encode("registration_mobile", "UTF8") + "=" + URLEncoder.encode(UserConfig.phone, "UTF8");
            data += "&" + URLEncoder.encode("api_token", "UTF8") + "=" + UserConfig.userToken;

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    MY_ADVERS + "?" + data, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject jsonArray) {
                            if (delegate != null)
                                delegate.onObjectReslut(request, ServiceDelegate.OK_CODE, null, jsonArray);
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    if (delegate != null)
                        delegate.onObjectReslut(request, ServiceDelegate.ERROR_CODE, null, null);
                }
            });
            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            String code = "" + System.currentTimeMillis();
            KelidApplication.getInstance().addToRequestQueue(jsonObjReq, code);
            return code;
//            KelidApplication.addToRequestQueue(jsonObjReq, "2");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public void SendSuggestion(final ServiceDelegate delegate, final int request, final String name, final String mobile, final String email, final String title, final String content) {


        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                SEND_SUGGESTION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (delegate != null)
                    delegate.onObjectReslut(request, ServiceDelegate.OK_CODE, null, null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (delegate != null)
                    delegate.onObjectReslut(request, ServiceDelegate.ERROR_CODE, null, null);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                if (name.length() > 0)
                    params.put("name", name);
                if (mobile.length() > 0)
                    params.put("mobile", mobile);
                if (email.length() > 0)
                    params.put("email", email);
                if (title.length() > 0)
                    params.put("subject", title);
                if (content.length() > 0)
                    params.put("content", content);

                return params;
            }

        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(15 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        KelidApplication.getInstance().addToRequestQueue(jsonObjReq, "2");

    }

    public String ChechActivationCode(final ServiceDelegate delegate, final int request, final Property userJob, final String activateCode) {


        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                CHECK_ACTIVATION_CODE + ((userJob == null) ? "" : userJob.token), new Response.Listener<String>() {

            @Override
            public void onResponse(String jsonArray) {
                if (jsonArray == null) {
                    delegate.onObjectReslut(request, ServiceDelegate.ERROR_CODE, null, null);
                }
                try {
                    delegate.onObjectReslut(request, ServiceDelegate.OK_CODE, userJob, new JSONObject(jsonArray));
                } catch (Exception e) {
                    e.printStackTrace();
                    delegate.onObjectReslut(request, ServiceDelegate.ERROR_CODE, null, userJob);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                delegate.onObjectReslut(request, ServiceDelegate.ERROR_CODE, null, null);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("activateCode", activateCode);

                return params;
            }

        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(15 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        String code = "" + System.currentTimeMillis();
        KelidApplication.getInstance().addToRequestQueue(jsonObjReq, code);
        return code;

    }


    public String PropertyList(final ServiceDelegate delegate, final Node currentNode, final int page, int city, int region, String word) {
        try {
            String data = "?city-id=" + ((city == 0) ? UserConfig.city : city) + "&page=" + page;
            if (region != 0)
                data = data + "&region-id=" + region;
            if (currentNode != DBAdapter.getInstance().root)
                data = data + "&level-name=level" + currentNode.level + "&level-code=" + currentNode.id;
            if (word != null && word.length() > 1) {
                data += "&word=" + URLEncoder.encode(word);
            }
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    JOB_LIST + data, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject jsonArray) {
                            delegate.onObjectReslut(ServiceDelegate.DEFAULT_REQUEST_CODE, ServiceDelegate.OK_CODE, null, jsonArray);
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    delegate.onObjectReslut(ServiceDelegate.DEFAULT_REQUEST_CODE, ServiceDelegate.ERROR_CODE, null, null);
                }
            });
            String code = "" + System.currentTimeMillis();
            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            KelidApplication.getInstance().addToRequestQueue(jsonObjReq, code);
            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public void PropertyFestival(final ServiceDelegate delegate) {


        try {
            StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                    FESTIVAL_LIST, new Response.Listener<String>() {

                @Override
                public void onResponse(String jsonArray) {
                    if (jsonArray == null) {
                        delegate.onObjectReslut(ServiceDelegate.DEFAULT_REQUEST_CODE, ServiceDelegate.ERROR_CODE, null, null);
                    }
                    try {
                        delegate.onObjectReslut(ServiceDelegate.DEFAULT_REQUEST_CODE, ServiceDelegate.OK_CODE, null, new JSONObject(jsonArray));
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    delegate.onObjectReslut(ServiceDelegate.DEFAULT_REQUEST_CODE, ServiceDelegate.ERROR_CODE, null, null);
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("province", "" + UserConfig.province);

                    return params;
                }

            };


//            StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
//                    FESTIVAL_LIST, null,
//                    new Response.Listener<String>() {
//
//                        @Override
//                        public void onResponse(JSONObject jsonArray) {
//                            delegate.onObjectReslut(ServiceDelegate.DEFAULT_REQUEST_CODE, ServiceDelegate.OK_CODE, null, jsonArray);
//                        }
//                    }, new Response.ErrorListener() {
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    delegate.onObjectReslut(ServiceDelegate.DEFAULT_REQUEST_CODE, ServiceDelegate.ERROR_CODE, null, null);
//                }
//            });
            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            KelidApplication.getInstance().addToRequestQueue(jsonObjReq, "2");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String PropertyDelete(final ServiceDelegate delegate, final int requestCOde, final Property userJob) {
        try {
            String data = URLEncoder.encode("api_token", "UTF8") + "=" + UserConfig.userToken;
            data += "&" + URLEncoder.encode("id", "UTF8") + "=" + userJob.remote_id;
            StringRequest strReq = new StringRequest(Request.Method.GET,
                    JOB_DELETE + "?" + data, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    delegate.onObjectReslut(requestCOde, ServiceDelegate.OK_CODE, userJob, response);
                    System.out.println(response);
//                    Log.d(TAG, response.toString());
//                    pDialog.hide();

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error != null)
                        error.getNetworkTimeMs();
                    delegate.onObjectReslut(requestCOde, ServiceDelegate.ERROR_CODE, userJob, null);
//                    VolleyLog.d(TAG, "Error: " + error.getMessage());
//                    pDialog.hide();
                }
            });
            strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            String code = "" + System.currentTimeMillis();
            KelidApplication.getInstance().addToRequestQueue(strReq, code);
            return code;
//            KelidApplication.addToRequestQueue(strReq, "3");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public String GetBusinessTemplate(final ServiceDelegate delegate) {
        try {

            JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                    BUSINESS_PATH, null,
                    new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray jsonArray) {
                            delegate.onObjectReslut(ServiceDelegate.DEFAULT_REQUEST_CODE, ServiceDelegate.OK_CODE, null, jsonArray);
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    delegate.onObjectReslut(ServiceDelegate.DEFAULT_REQUEST_CODE, ServiceDelegate.ERROR_CODE, null, null);
                }
            });
            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(40 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            String code = "" + System.currentTimeMillis();
            KelidApplication.getInstance().addToRequestQueue(jsonObjReq, code);
            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    public String SendNewAdvers(final ServiceDelegate delegate, final int reqCode, final int curentcode, final Property userJob) {
        MultipartRequest multipartRequest = new MultipartRequest(SEND_ADVERS, userJob, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                String a = new String(error.networkResponse.data);
                delegate.onObjectReslut(reqCode, ServiceDelegate.ERROR_CODE, null, null);

            }
        }, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                delegate.onObjectReslut(reqCode, ServiceDelegate.OK_CODE, userJob, response);
            }
        });
        String code = "" + System.currentTimeMillis();
        KelidApplication.getInstance().addToRequestQueue(multipartRequest, code);
        return code;
//        KelidApplication.addToRequestQueue(multipartRequest, "1111111");
    }

    public String SendNewAdversWithToekn(final ServiceDelegate delegate, final int reqCode, final int curentcode, final Property userJob) {
        try {


            MultipartRequest multipartRequest = new MultipartRequest(SEND_ADVERS_WITH_TOKEN + "?" + URLEncoder.encode("api_token", "UTF8") + "=" + UserConfig.userToken, userJob, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    String a = new String(error.networkResponse.data);
//                    System.out.println();
                    delegate.onObjectReslut(reqCode, ServiceDelegate.ERROR_CODE, null, null);

                }
            }, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    delegate.onObjectReslut(reqCode, ServiceDelegate.OK_CODE, userJob, response);
                }
            });
            String code = "" + System.currentTimeMillis();
            KelidApplication.getInstance().addToRequestQueue(multipartRequest, code);
            return code;
//            KelidApplication.getInstance().addToRequestQueue(multipartRequest, "33333");
        } catch (Exception e) {
//            delegate.onObjectReslut(reqCode, ServiceDelegate.ERROR_CODE, userJob, null);
        }
        return null;
    }

    public String SendEditAdversWithToekn(final ServiceDelegate delegate, final int reqCode, final int curentcode, final Property userJob) {
        String code = "" + System.currentTimeMillis();
        try {


            MultipartRequest multipartRequest = new MultipartRequest(EDIT_ADVERS_WITH_TOKEN + "?" + URLEncoder.encode("api_token", "UTF8") + "=" + UserConfig.userToken, userJob, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

//                    String a = new String(error.networkResponse.data);
                    delegate.onObjectReslut(reqCode, ServiceDelegate.ERROR_CODE, null, null);

                }
            }, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    delegate.onObjectReslut(reqCode, ServiceDelegate.OK_CODE, userJob, response);
                }
            });

            KelidApplication.getInstance().addToRequestQueue(multipartRequest, code);
            return code;

        } catch (Exception e) {
//            delegate.onObjectReslut(reqCode, ServiceDelegate.ERROR_CODE, userJob, null);
//            return code;
        }
        return null;
    }

    public void sendPhoto(final Property property, final Property.Image image) {
        if (image.uploadProgressBar != null)
            image.uploadProgressBar.setVisibility(View.INVISIBLE);
        PhotoPartRequest multipartRequest = new PhotoPartRequest(SEND_NEW_FILE, null, image.localImageFile, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (image.uploadProgressBar != null)
                image.uploadProgressBar.post(new Runnable() {
                    @Override
                    public void run() {
                        image.uploadProgressBar.setVisibility(View.VISIBLE);
                    }
                });
            }
        }, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int id = jsonObject.getInt("id");
                    int propertyId = jsonObject.getInt("propertyId");
                    String path = jsonObject.getString("path");
                    property.remote_id = propertyId;
                    image.remotename = path;
                    image.remote_Id = id;
                    for (int i = 0; i < property.images.size(); i++) {
                        Property.Image image1 = property.images.get(i);
                        if(image1.remote_Id==0)
                        {
                            VolleyService.getInstance().sendPhoto(property,image1);
                        }
                    }
                    if (image.uploadProgressBar != null)
                        image.uploadProgressBar.post(new Runnable() {
                            @Override
                            public void run() {
                                image.uploadProgressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                } catch (Exception e) {

                }
//                delegate.onObjectReslut(reqCode, ServiceDelegate.OK_CODE, userJob, response);
            }
        });
        String code = "" + System.currentTimeMillis();
        KelidApplication.getInstance().addToRequestQueue(multipartRequest, code);

    }


    public static class MyAdversImageDownload extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {

            try {
                if (params[0] == null || params[1] == null)
                    return null;
                File f = new File(params[1]);
                if (f.exists()) {
                    return null;
                }
                File parentFile = f.getParentFile();
                if (!parentFile.exists())
                    parentFile.mkdirs();
                URL url = new URL(params[0]);
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setRequestMethod("GET");
                urlc.setDoOutput(true);
                urlc.connect();
                FileOutputStream fo = new FileOutputStream(f);
                InputStream ins = urlc.getInputStream();
                byte[] buffer = new byte[1024];
                int bl;
                while ((bl = ins.read(buffer)) > 0) {
                    fo.write(buffer, 0, bl);
                }

                fo.close();
                FileUtils.saveToGallery(f.getAbsolutePath());

            } catch (final Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

        }
    }

    class MultipartRequest extends Request<String> {

        private MultipartEntity entity;

        String boundary;


        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            setRetryPolicy(new DefaultRetryPolicy(3 * 60 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            params.put("Content-Type", "multipart/form-data; boundary=" + boundary + "; charset=utf-8");
            return params;
        }

        private final Response.Listener<String> mListener;
        Property userJob;


        public MultipartRequest(String url, Property userJob, Response.ErrorListener errorListener, Response.Listener<String> listener) {
            super(Method.POST, url, errorListener);

            setRetryPolicy(new DefaultRetryPolicy(5 * 60 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            boundary = "" + System.currentTimeMillis();
            entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, boundary, Charset.forName(HTTP.UTF_8));


            mListener = listener;
            this.userJob = userJob;


            buildMultipartEntity();
        }


        private void buildMultipartEntity() {
            try {
                if (userJob.remote_id != 0) {
                    entity.addPart("id", new StringBody(Utils.asciiNumners("" + userJob.remote_id), Consts.UTF_8));
                }
                if (userJob.mobile != null)
                    entity.addPart("mobile", new StringBody(Utils.asciiNumners(userJob.mobile), Consts.UTF_8));
                if (userJob.tel != null)
                    entity.addPart("tel", new StringBody(Utils.asciiNumners(userJob.tel), Consts.UTF_8));
                if (userJob.email != null)
                    entity.addPart("email", new StringBody(userJob.email, Consts.UTF_8));
                if (userJob.name != null)
                    entity.addPart("name", new StringBody(userJob.name, Consts.UTF_8));
                if (userJob.region != 0)
                    entity.addPart("region_id", new StringBody("" + userJob.region));
                if (userJob.address != null)
                    entity.addPart("address", new StringBody(userJob.address, Consts.UTF_8));
                if (userJob.title != null)
                    entity.addPart("title", new StringBody(userJob.title, Consts.UTF_8));
                if (userJob.qr_code != null)
                    entity.addPart("qr_code", new StringBody(userJob.qr_code));
                if (userJob.telegram != null)
                    entity.addPart("telegram", new StringBody(userJob.telegram));
                if (userJob.city != 0) entity.addPart("city", new StringBody("" + userJob.city));
                if (userJob.desc != null)
                    entity.addPart("description", new StringBody(userJob.desc, Consts.UTF_8));
                if (userJob.nodeid != 0) {
                    Node node = DBAdapter.getInstance().allNodes.get(userJob.nodeid);
                    entity.addPart("level3", new StringBody("" + userJob.nodeid));
                    entity.addPart("level2", new StringBody("" + node.parent.id));
                    entity.addPart("level1", new StringBody("" + node.parent.parent.id));
                }
                entity.addPart("province", new StringBody("" + DBAdapter.getInstance().indexCities.get(userJob.city).provincecode));
                String defaultPic = null;
                JSONArray delete = new JSONArray();
                int i = 0;
                for (Property.Image filePath : userJob.images) {
                    if (filePath.deleted) {
                        delete.put(Utils.getName(filePath.localname));
                        continue;
                    }
                    if (filePath.main)
                        defaultPic = Utils.getName(filePath.localname);
                    File uploadFile = new File(filePath.localname);

//                    String fileName = uploadFile.getName();
                    entity.addPart("file[" + i + "]", new FileBody(uploadFile));
//                    entity.addPart("file["+i+"]", new StringBody(fileName, Consts.UTF_8));
                    i++;
                }
                if (defaultPic != null) entity.addPart("defaultPic", new StringBody(defaultPic));
                if (delete.length() > 0)
                    entity.addPart("deleted", new StringBody(delete.toString()));

            } catch (Exception e) {
                VolleyLog.e("UnsupportedEncodingException");
            }
        }

        @Override
        public String getBodyContentType() {
            return "multipart/form-data; boundary=" + boundary + "; charset=utf-8";

//            return "multipart/form-data; charset=utf-8"+";"+entity.getContentType().getValue().split(";")[1];
        }

        @Override
        public byte[] getBody() throws AuthFailureError {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            try {
                entity.writeTo(dos);
            } catch (IOException e) {
//                VolleyLog.e("IOException writing to ByteArrayOutputStream");
            }
            return bos.toByteArray();
        }

        @Override
        protected Response<String> parseNetworkResponse(NetworkResponse response) {
            return Response.success(new String(response.data), null);
        }

        @Override
        protected void deliverResponse(String response) {
            mListener.onResponse(response);
        }
    }

    class PhotoPartRequest extends Request<String> {

        private MultipartEntity entity;
        File filePath;

        String boundary;


        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            setRetryPolicy(new DefaultRetryPolicy(3 * 60 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            params.put("Content-Type", "multipart/form-data; boundary=" + boundary + "; charset=utf-8");
            return params;
        }

        private final Response.Listener<String> mListener;
        Property userJob;


        public PhotoPartRequest(String url, Property userJob, File filepath, Response.ErrorListener errorListener, Response.Listener<String> listener) {
            super(Method.POST, url, errorListener);
            this.filePath = filepath;
            setRetryPolicy(new DefaultRetryPolicy(5 * 60 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            boundary = "" + System.currentTimeMillis();
            entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, boundary, Charset.forName(HTTP.UTF_8));


            mListener = listener;
            this.userJob = userJob;


            buildMultipartEntity();
        }


        private void buildMultipartEntity() {
            try {
//                if (userJob.remote_id != 0) {
//                    entity.addPart("id", new StringBody(Utils.asciiNumners("" + userJob.remote_id), Consts.UTF_8));
//                }
//                if (userJob.mobile != null)
//                    entity.addPart("mobile", new StringBody(Utils.asciiNumners(userJob.mobile), Consts.UTF_8));
//                if (userJob.tel != null)
//                    entity.addPart("tel", new StringBody(Utils.asciiNumners(userJob.tel), Consts.UTF_8));
//                if (userJob.email != null)
//                    entity.addPart("email", new StringBody(userJob.email, Consts.UTF_8));
//                if (userJob.name != null)
//                    entity.addPart("name", new StringBody(userJob.name, Consts.UTF_8));
//                if (userJob.region != 0)
//                    entity.addPart("region_id", new StringBody("" + userJob.region));
//                if (userJob.address != null)
//                    entity.addPart("address", new StringBody(userJob.address, Consts.UTF_8));
//                if (userJob.title != null)
//                    entity.addPart("title", new StringBody(userJob.title, Consts.UTF_8));
//                if (userJob.qr_code != null)
//                    entity.addPart("qr_code", new StringBody(userJob.qr_code));
//                if (userJob.telegram != null)
//                    entity.addPart("telegram", new StringBody(userJob.telegram));
//                if (userJob.city != 0) entity.addPart("city", new StringBody("" + userJob.city));
//                if (userJob.desc != null)
//                    entity.addPart("description", new StringBody(userJob.desc, Consts.UTF_8));
//                if (userJob.nodeid != 0) {
//                    Node node = DBAdapter.getInstance().allNodes.get(userJob.nodeid);
//                    entity.addPart("level3", new StringBody("" + userJob.nodeid));
//                    entity.addPart("level2", new StringBody("" + node.parent.id));
//                    entity.addPart("level1", new StringBody("" + node.parent.parent.id));
//                }
//                entity.addPart("province", new StringBody("" + DBAdapter.getInstance().indexCities.get(userJob.city).provincecode));
//                String defaultPic = null;
//                JSONArray delete = new JSONArray();
//                int i = 0;
//                entity.addPart("level3", new StringBody("" + userJob.nodeid));
                File uploadFile = filePath;
                entity.addPart("image", new FileBody(uploadFile));
//                for (Property.Image filePath : userJob.images) {
//                    if (filePath.deleted) {
//                        delete.put(Utils.getName(filePath.localname));
//                        continue;
//                    }
//                    if (filePath.main)
//                        defaultPic = Utils.getName(filePath.localname);
//                    File uploadFile = new File(filePath.localname);
//
//
//                    entity.addPart("file[" + i + "]", new FileBody(uploadFile));
//
//                    i++;
//                }
//                if (defaultPic != null) entity.addPart("defaultPic", new StringBody(defaultPic));
//                if (delete.length() > 0)
//                    entity.addPart("deleted", new StringBody(delete.toString()));

            } catch (Exception e) {
                VolleyLog.e("UnsupportedEncodingException");
            }
        }

        @Override
        public String getBodyContentType() {
            return "multipart/form-data; boundary=" + boundary + "; charset=utf-8";

//            return "multipart/form-data; charset=utf-8"+";"+entity.getContentType().getValue().split(";")[1];
        }

        @Override
        public byte[] getBody() throws AuthFailureError {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            try {
                entity.writeTo(dos);
            } catch (IOException e) {
//                VolleyLog.e("IOException writing to ByteArrayOutputStream");
            }
            return bos.toByteArray();
        }

        @Override
        protected Response<String> parseNetworkResponse(NetworkResponse response) {
            return Response.success(new String(response.data), null);
        }

        @Override
        protected void deliverResponse(String response) {
            mListener.onResponse(response);
        }
    }


}


//public static interface MultipartProgressListener {
//    void transferred(long transfered, int progress);
//    void startTransfer(long length);
//}
//
//public static class CountingOutputStream extends FilterOutputStream {
//    private final MultipartProgressListener progListener;
//    private long transferred;
//    private long fileLength;
//
//    public CountingOutputStream(final OutputStream out, long fileLength,
//                                final MultipartProgressListener listener) {
//        super(out);
//        this.fileLength = fileLength;
//        this.progListener = listener;
//        this.transferred = 0;
//    }
//
//    public void write(byte[] b, int off, int len) throws IOException {
//        out.write(b, off, len);
//        if (progListener != null) {
//            this.transferred += len;
//            int prog = (int) (transferred * 100 / fileLength);
//            this.progListener.transferred(this.transferred, prog);
//        }
//    }
//
//    public void write(int b) throws IOException {
//        out.write(b);
//        if (progListener != null) {
//            this.transferred++;
//            int prog = (int) (transferred * 100 / fileLength);
//            this.progListener.transferred(this.transferred, prog);
//        }
//    }
//
//}

