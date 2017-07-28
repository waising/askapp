package com.asking91.app.ui.search.subject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.basepacket.SubjectEntity;
import com.asking91.app.global.SearchConstant;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.adapter.search.SubjectListAdapter;
import com.asking91.app.ui.widget.AskSwipeRefreshLayout;
import com.asking91.app.util.CommonUtil;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.ResponseBody;

public class SubjectListActivity extends BaseFrameActivity<SubjectDetailPresenter, SubjectDetailModel> implements SubjectDetailContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.layout_none)
    LinearLayout layout_none;
    @BindView(R.id.editText)
    EditText mEditText;
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.swipe_layout)
    AskSwipeRefreshLayout swipeLayout;
    @BindView(R.id.tv_once)
    TextView mtv_once;
    @BindView(R.id.layout_search)
    LinearLayout layout_search;

    private SubjectListAdapter adapter;
    private List<SubjectEntity> list;
    private String me, subjectCatalog;
    private int start = 0, limit = 5;

    /*
        limit 即请求的题目数，这里设置5条就好，不要设太多，比如设10条的话页面加载就很卡顿，mathview不给力
        另外一点关于下拉加载更多，这边的处理是使用新的数据，即容器没有一直add。原因：一直加载更多，越加越多，很容易造成页面卡死，mathview不给力
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_subject_list, null);
        setContentView(contentView);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); // 关闭软键盘
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolBar, SearchConstant.SearchSubject.tittle_detail);
    }

    @Override
    public void initData() {
        super.initData();
        Intent intent = getIntent();
        if (intent != null) {
            me = intent.getStringExtra("me");
            subjectCatalog = intent.getStringExtra("subjectCatalog");
            list = intent.getParcelableArrayListExtra("data");
            if (me.equals("meWord")) {
                String stringEditText = intent.getStringExtra("stringEditText");
                if (list != null && list.size() > 0) {
                    layout_none.setVisibility(View.GONE);
                    mEditText.setText(stringEditText);
                    SubjectListAdapter adapter = new SubjectListAdapter(this, list, subjectCatalog);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                    mRecyclerView.setAdapter(adapter);
                } else {
                    swipeLayout.setVisibility(View.GONE);
                    swipeLayout.setMode(PtrFrameLayout.Mode.NONE);
                }
                mToolBar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        swipeLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout ptrFrameLayout) {
                start += limit;
                adapter = null;
                getDataNow();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                start = 0;
                list.clear();
                getDataNow();
            }
        });
    }

    private void getDataNow() {
        mPresenter.presenterSearchSubject(222, subjectCatalog, mEditText.getText().toString().trim(), start, limit); // 文字搜题
    }

    @Override
    public void onRequestSuccess(int type, ResponseBody responseBody) {
        Map<String, Object> map = CommonUtil.parseDataToMap(responseBody);
        list = (ArrayList<SubjectEntity>) CommonUtil.parseDataToList(map.get("list"), new TypeToken<List<SubjectEntity>>() {
        });
        switch (type) {
            case 222:
                try {
/*              subjectEntity = (ArrayList<SubjectEntity>) CommonUtil.parseDataToList(map.get("list"), new TypeToken<List<SubjectEntity>>() { });
                list.addAll(subjectEntity); //备用 */
                    if (list != null && list.size() > 0) {
                        swipeLayout.setMode(PtrFrameLayout.Mode.BOTH);
                        swipeLayout.setVisibility(View.VISIBLE);
                        adapter = null;
                        adapter = new SubjectListAdapter(this, list, subjectCatalog);
                        mRecyclerView.setAdapter(adapter);
                        swipeLayout.refreshComplete();
                    } else {
                        swipeLayout.refreshComplete();
                        showShortToast(R.string.no_more_data);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
                }
                break;
        }
    }

    @OnClick({R.id.img_search, R.id.tv_exit, R.id.tv_once})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_search:
                if (mEditText.getText().toString().trim().length() > 0) {
                    mPresenter.presenterSearchSubject(222, subjectCatalog, mEditText.getText().toString().trim(), start, limit); // 文字搜题
                } else {
                    showShortToast(R.string.please_enter_keyword);
                }
                break;
            case R.id.tv_exit:
                // 输入框的“取消”按钮
                finish();
                break;
            case R.id.tv_once:
                // 文字搜题入口过来的，再搜一题
                finish();
                break;
        }
    }

    @Override
    public void onSuccess(String methodName, String string) {
    }

    @Override
    public void onInternetError(String methodName) {
        swipeLayout.refreshComplete();
    }

    @Override
    public void onError(int type) {

    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {
        //swipeLayout.refreshComplete();
    }

}
