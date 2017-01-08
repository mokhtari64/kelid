package com.example.mokhtari.myapplication.ui;

/**
 * Created by Mahdi on 07/01/2016.
 */
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mokhtari.myapplication.R;

public class CustomSearchAdabter extends BaseAdapter {
    Context context;
    int flags[];
    String[] nameItem;
    LayoutInflater inflter;

    public CustomSearchAdabter(Context applicationContext, int[] flags, String[] nameItem) {
        this.context = applicationContext;
        this.flags = flags;
        this.nameItem = nameItem;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return flags.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.search_item, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView);
        icon.setImageResource(flags[i]);
        names.setText(this.nameItem[i]);
        return view;
    }
}