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

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ir.mehdi.kelid.R;
import ir.mehdi.kelid.crop.CropImage;
import ir.mehdi.kelid.crop.CropImageView;
import ir.mehdi.kelid.utils.FileUtils;


/**
 * The fragment that will show the Image Cropping UI by requested preset.
 */
public final class MainFragment extends Fragment
        implements CropImageView.OnSetImageUriCompleteListener, CropImageView.OnCropImageCompleteListener {



    boolean action_crop_complete = false;
    //region: Fields and Consts

    Bitmap imageBitMap;
    boolean fixRate;

    public void setImageBitMap(Bitmap imageBitMap) {
        this.imageBitMap = imageBitMap;
    }



    private CropImageView mCropImageView;
    //endregion

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        if (item.getItemId() == R.id.main_action_crop) {
//            action_crop_complete = true;
//            mCropImageView.getCroppedImageAsync();
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * Set the image to show for cropping.
     */
    public void setImageUri(Uri imageUri) {
        mCropImageView.setImageUriAsync(imageUri);
        //        CropImage.activity(imageUri)
        //                .start(getContext(), this);
    }

    /**
     * Set the options of the crop image view to the given values.
     */
    public void setCropImageViewOptions(CropImageViewOptions options) {
        mCropImageView.setScaleType(options.scaleType);
        mCropImageView.setCropShape(options.cropShape);
        mCropImageView.setGuidelines(options.guidelines);

        mCropImageView.setFixedAspectRatio(true);
        mCropImageView.setMultiTouchEnabled(options.multitouch);
        mCropImageView.setShowCropOverlay(options.showCropOverlay);
        mCropImageView.setShowProgressBar(options.showProgressBar);
        mCropImageView.setAutoZoomEnabled(options.autoZoomEnabled);
        mCropImageView.setMaxZoom(options.maxZoomLevel);
    }



//    public void updateCurrentCropViewOptions() {
//        CropImageViewOptions options = new CropImageViewOptions();
//        options.scaleType = mCropImageView.getScaleType();
//        options.cropShape = mCropImageView.getCropShape();
//        options.guidelines =CropImageView.Guidelines.ON_TOUCH;
//        options.aspectRatio = new Pair<>(1, 1);
//        options.fixAspectRatio = mCropImageView.isFixAspectRatio();
//        options.showCropOverlay = mCropImageView.isShowCropOverlay();
//        options.showProgressBar = mCropImageView.isShowProgressBar();
//        options.autoZoomEnabled = mCropImageView.isAutoZoomEnabled();
//        options.maxZoomLevel = mCropImageView.getMaxZoom();
//        setCropImageViewOptions(options);
////        ((Main2) getActivity()).setCurrentOptions(options);
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_main_rect, container, false);
//        updateCurrentCropViewOptions();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCropImageView = (CropImageView) view.findViewById(R.id.cropImageView);
        mCropImageView.setFixedAspectRatio(fixRate);

        mCropImageView.setOnSetImageUriCompleteListener(this);
        mCropImageView.setOnCropImageCompleteListener(this);

//        updateCurrentCropViewOptions();

//        if (savedInstanceState == null) {

        if (imageBitMap != null)
            mCropImageView.setImageBitmap(imageBitMap);

//            if (mDemoPreset == CropDemoPreset.SCALE_CENTER_INSIDE) {
//
//                mCropImageView.setImageResource(R.drawable.fanoos_logo1);
//            } else {
//                mCropImageView.setImageResource(R.drawable.fanoos_logo1);
//            }
//        }
    }


    public boolean crop() {
        action_crop_complete = true;
        mCropImageView.getCroppedImageAsync();
        return true;

    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
////        if (item.getItemId() == R.id.preview) {
////            action_crop_complete = false;
////            mCropImageView.getCroppedImageAsync();
////            return true;
////        } else if (item.getItemId() == R.id.main_action_rotate) {
////            mCropImageView.rotateImage(90);
////            return true;
////        }
//        if (item.getItemId() == R.id.main_action_crop) {
//            action_crop_complete = true;
//            mCropImageView.getCroppedImageAsync();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((MainActivity) activity).setCurrentFragment(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mCropImageView != null) {
            mCropImageView.setOnSetImageUriCompleteListener(null);
            mCropImageView.setOnCropImageCompleteListener(null);
        }
    }

    @Override
    public void onSetImageUriComplete(CropImageView view, Uri uri, Exception error) {
        if (error == null) {
            Toast.makeText(getActivity(), "Image load successful", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("AIC", "Failed to load image by URI", error);
            Toast.makeText(getActivity(), "Image load failed: " + error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
        handleCropResult(result);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            handleCropResult(result);
        }
    }

    private void handleCropResult(CropImageView.CropResult result) {
        if (result.getError() == null) {
            Intent intent = new Intent();
            intent.putExtra("SAMPLE_SIZE", result.getSampleSize());
            if (result.getUri() != null) {
                intent.putExtra("URI", result.getUri());
            } else {
                CropResultActivity.mImage = mCropImageView.getCropShape() == CropImageView.CropShape.OVAL
                        ? CropImage.toOvalBitmap(result.getBitmap())
                        : result.getBitmap();
            }

            String path = FileUtils.saveBitmapToLocal(CropResultActivity.mImage, getActivity(), FileUtils.Camera_DIR);
            intent.putExtra("data", path);
//                CropResultActivity.mImage.recycle();
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();

        } else {
            Log.e("AIC", "Failed to crop image", result.getError());
            Toast.makeText(getActivity(), "Image crop failed: " + result.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void setFixRate(boolean fff) {
        fixRate=fff;


    }
}
