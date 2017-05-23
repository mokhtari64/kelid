package ir.mehdi.kelid.coolmenu;

import android.annotation.SuppressLint;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.sa90.materialarcmenu.ArcMenu;

import ir.mehdi.kelid.Const;
import ir.mehdi.kelid.Constant;
import ir.mehdi.kelid.MainActivity;
import ir.mehdi.kelid.R;

@SuppressLint("all")
@SuppressWarnings("all")
public class TranslateLayout extends FrameLayout implements View.OnClickListener {


    boolean menuEn = false;

    ToggleButton settingbtn;
    LayoutInflater inflater;
    RelativeLayout edit_device, edit_zone, edit_senario, menu_space, edit_user, rules, about_me, exit;
    LinearLayout setting_layer;
    Animation alpha, alpha_out, rotation, rotation_out;
    int screenWidth, sw;


    com.sa90.materialarcmenu.ArcMenu arcMenu;
    MainActivity activity;
    private OnOptionMainMenuListner mOnOtpionOptionMainMenuListner;
    private OnMainMenuListner mOnMainMenuListner;
    Fragment fragment;

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    private ImageView mMenu;
    ImageButton proprtyMenu, serviveMenu, consultingMenu, officeMenu;
    private ImageView mOptionMenu;

    private TextView mTitle;

    public ImageView getMainMenu() {
        return mMenu;
    }


    public ImageView getOptionMenu() {

        return mOptionMenu;

    }

    public TextView getTitle() {
        return mTitle;
    }

    private float mTitleTrans;
    private View view;

    public TranslateLayout(Context context) {
        super(context);
        activity = (MainActivity) context;

        init();
    }

    public TranslateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public TranslateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        if (!isInEditMode()) {
            try {
                setWillNotDraw(true);
                inflate(getContext(), R.layout.layout_title, this);
                view = getChildAt(0);

                initDrawer();
                mTitleTrans = getResources().getDimension(R.dimen.cl_title_trans);
                mMenu = (ImageView) view.findViewById(R.id.cl_menu);
                mOptionMenu = (ImageView) view.findViewById(R.id.cl_option_menu);
                mTitle = (TextView) view.findViewById(R.id.cl_title);
                arcMenu = (ArcMenu) view.findViewById(R.id.arcMenu);

                proprtyMenu = (ImageButton) view.findViewById(R.id.property);
                officeMenu = (ImageButton) view.findViewById(R.id.office);
                consultingMenu = (ImageButton) view.findViewById(R.id.consulting);
//                newsMenu = (ImageButton) view.findViewById(R.id.news);
                serviveMenu = (ImageButton) view.findViewById(R.id.service);

                mMenu.setOnClickListener(this);
                proprtyMenu.setOnClickListener(this);
                officeMenu.setOnClickListener(this);
                consultingMenu.setOnClickListener(this);
//                newsMenu.setOnClickListener(this);
                serviveMenu.setOnClickListener(this);
                mOptionMenu.setOnClickListener(this);
                setMenuAlpha(0);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    float orgPos1X;

    private void initDrawer() {
        setting_layer = (LinearLayout) findViewById(R.id.setting_layer);
        menu_space = (RelativeLayout) findViewById(R.id.menu_space);
        edit_device = (RelativeLayout) findViewById(R.id.edit_device);
        menu_space.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                closeMenu();
            }
        });
        edit_zone = (RelativeLayout) findViewById(R.id.edit_zone);
        edit_senario = (RelativeLayout) findViewById(R.id.edit_senario);
        edit_user = (RelativeLayout) findViewById(R.id.edit_user);
        rules = (RelativeLayout) findViewById(R.id.rules);
        about_me = (RelativeLayout) findViewById(R.id.about_me);
        exit = (RelativeLayout) findViewById(R.id.exit);


        orgPos1X = setting_layer.getX();
        settingbtn = (ToggleButton) findViewById(R.id.setting);
        alpha = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
        alpha_out = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_out);
        rotation = AnimationUtils.loadAnimation(getContext(), R.anim.clockwise_rotation);
        rotation_out = AnimationUtils.loadAnimation(getContext(), R.anim.unclockwise_rotation);


        screenWidth = ir.mehdi.kelid.utils.Utils.getScreenWidth(activity);
        sw = screenWidth;
