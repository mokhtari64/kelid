package ir.mehdi.kelid;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;


import ir.mehdi.kelid.coolmenu.CoolMenuFrameLayout;
import ir.mehdi.kelid.coolmenu.TranslateLayout;
import ir.mehdi.kelid.ui.AddConsulatingActivity;
import ir.mehdi.kelid.ui.AddOfficeActivity;
import ir.mehdi.kelid.ui.AddPropetyActivity;
import ir.mehdi.kelid.ui.AddServiceActivity;
import ir.mehdi.kelid.ui.KelidActivity;
import ir.mehdi.kelid.ui.NodeFragmentDialog;
import ir.mehdi.kelid.ui.fragment.ListItemFragment;
import ir.mehdi.kelid.utils.Utils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

/**
 * Created by Iman on 1/5/2017.
 */
public class MainActivity extends KelidActivity implements Constant {


    float orgPos1X;
    LinearLayout dashboard_layer;
    ImageButton dashboardTab;
    ToggleButton settingbtn;
    LayoutInflater inflater;
    RelativeLayout edit_device,edit_zone,edit_senario,menu_space,edit_user,rules,about_me,exit;
    LinearLayout setting_layer;
    Animation alpha,alpha_out,rotation,rotation_out;
    int screenWidth,sw;


    CoolMenuFrameLayout coolMenuFrameLayout;

    String[] titles;
    ListItemFragment[] fragments;

    List<String> titleList = null;
    List<Drawable> mainMenuDrawable = null;
    List<Drawable> optionMenuDrawable = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        float orgPos1X = setting_layer.getX();
        settingbtn = (ToggleButton) findViewById(R.id.setting);
        alpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        alpha_out = AnimationUtils.loadAnimation(this, R.anim.alpha_out);
        rotation = AnimationUtils.loadAnimation(this, R.anim.clockwise_rotation);
        rotation_out = AnimationUtils.loadAnimation(this, R.anim.unclockwise_rotation);

        setting_layer = (LinearLayout) findViewById(R.id.setting_layer);
        menu_space = (RelativeLayout) findViewById(R.id.menu_space);
        edit_device = (RelativeLayout) findViewById(R.id.edit_device);
        edit_zone = (RelativeLayout) findViewById(R.id.edit_zone);
        edit_senario = (RelativeLayout) findViewById(R.id.edit_senario);
        edit_user = (RelativeLayout) findViewById(R.id.edit_user);
        rules = (RelativeLayout) findViewById(R.id.rules);
        about_me = (RelativeLayout) findViewById(R.id.about_me);
        exit = (RelativeLayout) findViewById(R.id.exit);


        setContentView(R.layout.cool_activity_main);
        titles = new String[]{
                getString(R.string.property),
                getString(R.string.office_services),
                getString(R.string.consulting_office),
                getString(R.string.office_property)};
        fragments = new ListItemFragment[]{
                new ListItemFragment(), new ListItemFragment(), new ListItemFragment(), new ListItemFragment()
        };
        fragments[0].setType(Constant.PROPERTY);
        fragments[1].setType(Constant.SERVICE);
        fragments[2].setType(Constant.OFFICE);
        fragments[3].setType(Constant.CONSULTING);


