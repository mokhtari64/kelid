package ir.mehdi.kelid.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.util.Vector;

import ir.mehdi.kelid.Constant;
import ir.mehdi.kelid.R;
import ir.mehdi.kelid.arcmenulibrary.util.Util;
import ir.mehdi.kelid.db.DBAdapter;
import ir.mehdi.kelid.model.Property;
import ir.mehdi.kelid.model.PropertyCategory;
import ir.mehdi.kelid.model.PropertyDetail;
import ir.mehdi.kelid.ui.AddPropetyActivity;
import ir.mehdi.kelid.utils.Utils;

/**
 * Created by Iman on 8/9/2016.
 */
public class PropertyCreateFragment extends Fragment implements Constant {
    TextInputLayout nameLayout, titleLayout, adverLayout, descLayout, telLayout, mobileLayout, emailLayout, telegramLayout, addressLayout;
    EditText masahat, zirbana, arse, arz_khyaban, hashye_melk;
    RadioGroup tabaghat, vahedTabaghat, vahedNo, roomNo, tarakom, omr, ab_emtiazat, gaz_emtiazat, bargh_emtiazat;
    LinearLayout mainFeauterLayout;
    //    ToggleButton coler_abi, coler_gazi, coler_gazi_spilet, chiller, pakage, shomine, shofaj, garmayesh_az_kaf, ab_garmkon, ab_garmkon_divari, ab_garmkon_khorshidi;
//    ToggleButton moket, parket, seramik, sang, mozaeik, siman, rang_plastik, rang_roghan, kaghazdivari;
//    ToggleButton chob, divarkob, rang_hajim, divar_sang, divar_sofal, divar_kompozit, divar_chob, divar_shishe, divar_siman, divar_seramik, divar_geranit;
//    ToggleButton kabinet_felezi, kabinet_mdf, kabinet_chob, kabinet_melamine, kabinet_higlas;
//    ToggleButton[] toggleButtons;
    // ToggleButton asansor, parking, anbari, service_farangi, gachbori, rangkari, jakozi, iphone_tasviri, darb_barghi, panjere_dojedare, upvc, komod_divari, security_system;
    // ToggleButton system_bargh_hoshmand, system_etfa_harigh, system_alarm_gaz, havasaz, estakhr, labi, seraydari, faza_sabz, manba_ab, pomp_ab, bargh_ezterari, hood;
    //ToggleButton norpardazi_dakheli, norpardazi_nama, van, hamam_master, sona, alachigh, darb_zed_serghat, pele_ezterari,
    //      system_alarm_atashsozi, jaro_markazi, balkon, kaf_seramik, mdf;
    LinearLayout properyLayout;
    ir.mehdi.kelid.ui.PhotoGridView gridView;
    PhotoAdapter phtoAdapter;


    View mainView;
    Property property;
    LayoutInflater layoutInflater;
    AddPropetyActivity activity;

    Vector<RadioButton> imageRadioButtons = new Vector<>();


