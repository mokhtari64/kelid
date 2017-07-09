package ir.mehdi.kelid.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;

import java.io.File;
import java.util.Collection;
import java.util.Vector;

import ir.mehdi.kelid.Constant;
import ir.mehdi.kelid.KelidApplication;
import ir.mehdi.kelid.R;
import ir.mehdi.kelid.UserConfig;
import ir.mehdi.kelid.db.MySqliteOpenHelper;
import ir.mehdi.kelid.model.Property;
import ir.mehdi.kelid.ui.CardItemListAdapter;
import ir.mehdi.kelid.ui.MainActivity;

/**
 * Created by admin on 24/06/2017.
 */

public class UserProfileFragment  extends Fragment implements Constant {
    View main;
    ImageView userImage;
    Vector<Property> myKelidProperty;

    RecyclerView rateListview,myKelidListview,bookmarkListview;
    LayoutInflater inflater;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(main==null) {
            this.inflater=inflater;


            main = inflater.inflate(R.layout.fragment_userprofile, null);

            userImage= (ImageView) main.findViewById(R.id.userimage);
            if(UserConfig.userphoto!=null)
            {
                Bitmap bitmap = BitmapFactory.decodeFile(UserConfig.userphoto);
                userImage.setImageBitmap(bitmap);
            }

            CollapsingToolbarLayout collapsingToolbar =
                    (CollapsingToolbarLayout) main.findViewById(R.id.collapsing_toolbar);
            collapsingToolbar.setTitle("iman pourreza");
            main.findViewById(R.id.photo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getActivity()).showImageDIalog();
                }
            });
            rateListview= (RecyclerView) main.findViewById(R.id.rate_listview);
            myKelidListview= (RecyclerView) main.findViewById(R.id.mykelid_listview);
            bookmarkListview= (RecyclerView) main.findViewById(R.id.bookmark_listview);

            TabHost host = (TabHost)main.findViewById(R.id.tabHost);
            host.setup();

            //Tab 1
            TabHost.TabSpec spec = host.newTabSpec(getResources().getString(R.string.my_rate));
            spec.setContent(R.id.tab1);
            spec.setIndicator("", ContextCompat.getDrawable(getActivity(), R.drawable.my_advers));
            host.addTab(spec);

            //Tab 2
            spec = host.newTabSpec(getResources().getString(R.string.bookmark));
            spec.setContent(R.id.tab2);
            spec.setIndicator("", ContextCompat.getDrawable(getActivity(), R.drawable.bookmark));
            host.addTab(spec);

            //Tab 3
            spec =host.newTabSpec(getResources().getString(R.string.my_kelid));
            spec.setContent(R.id.tab3);
            spec.setIndicator("", ContextCompat.getDrawable(getActivity(), R.drawable.my_business_cards));

            host.addTab(spec);


        }
        myKelidProperty=new Vector<>();
        Collection<Property> values = MySqliteOpenHelper.getInstance().myPropertys.values();
        myKelidProperty.addAll(values);
        MykelidAdapter adapter=new MykelidAdapter();
        myKelidListview.setAdapter(adapter);

        return main;
    }

    public void changeImage(Bitmap bitmap, File jpg) {
        userImage.setImageBitmap(bitmap);
        SharedPreferences preferences = KelidApplication.applicationContext.getSharedPreferences("userconfing", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        UserConfig.userphoto=jpg.getAbsolutePath();
        edit.putString("userphoto", UserConfig.userphoto);
        edit.commit();
    }

    class MykelidAdapter extends RecyclerView.Adapter
    {



        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.myadver_item, parent, false);

//        view.setOnClickListener(Main2.myOnClickListener);

            MyKelidViewHoder myViewHolder = new MyKelidViewHoder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

//        @Override
//        public long getItemId(int position) {
//            return myKelidProperty.get(position).local_id;
//        }

        @Override
        public int getItemCount() {
            return 10;//myKelidProperty.size();
        }


    }
    class MyKelidViewHoder extends RecyclerView.ViewHolder
    {

        public MyKelidViewHoder(View itemView) {
            super(itemView);
        }
    }
}