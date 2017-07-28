package com.asking91.app.ui.supertutorial.selected;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.asking91.app.R.id.recyclerView;

/**
 * 题型诊断分析界面
 * Created by jswang on 2017/3/2.
 */

public class ExamSecondRequireFragment extends BaseFrameFragment<SuperSelectPresenterImpl, SuperSelectModelImpl> {
    @BindView(recyclerView)
    RecyclerView recycler;
    @BindView(R.id.multiStateView)
    MultiStateView multiStateView;

    private String pid;
    private List<ExamRequireEntity> mDataList = new ArrayList<>();
    private CommAdapter mAdapter;

    public static ExamSecondRequireFragment newInstance(String pid) {
        ExamSecondRequireFragment fragment = new ExamSecondRequireFragment();
        Bundle bundle = new Bundle();
        bundle.putString("pid", pid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_exam_second_req);
        ButterKnife.bind(this, getContentView());
        Bundle bundle = getArguments();
        if (bundle != null) {
            pid = bundle.getString("pid");
        }
    }

    @Override
    public void initView() {
        super.initView();
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CommAdapter(getContext(), mDataList);
        recycler.setAdapter(mAdapter);
    }

    @Override
    public void initLoad() {
        super.initLoad();
        if(!TextUtils.isEmpty(pid)){
            initLoadData(pid);
        }
    }


    public void initLoadData(String pid) {
        this.pid = pid;
        if (multiStateView != null) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            mPresenter.secondreviewzhuant(getActivity(),pid,"tixzdfxs",new ApiRequestListener<String>(){//二轮复习类型
                @Override
                public void onResultSuccess(String res) {
                    String content = JSON.parseObject(res).getString("tixzdfxs");
                    List<ExamRequireEntity> list = JSON.parseArray(content,ExamRequireEntity.class);
                    if (list != null && list.size()>0) {
                        mDataList.clear();
                        mDataList.addAll(list);
                        mAdapter.notifyDataSetChanged();
                        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                    } else {
                        multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                    }
                }
            });
        }
    }

    class CommAdapter extends RecyclerView.Adapter<CommAdapter.ViewHolder>{
        private List<ExamRequireEntity> mDatas;
        private Context mContext;

        public CommAdapter(Context context, List<ExamRequireEntity> datas){
            this.mContext = context;
            this.mDatas = datas;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_exam_second_req,parent,false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final ExamRequireEntity item = mDatas.get(position);
            holder.tv_name.setText("类型"+(position+1)+"："+item.name);//类型名

            holder.load_view.setVisibility(View.VISIBLE);
            holder.web_zhanycm.formatMath().showWebImage(holder.load_view);//斩妖除魔
            if(!TextUtils.isEmpty(item.zhanycm)){
                holder.web_zhanycm.setText(item.zhanycm);//斩妖除魔
            }else{
                holder.load_view.setVisibility(View.GONE);//斩妖除魔
            }

            if(item.isExpand){
                holder.el_layout.expand(false);
            }else{
                holder.el_layout.collapse(false);
            }
            holder.rl_expand.setOnClickListener(new View.OnClickListener(){//第一级，类型展开
                @Override
                public void onClick(View v) {
                    item.isExpand = !item.isExpand;
                    holder.rl_expand.setSelected(item.isExpand);
                    if(item.isExpand){
                        holder.el_layout.expand();
                    }else{
                        holder.el_layout.collapse();
                    }
                }
            });


            holder.rl_expand.setSelected(item.isExpand);

            if(item.diants != null && item.diants.size()>0){
                holder.ll_list.removeAllViews();
                holder.ll_list.setAdapter(new GridAdapter(item));
            }
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.rl_expand)
            RelativeLayout rl_expand;

            @BindView(R.id.tv_name)
            AskMathView tv_name;

            @BindView(R.id.el_layout)
            ExpandableLayout el_layout;

            @BindView(R.id.load_view)
            MultiStateView load_view;

            @BindView(R.id.web_zhanycm)
            AskMathView web_zhanycm;

            @BindView(R.id.ll_list)
            MtGridLayout ll_list;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                ll_list.setOrientation(LinearLayout.VERTICAL);
                ll_list.setColumnCount(1);
            }
        }
    }

    public class GridAdapter extends CBaseAdapter<ExerAskEntity> {
        ExamRequireEntity mEntity;

        class ViewHolder {
            public AskMathView tv_name;
            public View btn_detail;//查看详情
        }

        public GridAdapter(ExamRequireEntity mEntity) {
            super((Activity) getContext(), mEntity.diants);
            this.mEntity = mEntity;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = listContainer.inflate(R.layout.item_exam_second_req_child, null);
                holder.tv_name = (AskMathView) convertView.findViewById(R.id.tv_name);
                holder.btn_detail = convertView.findViewById(R.id.btn_detail);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final ExerAskEntity item = listItems.get(position);
            holder.tv_name.setText("题型"+(position+1)+"："+item.name);
            holder.btn_detail.setOnClickListener(new View.OnClickListener(){//查看解析
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("zhanycm", item.silyx);
                    bundle.putSerializable("ExerAskEntity", item);
                    openActivity(ExamTopicDetailsActivity.class, bundle);
                }
            });

            return convertView;
        }


    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }
}

