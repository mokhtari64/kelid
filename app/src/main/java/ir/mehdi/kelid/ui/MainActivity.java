package ir.mehdi.kelid.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import ir.mehdi.kelid.Constant;
import ir.mehdi.kelid.R;
import ir.mehdi.kelid.arcmenulibrary.ArcMenu;
import ir.mehdi.kelid.collage.ImageFilePath;
import ir.mehdi.kelid.ui.fragment.FilterFragment;
import ir.mehdi.kelid.ui.fragment.HomeFragment;
import ir.mehdi.kelid.ui.fragment.SearchFragment;
import ir.mehdi.kelid.ui.fragment.UserProfileFragment;
import ir.mehdi.kelid.utils.FileUtils;
import ir.mehdi.kelid.utils.Utils;

public class MainActivity extends KelidActivity implements Constant, View.OnClickListener {

    HomeFragment homeFragment;
    FilterFragment filterFragment;
    UserProfileFragment userProfileFragment;
    SearchFragment searchFragment;
    Fragment[] fragments;


    ImageView home, search, category, profile;
    ImageView tabImageView[];
    ArcMenu arcMenu;
    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeFragment = new HomeFragment();
        filterFragment = new FilterFragment();
        searchFragment = new SearchFragment();
        userProfileFragment = new UserProfileFragment();
        fragments = new Fragment[]{
                homeFragment,
                filterFragment,
                searchFragment,
                userProfileFragment
        };
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        arcMenu = (ArcMenu) findViewById(R.id.arcMenu);
        initArcMenu();
        home = (ImageView) findViewById(R.id.btn_home);
        search = (ImageView) findViewById(R.id.btn_search);
        category = (ImageView) findViewById(R.id.btn_category);
        profile = (ImageView) findViewById(R.id.btn_profile);
        tabImageView = new ImageView[]{home, category, search, profile};
        home.setOnClickListener(this);
        search.setOnClickListener(this);
        category.setOnClickListener(this);
        profile.setOnClickListener(this);
        currentTabImageView = home;
        currentTabImageView.setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageChange(tabImageView[position]);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    private static final int[] ITEM_DRAWABLES = {R.drawable.add_image,
            R.drawable.add_text, R.drawable.preview, R.drawable.qr};
    private String[] str = {"Facebook", "Twiiter", "Flickr", "Instagram"};
    private int type[] = new int[]{PROPERTY
            , SERVICE
            , OFFICE
            , CONSULTING
            , NEWS};

    private void initArcMenu() {
        final int itemCount = ITEM_DRAWABLES.length;
        arcMenu.setToolTipTextSize(14);
//        arcMenu.setMinRadius(104);
//        arcMenu.setArc(175,255);
        arcMenu.setToolTipSide(ArcMenu.TOOLTIP_UP);
        arcMenu.setToolTipTextColor(Color.RED);
        arcMenu.setToolTipBackColor(getResources().getColor(R.color.white_pressed));
        arcMenu.setToolTipCorner(2);
        arcMenu.setToolTipPadding(4);
        arcMenu.setColorNormal(getResources().getColor(R.color.mRedDark));
        arcMenu.showTooltip(true);

        arcMenu.setDuration(ArcMenu.ArcMenuDuration.LENGTH_LONG);
        for (int i = 0; i < itemCount; i++) {
            ImageView item = new ImageView(this);
            item.setImageResource(ITEM_DRAWABLES[i]);
            arcMenu.setAnim(400, 400, ArcMenu.ANIM_MIDDLE_TO_DOWN, ArcMenu.ANIM_MIDDLE_TO_RIGHT,
                    ArcMenu.ANIM_INTERPOLATOR_DECLERATE, ArcMenu.ANIM_INTERPOLATOR_BOUNCE);


            final int position = i;
            final int rrr = type[i];
            arcMenu.addItem(item, str[i], new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                    registerNew(rrr);
                }
            });
        }
    }

    public void registerNew(int i) {
        if (nodeFragmentDialog == null)
            nodeFragmentDialog = new NodeFragmentDialog();
//        nodeFragmentDialog.setType(i);
//        nodeFragmentDialog.setActivity(this);
        nodeFragmentDialog.show(getSupportFragmentManager(), "wizard");
    }

    NodeFragmentDialog nodeFragmentDialog;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    ImageView currentTabImageView;

    void pageChange(ImageView v) {
        currentTabImageView.clearColorFilter();//(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
        currentTabImageView = v;
        currentTabImageView.setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);

    }

    @Override
    public void onClick(View v) {
        if (v == currentTabImageView) {
            return;
        }
        if (v == home || v == search || v == profile || v == category) {
            int tag = Integer.parseInt(v.getTag().toString());
            pageChange((ImageView) v);
            mViewPager.setCurrentItem(tag);

        }


    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return fragments[position];
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return fragments.length;
        }


    }
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


        dialog.findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {


                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {


                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                Constant.CAMERAM_GRANT_REQUERST);

                    } else {


                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                Constant.CAMERAM_GRANT_REQUERST);

                    }
                } else {
                    pickFromCamera();

                }


                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {

                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                Constant.READ_EXTERNAL_STORAGE_GRANT_REQUERST);

                    } else {


                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                Constant.READ_EXTERNAL_STORAGE_GRANT_REQUERST);

                    }


                } else {
                    requestImageFromGallery();

                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void requestImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, Image_REQUEST);

    }
    private File mImagePath;
    private Uri mImageUri;
    public void pickFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            mImagePath = FileUtils.getInstance().createLocalFile(FileUtils.Camera_DIR, "IMG_" + System.currentTimeMillis(), ".jpg");
            mImageUri = Uri.fromFile(mImagePath);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);


            startActivityForResult(intent, CAMERA_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Image_REQUEST && resultCode == RESULT_OK) {
            try {

                String orginalPath = null;
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                ArrayList imagesEncodedList = new ArrayList<String>();
                if (data.getData() != null) {
                    Uri selectedImage = data.getData();
                    orginalPath = ImageFilePath.getPath(getApplicationContext(), selectedImage);
                    Bitmap bitmap = Utils.resize(Utils.modifyOrientation(BitmapFactory.decodeFile(orginalPath), orginalPath));
                    File jpg = FileUtils.getInstance().createTempFile("IMG_" + System.currentTimeMillis(), "jpg");
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 87, new FileOutputStream(jpg));
                    bitmap.recycle();
                    bitmap = BitmapFactory.decodeFile(orginalPath);
                    if (orginalPath != null)
                        userProfileFragment.changeImage(bitmap, jpg);
                }

//                }

            } catch (Exception e) {
                e.printStackTrace();

            }


        } else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            try {
                String orginalPath = null;
                this.getContentResolver().notifyChange(mImageUri, null);
                orginalPath = mImagePath.getAbsolutePath();
                ContentResolver cr = this.getContentResolver();
                Bitmap bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, mImageUri);
                bitmap = Utils.resize(Utils.modifyOrientation(bitmap, orginalPath));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 87, new FileOutputStream(orginalPath));
                bitmap.recycle();
                bitmap = BitmapFactory.decodeFile(orginalPath);
                userProfileFragment.changeImage(bitmap, new File(orginalPath));
            } catch (Exception e) {
                Toast.makeText(this, getString(R.string.load_failed), Toast.LENGTH_SHORT).show();

            }


        }
    }

}
