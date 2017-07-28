package com.asking91.app.ui.supertutorial;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.supertutorial.SuperLessonTree;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.pay.PurchaseActivity;
import com.asking91.app.ui.supertutorial.buy.classify.SuperClassifyActivity;
import com.asking91.app.ui.supertutorial.buy.exercises.SuperExercisesActivity;
import com.asking91.app.ui.supertutorial.buy.superclass.SuperClassActivity;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.ui.widget.camera.comm.AppManager;
import com.asking91.app.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * 章节选择页
 * Created by wxwang on 2016/11/1.
 */
public class SupergeTreeActivity extends BaseFrameActivity<SuperSelectPresenterImpl, SuperSelectModelImpl> implements SuperSelectContract.View {
    @BindView(R.id.toolBar)
    Toolbar mToolBar;

    @BindView(R.id.tv_chapter)
    TextView tv_chapter;

    @BindView(R.id.load_view)
    MultiStateView load_view;

    @BindView(R.id.p_recycler)
    RecyclerView p_recycler;

    @BindView(R.id.c_recycler)
    RecyclerView c_recycler;

    @BindView(R.id.ll_c_root)
    View ll_c_root;

    @BindView(R.id.tv_c_tit)
    TextView tv_c_tit;

    String action = "";
    String gradeId = "";
    String verName = "";
    String versionName = "";

    int showType;

    List<SuperLessonTree> pDataList = new ArrayList<>();
    List<SuperLessonTree> cDataList = new ArrayList<>();

    PCommAdapter mPAdapter;
    CCommAdapter mCAdapter;

    /**
     * 章
     */
    private String section;


    private String price;

