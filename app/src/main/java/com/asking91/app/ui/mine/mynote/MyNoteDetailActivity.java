package com.asking91.app.ui.mine.mynote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.asking91.app.R;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.login.LoginActivity;
import com.asking91.app.ui.mine.mytestrecord.TestRecordContract;
import com.asking91.app.ui.mine.mytestrecord.TestRecordModel;
import com.asking91.app.ui.mine.mytestrecord.TestRecordPresenter;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.SharePreferencesHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.richeditor.RichEditor;
import okhttp3.ResponseBody;

//修改我的笔记（查看笔记详情）

public class MyNoteDetailActivity extends BaseFrameActivity<TestRecordPresenter, TestRecordModel> implements TestRecordContract.View {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.knife)
    RichEditor knifeText;
    private String id, content, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mynote_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        token = getUserConstant().getToken();
        SharePreferencesHelper.getInstance(this).putString("meNote", "nothing");
        if (token == null) {
            CommonUtil.openActivity(this, LoginActivity.class, null);
        }

        Intent intent = getIntent();
        if (intent != null) {
            id = getIntent().getStringExtra("id");
            content = getIntent().getStringExtra("content"); // 笔记内容
        }

    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolbar, getString(R.string.alter_node));
        if (!TextUtils.isEmpty(content)) {//显示原来笔记内容
            knifeText.setHtml(content);
        }
    }

    @OnClick({R.id.btn_save})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save://修改后的笔记内容
                String editHtml = knifeText.getHtml();
                mPresenter.presenterAlterMyNote(editHtml, id);
                //showShortToast("提交成功");
                break;
        }
    }


    @Override
    public void onRequestSuccess(int type, ResponseBody responseBody) {
    }

    /**
     * 修改笔记成功
     * @param responseBody
     */
    @Override
    public void onRequestSuccess(ResponseBody responseBody) {
        //  Map<String, Object> map = CommonUtil.parseDataToMap(responseBody);
/*        if( map!=null && map.get("flag")!=null && map.get("flag").equals("0.0") ){
        }*/  // 后台的成功返回值不固定，等以后规范了再用上这语句
        SharePreferencesHelper.getInstance(this).putString("meNote", "noteSaveSuccess");
        this.finish();
    }

    @Override
    public void onRefreshData(ResponseBody responseBody) {
    }

    @Override
    public void onLoadMoreData(ResponseBody responseBody) {

    }

    @Override
    public void onInternetError(String methodName) {
        showShortToast(R.string.alter_node);
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }
}
