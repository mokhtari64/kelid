package com.example.mokhtari.myapplication.utils;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by Mahdi on 07/01/2016.
 */
public class ChangeHue {

    public static Bitmap applyHueFilterEffect(Bitmap source, int level, int mode) {
        int width = source.getWidth();
        int height = source.getHeight();
        int[] pixels = new int[width * height];
        float[] HSV = new float[3];
        source.getPixels(pixels, 0, width, 0, 0, width, height);
        int index = 0;
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                index = y * width + x;
                Color.colorToHSV(pixels[index], HSV);
                HSV[0] *= level;
                HSV[0] = (float) Math.max(0.0, Math.min(HSV[0], mode));
                pixels[index] |= Color.HSVToColor(HSV);
            }
        }
        Bitmap bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
        return bmOut;
    }
}