        coolMenuFrameLayout = (CoolMenuFrameLayout) findViewById(R.id.rl_main);
        coolMenuFrameLayout.setCascadeTitleColor(ContextCompat.getColor(this, R.color.cascadeTitleColor));
        coolMenuFrameLayout.setOriginTitleColor(ContextCompat.getColor(this, R.color.originTitleColor));
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.main_menu_anim);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatCount(0);
        coolMenuFrameLayout.setMainMenuAnimation(animation);

        Drawable mainMenu[] = new Drawable[]{ContextCompat.getDrawable(this, R.drawable.menu),
                ContextCompat.getDrawable(this, R.drawable.menu),
                ContextCompat.getDrawable(this, R.drawable.menu),
                ContextCompat.getDrawable(this, R.drawable.menu),
        };
        Drawable optionMenu[] = new Drawable[]{ContextCompat.getDrawable(this, R.drawable.amlak),
                ContextCompat.getDrawable(this, R.drawable.akhbar),
                ContextCompat.getDrawable(this, R.drawable.daftar_amlak),
                ContextCompat.getDrawable(this, R.drawable.khadamat),
        };
        titleList = Arrays.asList(titles);
        mainMenuDrawable = Arrays.asList(mainMenu);
        optionMenuDrawable = Arrays.asList(optionMenu);


        coolMenuFrameLayout.setTitles(titleList);
        coolMenuFrameLayout.setMainMenuDrawble(mainMenuDrawable);
        coolMenuFrameLayout.setOptionMenuDrawble(optionMenuDrawable);

        coolMenuFrameLayout.setOptionMenuListener(new TranslateLayout.OnOptionMainMenuListner() {
            @Override
            public void onMenuClick(TranslateLayout translateLayout, Fragment fragment) {
                Toast.makeText(MainActivity.this, translateLayout.getTitle().getText(), Toast.LENGTH_LONG).show();

            }
        });


        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        };
        coolMenuFrameLayout.setAdapter(adapter);


        screenWidth = Utils.getScreenWidth(MainActivity.this);
        sw = screenWidth;
        setting_layer.setX(orgPos1X - screenWidth);
        assert settingbtn != null;
        settingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (settingbtn.isChecked()) {
                    settingbtn.setClickable(false);
//                    setting_layer.setX(orgPos1X - screenWidth );

                    rotation.setRepeatCount(Animation.INFINITE);
                    rotation.setRepeatCount(0);
                    settingbtn.startAnimation(rotation_out);
//                    settingbtn.setHighlightColor(0xff33b5e5);
                    setting_layer.animate().translationX(setting_layer.getX() + sw).setDuration(Const.AnimDuration);
                    dashboard_layer.animate().alpha((float) 0.3).setDuration(Const.AnimDuration);

//                    recyclerView.animate().translationX(screenWidth).setDuration(dtime);
//                    setting_layer.setVisibility(View.VISIBLE);
//                    recyclerView.setVisibility(View.GONE);
                    final Handler handler = new Handler();
                    setting_layer.setEnabled(false);
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            settingbtn.setClickable(true);
                        }
                    }, Const.AnimDuration);


                } else {
                    closeMenu();

                }


            }
        });


    }

    void closeMenu() {
        if (settingbtn.isChecked()) {
            settingbtn.setChecked(false);
            settingbtn.setClickable(false);
            settingbtn.startAnimation(rotation);
            setting_layer.animate().translationX(orgPos1X - screenWidth).setDuration(Const.AnimDuration);
//                    recyclerView.animate().translationX(orgPos1X).setDuration(dtime);
            dashboard_layer.animate().alpha(1).setDuration(Const.AnimDuration);
//                    setting_layer.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.VISIBLE);
            final Handler handler = new Handler();
            setting_layer.setEnabled(false);
            handler.postDelayed(new Runnable() {
                public void run() {
                    settingbtn.setClickable(true);
                }
            }, Const.AnimDuration);
        }

    }

    NodeFragmentDialog nodeFragmentDialog;

    public void registerNew(int i) {
        if (nodeFragmentDialog == null)
            nodeFragmentDialog = new NodeFragmentDialog();
        nodeFragmentDialog.setType(i);
        nodeFragmentDialog.setActivity(this);
        nodeFragmentDialog.show(getSupportFragmentManager(), "wizard");
    }

    public void startActivity(int type) {
        Intent i;
        switch (type) {
            case PROPERTY:
                i = new Intent(this, AddPropetyActivity.class);
                startActivity(i);
                break;
            case CONSULTING:
                i = new Intent(this, AddConsulatingActivity.class);
                startActivity(i);
                break;
            case SERVICE:
                i = new Intent(this, AddServiceActivity.class);
                startActivity(i);
                break;
            case OFFICE:
                i = new Intent(this, AddOfficeActivity.class);
                startActivity(i);
                break;
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            } else {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    return;
                }

                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);

//                super.onBackPressed();
            }

//
        }
    }
}
