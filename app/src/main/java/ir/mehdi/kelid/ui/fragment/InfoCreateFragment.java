package ir.mehdi.kelid.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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
    SupportMapFragment mapFragment;

    Marker lastMarker;
    private Object info;

    public void setActivity(AddPropetyActivity activity) {
        this.activity = activity;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(main==null) {
            layoutInflater = inflater;
            main= (LinearLayout) inflater.inflate(R.layout.fragment_create_info, null);
            mapFragment=(SupportMapFragment) getChildFragmentManager()    .findFragmentById(R.id.map);


            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    final GoogleMap mMap = googleMap;


                    LatLng sydney = new LatLng(36.28765407836474, 59.61174532771111);
                    CameraUpdate center =
                            CameraUpdateFactory.newLatLng(sydney);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);


                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(5), 5000, null);



                    mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            if (lastMarker != null)
                                lastMarker.remove();
                            CameraPosition cameraPosition = mMap.getCameraPosition();
                            lastMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("" + cameraPosition.zoom));


                        }
                    });
                    // Add a marker in Sydney and move the camera


//                mMap.addMarker(new MarkerOptions().position(sydney).title(""));
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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

    public void getInfo() {
//        return info;
    }

    public void reFill() {

    }
}
