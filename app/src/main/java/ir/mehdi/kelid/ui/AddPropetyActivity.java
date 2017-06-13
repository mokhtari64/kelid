package ir.mehdi.kelid.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ir.mehdi.kelid.Constant;
import ir.mehdi.kelid.KelidApplication;
import ir.mehdi.kelid.R;
import ir.mehdi.kelid.UserConfig;
import ir.mehdi.kelid.collage.ImageFilePath;
import ir.mehdi.kelid.db.MySqliteOpenHelper;
import ir.mehdi.kelid.model.Node;
import ir.mehdi.kelid.model.Property;
import ir.mehdi.kelid.service.SMSDelegate;
import ir.mehdi.kelid.service.SMSReceiver;
import ir.mehdi.kelid.service.ServiceDelegate;
import ir.mehdi.kelid.service.VolleyService;
import ir.mehdi.kelid.ui.fragment.ActivationCodeFragment;
import ir.mehdi.kelid.ui.fragment.InfoCreateFragment;
import ir.mehdi.kelid.ui.fragment.MapFragment;
import ir.mehdi.kelid.ui.fragment.PropertyCreateFragment;
import ir.mehdi.kelid.ui.fragment.TestFragment;
import ir.mehdi.kelid.ui.fragment.UserPhoneFragment;
import ir.mehdi.kelid.utils.FileUtils;
import ir.mehdi.kelid.utils.Utils;

public class AddPropetyActivity extends KelidActivity implements Constant, ServiceDelegate, SMSDelegate, View.OnClickListener, NodeFragmentDialog.NodeDialogListener, StepFragmentDelegate {
    String curretnRequestCode;
    public static Property property;
    SMSReceiver receiver;


    TextView nextTextView, cancelTextView;
    ImageView backImageView;


    boolean cancel = false;

    public static Handler mainHandler;


    public long userJobbId = -2;


    NodeFragmentDialog nodeFragmentDialog;
    MapFragment mapFragment;

    InfoCreateFragment infoCreateFragment;
    PropertyCreateFragment propertyCreateFragment;
    TestFragment testFragment;
    UserPhoneFragment userPhoneFragment;
    ActivationCodeFragment activationCodeFragment;

    SweetAlertDialog progressDialog;
    int currentStep = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainHandler = new Handler(Looper.getMainLooper());
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        setContentView(R.layout.activity_add_property);
        nextTextView = (TextView) findViewById(R.id.next);
        cancelTextView = (TextView) findViewById(R.id.cancel);
        backImageView = (ImageView) findViewById(R.id.back);

        nextTextView.setOnClickListener(this);
        backImageView.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        propertyCreateFragment = new PropertyCreateFragment();
        testFragment = new TestFragment();
//        propertyCreateFragment = new TestFragment();
        infoCreateFragment = new InfoCreateFragment();

        propertyCreateFragment.setActivity(this);
        infoCreateFragment.setActivity(this);
        userPhoneFragment = new UserPhoneFragment();
        userPhoneFragment.setDelegate(this);


        activationCodeFragment = new ActivationCodeFragment();
        activationCodeFragment.setDelegate(this);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        fm.popBackStack(null, android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
//        ft.add(R.id.fragment_container, testFragment);
        ft.add(R.id.fragment_container, propertyCreateFragment);
        ft.commit();


    }


    public void showNodeDialog() {
        if (nodeFragmentDialog == null) {
            nodeFragmentDialog = new NodeFragmentDialog();
            nodeFragmentDialog.setNodeDialogListener(this);
        }
        nodeFragmentDialog.setOnlydismis(true);
        nodeFragmentDialog.setSearchable(false);

        nodeFragmentDialog.show(getSupportFragmentManager(), "wizard");
    }


    @Override
    protected void onResume() {

        super.onResume();

//        if (regionDialog) {
//            regionDialog = false;
//            propertyCreateFragment.showRegionDIalog();
//        }
//        propertyCreateFragment.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
//        if (!deleteForce && !forceNotSave) {
//            saveProperty(propertyCreateFragment.getProperty(), saveDB, false, false, false, false);
//        }
//        saveDB = false;
//        forceNotSave = false;
//        deleteForce = false;
//        if (curretnRequestCode != null) {
//            KelidApplication.getInstance().cancelPendingRequests(curretnRequestCode);
//            curretnRequestCode = null;
//        }
    }


