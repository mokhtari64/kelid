package ir.mehdi.kelid.collage;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import ir.mehdi.kelid.R;


public abstract class StickerView extends FrameLayout {
    private OperationListener operationListener;
    protected boolean deletable = true;
    protected boolean editable = true;
    protected boolean isBG = false;
//    protected boolean horizentalFix = true;

    public void setEditable(boolean editable) {
        this.editable = editable;
        iv_edit.setVisibility(editable ? VISIBLE : GONE);
    }

    public void setBG(boolean frontable) {
        this.isBG = frontable;
        if (isBG)
            this.iv_top.setImageResource(R.drawable.icon_bg_enable);
//        iv_top.setVisibility(frontable ? VISIBLE : GONE);
    }

//    public void setHorizentalFixable(boolean frontable) {
//        horizentalFix = frontable;
////        iv_yfix_scale.setVisibility(frontable ? VISIBLE : GONE);
//    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    protected boolean deletable() {
        return deletable;

    }

//    private static Matrix matrix;
//
//
//    private int dialerHeight, dialerWidth;

    public interface OperationListener {
        void onClick(StickerView stickerView);

        void onEdit(StickerView stickerView);

        void onDelete(StickerView stickerView);

        void onSetBg(StickerView stickerView);


    }

    public void setOperationListener(OperationListener operationListener) {
        this.operationListener = operationListener;
    }


    public static final String TAG = "com.knef.stickerView";
    private BorderView iv_border;
    private ImageView iv_scale;
    private ImageView iv_x_scale;
    //    public ImageView iv_yfix_scale;
    private ImageView iv_xfix_scale;
    private ImageView iv_y_scale;
    private ImageView iv_delete;
    private ImageView iv_flip;
    protected ImageView iv_edit;
    protected ImageView iv_top;
    int borderColor = Color.WHITE;


    // For scalling
    private float this_orgX = -1, this_orgY = -1;
    private float scale_orgX = -1, scale_orgY = -1;
    private double startAngle;
    float firstAngle = 0;

    private double scale_orgWidth = -1, scale_orgHeight = -1;
    // For rotating
    private float rotate_orgX = -1, rotate_orgY = -1, rotate_newX = -1, rotate_newY = -1;
    // For moving
    private float move_orgX = -1, move_orgY = -1;

    private double centerX, centerY;

    private final static int BUTTON_SIZE_DP = 30;
    private final static int SELF_SIZE_DP = 100;


    public StickerView(Context context) {
        super(context);
        init(context);
    }

    public StickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StickerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.iv_border = new BorderView(context);
        this.iv_scale = new ImageView(context);
        this.iv_x_scale = new ImageView(context);
//        this.iv_yfix_scale = new ImageView(context);
        this.iv_xfix_scale = new ImageView(context);
        this.iv_y_scale = new ImageView(context);
        this.iv_delete = new ImageView(context);
        this.iv_flip = new ImageView(context);
        this.iv_edit = new ImageView(context);
        this.iv_top = new ImageView(context);

        this.iv_scale.setImageResource(R.drawable.icon_resize);
        this.iv_x_scale.setImageResource(R.drawable.icon_x_resize);
//        this.iv_yfix_scale.setImageResource(R.drawable.icon_y_alined);
        this.iv_xfix_scale.setImageResource(R.drawable.icon_x_alined);
        this.iv_y_scale.setImageResource(R.drawable.icon_y_resize);
        this.iv_delete.setImageResource(R.drawable.icon_delete);
        this.iv_flip.setImageResource(R.drawable.icon_flip);
        this.iv_edit.setImageResource(R.drawable.icon_edit);
        this.iv_top.setImageResource(R.drawable.icon_top_enable);
        this.setTag("DraggableViewGroup");
        this.iv_border.setTag("iv_border");
        this.iv_scale.setTag("iv_scale");
        this.iv_x_scale.setTag("iv_x_scale");
//        this.iv_yfix_scale.setTag("iv_yfix_scale");
        this.iv_y_scale.setTag("iv_y_scale");
        this.iv_delete.setTag("iv_delete");
        this.iv_flip.setTag("iv_flip");
        this.iv_edit.setTag("iv_edit");
        this.iv_top.setTag("iv_top");

        int margin = convertDpToPixel(BUTTON_SIZE_DP, getContext()) / 2;
        int size = convertDpToPixel(SELF_SIZE_DP, getContext());

        LayoutParams this_params =
                new LayoutParams(
                        size,
                        size
                );
        this_params.gravity = Gravity.CENTER;

        LayoutParams iv_main_params =
                new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                );
        iv_main_params.setMargins(margin, margin, margin, margin);

        LayoutParams iv_border_params =
                new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                );
        iv_border_params.setMargins(margin, margin, margin, margin);

        LayoutParams iv_scale_params =
                new LayoutParams(
                        convertDpToPixel(BUTTON_SIZE_DP, getContext()),
                        convertDpToPixel(BUTTON_SIZE_DP, getContext())
                );
        iv_scale_params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        LayoutParams iv_x_scale_params =
                new LayoutParams(
                        convertDpToPixel(BUTTON_SIZE_DP, getContext()),
                        convertDpToPixel(BUTTON_SIZE_DP, getContext())
                );
