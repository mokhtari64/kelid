package ir.mehdi.kelid.ui.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import java.io.File;
import java.util.Vector;

import ir.mehdi.kelid.Constant;
import ir.mehdi.kelid.R;
import ir.mehdi.kelid.model.Property;
import ir.mehdi.kelid.service.ServiceDelegate;
import ir.mehdi.kelid.service.VolleyService;
import ir.mehdi.kelid.ui.AddPropetyActivity;
import ir.mehdi.kelid.utils.FileUtils;
import ir.mehdi.kelid.utils.Utils;

/**
 * Created by Iman on 5/23/2017.
 */

public class TestFragment extends Fragment implements Constant,ServiceDelegate {
    LinearLayout imageLayout,main;
    Button addImage,send;
    LayoutInflater layoutInflater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(main==null) {
            layoutInflater = inflater;
            main= (LinearLayout) inflater.inflate(R.layout.test_service_mohsen, null);
            imageLayout= (LinearLayout) main.findViewById(R.id.imagebutlayout);
            addImage= (Button) main.findViewById(R.id.add_image);
            send= (Button) main.findViewById(R.id.send);
            addImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((AddPropetyActivity)getActivity()).showImageDIalog();
                }
            });


        }
        return main;
    }

    public void setActivity(AddPropetyActivity addPropetyActivity) {
    }

    public void setUserJob(long userJobbId) {

    }

    public Property getProperty() {
        return null;
    }

    @Override
    public void onObjectReslut(int requestCode, int status, Property property, Object data) {

    }

    public void addImage(final String image) {
        if (image != null) {
            Bitmap bitmapOriginal = BitmapFactory.decodeFile(image);
            if (bitmapOriginal == null) {
                return;
            }
            final View view = layoutInflater.inflate(R.layout.collage_image_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView5);
            ImageView failedImage = (ImageView) view.findViewById(R.id.failed);
            final RadioButton radioButton = (RadioButton) view.findViewById(R.id.radioButton);
            radioButton.setTag(image);
            ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress);
            progressBar.setVisibility(View.VISIBLE);


            Bitmap bitmapsimplesize = Bitmap.createScaledBitmap(bitmapOriginal, (int) getResources().getDimension(R.dimen.advers_image_size), (int) getResources().getDimension(R.dimen.advers_image_size), true);
            bitmapOriginal.recycle();
            imageView.setImageBitmap(bitmapsimplesize);

            imageLayout.addView(view, Utils.dpToPx(getActivity(),100), Utils.dpToPx(getActivity(),100));
            VolleyService.getInstance().sendPhoto(progressBar,failedImage,image);


        }


    }


}
