package com.asking91.app.ui.refer;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.refer.ReferEntity;
import com.asking91.app.global.Constants;
import com.asking91.app.global.ReferConstant;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.util.CommonUtil;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 * Created by wxwang on 2016/11/23.
 */
public class ReferDetailActivity extends BaseFrameActivity<ReferPresenter,ReferModel>
        implements ReferContract.View ,Toolbar.OnMenuItemClickListener {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;

    @BindView(R.id.refer_from_tv)
    TextView mReferFromTv;

    @BindView(R.id.refer_date_tv)
    TextView mReferDataTv;

    @BindView(R.id.mathView)
    AskMathView mathView;
    String referId;
    @BindView(R.id.refer_title_ly)
    LinearLayout mReferTitleLy;
    @BindView(R.id.refer_line)
    TextView mReferLine;

    @BindView(R.id.refer_title_tv)
    TextView mReferTitleTv;

    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void initView(){
        super.initView();
        setToolbar(mToolbar,getString(R.string.refer_detail));
        if(getUserConstant().isTokenLogin())
            mToolbar.inflateMenu(R.menu.menu_refer);

        mathView.formatMath().showWebImage(mMultiStateView);
    }

    @Override
    public void initData(){
        super.initData();
        referId = getIntent().getStringExtra("referId");
    }

    @Override
    public void initLoad(){
        super.initLoad();
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        mReferTitleLy.setVisibility(View.INVISIBLE);
        mReferLine.setVisibility(View.INVISIBLE);
        mPresenter.getReferDetail(referId);

        //判断是否已收藏
        if(getUserConstant().isTokenLogin())
            mPresenter.checkCollection(Constants.Collect.refer,referId);
    }

    @Override
    public void initListener(){
        super.initListener();
        mToolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public void onSuccess(int type,ResponseBody body) {

        switch (type){
            case ReferConstant.Refer.refer_detail:
                detail(body);
                break;
            case ReferConstant.Refer.check:
                check(body);
                break;
            case ReferConstant.Refer.save:
                save(body);
                break;
            case ReferConstant.Refer.delete:
                delete(body);
                break;
        }

    }

    private void check(ResponseBody body){
        Map<String,Object> map = CommonUtil.parseDataToMap(body);
        if(map.get("flag")!=null && "0".equals(String.valueOf(map.get("flag")))){
            if("1".equals(String.valueOf(map.get("collect")))) {
                mToolbar.getMenu().getItem(0).setIcon(R.mipmap.love_2);
                isCollected = true;
            }
        }
    }

    /**
     * 显示详情
     * @param body
     */
    public void detail(ResponseBody body){
        ReferEntity referEntity = CommonUtil.data2Clazz(body,ReferEntity.class);

        if(referEntity!=null){
            mReferTitleTv.setText(referEntity.getTitle());
            mReferFromTv.setText(TextUtils.isEmpty(referEntity.getSource())?"未知":referEntity.getSource());
            mReferDataTv.setText(referEntity.getCreateDateFmt());

            mathView.setText(referEntity.getConnect());

            mReferTitleLy.setVisibility(View.VISIBLE);
            mReferLine.setVisibility(View.VISIBLE);
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);

            title = referEntity.getTitle();
            referId = referEntity.getId();
        }else{
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);

        }
    }


    boolean isCollected = false;

    /**
     * 收藏
     * @param body
     */
    public void save(ResponseBody body){
        Map<String,Object> map = CommonUtil.parseDataToMap(body);
        if(map.get("flag")!=null && "0".equals(String.valueOf(map.get("flag")))){
            showShortToast(String.valueOf(map.get("msg")));
            mToolbar.getMenu().getItem(0).setIcon(R.mipmap.love_2);
            isCollected = true;
        }

    }

    /**
     * 删除收藏
     * @param body
     */
    public void delete(ResponseBody body){
        Map<String,Object> map = CommonUtil.parseDataToMap(body);
        if(map.get("flag")!=null && "0".equals(String.valueOf(map.get("flag")))){
            showShortToast(String.valueOf(map.get("msg")));
            mToolbar.getMenu().getItem(0).setIcon(R.mipmap.love);
            isCollected = false;
        }

    }

    @Override
    public void onError(int type) {
        if (type == ReferConstant.Refer.refer_detail)
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        else if(type == ReferConstant.Refer.save)
            showShortToast("网络异常");
    }

    @Override
    public void onRefreshData(ResponseBody body) {

    }

    @Override
    public void onLoadMoreData(ResponseBody body) {

    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_follow:
                //关注
                if(isCollected) {
                    //取消
                    mPresenter.deleteCollection(title,referId);
                }
                else
                    mPresenter.saveCollection(Constants.Collect.refer, title, referId);
                break;
        }
        return true;
    }
}
