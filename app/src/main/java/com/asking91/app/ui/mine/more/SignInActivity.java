package com.asking91.app.ui.mine.more;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.asking91.app.R;
import com.asking91.app.global.MineConstant;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.mine.MineContract;
import com.asking91.app.ui.mine.MineModel;
import com.asking91.app.ui.mine.MinePresenter;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.util.CommonUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import okhttp3.ResponseBody;

/**
 * Created by jswang on 2017/3/23.
 */

public class SignInActivity extends BaseFrameActivity<MinePresenter, MineModel> implements Toolbar.OnMenuItemClickListener,MineContract.View {
    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @BindView(R.id.calen)
    CalendarView calen;

    @BindView(R.id.tv_date)
    TextView tv_date;

    @BindView(R.id.tv_siginin)
    TextView tv_siginin;

    @BindView(R.id.tv_siginin_tip)
    TextView tv_siginin_tip;

    private CommDialog mDialog;

    private List<Integer> siginDates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_mine_signin, null);
        setContentView(contentView);
        ButterKnife.bind(this);

        //取消全屏滑动
        getSwipeBackLayout().setSwipeMode(SwipeBackLayout.ORIGINAL);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(toolBar, getString(R.string.sign_in));
        toolBar.inflateMenu(R.menu.menu_otoask_more);
        toolBar.setOnMenuItemClickListener(this);

        calen.setShowWeekHead(View.GONE);
        calen.setOnCalendarViewListener(new CalendarView.OnCalendarViewListener(){
            @Override
            public void OnCalendarView(String date) {
                tv_date.setText(date);
            }
        });
        tv_date.setText(calen.getToDayDate());

        tv_siginin.setSelected(true);
        mPresenter.checkTodaySign(this,new ApiRequestListener<String>(){
            @Override
            public void onResultSuccess(String res) {
                if(TextUtils.equals(res,"0")){
                    tv_siginin_tip.setText("");
                }else if(TextUtils.equals(res,"1")){
                    tv_siginin.setSelected(false);
                    tv_siginin_tip.setText("今日可领 0.5 阿思币");
                }else if(TextUtils.equals(res,"2")){
                    tv_siginin.setSelected(false);
                    tv_siginin_tip.setText("今日可领 3.5 阿思币");
                }
            }
        });

        mPresenter.dailySign(this,new ApiRequestListener<ArrayMap<String,String>>(){
            @Override
            public void onResultSuccess(ArrayMap<String,String>  resMap) {
                List<Integer> list = JSON.parseArray(resMap.get("signDates"),Integer.class);
                siginDates.clear();
                siginDates.addAll(list);
                calen.reCalenView(list);
            }
        });
    }

    @OnClick({R.id.iv_pre, R.id.iv_next, R.id.tv_siginin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_pre:
                calen.slidePreviousMonth(0);
                break;
            case R.id.iv_next:
                calen.slideToNextMonth(0);
                break;
            case R.id.tv_siginin://签到
                if(!tv_siginin.isSelected()){
                    mPresenter.sign();
                }
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                StringBuilder str = new StringBuilder();
                str.append("1.每日签到送0.5枚币，连续签到满五天将额外获得3枚阿币，作为奖励。")
                        .append("\n\n").append("2.连续五天后就重新开始计算。");
                mDialog = new CommDialog(mthis,"签到规则",str.toString());
                mDialog.show();
                return true;
        }
        return false;
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void onError(int type) {

    }

    @Override
    public void onRefreshData(ResponseBody body) {

    }

    @Override
    public void onLoadMoreData(ResponseBody body) {

    }

    @Override
    public void onSuccess(int type, ResponseBody body) {
        switch (type) {
            case MineConstant.Mine.sign://
                sign(body);
                break;
        }
    }

    private void sign(ResponseBody body){
        Map<String, Object> map = CommonUtil.parseDataToMap(body);
        if(map.get("flag") != null){
            if ( "0".equals(map.get("flag"))) {
                showShortToast(map.get("msg").toString());
            }else if("1".equals(map.get("flag"))){
                setMoney(0.5);
                showShortToast(map.get("msg").toString());
            }else if("2".equals(map.get("flag"))){
                setMoney(3.5);
                showShortToast(map.get("msg").toString());
            }
        }
    }

    /**
     * 签到
     * @param money
     */
    private void setMoney(double money){
        siginDates.add(calen.todayDay);
        calen.reCalenView(siginDates);

        tv_siginin.setSelected(true);
        tv_siginin_tip.setText("");

        getUserConstant().getUserEntity().setIntegral(money + getUserConstant().getUserEntity().getIntegral());
        getUserConstant().saveUserData(new Gson().toJson(getUserConstant().getUserEntity()));
    }
}