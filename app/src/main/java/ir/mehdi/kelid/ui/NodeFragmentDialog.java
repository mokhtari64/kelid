package ir.mehdi.kelid.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.mehdi.kelid.R;
import ir.mehdi.kelid.db.DBAdapter;
import ir.mehdi.kelid.model.Node;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.util.DisplayMetrics;
import android.widget.ImageView;


/**
 * Created by Iman on 6/23/2016.
 */
public class NodeFragmentDialog extends DialogFragment {
    boolean onlydismis = false;
    boolean searchable = false;

//    EditText searchText;
//    ImageView search;
//    boolean searchMode = false;
    NodeDialogListener nodeDialogListener;
    public void setNodeDialogListener(NodeDialogListener nodeDialogListener) {
        this.nodeDialogListener = nodeDialogListener;
    }

    public interface NodeDialogListener {
        void setNode(Node n);
    }



    public void setOnlydismis(boolean onlydismis) {
        this.onlydismis = onlydismis;
    }

    public void setSearchable(boolean onlydismis) {
        this.searchable = onlydismis;
//        if (searchText != null)
//            searchText.setText("");
    }




    Node currentNode;

    public void setSize(Activity activity) {
        Window window = getDialog().getWindow();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = (int) (displaymetrics.widthPixels * 0.9);

        int height = getResources().getDimensionPixelSize(R.dimen.popup_width);
        window.setLayout(width, height);
    }

    void back() {
        getChildFragmentManager().popBackStackImmediate();
        currentNode = currentNode.parent;
    }

    public static class NodeFragmentSegment extends Fragment {

        static NodeFragmentDialog parent;

        Node node = DBAdapter.getInstance().root;
        Node nodes[];


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


            View view = inflater.inflate(R.layout.dialog_fragment_child, container, false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }

            LinearLayout content = (LinearLayout) view.findViewById(R.id.fragment_content);
            LinearLayout nodelay = (LinearLayout) view.findViewById(R.id.node);
            ImageView ok = (ImageView) view.findViewById(R.id.ok);

            TextView nodeheader = (TextView) view.findViewById(R.id.node_header);
            if (node != null) {
                nodeheader.setText(node.path);
            } else {
                nodeheader.setText(R.string.search);
            }

            if (node == DBAdapter.getInstance().root && !parent.searchable) {
                nodeheader.setVisibility(View.GONE);
                ok.setVisibility(View.GONE);
                nodelay.setVisibility(View.GONE);
            }

            if (parent.searchable) {
                if (node == DBAdapter.getInstance().root) {
                    nodeheader.setText(getString(R.string.all));
                }
                ok.setVisibility(View.VISIBLE);
                nodelay.setTag(node);
                nodelay.setOnClickListener(parent.clickListener);
            } else {
                ok.setVisibility(View.GONE);
            }
            if (node != null) {
                for (Node n : node.childs) {

                    LinearLayout rowItem = (LinearLayout) inflater.inflate(R.layout.nodedialog_fragment_row, null);
                    TextView viewById = (TextView) rowItem.findViewById(R.id.textView4);
                    viewById.setText(n.name);
                    content.addView(rowItem);
                    if (n.childs.size() == 0)
                        rowItem.findViewById(R.id.imageView3).setVisibility(View.GONE);
                    rowItem.setTag(n);
                    rowItem.setOnClickListener(parent.clickListener);


                }
            } else if (nodes != null) {

                for (Node n : nodes) {

                    LinearLayout rowItem = (LinearLayout) inflater.inflate(R.layout.nodedialog_fragment_row, null);
                    TextView viewById = (TextView) rowItem.findViewById(R.id.textView4);
                    viewById.setText(n.path);
                    content.addView(rowItem);
                    if (n.childs.size() == 0)
                        rowItem.findViewById(R.id.imageView3).setVisibility(View.GONE);
                    rowItem.setTag(n);
                    rowItem.setOnClickListener(parent.clickListener);


                }
            }
            return view;
        }


