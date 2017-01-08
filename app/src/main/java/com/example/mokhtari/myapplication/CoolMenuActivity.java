package com.example.mokhtari.myapplication;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.mokhtari.myapplication.coolmenu.CoolMenuFrameLayout;
import com.example.mokhtari.myapplication.coolmenu.TranslateLayout;
import com.example.mokhtari.myapplication.coolmenu.fragment.Fragment1;
import com.example.mokhtari.myapplication.coolmenu.fragment.Fragment2;
import com.example.mokhtari.myapplication.coolmenu.fragment.Fragment3;
import com.example.mokhtari.myapplication.coolmenu.fragment.Fragment4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Created by Iman on 1/5/2017.
 */
public class CoolMenuActivity extends AppCompatActivity {


    CoolMenuFrameLayout coolMenuFrameLayout;

    List<Fragment> fragments = new ArrayList<>();

    List<String> titleList = null;
    List<Drawable> mainMenuDrawable = null;
    List<Drawable> optionMenuDrawable = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cool_activity_main);



        coolMenuFrameLayout = (CoolMenuFrameLayout)findViewById(R.id.rl_main);
        coolMenuFrameLayout.setCascadeTitleColor(ContextCompat.getColor(this, R.color.cascadeTitleColor));
        coolMenuFrameLayout.setOriginTitleColor(ContextCompat.getColor(this, R.color.originTitleColor));
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.main_menu_anim);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatCount(0);
        coolMenuFrameLayout.setMainMenuAnimation(animation);
        String[] titles = {"CONTACT", "ABOUT", "TEAM", "PROJECTS"};
        Drawable mainMenu[]=new Drawable[]{ContextCompat.getDrawable(this,R.drawable.menu),
                ContextCompat.getDrawable(this,R.drawable.menu),
                ContextCompat.getDrawable(this,R.drawable.menu),
                ContextCompat.getDrawable(this,R.drawable.menu),
        };
        Drawable optionMenu[]=new Drawable[]{ContextCompat.getDrawable(this,R.drawable.amlak),
                ContextCompat.getDrawable(this,R.drawable.akhbar),
                ContextCompat.getDrawable(this,R.drawable.daftar_amlak),
                ContextCompat.getDrawable(this,R.drawable.khadamat),
        };
        titleList = Arrays.asList(titles);
        mainMenuDrawable=Arrays.asList(mainMenu);
        optionMenuDrawable=Arrays.asList(optionMenu);


        coolMenuFrameLayout.setTitles(titleList);
        coolMenuFrameLayout.setMainMenuDrawble(mainMenuDrawable);
        coolMenuFrameLayout.setOptionMenuDrawble(optionMenuDrawable);

        coolMenuFrameLayout.setOptionMenuListener(new TranslateLayout.OnOptionMainMenuListner() {
            @Override
            public void onMenuClick(TranslateLayout translateLayout,Fragment fragment) {
                Toast.makeText(CoolMenuActivity.this,translateLayout.getTitle().getText(),Toast.LENGTH_LONG).show();

            }
        });


        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());
        fragments.add(new Fragment4());

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        coolMenuFrameLayout.setAdapter(adapter);


    }


}
