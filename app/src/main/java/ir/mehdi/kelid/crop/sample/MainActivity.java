// "Therefore those skilled at the unorthodox
// are infinite as heaven and earth,
// inexhaustible as the great rivers.
// When they come to an end,
// they begin again,
// like the days and months;
// they die and are reborn,
// like the four seasons."
//
// - Sun Tsu,
// "The Art of War"

package ir.mehdi.kelid.crop.sample;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import ir.mehdi.kelid.Constant;
import ir.mehdi.kelid.R;
import ir.mehdi.kelid.crop.CropImage;
import ir.mehdi.kelid.ui.KelidActivity;


public class MainActivity extends KelidActivity implements Constant,View.OnClickListener {
    public static Bitmap bitmap;



    ImageView  back, done;
    MainFragment mainFragment;
//    private CropImageViewOptions options = new CropImageViewOptions();

    private MainFragment mCurrentFragment;

    private Uri mCropImageUri;

    public void setCurrentFragment(MainFragment fragment) {
        mCurrentFragment = fragment;
    }

    static boolean fixRate=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_activity_main);
        boolean a=false;
        if(savedInstanceState!=null)
        {
            a=savedInstanceState.getBoolean("fix_Rate",false);
        }
        if(!a) {
            Intent intent = getIntent();
            if (intent != null) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    fixRate = extras.getBoolean("fix_Rate");
                }
            }
        }



        done = (ImageView) findViewById(R.id.bookmark);
        back = (ImageView) findViewById(R.id.back);


        done.setOnClickListener(this);
        back.setOnClickListener(this);

        setMainFragmentByPreset();

    }

//

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


    }
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putBoolean("fix_Rate", fixRate);

    }



    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);


            boolean requirePermissions = false;
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {

                // request permissions and handle the result in onRequestPermissionsResult()
                requirePermissions = true;
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {

                mCurrentFragment.setImageUri(imageUri);
            }
        }
//        setMainFragmentByPreset(CropDemoPreset.RECT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0
                    ) {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CropImage.startPickImageActivity(this);
                }else if(grantResults[0] == PackageManager.PERMISSION_DENIED &&  ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0] ))
                {

                    Toast toast = Toast.makeText(this, R.string.unistall, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
                return;



            }
            return;


        }
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0
                    ) {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mCurrentFragment.setImageUri(mCropImageUri);
                }else if(grantResults[0] == PackageManager.PERMISSION_DENIED &&  ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0] ))
                {

                    Toast toast = Toast.makeText(this, R.string.unistall, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
                return;



            }
            return;

        }
    }

    private void setMainFragmentByPreset() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(mainFragment==null) {
            mainFragment = new MainFragment();
        }
        mainFragment.setFixRate(fixRate);
        if (bitmap != null)
            mainFragment.setImageBitMap(bitmap);


        fragmentManager.beginTransaction().replace(R.id.container, mainFragment).commit();
    }



    @Override
    public void onClick(View v) {
        if (v==back) {
            setResult(Activity.RESULT_CANCELED);
            finish();
        }
        else if (v==done) {
            if(mCurrentFragment != null) mCurrentFragment.crop();
//            finish();

        }

    }

}
