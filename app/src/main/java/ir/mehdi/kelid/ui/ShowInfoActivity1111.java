//package ir.mehdi.kelid.ui;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.graphics.Point;
//import android.graphics.Typeface;
//import android.graphics.drawable.Drawable;
//import android.graphics.drawable.TransitionDrawable;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.design.widget.AppBarLayout;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.DisplayMetrics;
//import android.view.Display;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.Button;
//import android.widget.CompoundButton;
//import android.widget.EditText;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.RadioButton;
//import android.widget.RelativeLayout;
//import android.widget.TableLayout;
//import android.widget.TableRow;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.viewpagerindicator.CirclePageIndicator;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.util.Calendar;
//import java.util.Collections;
//import java.util.Date;
//import java.util.HashMap;
//
//import cn.pedant.SweetAlert.SweetAlertDialog;
//import ir.mehdi.kelid.Constant;
//import ir.mehdi.kelid.R;
//import ir.mehdi.kelid.model.Property;
//import ir.mehdi.kelid.service.ServiceDelegate;
//import ir.mehdi.kelid.utils.Utils;
//
///**
// * Created by Iman on 3/4/2017.
// */
//public class ShowInfoActivity extends KelidActivity implements ServiceDelegate, Constant, View.OnClickListener {
//    View telSpacer, mobileSpacer, telegramSpacer;
//    Drawable defaultDrawble;
//    int defaultColor;
//    LinearLayout myadversActionLay, moreLayout;
//    TableLayout moreTable;
//    Animation moreAnimation, hideMoreAnimation;
//    TextView status, nodePath, myMore, telegramCHannel;
//    boolean moreExapnded = false;
//    boolean edit = false;
//
//
//    String curretnRequestCode;
//    Button immediate, upgrade, fesival;
//    Property property;
//    View mobile_lay, tel_lay, region_ley, email_ley, telegram_layout, visit_layout, adress_lay, feature_lay, time_lay, fanoos_lay, detail, nameLayout;
//    View day_1_free, day_1_color, day_2_free, day_2_color, day_3_free, day_3_color, day_4_free, day_4_color;
//    TextView day_1_cnt, day_2_cnt, day_3_cnt, day_4_cnt;
//    TextView mobile, tel, region, email, advers, title, desc,
//            telegram, totalview, address, time, features,
//            fanoos_code, nameLayble, name, fanoos_code_lable,
//            status_lable, region_lable, address_lable, time_lable,
//            features_lable, mobile_lable, tel_lable, telegram_lable, report_lable, fata_lable, day_2, day_3, day_4;
//    CallingListener callingListener = new CallingListener();
//    SMSListener smsListener = new SMSListener();
//
//    CustomPagerAdapter mCustomPagerAdapter;
//
//
//    JobReportDialog jobReportDialog;
//    ProgressBar progressBar;
//    ImageView mobilecall, tellcall, mobilesms, telegramaction;
//    CirclePageIndicator circlePageIndicator;
//    ViewPager mViewPager;
//
//    LinearLayout jobReport, warining;
//    UserJobImageViewListner listner;
//
//    SweetAlertDialog progressDialog;
//    ImageView okMenu, bookMenu, shareMenu, backMenu, editMenu;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (moreAnimation == null) {
//            moreAnimation = AnimationUtils.loadAnimation(this, R.anim.create_node_select_anim);
//            hideMoreAnimation = AnimationUtils.loadAnimation(this, R.anim.hide_more_animation);
//            hideMoreAnimation.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    moreLayout.setVisibility(View.GONE);
//
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
//        }
//        UserJob job = null;
//        Intent intent = getIntent();
//        boolean showing = false;
//        if (intent != null) {
//            Bundle extras = intent.getExtras();
//            if (extras != null) {
//                job = (Property) extras.get("job");
//
//                showing = true;
//            }
//        }
//        if (!showing || job == null) {
//            Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
//            finish();
//        }
//        defaultDrawble = ContextCompat.getDrawable(this, R.drawable.user_job_dialog_bg);
//        defaultColor = ContextCompat.getColor(this, R.color.colorPrimary);
//        progressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE).setTitleText("");
//
//        progressDialog.setCancelable(false);
//
//        setContentView(R.layout.user_job_dialog);
//        okMenu = (ImageView) findViewById(R.id.done);
//        bookMenu = (ImageView) findViewById(R.id.bookmark);
//        shareMenu = (ImageView) findViewById(R.id.share);
//        editMenu = (ImageView) findViewById(R.id.edit);
//        backMenu = (ImageView) findViewById(R.id.back);
//
//
//        telSpacer = findViewById(R.id.telspacer);
//        mobileSpacer = findViewById(R.id.mobilespacer);
//        telegramSpacer = findViewById(R.id.telegramspacer);
//        okMenu.setOnClickListener(this);
//        bookMenu.setOnClickListener(this);
//        shareMenu.setOnClickListener(this);
//        editMenu.setOnClickListener(this);
//        backMenu.setOnClickListener(this);
//
//
//        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.viewpagerindicator);
//
//        final Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.pie_anim);
//
//        final Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.pie_anim);
//
//        final Animation animation3 = AnimationUtils.loadAnimation(this, R.anim.pie_anim);
//
//        final Animation animation4 = AnimationUtils.loadAnimation(this, R.anim.pie_anim);
//
//        animation2.setStartOffset(50);
//        animation3.setStartOffset(75);
//        animation4.setStartOffset(100);
//
//
//        listner = new UserJobImageViewListner();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (property == null || property.myjob != 1)
//                    return;
//                animationView = 1;
//                day_1_color.startAnimation(animation1);
//                day_2_color.startAnimation(animation2);
//                day_3_color.startAnimation(animation3);
//                day_4_color.startAnimation(animation4);
//
//            }
//        }, 50);
//
//
//        jobReportDialog = new JobReportDialog(this);
//        DisplayMetrics displaymetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//        int width = (int) (displaymetrics.widthPixels * 0.9);
//        int height = (int) (displaymetrics.heightPixels * 0.7);
//        Window window = jobReportDialog.getWindow();
//        window.setLayout(width, height);
//
//
//        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            findViewById(R.id.main_content).setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
//        }
//
//
//        mobilesms = (ImageView) findViewById(R.id.mobilesms);
//        telegramaction = (ImageView) findViewById(R.id.telegramcall);
//        mobilecall = (ImageView) findViewById(R.id.mobilecall);
//        tellcall = (ImageView) findViewById(R.id.telcall);
//        mViewPager = (ViewPager) findViewById(R.id.pager);
//        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mViewPager.getLayoutParams();
//        layoutParams.height = (int) (displaymetrics.heightPixels * 0.3);
//        mViewPager.setLayoutParams(layoutParams);
//
//
//        detail = findViewById(R.id.detail);
//
//
//        jobReport = (LinearLayout) findViewById(R.id.job_report);
//        warining = (LinearLayout) findViewById(R.id.warning);
//        myadversActionLay = (LinearLayout) findViewById(R.id.myadverlay);
//        jobReport.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!Utils.isNetworkConnected()) {
//
//                    Toast.makeText(ShowInfoActivity.this, getString(R.string.intetnet_not_connected), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                jobReportDialog.recreate();
//                jobReportDialog.show();
//            }
//        });
//
//        telegramaction.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                telegramOpen(((String) telegramaction.getTag()));
//            }
//        });
//
//
//        advers = (TextView) findViewById(R.id.advers);
////        date = (TextView) findViewById(R.id.date);
//        desc = (TextView) findViewById(R.id.desc);
//        telegram = (TextView) findViewById(R.id.telegram);
//        totalview = (TextView) findViewById(R.id.totalview);
//        tel = (TextView) findViewById(R.id.tel);
//        region = (TextView) findViewById(R.id.region);
//        address = (TextView) findViewById(R.id.address);
//        email = (TextView) findViewById(R.id.email);
////        node = (TextView) findViewById(R.id.node);
//        title = (TextView) findViewById(R.id.title);
//        mobile = (TextView) findViewById(R.id.mobile);
//        fanoos_code_lable = (TextView) findViewById(R.id.fanoos_code_lable);
//        status = (TextView) findViewById(R.id.status);
//        status_lable = (TextView) findViewById(R.id.status_lable);
//        region_lable = (TextView) findViewById(R.id.region_lable);
//        address_lable = (TextView) findViewById(R.id.address_lable);
//        time_lable = (TextView) findViewById(R.id.time_lable);
//        features_lable = (TextView) findViewById(R.id.features_lable);
//        mobile_lable = (TextView) findViewById(R.id.mobile_lable);
//        tel_lable = (TextView) findViewById(R.id.tel_lable);
//        telegram_lable = (TextView) findViewById(R.id.telegram_lable);
//        report_lable = (TextView) findViewById(R.id.report_lable);
//        fata_lable = (TextView) findViewById(R.id.fata_lable);
//        day_2 = (TextView) findViewById(R.id.day_2);
//        day_3 = (TextView) findViewById(R.id.day_3);
//        day_4 = (TextView) findViewById(R.id.day_4);
//
//
//        nodePath = (TextView) findViewById(R.id.nodepath);
//        telegramCHannel = (TextView) findViewById(R.id.telegra_channel);
//        myMore = (TextView) findViewById(R.id.myadvers_more);
//        moreLayout = (LinearLayout) findViewById(R.id.more_lay);
//        moreLayout.setVisibility(View.GONE);
//        moreTable = (TableLayout) findViewById(R.id.more_table);
//
//        upgrade = (Button) findViewById(R.id.upgrade);
//        immediate = (Button) findViewById(R.id.immediate);
//        fesival = (Button) findViewById(R.id.festival);
//
//
//        upgrade.setOnClickListener(this);
//        immediate.setOnClickListener(this);
//        fesival.setOnClickListener(this);
//
//
//        fanoos_code = (TextView) findViewById(R.id.fanoos_code);
//        name = (TextView) findViewById(R.id.name);
//        nameLayout = findViewById(R.id.namelayout);
//        nameLayble = (TextView) findViewById(R.id.name_lable);
//        time = (TextView) findViewById(R.id.time);
//        features = (TextView) findViewById(R.id.features);
//        day_1_cnt = (TextView) findViewById(R.id.day_1_cnt);
//        day_2_cnt = (TextView) findViewById(R.id.day_2_cnt);
//        day_3_cnt = (TextView) findViewById(R.id.day_3_cnt);
//        day_4_cnt = (TextView) findViewById(R.id.day_4_cnt);
//        advers.setTypeface(FanoosApplication.BYEKAN_NORMAL);
////        date.setTypeface(FanoosApplication.BYEKAN_NORMAL);
//        desc.setTypeface(FanoosApplication.BYEKAN_NORMAL);
//        fanoos_code_lable.setTypeface(FanoosApplication.BYEKAN_NORMAL);
//        telegram.setTypeface(FanoosApplication.BYEKAN_NORMAL);
//        totalview.setTypeface(FanoosApplication.BYEKAN_NORMAL);
//        tel.setTypeface(FanoosApplication.DIGIT_NORMAL);
//        region_lable.setTypeface(FanoosApplication.DIGIT_NORMAL);
//        time_lable.setTypeface(FanoosApplication.DIGIT_NORMAL);
//        features_lable.setTypeface(FanoosApplication.DIGIT_NORMAL);
//        address_lable.setTypeface(FanoosApplication.DIGIT_NORMAL);
//        region.setTypeface(FanoosApplication.BYEKAN_NORMAL);
//        name.setTypeface(FanoosApplication.BYEKAN_NORMAL);
//        status_lable.setTypeface(FanoosApplication.BYEKAN_NORMAL);
//        nameLayble.setTypeface(FanoosApplication.BYEKAN_NORMAL);
//        address.setTypeface(FanoosApplication.BYEKAN_NORMAL);
//        email.setTypeface(FanoosApplication.BYEKAN_NORMAL);
////        node.setTypeface(FanoosApplication.BYEKAN_NORMAL);
//        title.setTypeface(FanoosApplication.BYEKAN_NORMAL);
//        mobile.setTypeface(FanoosApplication.DIGIT_NORMAL);
//        status.setTypeface(FanoosApplication.DIGIT_NORMAL);
//        nodePath.setTypeface(FanoosApplication.DIGIT_NORMAL);
//        telegramCHannel.setTypeface(FanoosApplication.DIGIT_NORMAL);
//        myMore.setTypeface(FanoosApplication.DIGIT_NORMAL);
//        fanoos_code.setTypeface(FanoosApplication.DIGIT_NORMAL);
//        day_1_cnt.setTypeface(FanoosApplication.DIGIT_NORMAL);
//        day_2_cnt.setTypeface(FanoosApplication.DIGIT_NORMAL);
//        day_3_cnt.setTypeface(FanoosApplication.DIGIT_NORMAL);
//        day_4_cnt.setTypeface(FanoosApplication.DIGIT_NORMAL);
//        mobile_lable.setTypeface(FanoosApplication.DIGIT_NORMAL);
//        tel_lable.setTypeface(FanoosApplication.DIGIT_NORMAL);
//        telegram_lable.setTypeface(FanoosApplication.DIGIT_NORMAL);
//        report_lable.setTypeface(FanoosApplication.DIGIT_NORMAL);
//        fata_lable.setTypeface(FanoosApplication.DIGIT_NORMAL);
//        day_2.setTypeface(FanoosApplication.DIGIT_NORMAL);
//        day_3.setTypeface(FanoosApplication.DIGIT_NORMAL);
//        day_4.setTypeface(FanoosApplication.DIGIT_NORMAL);
//
//
//        mobile_lay = findViewById(R.id.mobile_lay);
//        tel_lay = findViewById(R.id.tel_lay);
//        feature_lay = findViewById(R.id.feature_lay);
//        time_lay = findViewById(R.id.time_lay);
//        fanoos_lay = findViewById(R.id.fanoos_lay);
//        telegram_layout = findViewById(R.id.telegram_lay);
//        visit_layout = findViewById(R.id.visit_layout);
//        region_ley = findViewById(R.id.region_lay);
//        adress_lay = findViewById(R.id.address_lay);
//        email_ley = findViewById(R.id.email_lay);
////        fanoos_lay.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                showAddNewTooltips();
////            }
////        });
//
//        mobilecall.setOnClickListener(callingListener);
//        mobilesms.setOnClickListener(smsListener);
//        tellcall.setOnClickListener(callingListener);
//
//        tel.setOnClickListener(callingListener);
//
//
//        day_1_free = findViewById(R.id.day_1_free);
//        day_2_free = findViewById(R.id.day_2_free);
//        day_3_free = findViewById(R.id.day_3_free);
//        day_4_free = findViewById(R.id.day_4_free);
//        day_1_color = findViewById(R.id.day_1_color);
//        day_2_color = findViewById(R.id.day_2_color);
//        day_3_color = findViewById(R.id.day_3_color);
//        day_4_color = findViewById(R.id.day_4_color);
//        mTitleContainer = (RelativeLayout) findViewById(R.id.header);
//        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar);
//        setUserJob(job, true);
//
//
//    }
//
//    private void initPayTable() {
//        moreTable.removeAllViews();
//        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0, Utils.getDPforPixel(40));
//        layoutParams.weight = 1.0f;
//        layoutParams.bottomMargin = Utils.getDPforPixel(2);
//        layoutParams.topMargin = Utils.getDPforPixel(2);
//        layoutParams.rightMargin = Utils.getDPforPixel(2);
//        layoutParams.leftMargin = Utils.getDPforPixel(2);
//        if (property.payments == null)
//            return;
//        TableRow tableRow = new TableRow(this);
//        tableRow.setBackgroundColor(Color.WHITE);
//        tableRow.setGravity(Gravity.RIGHT);
//
//        TextView festival1 = new TextView(this);
//        festival1.setMaxLines(1);
//        festival1.setLayoutParams(layoutParams);
//        festival1.setBackgroundColor(Color.WHITE);
//        festival1.setTypeface(FanoosApplication.BYEKAN_NORMAL, Typeface.BOLD);
//        festival1.setText(R.string.festival_date);
//        festival1.setGravity(Gravity.CENTER);
//
//        tableRow.addView(festival1);
//
//
//        TextView pay1 = new TextView(this);
//        pay1.setMaxLines(1);
//        pay1.setBackgroundColor(Color.WHITE);
//        pay1.setGravity(Gravity.CENTER);
//        pay1.setLayoutParams(layoutParams);
//        pay1.setTypeface(FanoosApplication.BYEKAN_NORMAL, Typeface.BOLD);
//        pay1.setText(R.string.payment_date);
//        tableRow.addView(pay1);
//
//        TextView type1 = new TextView(this);
//        type1.setMaxLines(1);
//        type1.setBackgroundColor(Color.WHITE);
//        type1.setGravity(Gravity.CENTER);
//        type1.setLayoutParams(layoutParams);
//        type1.setTypeface(FanoosApplication.BYEKAN_NORMAL, Typeface.BOLD);
//        type1.setText(R.string.type);
//        tableRow.addView(type1);
//        moreTable.addView(tableRow, -1, -2);
//        CalendarTool calendarTool = new CalendarTool();
//        Calendar c1 = Calendar.getInstance();
//
//
//        for (int i = 0; i < property.payments.size(); i++) {
//            UserJob.Payment payment = property.payments.get(i);
//            tableRow = new TableRow(this);
//
//            TextView festival = new TextView(this);
//            festival.setMaxLines(1);
//            festival.setBackgroundColor(Color.WHITE);
//            festival.setTextColor(Color.BLACK);
//
//            tableRow.setGravity(Gravity.RIGHT);
//            festival.setLayoutParams(layoutParams);
//            festival.setGravity(Gravity.CENTER);
//            festival.setTypeface(FanoosApplication.BYEKAN_NORMAL);
//            if (payment.type == Utils.FESTIVAL_TYPE) {
//
//                c1.setTime(payment.festivalDate);
//                calendarTool.setGregorianDate(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH));
//                festival.setText(calendarTool.getIranianDate());
//            } else
//                festival.setText("---");
//            tableRow.addView(festival);
//            TextView pay = new TextView(this);
//            pay.setMaxLines(1);
//            pay.setTextColor(Color.BLACK);
//            pay.setBackgroundColor(Color.WHITE);
//            pay.setGravity(Gravity.CENTER);
//            pay.setLayoutParams(layoutParams);
//            pay.setTypeface(FanoosApplication.BYEKAN_NORMAL);
//            c1.setTime(payment.payDate);
//            calendarTool.setGregorianDate(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH));
//            pay.setText(calendarTool.getIranianDate());
//            tableRow.addView(pay);
//
//            TextView type = new TextView(this);
//            type.setMaxLines(1);
//            type.setTextColor(Color.BLACK);
//            type.setGravity(Gravity.CENTER);
//            type.setBackgroundColor(Color.WHITE);
//
//            type.setLayoutParams(layoutParams);
//            type.setTypeface(FanoosApplication.BYEKAN_NORMAL);
//            type.setText((payment.type == Utils.IMMEDIATE_TYPE) ? R.string.immediate : (payment.type == Utils.FESTIVAL_TYPE) ? R.string.festival : R.string.upgrade);
//            tableRow.addView(type);
//            moreTable.addView(tableRow, -1, -2);
//
//        }
//
//    }
//
//    private RelativeLayout mTitleContainer;
//    private TextView mTitle;
//    private AppBarLayout mAppBarLayout;
//
//
//    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
//    private static final int ALPHA_ANIMATIONS_DURATION = 200;
//
//
//    private boolean mIsTheTitleContainerVisible = true;
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (property.myjob == 1 && edit) {
//            property = MySqliteOpenHelper.getInstance().myJobs.get(property.local_id);
//            setUserJob(property, true);
//        }
//        edit = false;
//    }
//
//    private void bindActivity() {
//
//
//    }
//
//    public static void startAlphaAnimation(View v, long duration, int visibility) {
//        Drawable background = v.getBackground();
//        if (background instanceof TransitionDrawable) {
//            if (visibility == View.VISIBLE) {
//                TransitionDrawable transition = (TransitionDrawable) background;
//                transition.startTransition((int) duration);
//
//            } else {
//                TransitionDrawable transition = (TransitionDrawable) background;
//                transition.reverseTransition((int) duration);
//            }
//        }
//
//    }
//
//    private void handleAlphaOnTitle(float percentage) {
//        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
//            if (mIsTheTitleContainerVisible) {
//                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
//                mIsTheTitleContainerVisible = false;
//            }
//
//        } else {
//
//            if (!mIsTheTitleContainerVisible) {
//                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
//                mIsTheTitleContainerVisible = true;
//            }
//        }
//    }
//
//
//    private void done() {
//        if (Utils.isNetworkConnected()) {
//            if (property.validToSend()) {
//                if (UserConfig.userToken == null || UserConfig.userToken.equals("-1")) {
//                    Toast toast = Toast.makeText(UserJobDetailActivity.this, R.string.jobnophone, Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                    return;
//                }
//
//
//                if (property.status == UserJob.WAIT_STATUS) {
//                    String s = FanoosApplication.applicationContext.getString(R.string.isAcceptWait);
//                    s = String.format(s, (property.remote_id + Constant.DEFAULT_FANOOS_CODE));
//                    Toast toast = Toast.makeText(UserJobDetailActivity.this, s, Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                    return;
//
//
//                } else if (property.status == UserJob.DRAFT_STATUS || property.isChanged()) {
//                    if (progressDialog == null || !progressDialog.isShowing()) {
//                        progressDialog = new SweetAlertDialog(this).setTitleText("");
//                    }
//                    progressDialog.setTitle("");
////                    progressDialog.showContentText(true);
//                    progressDialog.setContentText(getResources().getString(R.string.sending)+"\n"+getString(R.string.wait));
//                    progressDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
//
//
//                    progressDialog.showCancelButton(false);
//                    if (!progressDialog.isShowing())
//                        progressDialog.show();
//
//                    if (property.city == -1 || property.city == 0) {
//                        property.city = UserConfig.city;
//                    }
//
//                    if (property.remote_id == 0) {
//                        curretnRequestCode = VolleyService.getInstance().SendNewAdversWithToekn(UserJobDetailActivity.this, newAdver, 1, property);
//                        if (curretnRequestCode == null) {
//                            progressDialog.dismiss();
//                            Toast toast = Toast.makeText(UserJobDetailActivity.this, getString(R.string.send_error), Toast.LENGTH_SHORT);
//                            toast.setGravity(Gravity.CENTER, 0, 0);
//                            toast.show();
//                        }
//                    } else {
//                        curretnRequestCode = VolleyService.getInstance().SendEditAdversWithToekn(UserJobDetailActivity.this, editAdver, 1, property);
//                        if (curretnRequestCode == null) {
//                            progressDialog.dismiss();
//                            Toast toast = Toast.makeText(UserJobDetailActivity.this, getString(R.string.send_error), Toast.LENGTH_SHORT);
//                            toast.setGravity(Gravity.CENTER, 0, 0);
//                            toast.show();
//                        }
//                    }
//
//                } else {
//                    Toast toast = Toast.makeText(UserJobDetailActivity.this, R.string.no_job_change, Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//
//                }
//            } else {
//                Toast toast = Toast.makeText(UserJobDetailActivity.this, R.string.jobmandatory, Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();
//            }
//
//        } else {
//            Toast toast = Toast.makeText(UserJobDetailActivity.this, getString(R.string.intetnet_not_connected), Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            toast.show();
//        }
//
//    }
//
//    private void showDuplicateDialog(final long userjob) {
//        final UserJob a = MySqliteOpenHelper.getInstance().myJobsremote.get(userjob);
//        if (progressDialog == null || !progressDialog.isShowing()) {
//            progressDialog = new SweetAlertDialog(this);
//        }
//        if (a != null) {
//            progressDialog.setContentText(getResources().getString(R.string.duplicate_job));
//            progressDialog.setTitleText(getString(R.string.duplicate_title));
//            progressDialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
//            progressDialog.showCancelButton(true);
//            progressDialog.setCancelText(getString(R.string.cancel));
//            progressDialog.setConfirmText(getString(R.string.duplication_action));
//            progressDialog.showContentText(true);
//            progressDialog.setCancelClickListener(null);
//            progressDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                @Override
//                public void onClick(SweetAlertDialog sweetAlertDialog) {
//
//                    Intent b = new Intent(UserJobDetailActivity.this, UserJobDetailActivity.class);
//                    UserJob userJob = MySqliteOpenHelper.getInstance().loadedUserJobs.get(a.remote_id);
//                    if (userJob != null) {
//                        b.putExtra("job", userJob);
//                    } else {
//                        b.putExtra("job", a);
//                    }
//
//                    startActivity(b);
//                }
//
//
//            });
//        } else {
//            progressDialog.setContentText(getResources().getString(R.string.duplicatio_nologin));
//            progressDialog.setTitleText(getString(R.string.duplicate_title));
//            progressDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
//            progressDialog.showCancelButton(false);
////            progressDialog.setCancelText(getString(R.string.cancel));
//            progressDialog.setConfirmText(getString(R.string.ok));
//            progressDialog.showContentText(true);
////            progressDialog.setCancelClickListener(null);
//            progressDialog.setConfirmClickListener(null);
//        }
//        if (!progressDialog.isShowing())
//            progressDialog.show();
//
//    }
//
//    @Override
//    public void onObjectReslut(int req, int status, UserJob userJob, Object data) {
//        if (status == ServiceDelegate.ERROR_CODE) {
//            if (req == detail_request) {
//                Toast toast = Toast.makeText(this, R.string.send_error, Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();
//
//                finish();
//                return;
//            }
//            if (progressDialog == null || !progressDialog.isShowing())
//                progressDialog = new SweetAlertDialog(UserJobDetailActivity.this);
//            progressDialog.showContentText(true);
//            progressDialog.setTitleText("");
//            progressDialog.setContentText(getString(R.string.send_error));
//            progressDialog.setConfirmText(getString(R.string.ok));
//            progressDialog.showCancelButton(false);
//            progressDialog.setCancelClickListener(null);
//            progressDialog.setConfirmClickListener(null);
//            progressDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
//            if (!progressDialog.isShowing())
//                progressDialog.show();
//            return;
//        } else if (req == report_requesr) {
//            if (progressDialog == null || !progressDialog.isShowing())
//                progressDialog = new SweetAlertDialog(UserJobDetailActivity.this);
//
//            progressDialog.setTitleText("");
//            progressDialog.setContentText(getString(R.string.report_success));
//            progressDialog.setConfirmText(getString(R.string.ok));
//            progressDialog.showCancelButton(false);
//            progressDialog.setCancelClickListener(null);
//            progressDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                @Override
//                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                    if (jobReportDialog != null)
//                        jobReportDialog.dismiss();
//                    progressDialog.dismiss();
//                }
//            })
//                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//            if (!progressDialog.isShowing())
//                progressDialog.show();
//
//
//        } else if (req == editAdver || req == newAdver) {
//            try {
//                JSONObject object = new JSONObject((String) data);
//                String statuss = object.getString("msg");
//                if (statuss.equals("ok")) {
//                    if (progressDialog == null || !progressDialog.isShowing())
//                        progressDialog = new SweetAlertDialog(UserJobDetailActivity.this);
//
//                    progressDialog.setTitleText("");
//                    progressDialog.setContentText(getString(R.string.sendsuccess));
//                    progressDialog.setConfirmText(getString(R.string.ok));
//                    progressDialog.showCancelButton(false);
//                    progressDialog.setCancelClickListener(null);
//                    progressDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                        @Override
//                        public void onClick(SweetAlertDialog sweetAlertDialog) {
//                            if (jobReportDialog != null)
//                                jobReportDialog.dismiss();
//                            progressDialog.dismiss();
//                        }
//                    })
//                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//                    if (!progressDialog.isShowing())
//                        progressDialog.show();
//                    userJob.status = UserJob.WAIT_STATUS;
//                    MySqliteOpenHelper.getInstance().insertORUpdateUserJob(userJob);
//                    setUserJob(userJob, true);
//                    return;
//                } else if (statuss.equals("duplicate")) {
//                    showDuplicateDialog(object.getLong("id"));
////                            Toast toast = Toast.makeText(this, getResources().getString(R.string.duplicate_job), Toast.LENGTH_SHORT);
////                            toast.setGravity(Gravity.CENTER, 0, 0);
////                            toast.show();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//
//            }
//
//
//        } else if (req == detail_request) {
//            JSONArray array = (JSONArray) data;
//            HashMap<Long, UserJob> userJobFromJson = Utils.getUserJobFromJson(array, true, false);
//            if (userJobFromJson != null && userJobFromJson.size() > 0) {
//                UserJob userJob1 = userJobFromJson.get(userJob.remote_id);
//                Collections.sort(userJob1.images);
//
//                userJob.fillFromUserJob(userJob1);
//            } else {
//                MySqliteOpenHelper.getInstance().delete(userJob);
//                Toast toast = Toast.makeText(this, this.getString(R.string.no_userjob), Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();
//                finish();
//                return;
//            }
//
//            userJob.setLoaded(true);
//            userJob.loadedDate = new Date();
//            if (userJob.images != null && userJob.images.size() > 0) {
//                Collections.sort(userJob.images);
//            }
//
//            setUserJob(userJob, false);
//        }
//
//
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        if (v == backMenu) {
//            finish();
//        } else if (v == bookMenu) {
//            if (property.remote_id == 0) {
//                Toast toast = Toast.makeText(UserJobDetailActivity.this, "errrrrrrrrrrror", Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();
//                return;
//            }
//            if (property.bookmark == 1) {
//                Toast toast = Toast.makeText(UserJobDetailActivity.this, getResources().getString(R.string.nobookmark_text), Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();
//                bookMenu.setImageResource(R.drawable.ic_bookmark_no);
//                property.bookmark = 0;
//
//                MySqliteOpenHelper.getInstance().insertORUpdateUserJob(property);
//
//
//            } else {
//                Toast toast = Toast.makeText(UserJobDetailActivity.this, getResources().getString(R.string.bookmark_text), Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();
//                property.bookmark = 1;
//                bookMenu.setImageResource(R.drawable.ic_bookmark_yes);
//                MySqliteOpenHelper.getInstance().insertORUpdateUserJob(property);
//
////
////
//            }
//        } else if (v == okMenu) {
//            done();
//        } else if (v == editMenu) {
//            edit = true;
//            Intent a = new Intent(this, CreateJobActivity.class);
//            a.putExtra("user_job_id", property.local_id);
//            startActivity(a);
//        } else if (v == shareMenu) {
//            String shareBody = VolleyService.JOB_DETAIL_URL + (property.remote_id + DEFAULT_FANOOS_CODE);
//            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//            sharingIntent.setType("text/plain");
//            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, property.title);
//            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//            startActivity(Intent.createChooser(sharingIntent, "Complete Action Using"));
//
//        } else if (v == upgrade) {
//            if (!UserConfig.upgrade) {
//                Utils.showHelp(this, v, getResources().getString(R.string.upgrade), getResources().getString(R.string.upgrade_target));
//                UserConfig.upgrade = true;
//                SharedPreferences preferences = FanoosApplication.applicationContext.getSharedPreferences("userconfing", Context.MODE_PRIVATE);
//                SharedPreferences.Editor edit = preferences.edit();
//                edit.putBoolean("upgrade", UserConfig.upgrade);
//                edit.commit();
//            } else {
//                Utils.payment(this, payment_requst, Utils.UPGRADE_TYPE, property.remote_id);
//            }
//
//        } else if (v == immediate) {
//            if (!UserConfig.immediate) {
//                Utils.showHelp(this, v, this.getResources().getString(R.string.immediate), this.getResources().getString(R.string.immediate_target));
//                UserConfig.immediate = true;
//                SharedPreferences preferences = FanoosApplication.applicationContext.getSharedPreferences("userconfing", Context.MODE_PRIVATE);
//                SharedPreferences.Editor edit = preferences.edit();
//                edit.putBoolean("immediate", UserConfig.immediate);
//                edit.commit();
//            } else {
//                Utils.payment(this, payment_requst, Utils.IMMEDIATE_TYPE, property.remote_id);
//            }
//
//        } else if (v == fesival) {
//            if (!UserConfig.festival) {
//                Utils.showHelp(this, v, this.getResources().getString(R.string.festival), this.getResources().getString(R.string.festival_target));
//                UserConfig.festival = true;
//                SharedPreferences preferences = FanoosApplication.applicationContext.getSharedPreferences("userconfing", Context.MODE_PRIVATE);
//                SharedPreferences.Editor edit = preferences.edit();
//                edit.putBoolean("festival", UserConfig.festival);
//                edit.commit();
//            } else {
//                Utils.payment(this, payment_requst, Utils.FESTIVAL_TYPE, property.remote_id);
//
//            }
//
//        }
//
//    }
//
//
//    class UserJobImageViewListner implements View.OnClickListener {
//
//        @Override
//        public void onClick(View v) {
//            ImageView image = (ImageView) v;
//            Intent a = new Intent(UserJobDetailActivity.this, ImageViewActivity.class);
//
//            a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            Integer tag = (Integer) image.getTag();
//            a.putExtra("position", tag);
//            a.putExtra("job", property);
//            a.putExtra("type", 1);
//            startActivity(a);
//        }
//    }
//
//
//    public void setReportVisible() {
//        jobReport.setVisibility(!(property.myjob == 1) ? View.VISIBLE : View.GONE);
//        warining.setVisibility(!(property.myjob == 1) ? View.VISIBLE : View.GONE);
//        myadversActionLay.setVisibility((property.myjob == 1) ? View.VISIBLE : View.GONE);
//        if (bookMenu != null)
//            bookMenu.setVisibility((property.myjob == 1) ? View.GONE : View.VISIBLE);
//        if (okMenu != null)
//            okMenu.setVisibility(((property.myjob == 1) && UserConfig.phone != null && !UserConfig.phone.equals("-1")) ? View.VISIBLE : View.GONE);
//        if (shareMenu != null) {
//            shareMenu.setVisibility((property.status == ACCEPT_STATUS || property.myjob!=1) ? View.VISIBLE : View.GONE);
//        }
//        if (editMenu != null) {
//            editMenu.setVisibility((property.myjob == 1) ? View.VISIBLE : View.GONE);
//        }
//
//        visit_layout.setVisibility((property.myjob == 1 && property.status != DRAFT_STATUS) ? View.VISIBLE : View.GONE);
//
//
//    }
//
//    boolean firstImageloaded = false;
//
//    public void setUserJob(final UserJob userJob, boolean firstImageloaded1) {
//        this.property = userJob;
//        firstImageloaded = firstImageloaded1;
//        okMenu.setVisibility(View.GONE);
//        bookMenu.setVisibility(View.GONE);
//        shareMenu.setVisibility(View.GONE);
//        editMenu.setVisibility(View.GONE);
//
//
//        if (userJob.getImageCount() > 0) {
//            mViewPager.setVisibility(View.VISIBLE);
//            mCustomPagerAdapter = new CustomPagerAdapter();
//            mViewPager.setAdapter(mCustomPagerAdapter);
//
//            mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//                @Override
//                public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
//                    int maxScroll = appBarLayout.getTotalScrollRange();
//                    float percentage = (float) Math.abs(offset) / (float) maxScroll;
//                    handleAlphaOnTitle(percentage);
//
//                }
//            });
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                mTitleContainer.setBackground(defaultDrawble);
//            } else {
//                mTitleContainer.setBackgroundDrawable(defaultDrawble);
//            }
//
//            DisplayMetrics dm = new DisplayMetrics();
//            getWindowManager().getDefaultDisplay().getMetrics(dm);
//            Display mDisplay = getWindowManager().getDefaultDisplay();
//            Point a = new Point();
//            mDisplay.getSize(a);
//            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) mViewPager.getLayoutParams();
//            param.height = a.y / 2;
//            mViewPager.setLayoutParams(param);
//        } else {
//            mViewPager.setVisibility(View.GONE);
//        }
//        if (userJob.getImageCount() > 1) {
//            circlePageIndicator.setVisibility(View.VISIBLE);
//            circlePageIndicator.setViewPager(mViewPager);
//        } else {
//            circlePageIndicator.setVisibility(View.GONE);
//
//        }
//
//
//        progressBar.setVisibility(View.VISIBLE);
//        detail.setVisibility(View.GONE);
//
//
//        if ((!userJob.isLoaded() || userJob.loadedDate == null || userJob.loadedDate.getTime() < (new Date().getTime() - 60 * 1000)) && userJob.myjob != 1) {
//            curretnRequestCode = VolleyService.getInstance().UserJobDetail(this, detail_request, userJob);
//            return;
//        }
//
//
//        if (firstImageloaded || userJob.getImageCount() == 0 || userJob.myjob == 1) {
//
//            detail.setVisibility(View.VISIBLE);
//
//            if (userJob.myjob == 0) {
//
//                UserJob userJob1 = MySqliteOpenHelper.getInstance().historyJobs.get(userJob.remote_id);
//                if (userJob1 != null) {
//                    userJob.local_id = userJob1.local_id;
//                }
//                userJob1 = MySqliteOpenHelper.getInstance().bookmarkJobs.get(userJob.remote_id);
//                if (userJob1 != null) {
//                    userJob.bookmark = userJob1.bookmark;
//                }
//
//                MySqliteOpenHelper.getInstance().loadedUserJobs.put(userJob.remote_id, userJob);
//                MySqliteOpenHelper.getInstance().insertORUpdateUserJob(userJob);
//            } else {
//                initPayTable();
//            }
//
//            setReportVisible();
//            if (bookMenu != null) {
//                if (userJob.bookmark == 1) {
//                    bookMenu.setImageResource(R.drawable.ic_bookmark_yes);
//                } else {
//                    bookMenu.setImageResource(R.drawable.ic_bookmark_no);
//                }
//            }
////
////        supportActionBar.setTitle("gggggggggggg");
//
//
//            if (userJob.remote_id != 0) {
//                fanoos_code.setText("" + (userJob.remote_id + DEFAULT_FANOOS_CODE));
//                fanoos_lay.setVisibility(View.VISIBLE);
//
//            } else {
//                fanoos_lay.setVisibility(View.GONE);
//
//            }
//            if (userJob.namevisible && userJob.name != null && userJob.name.trim().length() > 0) {
//                nameLayout.setVisibility(View.VISIBLE);
//                name.setText(userJob.name);
//            } else {
//                nameLayout.setVisibility(View.GONE);
//            }
//
//
////            SpannableString content = new SpannableString(DBAdapter.getInstance().allNodes.get(property.nodeid).path);
////            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
////            nodePath.setText(content);
//            nodePath.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent a = new Intent(UserJobDetailActivity.this, MainActivity.class);
//                    a.putExtra("node_id", userJob.nodeid);
//                    startActivity(a);
//                }
//            });
//            if (userJob.city != 0) {
//                telegramCHannel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int city = userJob.city;
//                        if (city != 0) {
//                            City city1 = DBAdapter.getInstance().cities.get(city);
//                            Province province = DBAdapter.getInstance().province.get(city1.provincecode);
//                            String url = province.chanenelPath.trim();
//                            Intent i = new Intent(Intent.ACTION_VIEW);
//                            i.setData(Uri.parse(url));
//                            startActivity(i);
//                        }
//
//
//                    }
//                });
//            } else {
//                telegramCHannel.setVisibility(View.GONE);
//            }
//
//            myMore.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (!moreExapnded) {
//                        myMore.setText(getString(R.string.myadvers_more_hide));
//                        moreLayout.setVisibility(View.VISIBLE);
//                        moreLayout.startAnimation(moreAnimation);
//                        moreExapnded = true;
//                    } else {
//                        moreExapnded = false;
//                        myMore.setText(getString(R.string.myadvers_more));
//                        moreLayout.startAnimation(hideMoreAnimation);
//                    }
//                }
//            });
//
//            if (userJob.myjob == 1) {
//                String status1 = (userJob.status == DRAFT_STATUS ? getString(R.string.draft) :
//                        userJob.status == WAIT_STATUS ? getString(R.string.waiting) :
//                                userJob.status == ACCEPT_STATUS ? getString(R.string.accepted) :
//                                        userJob.status == REJECT_STATUS ? getString(R.string.rejected) :
//                                                getString(R.string.unknown));
//                status.setText(status1);
//                immediate.setEnabled(userJob.status == WAIT_STATUS);
//                upgrade.setEnabled(userJob.status == ACCEPT_STATUS);
//                fesival.setEnabled(userJob.status == ACCEPT_STATUS);
//                immediate.setTextColor(userJob.status == WAIT_STATUS ? Color.WHITE : Color.GRAY);
//                upgrade.setTextColor(userJob.status == ACCEPT_STATUS ? Color.WHITE : Color.GRAY);
//                fesival.setTextColor(userJob.status == ACCEPT_STATUS ? Color.WHITE : Color.GRAY);
//            }
//            telSpacer.setVisibility(View.GONE);
//            mobileSpacer.setVisibility(View.GONE);
//            telegramSpacer.setVisibility(View.GONE);
//
//
//            if (userJob.mobile != null && userJob.mobile.trim().length() > 0) {
//                mobile.setText(userJob.mobile);
//                mobilecall.setTag(userJob.mobile);
//                mobilesms.setTag(userJob.mobile);
//                mobile_lay.setVisibility(View.VISIBLE);
//            } else {
//                mobile_lay.setVisibility(View.GONE);
//            }
//            if (userJob.tel != null && userJob.tel.trim().length() > 0) {
//                mobileSpacer.setVisibility(View.VISIBLE);
//                tel.setText(userJob.tel);
//                tellcall.setTag(userJob.tel);
//                tel_lay.setVisibility(View.VISIBLE);
//            } else {
//                tel_lay.setVisibility(View.GONE);
//            }
//            if (userJob.telegram != null && userJob.telegram.trim().length() > 0) {
//                if (userJob.tel != null && userJob.tel.trim().length() > 0) {
//                    telSpacer.setVisibility(View.VISIBLE);
//                } else {
//                    mobileSpacer.setVisibility(View.VISIBLE);
//                }
//                telegram.setText(userJob.telegram);
//                telegramaction.setTag(userJob.telegram);
//                telegram_layout.setVisibility(View.VISIBLE);
//
//            } else {
//                telegram_layout.setVisibility(View.GONE);
//            }
//            if (userJob.email != null && userJob.email.trim().length() > 0) {
//                if (userJob.telegram != null && userJob.telegram.trim().length() > 0) {
//                    telegramSpacer.setVisibility(View.VISIBLE);
//                } else if (userJob.tel != null && userJob.tel.trim().length() > 0) {
//                    telSpacer.setVisibility(View.VISIBLE);
//                } else {
//                    mobileSpacer.setVisibility(View.VISIBLE);
//                }
//                email.setText(userJob.email);
//                email_ley.setVisibility(View.VISIBLE);
//            } else {
//                email_ley.setVisibility(View.GONE);
//            }
//
//
//            if (userJob.title != null && userJob.title.trim().length() > 0) {
//                title.setText(userJob.title);
//                title.setVisibility(View.VISIBLE);
//            } else {
//                title.setVisibility(View.GONE);
//            }
//            if (userJob.desc != null && userJob.desc.trim().length() > 0) {
//                desc.setText(userJob.desc);
//                desc.setVisibility(View.VISIBLE);
//            } else {
//                desc.setVisibility(View.GONE);
//            }
//            if (userJob.advers != null && userJob.advers.trim().length() > 0) {
//                advers.setText(userJob.advers);
//                advers.setVisibility(View.VISIBLE);
//            } else {
//                advers.setVisibility(View.GONE);
//            }
////        if (property.dateString != null && property.dateString.trim().length() > 0) {
////            date.setText(property.dateString);
////        } else {
////            date.setVisibility(View.GONE);
////        }
//
//
//            if (userJob.region != 0) {
//                Region region = DBAdapter.getInstance().getRegion(userJob.region);
//                if (region != null) {
//                    this.region.setText(region.name);
//                    region_ley.setVisibility(View.VISIBLE);
//                } else {
//                    region_ley.setVisibility(View.GONE);
//                }
//            } else {
//                region_ley.setVisibility(View.GONE);
//            }
//            if (userJob.address != null && userJob.address.trim().length() > 0)
//                address.setText(userJob.address);
//            if (userJob.address == null || userJob.address.trim().length() == 0) {
//                adress_lay.setVisibility(View.GONE);
//            } else {
//                adress_lay.setVisibility(View.VISIBLE);
//            }
//
//
////            if (property.getImageCount() > 0) {
////                mViewPager.setVisibility(View.VISIBLE);
////                mViewPagerlay.setVisibility(View.VISIBLE);
////                ViewGroup.LayoutParams layoutParams = mViewPagerlay.getLayoutParams();
////                layoutParams.height = Utils.getDPforPixel(300);
////                mViewPagerlay.setLayoutParams(layoutParams);
////                pagerindex.setVisibility(View.VISIBLE);
////                pagerRadioButton = new RadioButton[property.getImageCount()];
////                for (int i = 0; i < pagerRadioButton.length; i++) {
////
////                    pagerRadioButton[i] = new RadioButton(this);
////
////
////                    pagerRadioButton[i].setClickable(false);
////                    pagerindex.addView(pagerRadioButton[i], -2, -2);
////                    if (i == 0) {
////                        ViewTreeObserver viewTreeObserver = pagerRadioButton[i].getViewTreeObserver();
////                        viewTreeObserver.addOnPreDrawListener(new MyTreeObserver(viewTreeObserver, pagerRadioButton[i]));
////                        final ViewTreeObserver viewTreeObserver1 = splitImage.getViewTreeObserver();
////                        viewTreeObserver1.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
////                            @Override
////                            public boolean onPreDraw() {
////                                if (split_left != -1)
////                                    splitImage.layout(split_left, split_top, split_right, split_bottom);
////                                splitImage.getViewTreeObserver().removeOnPreDrawListener(this);
////                                return true;
////                            }
////                        });
////
////                    }
////                }
////            } else {
////
////                mViewPagerlay.setVisibility(View.GONE);
////            }
//            String ff = "";
//            if (userJob.bike) {
//                ff += (this.getString(R.string.bike));
//
//            }
//            if (userJob.cardReader) {
//                if (ff.length() > 0)
//                    ff += ",";
//
//                ff += (this.getString(R.string.card_reader));
//            }
//
//
//            if (userJob.bike || userJob.cardReader) {
//                features.setText(ff.substring(0, ff.length()));
//                feature_lay.setVisibility(View.VISIBLE);
//            } else {
//                feature_lay.setVisibility(View.GONE);
//            }
//
//            ff = "";
//            if (userJob.moorning) {
//                ff += (this.getString(R.string.morning));
//
//            }
//            if (userJob.noon) {
//                if (ff.length() > 0)
//                    ff += ",";
//                ff += (this.getString(R.string.noon));
//            }
//            if (userJob.evening) {
//                if (ff.length() > 0)
//                    ff += ",";
//                ff += (this.getString(R.string.evening));
//            }
//            if (userJob.boarding) {
//                if (ff.length() > 0)
//                    ff += ",";
//                ff += (this.getString(R.string.boarding));
//            }
//            if (userJob.moorning || userJob.noon || userJob.evening || userJob.boarding) {
//                time.setText(ff);
//                time_lay.setVisibility(View.VISIBLE);
//            } else {
//                time_lay.setVisibility(View.GONE);
//            }
//
//
//            if (userJob.myjob == 1)
//                reColorVisisted();
//            progressBar.setVisibility(View.GONE);
//
//        }
//
//
//    }
//
//    private void reColorVisisted() {
//        totalview.setText(getString(R.string.totalvisited) + " : " + property.totalVisited);
//        day_1_cnt.setText("" + property.day1Cnt);
//        day_2_cnt.setText("" + property.day2Cnt);
//        day_3_cnt.setText("" + property.day3Cnt);
//        day_4_cnt.setText("" + property.day4Cnt);
//
//
//        double maxcnt = 0;
//        if (property.day1Cnt > maxcnt)
//            maxcnt = property.day1Cnt;
//        if (property.day2Cnt > maxcnt)
//            maxcnt = property.day2Cnt;
//        if (property.day3Cnt > maxcnt)
//            maxcnt = property.day3Cnt;
//        if (property.day4Cnt > maxcnt)
//            maxcnt = property.day4Cnt;
//        if (maxcnt != 0) {
//            visit_layout.setVisibility(View.VISIBLE);
//            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) day_1_free.getLayoutParams();
//            layoutParams.weight = (float) (1.0f - property.day1Cnt / maxcnt);
//            day_1_free.setLayoutParams(layoutParams);
//
//            layoutParams = (LinearLayout.LayoutParams) day_2_free.getLayoutParams();
//            layoutParams.weight = (float) (1.0f - property.day2Cnt / maxcnt);
//            day_2_free.setLayoutParams(layoutParams);
//
//            layoutParams = (LinearLayout.LayoutParams) day_3_free.getLayoutParams();
//            layoutParams.weight = (float) (1.0f - property.day3Cnt / maxcnt);
//            day_3_free.setLayoutParams(layoutParams);
//
//            layoutParams = (LinearLayout.LayoutParams) day_4_free.getLayoutParams();
//            layoutParams.weight = (float) (1.0f - property.day4Cnt / maxcnt);
//            day_4_free.setLayoutParams(layoutParams);
//
//
//            layoutParams = (LinearLayout.LayoutParams) day_1_color.getLayoutParams();
//            layoutParams.weight = (float) (property.day1Cnt / maxcnt);
//            day_1_color.setLayoutParams(layoutParams);
//
//            layoutParams = (LinearLayout.LayoutParams) day_2_color.getLayoutParams();
//            layoutParams.weight = (float) (property.day2Cnt / maxcnt);
//            day_2_color.setLayoutParams(layoutParams);
//
//            layoutParams = (LinearLayout.LayoutParams) day_3_color.getLayoutParams();
//            layoutParams.weight = (float) (property.day3Cnt / maxcnt);
//            day_3_color.setLayoutParams(layoutParams);
//
//            layoutParams = (LinearLayout.LayoutParams) day_4_color.getLayoutParams();
//            layoutParams.weight = (float) (property.day4Cnt / maxcnt);
//            day_4_color.setLayoutParams(layoutParams);
//        } else {
//            visit_layout.setVisibility(View.VISIBLE);
//            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) day_1_free.getLayoutParams();
//            layoutParams.weight = (0.9f);
//            day_1_free.setLayoutParams(layoutParams);
//
//            layoutParams = (LinearLayout.LayoutParams) day_2_free.getLayoutParams();
//            layoutParams.weight = (0.9f);
//            day_2_free.setLayoutParams(layoutParams);
//
//            layoutParams = (LinearLayout.LayoutParams) day_3_free.getLayoutParams();
//            layoutParams.weight = (0.9f);
//            day_3_free.setLayoutParams(layoutParams);
//
//            layoutParams = (LinearLayout.LayoutParams) day_4_free.getLayoutParams();
//            layoutParams.weight = (0.9f);
//            day_4_free.setLayoutParams(layoutParams);
//
//
//            layoutParams = (LinearLayout.LayoutParams) day_1_color.getLayoutParams();
//            layoutParams.weight = (0.1f);
//            day_1_color.setLayoutParams(layoutParams);
//
//            layoutParams = (LinearLayout.LayoutParams) day_2_color.getLayoutParams();
//            layoutParams.weight = (0.1f);
//            day_2_color.setLayoutParams(layoutParams);
//
//            layoutParams = (LinearLayout.LayoutParams) day_3_color.getLayoutParams();
//            layoutParams.weight = (0.1f);
//            day_3_color.setLayoutParams(layoutParams);
//
//            layoutParams = (LinearLayout.LayoutParams) day_4_color.getLayoutParams();
//            layoutParams.weight = (0.1f);
//            day_4_color.setLayoutParams(layoutParams);
//        }
//
//
//    }
//
//
//    int animationView = 1;
////    ActionBar supportActionBar;
//
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (curretnRequestCode != null) {
//            FanoosApplication.getInstance().cancelPendingRequests(curretnRequestCode);
//            curretnRequestCode = null;
//        }
//    }
//
//    class CallingListener implements View.OnClickListener {
//
//        @Override
//        public void onClick(View v) {
//            final String phone = ((String) v.getTag());
//            if (phone == null || phone.trim().length() == 0) {
//                return;
//            }
//            Calendar a = Calendar.getInstance();
//            a.setTime(new Date());
//            int i = a.get(Calendar.HOUR_OF_DAY);
//
//            try {
//                if (i >= 23 || i < 7) {
//                    final Dialog dialog = new Dialog(UserJobDetailActivity.this);
//                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setContentView(R.layout.payment_cost_dialog);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                        dialog.findViewById(R.id.main_content).setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
//                    }
//                    Window window = dialog.getWindow();
//                    DisplayMetrics displaymetrics = new DisplayMetrics();
//                    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//                    int width = (int) (displaymetrics.widthPixels * 0.9);
//                    int height = (int) (displaymetrics.heightPixels * 0.4);
//                    window.setLayout(width, height);
//
//                    TextView title = (TextView) dialog.findViewById(R.id.title);
//                    title.setText(R.string.call_time_title);
//                    TextView text = (TextView) dialog.findViewById(R.id.text);
//                    text.setText(R.string.call_time_warining);
//                    text.setTypeface(FanoosApplication.BYEKAN_NORMAL);
//                    Button button = (Button) dialog.findViewById(R.id.payment);
//                    button.setText(R.string.contiue);
//
//                    dialog.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                        }
//                    });
//                    dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                        }
//                    });
//                    dialog.findViewById(R.id.payment).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                            Intent intent = new Intent(Intent.ACTION_DIAL);
//                            intent.setData(Uri.parse("tel:" + phone.trim()));
//                            startActivity(intent);
//                        }
//                    });
//                    dialog.show();
//
//                } else {
//                    Intent intent = new Intent(Intent.ACTION_DIAL);
//                    intent.setData(Uri.parse("tel:" + phone.trim()));
//                    startActivity(intent);
//                }
//            } catch (Exception e) {
//                Toast toast = Toast.makeText(UserJobDetailActivity.this, R.string.no_call_feature, Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();
//            }
//
//
//        }
//    }
//
//    void telegramOpen(String ss) {
//        if (ss.startsWith("@") && ss.length() > 2)
//            ss = ss.substring(1);
//        Intent telegram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/" + ss));
//        this.startActivity(telegram);
//    }
//
//    class SMSListener implements View.OnClickListener {
//
//        @Override
//        public void onClick(View v) {
//            String phone = ((String) v.getTag());
//            if (phone == null || phone.trim().length() == 0) {
//                return;
//
//            }
//            try {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("sms:" + phone.trim()));
//                startActivity(intent);
//            } catch (Exception e) {
//                Toast toast = Toast.makeText(UserJobDetailActivity.this, R.string.no_sms_feature, Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();
//            }
//
//        }
//    }
//
//    public class JobReportDialog extends Dialog {
//        //        int report_code=-1;
////        int report_id = -1;
//        LinearLayout main_report;
//        LinearLayout other_report_lay;
//        View ok, cancel;
//        EditText otherReportText;
//        RadioButton image_report, contact_report, content_report, repeat_report, node_report, mistake_content_report, other_report, report_description;
//        RadioButton current;
//
//        public void recreate() {
//            main_report.setVisibility(View.VISIBLE);
//            ok.setVisibility(View.GONE);
//            if (current != null)
//                current.setChecked(false);
//        }
//
//        public JobReportDialog(final Context this1) {
//            super(this1);
//            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            setContentView(R.layout.job_report_dialog);
//
////            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
////                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
//
//            image_report = (RadioButton) this.findViewById(R.id.image_report);
//            contact_report = (RadioButton) this.findViewById(R.id.contact_report);
//            content_report = (RadioButton) this.findViewById(R.id.content_report);
//            repeat_report = (RadioButton) this.findViewById(R.id.repeat_report);
//            node_report = (RadioButton) this.findViewById(R.id.node_report);
//            mistake_content_report = (RadioButton) this.findViewById(R.id.mistake_content_report);
//            other_report = (RadioButton) this.findViewById(R.id.other_report);
//            image_report.setTag(1);
//            contact_report.setTag(2);
//            content_report.setTag(3);
//            repeat_report.setTag(4);
//            node_report.setTag(5);
//            mistake_content_report.setTag(6);
//            other_report.setTag(7);
//            otherReportText = (EditText) findViewById(R.id.ohter_report_text);
//            otherReportText.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    property.reporttext = s.toString();
//                    if (s.toString().length() > 0)
//
//                        ok.setVisibility(View.VISIBLE);
//                    else
//                        ok.setVisibility(View.GONE);
//
//
//                }
//            });
//
////            report_description = (RadioButton) this.findViewById(R.id.report_description);
//            class OnCheckListner implements CompoundButton.OnCheckedChangeListener {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (!isChecked) {
//
//                        return;
//                    }
//                    current = (RadioButton) buttonView;
//                    property.reportindex = (Integer) buttonView.getTag();
////                    report_code=(Integer)buttonView.getTag();
//
//
//                    if (buttonView == other_report) {
//                        if (otherReportText.getText() != null && otherReportText.getText().toString().length() > 0)
//                            ok.setVisibility(View.VISIBLE);
//                        else
//                            ok.setVisibility(View.GONE);
//                        Animation slide_in = AnimationUtils.loadAnimation(UserJobDetailActivity.this, R.anim.report_slide_in);
//                        slide_in.setAnimationListener(new Animation.AnimationListener() {
//                            @Override
//                            public void onAnimationStart(Animation animation) {
//                                main_report.setVisibility(View.VISIBLE);
////                                other_report_lay.setVisibility(View.GONE);
//                            }
//
//                            @Override
//                            public void onAnimationEnd(Animation animation) {
//                                main_report.setVisibility(View.GONE);
////                                other_report_lay.setVisibility(View.VISIBLE);
//                            }
//
//                            @Override
//                            public void onAnimationRepeat(Animation animation) {
//
//                            }
//                        });
//                        Animation slide_out = AnimationUtils.loadAnimation(UserJobDetailActivity.this, R.anim.report_slide_out);
//                        main_report.startAnimation(slide_in);
//                        other_report_lay.startAnimation(slide_out);
//                    } else {
//
//                        ok.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
//            OnCheckListner onCheckListner = new OnCheckListner();
//            image_report.setOnCheckedChangeListener(onCheckListner);
//            contact_report.setOnCheckedChangeListener(onCheckListner);
//            content_report.setOnCheckedChangeListener(onCheckListner);
//            repeat_report.setOnCheckedChangeListener(onCheckListner);
//            node_report.setOnCheckedChangeListener(onCheckListner);
//            mistake_content_report.setOnCheckedChangeListener(onCheckListner);
//            other_report.setOnCheckedChangeListener(onCheckListner);
//
//
//            main_report = (LinearLayout) this.findViewById(R.id.main_report);
//            other_report_lay = (LinearLayout) this.findViewById(R.id.other_report_lay);
////            other_report_lay.setVisibility(View.GONE);
//            ok = this.findViewById(R.id.ok);
//            cancel = this.findViewById(R.id.cancel);
//            ok.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (Utils.isNetworkConnected()) {
//                        if (progressDialog == null || !progressDialog.isShowing())
//                            progressDialog = new SweetAlertDialog(UserJobDetailActivity.this);
//                        progressDialog.setTitleText("");
//                        progressDialog.setContentText(getResources().getString(R.string.sending)+"\n"+getString(R.string.wait));
//                        progressDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
//                        progressDialog.showCancelButton(false);
//                        progressDialog.showContentText(true);
//
//                        progressDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
//                        if (!progressDialog.isShowing())
//                            progressDialog.show();
//
//
//                        VolleyService.getInstance().SendjobReport(UserJobDetailActivity.this, report_requesr, property);
////                        JobReportDialog.this.dismiss();
////                        Toast toast = Toast.makeText(UserJobDetailActivity.this, getString(R.string.report_success), Toast.LENGTH_SHORT);
////                        toast.setGravity(Gravity.CENTER, 0, 0);
////                        toast.show();
//                    } else {
//                        Toast toast = Toast.makeText(UserJobDetailActivity.this, getString(R.string.intetnet_not_connected), Toast.LENGTH_SHORT);
//                        toast.setGravity(Gravity.CENTER, 0, 0);
//                        toast.show();
//                    }
//
//
//                }
//            });
//            cancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    JobReportDialog.this.dismiss();
//                }
//            });
//
//
//        }
//
//    }
//
////    private void showAddNewTooltips() {
////        View inflate = LayoutInflater.from(this).inflate(R.layout.custom_tooltip, null);
////        TextView tt = (TextView) inflate.findViewById(R.id.text);
////        tt.setText("Fanoos Uniqe Code");
////        tt.setTypeface(FanoosApplication.BYEKAN_NORMAL);
////    }
////        ToolTip toolTip = new ToolTip()
////                .withColor(ContextCompat.getColor(this, R.color.tooltip_color))
////                .withContentView(inflate);
//    //.withAnimationType(ToolTip.AnimationType.NONE);//.withoutShadow();
//
////        mRedToolTipView = mToolTipFrameLayout.showToolTipForViewResId(this, toolTip, R.id.id1);
////        new Handler().postDelayed(new Runnable() {
////            @Override
////            public void run() {
////                hideTooltips();
////            }
////        }, 4000);
//
//
//    //    private void hideTooltips() {
////        if (mRedToolTipView != null)
////            mRedToolTipView.remove();
////
////
////    }
//    class CustomPagerAdapter extends PagerAdapter {
//
//        public int getItemPosition(Object object) {
//            return POSITION_NONE;
//        }
//
//
//        LayoutInflater mLayoutInflater;
//
//        public CustomPagerAdapter() {
//
//            mLayoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        }
//
//        @Override
//        public int getCount() {
//            if (property == null || property.images == null)
//                return 0;
//            return property.getImageCount();
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == (object);
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, final int position) {
//            UserJob.Image image = property.getShowImage().get(position);
//            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
//            final ProgressBar progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
//
//            final ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
//            imageView.setTag(position);
//
//            String finalImgURL;
//            if (image.localname != null) {
//                finalImgURL = "file://" + image.localname;
//            } else {
//                finalImgURL = image.remotename;
//            }
//
//            ImageLoader.getInstance().displayImage(finalImgURL, imageView, new ImageLoadingListener() {
//                @Override
//                public void onLoadingStarted(String imageUri, View view) {
//                    if (!firstImageloaded) {
////                        radios.setVisibility(View.GONE);
//                        progressBar.setVisibility(View.GONE);
//                    } else {
//                        progressBar.setVisibility(View.VISIBLE);
////                        if (property.getImageCount() >1)
////                            radios.setVisibility(View.VISIBLE);
////                        else
////                            radios.setVisibility(View.GONE);
//
//                    }
//
//                }
//
//                @Override
//                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//
//                }
//
//                @Override
//                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//
//                    progressBar.setVisibility(View.GONE);
//                    imageView.setOnClickListener(listner);
//                    if (!firstImageloaded) {
//                        firstImageloaded = true;
////                        if (property.getImageCount() >1)
////                            radios.setVisibility(View.VISIBLE);
////                        else
////                            radios.setVisibility(View.GONE);
//                        setUserJob(property, firstImageloaded);
//                    }
//
//                }
//
//                @Override
//                public void onLoadingCancelled(String imageUri, View view) {
//
//                }
//            });
//            container.addView(itemView);
//
//            return itemView;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((FrameLayout) object);
//        }
//    }
//
//
//}
