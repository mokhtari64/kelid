package ir.mehdi.kelid.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.mehdi.kelid.Constant;
import ir.mehdi.kelid.R;
import ir.mehdi.kelid.model.Property;
import ir.mehdi.kelid.ui.AddPropetyActivity;

/**
 * Created by Iman on 5/23/2017.
 */

public class TestFragment extends Fragment implements Constant {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_property, null);
    }

    public void setActivity(AddPropetyActivity addPropetyActivity) {
    }

    public void setUserJob(long userJobbId) {

    }

    public Property getProperty() {
        return null;
    }
}
