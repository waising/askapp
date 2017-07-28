package com.asking91.app.ui.supertutorial.selected;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.asking91.app.R;
import com.asking91.app.entity.supertutorial.ExamRequireEntity;
import com.asking91.app.entity.supertutorial.ExerAskEntity;
import com.asking91.app.mvpframe.base.BaseFrameFragment;
import com.asking91.app.ui.supertutorial.SuperSelectModelImpl;
import com.asking91.app.ui.supertutorial.SuperSelectPresenterImpl;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.ui.widget.camera.adapter.CBaseAdapter;
import com.asking91.app.ui.widget.linegridlayout.MtGridLayout;
import com.asking91.app.util.CommonUtil;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.asking91.app.R.id.recyclerView;

/**
 * 考点
 * Created by jswang on 2017/3/2.
 */

public class ExamFirstRequireFragment extends BaseFrameFragment<SuperSelectPresenterImpl, SuperSelectModelImpl> {
    @BindView(recyclerView)
    RecyclerView recycler;
    @BindView(R.id.multiStateView)
    MultiStateView multiStateView;

    @BindView(R.id.ll_tab1)//第一步
    View ll_tab1;
    @BindView(R.id.ll_tab2)//第二步
    View ll_tab2;
    @BindView(R.id.ll_tab3)//第三步
    View ll_tab3;

    private List<ExamRequireEntity> mDataList = new ArrayList<>();
    private CommAdapter mAdapter;

    public static ExamFirstRequireFragment newInstance(String pid) {
        ExamFirstRequireFragment fragment = new ExamFirstRequireFragment();
        Bundle bundle = new Bundle();
        bundle.putString("pid", pid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_exam_first_req);
        ButterKnife.bind(this, getContentView());
        Bundle bundle = getArguments();
        if (bundle != null) {
            pid = bundle.getString("pid");
        }
    }

    @Override
    public void initData() {
        super.initData();
        mDataList = new ArrayList<>();
        mAdapter = new CommAdapter(getContext(), mDataList);//考点adapter
    }

    @Override
    public void initView() {
        super.initView();
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(mAdapter);
    }

    @Override
    public void initLoad() {
        super.initLoad();
        if (!TextUtils.isEmpty(pid)) {//默认第一步
            setTabSelect(1);
        }
    }


