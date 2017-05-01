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
import android.widget.TextView;
import android.widget.Toast;

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
import ir.mehdi.kelid.utils.Utils;

/**
 * Created by Iman on 8/9/2016.
 */
public class PropertyCreateFragment extends Fragment implements Constant {
    TextInputLayout nameLayout, titleLayout, adverLayout, descLayout, telLayout, mobileLayout, emailLayout, telegramLayout, addressLayout;
    Button send;
    View mainView;
    Animation slideUp2Down;
    View telegram_lable;

    LinearLayout expandableLinearLayout;
    Property property;
    LayoutInflater layoutInflater;
    AddPropetyActivity activity;
    //    Vector<String> images = new Vector<>();
    Vector<RadioButton> imageRadioButtons = new Vector<>();
    LinearLayout imagesLayout;
    View horizentalImage;
//    int node_id = -1;

    public void setActivity(AddPropetyActivity activity) {
        this.activity = activity;
    }

    TextView noteid;
    EditText name, title, adver, desc, tel, mobile, email, telegram, address;
    Button photoButton, region, city;//designCardButton
    LinearLayout imagebutlayout;
    CheckBox noon, evening, moorning, boarding, bike, cardReader, namevisible;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mainView == null) {
            slideUp2Down = AnimationUtils.loadAnimation(activity, R.anim.create_node_select_anim);

            layoutInflater = inflater;
            mainView = inflater.inflate(R.layout.fragment_create_property, null);
            send = (Button) mainView.findViewById(R.id.send);
            final TextView rule = (TextView) mainView.findViewById(R.id.rule_accept);
            send.setTextColor(Color.GRAY);
            send.setClickable(false);
            CheckBox checkBox = (CheckBox) mainView.findViewById(R.id.rulecheck);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        send.setTextColor(Color.WHITE);
                        send.setClickable(true);
                        send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                activity.doneClicked();
                            }
                        });

                    } else {
                        send.setTextColor(Color.GRAY);
                        send.setClickable(false);
                        send.setOnClickListener(null);

                    }
                }
            });


            SpannableString content = new SpannableString(getString(R.string.rule_accept));
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            rule.setText(content);


            ((TextView) mainView.findViewById(R.id.max_photo)).setTypeface(FanoosApplication.BYEKAN_NORMAL);
            expandableLinearLayout = (LinearLayout) mainView.findViewById(R.id.expandale);
            noon = (CheckBox) mainView.findViewById(R.id.noon);
            namevisible = (CheckBox) mainView.findViewById(R.id.namevisible);


            rule.setTypeface(KelidApplication.DIGIT_NORMAL);
//            SpannableString content = new SpannableString(activity.getString(R.string.rule_accept));
//            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//            rule.setText(content);
            rule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent a = new Intent(activity, FanoosRuleActivity.class);
                    activity.startActivity(a);
                }
            });
            evening = (CheckBox) mainView.findViewById(R.id.evening);
            moorning = (CheckBox) mainView.findViewById(R.id.moorning);
            boarding = (CheckBox) mainView.findViewById(R.id.boarding);
            bike = (CheckBox) mainView.findViewById(R.id.bike);
            cardReader = (CheckBox) mainView.findViewById(R.id.card_reader);


            name = (EditText) mainView.findViewById(R.id.nameEditText);
            telegram_lable = mainView.findViewById(R.id.telegram_lable);
            title = (EditText) mainView.findViewById(R.id.titleEditText);
            adver = (EditText) mainView.findViewById(R.id.adversEditText);
            desc = (EditText) mainView.findViewById(R.id.descriptionEditText);
            tel = (EditText) mainView.findViewById(R.id.telephoneEditText);
            mobile = (EditText) mainView.findViewById(R.id.mobileEditText);

            email = (EditText) mainView.findViewById(R.id.emailEditText);
            address = (EditText) mainView.findViewById(R.id.addressEditText);
            telegram = (EditText) mainView.findViewById(R.id.telegramEditText);

            nameLayout = (TextInputLayout) mainView.findViewById(R.id.input_layout_name);
            titleLayout = (TextInputLayout) mainView.findViewById(R.id.input_layout_title);
            adverLayout = (TextInputLayout) mainView.findViewById(R.id.input_layout_advers);
            descLayout = (TextInputLayout) mainView.findViewById(R.id.input_layout_description);
            telLayout = (TextInputLayout) mainView.findViewById(R.id.input_layout_telephone);
            mobileLayout = (TextInputLayout) mainView.findViewById(R.id.input_layout_mobile);
            emailLayout = (TextInputLayout) mainView.findViewById(R.id.input_layout_email);
            addressLayout = (TextInputLayout) mainView.findViewById(R.id.input_layout_address);
            telegramLayout = (TextInputLayout) mainView.findViewById(R.id.input_layout_telegram);


            telegram.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {


                }

                @Override
                public void afterTextChanged(Editable s) {
                    String text = telegram.getText().toString();
                    if (text != null && text.length() > 0 && !text.startsWith("@"))
                        s.insert(0, "@");
                    if (text != null && text.length() > 0 && text.equals("@")) {
                        s.delete(0, 1);
                    }


                }
            });
            region = (Button) mainView.findViewById(R.id.region);
            city = (Button) mainView.findViewById(R.id.city);
            city.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent a = new Intent(activity, CityActivity.class);
                    a.putExtra("select_city", true);

                    activity.startActivityForResult(a,CITY_SELECT);