//        setting_layer.setX(orgPos1X - screenWidth);
        setting_layer.setX(screenWidth + orgPos1X);
        assert settingbtn != null;
        settingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!menuEn) {
                    settingbtn.setClickable(false);
                    rotation.setRepeatCount(Animation.INFINITE);
                    rotation.setRepeatCount(0);
                    settingbtn.startAnimation(rotation_out);
                    setting_layer.animate().translationX(orgPos1X).setDuration(Const.AnimDuration);
                    final Handler handler = new Handler();
                    setting_layer.setEnabled(false);
                    menuEn=true;
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            settingbtn.setClickable(true);
                        }
                    }, Const.AnimDuration);


                } else {
                    closeMenu();

                }


            }
        });


    }

    void closeMenu() {
        if (menuEn)  {
            settingbtn.setChecked(false);
            settingbtn.setClickable(false);
            settingbtn.startAnimation(rotation);
            setting_layer.animate().translationX(setting_layer.getWidth()).setDuration(Const.AnimDuration);
//                    recyclerView.animate().translationX(orgPos1X).setDuration(dtime);
//            dashboard_layer.animate().alpha(1).setDuration(Const.AnimDuration);
//                    setting_layer.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.VISIBLE);
            final Handler handler = new Handler();
            setting_layer.setEnabled(false);
            menuEn=false;
            handler.postDelayed(new Runnable() {
                public void run() {
                    settingbtn.setClickable(true);
                }
            }, Const.AnimDuration);
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return ((CoolMenuFrameLayout) getParent()).isOpening();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (indexOfChild(view) != getChildCount() - 1) {
            bringChildToFront(view);
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    public float getXFraction() {
        int width = Utils.getScreenWidth(getContext());
        return (width == 0) ? 0 : getX() / (float) width;
    }

    public void setXFraction(float xFraction) {
        int width = Utils.getScreenWidth(getContext());
        setX((width > 0) ? (xFraction * width) : 0);
    }

    public float getYFraction() {
        int height = Utils.getScreenHeight(getContext());
        return (height == 0) ? 0 : getY() / (float) height;
    }

    public void setYFraction(float yFraction) {
        int height = Utils.getScreenHeight(getContext());
        setY((height > 0) ? (yFraction * height) : 0);
    }

    public void setOnMenuClickListener(OnMainMenuListner mOnOptionMainMenuListner) {
        this.mOnMainMenuListner = mOnOptionMainMenuListner;
    }

    public void setmOnOtpionOptionMainMenuListner(OnOptionMainMenuListner mOnOptionMainMenuListner) {
        this.mOnOtpionOptionMainMenuListner = mOnOptionMainMenuListner;

    }

    public void setTitle(CharSequence title) {
        mTitle.setText(title);
    }

    public void setMainMenu(Drawable drawable) {
        mMenu.setImageDrawable(drawable);
    }

    public void setOptionMenu(Drawable drawable) {
        mOptionMenu.setImageDrawable(drawable);
    }


    public void setMainMenu(int resId) {
        mMenu.setImageResource(resId);
    }

    public void setMenuTitleSize(float size) {
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    public void setMenuTitleColor(int color) {
        mTitle.setTextColor(color);
    }

    public void setMenuAlpha(float fraction) {
//        mMenu.setAlpha(fraction);
        mMenu.setScaleX(fraction);
        mMenu.setScaleY(fraction);
        mTitle.setTranslationX((1 - fraction) * -mTitleTrans);
    }

    @Override
    public void onClick(View v) {
        if (R.id.cl_menu == v.getId()) {
            if (mOnMainMenuListner != null) {
                mOnMainMenuListner.onMenuClick();
            }
        } else if (v == mOptionMenu) {
            if (mOnOtpionOptionMainMenuListner != null) {
                mOnOtpionOptionMainMenuListner.onMenuClick(this, fragment);
            }

        } else if (v == proprtyMenu) {
            arcMenu.toggleMenu();
            activity.registerNew(Constant.PROPERTY);

        } else if (v == serviveMenu) {
            arcMenu.toggleMenu();
            activity.registerNew(Constant.SERVICE);

        } else if (v == consultingMenu) {
            arcMenu.toggleMenu();
            activity.registerNew(Constant.CONSULTING);

        } else if (v == officeMenu) {
            arcMenu.toggleMenu();
            activity.registerNew(Constant.OFFICE);

        }
    }

    public interface OnMainMenuListner {
        void onMenuClick();
    }

    public interface OnOptionMainMenuListner {
        void onMenuClick(TranslateLayout translateLayout, Fragment fragment);
    }
}