    @OnClick({R.id.ll_tab1, R.id.ll_tab2, R.id.ll_tab3})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_tab1://第一步
                setTabSelect(1);
                break;
            case R.id.ll_tab2://第二步
                setTabSelect(2);
                break;
            case R.id.ll_tab3://第三步
                setTabSelect(3);
                break;
        }
    }

    private void setTabSelect(int index) {//选中第几步
        switch (index) {
            case 1:
                if (!ll_tab1.isSelected()) {
                    ll_tab1.setSelected(true);
                    ll_tab2.setSelected(false);
                    ll_tab3.setSelected(false);
                    initLoadData(pid, index + "");
                }
                break;
            case 2:
                if (!ll_tab2.isSelected()) {
                    ll_tab1.setSelected(false);
                    ll_tab2.setSelected(true);
                    ll_tab3.setSelected(false);
                    initLoadData(pid, index + "");
                }
                break;
            case 3:
                if (!ll_tab3.isSelected()) {
                    ll_tab1.setSelected(false);
                    ll_tab2.setSelected(false);
                    ll_tab3.setSelected(true);
                    initLoadData(pid, index + "");
                }
                break;
        }
    }

    private String pid;
    private String index;

    public void initLoadData(String pid, String index) {
        this.pid = pid;
        this.index = index;
        if (multiStateView != null) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            mPresenter.firstreviewbeigk(getActivity(), pid, index, new ApiRequestListener<String>() {
                @Override
                public void onResultSuccess(String res) {//考点
                    List<ExamRequireEntity> list = JSON.parseArray(res, ExamRequireEntity.class);
                    if (list != null && list.size() > 0) {
                        mDataList.clear();
                        mDataList.addAll(list);
                        mAdapter.notifyDataSetChanged();
                        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);

                        final ExamRequireEntity e = mDataList.get(0);
                        mPresenter.firstreviewdiant(getActivity(), e._id, new ApiRequestListener<Map<String, String>>() {//第一个考点中的典例
                            @Override
                            public void onResultSuccess(Map<String, String> map) {
                                String res = map.get("diant");
                                List<ExerAskEntity> list = JSON.parseArray(res, ExerAskEntity.class);
                                e.diant.addAll(list);
                                mAdapter.notifyDataSetChanged();
                            }
                        });

                    } else {
                        multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                    }
                }
            });
        }
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    class CommAdapter extends RecyclerView.Adapter<CommAdapter.ViewHolder> {

        private static final int UNSELECTED = -1;
        private List<ExamRequireEntity> mDatas;
        private Context mContext;
        private int selectedItem = UNSELECTED;
        int prePosition = -1;
        private ArrayMap<Integer, ExpandableLayout> exLays;//展开的整个列表的expand 的List

        public CommAdapter(Context context, List<ExamRequireEntity> datas) {
            this.mContext = context;
            this.mDatas = datas;
            exLays = new ArrayMap<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_exam_review_require, parent, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.position = position;
            final ExamRequireEntity item = mDatas.get(position);

            holder.tv_title.setVisibility(View.GONE);
            holder.titleMathView.setVisibility(View.GONE);
            if(CommonUtil.isMath(item.content)){//有公式
                holder.titleMathView.setVisibility(View.VISIBLE);
                holder.titleMathView.setText("考点" + (position + 1) + "：" + item.content);
            }else{
                holder.tv_title.setVisibility(View.VISIBLE);
                holder.tv_title.setText("考点" + (position + 1) + "：" + item.content);
            }

            holder.mImageView.setSelected(false);
            holder.mExpandableLayout.collapse(false);
            exLays.put(position, holder.mExpandableLayout);
            holder.mImageView.setImageResource(R.mipmap.attr_right);

            if (item.diant != null && item.diant.size() > 0) {//典例内容
                holder.ll_list.removeAllViews();
                holder.ll_list.setAdapter(new GridAdapter(item));
            }
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }


        public void setItemImg(int prePosition) {
            if (prePosition > -1) {//收缩前一次展开的
                exLays.get(prePosition).collapse();
                ((ImageView) ((LinearLayout) exLays.get(prePosition).getParent()).findViewById(R.id.expand_iv)).setImageResource(R.mipmap.attr_right);
            }

        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            @BindView(R.id.expandable_layout)
            ExpandableLayout mExpandableLayout;
            @BindView(R.id.titleMathView)
            AskMathView titleMathView;
            @BindView(R.id.tv_title)
            TextView tv_title;

            @BindView(R.id.expand_iv)//展开图标
            ImageView mImageView;
            @BindView(R.id.expand_rl)//有展开图标的那块布局
            RelativeLayout mRl;

            @BindView(R.id.ll_list)//展开后的gridview
            MtGridLayout ll_list;

            int position;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                titleMathView.formatMath();
                mRl.setOnClickListener(this);//有展开图标的那块布局

                ll_list.setOrientation(LinearLayout.VERTICAL);
                ll_list.setColumnCount(1);
            }

            @Override
            public void onClick(View v) {//展开图标的那块布局被点击
                final ExamRequireEntity item = mDatas.get(position);
                if (item.diant != null && item.diant.size() > 0) {
                    collapseExpandable();//布局收起来
                } else {//请求展开中的典例数据
                    mPresenter.firstreviewdiant(getActivity(), item._id, new ApiRequestListener<Map<String, String>>() {
                        @Override
                        public void onResultSuccess(Map<String, String> map) {
                            String res = map.get("diant");
                            List<ExerAskEntity> list = JSON.parseArray(res, ExerAskEntity.class);
                            if (list != null && list.size() > 0) {
                                item.diant.addAll(list);
                                mAdapter.notifyDataSetChanged();
                                recycler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        collapseExpandable();//布局收起来
                                    }
                                });
                            }
                        }
                    });
                }

            }

            /**
             * 布局收起来
             */
            public void collapseExpandable() {
                mImageView.setSelected(false);
                mExpandableLayout.collapse();
                if (position == selectedItem) {
                    selectedItem = UNSELECTED;
                } else {
                    mImageView.setSelected(true);
                    mExpandableLayout.expand();
                    selectedItem = position;
                }
                mImageView.setImageResource(R.mipmap.attr_down);
                setItemImg(prePosition);
                prePosition = selectedItem;
            }
        }


    }

    public class GridAdapter extends CBaseAdapter<ExerAskEntity> {
        ExamRequireEntity mEntity;

        class ViewHolder {
            View btn_detail;
            TextView tvSort;
        }

        public GridAdapter(ExamRequireEntity mEntity) {
            super((Activity) getContext(), mEntity.diant);
            this.mEntity = mEntity;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            GridAdapter.ViewHolder holder = null;
            if (convertView == null) {
                holder = new GridAdapter.ViewHolder();
                convertView = listContainer.inflate(R.layout.item_exam_review_require_c, null);
                holder.btn_detail = convertView.findViewById(R.id.btn_detail);
                holder.tvSort = (TextView) convertView.findViewById(R.id.tv_sort);
                convertView.setTag(holder);
            } else {
                holder = (GridAdapter.ViewHolder) convertView.getTag();
            }
            final ExerAskEntity item = listItems.get(position);

            holder.tvSort.setText(String.format("典例 %s",position+1));
            holder.btn_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.xiangxjt = item.getXiangjbj();
                    item.qiaoxqj = item.getQiaoxqj();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ExerAskEntity", item);
                    bundle.putInt("showType", 1);
                    openActivity(ExamTopicDetailsActivity.class, bundle);
                }
            });

            return convertView;
        }
    }
}

