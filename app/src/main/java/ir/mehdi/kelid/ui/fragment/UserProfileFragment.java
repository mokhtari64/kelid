package ir.mehdi.kelid.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;

import ir.mehdi.kelid.Constant;
import ir.mehdi.kelid.R;
import ir.mehdi.kelid.ui.AddPropetyActivity;
import ir.mehdi.kelid.ui.MainActivity;

/**
 * Created by admin on 24/06/2017.
 */

public class UserProfileFragment  extends Fragment implements Constant {
    View main;
    ImageView userImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(main==null) {


            main = inflater.inflate(R.layout.fragment_userprofile, null);
//            final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//            setSupportActionBar(toolbar);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        }

        return main;
    }

    public void changeImage(Bitmap bitmap, File jpg) {
        userImage.setImageBitmap(bitmap);
    }
}