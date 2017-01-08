package com.example.mokhtari.myapplication.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mokhtari.myapplication.R;
import com.example.mokhtari.myapplication.model.*;

/**
 * Created by iman on 6/14/2016.
 */
public class ProvinceAdapter extends ArrayAdapter<Province> {
    private Context mContext;
    Province[] items;
    LayoutInflater lInflater;



    public ProvinceAdapter(Context context, int resource, Province[] items) {
        super(context, resource);
        this.mContext = context;
        this.items = items;

        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Province getItem(int position) {
        return items[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.province_activity_listitem,parent,false);
        }



        Province p = getItem(position);
        ((TextView)view).setText(p.name);
        return view;
    }


}
