package com.asking91.app.util;

import android.content.Context;

import com.asking91.app.global.Constants;

/**
 * 主题切换帮助类
 * Created by Jun on 2016/9/21.
 */

public class ThemeUtil {
    private SharePreferencesHelper mSPHelper;

    private static ThemeUtil mThemeUtil;

    public static ThemeUtil getInstance(Context context) {
        if (mThemeUtil == null) {
            mThemeUtil = new ThemeUtil(context);
        }

        return mThemeUtil;
    }

    public ThemeUtil(Context context) {
        super();
        mSPHelper = SharePreferencesHelper.getInstance(context);
    }

    /**
     * 保存主题设置
     * @param theme 主题
     */
    public void setTheme(String theme) {
//        mSPHelper.putString(Constants.Key.THEME_MODE, theme);
    }

    /**
     * 取出当前主题
     * @return 主题
     */
    public String getTheme() {
//        return mSPHelper.getString(Constants.Key.THEME_MODE, Constants.Theme.Blue);
        return null;
    }

    /**
     * 判断是否是蓝色主题
     * @return
     */
    public boolean isBlueTheme() {
        return getTheme().equals(Constants.Theme.Blue);
    }

    /**
     * 判断是否是白色主题
     * @return
     */
    public boolean isWhiteTheme() {
        return getTheme().equals(Constants.Theme.White);
    }
}
