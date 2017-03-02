package ir.mehdi.kelid;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;



import ir.mehdi.kelid.coolmenu.CoolMenuFrameLayout;
import ir.mehdi.kelid.coolmenu.TranslateLayout;
import ir.mehdi.kelid.ui.fragment.ListItemFragment;

import java.util.Arrays;
import java.util.List;
/**
 * Created by Iman on 1/5/2017.
 */
public class MainActivity extends AppCompatActivity {


    CoolMenuFrameLayout coolMenuFrameLayout;


    String[] titles ;
    ListItemFragment[] fragments ;

    List<String> titleList = null;
    List<Drawable> mainMenuDrawable = null;
    List<Drawable> optionMenuDrawable = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cool_activity_main);
        titles = new String[]{
                getString(R.string.property),
                getString(R.string.office_services),
                getString(R.string.consulting_office),
                getString(R.string.office_property)};
        fragments= new ListItemFragment[]{
                new ListItemFragment(),new ListItemFragment(),new ListItemFragment(),new ListItemFragment()
        };
        fragments[0].setType(1);
        fragments[1].setType(2);
        fragments[2].setType(3);
        fragments[3].setType(4);


        coolMenuFrameLayout = (CoolMenuFrameLayout)findViewById(R.id.rl_main);
        coolMenuFrameLayout.setCascadeTitleColor(ContextCompat.getColor(this, R.color.cascadeTitleColor));
        coolMenuFrameLayout.setOriginTitleColor(ContextCompat.getColor(this, R.color.originTitleColor));
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.main_menu_anim);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatCount(0);
        coolMenuFrameLayout.setMainMenuAnimation(animation);

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
                Toast.makeText(MainActivity.this,translateLayout.getTitle().getText(),Toast.LENGTH_LONG).show();

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


}
