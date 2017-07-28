package com.asking91.app.ui.onlineqa;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.ui.widget.camera.ui.BaseEvenActivity;
import com.asking91.app.ui.widget.camera.ui.CameraActivity;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.ui.widget.dialog.OnlineQAListBaseDialog;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.JLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * Created by wxiao on 2016/11/7.
 * 我要提问
 */

public class OnlineQAAskActivity extends BaseEvenActivity<OnlineQAAskPresenter, OnlineQAAskModel> implements OnlineQAAskPresenter.View, EasyPermissions.PermissionCallbacks {

    private Bitmap bitmap;
    @BindView(R.id.user_name_tv)
    TextView userNameTv;
    @BindView(R.id.title_more)
    ImageView titleMore;
    @BindView(R.id.title_click)
    LinearLayout titleClick;
    @BindView(R.id.user_info_rl)
    RelativeLayout userInfoRl;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.pic_take)
    ImageView picTake;
    @BindView(R.id.t1)
    TextView t1;
    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.r1)
    RelativeLayout r1;
    @BindView(R.id.t2)
    EditText t2;
    @BindView(R.id.edt1)
    EditText edt1;
    @BindView(R.id.r3)
    RelativeLayout r3;
    @BindView(R.id.t4)
    TextView t4;
    @BindView(R.id.et2)
    TextView et2;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.r4)
    RelativeLayout r4;
    @BindView(R.id.reply)
    Button reply;

    private MaterialDialog materialDialog;

    private String[] perms = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.CAMERA};
    private final int CAMERA = 0x03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_onlineqa_ask, null);
        setContentView(contentView);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        toolBar.setTitle("我要提问");
//        pathTmp = Environment.getExternalStorageDirectory()+"/91tmp";
    }

    @Override
    public void initView() {
        super.initView();
        materialDialog = getLoadingDialog().build();
        materialDialog.setContent("提交中...");

        et2.setText(mStr + " - " + numStr);
    }

    @Override
    public void initLoad() {
        super.initLoad();
    }

    @Override
    public void initListener() {
        super.initListener();
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //-----------拍照End------------------
    public void onEventMainThread(AppEventType event) {
        switch (event.type) {
            case AppEventType.ONLINE_QA_AS_CAMERA_REQUEST:
                String picTakePath = (String) event.values[0];
                File file = new File(picTakePath);
                JLog.i("OnlineQAAskActivity", "file.exist=" + file.exists());
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(picTakePath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bitmap = BitmapFactory.decodeStream(fis);
                picTake.setImageBitmap(bitmap);
                picTake.setTag(R.id.pic_take_path, picTakePath);
                break;
        }
    }

    @Override
    public void onSubmitPicSuccess(ResponseBody res) {
        String content = "";
        try {
            String t1 = res.string();
            JSONObject object = new JSONObject(t1);
            if ("1".equals(object.getString("flag"))) {
                materialDialog.dismiss();
                showShortToast(object.getString("msg"));
                return;
            }
            content = object.getString("content");
            String caifu = "0";
            if (!edt1.getText().toString().isEmpty()) {
                caifu = URLEncoder.encode(edt1.getText().toString(), com.asking91.app.global.Constants.encode);
            }
            mPresenter.submit(m, num, caifu, title.getText().toString(), t2.getText().toString() + "</br>" + content);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            materialDialog.dismiss();
        } catch (IOException e) {
            e.printStackTrace();
            materialDialog.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
            materialDialog.dismiss();
        }

    }

    @Override
    public void onSubmitSuccess(ResponseBody res) {
        materialDialog.dismiss();
        try {
            String result = res.string();
            JLog.logi("OnlineQAAskActivity", "onSubmitSuccess=" + result + "");
            JSONObject object = new JSONObject(result);
            if (object.getString("flag").equals("0")) {
                showShortToast("提交成功");
                finish();
            } else {
                showShortToast(object.getString("msg"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestError(RequestEntity requestEntity) {
        super.onRequestError(requestEntity);
        materialDialog.dismiss();
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {
        materialDialog.dismiss();
    }

    private OnlineQAListBaseDialog onlineQAListBaseDialog;
    private String m = "M", num = "7", mStr = "数学", numStr = "七年级";

    @OnClick({R.id.pic_take, R.id.reply, R.id.img1, R.id.et2})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pic_take://照相提问
                if (EasyPermissions.hasPermissions(this, perms)) {//有权限
                    CameraActivity.openCameraActivity(this, AppEventType.ONLINE_QA_AS_CAMERA_REQUEST, 0);
                } else {
                    requestPermissions();
                }
//                CommonUtil.checkCameraPower(mthis, OnlineQAAskActivity.class.getName());
                break;
            case R.id.reply://确认提交
                if (bitmap == null) {
                    showShortToast("请拍照问题");
                    return;
                }
                if (title.getText().toString().isEmpty()) {
                    showShortToast("请填写标题");
                    return;
                }
                if (m == null || num == null) {
                    showShortToast("请选择年级、科目");
                    return;
                }
                materialDialog.show();
                MultipartBody.Part body = CommonUtil.getMultipartBodyPart(this, bitmap, String.valueOf(picTake.getTag(R.id.pic_take_path)));

                mPresenter.submitPicture(body);
                break;
            case R.id.img1://选择年级
            case R.id.et2:
                if (onlineQAListBaseDialog == null) {

                    onlineQAListBaseDialog = new OnlineQAListBaseDialog(this);
                    onlineQAListBaseDialog.setOnClickListener(new OnlineQAListBaseDialog.OnBtnClickListener() {

                        @Override
                        public void onClick1(String str) {
                            m = "M";
                            mStr = str;
                        }

                        @Override
                        public void onClick2(String str) {
                            m = "P";
                            mStr = str;
                            if (num.equals("7")) {
                                num = "8";
                                numStr = "八年级";
                            }
                        }

                        @Override
                        public void onClick11(String str) {
                            num = "7";
                            numStr = str;
                        }

                        @Override
                        public void onClick12(String str) {
                            num = "8";
                            numStr = str;
                        }

                        @Override
                        public void onClick13(String str) {
                            num = "9";
                            numStr = str;
                        }

                        @Override
                        public void onClick21(String str) {
                            num = "10";
                            numStr = str;
                        }

                        @Override
                        public void onClick22(String str) {
                            num = "11";
                            numStr = str;
                        }

                        @Override
                        public void onClick23(String str) {
                            num = "12";
                            numStr = str;
                        }

                        @Override
                        public void onClickOk() {
                            if (m != null && num != null) {
                                et2.setText(mStr + " - " + numStr);
                                onlineQAListBaseDialog.dismiss();
                            }
                        }
                    });
                }
                onlineQAListBaseDialog.show();
                break;
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            File file = new File(data.getData().getPath());
//            JLog.i("OnlineQAAskActivity","file.exist="+file.exists());
//            FileInputStream fis = null;
//            try {
//                fis = new FileInputStream(data.getData().getPath());
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            bitmap= BitmapFactory.decodeStream(fis);
//            picTake.setImageBitmap(bitmap);
//            picTake.setTag(R.id.pic_take_path, data.getData().getPath());
//        }
//    }

    /**
     * 摄像头权限
     */
    public void requestPermissions() {
        EasyPermissions.requestPermissions(this, "需要开启摄像头权限",
                CAMERA, perms);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        CameraActivity.openCameraActivity(this, AppEventType.ONLINE_QA_AS_CAMERA_REQUEST, 0);
    }

}
