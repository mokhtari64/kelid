package ir.mehdi.kelid.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import ir.mehdi.kelid.R;

public class SearchActivity extends  KelidActivity implements AdapterView.OnItemSelectedListener {


    String[] nameItem = {"املاک", "دفاتر خدماتی", "دفاتر مشاوره ای", "دفاتر املاک", "اخبار"};
    int flags[] = {R.drawable.amlak, R.drawable.khadamat, R.drawable.moshavere, R.drawable.daftar_amlak, R.drawable.akhbar};
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.searchSpinner);
        spin.setOnItemSelectedListener(this);

        CustomSearchAdabter customAdapter = new CustomSearchAdabter(getApplicationContext(), flags, nameItem);
        spin.setAdapter(customAdapter);

        final ToggleButton btn = (ToggleButton) findViewById(R.id.buttonmah);
        final LinearLayout l1 = (LinearLayout) findViewById(R.id.l1);
        final LinearLayout l2 = (LinearLayout) findViewById(R.id.l2);
        final LinearLayout l3 = (LinearLayout) findViewById(R.id.l3);
        final LinearLayout l4 = (LinearLayout) findViewById(R.id.l4);
        final ImageView img1 = (ImageView)findViewById(R.id.img1);
        final ImageView img2 = (ImageView)findViewById(R.id.img2);
        final ImageView img3 = (ImageView)findViewById(R.id.img3);
        final ImageView img4 = (ImageView)findViewById(R.id.img4);
//        final ImageView img_main = (ImageView)findViewById(R.id.img_main);
        final TextView t1=(TextView)findViewById(R.id.t1);
        final TextView t2=(TextView)findViewById(R.id.t2);
        final TextView t3=(TextView)findViewById(R.id.t3);
        final TextView t4=(TextView)findViewById(R.id.t4);
        final Animation alpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        final Animation alpha_out = AnimationUtils.loadAnimation(this, R.anim.alpha_out);
        final Animation rotation = AnimationUtils.loadAnimation(this, R.anim.clockwise_rotation);
        final Animation rotation_out = AnimationUtils.loadAnimation(this, R.anim.unclockwise_rotation);


        final float orgPos1X = l1.getX();
        final float orgPos1Y = l1.getY();
        final float orgPos2X = l2.getX();
        final float orgPos2Y = l2.getY();
        final float orgPos3X = l3.getX();
        final float orgPos3Y = l3.getY();
        final float orgPos4X = l4.getX();
        final float orgPos4Y = l4.getY();




        assert btn != null;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn.isChecked()) {
                    l1.animate().translationX(orgPos1X + TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics())).setDuration(2000);
                    l2.animate().translationY(orgPos2Y + 66).setDuration(2000);
                    l2.animate().translationX(orgPos2X + 132).setDuration(2000);
                    l3.animate().translationY(orgPos3Y + 132).setDuration(2000);
                    l3.animate().translationX(orgPos3X + 66).setDuration(2000);
                    l4.animate().translationY(orgPos4Y + 200).setDuration(2000);
                    alpha .setFillAfter(true);
                    img1.startAnimation(alpha);
                    img2.startAnimation(alpha);
                    img3.startAnimation(alpha);
                    img4.startAnimation(alpha);
//                    rotation.setRepeatCount(Animation.INFINITE);
                    rotation.setRepeatCount(0);
//                    img_main.startAnimation(rotation);
                    t1.postDelayed(new Runnable() {
                        public void run() {
                            t1.setVisibility(View.VISIBLE);
                            t3.setVisibility(View.VISIBLE);
                            t2.setVisibility(View.VISIBLE);
                            t4.setVisibility(View.VISIBLE);
//                            img2.setImageResource(R.drawable.khadamat);
//                            img3.setImageResource(R.drawable.moshavere);
//                            img4.setImageResource(R.drawable.daftar_amlak);
                            img1.setColorFilter( 0xff33b5e5 );
                            img2.setColorFilter( 0xff99cc00 );
                            img3.setColorFilter( 0xffffbb33 );
                            img4.setColorFilter( 0xffff4444 );
                        }
                    }, 2100);


                } else {
                    t1.setVisibility(View.INVISIBLE);
                    t2.setVisibility(View.INVISIBLE);
                    t3.setVisibility(View.INVISIBLE);
                    t4.setVisibility(View.INVISIBLE);
                    img1.clearColorFilter();
                    img2.clearColorFilter();
                    img3.clearColorFilter();
                    img4.clearColorFilter();
                    alpha_out .setFillAfter(true);
                    img1.startAnimation(alpha_out);
                    img2.startAnimation(alpha_out);
                    img3.startAnimation(alpha_out);
                    img4.startAnimation(alpha_out);
                    rotation_out.setRepeatCount(0);
//                    img_main.startAnimation(rotation_out);
                    l1.animate().translationY(orgPos1Y).setDuration(2000);
                    l1.animate().translationX(orgPos1X).setDuration(2000);
                    l2.animate().translationY(orgPos2Y).setDuration(2000);
                    l2.animate().translationX(orgPos2X).setDuration(2000);
                    l3.animate().translationY(orgPos3Y).setDuration(2000);
                    l3.animate().translationX(orgPos3X).setDuration(2000);
                    l4.animate().translationY(orgPos4Y).setDuration(2000);
                    l4.animate().translationX(orgPos4X).setDuration(2000);


                }




            }
        });
    }


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(), nameItem[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


}