package com.asking91.app.ui.widget.camera.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by jswang on 2017/2/16.
 */

public class ScreenUtils {
    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }


    public static int getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }



}