    @Override
    public void onClick(View v) {


        if (v == nextTextView) {

            doneClicked();
        } else if (v == cancelTextView) {

            if (userJobbId == -2) {
                Toast toast = Toast.makeText(this, getResources().getString(R.string.job_temp_message), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            finish();
            return;
        } else if (v == backImageView) {
            onBackPressed();
            return;
        }


    }


    @Override
    public void onBackPressed() {
        resend = false;
        if (curretnRequestCode != null) {
            KelidApplication.getInstance().cancelPendingRequests(curretnRequestCode);
            curretnRequestCode = null;
        }

        if (currentStep > 2) {
            finish();

        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
                previousStep();
            } else {
                super.onBackPressed();
            }
        }
    }


    void nextStep() {

        currentStep++;

    }

    void previousStep() {
        if (curretnRequestCode != null) {
            KelidApplication.getInstance().cancelPendingRequests(curretnRequestCode);
            curretnRequestCode = null;
        }
        cancel = true;
        currentStep--;

    }

    private void showDuplicateDialog(final long userjob) {
        final Property a = MySqliteOpenHelper.getInstance().myPropertysremote.get(userjob);

        if (a != null) {
            if (progressDialog == null || !progressDialog.isShowing()) {
                progressDialog = new SweetAlertDialog(this);
            }

            progressDialog.setTitleText(getString(R.string.duplicate_title))
                    .showCancelButton(true)
                    .setContentText(getString(R.string.duplicate_job))
                    .setCancelText(getString(R.string.cancel))
                    .setConfirmText(getString(R.string.duplication_action))
                    .showContentText(true)
                    .setCancelClickListener(null)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent b = new Intent(AddPropetyActivity.this, ShowInfoActivity.class);
                            Property userJob = MySqliteOpenHelper.getInstance().loadedPropertys.get(a.remote_id);
                            if (userJob != null) {
                                b.putExtra("job", userJob);
                            } else {
                                b.putExtra("job", a);
                            }


                            startActivity(b);

                        }


                    });


        } else {
            if (progressDialog == null || !progressDialog.isShowing()) {
                progressDialog = new SweetAlertDialog(this);
            }
            progressDialog.setContentText(getResources().getString(R.string.duplicatio_nologin));
            progressDialog.setTitleText(getString(R.string.duplicate_title));
            progressDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            progressDialog.showCancelButton(false);
//            progressDialog.setCancelText(getString(R.string.cancel));
            progressDialog.setConfirmText(getString(R.string.ok));
            progressDialog.showContentText(true);
//            progressDialog.setCancelClickListener(null);
            progressDialog.setConfirmClickListener(null);
        }
        progressDialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
        if (!progressDialog.isShowing())
            progressDialog.show();

    }

    private void showTryAgainDialog() {
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = new SweetAlertDialog(this);
        }
        progressDialog.setTitleText("");
        progressDialog.setContentText(getResources().getString(R.string.send_error));
        progressDialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
        progressDialog.showCancelButton(true);
        progressDialog.setCancelText(getString(R.string.cancel));
        progressDialog.setConfirmText(getString(R.string.tryagain));
        progressDialog.showContentText(true);
        progressDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                progressDialog.dismissWithAnimation();
            }
        });
        progressDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                doneClicked();
            }
        });
        if (!progressDialog.isShowing())
            progressDialog.show();


    }


    public void saveProperty(Property property, boolean db, boolean saveConfing, boolean sendMyAdvers, boolean saveServerDate, boolean saveremote) {
        if (saveConfing)
            UserConfig.saveUserConfig();
        property.myproperty = 1;
        property.date = new Date();
        if (saveServerDate) {
            property.setServerData();
        }
        if (saveremote) {
            for (int i = 0; i < property.images.size(); i++) {
                Property.Image image = property.images.get(i);
                image.remotename = "-";
            }
        }
        if (db || userJobbId != -2) {
            if (property.city == 0) {
                property.city = UserConfig.city;
            }
            property.local_id = MySqliteOpenHelper.getInstance().insertORUpdateProperty(property);
        }
        if (db && userJobbId == -2) {
            UserConfig.clear();
            userJobbId = property.local_id;
        } else if (userJobbId == -2)
            UserConfig.cacheProperty(property);

        if (sendMyAdvers)
            VolleyService.getInstance().MyJobList(this, myAdversService);

    }

