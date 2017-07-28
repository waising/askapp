package com.asking91.app.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.LabelEntity;
import com.asking91.app.entity.user.SchoolEntity;
import com.asking91.app.global.MineConstant;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.set.SelectAddressActivity;
import com.asking91.app.ui.widget.dialog.SelectDialog;
import com.asking91.app.util.CommonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * Created by wxwang on 2016/11/29.
 */
public class MineSchoolInfoActivity extends BaseFrameActivity<MinePresenter, MineModel> implements MineContract.View {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    @BindView(R.id.name)
    EditText mName;

    @BindView(R.id.school_local)
    TextView mSchoolLocalTv;

    @BindView(R.id.school_name)
    TextView mSchoolNameTv;

    private String mSchoolId;
    private String regionCode;
    private String openActivityName;

    private final static int GET_SCHOOL_LOCAL = 111;
    private final static int GET_BACK_SCHOOL_LOCAL = 222;
    private final static int GET_SCHOOL_CODE = 666;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_info);
        ButterKnife.bind(this);
    }

    @Override
    public void initView(){
        super.initView();
        setToolbar(mToolbar, getString(R.string.school_info));
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void initData(){
        super.initData();

        openActivityName = getIntent().getStringExtra("openCameraActivity");
    }

    @Override
    public void initLoad(){
        super.initLoad();
    }

    @OnClick({R.id.school_local,R.id.school_name,R.id.school_btn})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.school_local:
                Intent intent = new Intent();
                intent.setClass(this, SelectAddressActivity.class);
                startActivityForResult(intent, GET_SCHOOL_LOCAL);
                break;
            case R.id.school_name:
                if (!TextUtils.isEmpty(mSchoolLocalTv.getText().toString()) && !TextUtils.isEmpty(regionCode)) {
                    mPresenter.getSchoolInfo(GET_SCHOOL_CODE, regionCode); // 请求学校数据
                } else {
                    showShortToast(R.string.choose_address);
                }
                break;

            case R.id.school_btn://完善资料
                if(!TextUtils.isEmpty(mSchoolId) && !TextUtils.isEmpty(mName.getText())&&
                        !TextUtils.isEmpty(regionCode) && !TextUtils.isEmpty(mSchoolLocalTv.getText())&&
                        !TextUtils.isEmpty(mSchoolNameTv.getText())){
                    mPresenter.completeUserSchool(mSchoolId,mSchoolNameTv.getText().toString(),
                            regionCode,mSchoolLocalTv.getText().toString(),mName.getText().toString());
                }else{
                    showShortToast("资料填写不完整");
                }

                break;
        }
    }

    // 学校名称
    private void showSchoolDialog(ResponseBody body) {
        try {
            List<SchoolEntity> list = new ArrayList<>();
            list = CommonUtil.parseDataToList(body, new TypeToken<List<SchoolEntity>>() {
            });
            if (list != null && list.size() > 0) {
                final List<LabelEntity> dialogEntities = new ArrayList<>();

                for (SchoolEntity schoolEntity : list) {
                    dialogEntities.add(new LabelEntity(schoolEntity.getId(), schoolEntity.getSchoolName()));
                }
                final SelectDialog selectDialog = new SelectDialog(this, "meArrayWheel");
                selectDialog.title(getString(R.string.basepacket_plase_choose))
                        .datas(dialogEntities)
                        .callBackListener(new SelectDialog.DialogCallBackListener() {
                            @Override
                            public void callBack(LabelEntity dialogEntity, int pos, String str) {
                                if (!TextUtils.isEmpty(dialogEntity.getName())) {
                                    mSchoolNameTv.setText(dialogEntity.getName());
                                    mSchoolId = dialogEntity.getId();
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

    @Override
    public void onSuccess(int type, ResponseBody body) {
        switch (type){
            case GET_SCHOOL_CODE:
                showSchoolDialog(body);
                break;
            case MineConstant.UserSchool.completeUserSchool://完善学校信息
                completeUserSchool(body);
                break;
        }
    }

    @Override
    public void onError(int type) {

    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    /**
     * 完善用户学校资料
     * @param body
     */
    private void completeUserSchool(ResponseBody body){
        Map<String,Object> map = CommonUtil.parseDataToMap(body);
        if(map.get("flag")!=null&& "0".equals(map.get("flag"))){
            showShortToast("完善资料成功！");

            //保存增加的金币
        //    getUserConstant().getUserEntity().setIntegral(getUserConstant().getUserEntity().getIntegral()+20);
            getUserConstant().saveUserData(new Gson().toJson(getUserConstant().getUserEntity()));

            //跳转
            openActivity(openActivityName,getIntent().getExtras());
            finish();
        }else{
            showShortToast("完善资料失败！");
        }
    }

    // 接收省市县数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == GET_BACK_SCHOOL_LOCAL) {
            // 222 是省市区
            if (data != null) {
                String address = data.getStringExtra("address"); // 省市县（区）
                //县（区）的代码
                regionCode = data.getStringExtra("RegionCode");
                if (!TextUtils.isEmpty(address)) {
                    mSchoolLocalTv.setText(address);
                    if (!address.equals(getUserConstant().getUserEntity().getRegionName())) {
                        // 清空学校名称
                        mSchoolNameTv.setText("");
                        mSchoolId = "";
                    }
                }
            }
        }
    }

    @Override
    public void onRefreshData(ResponseBody body) {

    }

    @Override
    public void onLoadMoreData(ResponseBody body) {

    }
}
