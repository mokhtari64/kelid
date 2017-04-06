package ir.mehdi.kelid.coolmenu;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import ir.mehdi.kelid.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public final class CoolMenuFrameLayout extends FrameLayout {
    private Object mainMenu;
    int cascadeTitleColor = Color.RED;
    int originTitleColor = Color.CYAN;

    Animation mMainMenuAnimation;

    public void setMainMenuAnimation(Animation a) {
        mMainMenuAnimation = a;
        if (a != null)
            ((TranslateLayout)getChildAt(num - 1)).getMainMenu().startAnimation(a);
    }

    public void setMainMenu(Object mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void setOriginTitleColor(int originTitleColor) {
        this.originTitleColor = originTitleColor;
    }

    public void setCascadeTitleColor(int cascadeTitleColor) {
        this.cascadeTitleColor = cascadeTitleColor;
    }

    private Context mContext;

    private int num = 4;

    private int mTitleColor;

    private float mTitleSize;


    private int[] ids = {R.id.view0, R.id.view1, R.id.view2, R.id.view3, R.id.view4};

    private Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    private ObjectAnimator[] mOpenAnimators;

    private ObjectAnimator[] mChosenAnimators;

    private ObjectAnimator[] mMenuOpenAnimators;

    private PagerAdapter mAdapter;

    private MenuObserver mObserver;

    private MenuChooser mMenuChooser = new MenuChooser();

    private TranslateLayout.OnMainMenuListner menuListener = new OnMainMenuListner1();

    private TranslateLayout.OnOptionMainMenuListner optionMenuListener;

    public void setOptionMenuListener(TranslateLayout.OnOptionMainMenuListner optionMenuListener) {
        this.optionMenuListener = optionMenuListener;
        if (optionMenuListener != null) {
            for (int i = 0; i < num; i++) {
                ((TranslateLayout) getChildAt(i)).setmOnOtpionOptionMainMenuListner(optionMenuListener);
            }
        }

    }

    private boolean opening = false;

    private List<Fragment> objects = new ArrayList<>();

    private int chosen;

    public CoolMenuFrameLayout(Context context) {
        this(context, null);
    }

    public CoolMenuFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoolMenuFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CoolMenuFrameLayout);
        num = array.getInteger(R.styleable.CoolMenuFrameLayout_num, 4);
        mTitleColor = array.getColor(R.styleable.CoolMenuFrameLayout_titleColor,
                getResources().getColor(android.R.color.primary_text_light));
        mTitleSize = array.getDimension(R.styleable.CoolMenuFrameLayout_titleSize,
                getResources().getDimension(R.dimen.cl_title_size));

        array.recycle();
        init();
    }


    private void init() {
        if (!isInEditMode()) {
            setWillNotDraw(true);
            chosen = num - 1;
            for (int i = 0; i < num; i++) {
                TranslateLayout frameLayout = new TranslateLayout(mContext);
                frameLayout.setId(ids[i]);
                frameLayout.setTag(i);
                frameLayout.setOnClickListener(mMenuChooser);
                frameLayout.setOnMenuClickListener(menuListener);

                frameLayout.setMenuTitleSize(mTitleSize);
                frameLayout.setMenuTitleColor(mTitleColor);
                if (i == num - 1) frameLayout.setMenuAlpha(1);
                LayoutParams layoutParams = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
                frameLayout.setLayoutParams(layoutParams);
                addView(frameLayout);

            }


            mOpenAnimators = new ObjectAnimator[num];
            mChosenAnimators = new ObjectAnimator[num];
            mMenuOpenAnimators = new ObjectAnimator[num];
            initAnim();
        }
    }

    private void initAnim() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            float transX = (float) ((i + 1) * 0.06);
            float transY = (float) ((i + 1) * 0.12);
            ObjectAnimator animator;

            PropertyValuesHolder valuesHolderX = PropertyValuesHolder.ofFloat("XFraction", 0, transX);
            PropertyValuesHolder valuesHolderY = PropertyValuesHolder.ofFloat("YFraction", 0, transY);
            animator = ObjectAnimator.ofPropertyValuesHolder(child, valuesHolderX, valuesHolderY);
            animator.setInterpolator(mInterpolator);
            animator.setDuration(300);
            mOpenAnimators[i] = animator;

            PropertyValuesHolder valuesHolderXReverse = PropertyValuesHolder.ofFloat("XFraction", transX, 1);
            animator = ObjectAnimator.ofPropertyValuesHolder(child, valuesHolderXReverse);
            animator.setInterpolator(mInterpolator);
            animator.setDuration(300);
            mChosenAnimators[i] = animator;

            PropertyValuesHolder valuesHolderAlpha = PropertyValuesHolder.ofFloat("menuAlpha", 1, 0);
            animator = ObjectAnimator.ofPropertyValuesHolder(child, valuesHolderAlpha);
            animator.setInterpolator(mInterpolator);
            animator.setDuration(300);
            mMenuOpenAnimators[i] = animator;
        }
    }


    public void setAdapter(PagerAdapter adapter) {
        if (mAdapter != null) {
            mAdapter.unregisterDataSetObserver(mObserver);
            mAdapter.startUpdate(this);
            for (int i = 0; i < num; i++) {
                mAdapter.destroyItem((ViewGroup) getChildAt(i), i, objects.get(i));
            }
            mAdapter.finishUpdate(this);
        }

        this.mAdapter = adapter;
        if (mAdapter == null) {
            return;
        }

        if (mObserver == null) {
            mObserver = new MenuObserver();
        }
        mAdapter.registerDataSetObserver(mObserver);

        int count = mAdapter.getCount();
        if (count != num) {
            throw new RuntimeException("number of view should equal 'num' that declared in xml");
        }

        for (int i = 0; i < count; i++) {
            ViewGroup childAt = (ViewGroup) getChildAt(i);
            Fragment object = (Fragment) mAdapter.instantiateItem(childAt, i);
            ((TranslateLayout) childAt).setFragment(object);
            objects.add(object);
        }
        mAdapter.finishUpdate(this);
    }

    @UiThread
    public void setTitles(@NonNull List<String> titles) {
        for (int i = 0; i < num; i++) {
            ((TranslateLayout) getChildAt(i)).setTitle(titles.get(i));
        }
    }

    @UiThread
    public void setMainMenuDrawble(@NonNull List<Drawable> titles) {
        for (int i = 0; i < num; i++) {
            ((TranslateLayout) getChildAt(i)).setMainMenu(titles.get(i));
        }
    }


    @UiThread
    public void setOptionMenuDrawble(@NonNull List<Drawable> titles) {
        for (int i = 0; i < num; i++) {
            ((TranslateLayout) getChildAt(i)).setOptionMenu(titles.get(i));
        }
    }


    @UiThread
    public void setTitleByIndex(@NonNull String title, int index) {
        if (index > num - 1) {
            throw new IndexOutOfBoundsException();
        }
        ((TranslateLayout) getChildAt(index)).setTitle(title);
    }

    @UiThread
    public void setMenuIcon(@NonNull int resId) {
        for (int i = 0; i < num; i++) {
            ((TranslateLayout) getChildAt(i)).setMainMenu(resId);
        }
    }

    public void toggle() {
        if (opening) {
            close();
        } else {
            open();
        }
    }

    private void open() {
        opening = true;
        for (int i = 0; i < num; i++) {

            TranslateLayout childAt = (TranslateLayout) getChildAt(i);

            childAt.getTitle().setTextColor(cascadeTitleColor);
            ImageView mainMenu = childAt.getMainMenu();
            Animation animation = mainMenu.getAnimation();
            if (animation != null)
                animation.cancel();
        }


        for (int i = 0; i < num; i++) {
            if (i == chosen) {
                mMenuOpenAnimators[i].start();
                mOpenAnimators[i].start();
            } else if (i > chosen) {
                mChosenAnimators[i].reverse();
            } else {
                mOpenAnimators[i].start();
            }
        }
        chosen = num - 1;
    }

    private void close() {
        opening = false;
        for (int i = 0; i < num; i++) {
            ((TranslateLayout) getChildAt(i)).getTitle().setTextColor(originTitleColor);
        }
        for (ObjectAnimator mAnimator : mOpenAnimators) {
            mAnimator.reverse();
        }
    }

    private void dataSetChanged() {
        if (opening) {
            close();
        }
        if (mAdapter != null) {
            mAdapter.startUpdate(this);
            for (int i = 0; i < num; i++) {
                mAdapter.destroyItem((ViewGroup) getChildAt(i), i, objects.get(i));
            }
            for (int i = 0; i < num; i++) {
                mAdapter.instantiateItem((ViewGroup) getChildAt(i), i);
            }
            mAdapter.finishUpdate(this);
        }
    }

    boolean isOpening() {
        return opening;
    }

    private class MenuObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            dataSetChanged();
        }

        @Override
        public void onInvalidated() {
            dataSetChanged();
        }
    }

    private class MenuChooser implements OnClickListener {

        @Override
        public void onClick(View v) {
            if (opening) {

                for (int i = 0; i < num; i++) {
                    TranslateLayout childAt = (TranslateLayout) getChildAt(i);
                    if (childAt == v && mMainMenuAnimation != null) {
                        ImageView mainMenu = childAt.getMainMenu();
                        mainMenu.startAnimation(mMainMenuAnimation);
                    } else {
                        Animation animation = childAt.getAnimation();
                        if (animation != null)
                            animation.cancel();
                    }
                    childAt.getTitle().setTextColor(originTitleColor);
                }


                chosen = (int) v.getTag();
                for (int i = 0; i < num; i++) {
                    if (i <= chosen) {
                        mOpenAnimators[i].reverse();
                    } else {
                        mChosenAnimators[i].start();
                    }
                }
                mMenuOpenAnimators[chosen].reverse();
            }
            opening = false;
        }
    }

    private class OnMainMenuListner1 implements TranslateLayout.OnMainMenuListner {

        @Override
        public void onMenuClick() {
            toggle();
        }
    }

}
