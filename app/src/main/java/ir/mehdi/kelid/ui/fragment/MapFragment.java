package ir.mehdi.kelid.ui.fragment;

import android.support.v4.app.Fragment;

import com.google.android.gms.maps.SupportMapFragment;

import ir.mehdi.kelid.Constant;
import ir.mehdi.kelid.service.ServiceDelegate;

/**
 * Created by Iman on 5/29/2017.
 */

public class MapFragment  extends SupportMapFragment implements Constant,ServiceDelegate {
    @Override
    public void onObjectReslut(int requestCode, int status, Object requestObject, Object responseObject) {

    }


}
