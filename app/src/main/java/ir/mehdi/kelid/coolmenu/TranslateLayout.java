package ir.mehdi.kelid.coolmenu;

import android.annotation.SuppressLint;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.Toast;
import android.widget.ToggleButton;


import ir.mehdi.kelid.Const;
import ir.mehdi.kelid.Constant;
import ir.mehdi.kelid.MainActivity;
import ir.mehdi.kelid.R;
import ir.mehdi.kelid.arcmenulibrary.ArcMenu;

@SuppressLint("all")
@SuppressWarnings("all")
public class TranslateLayout extends FrameLayout implements View.OnClickListener, Constant {
    private static final int[] ITEM_DRAWABLES = {R.drawable.add_image,
            R.drawable.add_text, R.drawable.preview, R.drawable.qr};
    private String[] str = {"Facebook", "Twiiter", "Flickr", "Instagram"};
    private int type[] = new int[]{PROPERTY
            , SERVICE
            , OFFICE
            , CONSULTING
            , NEWS};

    boolean menuEn = false;

    LayoutInflater inflater;
    Animation alpha, alpha_out, rotation, rotation_out;
    int screenWidth, sw;


    ArcMenu arcMenu;
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

    private void initArcMenu() {
        final int itemCount = ITEM_DRAWABLES.length;
        arcMenu.setToolTipTextSize(14);
//        arcMenu.setMinRadius(104);
//        arcMenu.setArc(175,255);
        arcMenu.setToolTipSide(ArcMenu.TOOLTIP_UP);
        arcMenu.setToolTipTextColor(Color.RED);
        arcMenu.setToolTipBackColor(getResources().getColor(R.color.white_pressed));
        arcMenu.setToolTipCorner(2);
        arcMenu.setToolTipPadding(8);
        arcMenu.setColorNormal(getResources().getColor(R.color.mRedDark));
        arcMenu.showTooltip(true);
        arcMenu.setDuration(ArcMenu.ArcMenuDuration.LENGTH_LONG);
//        arcMenu.setAnim(500,500, ArcMenu.ANIM_MIDDLE_TO_DOWN, ArcMenu.ANIM_MIDDLE_TO_RIGHT,
//                ArcMenu.ANIM_INTERPOLATOR_ANTICIPATE, ArcMenu.ANIM_INTERPOLATOR_ANTICIPATE);
        for (int i = 0; i < itemCount; i++) {
            ImageView item = new ImageView(activity);
            item.setImageResource(ITEM_DRAWABLES[i]);
            arcMenu.setAnim(400,400, ArcMenu.ANIM_MIDDLE_TO_DOWN, ArcMenu.ANIM_MIDDLE_TO_RIGHT,
                    ArcMenu.ANIM_INTERPOLATOR_DECLERATE, ArcMenu.ANIM_INTERPOLATOR_BOUNCE);
//            if(menuNum == 1){
//
//            }
//
//            if(menuNum == 4){
//                arcMenu.setAnim(500,500, ArcMenu.ANIM_MIDDLE_TO_DOWN, ArcMenu.ANIM_MIDDLE_TO_RIGHT,
//                        ArcMenu.ANIM_INTERPOLATOR_ANTICIPATE, ArcMenu.ANIM_INTERPOLATOR_ANTICIPATE);
//            }

            final int position = i;
            final int rrr = type[i];
            arcMenu.addItem(item, str[i], new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    activity.registerNew(rrr);
                }
            });
        }
    }

    private void init() {
        if (!isInEditMode()) {
            try {
                setWillNotDraw(true);
                inflate(getContext(), R.layout.layout_title, this);
                view = getChildAt(0);

                mTitleTrans = getResources().getDimension(R.dimen.cl_title_trans);
                mMenu = (ImageView) view.findViewById(R.id.cl_menu);
                mOptionMenu = (ImageView) view.findViewById(R.id.cl_option_menu);
                mTitle = (TextView) view.findViewById(R.id.cl_title);
                arcMenu = (ArcMenu) view.findViewById(R.id.arcMenu);
                initArcMenu();

//                proprtyMenu = (ImageButton) view.findViewById(R.id.property);
//                officeMenu = (ImageButton) view.findViewById(R.id.office);
//                consultingMenu = (ImageButton) view.findViewById(R.id.consulting);
////                newsMenu = (ImageButton) view.findViewById(R.id.news);
//                serviveMenu = (ImageButton) view.findViewById(R.id.service);

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

    void toggleArcMenu() {

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
            toggleArcMenu();
            activity.registerNew(Constant.PROPERTY);

        } else if (v == serviveMenu) {
            toggleArcMenu();
            activity.registerNew(Constant.SERVICE);

        } else if (v == consultingMenu) {
            toggleArcMenu();
            activity.registerNew(Constant.CONSULTING);

        } else if (v == officeMenu) {
            toggleArcMenu();
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
