package com.asking91.app.ui.juniorhigh;

import android.Manifest;
import android.app.Activity;
import android.os.Environment;
import android.text.TextUtils;

import com.asking91.app.global.Constants;

import java.io.File;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 初升高下载管理类
 * Created by haibin
 * on 2016/10/19.
 */

public class JuniorToHighDownLoadManager {

    private Activity mActivity;


    public JuniorToHighDownLoadManager(Activity mActivity) {
        this.mActivity = mActivity;
    }

    /**
     * 下载pdf文件
     */
    public void downLoadPdf(String url, String savePath) {
        requestExternalStorage(url, savePath);
    }

    private final int RC_EXTERNAL_STORAGE = 0x04;//存储权限

    @AfterPermissionGranted(RC_EXTERNAL_STORAGE)
    public void requestExternalStorage(String url, String savePath) {
        if (EasyPermissions.hasPermissions(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            JuniorToHighPdfService.startService(mActivity, url, savePath);
        } else {
            EasyPermissions.requestPermissions(mActivity, "", RC_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    /**
     * 根据文件绝对路径获取文件名
     *
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath))
            return "";
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
    }

    public static String getSavePath() {
        String savaPath = Environment.getExternalStorageDirectory() + "/" + Constants.APP_JUNIOR_TO_HIGH_PDF_PATH;
        return savaPath;
    }


}
