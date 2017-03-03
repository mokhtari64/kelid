package ir.mehdi.kelid.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ir.mehdi.kelid.model.Property;

/**
 * Created by Iman on 3/4/2017.
 */
public class KelidActivity extends  AppCompatActivity {


    public Handler mainHandler;


    public View.OnClickListener adversClickListner;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainHandler = new Handler(Looper.getMainLooper());
        adversClickListner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UserJob n = (UserJob) v.getTag();
//                showUserJob(n);

            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        } else {
            ViewCompat.setLayoutDirection(getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);
        }


    }

    protected void showUserJob(Property n) {
//        if (n.myjob != 1) {
//            UserJob property = MySqliteOpenHelper.getInstance().loadedUserJobs.get(n.remote_id);
//            if (property != null) {
//                n = property;
//
//            }
//        } else {
//            n.setLoaded(true);
//        }
//        Intent a = new Intent(FanoosActivity.this, UserJobDetailActivity.class);
//        a.putExtra("job", n);
//        startActivity(a);

    }


}

