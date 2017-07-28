package com.asking91.app.ui.set;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.LabelEntity;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.user.UserEntity;
import com.asking91.app.global.OnlineQAConstant;
import com.asking91.app.global.OtoConstant;
import com.asking91.app.ui.login.LoginActivity;
import com.asking91.app.ui.widget.camera.ui.BaseEvenActivity;
import com.asking91.app.ui.widget.camera.ui.CameraActivity;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.ui.widget.camera.utils.BitmapUtil;
import com.asking91.app.ui.widget.dialog.SelectDialog;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.DateUtil;
import com.asking91.app.util.FileUtils;
import com.asking91.app.util.JLog;
import com.asking91.app.util.QiniuUtil;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by wxiao on 2016/12/19.
 * 个人中心修改
 */

public class SetPersonModifyActivity extends BaseEvenActivity<SetPresenter, SetModel> implements SetPresenter.View,
        Toolbar.OnMenuItemClickListener, EasyPermissions.PermissionCallbacks  {

    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.user_img_iv)
    ImageView userImgIv;
    @BindView(R.id.nickName)
    EditText nickName;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.birthday)
    TextView birthday;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.schoolAddress)
    TextView schoolAddress;
    @BindView(R.id.schoolName)
    TextView schoolName;
    @BindView(R.id.grade)
    TextView grade;
    @BindView(R.id.classGrade)
    TextView classGrade;
    @BindView(R.id.introduce)
    EditText introduce;
    @BindView(R.id.rl_avatar)
    View rl_avatar;

    private TimePickerView pvTime;
    String token;
    private String avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_modify);
        ButterKnife.bind(this);
    }

    private String[] perms = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CHANGE_WIFI_STATE,Manifest.permission.CAMERA};
    private final int CAMERA = 0x03;

    @Override
    public void initData() {
        super.initData();
        token = getUserConstant().getToken();
        if (token == null || !getUserConstant().isTokenLogin()) {
            CommonUtil.openActivity(this, LoginActivity.class, null);
        }
        rl_avatar.setOnClickListener(this);
        if (getUserConstant().getUserEntity().getAvatar() != null) {
            BitmapUtil.displayUserImage(this, getUserConstant().getUserEntity().getAvatar(), userImgIv);
        } else {
            userImgIv.setImageResource(R.mipmap.default_user_img);
            //   BitmapUtil.displayUserImage(this,getUserConstant().getUserEntity().getAvatar(),userImgIv);
        }
        nickName.setText(getUserConstant().getUserEntity().getNickName()); // 昵称
        name.setText(getUserConstant().getUserEntity().getName()); // 用户姓名（用户注册的时候设置的）
        sex.setText(getUserConstant().getUserEntity().getSex() == 0 ? "女" : "男");
        birthday.setText(getUserConstant().getUserEntity().getBirthdayStr());
        address.setText(getUserConstant().getUserEntity().getArea()); // 联系地址（用户自己手动输入，不给选择）
        schoolName.setText(getUserConstant().getUserEntity().getSchoolName());
        introduce.setText(getUserConstant().getUserEntity().getRemark()); // 个人简介
        schoolAddress.setText(getUserConstant().getUserEntity().getRegionName()); // 学校所在 省市县
        String levelId = getUserConstant().getUserEntity().getLevelId(); // 年级
        try {
            if (!TextUtils.isEmpty(levelId)) {
                int integerId = Integer.valueOf(levelId); // 包装类 Integer 不能直接运算(下面的减1)，会报错，得转成基本数据类型 int
                if (integerId > 0) { //要再判断下
                    String gradeVersionValue = OnlineQAConstant.gradeVersionValues[integerId - 1];
                    if (!TextUtils.isEmpty(gradeVersionValue)) {
                        grade.setText(gradeVersionValue); // 年级
                    }
                }
            }
        } catch (Exception e) {

        }

        classGrade.setText(getUserConstant().getUserEntity().getClassId()); // 班级
        regionCode = getUserConstant().getUserEntity().getRegionCode();

    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(toolBar, getString(R.string.personal_center));
        toolBar.inflateMenu(R.menu.menu_commit);
        toolBar.getMenu().getItem(0).setTitle(getString(R.string.save));
    }

    private String picTakePath;
    //-----------拍照End------------------
    private Bitmap caBitmap;

    /**
     * 拍照成功之后
     * @param event
     */
    public void onEventMainThread(AppEventType event) {
        switch (event.type) {
            case AppEventType.SET_PER_MODI_CAMERA_REQUEST://拍照成功之后
                picTakePath = (String) event.values[0];//本地的图片地址
                caBitmap = (Bitmap) event.values[1];
                BitmapUtil.displayUserImage(this, ImageDownloader.Scheme.FILE.wrap(picTakePath), userImgIv);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick({R.id.nickName_lay, R.id.sex_lay,
            R.id.birthday_lay, R.id.address_lay, R.id.schoolAddressLay, R.id.schoolNameLay, R.id.grade_lay, R.id.classGradeLay})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_avatar:
            case R.id.user_img_iv://头像
                if (EasyPermissions.hasPermissions(this, perms)) {//调用系统摄像头
                    CameraActivity.openCameraActivity(this, AppEventType.SET_PER_MODI_CAMERA_REQUEST, 1);
                } else {
                    requestPermissions();//请求权限
                }
                break;
            case R.id.nickName_lay://昵称

                break;
            case R.id.name_lay:

                break;
            case R.id.sex_lay:
                showSexDialog();
                break;
            case R.id.birthday_lay:
                if (pvTime == null) {
                    //时间选择器
                    pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
                    pvTime.setTime(new Date());
                    pvTime.setCyclic(true);
                    pvTime.setCancelable(true);
                    //时间选择后回调
                    pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

                        @Override
                        public void onTimeSelect(Date date) {
                            birthday.setText(DateUtil.getTime(date));
                        }
                    });
                }
                pvTime.show();
                break;
            case R.id.address_lay:

                break;
            case R.id.schoolAddressLay:
                // 学校所在地
                // CommonUtil.openCameraActivity(this,SelectAddressActivity.class);
                Intent intent = new Intent();
                intent.setClass(this, SelectAddressActivity.class);

                int requestCode = 111; // 111
                startActivityForResult(intent, requestCode);
                overridePendingTransition(0, 0);
                // schoolName.setText("");
                break;
            case R.id.schoolNameLay:
                if (!TextUtils.isEmpty(schoolAddress.getText().toString()) && !TextUtils.isEmpty(regionCode)) {
                    mPresenter.presenterGetSchoolInfo(666, regionCode); // 请求学校数据
                } else {
                    showShortToast(R.string.choose_address);
                }
                break;
            case R.id.grade_lay:
                if (!TextUtils.isEmpty(schoolName.getText().toString())) {
                    showGradeDialog();
                } else {
                    showShortToast(R.string.choose_school);
                }
                break;
            case R.id.classGradeLay:
                String strGrade = grade.getText().toString();
                String strSchoolName = schoolName.getText().toString();

                if (!TextUtils.isEmpty(strGrade) && !TextUtils.isEmpty(strSchoolName)) {
                    showClassDialog();
                } else if (TextUtils.isEmpty(strGrade)) {
                    showShortToast(R.string.choose_grade);
                }
                if (TextUtils.isEmpty(strSchoolName)) {
                    showShortToast(R.string.choose_school);
                }
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_commit:
                saveModity();
                break;
        }
        return true;
    }

    @Override
    public void initListener() {
        super.initListener();
        toolBar.setOnMenuItemClickListener(this);
    }

    /**
     * 只要县的region_code
     * levelId--年级
     * classId--班级
     */
    private String regionCode;

    private void saveModity() {
        //上传头像
        //获取qiniu token
        if (picTakePath == null) {
            //保存信息
            mPresenter.updateUser(token, name.getText().toString(), nickName.getText().toString(), sex.getText().toString().equals("女") ? "0" : "1",
                    birthday.getText().toString(), schoolAddress.getText().toString(), regionCode, schoolName.getText().toString(),
                    introduce.getText().toString(), address.getText().toString(), levelId, classGrade.getText().toString(), getUserConstant().getUserEntity().getAvatar());
        } else
            mPresenter.getQiniuToken();
    }

    @Override
    public void onSuccess(String method, String responseBody) {
        if (method.equals("updateUser")) {
            RequestEntity entity = CommonUtil.data2Clazz(responseBody, RequestEntity.class);
            if (entity.getCode() == 0) {
                showShortToast(R.string.success_change);
                // userImgIv // 保存头像
                if (picTakePath != null)
                    getUserConstant().getUserEntity().setAvatar(OtoConstant.QiNiuHead + avatar);
                getUserConstant().getUserEntity().setNickName(nickName.getText().toString()); // 昵称
                getUserConstant().getUserEntity().setName(name.getText().toString()); // 姓名
                getUserConstant().getUserEntity().setSex(sex.getText().toString().equals("女") ? 0 : 1);
                getUserConstant().getUserEntity().setBirthdayStr(birthday.getText().toString()); // 出生日期
                getUserConstant().getUserEntity().setArea(address.getText().toString()); // 联系地址
                getUserConstant().getUserEntity().setRegionName(schoolAddress.getText().toString()); // 学校所在
                getUserConstant().getUserEntity().setSchoolName(schoolName.getText().toString()); // 学校名称
                getUserConstant().getUserEntity().setLevelId(levelId); // 年级
                getUserConstant().getUserEntity().setClassId(classGrade.getText().toString()); // 班级
                getUserConstant().getUserEntity().setRemark(introduce.getText().toString()); // 个人简介
                getUserConstant().getUserEntity().setRegionCode(regionCode); // 用于请求学校

                getUserConstant().setUserEntity(getUserConstant().getUserEntity());
                finish();
            } else {
                showShortToast(entity.getMsg());
            }
        }
    }

    @Override
    public void onSuccess(String method, ResponseBody baseRsqEntity) {
        if ("GetQiniuToken".equals(method)) {
            try {
                String qiniuToken = baseRsqEntity.string();
                //  JSONObject jsonRes = JSON.parseObject(result);
//                if (TextUtils.equals("0", jsonRes.getString("flag"))) {
                if (!TextUtils.isEmpty(qiniuToken)) {
                    //  String qiniuToken = jsonRes.getJSONObject("content").getString("token");

                    //发起上传题目
                    avatar = DateUtil.currentDateMilltime().replace(":", "-").replace(" ", "_") + "oto_ask.jpg";
                    QiniuUtil.getUploadManager().put(picTakePath, avatar, qiniuToken,
                            new UpCompletionHandler() {
                                @Override
                                public void complete(String key, ResponseInfo info, org.json.JSONObject response) {
                                    if (info.isOK()) {
                                        JLog.i("upload_avatar", "上传头像");
                                        //保存信息
                                        mPresenter.updateUser(token, name.getText().toString(), nickName.getText().toString(), sex.getText().toString().equals("女") ? "0" : "1",
                                                birthday.getText().toString(), schoolAddress.getText().toString(), regionCode, schoolName.getText().toString(),
                                                introduce.getText().toString(), address.getText().toString(), levelId, classGrade.getText().toString(), OtoConstant.QiNiuHead + avatar);

                                    } else {
                                        JLog.i("upload_avatar_fial", info.error);
                                        //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                                    }
                                }

                            }, null);


                    FileUtils.deleteDir(new File(BitmapUtil.getGreyPath()));

                } else {
                    showShortToast("上传失败");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onRequestSuccess(int requestType, ResponseBody responseBody) {
        switch (requestType) {
            case 666:
                showSchoolDialog(responseBody);
                break;
        }
    }

    // 学校名称
    private void showSchoolDialog(ResponseBody body) {
        try {
            List<UserEntity> list = CommonUtil.parseDataToList(body, new TypeToken<List<UserEntity>>() {
            });
            if (list != null && list.size() > 0) {
                final List<LabelEntity> dialogEntities = new ArrayList<>();

                for (UserEntity userEntity : list) {
                    dialogEntities.add(new LabelEntity("", userEntity.getSchoolName()));
                }
                final SelectDialog selectDialog = new SelectDialog(this, "meArrayWheel");
                selectDialog.title(getString(R.string.basepacket_plase_choose))
                        .datas(dialogEntities)
                        .callBackListener(new SelectDialog.DialogCallBackListener() {
                            @Override
                            public void callBack(LabelEntity dialogEntity, int pos, String str) {
                                if (!TextUtils.isEmpty(dialogEntity.getName())) {
                                    schoolName.setText(dialogEntity.getName());
                                    if (!schoolName.equals(getUserConstant().getUserEntity().getSchoolName())) {
                                        grade.setText("");
                                        classGrade.setText("");
                                    }
                                }
                                selectDialog.dismiss();
                            }
                        }).show();
            }
        } catch (Exception e) {
            Map<String, Object> map = CommonUtil.parseDataToMap(body);
            if ("0".equals(map.get("flag")) && map.get("msg") != null) {
                showShortToast(map.get("msg").toString());
            }
        }
    }

    // 班级
    private void showClassDialog() {
        final SelectDialog selectDialog = new SelectDialog(this, "meNumericWheelClass");
        selectDialog.title(getString(R.string.basepacket_plase_choose))
                .callBackListener(new SelectDialog.DialogCallBackListener() {
                    @Override
                    public void callBack(LabelEntity dialogEntity, int pos, String str) {
                        classGrade.setText(String.valueOf(pos + 1));
                        // getUserConstant().getUserEntity().setClassId(String.valueOf(pos + 1));
                        selectDialog.dismiss();
                    }
                }).show();
    }

    String levelId; // 年级ID

    // 年级
    private void showGradeDialog() {
        final SelectDialog selectDialog = new SelectDialog(this, "meNumericWheelGrade");
        selectDialog.title(getString(R.string.basepacket_plase_choose))
                .callBackListener(new SelectDialog.DialogCallBackListener() {
                    @Override
                    public void callBack(LabelEntity dialogEntity, int pos, String str) {
                        if (OnlineQAConstant.gradeVersionValues.length < pos)
                            return;

                        if (!OnlineQAConstant.gradeVersionValues[pos].equals(getUserConstant().getUserEntity().getLevelId())) {
                            grade.setText(OnlineQAConstant.gradeVersionValues[pos]);
                            levelId = String.valueOf(pos + 1);
                        } else {
                            classGrade.setText("");
                        }
                        selectDialog.dismiss();
                    }
                }).show();
    }

    // 性别
    private void showSexDialog() {
        final SelectDialog selectDialog = new SelectDialog(this, "meNumericWheelSex");
        selectDialog.title(getString(R.string.basepacket_plase_choose))
                .callBackListener(new SelectDialog.DialogCallBackListener() {
                    @Override
                    public void callBack(LabelEntity dialogEntity, int pos, String str) {
                        sex.setText(str);
                        selectDialog.dismiss();
                    }
                }).show();
    }

    // 接收省市县数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 222) {
            // 222 是省市区
            if (data != null) {
                String address = data.getStringExtra("address"); // 省市县（区）
                //县（区）的代码
                regionCode = data.getStringExtra("RegionCode");
                if (!TextUtils.isEmpty(address)) {
                    schoolAddress.setText(address);
                    if (!address.equals(getUserConstant().getUserEntity().getRegionName())) {
                        // 清空班级年级
                        schoolName.setText("");
                        grade.setText("");
                        classGrade.setText("");
                    }
                }
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onRequestError(String method, RequestEntity requestEntity) {
        super.onRequestError(requestEntity);
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {//请求摄像头权限成功之后
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        CameraActivity.openCameraActivity(this, AppEventType.SET_PER_MODI_CAMERA_REQUEST, 1);//调用系统摄像头
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    /**
     * 请求摄像头权限
     */
    public void requestPermissions() {
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "需要开启摄像头权限",
                    CAMERA, perms);
        }
    }
}
