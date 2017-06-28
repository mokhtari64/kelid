package ir.mehdi.kelid;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import ir.mehdi.kelid.db.DBAdapter;
import ir.mehdi.kelid.service.LruBitmapCache;


/**
 * Created by iman on 6/8/2016.
 */
public class KelidApplication
        extends Application {
    public static final String TAG = KelidApplication.class
            .getSimpleName();


    private RequestQueue mRequestQueue;
    private com.android.volley.toolbox.ImageLoader mImageLoader;
    private static KelidApplication mInstance;

    public static volatile Context applicationContext;
    public static Typeface BYEKAN_NORMAL;//
    public static Typeface DIGIT_NORMAL;//




    public static synchronized KelidApplication getInstance() {
        return mInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();

        UserConfig.loadConfig();
        DBAdapter.getInstance().getAllProvince();
        DBAdapter.getInstance().loadcity();
        DBAdapter.getInstance().loadNode();
        mInstance=this;

        applicationContext = getApplicationContext();

        if (BYEKAN_NORMAL == null) {
            BYEKAN_NORMAL = Typeface.createFromAsset(getAssets(), "font/Samim.ttf");
            DIGIT_NORMAL = Typeface.createFromAsset(getAssets(), "font/Samim.ttf");
        }
//        if (BYEKAN_BOLD == null)
//            BYEKAN_BOLD = Typeface.createFromAsset(getAssets(), "font/BYekan+ Bold.ttf");
        UserConfig.loadConfig();
//
//        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//                .resetViewBeforeLoading(true)
//                .cacheOnDisk(true)
//                .cacheInMemory(true)
//                .imageScaleType(ImageScaleType.EXACTLY)
//                .displayer(new FadeInBitmapDisplayer(300))
//                .showImageForEmptyUri(R.drawable.no_image) // resource or drawable
//                .showImageOnFail(R.drawable.no_image) // resource or drawable
//                .build();
//
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
//                .defaultDisplayImageOptions(defaultOptions)
//                .memoryCache(new WeakMemoryCache())
//                .diskCacheSize(100 * 1024 * 1024)
//                .build();
//
//        ImageLoader.getInstance().init(config);


    }
//    public static synchronized KelidApplication getInstance() {
//        return mInstance;
//    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public com.android.volley.toolbox.ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new com.android.volley.toolbox.ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }




}