//    public void doneClicked() {
//        if (!Utils.isNetworkConnected()) {
//            Toast toast = Toast.makeText(this, getString(R.string.intetnet_not_connected), Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            toast.show();
//            return;
//        }
//
//
//        if (currentStep == 0) {
//            if (propertyCreateFragment.validationEmpty()) {
//                if (propertyCreateFragment.validateEmail()) {
//                    Property a = propertyCreateFragment.getProperty();
//                    if (a.status == Property.WAIT_STATUS) {
//                        String s = KelidApplication.applicationContext.getString(R.string.isAcceptWait);
//                        s = String.format(s, (int) (a.remote_id + Constant.DEFAULT_FANOOS_CODE));
//                        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
//                    } else if (a.status == Property.DRAFT_STATUS || a.isChanged()) {
//                        saveProperty(a, false, false, false, false, false);
//                        if (progressDialog == null || !progressDialog.isShowing()) {
//                            progressDialog = new SweetAlertDialog(this);
//                        }
//                        progressDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
//                        progressDialog.setTitleText("")
//                                .showCancelButton(false)
//                                .setContentText(getString(R.string.sending) + "\n" + getString(R.string.wait))
//                                .setCancelClickListener(null);
//
//                        if (!progressDialog.isShowing())
//                            progressDialog.show();
//
//
//                        if (a.city == -1 || a.city == 0) {
//                            a.city = UserConfig.city;
//                        }
//                        if (UserConfig.userToken == null || UserConfig.userToken.equals("-1")) {
//                            curretnRequestCode = VolleyService.getInstance().SendNewAdvers(AddPropetyActivity.this, newAdver, currentStep, a);
//                        } else {
//                            if (a.remote_id == 0) {
//                                curretnRequestCode = VolleyService.getInstance().SendNewAdversWithToekn(AddPropetyActivity.this, newAdver, currentStep, a);
//                                if (curretnRequestCode == null) {
//                                    progressDialog.dismiss();
//                                    Toast toast = Toast.makeText(AddPropetyActivity.this, getString(R.string.send_error), Toast.LENGTH_SHORT);
//                                    toast.setGravity(Gravity.CENTER, 0, 0);
//                                    toast.show();
//                                }
//                            } else {
//                                curretnRequestCode = VolleyService.getInstance().SendEditAdversWithToekn(AddPropetyActivity.this, editAdver, currentStep, a);
//                                if (curretnRequestCode == null) {
//                                    progressDialog.dismiss();
//                                    Toast toast = Toast.makeText(AddPropetyActivity.this, getString(R.string.send_error), Toast.LENGTH_SHORT);
//                                    toast.setGravity(Gravity.CENTER, 0, 0);
//                                    toast.show();
//                                }
//
//                            }
//                        }
//                    } else {
//                        Toast.makeText(this, R.string.no_job_change, Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(this, R.string.err_msg_email, Toast.LENGTH_SHORT).show();
//
//                }
//
//
//            } else {
//                Toast.makeText(this, R.string.mandatory, Toast.LENGTH_SHORT).show();
//            }
//
//        } else if (currentStep == 1) {
//            sendActiationCode();
//
//        } else if (currentStep == 2) {
//            if (activationCodeFragment.validation()) {
////                progressDialog.setMessage(getResources().getString(R.string.cheecking_code));
//                if (progressDialog == null || !progressDialog.isShowing()) {
//                    progressDialog = new SweetAlertDialog(this);
//                }
//
//                progressDialog.setTitleText("");
//                progressDialog.showCancelButton(false);
//                progressDialog.setContentText(getString(R.string.cheecking_code) + "\n" + getString(R.string.wait));
//                progressDialog.showContentText(true);
//                progressDialog.setCancelClickListener(null);
//                progressDialog.setConfirmClickListener(null);
//                progressDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
//                if (!progressDialog.isShowing())
//                    progressDialog.show();
//                curretnRequestCode = VolleyService.getInstance().ChechActivationCode(AddPropetyActivity.this, newAdver, propertyCreateFragment.getProperty(), activationCodeFragment.getActivationCode());
//            } else {
//                Toast.makeText(this, R.string.invalid_code, Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            finish();
//        }
//    }

