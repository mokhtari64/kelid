package ir.mehdi.kelid;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ToggleButton;

import ir.mehdi.kelid.R;


public class OtherMain2Activity extends AppCompatActivity {

    Animation alpha;
    Animation alpha_out;
    Animation rotation;
    Animation rotation_out;
    int screenWidth,dtime,orgPos1X,sw;
    ImageView btn_1,btn_2,btn_3,btn_4;
    ToggleButton btn_center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenWidth = getScreenWidth(OtherMain2Activity.this);
        setContentView(R.layout.activity_main);
        btn_1 = (ImageView) findViewById(R.id.btn_1);
        btn_2 = (ImageView) findViewById(R.id.btn_2);
        btn_3 = (ImageView) findViewById(R.id.btn_3);
        btn_4 = (ImageView) findViewById(R.id.btn_4);
        btn_4.setVisibility(View.INVISIBLE);
        btn_center = (ToggleButton) findViewById(R.id.btn_center);
        alpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        alpha_out = AnimationUtils.loadAnimation(this, R.anim.alpha_out);
        rotation = AnimationUtils.loadAnimation(this, R.anim.clockwise_rotation);
        rotation_out = AnimationUtils.loadAnimation(this, R.anim.unclockwise_rotation);
        dtime = 500;
        sw=200;
//        btn_2.setX(orgPos1X - screenWidth);

        assert btn_center != null;
        btn_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_center.isChecked()) {
                    btn_center.setClickable(false);
//                    setting_layer.setX(orgPos1X - screenWidth );

                    rotation.setRepeatCount(Animation.INFINITE);
                    rotation.setRepeatCount(0);
                    btn_4.startAnimation(rotation);
                    btn_4.setVisibility(View.VISIBLE);
/*                    RotateAnimation an = new RotateAnimation(30, 90,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    an.setDuration(500);               // duration in ms
                    an.setRepeatCount(0);                // -1 = infinite repeated
                    an.setRepeatMode(Animation.REVERSE); // reverses each repeat
                    an.setFillAfter(true);
                    btn_2.setAnimation(an);*/
//                    btn_2.animate().rotationBy(90);
//                    settingbtn.setHighlightColor(0xff33b5e5);
//                    btn_2.animate().translationX(btn_2.getX() + sw).setDuration(dtime);
//                    btn_center.animate().alpha((float) 0.3).setDuration(dtime);

//                    recyclerView.animate().translationX(screenWidth).setDuration(dtime);
//                    setting_layer.setVisibility(View.VISIBLE);
//                    recyclerView.setVisibility(View.GONE);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
//                            btn_center.setEnabled(false);
                            btn_center.setClickable(true);
                        }
                    }, dtime);


                } else {
                    closeMenu();
                }
            }
        });


    }

    void closeMenu() {
        btn_center.setChecked(false);
        btn_center.setClickable(false);
//        btn_2.animate().rotationBy(-90);
        btn_4.startAnimation(rotation_out);
//        btn_2.animate().translationX(orgPos1X - screenWidth).setDuration(dtime);
//                    recyclerView.animate().translationX(orgPos1X).setDuration(dtime);
//                    setting_layer.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
//        btn_center.setEnabled(false);
        handler.postDelayed(new Runnable() {
            public void run() {
                btn_center.setClickable(true);
            }
        }, dtime);


    }


    public  static int getScreenWidth(Activity activity)
    {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        return width;
    }
}
