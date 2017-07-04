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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;

import java.io.File;

import ir.mehdi.kelid.Constant;
import ir.mehdi.kelid.KelidApplication;
import ir.mehdi.kelid.R;
import ir.mehdi.kelid.UserConfig;
import ir.mehdi.kelid.ui.MainActivity;

/**
 * Created by admin on 24/06/2017.
 */

public class UserProfileFragment  extends Fragment implements Constant {
    View main;
    ImageView userImage;

    ListView rateListview,myKelidListview,bookmarkListview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(main==null) {


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
            rateListview= (ListView) main.findViewById(R.id.rate_listview);
            myKelidListview= (ListView) main.findViewById(R.id.mykelid_listview);
            bookmarkListview= (ListView) main.findViewById(R.id.bookmark_listview);

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
}