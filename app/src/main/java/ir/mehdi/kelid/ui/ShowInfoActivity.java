package ir.mehdi.kelid.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import ir.mehdi.kelid.R;

public class ShowInfoActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    int propertyid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);
        mViewPager = (ViewPager) findViewById(R.id.container);
        Bundle extras = getIntent().getExtras();
        if(extras==null) {

//            Toast.makeText(this, "no select property", Toast.LENGTH_LONG).show();
//            finish();
        }else {
            propertyid = extras.getInt("propertyid");
        }
    }
}
