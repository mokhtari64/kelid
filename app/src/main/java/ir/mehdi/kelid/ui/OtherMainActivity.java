package ir.mehdi.kelid.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ir.mehdi.kelid.R;

import ir.mehdi.kelid.db.Database;
import ir.mehdi.kelid.model.Node;

public class OtherMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager supportFragmentManager;
    //    private TextView property;
    public static Activity context;

//    public void setDialogFragment(Node node) {
//
//        NodeFragmentDialog fragment = new NodeFragmentDialog();
//        fragment.setMainActivity(this);
//        fragment.setNode(node);
//
////        NodeFragmentDialog nodeFragmentDialog=new NodeFragmentDialog();
////        nodeFragmentDialog.setMainActivity(OtherMainActivity.this);
//
//        fragment.show(supportFragmentManager, "wizard");
//
//    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        supportFragmentManager = getSupportFragmentManager();
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        context = this;
        Database.getInstance();
//        setSupportActionBar(toolbar);
        setFragment(Database.getInstance().root, true);
//        property = (TextView) findViewById(R.id.main_tv_property);





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                NodeFragmentDialog fragment = new NodeFragmentDialog();
//                fragment.setActivity(OtherMainActivity.this);
////                fragment.setSize();
//                fragment.show(supportFragmentManager, "wizard");

//                fragment.setMainActivity(OtherMainActivity.this);


//        NodeFragmentDialog nodeFragmentDialog=new NodeFragmentDialog();
//        nodeFragmentDialog.setMainActivity(OtherMainActivity.this);



//                setDialogFragment();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();


            }
        });

        com.getbase.floatingactionbutton.FloatingActionButton fab_action_a = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.action_a);
        fab_action_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NodeFragmentDialog fragment = new NodeFragmentDialog();
                fragment.setActivity(OtherMainActivity.this);
//                fragment.setSize();
                fragment.show(supportFragmentManager, "wizard");


            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //dbm=new Database(this);


//        property.setOnClickListener(new OnClickListener() {
//            public void onClick(View v) {
//                Intent i = new Intent(OtherMainActivity.this, ShowDetail.class);
//                i.putExtra("node_id", 1000000);
//                startActivity(i);
//                // overridePendingTransition(R.anim.in, R.anim.out);
//
//
//            }
//        });


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
//            return true;
            Intent i = new Intent(OtherMainActivity.this, MyMain.class);
            startActivity(i);
        } else if (id == R.id.action_filter) {
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//            AlertDialog alertDialog = alertDialogBuilder.create();
//            alertDialog.setTitle("This is list choice dialog box");
//
//            LayoutInflater inflater = getLayoutInflater();
//            View rowView= inflater.inflate(R.layout.filter_dialog, null, true);
//            alertDialog.setView(rowView);
//            alertDialog.show();

            Intent i = new Intent(OtherMainActivity.this, FilterActivity.class);
            startActivity(i);

            return true;
        }else if (id == R.id.action_search) {
            Intent i = new Intent(OtherMainActivity.this, SearchActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    public void setFragment(Node node, boolean a) {
        android.app.FragmentManager fm = getFragmentManager();
        NodeFragment fragment = new NodeFragment();
        fragment.setActivity(this);
        fragment.setNode(node);

        android.app.FragmentTransaction ft = fm.beginTransaction();
        ft.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        if (a) {
            ft.add(R.id.contentPanel, fragment);
        } else
            ft.replace(R.id.contentPanel, fragment).addToBackStack(null);
        ft.commit();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
