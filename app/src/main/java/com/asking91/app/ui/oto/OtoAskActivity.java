package com.asking91.app.ui.oto;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asking91.app.Asking91;
import com.asking91.app.R;
import com.asking91.app.entity.LabelEntity;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.TImage;
import com.asking91.app.global.HttpCodeConstant;
import com.asking91.app.global.OtoConstant;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.ui.widget.camera.comm.CourseViewHelper;
import com.asking91.app.ui.widget.camera.comm.OnItemLabelEntityListener;
import com.asking91.app.ui.widget.camera.ui.BaseEvenActivity;
import com.asking91.app.ui.widget.camera.ui.CameraActivity;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.ui.widget.camera.utils.BitmapUtil;
import com.asking91.app.ui.widget.dialog.OtoAskDialog;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.DateUtil;
import com.asking91.app.util.DeviceUtil;
import com.asking91.app.util.FileUtils;
import com.google.gson.Gson;
import com.hanvon.HWCloudManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by jswang on 2016/11/11.
 * 一对一答疑--拍照问答
 */

public class OtoAskActivity extends BaseEvenActivity<OtoAskPresent, OtoAskModel> implements OtoAskPresent.View, Toolbar.OnMenuItemClickListener
        , EasyPermissions.PermissionCallbacks {

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

    @BindView(R.id.ll_subject)
    LinearLayout ll_subject;

    @BindView(R.id.ll_grade)
    LinearLayout ll_grade;

    @BindView(R.id.mSeekBar)
    CustomSeekbar mSeekBar;

    @BindView(R.id.reply)
    Button reply;

    private MaterialDialog materialDialog;
    private OtoAskDialog otoAskDialog;
    private String subject = OtoConstant.subjectValues[0];
    private String grade = OtoConstant.versionTvValues[0];
    private int askMoney = 0;

    ArrayList<LabelEntity> subjectList = new ArrayList<>();
    ArrayList<LabelEntity> gradeList = new ArrayList<>();

    private String[] perms = {Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    private final static int RC_RECORD_AUDIO = 0x02;

    private HWCloudManager hwCloudManagerFormula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_oto_ask, null);
        setContentView(contentView);
        ButterKnife.bind(this);

        //取消全屏滑动
        getSwipeBackLayout().setSwipeMode(SwipeBackLayout.ORIGINAL);
    }

    @Override
    public void initData() {
        super.initData();
        picTakePath = getIntent().getStringExtra("picTakePath");
        requestPermissions();


    }

    public void requestPermissions() {
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "需要开启语音和拍照存储权限",
                    RC_RECORD_AUDIO, perms);
        } else {

            hwCloudManagerFormula = new HWCloudManager(mthis, OtoConstant.HWY_KEY);
        }

    }


    @Override
    public void initView() {
        super.initView();
        setToolbar(toolBar, "提交问题");
        toolBar.inflateMenu(R.menu.menu_otoask_more);
        getLoadingDialog().content("提交中...");
        materialDialog = getLoadingDialog().build();
        if (Asking91.getmInstance().getmDataList().size() > 0 && TextUtils.isEmpty(picTakePath)) {
            picTakePath = Asking91.getmInstance().getmDataList().get(0).get(0).getCompressPath();
        }
        if (!TextUtils.isEmpty(picTakePath)) {
            if (TextUtils.equals(picTakePath, "meWord")) {
                picTakePath = "";
                picTake.setImageResource(R.mipmap.onlineqa_ask_pic_bg);
            } else {
                caBitmap = CommonUtil.decodeBitmapWithOrientationMax(picTakePath, 800, 800);
                picTake.setImageBitmap(caBitmap);
            }
        }

        subjectList.add(new LabelEntity(OtoConstant.subjectValues[0], getString(R.string.online_dialog1_t1), true));
        subjectList.add(new LabelEntity(OtoConstant.subjectValues[1], getString(R.string.online_dialog1_t2)));
        CourseViewHelper.getView2(this, ll_subject, getString(R.string.oto_ask_t1), CourseViewHelper.getCourseViewAdapter(this, subjectList
                , new OnItemLabelEntityListener() {
                    @Override
                    public void OnItemLabelEntity(LabelEntity e) {
                        subject = e.getId();
                    }
                }));

        gradeList.add(new LabelEntity(OtoConstant.versionTvValues[0], getString(R.string.online_dialog1_t3), true));
        gradeList.add(new LabelEntity(OtoConstant.versionTvValues[1], getString(R.string.online_dialog1_t4)));
        gradeList.add(new LabelEntity(OtoConstant.versionTvValues[2], getString(R.string.online_dialog1_t5)));
        gradeList.add(new LabelEntity(OtoConstant.versionTvValues[3], getString(R.string.online_dialog1_t6)));
        gradeList.add(new LabelEntity(OtoConstant.versionTvValues[4], getString(R.string.online_dialog1_t7)));
        gradeList.add(new LabelEntity(OtoConstant.versionTvValues[5], getString(R.string.online_dialog1_t8)));
        CourseViewHelper.getView2(this, ll_grade, getString(R.string.oto_ask_t2), CourseViewHelper.getCourseViewAdapter(this, gradeList
                , new OnItemLabelEntityListener() {
                    @Override
                    public void OnItemLabelEntity(LabelEntity e) {
                        grade = e.getId();
                    }
                }));
    }


    //-----------拍照End------------------
    private Bitmap caBitmap;

    public void onEventMainThread(AppEventType event) {
        switch (event.type) {
            case AppEventType.OTO_QA_AS_CAMERA_REQUEST:
                picTakePath = (String) event.values[0];
                caBitmap = (Bitmap) event.values[1];
                ArrayList<TImage> images = new ArrayList();
                images.add(TImage.of(picTakePath, TImage.FromType.OTHER));
                Asking91.getmInstance().getmDataList().clear();
                Asking91.getmInstance().getmDataList().add(images);
                picTake.setImageBitmap(CommonUtil.decodeBitmapWithOrientationMax(picTakePath,
                        DeviceUtil.getScreenWidth(mthis), DeviceUtil.getScreenHeight(mthis)));
                break;
            case AppEventType.SUBMIT_SUCCESS:
                finish();
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                otoAskDialog = new OtoAskDialog(mthis);
                otoAskDialog.show();
                return true;
        }
        return false;
    }

    @Override
    public void initListener() {
        super.initListener();
        toolBar.setOnMenuItemClickListener(this);

        mSeekBar.setMax(6);
        mSeekBar.setCustomSeekbarListener(new CustomSeekbar.OnCustomSeekbarListener() {
            @Override
            public void OnCustomSeekbar(int volume) {
                askMoney = volume;
            }
        });
    }

    @OnClick({R.id.pic_take, R.id.reply})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pic_take:
                CameraActivity.openCameraActivity(this, AppEventType.OTO_QA_AS_CAMERA_REQUEST, 0);
                break;
            case R.id.reply:
                if (TextUtils.isEmpty(picTakePath)) {
                    showShortToast("请拍下题目哦~");
                } else {
                    materialDialog.show();
                    mPresenter.qiniutoken();
                }
                break;
        }
    }

    private String picTakePath, picName, picMsg, picHwyResult;

    @Override
    public void onSuccess(String methodName, ResponseBody baseRsqEntity) {
        if (methodName.equals("otoGetToken")) {
            try {
                picMsg = baseRsqEntity.string();
                //   JSONObject jsonRes = JSON.parseObject(result);
                //  if (TextUtils.equals("0", jsonRes.getString("flag"))) {
                if (!TextUtils.isEmpty(picMsg)) {
                    //    picMsg = jsonRes.getJSONObject("content").getString("token");
                    //发起上传题目
                    picName = DateUtil.currentDateMilltime().replace(":", "-").replace(" ", "_") + "oto_ask.jpg";
                    mPresenter.upload(picTakePath, picName, picMsg);
                    FileUtils.deleteDir(new File(BitmapUtil.getGreyPath()));

                    //屏蔽汉王识别
                    //mPresenter.getHwyQuestion(hwCloudManagerFormula, picTakePath, caBitmap);
                } else {
                    materialDialog.dismiss();
                    showShortToast("上传失败");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 打开教师等待界面
     */
    private void openTeaWaitingActivity() {
        String userName = getUserConstant().getUserName();
        mPresenter.studentinfo(this, userName, new ApiRequestListener<String>() {
            @Override
            public void onResultSuccess(String resStr) {
                materialDialog.dismiss();

                JSONObject resObject = JSON.parseObject(resStr);
                int askTimes = resObject.getInteger("askTimes");
                double integral = resObject.getDouble("integral");
                String userAvatar = resObject.getString("avatar");
                String qiNiuUrl = OtoConstant.QiNiuHead + picName;

                if (integral != getUserConstant().getUserEntity().getIntegral()) {
                    getUserConstant().getUserEntity().setIntegral(integral);
                    getUserConstant().saveUserData(new Gson().toJson(getUserConstant().getUserEntity()));
                }

                if (askTimes <= 0 || integral >=0) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("askTimes", askTimes);
                    bundle.putInt("askMoney", askMoney);
                    bundle.putString("userAvatar", userAvatar);
                    bundle.putString("subject", subject);
                    bundle.putString("grade", grade);
                    bundle.putString("picTakePath", picTakePath);
                    bundle.putString("picName", picName);
                    bundle.putString("qiNiuUrl", qiNiuUrl);
                    bundle.putString("picHwyResult", picHwyResult);
                    openActivity(TeaWaitingActivity.class, bundle);
                } else {
                    showShortToast("阿思币不足，请充值");
                }
            }

            @Override
            public void onResultFail() {
                materialDialog.dismiss();
            }
        });


    }

    @Override
    public void onSuccess(String methodName, String string) {
        if (methodName.equals("upload-onNext")) {
            openTeaWaitingActivity();
        } else if (methodName.equals("getHwyQuestion-next")) {
            picHwyResult = "";
            if (!TextUtils.isEmpty(string)) {
                picHwyResult = CommonUtil.getReplaceStr(string);
            }
            if (TextUtils.isEmpty(picHwyResult)) {
                //showShortToast("未找到匹配题目");
            }
            materialDialog.show();
            picName = DateUtil.currentDateMilltime().replace(":", "-").replace(" ", "_") + "oto_ask.jpg";
            mPresenter.upload(picTakePath, picName, picMsg);
            FileUtils.deleteDir(new File(BitmapUtil.getGreyPath()));
        }
    }

    @Override
    public void onRequestError(RequestEntity requestEntity) {
        materialDialog.dismiss();
        if (requestEntity.getTag().equals("upload_retrywhen")) {
            showShortToast("未登录或者登录已过期，请重新登录");
        } else if (requestEntity.getTag().equals("getHwyQuestion")) {

        }
        if (requestEntity.getCode() == HttpCodeConstant.TIME_OUT) {
            showShortToast("网络不稳定，提交超时");
            materialDialog.dismiss();
        }

    }

    @Override
    public void onRequestStart() {
        materialDialog.show();
    }

    @Override
    public void onRequestEnd() {
        materialDialog.dismiss();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        hwCloudManagerFormula = new HWCloudManager(mthis, OtoConstant.HWY_KEY);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //失败不让提交问题
        reply.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        try {
            FileUtils.deleteFile(new File(picTakePath));
        } catch (Exception e) {
        }
        super.onDestroy();
    }


}
