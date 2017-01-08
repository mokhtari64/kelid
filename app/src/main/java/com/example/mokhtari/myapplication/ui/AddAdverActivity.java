package com.example.mokhtari.myapplication.ui;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.example.mokhtari.myapplication.R;
import com.example.mokhtari.myapplication.db.Database;
import com.example.mokhtari.myapplication.model.PropertyDetail;

import java.util.Vector;

/**
 * Created by Mahdi on 06/22/2016.
 */
public class AddAdverActivity extends Activity {
    LinearLayout properyLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Database.getInstance().loadProperty();
        setContentView(R.layout.adver);
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
