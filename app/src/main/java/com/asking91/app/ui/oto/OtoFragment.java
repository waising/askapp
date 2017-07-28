package com.asking91.app.ui.oto;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.asking91.app.Asking91;
import com.asking91.app.R;
import com.asking91.app.common.BaseFragment;
import com.asking91.app.entity.TImage;
import com.asking91.app.ui.login.LoginActivity;
import com.asking91.app.ui.widget.camera.ui.CameraActivity;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtoFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.oto_img)
    ImageView mOtoImg;

    private String[] perms = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.CAMERA};
    private final int CAMERA = 0x03;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_oto);
        ButterKnife.bind(this, getContentView());
        EventBus.getDefault().register(this);
    }

    //拍照后处理
    public void onEventMainThread(AppEventType event) {
        switch (event.type) {
            case AppEventType.HOME_CAMERA_REQUEST:
                TImage img = TImage.of("", TImage.FromType.OTHER);
                img.setCompressPath((String) event.values[0]);
                ArrayList<TImage> images = new ArrayList<TImage>();
                images.add(img);
                Asking91.getmInstance().getmDataList().clear();
                Asking91.getmInstance().getmDataList().add(images);
                Intent intent = new Intent(getContext(), OtoAskActivity.class);
                startActivity(intent);
                break;
            case AppEventType.RE_CAMERA_QUS_REQUEST:
                CameraActivity.openCameraActivity(getActivity(), AppEventType.HOME_CAMERA_REQUEST, 0);
                break;
        }
    }

    @OnClick(R.id.oto_img)
    public void OnClick(View view) {
        if (getUserConstant().isTokenLogin()) {
            if (EasyPermissions.hasPermissions(getActivity(), perms)) {//有权限
                CameraActivity.openCameraActivity(getActivity(), AppEventType.HOME_CAMERA_REQUEST, 0);
            } else {
                requestPermissions();
            }
        } else {
            CommonUtil.openActivity(getActivity(), LoginActivity.class);
        }
    }

    /**
     * 摄像头权限
     */
    public void requestPermissions() {
        if (!EasyPermissions.hasPermissions(getActivity(), perms)) {
            EasyPermissions.requestPermissions(this, "需要开启摄像头权限",
                    CAMERA, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        CameraActivity.openCameraActivity(getActivity(), AppEventType.HOME_CAMERA_REQUEST, 0);//请求成功之后调用摄像头
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