        public void setNode(Node node, Node[] nodes) {
            this.node = node;
            this.nodes = nodes;

        }
    }


    void setFragment(Node node) {
        currentNode = node;
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        NodeFragmentSegment nn = new NodeFragmentSegment();
        nn.setNode(node, null);

        if (node == DBAdapter.getInstance().root)
            fragmentTransaction.setCustomAnimations(R.anim.root_no_slide_in, R.anim.root_no_slide_in, R.anim.pop_slide_in, R.anim.pop_slide_out);
        else
            fragmentTransaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.pop_slide_in, R.anim.pop_slide_out);
        if (node.childs.size() > 0)
            fragmentTransaction.addToBackStack(node.name);
        fragmentTransaction.replace(R.id.dialog_child_fragment1, nn);
        fragmentTransaction.commit();
    }

    void setFragment(Node[] node) {
        currentNode = null;
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        NodeFragmentSegment nn = new NodeFragmentSegment();
        nn.setNode(null, node);


        fragmentTransaction.setCustomAnimations(R.anim.root_no_slide_in, R.anim.root_no_slide_in, R.anim.pop_slide_in, R.anim.pop_slide_out);
//        if (node.childs.size() > 0)
        fragmentTransaction.addToBackStack("search");
        fragmentTransaction.replace(R.id.dialog_child_fragment1, nn);
        fragmentTransaction.commit();
    }

    public NodeListenter clickListener = new NodeListenter();


    class NodeListenter implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Node n = (Node) v.getTag();
            if (n.childs.size() > 0) {
                if (currentNode == n) {
                    getDialog().dismiss();
                    getChildFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    if (nodeDialogListener != null) {
                        nodeDialogListener.setNode(n);
                    }

//                    FanoosActivity activity = (FanoosActivity) getActivity();
//                    activity.setNodeType(n);

                } else {
                    setFragment(n);
                }
            } else {
//                searchText.setText("");
                getDialog().dismiss();
                getChildFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                if (!onlydismis) {
                    Intent a = new Intent(getActivity(), AddPropetyActivity.class);
//                    UserConfig.loadLast().nodeid = n.id;
                    startActivity(a);
                } else {
                    if (nodeDialogListener != null) {
                        nodeDialogListener.setNode(n);
                    }
                }

            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        getDialog().setTitle(getResources().getString(R.string.dialog_title));
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    return false;
                }
                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    if (searchMode) {
//                        getChildFragmentManager().popBackStack(null, android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                        setFragment(DBAdapter.getInstance().root);
//                        currentNode = DBAdapter.getInstance().root;
//                        searchMode = false;
//                        searchText.setText("");
//                        return true;
//                    } else
                    if (getChildFragmentManager().getBackStackEntryCount() > 1) {
                        back();
                        return true;
                    }
                }

                return false;
            }
        });
        NodeFragmentSegment.parent = this;
        View view = inflater.inflate(R.layout.dialog_fragment_layout, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            view.findViewById(R.id.main_content).setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        ImageView back = (ImageView) view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getChildFragmentManager().getBackStackEntryCount() > 1) {
                    back();
                } else dismiss();
            }
        });
//        searchText = (EditText) view.findViewById(R.id.editText4);
//        search = (ImageView) view.findViewById(R.id.imageView7);
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (searchText.getText().toString().trim().length() > 0) {
//                    searchMode = true;
//                    Node[] searchNode = DBAdapter.getInstance().getSearchNode(searchText.getText().toString().trim());
//                    setFragment(searchNode);
//
//
//                }
//            }
//        });
        setFragment(DBAdapter.getInstance().root);
//        if (showAdver) {
//            if(adversFragmentSegment==null) {
//                 adversFragmentSegment = new AdversFragmentSegment();
//                NodeFragmentSegment.parent=this;
//            }
//
//            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
//            fragmentTransaction.setCustomAnimations(R.anim.root_no_slide_in, R.anim.root_no_slide_in, R.anim.pop_slide_in, R.anim.pop_slide_out);
//            fragmentTransaction.replace(R.id.dialog_child_fragment1, adversFragmentSegment);
//            fragmentTransaction.commit();
//        } else {
//
//        }
        return view;
    }
}