//    private void sendActiationCode() {
//        if (userPhoneFragment.validation()) {
//            UserConfig.temp_phone = Utils.asciiNumners(userPhoneFragment.getPhone());
//            if (progressDialog == null || !progressDialog.isShowing()) {
//                progressDialog = new SweetAlertDialog(this);
//            }
//            progressDialog.setTitleText("");
//            progressDialog.showCancelButton(false);
//            progressDialog.setContentText(getString(R.string.generaion_code) + "\n" + getString(R.string.wait));
//            progressDialog.showContentText(true);
//            progressDialog.setCancelClickListener(null);
//            progressDialog.setConfirmClickListener(null);
//            progressDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
//            if (!progressDialog.isShowing())
//                progressDialog.show();
//
//
//            SharedPreferences preferences = KelidApplication.applicationContext.getSharedPreferences("userconfing", Context.MODE_PRIVATE);
//            long last_send = preferences.getLong("last_send", 0);
//            String last_phone = preferences.getString("last_phone", null);
//            Date d = new Date();
//            SharedPreferences.Editor edit = preferences.edit();
//            edit.putLong("last_send", d.getTime());
//            edit.putString("last_phone", UserConfig.temp_phone);
//            edit.commit();
//            if (d.getTime() - last_send > (10 * 60 * 1000) || last_phone == null || !last_phone.equals(UserConfig.temp_phone)) {
//                curretnRequestCode = VolleyService.getInstance().SendActivationCode(AddPropetyActivity.this, newAdver, propertyCreateFragment.getProperty(), UserConfig.temp_phone);
//            } else {
////                curretnRequestCode = VolleyService.getInstance().ReSendActivationCode(AddPropetyActivity.this, newAdver, propertyCreateFragment.getProperty(), UserConfig.temp_phone);
//            }
//
//
//        } else {
//            Toast.makeText(this, R.string.phone_empty, Toast.LENGTH_SHORT).show();
//        }
//    }

    boolean resend;

    @Override
    public void doneClicked() {
        if (currentStep == 0) {
            if (propertyCreateFragment.isValid()) {
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.pop_slide_in, R.anim.pop_slide_out);
                ft.replace(R.id.fragment_container, infoCreateFragment).addToBackStack("");
                ft.commit();
                nextStep();
            }
        } else if (currentStep == 1) {
            if (infoCreateFragment.isValid()) {
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.pop_slide_in, R.anim.pop_slide_out);
                ft.replace(R.id.fragment_container, userPhoneFragment).addToBackStack("");
                ft.commit();
                nextStep();
            }
        } else if (currentStep == 2) {
            if (userPhoneFragment.isValid()) {
                finish();
            }
        }

    }

    @Override
    public void tryAgain() {
        resend = true;
//        sendActiationCode();
    }

    @Override
    public void back() {
        onBackPressed();
    }


    @Override
    public void recivedSMS(String body) {

        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(body);
        if (m.find()) {
            if (receiver != null)
                try {
                    unregisterReceiver(receiver);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            activationCodeFragment.setCode(m.group());
            mainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    doneClicked();
                }
            }, 1000);


        }


    }


    public void pickFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            // place where to store camera taken picture

            mImagePath = FileUtils.getInstance().createTempFile(FileUtils.Camera_DIR, "IMG_", ".png");
            mImageUri = Uri.fromFile(mImagePath);
            mImagePath.delete();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
            orginalPath = mImagePath.getAbsolutePath();

            startActivityForResult(intent, CAMERA_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }


