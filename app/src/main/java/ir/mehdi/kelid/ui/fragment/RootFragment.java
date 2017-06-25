package ir.mehdi.kelid.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import ir.mehdi.kelid.Constant;
import ir.mehdi.kelid.Main2;
import ir.mehdi.kelid.model.Node;

/**
 * Created by Iman on 3/5/2017.
 */
public class RootFragment extends Fragment implements Constant {
    static Main2 activity;
    int fragmentrootid,layoutResId;
    View.OnClickListener itemClickListener;

     View.OnClickListener nodeItemClickListener;



    FrameLayout view;
    private Node node;



    int bg;


    public void setParameter(Main2 activity, Node node, int color, int layoutid, int fragmentid, View.OnClickListener itemClickListener) {
        this.activity = activity;
        this.node = node;
        this.bg = color;
        layoutResId=layoutid;
        fragmentrootid=fragmentid;
        this.itemClickListener=itemClickListener;


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (nodeItemClickListener == null) {
            nodeItemClickListener=new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Node tag = (Node) view.getTag();
                    setFragment(tag);
                }
            };

        }

        if (view == null) {
            view = (FrameLayout) inflater.inflate(layoutResId, container, false);
//            switch (node.fragmentrootid) {
//                case PROPERTY:
//                    view = (FrameLayout) inflater.inflate(R.layout.property_root_fragment, container, false);
//                    fragmentrootid = R.fragmentrootid.property_frg;
//                    itemClickListener = propertyItemClickListener;
//                    break;
//                case CONSULTING:
//                    view = (FrameLayout) inflater.inflate(R.layout.consulating_root_fragment, container, false);
//                    fragmentrootid = R.fragmentrootid.consulate_frg;
//                    itemClickListener = consulateItemClickListener;
//                    break;
//                case OFFICE:
//                    view = (FrameLayout) inflater.inflate(R.layout.office_root_fragment, container, false);
//                    fragmentrootid = R.fragmentrootid.office_frg;
//                    itemClickListener = officeItemClickListener;
//                    break;
//                case SERVICE:
//                    view = (FrameLayout) inflater.inflate(R.layout.serviec_root_fragment, container, false);
//                    fragmentrootid = R.fragmentrootid.service_frg;
//                    itemClickListener = serviceItemClickListener;
//                    break;
//            }
            view.setBackgroundColor(bg);
        }
        setFragment(node);
        return view;
    }

    public void setFragment(Node node) {
        ListItemFragment fragment = new ListItemFragment();
        fragment.setClickListener(nodeItemClickListener, itemClickListener);

        fragment.setActivity(activity);
        fragment.setNode(node);
        fragment.setBackGroundColor(bg);
        setFragment(fragment, node);
    }


    private void setFragment(Fragment fragment, Node n) {
        try {
            android.support.v4.app.FragmentManager fm = activity.getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
            ft.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            long l = System.currentTimeMillis();

            android.support.v4.app.FragmentTransaction fragmentTransaction = ft.replace(fragmentrootid, fragment, "" + l);
            if (n.id != PROPERTY && n.id != OFFICE && n.id != SERVICE && n.id != CONSULTING)
                fragmentTransaction.addToBackStack("" + l);
            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}