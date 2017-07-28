
package com.asking91.app.global;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;
import com.asking91.app.Asking91;
import com.asking91.app.api.Networks;
import com.asking91.app.entity.supertutorial.StudyHistory;
import com.asking91.app.entity.user.UserEntity;
import com.asking91.app.util.CommonUtil;
import com.google.gson.Gson;

/**
 * Created by Jun on 2016/4/16.
 */
@SuppressLint("CommitPrefEdits")
public class UserConstant {

    private static final String TAG = "UserConstant";

    private static UserConstant mUserConstant;

    private SharedPreferences mPreferences;

    private SharedPreferences.Editor mEditor;

    private UserEntity userEntity;

    public static UserConstant getInstance(Context context) {
        if (mUserConstant == null)
            mUserConstant = new UserConstant(context);

        return mUserConstant;
    }

    private UserConstant(Context context) {
        mPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
    }

    public boolean saveToken(String value) {
        mEditor = mPreferences.edit();
        mEditor.putString(Constants.Key.TOKEN, value);

        return mEditor.commit();
    }

    public String getToken() {
        return mPreferences.getString(Constants.Key.TOKEN, "");
    }

    /**
     * 保存用户数据
     *
     * @param data
     * @return
     */
    public boolean saveUserData(String data) {
        mEditor = mPreferences.edit();
        mEditor.putString(Constants.Key.USER_DATA, data);

        return mEditor.commit() && setIsLogin(true);
    }

    public String getUserData() {
        return mPreferences.getString(Constants.Key.USER_DATA, "");
    }

    public String getUserId() {

        return getUserEntity().getId();
    }

    public String getUserName() {
        return getUserEntity().getUserName();
    }

    public String getPassWord() {
        return getUserEntity().getPassWord();
    }


    private StudyHistory mHistory;

    public void setStudyHistory(StudyHistory mHistory) {
        try {
            this.mHistory = mHistory;
            String value = JSON.toJSONString(mHistory);
            mEditor = mPreferences.edit();
            mEditor.putString(Constants.Key.StudyHistory, value);
            mEditor.commit();
        } catch (Exception e) {
        }
    }

    public StudyHistory getStudyHistory() {
        try {
            if (mHistory == null) {
                String value = mPreferences.getString(Constants.Key.StudyHistory, "");
                mHistory = JSON.parseObject(value, StudyHistory.class);
            }
        } catch (Exception e) {
        }
        return mHistory;
    }

    //退出登录
    public boolean logout() {
        mEditor = mPreferences.edit();
        mEditor.remove(Constants.Key.USER_DATA);
        mEditor.putBoolean(Constants.Key.IS_LOGIN, false);
        mEditor.putString(Constants.Key.WYBAIBAN_TOKEN, "");
        mEditor.putString(Constants.Key.TOKEN, "");
        mEditor.putBoolean(Constants.Key.IS_USER_DATA_PERFECT, false);
       // setIsLoginState(false);
        userEntity = null;
        Networks.setToken("");
        return mEditor.commit() && setIsLogin(false);
    }

    public boolean isLogin() {
        return mPreferences.getBoolean(Constants.Key.IS_LOGIN, false);
    }

    private boolean setIsLogin(boolean login) {
        mEditor = mPreferences.edit();
        mEditor.putBoolean(Constants.Key.IS_LOGIN, login);
        return mEditor.commit();
    }

    /**
     * 是否处于登录状态
     *
     * @return
     */
    public boolean isLoginState() {
        return mPreferences.getBoolean(Constants.Key.LOGIN_STATE, false);
    }

    /**
     * 登录状态
     *
     * @param login
     * @return
     */
    public boolean setIsLoginState(boolean login) {
        mEditor = mPreferences.edit();
        mEditor.putBoolean(Constants.Key.LOGIN_STATE, login);
        return mEditor.commit();
    }


    //资料完善
    public boolean isUserDataPerfect() {
        return mPreferences.getBoolean(Constants.Key.IS_USER_DATA_PERFECT, false);
    }

    public boolean setIsUserDataPerfect(boolean isPerfect) {
        mEditor = mPreferences.edit();
        mEditor.putBoolean(Constants.Key.IS_USER_DATA_PERFECT, isPerfect);
        return mEditor.commit();
    }

    public boolean isTokenLogin() {
        return isLogin() && !"".equals(getToken());
    }

    public UserEntity getUserEntity() {
        if (userEntity == null && "" != getUserData()) {
            //初始化用户数据
            userEntity = CommonUtil.data2Clazz(getUserData(), UserEntity.class);
        }
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
        if (userEntity != null)
            saveUserData(new Gson().toJson(userEntity));
    }

    /**
     * 网易账号
     *
     * @param value
     * @return
     */
    public boolean saveWYAccout(String value) {
        mEditor = mPreferences.edit();
        mEditor.putString(Constants.Key.WYBAIBAN_ACCOUT, value);

        return mEditor.commit();
    }

    public String getWYAccout() {
        return mPreferences.getString(Constants.Key.WYBAIBAN_ACCOUT, "");
    }

    /**
     * 网易Token
     */
    public void saveWYToken(String token) {
        mPreferences.edit().putString(Constants.Key.WYBAIBAN_TOKEN, token).commit();
    }

    /**
     * 获取网易Token
     */
    public String getWYToken() {
        if (mPreferences == null) {
            mPreferences = Asking91.getmInstance().getApplicationContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        }
        return mPreferences.getString(Constants.Key.WYBAIBAN_TOKEN, null);
    }
}
