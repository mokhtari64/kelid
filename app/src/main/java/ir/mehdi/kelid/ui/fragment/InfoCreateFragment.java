package ir.mehdi.kelid.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import ir.mehdi.kelid.Constant;
import ir.mehdi.kelid.R;
import ir.mehdi.kelid.service.ServiceDelegate;
import ir.mehdi.kelid.ui.AddPropetyActivity;

/**
 * Created by Iman on 5/29/2017.
 */

public class InfoCreateFragment  extends Fragment implements Constant,ServiceDelegate {
    AddPropetyActivity activity;
    LinearLayout imageLayout,main;
    LayoutInflater layoutInflater;
    Button addImage,send;

    public void setActivity(AddPropetyActivity activity) {
        this.activity = activity;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(main==null) {
            layoutInflater = inflater;
            main= (LinearLayout) inflater.inflate(R.layout.fragment_create_info, null);
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

    @Override
    public void onObjectReslut(int requestCode, int status, Object requestObject, Object responseObject) {

    }
    public boolean isValid() {
        return true;
    }
}