//        Intent cameraIntent = new Intent;
//        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private File mImagePath;
    private Uri mImageUri;

    public void requestImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), Image_REQUEST);

    }

    String orginalPath = null;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Image_REQUEST && resultCode == RESULT_OK) {
            try {
                Uri selectedImage = data.getData();
                orginalPath = ImageFilePath.getPath(getApplicationContext(), selectedImage);

//                Intent cameraIntent = new Intent(this, MainActivity.class);
//                cameraIntent.putExtra("fix_Rate", true);
//                Bitmap bitmap = Utils.resize(Utils.modifyOrientation(BitmapFactory.decodeFile(orginalPath), orginalPath));
//                if (!FileUtils.getInstance().existInDefaultFoder(orginalPath)) {
//                    orginalPath = FileUtils.getInstance().createTempFile(FileUtils.Camera_DIR, "IMG_", ".png").getAbsolutePath();
//                }
////                String path = ImageFilePath.getPath(getApplicationContext(), selectedImage);
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 87, new FileOutputStream(orginalPath));
//                bitmap.recycle();
//                bitmap = BitmapFactory.decodeFile(orginalPath);
////                try {
////                    new File(filepath).delete();
////                }catch (Exception e)
////                {
////                    e.printStackTrace();
////                }
//
//                MainActivity.bitmap = bitmap;
//
//                if (MainActivity.bitmap != null) {
//                    forceNotSave = true;
//                    startActivityForResult(cameraIntent, CROP_IMAGE);
//                } else {
//                    Toast.makeText(this, getString(R.string.load_failed), Toast.LENGTH_SHORT).show();
//                }
            } catch (Exception e) {
                e.printStackTrace();

            }


            if (orginalPath != null)
                testFragment.addImage(orginalPath);


        } else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {


            try {
                this.getContentResolver().notifyChange(mImageUri, null);
                orginalPath = mImagePath.getAbsolutePath();
//                String data1 = (String) data.getExtras().get("data");

//                ContentResolver cr = this.getContentResolver();
//                Bitmap bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, mImageUri);
//
//
//                bitmap = Utils.resize(Utils.modifyOrientation(bitmap, orginalPath));
//
//                if (!FileUtils.getInstance().existInDefaultFoder(orginalPath)) {
//                    orginalPath = FileUtils.getInstance().createTempFile(FileUtils.Camera_DIR, "IMG_", ".png").getAbsolutePath();
//                }
//
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 87, new FileOutputStream(orginalPath));
//                bitmap.recycle();
//                MainActivity.bitmap = BitmapFactory.decodeFile(mImagePath.getAbsolutePath());
////                try{
////                    new File(absolutePath).delete();
////                }catch (Exception e)
////                {
////                    e.printStackTrace();
////                }
//                String aaa = mImagePath.getAbsolutePath();
//           MainActivity.bitmap = Utils.modifyOrientation(BitmapFactory.decodeFile(aaa), aaa);//BitmapFactory.decodeFile(mImagePath.getAbsolutePath());
//
//                forceNotSave = true;
//                Intent cameraIntent = new Intent(this, MainActivity.class);
//                cameraIntent.putExtra("fix_Rate", true);
//                startActivityForResult(cameraIntent, CROP_IMAGE);
            } catch (Exception e) {
                Toast.makeText(this, getString(R.string.load_failed), Toast.LENGTH_SHORT).show();

            }


            testFragment.addImage(orginalPath);

        } else if (requestCode == CROP_IMAGE) {

            if (resultCode == RESULT_OK) {
                try {
                    String data1 = (String) data.getExtras().get("data");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (mImagePath != null && mImagePath.exists()) {
                mImagePath.delete();
            }
            if (orginalPath != null) {
                File f = new File(orginalPath);
                try {
                    if (f.exists()) {
                        f.delete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            mImagePath = null;


        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case READ_EXTERNAL_STORAGE_GRANT_REQUERST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        ) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        requestImageFromGallery();
                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED && ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {

                        Toast toast = Toast.makeText(this, R.string.unistall, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }


                }
                return;
            }
            case CAMERAM_GRANT_REQUERST: {
                if (grantResults.length > 0
                        ) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        pickFromCamera();
                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED && ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {

                        Toast toast = Toast.makeText(this, R.string.unistall, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }


                }
                return;

            }
            case SAVE_GRANT_REQUERST_FOR_JOb: {
                if (grantResults.length > 0
                        ) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        showImageDIalog();
                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED && ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {

                        Toast toast = Toast.makeText(this, R.string.unistall, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }


                }
                return;

            }


        }
    }

    //    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == Image_REQUEST && resultCode == RESULT_OK) {
//            try {
//                Uri selectedImage = data.getData();
//                orginalPath = ImageFilePath.getPath(getApplicationContext(), selectedImage);
//                Intent cameraIntent = new Intent(this, MainActivity.class);
//                cameraIntent.putExtra("fix_Rate", true);
//                Bitmap bitmap = Utils.resize(Utils.modifyOrientation(BitmapFactory.decodeFile(orginalPath), orginalPath));
//                if (!FileUtils.getInstance().existInDefaultFoder(orginalPath)) {
//                    orginalPath = FileUtils.getInstance().createTempFile(FileUtils.Camera_DIR, "IMG_", ".png").getAbsolutePath();
//                }
////                String path = ImageFilePath.getPath(getApplicationContext(), selectedImage);
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 87, new FileOutputStream(orginalPath));
//                bitmap.recycle();
//                bitmap = BitmapFactory.decodeFile(orginalPath);
////                try {
////                    new File(filepath).delete();
////                }catch (Exception e)
////                {
////                    e.printStackTrace();
////                }
//
//               MainActivity.bitmap = bitmap;
//
//                if (MainActivity.bitmap != null) {
//                    forceNotSave = true;
//                    startActivityForResult(cameraIntent, CROP_IMAGE);
//                } else {
//                    Toast.makeText(this, getString(R.string.load_failed), Toast.LENGTH_SHORT).show();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//
//            }
//
//
////            if (filepath != null)
////                userJobCreateFragment.addImage(filepath);
//
//
//        } else if (requestCode == DESIGN_CARD_REQUEST && resultCode == RESULT_OK) {
////            userJobCreateFragment.reFill();
//
//
//        }  else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
//
//
//            try {
//                this.getContentResolver().notifyChange(mImageUri, null);
//                orginalPath = mImagePath.getAbsolutePath();
//                ContentResolver cr = this.getContentResolver();
//                Bitmap bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, mImageUri);
//
//
//                bitmap = Utils.resize(Utils.modifyOrientation(bitmap, orginalPath));
//
//                if (!FileUtils.getInstance().existInDefaultFoder(orginalPath)) {
//                    orginalPath = FileUtils.getInstance().createTempFile(FileUtils.Camera_DIR, "IMG_", ".png").getAbsolutePath();
//                }
//
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 87, new FileOutputStream(orginalPath));
//                bitmap.recycle();
//                MainActivity.bitmap = BitmapFactory.decodeFile(mImagePath.getAbsolutePath());
////                try{
////                    new File(absolutePath).delete();
////                }catch (Exception e)
////                {
////                    e.printStackTrace();
////                }
//                String aaa = mImagePath.getAbsolutePath();
//                MainActivity.bitmap = Utils.modifyOrientation(BitmapFactory.decodeFile(aaa), aaa);//BitmapFactory.decodeFile(mImagePath.getAbsolutePath());
//
//                forceNotSave = true;
//                Intent cameraIntent = new Intent(this, MainActivity.class);
//                cameraIntent.putExtra("fix_Rate", true);
//                startActivityForResult(cameraIntent, CROP_IMAGE);
//            } catch (Exception e) {
//                Toast.makeText(this, getString(R.string.load_failed), Toast.LENGTH_SHORT).show();
//
//            }
//
//
////            userJobCreateFragment.addImage(file);
//
//        } else if (requestCode == CROP_IMAGE) {
//
//            if (resultCode == RESULT_OK) {
//                try {
//                    String data1 = (String) data.getExtras().get("data");
//                    userJobCreateFragment.addImage(data1);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if (mImagePath != null && mImagePath.exists()) {
//                mImagePath.delete();
//            }
//            if (orginalPath != null) {
//                File f = new File(orginalPath);
//                try {
//                    if (f.exists()) {
//                        f.delete();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            mImagePath = null;
//
//
//        }
//
//
//    }
    public void showImageDIalog() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.collage_select_image_dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            dialog.findViewById(R.id.main_content).setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        dialog.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = (int) (displaymetrics.widthPixels * 0.7);
        int height = (int) (displaymetrics.heightPixels * 0.8);
        window.setLayout(width, -2);

