package ir.mehdi.kelid.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import ir.mehdi.kelid.R;
import ir.mehdi.kelid.db.Database;
import ir.mehdi.kelid.model.Node;


public class NodeFragment extends Fragment {
    static OtherMainActivity activity;
    Node node;

    int searchimgWidth=-1;
    public void setNode(Node node) {
        this.node = node;

    }

    public static View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Node n = (Node) v.getTag();
            if (n.childs.size() > 0)
                activity.setFragment(n, false);
        }
    };

    public void setActivity(OtherMainActivity activity) {
        NodeFragment.activity = activity;
    }

    private OnItemSelectedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        if (node == null)
            return view;
        if (node == Database.getInstance().root) {
            view = inflater.inflate(R.layout.root_fragment,
                    container, false);




            final ToggleButton btn = (ToggleButton) view.findViewById(R.id.mymain_tg_btn);
            final LinearLayout l1 = (LinearLayout) view.findViewById(R.id.ic1);
            final LinearLayout l2 = (LinearLayout) view.findViewById(R.id.ic2);
            final LinearLayout l3 = (LinearLayout) view.findViewById(R.id.ic3);
            final LinearLayout l4 = (LinearLayout) view.findViewById(R.id.ic4);
            final LinearLayout l5 = (LinearLayout) view.findViewById(R.id.ic5);
            final ImageView img1 = (ImageView)view.findViewById(R.id.mymain_img1);
            final ImageView img2 = (ImageView)view.findViewById(R.id.mymain_img2);
            final ImageView img3 = (ImageView)view.findViewById(R.id.mymain_img3);
            final ImageView img4 = (ImageView)view.findViewById(R.id.mymain_img4);
            final ImageView img5 = (ImageView)view.findViewById(R.id.mymain_img5);
            final ImageView searchimg = (ImageView)view.findViewById(R.id.searchimg2);
            final ImageView img_main = (ImageView)view.findViewById(R.id.img_main);
            final TextView t1=(TextView)view.findViewById(R.id.t1);
            final TextView t2=(TextView)view.findViewById(R.id.t2);
            final TextView t3=(TextView)view.findViewById(R.id.t3);
            final TextView t4=(TextView)view.findViewById(R.id.t4);
            final TextView t5=(TextView)view.findViewById(R.id.t5);
            final Animation alpha = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
            final Animation alpha_out = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_out);
            final Animation rotation = AnimationUtils.loadAnimation(getActivity(), R.anim.clockwise_rotation);
            final Animation rotation_out = AnimationUtils.loadAnimation(getActivity(), R.anim.unclockwise_rotation);



            final float orgPos1X = l1.getX();
            final float orgPos1Y = l1.getY();
            final float orgPos2X = l2.getX();
            final float orgPos2Y = l2.getY();
            final float orgPos3X = l3.getX();
            final float orgPos3Y = l3.getY();
            final float orgPos4X = l4.getX();
            final float orgPos4Y = l4.getY();
            final float orgPos5X = l5.getX();
            final float orgPos5Y = l5.getY();
//            final float orgSearchImg = searchimg.getX();
//            final float orgSearchImgW = searchimg.getWidth();

            final ViewTreeObserver vto = searchimg.getViewTreeObserver();
            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {
                    // Remove after the first run so it doesn't fire forever
                    searchimg.getViewTreeObserver().removeOnPreDrawListener(this);
                    setSearchImageWidth(searchimg.getMeasuredWidth());
//                    finalHeight = iv.getMeasuredHeight();
                    searchimg.setX(- searchimg.getMeasuredWidth());
//                    vto.removeOnPreDrawListener(this);
//                    tv.setText("Height: " + finalHeight + " Width: " + finalWidth);
                    return true;
                }
            });
/*            Display display = getWindowManager().getDefaultDisplay();
            int width = display.getWidth();  // deprecated
            int height = display.getHeight();*/

//            searchimg.getLayoutParams().width = getApplicationContext().getResources().getDisplayMetrics().widthPixels *3/8;





//
            img1.setColorFilter( 0xff33b5e5 );
            img2.setColorFilter( 0xff99cc00 );
            img3.setColorFilter( 0xffffbb33 );
            img4.setColorFilter( 0xffff4444 );
            img5.setColorFilter( 0xffff4400 );
            img1.setVisibility(View.INVISIBLE);
            img2.setVisibility(View.INVISIBLE);
            img3.setVisibility(View.INVISIBLE);
            img4.setVisibility(View.INVISIBLE);
            img5.setVisibility(View.INVISIBLE);




            assert btn != null;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btn.isChecked()) {
                        l1.animate().translationX(orgPos1X + TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics())).setDuration(2000);
//                        l2.animate().translationY(orgPos2Y + TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 66, getResources().getDisplayMetrics())).setDuration(2000);
                        l2.animate().translationX(orgPos2X + TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -50, getResources().getDisplayMetrics())).setDuration(2000);
