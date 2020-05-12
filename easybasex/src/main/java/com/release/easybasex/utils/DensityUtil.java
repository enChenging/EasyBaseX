package com.release.easybasex.utils;

import android.content.Context;
import android.util.DisplayMetrics;


/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
public class DensityUtil {

    public enum EScreenDensity {
        XXHDPI,    //超高分辨率    1080×1920
        XHDPI,    //超高分辨率    720×1280
        HDPI,    //高分辨率         480×800
        MDPI,    //中分辨率         320×480
    }

    public static EScreenDensity getDisply(Context context) {
        EScreenDensity eScreenDensity;
        //初始化屏幕密度
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        int densityDpi = dm.densityDpi;

        if (densityDpi <= 160) {
            eScreenDensity = EScreenDensity.MDPI;
        } else if (densityDpi <= 240) {
            eScreenDensity = EScreenDensity.HDPI;
        } else if (densityDpi < 400) {
            eScreenDensity = EScreenDensity.XHDPI;
        } else {
            eScreenDensity = EScreenDensity.XXHDPI;
        }
        return eScreenDensity;
    }


    public static int getScreenWidth() {
        return AppUtils.getAppContext().getResources().getDisplayMetrics().widthPixels;
    }


    public static int getScreenHeight() {
        return AppUtils.getAppContext().getResources().getDisplayMetrics().heightPixels;
    }

    public static int dpToPxInt(float dip) {
        //px/dip= density
        float density = AppUtils.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }

    public static float dpToPx(float dp) {
        return dp * AppUtils.getAppContext().getResources().getDisplayMetrics().density;
    }

    public static int pxToDpInt(float px) {
        float density = AppUtils.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5f);
    }


    public static float pxToDp(float px) {
        return px / AppUtils.getAppContext().getResources().getDisplayMetrics().density;
    }


    public static float pxToSp(float pxValue) {
        return pxValue / AppUtils.getAppContext().getResources().getDisplayMetrics().scaledDensity;
    }


    public static float spToPx(float spValue) {
        return spValue * AppUtils.getAppContext().getResources().getDisplayMetrics().scaledDensity;
    }
}