//                    dialog.setTitle(getString(R.string.select_photo));
        dialog.findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromCamera();
//                if (ContextCompat.checkSelfPermission(AddPropetyActivity.this,
//                        Manifest.permission.READ_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//
//
//                    if (ActivityCompat.shouldShowRequestPermissionRationale(AddPropetyActivity.this,
//                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
//
//
//                        ActivityCompat.requestPermissions(AddPropetyActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                                Constant.CAMERAM_GRANT_REQUERST);
//
//                    } else {
//
//
//                        ActivityCompat.requestPermissions(AddPropetyActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                                Constant.CAMERAM_GRANT_REQUERST);
//
//                    }
//                } else {
//                    pickFromCamera();
//
//                }


                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestImageFromGallery();

//                if (ContextCompat.checkSelfPermission(AddPropetyActivity.this,
//                        Manifest.permission.READ_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//
//                    if (ActivityCompat.shouldShowRequestPermissionRationale(AddPropetyActivity.this,
//                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
//
//                        ActivityCompat.requestPermissions(AddPropetyActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                                Constant.READ_EXTERNAL_STORAGE_GRANT_REQUERST);
//
//                    } else {
//
//
//                        ActivityCompat.requestPermissions(AddPropetyActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                                Constant.READ_EXTERNAL_STORAGE_GRANT_REQUERST);
//
//                    }
//
//
//                } else {
//                    requestImageFromGallery();
//
//                }
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    @Override
    public void setNode(Node n) {
//        propertyCreateFragment.setNodeType(n);
    }

    @Override
    public void onObjectReslut(int req, int code, Object requestObject, Object data) {
        if (cancel) {
            cancel = false;
            return;
        }
        Property property = (Property) requestObject;
        progressDialog.dismiss();
        if (req == myAdversService) {
            progressDialog.dismiss();
            Collection<Property> userJobFromJson = null;
            try {
                HashMap<Long, Property> userJobFromJson1 = Utils.getMyJobFromJson((JSONObject) data, false, true);
                if (userJobFromJson1 == null)
                    return;
                userJobFromJson = userJobFromJson1.values();
                if (userJobFromJson != null && userJobFromJson.size() > 0) {
                    Iterator<Property> iterator = userJobFromJson.iterator();
                    while (iterator.hasNext()) {
                        Property next = iterator.next();
                        Vector<Property.Image> images = next.images;
                        if (images != null && images.size() > 0) {
                            for (int i = 0; i < images.size(); i++) {
                                new VolleyService.MyAdversImageDownload().execute(images.get(i).remotename, images.get(i).localname);
                            }
                        }
                        next.myproperty = 1;
                        MySqliteOpenHelper.getInstance().insertORUpdateProperty(next);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        } else if (req == editAdver) {
            if (code == ERROR_CODE || data == null) {
                showTryAgainDialog();
                return;
            } else {

                try {
                    JSONObject object = new JSONObject((String) data);
                    String status = object.getString("msg");
//                    String status = object.getString("status");
                    if (status.equals("ok")) {
                        progressDialog.dismiss();
                        property.status = Property.WAIT_STATUS;

                        saveProperty(property, true, false, false, true, true);
                        nextStep();
                        nextStep();
                        nextStep();

                    } else if (status.equals("duplicate")) {
                        showDuplicateDialog(object.getLong("id"));
//                        Toast toast = Toast.makeText(this, getResources().getString(R.string.duplicate_job), Toast.LENGTH_SHORT);
//                        toast.setGravity(Gravity.CENTER, 0, 0);
//                        toast.show();
                    } else {
                        showTryAgainDialog();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    showTryAgainDialog();
                    return;
                }


                return;
            }


        } else if (req == newAdver) {
            if (code == ERROR_CODE || data == null) {
                showTryAgainDialog();
                return;
            }

            if (resend)
                return;
            resend = false;
            if (currentStep == 0) {
                try {
                    if (receiver != null)
                        unregisterReceiver(receiver);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
                if (UserConfig.userToken == null || UserConfig.userToken.equals("-1")) {
                    Property a = property;
                    try {
                        JSONObject object = new JSONObject((String) data);
                        a.token = object.getString("token");

                        if (a.mobile != null)
                            userPhoneFragment.setPhoneEditText(a.mobile);
                        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                        ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.pop_slide_in, R.anim.pop_slide_out);
                        ft.replace(R.id.fragment_container, userPhoneFragment).addToBackStack("");
                        ft.commit();
                        nextStep();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        showTryAgainDialog();
                    }
                } else {
                    try {
                        JSONObject object = new JSONObject((String) data);
                        String status = object.getString("msg");
                        if (status.equals("ok")) {
                            property.remote_id = object.getInt("id");
                            property.status = Property.WAIT_STATUS;

                            saveProperty(property, true, false, true, true, true);
                            nextStep();
                            nextStep();
                            nextStep();
                        } else if (status.equals("duplicate")) {
                            showDuplicateDialog(object.getLong("id"));
                        } else {
                            showTryAgainDialog();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        showTryAgainDialog();
                    }

                }

            } else if (currentStep == 1) {
                progressDialog.dismiss();
                try {
                    JSONObject object = (JSONObject) data;
                    String api_token = object.getString("msg");
                    if (api_token != null && api_token.length() != 0) {
                        if (api_token.equals("SmsSend")) {
                            receiver = new SMSReceiver(this);
                            IntentFilter filter = new IntentFilter();
                            filter.addAction("android.provider.Telephony.SMS_RECEIVED");
                            registerReceiver(receiver, filter);
                            activationCodeFragment.setPhone(UserConfig.temp_phone);
                            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                            android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                            ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.pop_slide_in, R.anim.pop_slide_out);
                            ft.replace(R.id.fragment_container, activationCodeFragment).addToBackStack("");
                            ft.commit();
                            nextStep();
                        } else if (api_token.equals("duplicate")) {
                            showDuplicateDialog(object.getLong("id"));
                        } else if (api_token.equals("expireToken")) {
                            SharedPreferences preferences = KelidApplication.applicationContext.getSharedPreferences("userconfing", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = preferences.edit();
                            edit.putLong("last_send", 0);
                            edit.putString("last_phone", null);
                            edit.commit();
                            showTryAgainDialog();
                        }
                    } else {
                        showTryAgainDialog();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    showTryAgainDialog();
                }


            } else if (currentStep == 2) {
                progressDialog.dismiss();
                try {
                    if (receiver != null)
                        unregisterReceiver(receiver);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {

                    JSONObject json = (JSONObject) data;
                    String api_token = null;
                    try {
                        api_token = json.getString("api_token");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String msg = json.getString("msg");
                    if (msg.equals("registerBusiness")) {
                        UserConfig.userToken = api_token;
                        property.status = Property.WAIT_STATUS;


                        saveProperty(property, true, true, true, true, true);

                        nextStep();
                    } else if (msg.equals("activeCodeIsIncorrect")) {
                        Toast.makeText(this, getResources().getString(R.string.activeCodeIsIncorrect), Toast.LENGTH_SHORT).show();

                    } else if (msg.equals("businessActived")) {
                        Toast.makeText(this, getResources().getString(R.string.businessActived), Toast.LENGTH_SHORT).show();

                    } else if (msg.equals("tokenExpire")) {
                        Toast.makeText(this, getResources().getString(R.string.tokenExpire), Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        }


    }
}
//    LinearLayout properyLayout;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        DBAdapter.getInstance().loadProperty();
//        setContentView(R.layout.activity_add_property);
//        properyLayout= (LinearLayout) findViewById(R.id.proprty_layout);
//        Vector<PropertyDetail> pDetail = DBAdapter.getInstance().allNodes.get(1113100).pDetail;
//        LinearLayout.LayoutParams  params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        for(PropertyDetail a:pDetail)
//        {
//            ToggleButton aaa=new ToggleButton(this);
//            aaa.setText(a.name);
//            aaa.setTextOff(a.name);
//            aaa.setTextOn(a.name);
//            aaa.setBackgroundResource(R.drawable.my_toggle_background);
//            aaa.setLayoutParams(params);
//            properyLayout.addView(aaa);
//        }
//
//
//    }