//        LayoutParams iv_yfix_scale_params =
//                new LayoutParams(
//                        convertDpToPixel(BUTTON_SIZE_DP, getContext()),
//                        convertDpToPixel(BUTTON_SIZE_DP, getContext())
//                );
//        iv_yfix_scale_params.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
//        iv_yfix_scale_params.bottomMargin = 10;
        LayoutParams iv_xfix_scale_params =
                new LayoutParams(
                        convertDpToPixel(BUTTON_SIZE_DP, getContext()),
                        convertDpToPixel(BUTTON_SIZE_DP, getContext())
                );
        iv_xfix_scale_params.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
//        iv_xfix_scale_params.topMargin = 10;
        iv_x_scale_params.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
        LayoutParams iv_y_scale_params =
                new LayoutParams(
                        convertDpToPixel(BUTTON_SIZE_DP, getContext()),
                        convertDpToPixel(BUTTON_SIZE_DP, getContext())
                );
        iv_y_scale_params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;

        LayoutParams iv_delete_params =
                new LayoutParams(
                        convertDpToPixel(BUTTON_SIZE_DP, getContext()),
                        convertDpToPixel(BUTTON_SIZE_DP, getContext())
                );
        iv_delete_params.gravity = Gravity.TOP | Gravity.RIGHT;

        LayoutParams iv_flip_params =
                new LayoutParams(
                        convertDpToPixel(BUTTON_SIZE_DP, getContext()),
                        convertDpToPixel(BUTTON_SIZE_DP, getContext())
                );
        iv_flip_params.gravity = Gravity.BOTTOM | Gravity.LEFT;
        LayoutParams iv_edit_params =
                new LayoutParams(
                        convertDpToPixel(BUTTON_SIZE_DP, getContext()),
                        convertDpToPixel(BUTTON_SIZE_DP, getContext())
                );
        iv_edit_params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        LayoutParams iv_top_params =
                new LayoutParams(
                        convertDpToPixel(BUTTON_SIZE_DP, getContext()),
                        convertDpToPixel(BUTTON_SIZE_DP, getContext())
                );
        iv_top_params.gravity = Gravity.TOP | Gravity.LEFT;

        this.setLayoutParams(this_params);
        this.addView(getMainView(), iv_main_params);
        this.addView(iv_border, iv_border_params);
        this.addView(iv_scale, iv_scale_params);
        this.addView(iv_x_scale, iv_x_scale_params);
//        this.addView(iv_yfix_scale, iv_yfix_scale_params);
        this.addView(iv_xfix_scale, iv_xfix_scale_params);
        this.addView(iv_y_scale, iv_y_scale_params);
        this.addView(iv_delete, iv_delete_params);
        this.addView(iv_flip, iv_flip_params);
//        if (this instanceof StickerTextView)
        this.addView(iv_edit, iv_edit_params);
        this.addView(iv_top, iv_top_params);
        this.setOnTouchListener(mTouchListener);
        this.iv_scale.setOnTouchListener(mTouchListener);
        this.iv_x_scale.setOnTouchListener(mTouchListener);

        this.iv_y_scale.setOnTouchListener(mTouchListener);
        this.iv_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StickerView.this.getParent() != null) {
                    if (deletable()) {
                        ViewGroup myCanvas = ((ViewGroup) StickerView.this.getParent());
                        myCanvas.removeView(StickerView.this);
                    }
                    if (operationListener != null)
                        operationListener.onDelete(StickerView.this);
                }
            }
        });
