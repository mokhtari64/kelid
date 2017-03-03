package ir.mehdi.kelid.ui;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import ir.mehdi.kelid.R;

import java.io.File;

/**
 * Created by Mahdi on 07/05/2016.
 */
public class MyMain extends  KelidActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_main);


        final ToggleButton btn = (ToggleButton) findViewById(R.id.mymain_tg_btn);
        final Button tbtn = (Button) findViewById(R.id.buttonTst);
        final LinearLayout l1 = (LinearLayout) findViewById(R.id.ic1);
        final LinearLayout l2 = (LinearLayout) findViewById(R.id.ic2);
        final LinearLayout l3 = (LinearLayout) findViewById(R.id.ic3);
        final LinearLayout l4 = (LinearLayout) findViewById(R.id.mymain_ic4);
        final ImageView img1 = (ImageView) findViewById(R.id.mymain_img1);
        final ImageView img2 = (ImageView) findViewById(R.id.mymain_img2);
        final ImageView img3 = (ImageView) findViewById(R.id.mymain_img3);
        final ImageView img4 = (ImageView) findViewById(R.id.mymain_img4);
//        final ImageView img_main = (ImageView)findViewById(R.id.img_main);
        final TextView t1 = (TextView) findViewById(R.id.mymain_t1);
        final TextView t2 = (TextView) findViewById(R.id.mymain_t2);
        final TextView t3 = (TextView) findViewById(R.id.mymain_t3);
        final TextView t4 = (TextView) findViewById(R.id.mymain_t4);
        final Animation alpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        final Animation alpha_out = AnimationUtils.loadAnimation(this, R.anim.alpha_out);
        final Animation rotation = AnimationUtils.loadAnimation(this, R.anim.clockwise_rotation);
        final Animation rotation_out = AnimationUtils.loadAnimation(this, R.anim.unclockwise_rotation);


        final float orgPos1X = l1.getX();
        final float orgPos1Y = l1.getY();
        final float orgPos2X = l2.getX();
        final float orgPos2Y = l2.getY();
        final float orgPos3X = l3.getX();
        final float orgPos3Y = l3.getY();
        final float orgPos4X = l4.getX();
        final float orgPos4Y = l4.getY();

        tbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PackageManager pm = getPackageManager();
                    ApplicationInfo ai = pm.getApplicationInfo(getPackageName(), 0);
                    File srcFile = new File(ai.publicSourceDir);
                    Intent share = new Intent();
                    share.setAction(Intent.ACTION_SEND);
                    share.setType("application/vnd.android.package-archive");
                    share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(srcFile));
                    startActivity(Intent.createChooser(share, "MyApp"));
                } catch (Exception e) {
                    Log.e("ShareApp", e.getMessage());
                }
            }
        });


        assert btn != null;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn.isChecked()) {
                    l1.animate().translationX(orgPos1X - 200).setDuration(2000);
                    l2.animate().translationY(orgPos2Y - 66).setDuration(2000);
                    l2.animate().translationX(orgPos2X - 132).setDuration(2000);
                    l3.animate().translationY(orgPos3Y - 132).setDuration(2000);
                    l3.animate().translationX(orgPos3X - 66).setDuration(2000);
                    l4.animate().translationY(orgPos4Y - 200).setDuration(2000);
                    alpha.setFillAfter(true);
                    img1.startAnimation(alpha);
                    img2.startAnimation(alpha);
                    img3.startAnimation(alpha);
                    img4.startAnimation(alpha);
//                    rotation.setRepeatCount(Animation.INFINITE);
//                    rotation.setRepeatCount(0);
//                    img_main.startAnimation(rotation);
                    t1.postDelayed(new Runnable() {
                        public void run() {
                            t1.setVisibility(View.VISIBLE);
                            t3.setVisibility(View.VISIBLE);
                            t2.setVisibility(View.VISIBLE);
                            t4.setVisibility(View.VISIBLE);
//                            img2.setImageResource(R.drawable.khadamat);
//                            img3.setImageResource(R.drawable.moshavere);
//                            img4.setImageResource(R.drawable.daftar_amlak);
                            img1.setColorFilter(0xff33b5e5);
                            img2.setColorFilter(0xff99cc00);
                            img3.setColorFilter(0xffffbb33);
                            img4.setColorFilter(0xffff4444);
                        }
                    }, 2100);


                } else {
                    t1.setVisibility(View.INVISIBLE);
                    t2.setVisibility(View.INVISIBLE);
                    t3.setVisibility(View.INVISIBLE);
                    t4.setVisibility(View.INVISIBLE);
                    img1.clearColorFilter();
                    img2.clearColorFilter();
                    img3.clearColorFilter();
                    img4.clearColorFilter();
                    alpha_out.setFillAfter(true);
                    img1.startAnimation(alpha_out);
                    img2.startAnimation(alpha_out);
                    img3.startAnimation(alpha_out);
                    img4.startAnimation(alpha_out);
                    rotation_out.setRepeatCount(0);
//                    img_main.startAnimation(rotation_out);
                    l1.animate().translationY(orgPos1Y).setDuration(2000);
                    l1.animate().translationX(orgPos1X).setDuration(2000);
                    l2.animate().translationY(orgPos2Y).setDuration(2000);
                    l2.animate().translationX(orgPos2X).setDuration(2000);
                    l3.animate().translationY(orgPos3Y).setDuration(2000);
                    l3.animate().translationX(orgPos3X).setDuration(2000);
                    l4.animate().translationY(orgPos4Y).setDuration(2000);
                    l4.animate().translationX(orgPos4X).setDuration(2000);


                }


            }
        });
    }
}