//                    dialog.dismiss();
                }
            });
            Region[] regionss = DBAdapter.getInstance().getRegion(UserConfig.city, "");
            if (region == null || regionss.length == 0) {
                region.setEnabled(false);
            }
            region.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRegionDIalog();

                }
            });
            region.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRegionDIalog();

                }
            });
//            designCardButton = (Button) mainView.findViewById(R.id.design_card);
//            designCardButton.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//
//                    Intent intent = new Intent(activity, CollageActivity.class);
//                    intent.putExtra("user_job_id", activity.userJobbId);
//
//                    activity.startActivityForResult(intent, DESIGN_CARD_REQUEST);
//                }
//            });


            noteid = ((TextView) mainView.findViewById(R.id.node_type_text));
            noteid.setTypeface(null, Typeface.BOLD);
            noteid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.showNodeDialog();
                }
            });
            photoButton = (Button) mainView.findViewById(R.id.photoButton);
            imagebutlayout = (LinearLayout) mainView.findViewById(R.id.imagebutlayout);
            photoButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(activity,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {


                        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {


                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    Constant.SAVE_GRANT_REQUERST_FOR_JOb);

                        } else {


                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    Constant.SAVE_GRANT_REQUERST_FOR_JOb);

                        }
                    } else {
                        activity.showImageDIalog();

                    }

                }
            });


            imagesLayout = (LinearLayout) mainView.findViewById(R.id.imagesLayout);
            horizentalImage = mainView.findViewById(R.id.horizontalScrollView);
//            imagesLayout.setVisibility(View.GONE);
            setUserJob(property);

        }


        return mainView;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//    }

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
                region.setText(item.name);


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

    public void changeCity(int city1) {
        property.city=city1;
        City c = DBAdapter.getInstance().cities.get(property.city);
        if (c != null) {
            city.setText(c.name);
        } else {
            city.setText(R.string.change_city);
        }
        region.setText(R.string.region_select);
        property.region=0;
//        Region regions = DBAdapter.getInstance().getRegion(a.region);
//        if (regions != null) {
//            region.setText(regions.name);
//        } else {
//            region.setText(R.string.region_select);
//        }

    }

//    public void clear() {
//        imagesLayout.removeAllViews();
////        images.clear();
////        node_id = -1;
//        adver.setText("");
//        name.setText("");
//        desc.setText("");
//        tel.setText("");
//        mobile.setText("");
//        email.setText("");
//        address.setText("");
//        title.setText("");
//        telegram.setText("");
//        adver.setText("");
//        noteid.setText("");
//        region.setText(R.string.region_select);
//
//
//    }

    class ChangeBusinerssCard implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                UserJob.Image a = (UserJob.Image) buttonView.getTag();
