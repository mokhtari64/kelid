package ir.mehdi.kelid.utils;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.Vector;

import ir.mehdi.kelid.Const;
import ir.mehdi.kelid.KelidApplication;
import ir.mehdi.kelid.R;
import ir.mehdi.kelid.db.DBAdapter;
import ir.mehdi.kelid.db.MySqliteOpenHelper;
import ir.mehdi.kelid.model.Node;
import ir.mehdi.kelid.model.Property;
import ir.mehdi.kelid.ui.KelidActivity;


/**
 * Created by iman on 6/8/2016.
 */
public class Utils {
    public static final int FESTIVAL_TYPE = 1;
    public static final int UPGRADE_TYPE = 2;
    public static final int IMMEDIATE_TYPE = 3;

    public static int dpToPx(Context context, int dp) {
        Resources r = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
//    public static int getDPforPixel(int dp) {
//        Resources r = KelidApplication.applicationContext.getResources();
//        return (int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP,
//                dp,
//                r.getDisplayMetrics()
//        );
//    }

    public static int getScreenWidth(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        return width;
    }

    public static void expand(final View v) {
        float targetWidth = v.getWidth();
        v.setX(-targetWidth);
        v.setVisibility(View.VISIBLE);
        v.animate().translationX(0).setDuration(Const.AnimDuration);
    }

//    public static void collapse(final View v, Activity activity) {
//        float sw = app.shome.ir.shome.Utils.getScreenWidth(activity);
//        v.animate().translationX(-sw).setDuration(Const.AnimDuration);
//    }

    public static void zoom_in(final View v) {
        Animation aa = AnimationUtils.loadAnimation(KelidApplication.applicationContext, R.anim.zoom_in);
        aa.setDuration(Const.AnimDuration);
        v.startAnimation(aa);
    }

    public static void zoom_out(final View v) {
        Animation aa = AnimationUtils.loadAnimation(KelidApplication.applicationContext, R.anim.zoom_out);
        aa.setDuration(Const.AnimDuration);
        v.startAnimation(aa);
    }

    public static void change_color(final View v, int colorFrom, int colorTo) {
//        int colorFrom = getResources().getColor(R.color.transparent);
//        int colorTo = getResources().getColor(R.color.white);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(Const.AnimDuration);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                v.setBackgroundColor((int) animator.getAnimatedValue());

            }


        });

        colorAnimation.start();
    }

    public static void ChangeColorFilter(final ImageView v, int colorFrom, int colorTo){
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(Const.AnimDuration);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                v.setColorFilter((int) animator.getAnimatedValue());

            }

        });
        colorAnimation.start();
    }


    public static void showToast(Context context, String text, int gravity) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View layout = inflater.inflate(R.layout.custom_toast,(ViewGroup) findViewById(R.id.toast_layout_root));
//
//        TextView textView = (TextView) layout.findViewById(R.id.text);
//        textView.setText(text);
//
//        Toast toast = new Toast(context);
//        toast.setGravity(gravity, 0, 0);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.setView(layout);
//        toast.show();

    }

    public static Bitmap resize(Bitmap bitmap) {
        int newWidth = bitmap.getWidth();
        int newHeight = bitmap.getHeight();
        if (newWidth > 900) {
            newHeight = (int) (newHeight * (900f / newWidth));
            newWidth = 900;
        }
        if (newHeight > 900) {
            newWidth = (int) (newWidth * (900f / newHeight));
            newHeight = 900;
        }

        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);

    }

    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) {
        ExifInterface ei = null;
        try {
            ei = new ExifInterface(image_absolute_path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }


    public static boolean needToRefresh(ArrayList old, ArrayList new1) {

        if (old == null || new1 == null || old.size() < new1.size())
            return true;
        for (int i = 0; i < new1.size(); i++) {
            if (!old.get(i).equals(new1.get(i)))
                return true;
        }
        return false;

    }

    public static void showHelp(Activity activity, View view, String title, String content) {
        TapTargetView.showFor(activity,                 // `this` is an Activity
                TapTarget.forView(view, title, content)
                        // All options below are optional
                        .outerCircleColor(R.color.colorPrimaryDark)      // Specify a color for the outer circle
                        .targetCircleColor(R.color.progress)   // Specify a color for the target circle
                        .titleTextSize(20)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.red_btn_bg_color)      // Specify the color of the title text
                        .descriptionTextSize(15)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.white)  // Specify the color of the description text
                        .textColor(R.color.white)            // Specify a color for both the title and description text
//                        .textTypeFace(Typeface.SANS_SERIF)  // Specify a typeface for the text
                        .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(true)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(false)                   // Whether to tint the target view's color
                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                        .icon(ContextCompat.getDrawable(activity, R.drawable.ic_launcher))                   // Specify a custom drawable to draw as the target
                        .targetRadius(60),                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional
//                        doSomething();
                    }
                });
    }