    private String courseTypeName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_common_treeview);
        AppManager.getAppManager().addActivity(this);

        ButterKnife.bind(this);
        setSwipeBackEnable(false);

        gradeId = getIntent().getStringExtra("gradeId");
        verName = getIntent().getStringExtra("verName");
        versionName = getIntent().getStringExtra("versionName");
        showType = getIntent().getIntExtra("showType", 0);
        action = getIntent().getStringExtra("action");
        price = getIntent().getStringExtra("price");
        courseTypeName = getIntent().getStringExtra("courseTypeName");

    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolBar, verName);

        tv_chapter.setText(versionName);

        mPAdapter = new PCommAdapter(this);
        p_recycler.setLayoutManager(new LinearLayoutManager(this));
        p_recycler.setAdapter(mPAdapter);

        mCAdapter = new CCommAdapter(this);
        c_recycler.setLayoutManager(new LinearLayoutManager(this));
        c_recycler.setAdapter(mCAdapter);

        load_view.setViewState(load_view.VIEW_STATE_LOADING);
        mPresenter.superlessontree(gradeId, new ApiRequestListener<String>() {
            @Override
            public void onResultSuccess(String res) {
                List<SuperLessonTree> list = JSON.parseArray(res, SuperLessonTree.class);
                pDataList.clear();
                pDataList.addAll(list);

                load_view.setViewState(load_view.VIEW_STATE_CONTENT);
            }
        });
    }

    @OnClick({R.id.tv_chapter, R.id.ll_c_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_chapter://顶部标题栏点击跳转
                if (getIntent().getStringExtra("testData") != null && "1".equals(getIntent().getStringExtra("testData"))) {
                    //直接跳转
                    openActivity(SuperClassifyActivity.class, getIntent().getExtras());
                }
                finish();
                break;
            case R.id.ll_c_back://回退的跳转
                ll_c_root.setVisibility(View.GONE);
                p_recycler.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void onItemClickListener(SuperLessonTree e) {
        if (e.getChildren() != null && e.getChildren().size() > 0) {//点第一层时
            tv_c_tit.setText(e.getName());//第几章
            section = e.getName();
            ll_c_root.setVisibility(View.VISIBLE);
            p_recycler.setVisibility(View.GONE);

            cDataList.clear();
            cDataList.addAll(e.getChildren());
            mCAdapter.notifyDataSetChanged();
        } else {//点第二层时
            String knowledgeName = e.getName();
            String knowledgeId = e.getId();
            Bundle bundle = getIntent().getExtras();
            bundle.putString("gradeId", gradeId);
            bundle.putString("knowledgeName", knowledgeName);//第几讲
            bundle.putString("knowledgeId", knowledgeId);
            bundle.putString("versionName", versionName);
            bundle.putString("verName", verName);
            bundle.putString("action", action);
            bundle.putString("schedule", section + "\n  " + knowledgeName);//进度值的保存

            if (showType == 0) {
                //1: 已购买 0未购买 并且不免费
                if (e.getPurchased() != 1 && e.getFree() != 1) {
                    bundle.putString("versionLevelId", e.getId());
                    bundle.putString("className", versionName);
                    bundle.putString("versionName", String.format("%s-%s-%s", getString(R.string.super_syncbook), verName, courseTypeName));
                    bundle.putString("price", price);
                    bundle.putString("timeLimit", this.getIntent().getStringExtra("timeLimit"));
                    bundle.putString("favorableStart", this.getIntent().getStringExtra("favorableStart"));
                    bundle.putString("favorableEnd", this.getIntent().getStringExtra("favorableEnd"));
                    CommonUtil.openActivity(PurchaseActivity.class, bundle);
                } else {
                    CommonUtil.openActivity(SuperClassActivity.class, bundle);
                }

            } else {
                CommonUtil.openActivity(SuperExercisesActivity.class, bundle);
            }
        }
    }

    class PCommAdapter extends RecyclerView.Adapter<PCommAdapter.CommViewHolder> {
        private Context mContext;

        public PCommAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public CommViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CommViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_section_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(final CommViewHolder holder, final int position) {
            final SuperLessonTree e = pDataList.get(position);
            if (e != null) {
                holder.tvName.setText(e.getName());//第几章
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {//每一项的点击事件
                @Override
                public void onClick(View view) {
                    onItemClickListener(e);
                }
            });

        }

        @Override
        public int getItemCount() {
            return pDataList.size();
        }

        class CommViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_name)
            TextView tvName;

            public CommViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    class CCommAdapter extends RecyclerView.Adapter<CCommAdapter.CommViewHolder> {
        private Context mContext;

        public CCommAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public CommViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CommViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_section_detail_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(final CommViewHolder holder, final int position) {
            final SuperLessonTree e = cDataList.get(position);
            holder.mvName.setText(e.getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {//点击某项，没有公式的
                @Override
                public void onClick(View v) {
                    onItemClickListener(e);
                }
            });

            holder.mvName.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_UP:
                            onItemClickListener(e);
                            break;
                    }
                    return true;
                }
            });

            holder.tv_pay.setVisibility(View.GONE);
            if (showType == 0) {//免费和已购买字体黑色，未购买字体灰色

                //未购买
                if (e.getFree() == 1) {//免费
                    holder.tv_pay.setVisibility(View.VISIBLE);
                    holder.mvName.setSelected(false);//字体变黑
                    holder.mvName.setWebViewTextSize(14);
                    holder.mvName.setWebViewTextColor(e.getName());

                } else {//不免费
                    holder.mvName.setSelected(!(e.getPurchased() == 1));
                    if (e.getPurchased() == 1) {
                        holder.mvName.setWebViewTextColor(e.getName());
                    } else {
                        holder.mvName.setText(e.getName());
                    }
                    holder.mvName.setWebViewTextSize(14);
                }
            }
        }

        @Override
        public int getItemCount() {
            return cDataList.size();
        }

        class CommViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.mv_name)
            AskMathView mvName;

            @BindView(R.id.tv_pay)
            TextView tv_pay;

            public CommViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    @Override
    public void onFreeStudyVersionSuccess(ResponseBody obj) {
    }

    @Override
    public void onGetSuperSelectSuccess(ResponseBody obj) {

    }

    @Override
    public void onFreeStudySuccess(ResponseBody obj) {

    }

    @Override
    public void onRefreshData(ResponseBody res) {

    }

    @Override
    public void onLoadMoreData(ResponseBody res) {

    }

    @Override
    public void onResultSuccess(String method, ResponseBody res) {

    }

    @Override
    public void onResultSuccess(String method, ResponseBody res, int position) {

    }

    @Override
    public void onRequestStart() {
    }

    @Override
    public void onRequestEnd() {
    }

    @Override
    public void onRequestError(RequestEntity requestEntity) {
        super.onRequestError(requestEntity);
    }

    @Override
    protected void onDestroy() {
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
    }
}
