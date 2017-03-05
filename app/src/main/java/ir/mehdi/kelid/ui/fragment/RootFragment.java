package ir.mehdi.kelid.ui.fragment;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import ir.mehdi.kelid.Constant;
import ir.mehdi.kelid.MainActivity;
import ir.mehdi.kelid.R;
import ir.mehdi.kelid.model.Node;

/**
 * Created by Iman on 3/5/2017.
 */
public class RootFragment extends Fragment implements Constant {
    static MainActivity activity;
    int id;


    FrameLayout view;
    private Node node;


    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }

    boolean first = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(view==null)
        {
//            view = (FrameLayout) inflater.inflate(R.layout.property_root_fragment, container, false);
            switch (node.id)
            {
                case PROPERTY:
                    view = (FrameLayout) inflater.inflate(R.layout.property_root_fragment, container, false);
                    id=R.id.property_frg;

                    break;
                case CONSULTING:
                    view = (FrameLayout) inflater.inflate(R.layout.consulating_root_fragment, container, false);
                    id=R.id.consulate_frg;
                    break;
                case OFFICE:
                    view = (FrameLayout) inflater.inflate(R.layout.office_root_fragment, container, false);
                    id=R.id.office_frg;
                    break;
                case SERVICE:
                    view = (FrameLayout) inflater.inflate(R.layout.serviec_root_fragment, container, false);
                    id=R.id.service_frg;
                    break;
            }
//            id= (int) System.currentTimeMillis();

//            id=view.getChildAt(0).getId();

//            RelativeLayout relativeLayout=new RelativeLayout(activity);
//            relativeLayout.setId(id);
//            view.addView(relativeLayout,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            view.setBackgroundColor(bg);
        }
        setFragment(node);

//        if (first) {
//
//            if (node == null)
//                activity.setFragment(DBAdapter.getInstance().root, first);
//            else
//                activity.setFragment(node, first);
//            first = false;
//        }


        return view;
    }

    public void setFragment(Node node) {
        ListItemFragment fragment = new ListItemFragment();
        fragment.setActivity(activity);
        fragment.setNode(node);
        fragment.setBackGroundColor(bg);
        setFragment(fragment);
    }


    private void setFragment(Fragment fragment) {
        try {
            android.support.v4.app.FragmentManager fm = activity.getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
            ft.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            long l = System.currentTimeMillis();
            fragment.isAdded();
            android.support.v4.app.FragmentTransaction fragmentTransaction = ft.replace(id, fragment, "" + l);
            fragmentTransaction.addToBackStack("" + l);

            ft.commit();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setNode(Node node) {
        this.node = node;
    }
    int bg;
    public void setBackGroundColor(int bg)
    {
        this.bg=bg;



    }
}