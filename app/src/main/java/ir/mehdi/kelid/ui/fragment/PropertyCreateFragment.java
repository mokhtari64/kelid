package ir.mehdi.kelid.ui.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Vector;

import ir.mehdi.kelid.Constant;
import ir.mehdi.kelid.KelidApplication;
import ir.mehdi.kelid.R;
import ir.mehdi.kelid.UserConfig;
import ir.mehdi.kelid.db.DBAdapter;
import ir.mehdi.kelid.db.MySqliteOpenHelper;
import ir.mehdi.kelid.model.City;
import ir.mehdi.kelid.model.Node;
import ir.mehdi.kelid.model.Property;
import ir.mehdi.kelid.model.Region;
import ir.mehdi.kelid.ui.AddPropetyActivity;
import ir.mehdi.kelid.ui.CityActivity;
import ir.mehdi.kelid.utils.Utils;

/**
 * Created by Iman on 8/9/2016.
 */
public class PropertyCreateFragment extends Fragment implements Constant {
    TextInputLayout nameLayout, titleLayout, adverLayout, descLayout, telLayout, mobileLayout, emailLayout, telegramLayout, addressLayout;
    EditText masahat, zirbana, arse, arz_khyaban, hashye_melk;
    RadioGroup tabaghat, vahedTabaghat, vahedNo, roomNo, tarakom, omr, ab_emtiazat, gaz_emtiazat, bargh_emtiazat;
    ToggleButton coler_abi, coler_gazi, coler_gazi_spilet, chiller, pakage, shomine, shofaj, garmayesh_az_kaf, ab_garmkon, ab_garmkon_divari, ab_garmkon_khorshidi;
    ToggleButton moket, parket, seramik, sang, mozaeik, siman, rang_plastik, rang_roghan, kaghazdivari;
    ToggleButton chob, divarkob, rang_hajim, divar_sang, divar_sofal, divar_kompozit, divar_chob, divar_shishe, divar_siman, divar_seramik, divar_geranit;
    ToggleButton kabinet_felezi, kabinet_mdf, kabinet_chob, kabinet_melamine, kabinet_higlas;
    ToggleButton asansor, parking, anbari, service_farangi, gachbori, rangkari, jakozi, iphone_tasviri, darb_barghi, panjere_dojedare, upvc, komod_divari, security_system;
    ToggleButton system_bargh_hoshmand, system_etfa_harigh, system_alarm_gaz, havasaz, estakhr, labi, seraydari, faza_sabz, manba_ab, pomp_ab, bargh_ezterari, hood;
    ToggleButton norpardazi_dakheli, norpardazi_nama, van, hamam_master, sona, alachigh, darb_zed_serghat, pele_ezterari,
            system_alarm_atashsozi, jaro_markazi, balkon, kaf_seramik, mdf;

//    int selectedIdtedId = radioGroup.getCheckedRadioButtonId();
//

//    radioButton = (RadioButton) findViewById(selectedId);


    View mainView;
    Animation slideUp2Down;
    View telegram_lable;

    LinearLayout expandableLinearLayout;
    Property property;
    LayoutInflater layoutInflater;
    AddPropetyActivity activity;

    Vector<RadioButton> imageRadioButtons = new Vector<>();
    LinearLayout imagesLayout;
    View horizentalImage;

//    int node_id = -1;