//    private static void showPaymentDialog(Activity context, String titleLable, String textLable, final View.OnClickListener payment) {
//        final Dialog dialog = new Dialog(context);
//        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.payment_cost_dialog);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            dialog.findViewById(R.id.main_content).setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
//        }
//        Window window = dialog.getWindow();
//        DisplayMetrics displaymetrics = new DisplayMetrics();
//        context.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//        int width = (int) (displaymetrics.widthPixels * 0.9);
//        int height = (int) (displaymetrics.heightPixels * 0.4);
//        window.setLayout(width, height);
//
//        TextView title = (TextView) dialog.findViewById(R.id.title);
//        title.setText(titleLable);
//        TextView text = (TextView) dialog.findViewById(R.id.text);
//        text.setText(textLable);
//        text.setTypeface(FanoosApplication.BYEKAN_NORMAL);
//
//        dialog.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        dialog.findViewById(R.id.payment).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                payment.onClick(v);
//            }
//        });
//        dialog.show();
//
//
//    }
//
//    public static void startPaymentIntent(final Activity context, Intent intent, int requestCOde) {
//        PackageManager manager = context.getPackageManager();
//        List<ResolveInfo> infos = manager.queryIntentActivities(intent, 0);
//        if (infos.size() > 0) {
//            context.startActivityForResult(intent, requestCOde);
//        } else {
//            Toast.makeText(context, "", Toast.LENGTH_LONG);
//            intent.setClass(context, PaymentActivity.class);
//            context.startActivityForResult(intent, requestCOde);
//        }
//
//    }
//
//    public static void payment(final Activity context, final int requestCOde, int type, final long job_id) {
//
//        if (type == FESTIVAL_TYPE) {
//            showPaymentDialog(context, context.getString(R.string.festival), context.getString(R.string.festival_cost), new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    PersianCalendar now;
//                    now = new PersianCalendar();
//                    DatePickerDialog dpd = DatePickerDialog.newInstance(
//                            new DatePickerDialog.OnDateSetListener() {
//                                @Override
//                                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//                                    MonthAdapter.CalendarDay selectedDay = view.getSelectedDay();
//                                    CalendarTool calendarTool = new CalendarTool();
//                                    calendarTool.setIranianDate(year, monthOfYear, dayOfMonth);
//
//                                    YearMonthDate farsi = new YearMonthDate(year, monthOfYear, dayOfMonth);
//                                    YearMonthDate yearMonthDate = YearMonthDate.jalaliToGregorian(farsi);
//                                    String url = String.format(VolleyService.FESTIVAL_PAYMENT, Utils.asciiNumners("" + job_id), calendarTool.getGregorianDate());
//                                    Intent i = new Intent("android.intent.action.VIEW");
//                                    i.setData(Uri.parse(url));
//                                    startPaymentIntent(context, i, requestCOde);
//
//
//                                }
//                            },
//                            now.getPersianYear(),
//                            now.getPersianMonth(),
//                            now.getPersianDay()
//                    );
//
//                    dpd.setThemeDark(false);
//                    dpd.show(context.getFragmentManager(), "DATEPICKER");
//
//
//                }
//            });
//
//
//        } else if (type == UPGRADE_TYPE) {
//            showPaymentDialog(context, context.getString(R.string.upgrade), context.getString(R.string.upgrade_cost), new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String url = VolleyService.UPGRADE_PAYMENT + job_id;
//                    Intent i = new Intent("android.intent.action.VIEW");
//                    i.setData(Uri.parse(url));
//                    startPaymentIntent(context, i, requestCOde);
//
//                }
//            });
//
//        } else if (type == IMMEDIATE_TYPE) {
//            showPaymentDialog(context, context.getString(R.string.immediate), context.getString(R.string.immediate_cost), new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String url = VolleyService.IMMEDIATE_PAYMENT + job_id;
//                    Intent i = new Intent("android.intent.action.VIEW");
//                    i.setData(Uri.parse(url));
//                    startPaymentIntent(context, i, requestCOde);
//
//                }
//            });
//
//        }
//
//
//    }

    //    public static void setClipView(View view, boolean clip) {
