package com.asking91.app.ui.update;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asking91.app.R;
import com.asking91.app.api.Networks;
import com.asking91.app.entity.user.UpdateEntity;
import com.asking91.app.mvpframe.BaseSubscriber;
import com.asking91.app.util.SystemUtil;

import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 检查更新
 * Created by haibin
 * on 2016/10/19.
 */

public class CheckUpdateManager {
    private ProgressDialog mWaitDialog;
    private Activity mActivity;
    private boolean mIsShowDialog;

    private AlertDialog.Builder builder;

    public CheckUpdateManager(Activity mActivity, boolean showWaitingDialog) {
        this.mActivity = mActivity;
        this.mIsShowDialog = showWaitingDialog;
        if (mIsShowDialog) {
            mWaitDialog = new ProgressDialog(mActivity);
            mWaitDialog.setMessage("正在检查中...");
            mWaitDialog.setCancelable(false);
            mWaitDialog.setCanceledOnTouchOutside(false);
        }
    }

    public void checkUpdate() {
        if (mIsShowDialog) {
            mWaitDialog.show();
        }
        Networks.getInstance().getUserApi().updateAPKUrl()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new BaseSubscriber(mActivity) {
                    @Override
                    public void call() {
                        super.call();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody baseRsqEntity) {
                        mWaitDialog.dismiss();
                        try {
                            String res = baseRsqEntity.string();
                            JSONObject jsonObj = JSON.parseObject(res);
                            String value = jsonObj.getString("value");
                            final UpdateEntity updateEntity = JSON.parseObject(value, UpdateEntity.class);
                            if (updateEntity.VersionCode <= SystemUtil.getVersionCode(mActivity)) {//如果是最新版本
                                builder = new AlertDialog.Builder(mActivity, R.style.App_Theme_Dialog_Alert)
                                        .setCancelable(false)
                                        .setTitle("")
                                        .setMessage("已是最新版本")
                                        .setPositiveButton("确定", null);
                                builder.show();
                            } else {//更新版本
                                if (updateEntity.State == 1) {//强制更新，有弹窗，不能取消
                                    requestExternalStorage(updateEntity.ApkUrl, true);
                                } else {
                                    builder = new AlertDialog.Builder(mActivity, R.style.App_Theme_Dialog_Alert)
                                            .setCancelable(false)
                                            .setTitle("更新版本")
                                            .setMessage(updateEntity.Description)
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    requestExternalStorage(updateEntity.ApkUrl, false);
                                                }
                                            })
                                            .setNegativeButton("取消", null);
                                    builder.show();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if(mWaitDialog != null)
                            mWaitDialog.dismiss();
                        Toast.makeText(mActivity, "获取更新信息失败，请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private final int RC_EXTERNAL_STORAGE = 0x04;//存储权限

    @AfterPermissionGranted(RC_EXTERNAL_STORAGE)
    public void requestExternalStorage(String ApkUrl, boolean isForcedupdate) {
        if (EasyPermissions.hasPermissions(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            DownloadService.startService(mActivity, ApkUrl, isForcedupdate);
        } else {
            EasyPermissions.requestPermissions(mActivity, "", RC_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private AlertDialog mDialog;
    private TextView tv_download_progress;
    private ProgressBar pb_progress;

    public void showProgressDialog() {
        View view = View.inflate(mActivity, R.layout.layout_notification_view, null);
        tv_download_progress = (TextView) view.findViewById(R.id.tv_download_progress);
        pb_progress = (ProgressBar) view.findViewById(R.id.pb_progress);
        pb_progress.setMax(100);

        mDialog = new AlertDialog.Builder(mActivity, R.style.App_Theme_Dialog_Alert)
                .setTitle("正在下载版本")
                .setCancelable(false)
                .setView(view).create();
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    public void setProgressDialog(String tit,int Progress) {
        if (tv_download_progress != null && pb_progress != null) {
            tv_download_progress.setText(tit);
            pb_progress.setProgress(Progress);
        }
    }

    public void dismissProgressDialog() {
        if (mDialog != null) {
            mDialog.cancel();
        }
    }
}
