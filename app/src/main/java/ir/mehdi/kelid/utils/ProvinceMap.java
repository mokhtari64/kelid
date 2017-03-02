package ir.mehdi.kelid.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import ir.mehdi.kelid.UserConfig;
import ir.mehdi.kelid.db.Database;
import ir.mehdi.kelid.model.Province;


/**
 * Created by Iman on 11/27/2015.
 */
public class ProvinceMap {

    public int selectedColor = 0xFF225CFF, defaultColor = 0xCC7CC7FF;


    public Bitmap orginalBitmap;
    Canvas orginalCanvas;
    private Paint mPaint, selectPaint, transparentPaint;
    int orginalheight = 1009, orginalWidth = 1123;

    public void setCurrentProvince(Province oldProvince, Province currentProvince) {

        if (oldProvince != null) {
            orginalCanvas.drawPath(oldProvince.path, transparentPaint);
            orginalCanvas.drawPath(oldProvince.path, mPaint);
        }
        if (currentProvince != null) {
            UserConfig.province = currentProvince.code;
            orginalCanvas.drawPath(currentProvince.path, selectPaint);

        }
    }


    static ProvinceMap instance;

    public static ProvinceMap getInstance() {
        if (instance == null)
            instance = new ProvinceMap();
        return instance;
    }






    private ProvinceMap() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(defaultColor);
        mPaint.setStrokeWidth(2);
        //mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        transparentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        transparentPaint.setStyle(Paint.Style.FILL);
        transparentPaint.setColor(Color.TRANSPARENT);
        transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        transparentPaint.setStrokeWidth(2);
        selectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectPaint.setStyle(Paint.Style.FILL);
        selectPaint.setColor(selectedColor);
        selectPaint.setStrokeWidth(2);
        orginalBitmap = Bitmap.createBitmap(orginalWidth, orginalheight, Bitmap.Config.ARGB_8888);
        orginalCanvas = new Canvas(orginalBitmap);
        for (Province c : Database.getInstance().getAllProvince())
            orginalCanvas.drawPath(c.path, mPaint);


    }


}