    public void setActivity(AddPropetyActivity activity) {
        this.activity = activity;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mainView == null) {
            layoutInflater = inflater;
            mainView = inflater.inflate(R.layout.fragment_create_property, null);
            masahat = (EditText) mainView.findViewById(R.id.masahat);
            zirbana = (EditText) mainView.findViewById(R.id.zirbana);
            arse = (EditText) mainView.findViewById(R.id.arse);
            arz_khyaban = (EditText) mainView.findViewById(R.id.arz_khyaban);
            hashye_melk = (EditText) mainView.findViewById(R.id.hashye_melk);
            tabaghat = (RadioGroup) mainView.findViewById(R.id.tabaghat);
            vahedTabaghat = (RadioGroup) mainView.findViewById(R.id.tabaghevahed);
            vahedNo = (RadioGroup) mainView.findViewById(R.id.vahedno);
            roomNo = (RadioGroup) mainView.findViewById(R.id.roomno);
            tarakom = (RadioGroup) mainView.findViewById(R.id.tarakom);
            omr = (RadioGroup) mainView.findViewById(R.id.omr);
            ab_emtiazat = (RadioGroup) mainView.findViewById(R.id.ab_emtiazat);
            gaz_emtiazat = (RadioGroup) mainView.findViewById(R.id.gaz_emtiazat);
            bargh_emtiazat = (RadioGroup) mainView.findViewById(R.id.bargh_emtiazat);
            mainFeauterLayout = (LinearLayout) mainView.findViewById(R.id.emkanat);

//            coler_abi = (ToggleButton) mainView.findViewById(R.id.coler_abi);
//            coler_gazi = (ToggleButton) mainView.findViewById(R.id.coler_gazi);
//            coler_gazi_spilet = (ToggleButton) mainView.findViewById(R.id.coler_gazi_spilet);
//            chiller = (ToggleButton) mainView.findViewById(R.id.chiller);
//            pakage = (ToggleButton) mainView.findViewById(R.id.pakage);
//            shomine = (ToggleButton) mainView.findViewById(R.id.shomine);
//            shofaj = (ToggleButton) mainView.findViewById(R.id.shofaj);
//            garmayesh_az_kaf = (ToggleButton) mainView.findViewById(R.id.garmayesh_az_kaf);
//            ab_garmkon = (ToggleButton) mainView.findViewById(R.id.ab_garmkon);
//            ab_garmkon_divari = (ToggleButton) mainView.findViewById(R.id.ab_garmkon_divari);
//            ab_garmkon_khorshidi = (ToggleButton) mainView.findViewById(R.id.ab_garmkon_khorshidi);
//            moket = (ToggleButton) mainView.findViewById(R.id.moket);
//            parket = (ToggleButton) mainView.findViewById(R.id.parket);
//            seramik = (ToggleButton) mainView.findViewById(R.id.seramik);
//            sang = (ToggleButton) mainView.findViewById(R.id.sang);
//            mozaeik = (ToggleButton) mainView.findViewById(R.id.mozaeik);
//            siman = (ToggleButton) mainView.findViewById(R.id.siman);
//            rang_plastik = (ToggleButton) mainView.findViewById(R.id.rang_plastik);
//            rang_roghan = (ToggleButton) mainView.findViewById(R.id.rang_roghan);
//            kaghazdivari = (ToggleButton) mainView.findViewById(R.id.kaghazdivari);
//            chob = (ToggleButton) mainView.findViewById(R.id.chob);
//            divarkob = (ToggleButton) mainView.findViewById(R.id.divarkob);
//            rang_hajim = (ToggleButton) mainView.findViewById(R.id.rang_hajim);
//            divar_sang = (ToggleButton) mainView.findViewById(R.id.divar_sang);
//            divar_sofal = (ToggleButton) mainView.findViewById(R.id.divar_sofal);
//            divar_kompozit = (ToggleButton) mainView.findViewById(R.id.divar_kompozit);
//            divar_chob = (ToggleButton) mainView.findViewById(R.id.divar_chob);
//            divar_shishe = (ToggleButton) mainView.findViewById(R.id.divar_shishe);
//            divar_siman = (ToggleButton) mainView.findViewById(R.id.divar_siman);
//            divar_seramik = (ToggleButton) mainView.findViewById(R.id.divar_seramik);
//            divar_geranit = (ToggleButton) mainView.findViewById(R.id.divar_geranit);
//            kabinet_felezi = (ToggleButton) mainView.findViewById(R.id.kabinet_felezi);
//            kabinet_mdf = (ToggleButton) mainView.findViewById(R.id.kabinet_mdf);
//            kabinet_chob = (ToggleButton) mainView.findViewById(R.id.kabinet_chob);
//            kabinet_melamine = (ToggleButton) mainView.findViewById(R.id.kabinet_melamine);
//            kabinet_higlas = (ToggleButton) mainView.findViewById(R.id.kabinet_higlas);
//            toggleButtons = new ToggleButton[]{coler_abi, coler_gazi, coler_gazi_spilet, chiller, pakage, shomine, shofaj,
//                    garmayesh_az_kaf, ab_garmkon, ab_garmkon_divari, ab_garmkon_khorshidi, moket, parket, seramik,
//                    sang, mozaeik, siman, rang_plastik, rang_roghan, kaghazdivari, chob, divarkob, rang_hajim, divar_sang,
//                    divar_sofal, divar_kompozit, divar_chob, divar_shishe, divar_siman, divar_seramik, divar_geranit, kabinet_felezi,
//                    kabinet_mdf, kabinet_chob, kabinet_melamine, kabinet_higlas};


            //asansor = (ToggleButton) mainView.findViewById(R.id.asansor);
            //parking = (ToggleButton) mainView.findViewById(R.id.parking);
            //anbari = (ToggleButton) mainView.findViewById(R.id.anbari);
            //service_farangi = (ToggleButton) mainView.findViewById(R.id.service_farangi);
            //gachbori = (ToggleButton) mainView.findViewById(R.id.gachbori);
            //rangkari = (ToggleButton) mainView.findViewById(R.id.rangkari);
            //jakozi = (ToggleButton) mainView.findViewById(R.id.jakozi);
            //iphone_tasviri = (ToggleButton) mainView.findViewById(R.id.iphone_tasviri);
            //darb_barghi = (ToggleButton) mainView.findViewById(R.id.darb_barghi);
            //panjere_dojedare = (ToggleButton) mainView.findViewById(R.id.panjere_dojedare);
            //upvc = (ToggleButton) mainView.findViewById(R.id.upvc);
            //komod_divari = (ToggleButton) mainView.findViewById(R.id.komod_divari);
            //security_system = (ToggleButton) mainView.findViewById(R.id.security_system);
            //system_bargh_hoshmand = (ToggleButton) mainView.findViewById(R.id.system_bargh_hoshmand);
            //system_etfa_harigh = (ToggleButton) mainView.findViewById(R.id.system_etfa_harigh);
            //system_alarm_gaz = (ToggleButton) mainView.findViewById(R.id.system_alarm_gaz);
            //havasaz = (ToggleButton) mainView.findViewById(R.id.havasaz);
            //estakhr = (ToggleButton) mainView.findViewById(R.id.estakhr);
            //labi = (ToggleButton) mainView.findViewById(R.id.labi);
            //seraydari = (ToggleButton) mainView.findViewById(R.id.seraydari);
            //faza_sabz = (ToggleButton) mainView.findViewById(R.id.faza_sabz);
            //manba_ab = (ToggleButton) mainView.findViewById(R.id.manba_ab);
            //pomp_ab = (ToggleButton) mainView.findViewById(R.id.pomp_ab);
            //bargh_ezterari = (ToggleButton) mainView.findViewById(R.id.bargh_ezterari);
            //hood = (ToggleButton) mainView.findViewById(R.id.hood);
            //norpardazi_dakheli = (ToggleButton) mainView.findViewById(R.id.norpardazi_dakheli);
            //norpardazi_nama = (ToggleButton) mainView.findViewById(R.id.norpardazi_nama);
            //van = (ToggleButton) mainView.findViewById(R.id.van);
            //hamam_master = (ToggleButton) mainView.findViewById(R.id.hamam_master);
            //sona = (ToggleButton) mainView.findViewById(R.id.sona);
            //alachigh = (ToggleButton) mainView.findViewById(R.id.alachigh);
            //darb_zed_serghat = (ToggleButton) mainView.findViewById(R.id.darb_zed_serghat);
            //pele_ezterari = (ToggleButton) mainView.findViewById(R.id.pele_ezterari);
            //system_alarm_atashsozi = (ToggleButton) mainView.findViewById(R.id.system_alarm_atashsozi);
            //jaro_markazi = (ToggleButton) mainView.findViewById(R.id.jaro_markazi);
            //balkon = (ToggleButton) mainView.findViewById(R.id.balkon);
            //kaf_seramik = (ToggleButton) mainView.findViewById(R.id.kaf_seramik);
            //mdf = (ToggleButton) mainView.findViewById(R.id.mdf);
            gridView = (ir.mehdi.kelid.ui.PhotoGridView) mainView.findViewById(R.id.photo_grid);
            gridView.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return event.getAction() == MotionEvent.ACTION_MOVE;
                }

            });
            phtoAdapter = new PhotoAdapter();
            gridView.setAdapter(phtoAdapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        ((AddPropetyActivity) getActivity()).showImageDIalog();
                    }
                }
            });
            properyLayout = (LinearLayout) mainView.findViewById(R.id.proprty_layout);
            initFeatures();
            reFill();


        }
        return mainView;
    }

    CompoundButton.OnCheckedChangeListener featureListner = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Integer tag = (Integer) buttonView.getTag();
            PropertyDetail propertyDetail = DBAdapter.getInstance().allPropertyDetail.get(tag);
            if (isChecked) {
                if (!AddPropetyActivity.property.details.contains(propertyDetail)) {
                    AddPropetyActivity.property.details.add(propertyDetail);
                }
            } else {
                AddPropetyActivity.property.details.remove(propertyDetail);
            }


        }
    };


    private void initFeatures() {
        TextView textView = (TextView) layoutInflater.inflate(R.layout.emkanat_header, null);
        textView.setText(R.string.emkanat);
        TableLayout tableLayout = new TableLayout(activity);
        tableLayout.setBackgroundResource(R.drawable.my_background);
        tableLayout.setOrientation(LinearLayout.VERTICAL);
        tableLayout.addView(textView);
        for (int i = 1; i < DBAdapter.getInstance().allPropertyCategory.size(); i++) {
            PropertyCategory propertyCategory = DBAdapter.getInstance().allPropertyCategory.get(i);
            TableRow tableRow = new TableRow(activity);
            HorizontalScrollView horizontalScrollView = new HorizontalScrollView(activity);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
            layoutParams.width = 0;
            layoutParams.height = Util.dpToPx(50);
            layoutParams.weight = 10;
            horizontalScrollView.setLayoutParams(layoutParams);

            TableRow.LayoutParams layoutParams2 = new TableRow.LayoutParams();
            layoutParams2.width = 0;
            layoutParams2.height = Util.dpToPx(50);
            layoutParams2.weight = 3;
            TextView lable = (TextView) layoutInflater.inflate(R.layout.emkanat_lable, null);
            lable.setLayoutParams(layoutParams2);
            lable.setText(propertyCategory.name);
            tableRow.addView(horizontalScrollView);
            tableRow.addView(lable);
            LinearLayout linearLayout = new LinearLayout(activity);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            horizontalScrollView.addView(linearLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            for (int j = 0; j < propertyCategory.properties.size(); j++) {
                PropertyDetail propertyDetail = propertyCategory.properties.get(j);
                ToggleButton toggleButton = (ToggleButton) layoutInflater.inflate(R.layout.toggle_layout, null);
                toggleButton.setTextOff(propertyDetail.name);
                toggleButton.setText(propertyDetail.name);
                toggleButton.setTextOn(propertyDetail.name);
                toggleButton.setTag(propertyDetail.id);
                if (AddPropetyActivity.property.details.contains(propertyDetail)) {
                    toggleButton.setChecked(true);
                } else {
                    toggleButton.setChecked(false);
                }
                toggleButton.setOnCheckedChangeListener(featureListner);
                linearLayout.addView(toggleButton);
            }
            tableLayout.addView(tableRow, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        }
        mainFeauterLayout.addView(tableLayout);
        Vector<PropertyDetail> pDetail = DBAdapter.getInstance().allPropertyCategory.get(0).properties;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        LinearLayout sayerRow = null;
        for (int i = 0; i < pDetail.size(); i++) {
            if (i % 3 == 0) {
                sayerRow = new LinearLayout(activity);
                sayerRow.setOrientation(LinearLayout.HORIZONTAL);
                properyLayout.addView(sayerRow);
            }
            PropertyDetail a = pDetail.get(i);
            ToggleButton aaa = (ToggleButton) layoutInflater.inflate(R.layout.toggle_layout, null);
            aaa.setText(a.name);
            aaa.setTextOff(a.name);
            aaa.setTextOn(a.name);
//            ViewGroup.LayoutParams layoutParams = aaa.getLayoutParams();
//            layoutParams.height=LinearLayout.LayoutParams.MATCH_PARENT;
//            aaa.setLayoutParams(layoutParams);

            aaa.setLayoutParams(params);
            if (AddPropetyActivity.property.details.contains(a)) {
                aaa.setChecked(true);
            } else {
                aaa.setChecked(false);
            }
            aaa.setTag(a.id);
            aaa.setOnCheckedChangeListener(featureListner);
            sayerRow.addView(aaa);//,LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
        }
    }

    Button add;

    public void reFill() {
        if(AddPropetyActivity.property.masahat!=0)
        masahat.setText(""+AddPropetyActivity.property.masahat);
        if(AddPropetyActivity.property.zirBana!=0)
        zirbana.setText(""+AddPropetyActivity.property.zirBana);
        if(AddPropetyActivity.property.arseZamin!=0)
        arse.setText(""+AddPropetyActivity.property.arseZamin);
        if(AddPropetyActivity.property.hashieh!=0)
        hashye_melk.setText(""+AddPropetyActivity.property.hashieh);
        int count = tabaghat.getChildCount();
        for (int i = 0; i < count; i++) {
            RadioButton o = (RadioButton) tabaghat.getChildAt(i);
            if (Integer.parseInt(o.getTag().toString()) == AddPropetyActivity.property.totalTabaghe) {
                o.setChecked(true);
                break;
            }
        }
        count = vahedTabaghat.getChildCount();
        for (int i = 0; i < count; i++) {
            RadioButton o = (RadioButton) vahedTabaghat.getChildAt(i);
            if (Integer.parseInt(o.getTag().toString()) == AddPropetyActivity.property.tabaghe) {
                o.setChecked(true);
                break;
            }
        }
        count = vahedNo.getChildCount();
        for (int i = 0; i < count; i++) {
            RadioButton o = (RadioButton) vahedNo.getChildAt(i);
            if (Integer.parseInt(o.getTag().toString()) == AddPropetyActivity.property.vahed) {
                o.setChecked(true);
                break;
            }
        }
        count = roomNo.getChildCount();
        for (int i = 0; i < count; i++) {
            RadioButton o = (RadioButton) roomNo.getChildAt(i);
            if (Integer.parseInt(o.getTag().toString()) == AddPropetyActivity.property.rooms) {
                o.setChecked(true);
                break;
            }
        }


        count = tarakom.getChildCount();
        for (int i = 0; i < count; i++) {
            RadioButton o = (RadioButton) tarakom.getChildAt(i);
            if (Integer.parseInt(o.getTag().toString())== AddPropetyActivity.property.tarakom) {
                o.setChecked(true);
                break;
            }
        }
        count = omr.getChildCount();
        for (int i = 0; i < count; i++) {
            RadioButton o = (RadioButton) omr.getChildAt(i);
            if (Integer.parseInt(o.getTag().toString()) == AddPropetyActivity.property.omrSakhteman) {
                o.setChecked(true);
                break;
            }
        }
        count = ab_emtiazat.getChildCount();
        for (int i = 0; i < count; i++) {
            RadioButton o = (RadioButton) ab_emtiazat.getChildAt(i);
            if (Integer.parseInt(o.getTag().toString()) == AddPropetyActivity.property.ab) {
                o.setChecked(true);
                break;
            }
        }count = gaz_emtiazat.getChildCount();
        for (int i = 0; i < count; i++) {
            RadioButton o = (RadioButton) gaz_emtiazat.getChildAt(i);
            if (Integer.parseInt(o.getTag().toString())== AddPropetyActivity.property.gaz) {
                o.setChecked(true);
                break;
            }
        }
        count = bargh_emtiazat.getChildCount();
        for (int i = 0; i < count; i++) {
            RadioButton o = (RadioButton) bargh_emtiazat.getChildAt(i);
            if (Integer.parseInt(o.getTag().toString()) == AddPropetyActivity.property.bargh) {
                o.setChecked(true);
                break;
            }
        }

    }

    public void getProperty() {
        if (masahat.getText().toString() != null && masahat.getText().toString().trim().length() > 0)
            AddPropetyActivity.property.masahat = Integer.parseInt(masahat.getText().toString());
        else
            AddPropetyActivity.property.masahat =0;
        if (zirbana.getText().toString() != null && zirbana.getText().toString().trim().length() > 0)
            AddPropetyActivity.property.zirBana = Integer.parseInt(zirbana.getText().toString());
        else
            AddPropetyActivity.property.zirBana =0;
        if (arse.getText().toString() != null && arse.getText().toString().trim().length() > 0)
                      AddPropetyActivity.property.arseZamin = Integer.parseInt(arse.getText().toString());
        else
            AddPropetyActivity.property.arseZamin =0;
        if (hashye_melk.getText().toString() != null && hashye_melk.getText().toString().trim().length() > 0)
            AddPropetyActivity.property.hashieh = Integer.parseInt(hashye_melk.getText().toString());
        else
            AddPropetyActivity.property.hashieh =0;
        // if (arz_khyaban.getText() != null)
        //   AddPropetyActivity.property.hashieh = Integer.parseInt(arz_khyaban.getText().toString());


        int checkedRadioButtonId = tabaghat.getCheckedRadioButtonId();
        if (checkedRadioButtonId != -1) {
            RadioButton a = (RadioButton) mainView.findViewById(checkedRadioButtonId);
            AddPropetyActivity.property.totalTabaghe = Integer.parseInt(a.getTag().toString());
        }
        checkedRadioButtonId = vahedTabaghat.getCheckedRadioButtonId();
        if (checkedRadioButtonId != -1) {
            RadioButton a = (RadioButton) mainView.findViewById(checkedRadioButtonId);
            AddPropetyActivity.property.tabaghe = Integer.parseInt(a.getTag().toString());
        }
        checkedRadioButtonId = vahedNo.getCheckedRadioButtonId();
        if (checkedRadioButtonId != -1) {
            RadioButton a = (RadioButton) mainView.findViewById(checkedRadioButtonId);
            AddPropetyActivity.property.vahed = Integer.parseInt(a.getTag().toString());
        }
        checkedRadioButtonId = roomNo.getCheckedRadioButtonId();
        if (checkedRadioButtonId != -1) {
            RadioButton a = (RadioButton) mainView.findViewById(checkedRadioButtonId);
            AddPropetyActivity.property.rooms = Integer.parseInt(a.getTag().toString());
        }
        checkedRadioButtonId = tarakom.getCheckedRadioButtonId();
        if (checkedRadioButtonId != -1) {
            RadioButton a = (RadioButton) mainView.findViewById(checkedRadioButtonId);
            AddPropetyActivity.property.tarakom = Integer.parseInt(a.getTag().toString());
        }
        checkedRadioButtonId = omr.getCheckedRadioButtonId();
        if (checkedRadioButtonId != -1) {
            RadioButton a = (RadioButton) mainView.findViewById(checkedRadioButtonId);
            AddPropetyActivity.property.omrSakhteman = Integer.parseInt(a.getTag().toString());
        }

        checkedRadioButtonId = ab_emtiazat.getCheckedRadioButtonId();
        if (checkedRadioButtonId != -1) {
            RadioButton a = (RadioButton) mainView.findViewById(checkedRadioButtonId);
            AddPropetyActivity.property.ab = Integer.parseInt(a.getTag().toString());
        }
        checkedRadioButtonId = gaz_emtiazat.getCheckedRadioButtonId();
        if (checkedRadioButtonId != -1) {
            RadioButton a = (RadioButton) mainView.findViewById(checkedRadioButtonId);
            AddPropetyActivity.property.gaz = Integer.parseInt(a.getTag().toString());
        }
        checkedRadioButtonId = bargh_emtiazat.getCheckedRadioButtonId();
        if (checkedRadioButtonId != -1) {
            RadioButton a = (RadioButton) mainView.findViewById(checkedRadioButtonId);
            AddPropetyActivity.property.bargh = Integer.parseInt(a.getTag().toString());
        }


    }

    class PhotoAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return AddPropetyActivity.property.images.size() + 1;
        }

        // 3
        @Override
        public long getItemId(int position) {
            return AddPropetyActivity.property.images.size() + 1;
        }

        // 4
        @Override
        public Object getItem(int position) {
            return null;
        }

        // 5
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (position == 0) {
                add = new Button(activity);
                add.setText("+");
                convertView = add;
                GridView.LayoutParams layoutParams = new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, Utils.dpToPx(getActivity(), 100));
                add.setLayoutParams(layoutParams);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((AddPropetyActivity) getActivity()).showImageDIalog();
                    }
                });
            } else {
                Property.Image image = AddPropetyActivity.property.images.get(position - 1);
                final View view = layoutInflater.inflate(R.layout.collage_image_item, null);
                convertView = view;
                ImageView imageView = (ImageView) view.findViewById(R.id.imageView5);
                ImageView failedImage = (ImageView) view.findViewById(R.id.failed);
                ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress);
                image.uploadProgressBar = progressBar;
                image.uploadProgressBar.setVisibility(View.VISIBLE);
                image.failedImageView = failedImage;
                image.failedImageView.setVisibility(View.INVISIBLE);
                image.localImageFile = new File(image.localname);
                Bitmap bitmapsimplesize = Bitmap.createScaledBitmap(image.bitmap, (int) getResources().getDimension(R.dimen.advers_image_size), (int) getResources().getDimension(R.dimen.advers_image_size), true);
