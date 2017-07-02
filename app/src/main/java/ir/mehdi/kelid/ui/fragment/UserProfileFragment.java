package ir.mehdi.kelid.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;

import java.io.File;

import ir.mehdi.kelid.Constant;
import ir.mehdi.kelid.R;
import ir.mehdi.kelid.ui.MainActivity;

/**
 * Created by admin on 24/06/2017.
 */

public class UserProfileFragment  extends Fragment implements Constant {
    View main;
    ImageView userImage;
    RecyclerView myKelidList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(main==null) {


            main = inflater.inflate(R.layout.fragment_userprofile, null);
            myKelidList= (RecyclerView) main.findViewById(R.id.my_kelid);
            userImage= (ImageView) main.findViewById(R.id.userimage);
            userImage.setImageResource(R.drawable.iman);
            CollapsingToolbarLayout collapsingToolbar =
                    (CollapsingToolbarLayout) main.findViewById(R.id.collapsing_toolbar);
            collapsingToolbar.setTitle("iman pourreza");
            main.findViewById(R.id.photo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getActivity()).showImageDIalog();
                }
            });

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
    }
}