//        if (view != null) {
//            ViewParent parent = view.getParent();
//            if(parent instanceof ViewGroup) {
//                ViewGroup viewGroup = (ViewGroup) view.getParent();
//                viewGroup.setClipChildren(clip);
//                setClipView(viewGroup, clip);
//            }
//        }
//    }
    public static Date getZeroTimeDate(Date fecha) {
        Date res = fecha;
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        res = calendar.getTime();
        return res;
    }

    public static class VisitedDate implements Comparable<VisitedDate> {
        public Date date;
        public long visited;
        public long jobid;
        public int dayno = 0;

        @Override
        public int compareTo(VisitedDate o) {
            return o.date.compareTo(date);
        }
    }

    public static String getName(String url) {
        int i = url.lastIndexOf("/");
        if (i != -1) {
            return url.substring(i + 1);
        }
        return url;

    }

    static String boundary = UUID.randomUUID().toString();
    static String twoHyphens = "--";
    static String crlf = "\r\n";
    static String charset = "UTF-8";

    public static void addFormField(PrintWriter writer, String name, String value) {
        writer.append("--" + boundary).append(crlf);
        writer.append("Content-Disposition: form-data; name=\"" + name + "\"")
                .append(crlf);
        writer.append("Content-Type: text/plain; charset=" + charset).append(
                crlf);
        writer.append(crlf);
        writer.append(value).append(crlf);
        writer.flush();
    }

    public static boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) KelidApplication.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);

                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else if (connectivityManager != null) {
            //noinspection deprecation
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();

            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;

                }


            }

        }
