package ir.mehdi.kelid.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ir.mehdi.kelid.MainActivity;
import ir.mehdi.kelid.R;
import ir.mehdi.kelid.UserConfig;
import ir.mehdi.kelid.db.Database;
import ir.mehdi.kelid.model.*;
import ir.mehdi.kelid.utils.ProvinceMap;


public class CityActivity extends AppCompatActivity {
    Context context;


    Matrix inverse = new Matrix();
    boolean inverseEn = true;
    private TextView provinceButton;
    private TextView cityButton;
    ImageView mapImageView;


    public void selectCity(City city, boolean check) {

        if (!(Database.getInstance().getCurrentCity() == city && check))
            Database.getInstance().setCurrentCity(city);
        if (city == null) {
            cityButton.setText(R.string.ChooseCity);

        } else
            cityButton.setText(city.name);
        return;

    }

    public void selectProvince(Province province, boolean check) {
        if (Database.getInstance().getCurrentProvince() == province && check) {
            if (Database.getInstance().getCurrentProvince() == null) {
                provinceButton.setText(R.string.ChooseProvince);
            } else
                provinceButton.setText(Database.getInstance().getCurrentProvince().name);


            return;
        }

        if (Database.getInstance().getCurrentProvince() != province) {
            selectCity(null, false);
            Database.getInstance().setCurrentProvince(province);
        } else {
            Database.getInstance().setCurrentProvince(null);

        }

        mapImageView.setImageBitmap(ProvinceMap.getInstance().orginalBitmap);

        if (Database.getInstance().getCurrentProvince() == null) {
            provinceButton.setText(R.string.ChooseProvince);
        } else
            provinceButton.setText(Database.getInstance().getCurrentProvince().name);

    }

    public void selectProvince(int i, boolean check) {
        if (i >= 0 && i < Database.getInstance().getProvinceCount()) {
            selectProvince(Database.getInstance().getAllProvince()[i], check);
//            Intent myIntent = new Intent(getApplicationContext(), ListActivity.class);
//            myIntent.putExtra("type", "city");
//            myIntent.putExtra("province_id", Database.getInstance().getCurrentProvince().code);
//
//            startActivity(myIntent);

        }
        else
            selectProvince(null, check);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_city_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        Intent myIntent = null;
        switch (itemId) {
            case android.R.id.home: {
                if (UserConfig.city != -1) {
                    myIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(myIntent);
                    finish();

                    break;
                } else {
                    Toast.makeText(this,getResources().getString(R.string.NoCitySelectError), Toast.LENGTH_LONG).show();
                }
            }
            case R.id.done_menu:
                if (Database.getInstance().getCurrentCity() != null) {
                    UserConfig.city = Database.getInstance().getCurrentCity().code;
                    UserConfig.province = Database.getInstance().getCurrentProvince().code;
                    UserConfig.save();
                    myIntent = new Intent(getApplicationContext(), MainActivity.class);
//                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    finish();
                    startActivity(myIntent);

                } else {
                    Toast.makeText(this, getResources().getString(R.string.NoCitySelectError), Toast.LENGTH_LONG).show();
                }

                // Toast.makeText(this, "home pressed", Toast.LENGTH_LONG).show();
                break;


        }
        return true;

    }
//    private void setupWindowAnimations() {
//        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
//        getWindow().setExitTransition(transition);
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_city);
//        setupWindowAnimations();



        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getResources().getString(R.string.ChooseCity));
        }
        context = this;
        provinceButton = (TextView) findViewById(R.id.province_text_button);
        if (Database.getInstance().getCurrentProvince() == null) {
            provinceButton.setText(R.string.ChooseProvince);
        } else
            provinceButton.setText(Database.getInstance().getCurrentProvince().name);

        provinceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), ListActivity.class);
                myIntent.putExtra("type", "province");
                startActivity(myIntent);
            }
        });


        cityButton = (TextView) findViewById(R.id.city_text_button);
        if (Database.getInstance().getCurrentCity() == null) {
            cityButton.setText(R.string.ChooseCity);
        } else
            cityButton.setText(Database.getInstance().getCurrentCity().name);
        cityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Database.getInstance().getCurrentProvince() == null) {
                    Toast.makeText(CityActivity.this,getResources().getString(R.string.FirstSelectProvince), Toast.LENGTH_LONG).show();
                    return;
                }
                Intent myIntent = new Intent(getApplicationContext(), ListActivity.class);
                myIntent.putExtra("type", "city");
                myIntent.putExtra("province_id", Database.getInstance().getCurrentProvince().code);

                startActivity(myIntent);
            }
        });
//        if (Ci == null) {
//            cityButton.setText(R.string.ChooseCity);
//
//        } else
//            cityButton.setText(city.name);


        mapImageView = (ImageView) findViewById(R.id.mapImageView);
        mapImageView.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v1, MotionEvent event) {
                if (inverseEn) {
                    inverseEn = false;
                    mapImageView.getImageMatrix().invert(inverse);
                }

                Point point = new Point();
                if (event.getAction() != MotionEvent.ACTION_DOWN)
                    return false;
                float[] touchPoint = new float[]{event.getX(), event.getY()};
                inverse.mapPoints(touchPoint);
                // System.out.println("map---------------------------in (" + touchPoint[0] + "," + touchPoint[1] + ")");
                point.x = Integer.valueOf((int) touchPoint[0]);
                point.y = Integer.valueOf((int) touchPoint[1]);

                for (int i = 0; i < Database.getInstance().getProvinceCount(); i++) {
                    if (Database.getInstance().getProvinceForPosition(i).regionPath.contains(point.x, point.y)) {
                        // System.out.println("---------------------------in (" + i + ")" + ":" + ProvinceMap.getInstance().getProvinceForPosition(i).name);

                        selectProvince(i, false);
                        return true;

                    }
                }
                selectProvince(null, false);

                return true;
            }


        });


        mapImageView.setImageBitmap(ProvinceMap.getInstance().orginalBitmap);

    }


}
