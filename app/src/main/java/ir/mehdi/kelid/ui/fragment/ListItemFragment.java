package ir.mehdi.kelid.ui.fragment;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ir.mehdi.kelid.Const;
import ir.mehdi.kelid.Constant;
import ir.mehdi.kelid.Main2;
import ir.mehdi.kelid.R;
import ir.mehdi.kelid.model.Node;
import ir.mehdi.kelid.ui.CardItemListAdapter;
import ir.mehdi.kelid.utils.Utils;

/**
 * Created by Iman on 3/2/2017.
 */
public class ListItemFragment extends Fragment implements Constant {


    View.OnClickListener nodeClickListener, itemClickListener;


    public void setClickListener(View.OnClickListener nodeClickListener, View.OnClickListener itemClickListener) {
        this.nodeClickListener = nodeClickListener;
        this.itemClickListener = itemClickListener;
    }

    boolean childeVisible = true;
    View childe;

    Main2 activity;
    View mainContent;
    TextView nodePath;
    LinearLayout nodeChilde;
    RelativeLayout child_layer;
    RotateAnimation r;
    ImageView nodeChildeController,bar;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    private int type;
    private Node node;

    int bg;

    public void setBackGroundColor(int bg) {
        this.bg = bg;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mainContent == null) {
            mainContent = inflater.inflate(R.layout.card_list_layout, container, false);
            nodePath = (TextView) mainContent.findViewById(R.id.node_path);
            childe = mainContent.findViewById(R.id.node_child_scroll);
            nodeChildeController = (ImageView) mainContent.findViewById(R.id.childe_controll);
            child_layer = (RelativeLayout) mainContent.findViewById(R.id.chlid_btn);
            bar = (ImageView) mainContent.findViewById(R.id.chlid_btn2);


            nodeChilde = (LinearLayout) mainContent.findViewById(R.id.node_child);
//            bar.setColorFilter(R.color.mRedDark, PorterDuff.Mode.SRC_OVER );
            if (node != null && node.childs != null && node.childs.size() > 0) {
                childe.setVisibility(View.VISIBLE);

                child_layer.setVisibility(View.VISIBLE);
                child_layer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (childeVisible) {
                            int height = childe.getHeight();
                            Utils.ChangeColorFilter(bar, getResources().getColor(R.color.mRedLight), getResources().getColor(R.color.mRedDark));
                            childe.animate().translationY(-height).setDuration(Const.AnimDuration);
                            child_layer.animate().translationY(-height).setDuration(Const.AnimDuration);
                            nodeChildeController.animate().rotation(180).setDuration(Const.AnimDuration);

                            childeVisible = false;

                        } else {
                            int height = childe.getHeight();
                            Utils.ChangeColorFilter(bar, getResources().getColor(R.color.mRedDark), getResources().getColor(R.color.mRedLight));
                            childe.animate().translationY(0).setDuration(Const.AnimDuration);
                            child_layer.animate().translationY(0).setDuration(Const.AnimDuration);
                            nodeChildeController.animate().rotation(0).setDuration(Const.AnimDuration);
                            childeVisible = true;
                        }
                    }
                });
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.bottomMargin = 10;
                params.topMargin = 10;
                params.rightMargin = 10;
                params.leftMargin = 10;

                for (int i = 0; i < node.childs.size(); i++) {
                    Node node = this.node.childs.get(i);
                    TextView t = new TextView(getContext());
                    t.setPadding(50, 50, 50, 50);
                    t.setTag(node);
                    t.setOnClickListener(nodeClickListener);
                    t.setText(node.name);
                    t.setLayoutParams(params);
                    t.setBackgroundColor(Color.WHITE);
                    t.setGravity(Gravity.CENTER);
                    nodeChilde.addView(t);
                }

            } else {
                childe.setVisibility(View.GONE);
                child_layer.setVisibility(View.GONE);


            }
            if (node.level == 0) {
                nodePath.setVisibility(View.GONE);
            } else {
                nodePath.setVisibility(View.VISIBLE);
                nodePath.setText(node.path);
            }
            recyclerView = (RecyclerView) mainContent.findViewById(R.id.recycler);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, Utils.dpToPx(getContext(), 10), true));
            mainContent.setBackgroundColor(bg);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            adapter = new CardItemListAdapter(itemClickListener);
            recyclerView.setAdapter(adapter);
        }
        return mainContent;
    }


    public void setActivity(Main2 activity) {
        this.activity = activity;
    }

    public void setNode(Node node) {
        this.node = node;
    }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }



}