    public void setActivity(AddPropetyActivity activity) {
        this.activity = activity;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mainView == null) {
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
            coler_abi=(ToggleButton) mainView.findViewById(R.id.coler_abi);
            coler_gazi=(ToggleButton) mainView.findViewById(R.id.coler_gazi);
            coler_gazi_spilet=(ToggleButton) mainView.findViewById(R.id.coler_gazi_spilet);
            chiller=(ToggleButton) mainView.findViewById(R.id.chiller);
            pakage=(ToggleButton) mainView.findViewById(R.id.pakage);
            shomine=(ToggleButton) mainView.findViewById(R.id.shomine);
            shofaj=(ToggleButton) mainView.findViewById(R.id.shofaj);
            garmayesh_az_kaf=(ToggleButton) mainView.findViewById(R.id.garmayesh_az_kaf);
            ab_garmkon=(ToggleButton) mainView.findViewById(R.id.ab_garmkon);
            ab_garmkon_divari=(ToggleButton) mainView.findViewById(R.id.ab_garmkon_divari);
            ab_garmkon_khorshidi=(ToggleButton) mainView.findViewById(R.id.ab_garmkon_khorshidi);
            moket=(ToggleButton) mainView.findViewById(R.id.moket);
            parket=(ToggleButton) mainView.findViewById(R.id.parket);
            seramik=(ToggleButton) mainView.findViewById(R.id.seramik);
            sang=(ToggleButton) mainView.findViewById(R.id.sang);
            mozaeik=(ToggleButton) mainView.findViewById(R.id.mozaeik);
            siman=(ToggleButton) mainView.findViewById(R.id.siman);
            rang_plastik=(ToggleButton) mainView.findViewById(R.id.rang_plastik);
            rang_roghan=(ToggleButton) mainView.findViewById(R.id.rang_roghan);
            kaghazdivari=(ToggleButton) mainView.findViewById(R.id.kaghazdivari);
            chob=(ToggleButton) mainView.findViewById(R.id.chob);
            divarkob=(ToggleButton) mainView.findViewById(R.id.divarkob);
            rang_hajim=(ToggleButton) mainView.findViewById(R.id.rang_hajim);
            divar_sang=(ToggleButton) mainView.findViewById(R.id.divar_sang);
            divar_sofal=(ToggleButton) mainView.findViewById(R.id.divar_sofal);
            divar_kompozit=(ToggleButton) mainView.findViewById(R.id.divar_kompozit);
            divar_chob=(ToggleButton) mainView.findViewById(R.id.divar_chob);
            divar_shishe=(ToggleButton) mainView.findViewById(R.id.divar_shishe);
            divar_siman=(ToggleButton) mainView.findViewById(R.id.divar_siman);
            divar_seramik=(ToggleButton) mainView.findViewById(R.id.divar_seramik);
            divar_geranit=(ToggleButton) mainView.findViewById(R.id.divar_geranit);
            kabinet_felezi=(ToggleButton) mainView.findViewById(R.id.kabinet_felezi);
            kabinet_mdf=(ToggleButton) mainView.findViewById(R.id.kabinet_mdf);
            kabinet_chob=(ToggleButton) mainView.findViewById(R.id.kabinet_chob);
            kabinet_melamine=(ToggleButton) mainView.findViewById(R.id.kabinet_melamine);
            kabinet_higlas=(ToggleButton) mainView.findViewById(R.id.kabinet_higlas);
            asansor=(ToggleButton) mainView.findViewById(R.id.asansor);
            parking=(ToggleButton) mainView.findViewById(R.id.parking);
            anbari=(ToggleButton) mainView.findViewById(R.id.anbari);
            service_farangi=(ToggleButton) mainView.findViewById(R.id.service_farangi);
            gachbori=(ToggleButton) mainView.findViewById(R.id.gachbori);
            rangkari=(ToggleButton) mainView.findViewById(R.id.rangkari);
            jakozi=(ToggleButton) mainView.findViewById(R.id.jakozi);
            iphone_tasviri=(ToggleButton) mainView.findViewById(R.id.iphone_tasviri);
            darb_barghi=(ToggleButton) mainView.findViewById(R.id.darb_barghi);
            panjere_dojedare=(ToggleButton) mainView.findViewById(R.id.panjere_dojedare);
            upvc=(ToggleButton) mainView.findViewById(R.id.upvc);
            komod_divari=(ToggleButton) mainView.findViewById(R.id.komod_divari);
            security_system=(ToggleButton) mainView.findViewById(R.id.security_system);
            system_bargh_hoshmand=(ToggleButton) mainView.findViewById(R.id.system_bargh_hoshmand);
            system_etfa_harigh=(ToggleButton) mainView.findViewById(R.id.system_etfa_harigh);
            system_alarm_gaz=(ToggleButton) mainView.findViewById(R.id.system_alarm_gaz);
            havasaz=(ToggleButton) mainView.findViewById(R.id.havasaz);
            estakhr=(ToggleButton) mainView.findViewById(R.id.estakhr);
            labi=(ToggleButton) mainView.findViewById(R.id.labi);
            seraydari=(ToggleButton) mainView.findViewById(R.id.seraydari);
            faza_sabz=(ToggleButton) mainView.findViewById(R.id.faza_sabz);
            manba_ab=(ToggleButton) mainView.findViewById(R.id.manba_ab);
            pomp_ab=(ToggleButton) mainView.findViewById(R.id.pomp_ab);
            bargh_ezterari=(ToggleButton) mainView.findViewById(R.id.bargh_ezterari);
            hood=(ToggleButton) mainView.findViewById(R.id.hood);
            norpardazi_dakheli=(ToggleButton) mainView.findViewById(R.id.norpardazi_dakheli);
            norpardazi_nama=(ToggleButton) mainView.findViewById(R.id.norpardazi_nama);
            van=(ToggleButton) mainView.findViewById(R.id.van);
            hamam_master=(ToggleButton) mainView.findViewById(R.id.hamam_master);
            sona=(ToggleButton) mainView.findViewById(R.id.sona);
            alachigh=(ToggleButton) mainView.findViewById(R.id.alachigh);
            darb_zed_serghat=(ToggleButton) mainView.findViewById(R.id.darb_zed_serghat);
            pele_ezterari=(ToggleButton) mainView.findViewById(R.id.pele_ezterari);
            system_alarm_atashsozi=(ToggleButton) mainView.findViewById(R.id.system_alarm_atashsozi);
            jaro_markazi=(ToggleButton) mainView.findViewById(R.id.jaro_markazi);
            balkon=(ToggleButton) mainView.findViewById(R.id.balkon);
            kaf_seramik=(ToggleButton) mainView.findViewById(R.id.kaf_seramik);
            mdf=(ToggleButton) mainView.findViewById(R.id.mdf);

        }
        return mainView;
    }




    public void showRegionDIalog() {
        Region[] regionss = DBAdapter.getInstance().getRegion(property.city, "");
        if (regionss == null || regionss.length == 0) {
            Toast.makeText(activity, activity.getString(R.string.no_region), Toast.LENGTH_SHORT).show();
            return;
        }
        final Dialog dialog = new Dialog(activity);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setTitle(R.string.region);
        Window window = dialog.getWindow();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = (int) (displaymetrics.widthPixels * 0.8);
        int height = (int) (displaymetrics.heightPixels * 0.9);
        window.setLayout(width, height);
        dialog.setContentView(R.layout.collage_region_dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            dialog.findViewById(R.id.main_content).setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        Button city = (Button) dialog.findViewById(R.id.button2);
        dialog.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        EditText editText = (EditText) dialog.findViewById(R.id.editText);
        final ListView listView = (ListView) dialog.findViewById(R.id.listView2);
        ArrayAdapter<Region> adapter = new ArrayAdapter<Region>(activity, android.R.layout.simple_list_item_1, regionss);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Region item = (Region) parent.getAdapter().getItem(position);
                dialog.dismiss();
                property.region = item.id;


            }
        });
        city.setText(DBAdapter.getInstance().cities.get(property.city).name);
//        city.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//  activity.regionDialog = true;
//        Intent a = new Intent(activity, CityActivity.class);
//        a.putExtra("change_city", true);
//
//        startActivity(a);
//        dialog.dismiss();
//            }
//        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Region[] region = DBAdapter.getInstance().getRegion(UserConfig.city, s.toString());

                ArrayAdapter<Region> adapter = new ArrayAdapter<Region>(activity, android.R.layout.simple_list_item_1, region);
                listView.setAdapter(adapter);
            }
        });


        dialog.show();

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