//                        l3.animate().translationY(orgPos3Y + TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 132, getResources().getDisplayMetrics())).setDuration(2000);
                        l3.animate().translationX(orgPos3X + TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics())).setDuration(2000);
                        l4.animate().translationX(orgPos4X + TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -50, getResources().getDisplayMetrics())).setDuration(2000);
                        l5.animate().translationX(orgPos5X + TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics())).setDuration(2000);
//                        RelativeLayout.LayoutParams aa=( RelativeLayout.LayoutParams)searchimg.getLayoutParams();
//                        aa.width=-1;
//                        searchimg.setLayoutParams(aa);
//                        l4.animate().translationY(orgPos4Y + TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics())).setDuration(2000);
                        searchimg.animate().translationX(0).setDuration(2000);
                        alpha .setFillAfter(true);
                        img1.startAnimation(alpha);
                        img2.startAnimation(alpha);
                        img3.startAnimation(alpha);
                        img4.startAnimation(alpha);
                        img5.startAnimation(alpha);
//                    rotation.setRepeatCount(Animation.INFINITE);
//                        rotation.setRepeatCount(0);
//                        img_main.startAnimation(rotation);
                        t1.postDelayed(new Runnable() {
                            public void run() {
                                t1.setVisibility(View.VISIBLE);
                                t3.setVisibility(View.VISIBLE);
                                t2.setVisibility(View.VISIBLE);
                                t4.setVisibility(View.VISIBLE);
                                t5.setVisibility(View.VISIBLE);
//                            img2.setImageResource(R.drawable.khadamat);
//                            img3.setImageResource(R.drawable.moshavere);
//                            img4.setImageResource(R.drawable.daftar_amlak);

                            }
                        }, 2100);


                    } else {
                        t1.setVisibility(View.INVISIBLE);
                        t2.setVisibility(View.INVISIBLE);
                        t3.setVisibility(View.INVISIBLE);
                        t4.setVisibility(View.INVISIBLE);
                        t5.setVisibility(View.INVISIBLE);
//                        img1.clearColorFilter();
//                        img2.clearColorFilter();
//                        img3.clearColorFilter();
//                        img4.clearColorFilter();
                        alpha_out .setFillAfter(true);
                        img1.startAnimation(alpha_out);
                        img2.startAnimation(alpha_out);
                        img3.startAnimation(alpha_out);
                        img4.startAnimation(alpha_out);
                        img5.startAnimation(alpha_out);
//                        rotation_out.setRepeatCount(0);
//                        img_main.startAnimation(rotation_out);
                        l1.animate().translationY(orgPos1Y).setDuration(2000);
                        l1.animate().translationX(orgPos1X).setDuration(2000);
                        l2.animate().translationY(orgPos2Y).setDuration(2000);
                        l2.animate().translationX(orgPos2X).setDuration(2000);
                        l3.animate().translationY(orgPos3Y).setDuration(2000);
                        l3.animate().translationX(orgPos3X).setDuration(2000);
                        l4.animate().translationY(orgPos4Y).setDuration(2000);
                        l4.animate().translationX(orgPos4X).setDuration(2000);
                        l5.animate().translationY(orgPos5Y).setDuration(2000);
                        l5.animate().translationX(orgPos5X).setDuration(2000);
                        searchimg.animate().translationX(0-searchimgWidth).setDuration(2000);


                    }




                }
            });



//            ((TextView)view.findViewById(R.id.tc_1)).setText(Database.getInstance().allNodes.get(1000000).name);
//            ((TextView)view.findViewById(R.id.tc_2)).setText(Database.getInstance().allNodes.get(2000000).name);
//            ((TextView)view.findViewById(R.id.tc_3)).setText(Database.getInstance().allNodes.get(3000000).name);
//            ((TextView)view.findViewById(R.id.tc_4)).setText(Database.getInstance().allNodes.get(4000000).name);
//            ((TextView)view.findViewById(R.id.tc_5)).setText(Database.getInstance().allNodes.get(5000000).name);
//            ((TextView)view.findViewById(R.id.tc_6)).setText(Database.getInstance().allNodes.get(6000000).name);
//            ((TextView)view.findViewById(R.id.tc_7)).setText(Database.getInstance().allNodes.get(7000000).name);
//            ((TextView)view.findViewById(R.id.tc_8)).setText(Database.getInstance().allNodes.get(8000000).name);
//            ((TextView)view.findViewById(R.id.tc_9)).setText(Database.getInstance().allNodes.get(9000000).name);
//            ((TextView)view.findViewById(R.id.tc_10)).setText(Database.getInstance().allNodes.get(1100000).name);
            view.findViewById(R.id.ic1).setTag(Database.getInstance().allNodes.get(1000000));
            view.findViewById(R.id.ic2).setTag(Database.getInstance().allNodes.get(3000000));
            view.findViewById(R.id.ic3).setTag(Database.getInstance().allNodes.get(4000000));
            view.findViewById(R.id.ic4).setTag(Database.getInstance().allNodes.get(2000000));
            view.findViewById(R.id.ic5).setTag(Database.getInstance().allNodes.get(5000000));