//        this.iv_yfix_scale.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                Log.v(TAG, "flip the view");
//                float angela = getRotation();
//                if ((angela >= 0 && angela <= 180)) {
//                    angela = 90;
//                } else if ((angela < 0 && angela > -180)) {
//                    angela = -90;
//                }
//
//                setRotation(angela);
//
//                requestLayout();
//            }
//        });
        this.iv_xfix_scale.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.v(TAG, "flip the view");
                float angela = getRotation();
                if ((angela >= 0 && angela <= 90)) {
                    angela = 0;
                } else if ((angela > 90 && angela > 180)) {
                    angela = 180;
                } else if ((angela < 0 && angela > -90)) {
                    angela = 0;
                } else if ((angela < -90 && angela > -180)) {
                    angela = 180;
                }

                setRotation(angela);

                requestLayout();
            }
        });
        this.iv_flip.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.v(TAG, "flip the view");

                View mainView = getMainView();
                mainView.setRotationY(mainView.getRotationY() == -180f ? 0f : -180f);
                mainView.invalidate();
                requestLayout();
            }
        });
        this.iv_top.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.v(TAG, "top the view");
                if (!isBG) {
                    bringToFront();
                } else {
                    if (operationListener != null)
                        operationListener.onSetBg(StickerView.this);

                }
            }
        });
        this.iv_edit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.v(TAG, "top the view");
                if (operationListener != null)
                    operationListener.onEdit(StickerView.this);
            }
        });

//        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//
//            @Override
//            public void onGlobalLayout() {
//                // method called more than once, but the values only need to be initialized one time
//                if (dialerHeight == 0 || dialerWidth == 0) {
//                    dialerHeight = getHeight();
//                    dialerWidth = getWidth();
//
//                    // resize
//                    Matrix resize = new Matrix();
//                    resize.postScale(dialerWidth, dialerHeight);
//
//                }
//            }
//        });
    }


    private double getAngle3(double touch_x, double touch_y) {
        double r = Math.atan2(touch_x - (StickerView.this.getX() + StickerView.this.getLayoutParams().width / 2d), (StickerView.this.getY() + StickerView.this.getLayoutParams().height / 2d) - touch_y);
        return Math.toDegrees(r);
    }

    private double getAngle2(double touch_x, double touch_y) {

        double tx = touch_x - (StickerView.this.getX() + StickerView.this.getLayoutParams().width / 2d), ty = touch_y - (StickerView.this.getY() + StickerView.this.getLayoutParams().height / 2d);
        double t_length = Math.sqrt(tx * tx + ty * ty);
        return Math.acos(ty / t_length);
    }

    private double getAngle(double xTouch, double yTouch) {
        double x = xTouch - (StickerView.this.getX() + StickerView.this.getLayoutParams().width / 2d);
        double y = yTouch - (StickerView.this.getY() + StickerView.this.getLayoutParams().height / 2d);

        switch (getQuadrant(x, y)) {
            case 1:
                return Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            case 2:
                return 180 - Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            case 3:
                return 180 + (-1 * Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
            case 4:
                return 360 + Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            default:
                return 0;
        }
    }

    private void rotateDialer(float degrees) {
//        matrix.postRotate(degrees);
        setRotation(degrees);
    }

    /**
     * @return The selected quadrant.
     */
    private static int getQuadrant(double x, double y) {
        if (x >= 0) {
            return y >= 0 ? 1 : 4;
        } else {
            return y >= 0 ? 2 : 3;
        }
    }

    public boolean isFlip() {
        return getMainView().getRotationY() == -180f;
    }

    protected abstract View getMainView();

    private static final int MAX_CLICK_DURATION = 100;
    private long startClickTime;
    private OnTouchListener mTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {

            if (view == StickerView.this) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startClickTime = System.currentTimeMillis();

                        Log.v(TAG, "sticker view action down");
                        move_orgX = event.getRawX();
                        move_orgY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:

                        Log.v(TAG, "sticker view action move");
                        float offsetX = event.getRawX() - move_orgX;
                        float offsetY = event.getRawY() - move_orgY;
                        StickerView.this.setX(StickerView.this.getX() + offsetX);
                        StickerView.this.setY(StickerView.this.getY() + offsetY);
                        move_orgX = event.getRawX();
                        move_orgY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        long clickDuration = System.currentTimeMillis() - startClickTime;
                        if (clickDuration < MAX_CLICK_DURATION) {
                            if (operationListener != null)
                                operationListener.onClick(StickerView.this);
                        }
                        Log.v(TAG, "sticker view action up");

                        break;

                }


            } else if (view.getTag().equals("iv_x_scale")) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        scale_orgX = event.getRawX();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.v(TAG, "iv_x_scale action move");


                        double offsetX = event.getRawX() - scale_orgX;
                        if (Math.abs(StickerView.this.getLayoutParams().width) > 2 * iv_x_scale.getWidth()) {
                            StickerView.this.getLayoutParams().width += offsetX;

                        }else
                        {
                            return true;
                        }
                        scale_orgX = event.getRawX();


                }
            } else if (view.getTag().equals("iv_y_scale")) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        scale_orgY = event.getRawY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.v(TAG, "iv_y_scale action move");


                        double offsetY = event.getRawY() - scale_orgY;
                        if (Math.abs(StickerView.this.getLayoutParams().height) > 2 * iv_x_scale.getHeight()) {
                            StickerView.this.getLayoutParams().height += offsetY;

                        } else
                            return true;
                        scale_orgY = event.getRawY();
