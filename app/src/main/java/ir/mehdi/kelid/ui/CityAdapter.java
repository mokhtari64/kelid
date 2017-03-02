package ir.mehdi.kelid.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import ir.mehdi.kelid.R;
import ir.mehdi.kelid.model.City;


/**
 * Created by iman on 6/14/2016.
 */
public class CityAdapter extends ArrayAdapter<City> {
    private Context mContext;
    City[] items;
    LayoutInflater lInflater;



    public CityAdapter(Context context, int resource, City[] items) {
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
    public City getItem(int position) {
        return items[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.province_activity_listitem,parent,false);
        }



        City p = getItem(position);
        ((TextView)view).setText(p.name);
        return view;
    }


}