//                image.bitmap.recycle();
                imageView.setImageBitmap(bitmapsimplesize);
                AddPropetyActivity.property.sendPhotos();


            }

            return convertView;
        }

    }

    public void addImage(final Bitmap bitmapOriginal, File file, String orginal) {
        if (bitmapOriginal != null) {
            Property.Image image = new Property.Image();
            image.bitmap = bitmapOriginal;

            image.orginalPath = orginal;
            image.localname = file.getAbsolutePath();
            if (AddPropetyActivity.property.images.contains(image)) {
                Toast.makeText(getActivity(), "Duplcation Image File", Toast.LENGTH_LONG).show();
                return;
            }
            AddPropetyActivity.property.images.add(image);
            phtoAdapter.notifyDataSetChanged();


        }


    }


    public boolean isValid() {
        return true;
    }


    class ChangeBusinerssCard implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                Property.Image a = (Property.Image) buttonView.getTag();
//                property.imagepath.remove(a);
                a.main = true;
                for (RadioButton bu : imageRadioButtons) {
                    if (bu != buttonView) {
                        bu.setChecked(false);
                    }
                }
            } else {
                Property.Image a = (Property.Image) buttonView.getTag();
//                property.imagepath.remove(a);
                a.main = false;

            }

        }

    }

    ChangeBusinerssCard businerssCard = new ChangeBusinerssCard();

