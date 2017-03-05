package ir.mehdi.kelid.ui.fragment;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.mehdi.kelid.Constant;
import ir.mehdi.kelid.MainActivity;
import ir.mehdi.kelid.R;
import ir.mehdi.kelid.model.Node;
import ir.mehdi.kelid.ui.CardItemListAdapter;
import ir.mehdi.kelid.utils.Utils;

/**
 * Created by Iman on 3/2/2017.
 */
public class ListItemFragment extends Fragment implements Constant {

    Animation hide, show;
    boolean childeVisible=true;
    View childe;

    MainActivity activity;
    View mainContent;
    TextView nodePath;
    LinearLayout nodeChilde;

    ImageView nodeChildeController;

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
        mainContent = inflater.inflate(R.layout.card_list_layout, container, false);
        nodePath = (TextView) mainContent.findViewById(R.id.node_path);
        childe =  mainContent.findViewById(R.id.node_child_scroll);
        nodeChildeController = (ImageView) mainContent.findViewById(R.id.childe_controll);
        nodeChildeController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(childeVisible)
                {
                    childe.startAnimation(hide);
                    childeVisible=false;

                }else
                {
                    childe.startAnimation(show);
                    childeVisible=true;
                }
            }
        });
        nodeChilde = (LinearLayout) mainContent.findViewById(R.id.node_child);
        hide = AnimationUtils.loadAnimation(activity, R.anim.node_hide_anim);
        show = AnimationUtils.loadAnimation(activity, R.anim.node_show_anim);
        nodePath.setText(node.path);
        recyclerView = (RecyclerView) mainContent.findViewById(R.id.recycler);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, Utils.dpToPx(getContext(), 10), true));
        mainContent.setBackgroundColor(bg);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new CardItemListAdapter();
        recyclerView.setAdapter(adapter);

        return mainContent;
    }


//    public void setType(int type) {
//        this.type = type;
//    }

    public void setActivity(MainActivity activity) {
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

    /**
     * Converting dp to pixel
     */

}