//                property.imagepath.remove(a);
                a.main = true;
                for (RadioButton bu : imageRadioButtons) {
                    if (bu != buttonView) {
                        bu.setChecked(false);
                    }
                }
            } else {
                UserJob.Image a = (UserJob.Image) buttonView.getTag();
//                property.imagepath.remove(a);
                a.main = false;

            }

        }

    }

    ChangeBusinerssCard businerssCard = new ChangeBusinerssCard();

    public void addImage(final String image_url) {

        if (image_url != null) {
            Vector<UserJob.Image> showImage = property.getShowImage();
            if (showImage.size() < 5) {
                boolean exist = false;
                for (int i = 0; i < showImage.size(); i++) {
                    UserJob.Image image = showImage.get(i);
                    if (image.localname != null && image.localname.equals(image_url)) {
                        exist = true;
                        break;
                    }
                }
                if (exist) {
                    Toast.makeText(getContext(), getActivity().getString(R.string.exist_image), Toast.LENGTH_SHORT).show();
                } else {
                    UserJob.Image image = property.addImage(0, image_url, null, 1, 0);
                    addImage(image);
                    if (property.getImageCount() == 5) {
//                        photoButton.setVisibility(View.GONE);
//                        designCardButton.setVisibility(View.GONE);
                        imagebutlayout.setVisibility(View.GONE);

                    } else {
                        imagebutlayout.setVisibility(View.VISIBLE);

                    }

                }
            } else {
                Toast.makeText(getContext(), getActivity().getString(R.string.max_image_cnt), Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void addImage(final UserJob.Image image) {
        if (image != null && image.localname != null) {
            Bitmap bitmapOriginal = BitmapFactory.decodeFile(image.localname);
            if (bitmapOriginal == null) {
                image.deleted = true;
                return;
            }
            imagesLayout.setVisibility(View.VISIBLE);
            horizentalImage.setVisibility(View.VISIBLE);
            telegram_lable.setVisibility(View.VISIBLE);
            final View view = layoutInflater.inflate(R.layout.collage_image_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView5);
            final RadioButton radioButton = (RadioButton) view.findViewById(R.id.radioButton);
            radioButton.setTag(image);
            radioButton.setOnCheckedChangeListener(businerssCard);
            radioButton.setChecked(image.main);
            imageRadioButtons.add(radioButton);
            ImageView close = (ImageView) view.findViewById(R.id.imageView6);

            Bitmap bitmapsimplesize = Bitmap.createScaledBitmap(bitmapOriginal, (int) getResources().getDimension(R.dimen.advers_image_size), (int) getResources().getDimension(R.dimen.advers_image_size), true);
            bitmapOriginal.recycle();
            imageView.setImageBitmap(bitmapsimplesize);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.setVisibility(View.GONE);
                    if (radioButton.isChecked()) {
                        image.main = false;
                    }
                    image.deleted = true;
                    imagebutlayout.setVisibility(View.VISIBLE);

//                    image.remove(image_url);
//                    if (images.size() == 0)
                    Vector<UserJob.Image> showImage = property.getShowImage();
                    if (showImage.size() > 0) {
                        horizentalImage.setVisibility(View.VISIBLE);
                        telegram_lable.setVisibility(View.VISIBLE);
                    } else {

                        horizentalImage.setVisibility(View.GONE);
                        telegram_lable.setVisibility(View.GONE);
                    }


                }
            });
            imagesLayout.addView(view, Utils.getDPforPixel(100), Utils.getDPforPixel(100));

        }


    }

    public boolean validationEmpty() {
        boolean check = true;
        if (mobile.getText() == null || mobile.getText().toString().trim().isEmpty()) {
            check = false;
            mobileLayout.setError(getString(R.string.empty_error));
            requestFocus(mobile);
        } else {
            mobileLayout.setErrorEnabled(false);
        }
        if (title.getText() == null || title.getText().toString().trim().isEmpty()) {
            check = false;
            titleLayout.setError(getString(R.string.empty_error));
            requestFocus(title);

        } else {
            titleLayout.setErrorEnabled(false);
        }
        if (desc.getText() == null || desc.getText().toString().trim().isEmpty()) {
            check = false;
            descLayout.setError(getString(R.string.empty_error));
            requestFocus(desc);

        } else {
            descLayout.setErrorEnabled(false);
        }
        if (name.getText() == null || name.getText().toString().trim().isEmpty()) {

            nameLayout.setError(getString(R.string.empty_error));
            requestFocus(name);
            check = false;

        } else {
            nameLayout.setErrorEnabled(false);
        }
        return check;
    }

    public boolean validateEmail() {
        ;
        if ((email.getText() != null && !email.getText().toString().trim().isEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches())) {
            emailLayout.setError(getString(R.string.err_msg_email));
            requestFocus(email);
            return false;
        }

        emailLayout.setErrorEnabled(false);
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public Property getProperty() {

        if (property != null) {

            property.title = title.getText().toString();

            property.desc = desc.getText().toString();
            property.tel = Utils.asciiNumners(tel.getText().toString());
            property.mobile = Utils.asciiNumners(mobile.getText().toString());
            property.email = email.getText().toString();
            property.telegram = telegram.getText().toString();
            property.name = name.getText().toString();
            property.address = address.getText().toString();

        }
        return property;
    }

    private void setProperty(Property property) {
        this.property = property;
        fillUserjob(this.property);
    }

    public void setUserJob(long userjobid) {
        if (userjobid == -2) {
            property = UserConfig.loadLast();
        } else {
            property = MySqliteOpenHelper.getInstance().myPropertys.get(userjobid);
        }
        property.token = null;
    }

    public void reFill() {
        fillUserjob(this.property);
    }

    private void fillUserjob(Property a) {
        if (a == null)
            return;
        if (imagesLayout == null)
            return;
        imagesLayout.removeAllViews();

        if (a.name != null)
            name.setText(a.name);
        if (a.desc != null)
            desc.setText(a.desc);
        if (a.tel != null)
            tel.setText(a.tel);
        if (a.mobile != null && a.mobile.trim().length() != 0)
            mobile.setText(a.mobile);
        else if (a.local_id == 0 && UserConfig.phone != null && !UserConfig.phone.equals("-1")) {
            mobile.setText(UserConfig.phone);
        }
        if (a.email != null)
            email.setText(a.email);
        if (a.title != null)
            title.setText(a.title);
        if (a.telegram != null)
            telegram.setText(a.telegram);
        if (a.address != null)
            address.setText(a.address);


        noon.setChecked(a.noon);
        namevisible.setChecked(a.namevisible);

        evening.setChecked(a.evening);

        moorning.setChecked(a.moorning);

        boarding.setChecked(a.boarding);

        bike.setChecked(a.bike);

        cardReader.setChecked(a.cardReader);


        Node node = DBAdapter.getInstance().allNodes.get(a.nodeid);
        if (node != null) {
            noteid.setText(node.path);
            expandableLinearLayout.setVisibility(View.VISIBLE);
        } else {
            noteid.setText(R.string.selection);
            expandableLinearLayout.setVisibility(View.INVISIBLE);
        }
        Vector<UserJob.Image> showImage = property.getShowImage();
        for (int i = 0; i < showImage.size(); i++) {
            UserJob.Image image = showImage.get(i);

            addImage(image);

        }
        if (showImage.size() == 0) {
            horizentalImage.setVisibility(View.GONE);
            telegram_lable.setVisibility(View.GONE);
        }
        if (showImage.size() == 5) {
            imagebutlayout.setVisibility(View.GONE);

        }


        Region regions = DBAdapter.getInstance().getRegion(a.region);
        if (regions != null) {
            region.setText(regions.name);
        } else {
            region.setText(R.string.region_select);
        }
        City c = DBAdapter.getInstance().cities.get(property.city);
        if (c != null) {
            city.setText(c.name);
        } else {
            city.setText(R.string.change_city);
        }


    }

    public void setNodeType(Node text) {
        property.nodeid = text.id;
        if (property.city == 0) {
            property.city = UserConfig.city;
        }
        City c = DBAdapter.getInstance().cities.get(property.city);
        if (c != null) {
            city.setText(c.name);
        } else {
            city.setText(R.string.change_city);
        }
        if (noteid != null)
            noteid.setText(text.path);
        if (expandableLinearLayout.getVisibility() != View.VISIBLE) {
            expandableLinearLayout.setVisibility(View.VISIBLE);
            expandableLinearLayout.startAnimation(slideUp2Down);
        }
    }

    public void setRegion(Region r) {
        property.region = r.id;
        noteid.setText(r.name);
    }

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
