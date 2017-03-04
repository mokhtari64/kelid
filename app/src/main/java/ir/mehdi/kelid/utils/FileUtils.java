package ir.mehdi.kelid.utils;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import ir.mehdi.kelid.KelidApplication;

public class FileUtils {

    private static FileUtils instance = null;

//    private static Context mContext;

    public static final String APP_DIR = "Fanoos";
    public static final String Camera_DIR = "IMAGES";
    public static final String BUSINESS_CARD_DIR = "CARDS";
    public static final String APK = "APK";

//    private static final String TEMP_DIR = "Fanoos/.TEMP";

    public static FileUtils getInstance() {
        if (instance == null) {
            synchronized (FileUtils.class) {
                if (instance == null) {
//                    mContext = FanoosApplication.applicationContext.getApplicationContext();
                    instance = new FileUtils();
                }
            }
        }
        return instance;
    }

    public File getImageFile(String filename) {
        return new File(getDefualPath(), filename);

    }


    public static void saveToGallery(String file)
    {
        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.MediaColumns.DATA, file);

        KelidApplication.applicationContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);


    }
    public static String saveBitmapToLocal(Bitmap bm, Context context, String dir) {
        String path = null;
        try {
            File file = FileUtils.getInstance().createTempFile(dir, "IMG_", ".png");
            FileOutputStream fos = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 87, fos);
            fos.flush();
            fos.close();
            path = file.getAbsolutePath();
            bm.recycle();
            saveToGallery(file.getAbsolutePath());
//            ContentValues values = new ContentValues();
//
//            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
//            values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
//            values.put(MediaStore.MediaColumns.DATA, file.getAbsolutePath());
//
//            context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return path;
    }

    public File getCardDir() {
        File f ;
        if (isSDCanWrite()) {
            f = new File(Environment.getExternalStorageDirectory() + File.separator + APP_DIR + File.separator + BUSINESS_CARD_DIR + File.separator);
        } else {
            f = new File(getLocalPath()+ BUSINESS_CARD_DIR + File.separator);
        }
        return f;

    }



    public File createTempFile(String dir_name, String prefix, String extension)
            throws IOException {
        if (isSDCanWrite()) {
            File dir;
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
//                dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + APP_DIR + File.separator + dir_name);
//            elseCamera_DIR
            dir = new File(Environment.getExternalStorageDirectory() + File.separator + APP_DIR + File.separator + dir_name);
            if (!dir.exists())
                dir.mkdirs();
            File file = new File(dir, prefix + System.currentTimeMillis() + extension);
            file.createNewFile();
            return file;
        } else {
            File file = new File(getLocalPath() + dir_name + "/" + prefix
                    + System.currentTimeMillis() + extension);
            if(!file.getParentFile().exists())
            {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
            return file;
        }

    }


    private File getDefualPath() {
        File dir;
        if (isSDCanWrite()) {
            dir = new File(Environment.getExternalStorageDirectory() + File.separator + APP_DIR + File.separator + Camera_DIR);

        } else {
            dir = new File(getLocalPath() + File.separator + Camera_DIR);


        }
        return dir;

    }


    public String getAppDirPath() {
        File f;
        if (isSDCanWrite()) {
            f = new File(Environment.getExternalStorageDirectory() + File.separator + APP_DIR + File.separator + APK + File.separator);
        } else {
            f = new File(getLocalPath() + APP_DIR + File.separator + APK + File.separator);
        }

        return f.getAbsolutePath();
    }





    private static String getLocalPath() {
        String sdPath = null;
        sdPath = KelidApplication.applicationContext.getFilesDir().getAbsolutePath() + File.separator;
        return sdPath;
    }


    public boolean isSDCanWrite() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)
                && Environment.getExternalStorageDirectory().canWrite()
                && Environment.getExternalStorageDirectory().canRead()) {
            return true;
        } else {
            return false;
        }
    }

    private FileUtils() {

        if (isSDCanWrite()) {
            creatSDDir(APP_DIR);
//            creatSDDir(TEMP_DIR);
        }
    }

    public File creatSDDir(String dirName) {
        File dir = new File(Environment.getExternalStorageDirectory() + File.separator + dirName);
        dir.mkdirs();
        return dir;
    }

    public boolean existInDefaultFoder(String localname) {
        File a = new File(localname);
        return a.getParentFile().equals(getDefualPath());
    }

    public String copyToDefaultFoder(String localname) {
        File infile = new File(localname);
        String name="IMG_"+System.currentTimeMillis()+".png";
        File outfile = new File(getDefualPath(),name );
        File parentFile = outfile.getParentFile();
        if(!parentFile.exists())
            parentFile.mkdirs();

        InputStream in = null;//= new FileInputStream(infile);
        OutputStream out = null;//= new FileOutputStream(outfile);
        try {
            in = new FileInputStream(infile);
            out = new FileOutputStream(outfile);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return outfile.getAbsolutePath();

    }
}
