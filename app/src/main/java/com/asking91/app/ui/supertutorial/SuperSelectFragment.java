package com.asking91.app.ui.supertutorial;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.asking91.app.R;
import com.asking91.app.entity.pay.SynchronousCourseEntity;
import com.asking91.app.global.UserConstant;
import com.asking91.app.mvpframe.base.BaseFrameFragment;
import com.asking91.app.ui.login.LoginActivity;
import com.asking91.app.ui.supertutorial.buy.classify.SuperClassifyActivity;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.DialogUtil;
import com.asking91.app.util.SharePreferencesHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;


/**
 * Created by wxiao on 2016/10/17.
 * 超级辅导课————选择页面改写
 */

public class SuperSelectFragment extends BaseFrameFragment<SuperSelectPresenterImpl, SuperSelectModelImpl> implements SuperSelectContract.View {
    @BindView(R.id.tool_text)
    TextView toolText;
    /**
     * 0,同步课堂 1,练吧
     */
    private int showType = 0;


    ArrayList<SynchronousCourseEntity.NodelistBeanX> tabList = new ArrayList<>();
    private Dialog pdDialog = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_supertutorial_select);
        ButterKnife.bind(this, getContentView());
        showType = this.getArguments().getInt("showType");
    }

    public Dialog showProgressDialog(String msg, boolean isCanCancel) {
        closeProgressDialog();
        if (null == pdDialog) {
            pdDialog = DialogUtil.getProgressDialog(getActivity(), msg, isCanCancel);
        }
        return pdDialog;
    }

    public void closeProgressDialog() {
        if (pdDialog != null) {
            pdDialog.dismiss();
            pdDialog = null;
        }
    }

    @Override
    public void initLoad() {
        super.initLoad();
        requstSelect();
    }

    /**
     * 请求选择页
     */
    private void requstSelect() {
        showProgressDialog("请稍候", false);
        mPresenter.SynchronousCourse(getActivity(), "KC03", new ApiRequestListener<String>() {//获取同步课堂数据
            @Override
            public void onResultSuccess(String res) {
                closeProgressDialog();
                ArrayList<SynchronousCourseEntity> list = (ArrayList<SynchronousCourseEntity>) JSON.parseArray(res, SynchronousCourseEntity.class);
                if (list != null && list.size() > 0) {//有数据
                    SynchronousCourseEntity synchronousCourseEntity = list.get(0);
                    if (synchronousCourseEntity != null) {
                        tabList.addAll(synchronousCourseEntity.getNodelist());
                    }
                } else {

                }
            }

            @Override
            public void onResultFail() {
                super.onResultFail();
                closeProgressDialog();
            }
        });


    }

    @Override
    public void initView() {
        super.initView();
        toolText.setText(showType != 1 ? getString(R.string.super_syncbook) : getString(R.string.home_menu_test));
    }

    @OnClick({R.id.l1, R.id.l2, R.id.l3, R.id.l4})
    public void onClick(View view) {
        //练吧不要求登录
        if (showType != 1 && !UserConstant.getInstance(getActivity()).isTokenLogin()) {
            openActivity(LoginActivity.class);
            return;
        }

        if (tabList != null && tabList.size() > 0) {

            switch (view.getId()) {
                case R.id.l1://初中数学
                    turnRun("M2", getString(R.string.ask_czsx), showType, findVersion(getString(R.string.ask_czsx)));
                    //     turnRunBuy("M2", getString(R.string.ask_czsx), showType);
                    break;
                case R.id.l2://初中物理
                    turnRun("P2", getString(R.string.ask_czwl), showType, findVersion(getString(R.string.ask_czwl)));
                    break;
                case R.id.l3://高中数学
                    //    turnRunBuy("M3", getString(R.string.ask_gzsx), showType);
                    turnRun("M3", getString(R.string.ask_gzsx), showType, findVersion(getString(R.string.ask_gzsx)));
                    break;
                case R.id.l4://高中物理
                    // turnRunBuy("P3", getString(R.string.ask_gzwl), showType);
                    turnRun("P3", getString(R.string.ask_gzwl), showType, findVersion(getString(R.string.ask_gzwl)));
                    break;
            }

        }

    }

    /**
     * 跳转到上一次学习的目录那一页
     *
     * @param data
     */
    private void turnRunSupergeTree(String[] data, SynchronousCourseEntity.NodelistBeanX item) {
        Bundle bundle = new Bundle();
        bundle.putString("gradeId", data[0]);
        bundle.putInt("showType", showType);//默认练习
        bundle.putString("action", data[2]);
        bundle.putString("verName", data[3]);
        bundle.putString("versionName", String.format("%s-%s", data[4], data[5]));
        bundle.putString("courseTypeName", data[4]);
        bundle.putString("testData", "1");
        bundle.putSerializable("item", item);
        CommonUtil.openActivity(SupergeTreeActivity.class, bundle);
    }


    private void turnRun(String action, String verName, int showType, SynchronousCourseEntity.NodelistBeanX item) {
        Bundle bundle = new Bundle();
        bundle.putInt("showType", showType);
        bundle.putString("action", action);
        bundle.putString("verName", verName);
        bundle.putSerializable("item", item);
        //1,练吧,练吧需要跳转到上一次选择过的章节页
        if (showType == 1) {
            String testData = SharePreferencesHelper.getInstance(getActivity().getApplicationContext()).getString("super_test_data");
            String[] data = testData.split(",");
            if (!TextUtils.isEmpty(testData) && data[2].equals(action)) {//有无记录上一次学习
                turnRunSupergeTree(data, item);
            } else {
                openActivity(SuperClassifyActivity.class, bundle);
            }

        } else {//0,同步课堂
            openActivity(SuperClassifyActivity.class, bundle);
        }


    }


    @Override
    public void onResultSuccess(String method, ResponseBody res) {

    }

    @Override
    public void onResultSuccess(String method, ResponseBody res, int position) {

    }

    @Override
    public void onFreeStudySuccess(ResponseBody obj) {

    }

    @Override
    public void onFreeStudyVersionSuccess(ResponseBody obj) {

    }

    @Override
    public void onRefreshData(ResponseBody res) {

    }

    @Override
    public void onLoadMoreData(ResponseBody res) {

    }

    @Override
    public void onRequestStart() {
    }

    @Override
    public void onRequestEnd() {
    }


    @Override
    public void onGetSuperSelectSuccess(ResponseBody obj) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 根据productName查找相应的版本
     *
     * @param productName
     */
    public SynchronousCourseEntity.NodelistBeanX findVersion(String productName) {
        SynchronousCourseEntity.NodelistBeanX item = null;
        SynchronousCourseEntity.NodelistBeanX.ValueBeanX value = null;
        for (int i = 0; i < tabList.size(); i++) {
            item = tabList.get(i);
            if (item != null) {
                value = item.getValue();
                if (value != null && value.getProductName().equals(productName)) {
                    return tabList.get(i);
                }

            }

        }
        return null;
    }
}
