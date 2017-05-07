package ir.mehdi.kelid.collage;


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cheungchingai on 6/15/15.
 */
public class StickerTextView extends StickerView{
    String dialogTitle;

    public void setDialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
    }

    public String getDialogTitle() {
        return dialogTitle;
    }

    private AutoResizeTextView tv_main;
//    int type;//1 name,2 title,3 adcers,4 desc,5 addres,6 tele,7 mobile,
    public StickerTextView(Context context) {
        super(context);

    }

//    public StickerTextView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }

    public StickerTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public void setTexColor(int color)
    {
        tv_main.setTextColor(color);
    }

    @Override
    public View getMainView() {

        if(tv_main != null)
            return tv_main;

        tv_main = new AutoResizeTextView(getContext());
        tv_main.setMaxLines(1);
        //tv_main.setTextSize(22);
        tv_main.setTextColor(Color.WHITE);
        tv_main.setGravity(Gravity.CENTER);
        tv_main.setTextSize(400);
        tv_main.setShadowLayer(4, 0, 0, Color.BLACK);
        tv_main.setMaxLines(1);
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        params.gravity = Gravity.CENTER;
        tv_main.setLayoutParams(params);
//        if(getImageViewFlip()!=null)
//            getImageViewFlip().setVisibility(View.GONE);
        return tv_main;
    }

    public void setText(String text){

        if(tv_main!=null)
            tv_main.setText(text);
    }

    protected  void editText()
    {

    }

    public AutoResizeTextView getText(){
        if(tv_main!=null)
            return tv_main;

        return null;
    }

    public static float pixelsToSp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px/scaledDensity;
    }

    @Override
    protected void onScaling(boolean scaleUp) {
        super.onScaling(scaleUp);
    }
}
