package com.asking91.app.ui.mine.mynote;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.asking91.app.R;
import com.asking91.app.entity.onlineqa.OnlineQADetailEntity;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.adapter.mine.MyNoteAdapter;
import com.asking91.app.ui.login.LoginActivity;
import com.asking91.app.ui.mine.mytestrecord.TestRecordContract;
import com.asking91.app.ui.mine.mytestrecord.TestRecordModel;
import com.asking91.app.ui.mine.mytestrecord.TestRecordPresenter;
import com.asking91.app.ui.onlinetest.topicask.OnlineTestTopicAskNodeActivity;
import com.asking91.app.ui.widget.AskSwipeRefreshLayout;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.SharePreferencesHelper;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import okhttp3.ResponseBody;

//我的笔记

public class MyNoteActivity extends BaseFrameActivity<TestRecordPresenter, TestRecordModel> implements TestRecordContract.View, Toolbar.OnMenuItemClickListener {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.swipe_layout)
    AskSwipeRefreshLayout swipeLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    String token;
    int start = 0, limit = 5;
    MyNoteAdapter myNoteAdapter;
    /**
     * list
     */
    private List<OnlineQADetailEntity.AnwserEntity> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mynote);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        SharePreferencesHelper.getInstance(this).putString("meNote", "nothing");//是否修改笔记成功标记
        token = getUserConstant().getToken();
        if (token == null) {//是否登录
            CommonUtil.openActivity(this, LoginActivity.class, null);
        }
    }

    @Override
    public void initLoad() {
        super.initLoad();
        getDataNow();
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolbar, getResources().getString(R.string.my_note));//标题栏
        mToolbar.inflateMenu(R.menu.menu_note);//标题栏右边文字
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //取消全屏滑动
        getSwipeBackLayout().setSwipeMode(SwipeBackLayout.ORIGINAL);
    }

    @Override
    public void initListener() {
        super.initListener();
        mToolbar.setOnMenuItemClickListener(this);//标题栏menu监听
        swipeLayout.setPtrHandler(new PtrDefaultHandler2() {//刷新控件
            @Override
            public void onLoadMoreBegin(PtrFrameLayout ptrFrameLayout) {
                //　上拉加载更多
                start += limit;
                getDataNow();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                // 下拉刷新
                start = 0;
                list.clear();
                swipeLayout.setMode(PtrFrameLayout.Mode.BOTH);
                getDataNow();
            }
        });
    }

    /**
     * 删除笔记接口回调
     */
    int DelPosition;
    MyNoteAdapter.CallDelNote callDelNote = new MyNoteAdapter.CallDelNote() {
        @Override
        public void delNote(int currentPosition) {
            // 删除笔记
            DelPosition = currentPosition;
            showDelDialog();
        }
/*        @Override
        public void alterNote(String str) {
*//*            // 修改笔记
            mPresenter.presenterMyNote(token, start, limit);*//*
        }*/
    };

    /**
     * 右上角笔记编辑界面跳转
     * @param item
     * @return
     */

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_node://
                openActivity(OnlineTestTopicAskNodeActivity.class);
                break;
        }
        return true;
    }

    /**
     * 删除接口弹出窗
     */
    public void showDelDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.dialog_content)
                .positiveText(R.string.dialog_sure_del_note)
                .neutralText(R.string.dialog_cancel)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which.name().equals("POSITIVE")) {
                            String MynoteID = list.get(DelPosition).getId();
                            if (!TextUtils.isEmpty(MynoteID)) {
                                mPresenter.presenterDelMyNote(token, MynoteID);//删除请求
                            }
                        }
                    }
                })
                .show();
    }

    /**
     * 页面数据请求
     */
    private void getDataNow() {
        mPresenter.presenterMyNote(token, start, limit);
    }

    /**
     * 填充页面数据
     */
    public void refshAdapt() {
        if (myNoteAdapter == null) {
            myNoteAdapter = new MyNoteAdapter(this, list);
            myNoteAdapter.startDelNote(callDelNote);
            recyclerView.setAdapter(myNoteAdapter);
        } else {
            myNoteAdapter.setData(list);
            myNoteAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 页面请求成功返回
     * @param responseBody
     */
    @Override
    public void onRequestSuccess(ResponseBody responseBody) {
        swipeLayout.refreshComplete(); // 关闭刷新控件动画
        Map<String, Object> map = CommonUtil.parseDataToMap(responseBody);
        if (map != null && map.get("list") != null) {
            List<OnlineQADetailEntity.AnwserEntity> listPaper = CommonUtil.parseDataToList(map.get("list"), new TypeToken<List<OnlineQADetailEntity.AnwserEntity>>() {
            });
            if (listPaper != null && listPaper.size() > 0) {
                list.addAll(listPaper); // 实现分页加载更多所需要的容器数据
                refshAdapt();
            } else if (start > 0) {
                // 后台返回的 total 值是 double 类型，不能用 int 整型去接，麻烦，暂时先用这个方法判断
/*                if(map.get("total")!=null){
                    Integer total = Integer.valueOf((String) map.get("total"));
                    if(total<=start){
                        showShortToast("没有更多数据了");
                    }*/
                swipeLayout.setMode(PtrFrameLayout.Mode.REFRESH);
                showShortToast(R.string.no_more_data);
            }
        }

    }

    /**
     * 删除成功
     * @param responseBody
     */
    @Override
    public void onRefreshData(ResponseBody responseBody) {
        Map<String, Object> map = CommonUtil.parseDataToMap(responseBody);
        showShortToast(R.string.success_del);
        list.remove(DelPosition);
        myNoteAdapter.notifyItemRemoved(DelPosition);
    }

    @Override
    public void onLoadMoreData(ResponseBody responseBody) {

    }

    @Override
    public void onRequestSuccess(int type, ResponseBody responseBody) {
    }

    @Override
    public void onRequestStart() {
    }

    @Override
    public void onRequestEnd() {
    }

    /**
     * 请求出错时
     * @param methodName
     */
    @Override
    public void onInternetError(String methodName) {
        if (swipeLayout != null) {
            swipeLayout.refreshComplete();
        }
        start = 0;
        swipeLayout.setMode(PtrFrameLayout.Mode.REFRESH);
        showShortToast(R.string.no_more_data);
    }

    /**
     * 修改笔记界面成功保存回来时,重新刷新界面
     */
    @Override
    protected void onResume() {
        String str = SharePreferencesHelper.getInstance(this).getString("meNote");//修改笔记是否成功
        if (str.equals("noteSaveSuccess")) {
            if (list.size() > 0) {
                myNoteAdapter.notifyDataSetChanged();
                list.clear();
            }
            mPresenter.presenterMyNote(token, start, limit);
        }
        super.onResume();
    }

}
