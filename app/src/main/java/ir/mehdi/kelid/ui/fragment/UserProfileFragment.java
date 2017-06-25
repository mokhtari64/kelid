package ir.mehdi.kelid.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.mehdi.kelid.Constant;

/**
 * Created by admin on 24/06/2017.
 */

public class UserProfileFragment  extends Fragment implements Constant {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = new View(container.getContext());
        view.setBackgroundColor(Color.RED);
        return view;
    }
}