//    public void addImage(final String image_url) {
//
//        if (image_url != null) {
//            Vector<Property.Image> showImage = property.getShowImage();
//            if (showImage.size() < 5) {
//                boolean exist = false;
//                for (int i = 0; i < showImage.size(); i++) {
//                    Property.Image image = showImage.get(i);
//                    if (image.localname != null && image.localname.equals(image_url)) {
//                        exist = true;
//                        break;
//                    }
//                }
//                if (exist) {
//                    Toast.makeText(getContext(), getActivity().getString(R.string.exist_image), Toast.LENGTH_SHORT).show();
//                } else {
//                    Property.Image image = property.addImage(0, image_url, null, 1, 0);
//                    addImage(image);
////                    if (property.getImageCount() == 5) {
//////                        photoButton.setVisibility(View.GONE);
//////                        designCardButton.setVisibility(View.GONE);
////                        imagebutlayout.setVisibility(View.GONE);
////
////                    } else {
////                        imagebutlayout.setVisibility(View.VISIBLE);
////
////                    }
//
//                }
//            } else {
//                Toast.makeText(getContext(), getActivity().getString(R.string.max_image_cnt), Toast.LENGTH_SHORT).show();
//            }
//        }
//
//    }

//    public void addImage(final Property.Image image) {
//        if (image != null && image.localname != null) {
//            Bitmap bitmapOriginal = BitmapFactory.decodeFile(image.localname);
//            if (bitmapOriginal == null) {
//                image.deleted = true;
//                return;
//            }
//            imagesLayout.setVisibility(View.VISIBLE);
//            horizentalImage.setVisibility(View.VISIBLE);
//            telegram_lable.setVisibility(View.VISIBLE);
//            final View view = layoutInflater.inflate(R.layout.collage_image_item, null);
//            ImageView imageView = (ImageView) view.findViewById(R.id.imageView5);
//            final RadioButton radioButton = (RadioButton) view.findViewById(R.id.radioButton);
//            radioButton.setTag(image);
//            radioButton.setOnCheckedChangeListener(businerssCard);
//            radioButton.setChecked(image.main);
//            imageRadioButtons.add(radioButton);
//            ImageView close = (ImageView) view.findViewById(R.id.imageView6);
//
//            Bitmap bitmapsimplesize = Bitmap.createScaledBitmap(bitmapOriginal, (int) getResources().getDimension(R.dimen.advers_image_size), (int) getResources().getDimension(R.dimen.advers_image_size), true);
//            bitmapOriginal.recycle();
//            imageView.setImageBitmap(bitmapsimplesize);
//            close.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    view.setVisibility(View.GONE);
//                    if (radioButton.isChecked()) {
//                        image.main = false;
//                    }
//                    image.deleted = true;
//                    imagebutlayout.setVisibility(View.VISIBLE);
//
////                    image.remove(image_url);
////                    if (images.size() == 0)
//                    Vector<Property.Image> showImage = property.getShowImage();
//                    if (showImage.size() > 0) {
//                        horizentalImage.setVisibility(View.VISIBLE);
//                        telegram_lable.setVisibility(View.VISIBLE);
//                    } else {
//
//                        horizentalImage.setVisibility(View.GONE);
//                        telegram_lable.setVisibility(View.GONE);
//                    }
//
//
//                }
//            });
//            imagesLayout.addView(view, Utils.dpToPx(activity,100), Utils.dpToPx(activity,100));
//
//        }
//
//
//    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

