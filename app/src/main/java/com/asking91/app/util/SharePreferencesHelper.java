package com.asking91.app.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Jun on 2016/5/3.
 */
public class SharePreferencesHelper {

    private static final String TAG = "SharePreferencesHelper";

    private SharedPreferences mPreferences;

    private SharedPreferences.Editor mEditor;

    private static SharePreferencesHelper mSPHelper;

    public static final String FIRST_OPEN = "first_open";
    public static final String OPEN_TIMES = "open_times";

    public static SharePreferencesHelper getInstance(Context context) {
        if (mSPHelper == null)
            mSPHelper = new SharePreferencesHelper(context);

        return mSPHelper;
    }

    private SharePreferencesHelper(Context context) {
        mPreferences = context.getSharedPreferences(TAG, Context.MODE_APPEND);
    }

    public boolean putString(String key, String value) {
        mEditor = mPreferences.edit();
        mEditor.putString(key, value);
        return mEditor.commit();
    }

    public String getString(String key) {
        return mPreferences.getString(key, "");
    }

    public String getString(String key, String defValue) {
        return mPreferences.getString(key, defValue);
    }

    public boolean removeString(String key) {
        mEditor = mPreferences.edit();
        mEditor.remove(key);
        return mEditor.commit();
    }

    public boolean putInt(String key, int value) {
        mEditor = mPreferences.edit();
        mEditor.putInt(key, value);
        return mEditor.commit();
    }

    public int getInt(String key) {
        return mPreferences.getInt(key, 0);
    }

    public int getInt(String key, int defValue) {
        return mPreferences.getInt(key, defValue);
    }

    public boolean putBoolean(String key, boolean value) {
        mEditor = mPreferences.edit();
        mEditor.putBoolean(key, value);
        return mEditor.commit();
    }

    public boolean getBoolean(String key) {
        return mPreferences.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mPreferences.getBoolean(key, defValue);
    }

}
