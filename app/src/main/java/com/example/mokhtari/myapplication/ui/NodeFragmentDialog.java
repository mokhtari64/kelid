package com.example.mokhtari.myapplication.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mokhtari.myapplication.R;
import com.example.mokhtari.myapplication.db.Database;
import com.example.mokhtari.myapplication.model.Node;
import com.example.mokhtari.myapplication.utils.Utils;

/**
 * Created by Iman on 6/23/2016.
 */
//public class NodeFragmentDialog extends DialogFragment {
//
////    static MainActivity activity;
//
//
//    public static class NodeFragmentSegment extends Fragment {
//
//        static NodeFragmentDialog parent;
//        private NodeListenter clickListener;
//        Node node=Database.getInstance().root;
//
//        public void setClickListener(NodeListenter clickListener) {
//            this.clickListener = clickListener;
//        }
//
//
//
//        @Nullable
//        @Override
//        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//
//            View view = inflater.inflate(R.layout.dialog_fragment_child, container, false);
//            LinearLayout content = (LinearLayout) view.findViewById(R.id.dialog_fragment_content);
//            TextView nodeheader = (TextView) view.findViewById(R.id.dialog_node_header);
//            nodeheader.setText(node.name);
//            for (Node n : node.childs) {
//
//                LinearLayout rowItem = (LinearLayout) inflater.inflate(R.layout.dialog_fragment_row, null);
//                TextView viewById = (TextView) rowItem.findViewById(R.id.dialog_fragment_row);
//                viewById.setText(n.name);
//                content.addView(rowItem);
//                if(n.childs.size()==0)
//                    rowItem.findViewById(R.id.dialog_fragment_row_next_image).setVisibility(View.GONE);
//                rowItem.setTag(n);
//                rowItem.setOnClickListener(parent.clickListener);
//
//
//            }
//            return view;
//        }
//
//        public void setNode(Node node) {
//            this.node = node;
//
//        }
//    }
//
//    void setFragment(Node node) {
//        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
//        NodeFragmentSegment nn = new NodeFragmentSegment();
//        nn.setNode(node);
//        nn.setClickListener(clickListener);
//        fragmentTransaction.replace(R.id.dialog_child_fragment1, nn);
//        fragmentTransaction.commit();
//
//
//    }
//
//    public NodeListenter clickListener = new NodeListenter();
//
//
//    class NodeListenter implements View.OnClickListener {
//        @Override
//        public void onClick(View v) {
//            Node n = (Node) v.getTag();
//            if (n.childs.size() > 0)
//                setFragment(n);
//        }
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        NodeFragmentSegment.parent=this;
//        View view = inflater.inflate(R.layout.dialog_fragment_layout, container, false);
//        getDialog().setTitle("test");
//        setFragment(Database.getInstance().root);
//        return view;
//    }
//}


public class NodeFragmentDialog extends DialogFragment {

//    static MainActivity activity;

    Node currentNode;
    private MainActivity activity;

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }

    public static class NodeFragmentSegment extends Fragment {

        static NodeFragmentDialog parent;
        private NodeListenter clickListener;
        Node node = Database.getInstance().root;

        public void setClickListener(NodeListenter clickListener) {
            this.clickListener = clickListener;
        }


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


            View view = inflater.inflate(R.layout.dialog_fragment_child, container, false);
            LinearLayout content = (LinearLayout) view.findViewById(R.id.dialog_fragment_content);
            TextView nodeheader = (TextView) view.findViewById(R.id.dialog_node_header);
            nodeheader.setText(node.path);
//            (node == DBAdapter.getInstance().root)?getResources().getString(R.string.dialog_title):
            if(node == Database.getInstance().root)
                nodeheader.setVisibility(View.GONE);

            for (Node n : node.childs) {

                LinearLayout rowItem = (LinearLayout) inflater.inflate(R.layout.dialog_fragment_row, null);
                TextView viewById = (TextView) rowItem.findViewById(R.id.dialog_fragment_row);
                viewById.setText(n.name);
                content.addView(rowItem);
                if (n.childs.size() == 0)
                    rowItem.findViewById(R.id.dialog_fragment_row_next_image).setVisibility(View.GONE);
                rowItem.setTag(n);
                rowItem.setOnClickListener(parent.clickListener);


            }
            return view;
        }

        public void setNode(Node node) {
            this.node = node;

        }
    }

    void setFragment(Node node) {
        currentNode = node;
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        NodeFragmentSegment nn = new NodeFragmentSegment();
        nn.setNode(node);
        nn.setClickListener(clickListener);
        if (node == Database.getInstance().root)
            fragmentTransaction.setCustomAnimations(R.anim.root_no_slide_in, R.anim.root_no_slide_in, R.anim.pop_slide_in, R.anim.pop_slide_out);
        else
            fragmentTransaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.pop_slide_in, R.anim.pop_slide_out);
        if (node.childs.size() > 0)
            fragmentTransaction.addToBackStack(node.name);
        fragmentTransaction.replace(R.id.dialog_child_fragment1, nn);
        fragmentTransaction.commit();


    }

    public NodeListenter clickListener = new NodeListenter();


    class NodeListenter implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Node n = (Node) v.getTag();
            if (n.childs.size() > 0)
                setFragment(n);
            else {
                getDialog().dismiss();
                getChildFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                Intent i = new Intent(activity, AddAdverActivity.class);
                startActivity(i);

//                Intent a = new Intent(getActivity(), CreateJobActivity.class);
//                a.putExtra("node_id",n.id);
//                startActivity(a);

            }
        }
    }

    public void setSize() {
        Window window = getDialog().getWindow();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = (int) (displaymetrics.widthPixels * 0.9);
        int height = Utils.getDPforPixel(activity,100);
        window.setLayout(width, height);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        getDialog().setTitle(getResources().getString(R.string.dialog_title));

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    return false;
                }
                if (keyCode == KeyEvent.KEYCODE_BACK) {//&& currentNode != DBAdapter.getInstance().root) {
//                    System.out.println("----------------back true");
//                    setFragment(currentNode.parent);
//                    return true;

//                    boolean a=getChildFragmentManager().popBackStackImmediate();
//                    return a;
                    if (getChildFragmentManager().getBackStackEntryCount() > 1) {
                        getChildFragmentManager().popBackStackImmediate();
                        return true;
                    }
                }

                return false;
            }
        });
        NodeFragmentSegment.parent = this;
        View view = inflater.inflate(R.layout.dialog_fragment_layout, container, false);

        setSize();
        setFragment(Database.getInstance().root);

        return view;
    }
}