//    public Property getProperty() {
//
//        if (property != null) {
//
//            property.title = title.getText().toString();
//
//            property.desc = desc.getText().toString();
//            property.tel = Utils.asciiNumners(tel.getText().toString());
//            property.mobile = Utils.asciiNumners(mobile.getText().toString());
//            property.email = email.getText().toString();
//            property.telegram = telegram.getText().toString();
//            property.name = name.getText().toString();
//            property.address = address.getText().toString();
//
//        }
//        return property;
//    }


//    public void reFill() {
//        fillUserjob(this.property);
//    }
//
//    private void fillUserjob(Property a) {
//        if (a == null)
//            return;
//        if (imagesLayout == null)
//            return;
//        imagesLayout.removeAllViews();
//
//        if (a.name != null)
//            name.setText(a.name);
//        if (a.desc != null)
//            desc.setText(a.desc);
//        if (a.tel != null)
//            tel.setText(a.tel);
//        if (a.mobile != null && a.mobile.trim().length() != 0)
//            mobile.setText(a.mobile);
//        else if (a.local_id == 0 && UserConfig.phone != null && !UserConfig.phone.equals("-1")) {
//            mobile.setText(UserConfig.phone);
//        }
//        if (a.email != null)
//            email.setText(a.email);
//        if (a.title != null)
//            title.setText(a.title);
//        if (a.telegram != null)
//            telegram.setText(a.telegram);
//        if (a.address != null)
//            address.setText(a.address);
//
//
//
//
//
//        Node node = DBAdapter.getInstance().allNodes.get(a.nodeid);
//        if (node != null) {
//            noteid.setText(node.path);
//            expandableLinearLayout.setVisibility(View.VISIBLE);
//        } else {
//            noteid.setText(R.string.selection);
//            expandableLinearLayout.setVisibility(View.INVISIBLE);
//        }
//        Vector<Property.Image> showImage = property.getShowImage();
//        for (int i = 0; i < showImage.size(); i++) {
//            Property.Image image = showImage.get(i);
//
//            addImage(image);
//
//        }
//        if (showImage.size() == 0) {
//            horizentalImage.setVisibility(View.GONE);
//            telegram_lable.setVisibility(View.GONE);
//        }
//        if (showImage.size() == 5) {
//            imagebutlayout.setVisibility(View.GONE);
//
//        }
//
//
//        Region regions = DBAdapter.getInstance().getRegion(a.region);
//        if (regions != null) {
//            region.setText(regions.name);
//        } else {
//            region.setText(R.string.region_select);
//        }
//        City c = DBAdapter.getInstance().cities.get(property.city);
//        if (c != null) {
//            city.setText(c.name);
//        } else {
//            city.setText(R.string.change_city);
//        }
//
//
//    }
//
//    public void setNodeType(Node text) {
//        property.nodeid = text.id;
//        if (property.city == 0) {
//            property.city = UserConfig.city;
//        }
//        City c = DBAdapter.getInstance().cities.get(property.city);
//        if (c != null) {
//            city.setText(c.name);
//        } else {
//            city.setText(R.string.change_city);
//        }
//        if (noteid != null)
//            noteid.setText(text.path);
//        if (expandableLinearLayout.getVisibility() != View.VISIBLE) {
//            expandableLinearLayout.setVisibility(View.VISIBLE);
//            expandableLinearLayout.startAnimation(slideUp2Down);
//        }
//    }
//
//    public void setRegion(Region r) {
//        property.region = r.id;
//        noteid.setText(r.name);
//    }

//    public static boolean isEmailValid(String email) {
//        boolean isValid = false;
//
//        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
//        CharSequence inputStr = email;
//
//        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(inputStr);
//        if (matcher.matches()) {
//            isValid = true;
//        }
//        return isValid;
//    }
}
