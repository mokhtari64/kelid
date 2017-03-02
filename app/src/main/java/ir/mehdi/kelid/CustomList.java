package ir.mehdi.kelid;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ir.mehdi.kelid.R;

import ir.mehdi.kelid.model.Node;

public class CustomList extends ArrayAdapter<String>{


    private final Activity context;

    private final Node[] mytext;
    //private final Integer[] imageId;


    @Override
    public int getCount() {
        return mytext.length;
    }

    //public CustomList(Activity context,String[] web, Integer[] imageId) {
    public CustomList(Activity context,Node[] mytext) {
        super(context, R.layout.list_item);
        this.context = context;
        this.mytext = mytext;
        //this.imageId = imageId;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.text);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.image);
        txtTitle.setText(mytext[position].toString());
        imageView.setImageResource(R.drawable.check_blue);
        //imageView.setImageResource(imageId[position]);
        return rowView;
    }

}