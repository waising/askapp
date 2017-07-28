package com.asking91.app.ui.onlinetest.topicask;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.EditText;

import com.asking91.app.R;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.publicdata.PublicDataContract;
import com.asking91.app.ui.publicdata.PublicDataModel;
import com.asking91.app.ui.publicdata.PublicDataPresenter;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.SharePreferencesHelper;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * 新增笔记页面
 * Created by wxwang on 2016/11/18.
 */
public class OnlineTestTopicAskNodeActivity extends BaseFrameActivity<PublicDataPresenter, PublicDataModel>
        implements PublicDataContract.View {

    @BindView(R.id.toolBar)
    Toolbar mToolBar;

    @BindView(R.id.node_text)
    EditText mNodeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlinetest_topic_ask_node);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolBar, getString(R.string.ask_node));
    }

    @Override
    public void initData(){
        super.initData();
        SharePreferencesHelper.getInstance(this).putString("meNote","nothing");//笔记内容标记
    }

    @Override
    public void initLoad(){
        super.initLoad();
    }

    /**
     * 笔记保存成功回调
     * @param type
     * @param body
     */
    @Override
    public void onSuccess(int type, ResponseBody body) {
        Map<String,Object> map = CommonUtil.parseDataToMap(body);

        if((Double)map.get("flag") != 0){
            showShortToast("保存失败");
        }else{
            SharePreferencesHelper.getInstance(this).putString("meNote","noteSaveSuccess");//笔记内容保存成功标记
            showShortToast("保存成功");
            finish();
        }
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {
        showShortToast("保存失败");
    }

    @OnClick(R.id.save_node_btn)
    public void onClick(){
        if(TextUtils.isEmpty(mNodeText.getText())){
            showShortToast("请输入笔记内容");
        }else{
            mPresenter.saveNode(String.valueOf(mNodeText.getText()));
        }
    }
}
