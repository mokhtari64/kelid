package ir.mehdi.kelid.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import ir.mehdi.kelid.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends Activity {
    private static int SPLASH_TIME_OUT = 2000;
    boolean start = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if(UserConfig.city!=-1) {
//
                    Intent i = new Intent(SplashActivity.this, OtherMainActivity.class);
                    startActivity(i);
                finish();
//                }else
//                {
//                    Intent i = new Intent(SplashActivity.this, CityActivity.class);
//                    startActivity(i);
//                }
//                finish();
            }
        }, SPLASH_TIME_OUT);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        start = false;
//        Intent i = new Intent(SplashActivity.this, OtherMainActivity.class);
//        startActivity(i);
    }


}
