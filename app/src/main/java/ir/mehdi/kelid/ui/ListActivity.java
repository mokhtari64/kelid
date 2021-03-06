package ir.mehdi.kelid.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import ir.mehdi.kelid.Constant;
import ir.mehdi.kelid.R;
import ir.mehdi.kelid.db.DBAdapter;
import ir.mehdi.kelid.model.City;
import ir.mehdi.kelid.model.Province;

import java.util.ArrayList;




public class ListActivity extends  KelidActivity implements Constant {

    ListView listView;
    String type;
    String title;
    int province_id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_city_province);
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            type = extra.getString("type");
            province_id=extra.getInt("province_id");
        }
        title = (type == null || type.equals("province")) ? getResources().getString(R.string.ChooseProvince) : getResources().getString(R.string.ChooseCity);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
        }

        listView = (ListView) findViewById(R.id.listView);
        if((type == null || type.equals("province")) ) {
            Province[] provinces = DBAdapter.getInstance().getAllProvince();
            ProvinceAdapter adapter = new ProvinceAdapter(this, R.layout.province_activity_listitem, provinces);

            listView.setAdapter(adapter);
        }else
        {
            if(province_id!=0) {
                ArrayList<City> citiy = DBAdapter.getInstance().provinceCities.get(province_id);
                City[] cities=new City[citiy.size()];
                citiy.toArray(cities);
                CityAdapter adapter=new CityAdapter(this, R.layout.province_activity_listitem, cities);
                listView.setAdapter(adapter);

            }

        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                if((type == null || type.equals("province")) ) {
                    final Province item = (Province) parent.getItemAtPosition(position);
                    DBAdapter.getInstance().setCurrentProvince(item);
                    Intent i = new Intent(ListActivity.this, CityActivity.class);
                    startActivity(i);
                    finish();
                }else
                {
                    final City item = (City) parent.getItemAtPosition(position);
                    DBAdapter.getInstance().setCurrentCity(item);
                    Intent i = new Intent(ListActivity.this, CityActivity.class);
                    startActivity(i);
                    finish();

                }
            }

        });


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(this, CityActivity.class);
        startActivity(myIntent);
        finish();
        return true;
    }

//


}