//        Toast.makeText(this, "adasd", Toast.LENGTH_SHORT).show();
        return false;
    }

    static public void createJson(Property property) {
        try {
//            String boundary = UUID.randomUUID().toString();
//            String twoHyphens = "--";
//            String crlf = "\r\n";
//            String charset = "UTF-8";
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            OutputStream outputStream = baos;
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);

//                DataOutputStream dos = new DataOutputStream(outputStream);

            // FIRST PART; A JSON object
            if (property.mobile != null) addFormField(writer, "mobile", property.mobile);
//                if (property.remote_id != 0) contentValues.put("remote_id", property.remote_id);
//                if (property.myjob != 0) contentValues.put("myjob", property.myjob);
            if (property.tel != null) addFormField(writer, "tel", property.tel);
            if (property.email != null) addFormField(writer, "email", property.email);
            if (property.name != null) addFormField(writer, "name", property.name);
            if (property.region != 0) addFormField(writer, "region_id", "" + property.region);
            if (property.address != null) addFormField(writer, "address", property.address);
//                if (property.dateString != null) contentValues.put("created", property.dateString);
            if (property.title != null) addFormField(writer, "title", property.title);
//            if (property.qr_code != null) addFormField(writer, "qr_code", property.qr_code);
            if (property.city != 0) addFormField(writer, "city", "" + property.city);
            if (property.desc != null) addFormField(writer, "description", property.desc);

            if (property.nodeid != 0) {
                Node node = DBAdapter.getInstance().allNodes.get(property.nodeid);
                addFormField(writer, "level3", "" + property.nodeid);
                addFormField(writer, "level2", "" + node.parent.id);
                addFormField(writer, "level1", "" + node.parent.parent.id);
            }

//                if (property.businesscardpathlocal != null) contentValues.put("businesscardpathlocal", property.businesscardpathlocal);

            if (property.city != 0 && property.city != -1)
                addFormField(writer, "province", "" + DBAdapter.getInstance().indexCities.get(property.city).provincecode);

//                dos.writeBytes(twoHyphens + boundary);
//                dos.writeBytes(crlf);
//                dos.writeBytes("Content-Type: application/json");
//                dos.writeBytes(crlf);
//                dos.writeBytes("Content-Disposition: form-data; name=\"" + jsonPartKey + "\"");
//                dos.writeBytes(crlf);
//                dos.writeBytes(crlf);
//                dos.writeBytes(jsonParam.toString());
//                dos.writeBytes(crlf);
//                PrintWriter writer = null;
            JSONArray delete = new JSONArray();

            int i = 0;
            for (Property.Image filePath : property.images) {


                i++;
                File uploadFile = new File(filePath.localname);
                String fileName = uploadFile.getName();
//                    writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"),
//                            true);
                writer.append("--" + boundary).append(crlf);
                writer.append("Content-Disposition: form-data; name=\"" + "file[" + i
                        + "]\"; filename=\"" + fileName + "\"")
                        .append(crlf);
                delete.put(fileName);
                writer.append(
                        "Content-Type: "
                                + URLConnection.guessContentTypeFromName(fileName))
                        .append(crlf);
                writer.append("Content-Transfer-Encoding: binary").append(crlf);
                writer.append(crlf);
                writer.flush();
//                FileInputStream inputStream = new FileInputStream(uploadFile);
//                byte[] buffer = new byte[4096];
//                int bytesRead = -1;
//                while ((bytesRead = inputStream.read(buffer)) != -1) {
//                    outputStream.write(buffer, 0, bytesRead);
//                }
//                inputStream.close();
                writer.append(crlf);
                writer.append(twoHyphens + boundary + crlf);
                writer.flush();

            }
            addFormField(writer, "deleted", delete.toString());

            outputStream.flush();

            String s = baos.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static public HashMap<Long, Property> getUserJobFromJson(JSONArray array, boolean forceadd, boolean saveImages) {

        try {
            HashMap<Long, Property> userJob = new HashMap<>();
            for (int i = 0; i < array.length(); i++) {
                Property userJob1 = new Property();
                JSONObject object = array.getJSONObject(i);
                userJob1.remote_id = object.getInt("id");
                Property userJob2 = MySqliteOpenHelper.getInstance().myPropertysremote.get(userJob1.remote_id);
                if (userJob2 != null && !forceadd) {
                    int status = object.getInt("status");
                    if (userJob2.status != status) {
                        userJob2.status = status;
                        MySqliteOpenHelper.getInstance().insertORUpdateProperty(userJob2);

                    }
                    continue;
                }
                if (userJob.containsKey(userJob1.remote_id)) {
                    userJob.get(userJob1.remote_id).addImageFromJsonObject(object, saveImages);
                } else {
                    userJob1.fillFromJsonObject(object, saveImages);
                    userJob.put(userJob1.remote_id, userJob1);
                }
            }
//            Collection<UserJob> values = userJob.values();
            return userJob;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static public HashMap<Long, Property> getMyJobFromJson(JSONObject a, boolean forceadd, boolean saveImages) {
        HashMap<Long, Property> userJob = new HashMap<>();
        if (a == null) {
            return null;
        }

        try {
//            JSONObject a = new JSONObject(data);
            JSONArray array = a.getJSONArray("result");
            JSONArray visited = a.getJSONArray("visited5");
            JSONArray payment = a.getJSONArray("infoPayment");
            JSONArray totalvisited = a.getJSONArray("visitedTotal");


            HashMap<Long, Vector<VisitedDate>> userjobVisisted = new HashMap<>();
            HashMap<Long, Vector<Property.Payment>> userjobPayment = new HashMap<>();
            HashMap<Long, Long> userjobTotal = new HashMap<>();
            for (int i = 0; i < totalvisited.length(); i++) {
                JSONObject jsonObject = totalvisited.getJSONObject(i);
                long id_userjob = jsonObject.getLong("id_userjob");
                long total_visit = jsonObject.getLong("total_visit");
                userjobTotal.put(id_userjob, total_visit);
            }


            for (int i = 0; i < visited.length(); i++) {
                VisitedDate v = new VisitedDate();
                JSONObject jsonObject = visited.getJSONObject(i);
                v.jobid = jsonObject.getLong("id_userjob");
                v.visited = jsonObject.getLong("view_count");
                String created_at = jsonObject.getString("created_at");
                String format = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(format);

                v.date = sdf.parse(created_at);
                Vector<VisitedDate> visitedDates1 = userjobVisisted.get(v.jobid);
                if (visitedDates1 != null)
                    visitedDates1.add(v);
                else {
                    Vector<VisitedDate> aaa = new Vector<>();
                    aaa.add(v);
                    userjobVisisted.put(v.jobid, aaa);
                }


            }
            for (int i = 0; i < payment.length(); i++) {
                Property.Payment payment1 = new Property.Payment();
                JSONObject jsonObject = payment.getJSONObject(i);
                String updated_at = jsonObject.getString("updated_at");
                String festival_date = jsonObject.getString("updated_at");


                String format = "yyyy-MM-dd HH:mm:ss";
                String format2 = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                SimpleDateFormat sdf2 = new SimpleDateFormat(format2);
                payment1.payDate = sdf.parse(updated_at);
                payment1.festivalDate = sdf2.parse(festival_date);
                String title_en = jsonObject.getString("title_en");
                payment1.type = title_en.equals("immediate") ? IMMEDIATE_TYPE : title_en.equals("upgrade") ? UPGRADE_TYPE : FESTIVAL_TYPE;
                long userjob_id = jsonObject.getLong("userjob_id");
                Vector<Property.Payment> visitedDates1 = userjobPayment.get(userjob_id);
                if (visitedDates1 != null)
                    visitedDates1.add(payment1);
                else {
                    Vector<Property.Payment> aaa = new Vector<>();
                    aaa.add(payment1);
                    userjobPayment.put(userjob_id, aaa);
                }


            }


            for (int i = 0; i < array.length(); i++) {
                Property userJob1 = new Property();
                JSONObject object = array.getJSONObject(i);
                userJob1.remote_id = object.getInt("id");

                Long aLong = userjobTotal.get(userJob1.remote_id);
                if (aLong != null) {
                    userJob1.totalVisited = aLong;
                }
                Vector<VisitedDate> visitedDates1 = userjobVisisted.get(userJob1.remote_id);
                Vector<Property.Payment> payments2 = userjobPayment.get(userJob1.remote_id);
                if (visitedDates1 != null) {
                    userJob1.setPieNum(visitedDates1);
                }

                Property userJob2 = MySqliteOpenHelper.getInstance().myPropertysremote.get(userJob1.remote_id);
                if (userJob2 != null && !forceadd) {
                    int status = object.getInt("status");
                    if (userJob2.status != status) {
                        userJob2.status = status;
//                        userJob2.setServerData();
                    }
                    Long aLong1 = userjobTotal.get(userJob2.remote_id);
                    if (aLong1 != null) {
                        userJob2.totalVisited = aLong1;
                    }
                    Vector<VisitedDate> visitedDates2 = userjobVisisted.get(userJob2.remote_id);
                    if (visitedDates2 != null) {
                        userJob2.setPieNum(visitedDates2);
                    }

                    MySqliteOpenHelper.getInstance().insertORUpdateProperty(userJob2);
                    continue;
                }
                if (userJob.containsKey(userJob1.remote_id)) {
                    userJob.get(userJob1.remote_id).addImageFromJsonObject(object, saveImages);
                } else {
                    userJob1.fillFromJsonObject(object, saveImages);
                    userJob1.setServerData();
                    userJob.put(userJob1.remote_id, userJob1);
                }
            }


//            Collection<UserJob> values = userJob.values();
            return userJob;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userJob;
    }

    public static String asciiNumners(String arabicNumber) {
        StringBuilder builder = new StringBuilder();
        String arabicDigits = "";
        String str = arabicNumber;
        char[] arabicChars = {'\u0660', '\u0661', '\u0662', '\u0663', '\u0664', '\u0665', '\u0666', '\u0667', '\u0668', '\u0669'};
        char[] farsiChars = {'\u06f0', '\u06f1', '\u06f2', '\u06f3', '\u06f4', '\u06f5', '\u06f6', '\u06f7', '\u06f8', '\u06f9'};

        for (int i = 0; i < str.length(); i++) {
            boolean isdigit = false;
            for (int j = 0; j < arabicChars.length; j++) {
                if (arabicChars[j] == str.charAt(i)) {
                    isdigit = true;
                    builder.append((char) (j + 48));
                    break;
                }
                if (farsiChars[j] == str.charAt(i)) {
                    isdigit = true;
                    builder.append((char) (j + 48));
                    break;
                }
            }
            if (!isdigit) {
                builder.append(str.charAt(i));
            }


        }

        return builder.toString();


    }


    public static int containerHeight(KelidActivity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Display mDisplay = activity.getWindowManager().getDefaultDisplay();
        Point a = new Point();
        mDisplay.getSize(a);

        int height = a.y;
        TypedValue tv = new TypedValue();
        float actionHeaderHeight = -1;
        if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionHeaderHeight = TypedValue.complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics());
        }
        float adverHeight = activity.getResources().getDimension(R.dimen.advers_height_one_grid) / 2;//TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,  activity.getResources().getDimension(R.dimen.advers_height), activity.getResources().getDisplayMetrics());
//        float filterHeight = activity.getResources().getDimension(R.dimen.filter_height);//TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,  activity.getResources().getDimension(R.dimen.advers_height), activity.getResources().getDisplayMetrics());
        float nodeHeaderHeight = activity.getResources().getDimension(R.dimen.node_hear_height);//TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, activity.getResources().getDimension(R.dimen.node_hear_height), activity.getResources().getDisplayMetrics());

//        height = (int) (height - adverHeight - nodeHeaderHeight - filterHeight - 2 * actionHeaderHeight) / 5;


        return (int) (height);
    }




    public final static String unescape_perl_string(String oldstr) {

    /*
     * In contrast to fixing Java's broken regex charclasses,
     * this one need be no bigger, as unescaping shrinks the string
     * here, where in the other one, it grows it.
     */

        StringBuffer newstr = new StringBuffer(oldstr.length());

        boolean saw_backslash = false;

        for (int i = 0; i < oldstr.length(); i++) {
            int cp = oldstr.codePointAt(i);
            if (oldstr.codePointAt(i) > Character.MAX_VALUE) {
                i++; /****WE HATES UTF-16! WE HATES IT FOREVERSES!!!****/
            }

            if (!saw_backslash) {
                if (cp == '\\') {
                    saw_backslash = true;
                } else {
                    newstr.append(Character.toChars(cp));
                }
                continue; /* switch */
            }

            if (cp == '\\') {
                saw_backslash = false;
                newstr.append('\\');
                newstr.append('\\');
                continue; /* switch */
            }

            switch (cp) {

                case 'r':
                    newstr.append('\r');
                    break; /* switch */

                case 'n':
                    newstr.append('\n');
                    break; /* switch */

                case 'f':
                    newstr.append('\f');
                    break; /* switch */

            /* PASS a \b THROUGH!! */
                case 'b':
                    newstr.append("\\b");
                    break; /* switch */

                case 't':
                    newstr.append('\t');
                    break; /* switch */

                case 'a':
                    newstr.append('\007');
                    break; /* switch */

                case 'e':
                    newstr.append('\033');
                    break; /* switch */

            /*
             * A "control" character is what you get when you xor its
             * codepoint with '@'==64.  This only makes sense for ASCII,
             * and may not yield a "control" character after all.
             *
             * Strange but true: "\c{" is ";", "\c}" is "=", etc.
             */
                case 'c': {
                    if (++i == oldstr.length()) {
                        die("trailing \\c");
                    }
                    cp = oldstr.codePointAt(i);
                /*
                 * don't need to grok surrogates, as next line blows them up
                 */
                    if (cp > 0x7f) {
                        die("expected ASCII after \\c");
                    }
                    newstr.append(Character.toChars(cp ^ 64));
                    break; /* switch */
                }

                case '8':
                case '9':
                    die("illegal octal digit");
                      /* NOTREACHED */

    /*
     * may be 0 to 2 octal digits following this one
     * so back up one for fallthrough to next case;
     * unread this digit and fall through to next case.
     */
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                    --i;
                      /* FALLTHROUGH */

            /*
             * Can have 0, 1, or 2 octal digits following a 0
             * this permits larger values than octal 377, up to
             * octal 777.
             */
                case '0': {
                    if (i + 1 == oldstr.length()) {
                    /* found \0 at end of string */
                        newstr.append(Character.toChars(0));
                        break; /* switch */
                    }
                    i++;
                    int digits = 0;
                    int j;
                    for (j = 0; j <= 2; j++) {
                        if (i + j == oldstr.length()) {
                            break; /* for */
                        }
                    /* safe because will unread surrogate */
                        int ch = oldstr.charAt(i + j);
                        if (ch < '0' || ch > '7') {
                            break; /* for */
                        }
                        digits++;
                    }
                    if (digits == 0) {
                        --i;
                        newstr.append('\0');
                        break; /* switch */
                    }
                    int value = 0;
                    try {
                        value = Integer.parseInt(
                                oldstr.substring(i, i + digits), 8);
                    } catch (NumberFormatException nfe) {
                        die("invalid octal value for \\0 escape");
                    }
                    newstr.append(Character.toChars(value));
                    i += digits - 1;
                    break; /* switch */
                } /* end case '0' */

                case 'x': {
                    if (i + 2 > oldstr.length()) {
                        die("string too short for \\x escape");
                    }
                    i++;
                    boolean saw_brace = false;
                    if (oldstr.charAt(i) == '{') {
                        /* ^^^^^^ ok to ignore surrogates here */
                        i++;
                        saw_brace = true;
                    }
                    int j;
                    for (j = 0; j < 8; j++) {

                        if (!saw_brace && j == 2) {
                            break;  /* for */
                        }

                    /*
                     * ASCII test also catches surrogates
                     */
                        int ch = oldstr.charAt(i + j);
                        if (ch > 127) {
                            die("illegal non-ASCII hex digit in \\x escape");
                        }

                        if (saw_brace && ch == '}') {
                            break; /* for */
                        }

                        if (!((ch >= '0' && ch <= '9')
                                ||
                                (ch >= 'a' && ch <= 'f')
                                ||
                                (ch >= 'A' && ch <= 'F')
                        )
                                ) {
                            die(String.format(
                                    "illegal hex digit #%d '%c' in \\x", ch, ch));
                        }

                    }
                    if (j == 0) {
                        die("empty braces in \\x{} escape");
                    }
                    int value = 0;
                    try {
                        value = Integer.parseInt(oldstr.substring(i, i + j), 16);
                    } catch (NumberFormatException nfe) {
                        die("invalid hex value for \\x escape");
                    }
                    newstr.append(Character.toChars(value));
                    if (saw_brace) {
                        j++;
                    }
                    i += j - 1;
                    break; /* switch */
                }

                case 'u': {
                    if (i + 4 > oldstr.length()) {
                        die("string too short for \\u escape");
                    }
                    i++;
                    int j;
                    for (j = 0; j < 4; j++) {
                    /* this also handles the surrogate issue */
                        if (oldstr.charAt(i + j) > 127) {
                            die("illegal non-ASCII hex digit in \\u escape");
                        }
                    }
                    int value = 0;
                    try {
                        value = Integer.parseInt(oldstr.substring(i, i + j), 16);
                    } catch (NumberFormatException nfe) {
                        die("invalid hex value for \\u escape");
                    }
                    newstr.append(Character.toChars(value));
                    i += j - 1;
                    break; /* switch */
                }

                case 'U': {
                    if (i + 8 > oldstr.length()) {
                        die("string too short for \\U escape");
                    }
                    i++;
                    int j;
                    for (j = 0; j < 8; j++) {
                    /* this also handles the surrogate issue */
                        if (oldstr.charAt(i + j) > 127) {
                            die("illegal non-ASCII hex digit in \\U escape");
                        }
                    }
                    int value = 0;
                    try {
                        value = Integer.parseInt(oldstr.substring(i, i + j), 16);
                    } catch (NumberFormatException nfe) {
                        die("invalid hex value for \\U escape");
                    }
                    newstr.append(Character.toChars(value));
                    i += j - 1;
                    break; /* switch */
                }

                default:
                    newstr.append('\\');
                    newstr.append(Character.toChars(cp));
           /*
            * say(String.format(
            *       "DEFAULT unrecognized escape %c passed through",
            *       cp));
            */
                    break; /* switch */

            }
            saw_backslash = false;
        }

    /* weird to leave one at the end */
        if (saw_backslash) {
            newstr.append('\\');
        }

        return newstr.toString();
    }

    private static final void die(String foa) {
        throw new IllegalArgumentException(foa);
    }


}
