package ir.mehdi.kelid;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;


import ir.mehdi.kelid.coolmenu.CoolMenuFrameLayout;
import ir.mehdi.kelid.coolmenu.TranslateLayout;
import ir.mehdi.kelid.db.DBAdapter;
import ir.mehdi.kelid.model.Node;
import ir.mehdi.kelid.ui.AddConsulatingActivity;
import ir.mehdi.kelid.ui.AddOfficeActivity;
import ir.mehdi.kelid.ui.AddPropetyActivity;
import ir.mehdi.kelid.ui.AddServiceActivity;
import ir.mehdi.kelid.ui.KelidActivity;
import ir.mehdi.kelid.ui.NodeFragmentDialog;
import ir.mehdi.kelid.ui.ShowInfoActivity;
import ir.mehdi.kelid.ui.fragment.RootFragment;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Iman on 1/5/2017.
 */
public class MainActivity extends KelidActivity implements Constant {


    CoolMenuFrameLayout coolMenuFrameLayout;

    String[] titles ;
    int[] layout = new int[]{
            R.layout.office_root_fragment,
            R.layout.consulating_root_fragment,
            R.layout.serviec_root_fragment,
            R.layout.property_root_fragment

    };
    int[] fragmentid = new int[]{
            R.id.office_frg,
            R.id.consulate_frg,
            R.id.service_frg,
            R.id.property_frg,
    };
    int[] color = new int[]{
            R.color.OFFICE,
            R.color.CONSULTING,
            R.color.SERVICE,
            R.color.PROPERTY,
    };
    Node[] nodes = new Node[]{
            DBAdapter.getInstance().allNodes.get(OFFICE),
            DBAdapter.getInstance().allNodes.get(CONSULTING),
            DBAdapter.getInstance().allNodes.get(SERVICE),
            DBAdapter.getInstance().allNodes.get(PROPERTY)
    };

    View.OnClickListener[] itemListner = new View.OnClickListener[]{
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            },
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            },
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            },
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent a = new Intent(MainActivity.this, ShowInfoActivity.class);
                    startActivity(a);
                }
            },


    };
    RootFragment[] fragments=new RootFragment[4];

    List<String> titleList = null;
    List<Drawable> mainMenuDrawable = null;
    List<Drawable> optionMenuDrawable = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titles = new String[]{
                getString(R.string.office_property),
                getString(R.string.consulting_office),
                getString(R.string.office_services),
                getString(R.string.property)
        };

        setContentView(R.layout.cool_activity_main);


        fragments = new RootFragment[]{
                new RootFragment(), new RootFragment(), new RootFragment(), new RootFragment()
        };

        for (int i = 0; i < fragments.length; i++) {
            fragments[i].setParameter(this,nodes[i],ContextCompat.getColor(this,color[i]),layout[i],fragmentid[i],itemListner[i]);
        }



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

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
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
