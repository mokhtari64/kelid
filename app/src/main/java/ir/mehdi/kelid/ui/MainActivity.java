package ir.mehdi.kelid.ui;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import ir.mehdi.kelid.Constant;
import ir.mehdi.kelid.R;
import ir.mehdi.kelid.arcmenulibrary.ArcMenu;
import ir.mehdi.kelid.ui.fragment.FilterFragment;
import ir.mehdi.kelid.ui.fragment.HomeFragment;
import ir.mehdi.kelid.ui.fragment.SearchFragment;
import ir.mehdi.kelid.ui.fragment.UserProfileFragment;

public class MainActivity extends KelidActivity implements Constant, View.OnClickListener {

    HomeFragment homeFragment;
    FilterFragment filterFragment;
    UserProfileFragment userProfileFragment;
    SearchFragment searchFragment;
    Fragment[] fragments;


    ImageView home, search, category, profile;
    ImageView tabImageView[];
    ArcMenu arcMenu;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeFragment = new HomeFragment();
        filterFragment = new FilterFragment();
        searchFragment = new SearchFragment();
        userProfileFragment = new UserProfileFragment();
        fragments = new Fragment[]{
                homeFragment,
                filterFragment,
                searchFragment,
                userProfileFragment
        };
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        arcMenu = (ArcMenu) findViewById(R.id.arcMenu);
        initArcMenu();
        home = (ImageView) findViewById(R.id.btn_home);
        search = (ImageView) findViewById(R.id.btn_search);
        category = (ImageView) findViewById(R.id.btn_category);
        profile = (ImageView) findViewById(R.id.btn_profile);
        tabImageView=new  ImageView[]{home,category,search,profile};
        home.setOnClickListener(this);
        search.setOnClickListener(this);
        category.setOnClickListener(this);
        profile.setOnClickListener(this);
        currentTabImageView = home;
        currentTabImageView.setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageChange(tabImageView[position]);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    private static final int[] ITEM_DRAWABLES = {R.drawable.add_image,
            R.drawable.add_text, R.drawable.preview, R.drawable.qr};
    private String[] str = {"Facebook", "Twiiter", "Flickr", "Instagram"};
    private int type[] = new int[]{PROPERTY
            , SERVICE
            , OFFICE
            , CONSULTING
            , NEWS};

    private void initArcMenu() {
        final int itemCount = ITEM_DRAWABLES.length;
        arcMenu.setToolTipTextSize(14);
//        arcMenu.setMinRadius(104);
//        arcMenu.setArc(175,255);
        arcMenu.setToolTipSide(ArcMenu.TOOLTIP_UP);
        arcMenu.setToolTipTextColor(Color.RED);
        arcMenu.setToolTipBackColor(getResources().getColor(R.color.white_pressed));
        arcMenu.setToolTipCorner(2);
        arcMenu.setToolTipPadding(4);
        arcMenu.setColorNormal(getResources().getColor(R.color.mRedDark));
        arcMenu.showTooltip(true);
//        arcMenu.setRadius(120);
        arcMenu.setDuration(ArcMenu.ArcMenuDuration.LENGTH_LONG);
//        arcMenu.setAnim(500,500, ArcMenu.ANIM_MIDDLE_TO_DOWN, ArcMenu.ANIM_MIDDLE_TO_RIGHT,
//                ArcMenu.ANIM_INTERPOLATOR_ANTICIPATE, ArcMenu.ANIM_INTERPOLATOR_ANTICIPATE);
        for (int i = 0; i < itemCount; i++) {
            ImageView item = new ImageView(this);
            item.setImageResource(ITEM_DRAWABLES[i]);
            arcMenu.setAnim(400, 400, ArcMenu.ANIM_MIDDLE_TO_DOWN, ArcMenu.ANIM_MIDDLE_TO_RIGHT,
                    ArcMenu.ANIM_INTERPOLATOR_DECLERATE, ArcMenu.ANIM_INTERPOLATOR_BOUNCE);
//            if(menuNum == 1){
//
//            }
//
//            if(menuNum == 4){
//                arcMenu.setAnim(500,500, ArcMenu.ANIM_MIDDLE_TO_DOWN, ArcMenu.ANIM_MIDDLE_TO_RIGHT,
//                        ArcMenu.ANIM_INTERPOLATOR_ANTICIPATE, ArcMenu.ANIM_INTERPOLATOR_ANTICIPATE);
//            }

            final int position = i;
            final int rrr = type[i];
            arcMenu.addItem(item, str[i], new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    registerNew(rrr);
                }
            });
        }
    }

    public void registerNew(int i) {
        if (nodeFragmentDialog == null)
            nodeFragmentDialog = new NodeFragmentDialog();
//        nodeFragmentDialog.setType(i);
//        nodeFragmentDialog.setActivity(this);
        nodeFragmentDialog.show(getSupportFragmentManager(), "wizard");
    }

    NodeFragmentDialog nodeFragmentDialog;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    ImageView currentTabImageView;

    void pageChange(ImageView v) {
        currentTabImageView.clearColorFilter();//(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
        currentTabImageView = v;
        currentTabImageView.setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);

    }

    @Override
    public void onClick(View v) {
        if (v == currentTabImageView) {
            return;
        }
        if (v == home || v == search || v == profile || v == category) {
            int tag = Integer.parseInt(v.getTag().toString());
            pageChange((ImageView) v);
            mViewPager.setCurrentItem(tag);

        }


    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return fragments[position];
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return fragments.length;
        }


    }
}