//                        break;


                }
            } else if (view.getTag().equals("iv_scale")) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        Log.v(TAG, "iv_scale action down");

                        this_orgX = StickerView.this.getX();
                        this_orgY = StickerView.this.getY();

                        scale_orgX = event.getRawX();
                        scale_orgY = event.getRawY();
                        scale_orgWidth = StickerView.this.getLayoutParams().width;
                        scale_orgHeight = StickerView.this.getLayoutParams().height;

                        rotate_orgX = event.getRawX();
                        rotate_orgY = event.getRawY();

                        centerX = StickerView.this.getX() +
                                ((View) StickerView.this.getParent()).getX() +
                                (float) StickerView.this.getWidth() / 2;


                        //double statusBarHeight = Math.ceil(25 * getContext().getResources().getDisplayMetrics().density);
                        int result = 0;
                        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
                        if (resourceId > 0) {
                            result = getResources().getDimensionPixelSize(resourceId);
                        }
                        double statusBarHeight = result;
                        centerY = StickerView.this.getY() +
                                ((View) StickerView.this.getParent()).getY() +
                                statusBarHeight +
                                (float) StickerView.this.getHeight() / 2;
                        startAngle = Math.atan2(StickerView.this.getHeight() / 2, StickerView.this.getWidth() / 2) * 180 / Math.PI;

                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.v(TAG, "iv_scale action move");


                        rotate_newX = event.getRawX();
                        rotate_newY = event.getRawY();
//                        Log.v(TAG, "log angle 1: " +  (StickerView.this.getTop()+StickerView.this.getWidth())+","+ (StickerView.this.getLeft()+StickerView.this.getHeight()));
//                        Log.v(TAG, "log angle 2: " + StickerView.this.getBottom()+","+StickerView.this.getRight());

                        double angle_diff = Math.abs(
                                Math.atan2(event.getRawY() - scale_orgY, event.getRawX() - scale_orgX)
                                        - Math.atan2(scale_orgY - centerY, scale_orgX - centerX)) * 180 / Math.PI;

                        Log.v(TAG, "angle_diff: " + angle_diff);

                        double length1 = getLength(centerX, centerY, scale_orgX, scale_orgY);
                        double length2 = getLength(centerX, centerY, event.getRawX(), event.getRawY());

                        int size = convertDpToPixel(SELF_SIZE_DP, getContext());
                        if (length2 > length1
                                && (angle_diff < 25 || Math.abs(angle_diff - 180) < 25)
                                ) {
                            //scale up
                            double offsetX = Math.abs(event.getRawX() - scale_orgX);
                            double offsetY = Math.abs(event.getRawY() - scale_orgY);
                            double offset = Math.max(offsetX, offsetY);
                            offset = Math.round(offset);
                            StickerView.this.getLayoutParams().width += offset;
                            StickerView.this.getLayoutParams().height += offset;
                            onScaling(true);
                            //DraggableViewGroup.this.setX((float) (getX() - offset / 2));
                            //DraggableViewGroup.this.setY((float) (getY() - offset / 2));
                        } else if (length2 < length1
                                && (angle_diff < 25 || Math.abs(angle_diff - 180) < 25)
                                && StickerView.this.getLayoutParams().width > size / 2
                                && StickerView.this.getLayoutParams().height > size / 2) {
                            //scale down
                            double offsetX = Math.abs(event.getRawX() - scale_orgX);
                            double offsetY = Math.abs(event.getRawY() - scale_orgY);
                            double offset = Math.max(offsetX, offsetY);
                            offset = Math.round(offset);
                            StickerView.this.getLayoutParams().width -= offset;
                            StickerView.this.getLayoutParams().height -= offset;
                            onScaling(false);
                        }

                        //rotate

                        double angle = Math.atan2(event.getRawY() - centerY, event.getRawX() - centerX) * 180 / Math.PI;
//                        Log.v(TAG, "log angle: " + angle);
//                        Log.v(TAG, "log angle2: " + ((float) (angle - startAngle)));

                        //setRotation((float) angle - 45);
                        float angela = (float) (angle - startAngle);
                        setRotation(angela);