//            view.findViewById(R.id.ic_6).setTag(Database.getInstance().allNodes.get(6000000));
//            view.findViewById(R.id.ic_7).setTag(Database.getInstance().allNodes.get(7000000));
//            view.findViewById(R.id.ic_8).setTag(Database.getInstance().allNodes.get(8000000));
//            view.findViewById(R.id.ic_9).setTag(Database.getInstance().allNodes.get(9000000));
//            view.findViewById(R.id.ic_10).setTag(Database.getInstance().allNodes.get(1100000));
            view.findViewById(R.id.ic1).setOnClickListener(clickListener);
            view.findViewById(R.id.ic2).setOnClickListener(clickListener);
            view.findViewById(R.id.ic3).setOnClickListener(clickListener);
            view.findViewById(R.id.ic4).setOnClickListener(clickListener);
            view.findViewById(R.id.ic5).setOnClickListener(clickListener);
//            view.findViewById(R.id.ic_6).setOnClickListener(clickListener);
//            view.findViewById(R.id.ic_7).setOnClickListener(clickListener);
//            view.findViewById(R.id.ic_8).setOnClickListener(clickListener);
//            view.findViewById(R.id.ic_9).setOnClickListener(clickListener);
//            view.findViewById(R.id.ic_10).setOnClickListener(clickListener);

        } else
        {

            view = inflater.inflate(R.layout.main_fragment_content,
                    container, false);
            LinearLayout content= (LinearLayout) view.findViewById(R.id.dialog_fragment_content);
            TextView nodeheader= (TextView) view.findViewById(R.id.dialog_node_header);
            nodeheader.setText(node.path);
//            RelativeLayout.LayoutParams p=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//            LinearLayout content = new LinearLayout(getActivity());//(LinearLayout) view.findViewById(R.id.fragment_content);
//            content.setOrientation(LinearLayout.VERTICAL);
//            view=content;
//            content.setLayoutParams(p);
//            LinearLayout.LayoutParams rowParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.getDPforPixel(activity, 30));
//            rowParam.setMargins(Utils.getDPforPixel(activity, 3), Utils.getDPforPixel(activity, 3), Utils.getDPforPixel(activity, 3), Utils.getDPforPixel(activity, 3));
//            LinearLayout.LayoutParams rowParam2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
//            LinearLayout.LayoutParams rowParam3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 0);
//            rowParam3.setMargins(0, Utils.getDPforPixel(activity, 8), 0, Utils.getDPforPixel(activity, 8));
//            rowParam2.setMargins(Utils.getDPforPixel(activity, 5), 0, Utils.getDPforPixel(activity, 5), 0);
            LayoutInflater systemService = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for (Node n : node.childs) {

                LinearLayout rowItem = (LinearLayout) systemService.inflate(R.layout.fragment_row_item, null);
                TextView viewById = (TextView) rowItem.findViewById(R.id.dialog_fragment_row);
                viewById.setText(n.name);
                content.addView(rowItem);
                if(n.childs.size()==0)
                    rowItem.findViewById(R.id.dialog_fragment_row_next_image).setVisibility(View.GONE);
                rowItem.setTag(n);
                rowItem.setOnClickListener(clickListener);
//                LinearLayout row = new LinearLayout(activity);
//                if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                    row.setBackgroundDrawable( getResources().getDrawable(R.drawable.row_selector_bg) );
//                } else {
//                    row.setBackground( getResources().getDrawable(R.drawable.row_selector_bg));
//                }
//
//                row.setLayoutParams(rowParam);
//                ImageView ic = new ImageView(activity);
//                ic.setImageResource(R.drawable.next_icon);
//                ic.setLayoutParams(rowParam3);
//                row.addView(ic);
//                TextView a = new TextView(activity);
//                a.setLayoutParams(rowParam2);
//                a.setText(n.name);
//                a.setGravity(Gravity.CENTER | Gravity.RIGHT);
//                row.addView(a);
//                a.setTag(n);
//                content.addView(row);
//                row.setTag(n);
//                row.setOnClickListener(clickListener);


            }
//        button.setText(node.name);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        }
        return view;



    }

    public void setSearchImageWidth(int searchImageWidth) {
        searchimgWidth = searchImageWidth;
    }


    public interface OnItemSelectedListener {
        void onRssItemSelected(String link);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    // triggers update of the details fragment

}
