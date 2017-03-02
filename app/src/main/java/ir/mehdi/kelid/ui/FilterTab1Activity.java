package ir.mehdi.kelid.ui;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import ir.mehdi.kelid.R;
import ir.mehdi.kelid.utils.ChangeHue;

/**
 * Created by Mahdi on 06/20/2016.
 */
public class FilterTab1Activity extends Activity
{

    ImageView mainIC1;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_property);

        mainIC1 = (ImageView) findViewById(R.id.filter_ic1);
        mainIC1.setImageBitmap(ChangeHue.applyHueFilterEffect(BitmapFactory.decodeResource(getResources(), R.drawable.amlak), 2 ,360));

//        TextView tv=new TextView(this);
//        tv.setTextSize(25);
//        tv.setGravity(Gravity.CENTER_VERTICAL);
//        tv.setText("This Is Tab1 Activity");
//        setContentView(tv);





    }
}