package ir.mehdi.kelid.ui;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import ir.mehdi.kelid.R;
import ir.mehdi.kelid.db.Database;
import ir.mehdi.kelid.model.PropertyDetail;

import java.util.Vector;

/**
 * Created by Mahdi on 06/22/2016.
 */
public class AddPropetyActivity extends  KelidActivity {
    LinearLayout properyLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Database.getInstance().loadProperty();
        setContentView(R.layout.activity_add_property);
        properyLayout= (LinearLayout) findViewById(R.id.proprty_layout);
        Vector<PropertyDetail> pDetail = Database.getInstance().allNodes.get(1113100).pDetail;
        LinearLayout.LayoutParams  params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for(PropertyDetail a:pDetail)
        {
            ToggleButton aaa=new ToggleButton(this);
            aaa.setText(a.name);
            aaa.setTextOff(a.name);
            aaa.setTextOn(a.name);
            aaa.setBackgroundResource(R.drawable.my_toggle_background);
            aaa.setLayoutParams(params);
            properyLayout.addView(aaa);
        }


    }
}