//                        if (((angela>-0.20 && angela<0.2) || (angela>-89.8 && angela<-90.2) || (angela>89.8 && angela<90.2) || (angela>-179.80 && angela<-180.2))) {
//                            borderColor = Color.GREEN;
//                        } else {
//                            borderColor = Color.WHITE;
//                        }
                        Log.v(TAG, "log angle 1: " + angela + ":" + ((angela > -0.20 && angela < 0.2) || (angela > -89.8 && angela < -90.2) || (angela > 89.8 && angela < 90.2) || (angela > -179.80 && angela < -180.2)));


                        onRotating();

                        rotate_orgX = rotate_newX;
                        rotate_orgY = rotate_newY;

                        scale_orgX = event.getRawX();
                        scale_orgY = event.getRawY();

                        postInvalidate();
                        requestLayout();
                        break;
                    case MotionEvent.ACTION_UP:
//                        borderColor = Color.WHITE;
                        Log.v(TAG, "iv_scale action up");
                        break;
                }
            }
            postInvalidate();
            requestLayout();
            return true;
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private double getLength(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));
    }

    private float[] getRelativePos(float absX, float absY) {
        Log.v("ken", "getRelativePos getX:" + ((View) this.getParent()).getX());
        Log.v("ken", "getRelativePos getY:" + ((View) this.getParent()).getY());
        float[] pos = new float[]{
                absX - ((View) this.getParent()).getX(),
                absY - ((View) this.getParent()).getY()
        };
        Log.v(TAG, "getRelativePos absY:" + absY);
        Log.v(TAG, "getRelativePos relativeY:" + pos[1]);
        return pos;
    }

    public void setControlItemsHidden(boolean isHidden) {
        if (isHidden) {
            iv_border.setVisibility(View.INVISIBLE);
            iv_scale.setVisibility(View.INVISIBLE);
            iv_delete.setVisibility(View.INVISIBLE);
            iv_flip.setVisibility(View.INVISIBLE);
//            if (this instanceof StickerTextView)
            iv_edit.setVisibility(View.INVISIBLE);

            iv_top.setVisibility(View.INVISIBLE);
            iv_x_scale.setVisibility(View.INVISIBLE);
//            iv_yfix_scale.setVisibility(View.INVISIBLE);
            iv_xfix_scale.setVisibility(View.INVISIBLE);
            iv_y_scale.setVisibility(View.INVISIBLE);

        } else {
            iv_border.setVisibility(View.VISIBLE);
            iv_scale.setVisibility(View.VISIBLE);

            iv_flip.setVisibility(View.VISIBLE);

            iv_delete.setVisibility(View.VISIBLE);
//            if (this instanceof StickerTextView)
            if (editable)
                iv_edit.setVisibility(View.VISIBLE);
//            if (frontable)
            iv_top.setVisibility(View.VISIBLE);
            iv_x_scale.setVisibility(View.VISIBLE);
//            if (horizentalFix)
//                iv_yfix_scale.setVisibility(View.VISIBLE);
            iv_xfix_scale.setVisibility(View.VISIBLE);
            iv_y_scale.setVisibility(View.VISIBLE);
        }
    }

//    protected View getImageViewFlip() {
//        return iv_flip;
//    }

    protected void onScaling(boolean scaleUp) {
    }

    protected void onRotating() {
    }

    private class BorderView extends View {

        public BorderView(Context context) {
            super(context);
        }

        public BorderView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public BorderView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            // Draw sticker border

            LayoutParams params = (LayoutParams) this.getLayoutParams();

            Log.v(TAG, "params.leftMargin: " + params.leftMargin);

            Rect border = new Rect();
            border.left = (int) this.getLeft() - params.leftMargin;
            border.top = (int) this.getTop() - params.topMargin;
            border.right = (int) this.getRight() - params.rightMargin;
            border.bottom = (int) this.getBottom() - params.bottomMargin;
            Paint borderPaint = new Paint();
            borderPaint.setStrokeWidth(6);
            borderPaint.setColor(borderColor);
            borderPaint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(border, borderPaint);

        }
    }

    public static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

//    public void setControlsVisibility(boolean isVisible) {
//        if (!isVisible) {
//            iv_border.setVisibility(View.GONE);
//            iv_delete.setVisibility(View.GONE);
//            iv_flip.setVisibility(View.GONE);
//            iv_scale.setVisibility(View.GONE);
//        } else {
//            iv_border.setVisibility(View.VISIBLE);
//            iv_delete.setVisibility(View.VISIBLE);
//            iv_flip.setVisibility(View.VISIBLE);
//            iv_scale.setVisibility(View.VISIBLE);
//        }
//
//    }
}
