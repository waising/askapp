package com.asking91.app.ui.supertutorial.selected;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.supertutorial.ExamReviewTree;
import com.asking91.app.mvpframe.base.BaseFrameFragment;
import com.asking91.app.ui.supertutorial.SuperSelectModelImpl;
import com.asking91.app.ui.supertutorial.SuperSelectPresenterImpl;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.util.CommonUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 第一轮复习的章节页的fragment
 * Created by linbin on 2017/3/2.
 */

public class CatalogueSelectRoundOneFragment extends BaseFrameFragment<SuperSelectPresenterImpl, SuperSelectModelImpl> {

    RecyclerView recycler;

    ArrayList<ExamReviewTree> dataList = new ArrayList<>();//章节点数据

    // ExamReviewTree mPExam;

    private CommAdapter mAdapter;

    /**
     * M2-初中数学（8）  P2-初中物理（6）  M3-高中数学（9.5）  P3-高中物理（7）
     */
    String actionType;

    private String action;
    private String verName;

    public static CatalogueSelectRoundOneFragment newInstance(String actionType, String action, String verName) {
        CatalogueSelectRoundOneFragment fragment = new CatalogueSelectRoundOneFragment();
        Bundle bundle = new Bundle();
        bundle.putString("actionType", actionType);
        bundle.putString("action", action);
        bundle.putString("verName", verName);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static CatalogueSelectRoundOneFragment newInstance() {
        CatalogueSelectRoundOneFragment fragment = new CatalogueSelectRoundOneFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catelogue_selected, container, false);
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        Bundle bundle = getArguments();
        if (bundle != null) {
            actionType = bundle.getString("actionType");
            action = bundle.getString("action");
            verName = bundle.getString("verName");
        }
        requestSection();
        return view;
    }

    /**
     * 请求一轮复习的章节目录
     */
    private void requestSection() {
        mPresenter.firstreviewzhangjd(this.getActivity(), actionType, new ApiRequestListener<String>() {//获取章节点数据
            @Override
            public void onResultSuccess(String res) {
//                dataList.clear();
                dataList = (ArrayList<ExamReviewTree>) JSON.parseArray(res, ExamReviewTree.class);
                if (dataList != null && dataList.size() > 0) {
                    mAdapter = new CommAdapter(getActivity());
                    recycler.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });


    }

    class CommAdapter extends RecyclerView.Adapter<CommAdapter.ViewHolder> {
        private Context mContext;

        public CommAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public CommAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CommAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_section_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(final CommAdapter.ViewHolder holder, final int position) {
            final ExamReviewTree e = dataList.get(position);

            if (e != null) {
                if (CommonUtil.isMath(e.getText())) {//如果包含英文字符，即包含英文公式的
                    holder.mvName.setVisibility(View.VISIBLE);
                    holder.tvName.setVisibility(View.GONE);

                    holder.mvName.setText(e.getText());
                } else {//不包含英文公式的
                    holder.tvName.setText(e.getText());
                    holder.tvName.setVisibility(View.VISIBLE);
                    holder.mvName.setVisibility(View.GONE);
                }

                holder.llMain.setOnClickListener(new View.OnClickListener() {//每一项的点击事件

                    @Override
                    public void onClick(View view) {//跳转到详情页
                        if (actionType.equals("6")) {//初中物理的时候只有一层，没有第二层
                            Bundle bundle = new Bundle();
                            bundle.putString("action", action);
                            bundle.putString("verName", verName);
                            bundle.putString("fromWhere", "oneRound");//从一轮复习跳转过来
                            bundle.putSerializable("e", e);
                            openActivity(ExamReviewActivity.class, bundle);
                        } else {
//                            FragmentManager mManager = getFragmentManager();
//                            FragmentTransaction ft1 = mManager.beginTransaction();//事务
//                            ft1.addToBackStack(null);//这里将我们的Fragment加入到返回栈
//                            ft1.replace(R.id.frame_branch, CatalogueSelectDetailFragment.newInstance(e, action, verName, new View.OnClickListener() {
//
//
//                                @Override
//                                public void onClick(View view) {
//                                    getFragmentManager().popBackStack();
//                                }
//                            }));//跳转到第二个Fragment
//                            ft1.commit();
                            switchFragment(CatalogueSelectRoundOneFragment.this, CatalogueSelectDetailFragment.newInstance(e, action, verName, new View.OnClickListener() {


                                @Override
                                public void onClick(View view) {
                                    getFragmentManager().popBackStack();
                                }
                            }));
                        }

                    }
                });


            }


        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.tv_name)//章节名
                    TextView tvName;
            @BindView(R.id.mv_name)//有英文公式时
                    AskMathView mvName;

            @BindView(R.id.ll_main)//linearLayout
                    LinearLayout llMain;


            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    public void onRequestStart() {
    }

    public void onRequestError(RequestEntity requestEntity) {
    }

    public void onRequestEnd() {
    }

    /**
     * 切换fragment
     */
    public void switchFragment(Fragment from, Fragment to) {

        FragmentManager mManager = getFragmentManager();
        FragmentTransaction ft1 = mManager.beginTransaction();//事务

        if (!to.isAdded()) {//判断是否被add过
            ft1.addToBackStack(null);//这里将我们的Fragment加入到返回栈
            ft1.hide(from).add(R.id.frame_branch, to).commit();
        } else {
            ft1.hide(from).show(to).commit();
        }
    }


}