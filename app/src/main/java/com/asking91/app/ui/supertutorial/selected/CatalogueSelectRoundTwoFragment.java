package com.asking91.app.ui.supertutorial.selected;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
 * 二轮复习的章节选择的fragment
 * Created by linbin on 2017/3/2.
 */

public class CatalogueSelectRoundTwoFragment extends BaseFrameFragment<SuperSelectPresenterImpl, SuperSelectModelImpl> {

    RecyclerView recycler;

    ArrayList<ExamReviewTree> dataList = new ArrayList<>();//章节点数据

    ExamReviewTree mPExam;

    private CommAdapter mAdapter;

    /**
     * M2-初中数学（8）  P2-初中物理（6）  M3-高中数学（9.5）  P3-高中物理（7）
     */
    String actionType;

    private String action;
    private String verName;

    public static CatalogueSelectRoundTwoFragment newInstance(String actionType, String action, String verName) {
        CatalogueSelectRoundTwoFragment fragment = new CatalogueSelectRoundTwoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("actionType", actionType);
        bundle.putString("action", action);
        bundle.putString("verName", verName);
        fragment.setArguments(bundle);
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
     * 请求二轮复习的章节目录
     */
    private void requestSection() {
        mPresenter.secondreviewtree(this.getActivity(), actionType, new ApiRequestListener<String>() {//第二轮复习的节点树数据获取
            @Override
            public void onResultSuccess(String res) {
                dataList = (ArrayList<ExamReviewTree>) JSON.parseArray(res, ExamReviewTree.class);
                if (dataList != null && dataList.size() > 0) {
                    mAdapter = new CommAdapter(getActivity());
                    recycler.setAdapter(mAdapter);
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
            return new CommAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_round_two_section_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(final CommAdapter.ViewHolder holder, final int position) {
            final ExamReviewTree e = dataList.get(position);
            if (e != null) {
                if (CommonUtil.isMath(e.getText())) {//如果包含英文字符，即包含英文公式的
                    holder.mvName.setVisibility(View.VISIBLE);
                    holder.tvName.setVisibility(View.GONE);
                    holder.mvName.setOnTouchListener(new View.OnTouchListener() {//点击某项，有公式的
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                //跳转到一轮复习，二轮复习总界面
                                jumToMain(e);
                                return false;
                            }
                            return true;
                        }
                    });
                    holder.mvName.setText(e.name);

                } else {//不包含英文公式的
                    holder.tvName.setText(e.name);
                    holder.tvName.setVisibility(View.VISIBLE);
                    holder.mvName.setVisibility(View.GONE);
                    holder.tvName.setOnClickListener(new View.OnClickListener() {//点击某项，没有公式的
                        @Override
                        public void onClick(View v) {
                            //  EventBus.getDefault().post(new AppEventType(AppEventType.CATA_SELECT_REQUEST, mPExam, e));
                            //跳转到一轮复习，二轮复习总界面
                            jumToMain(e);
                        }
                    });
                }


            }

        }

        /**
         * 跳转到一轮复习，二轮复习总界面
         *
         * @param e
         */
        private void jumToMain(ExamReviewTree e) {
            Bundle bundle = new Bundle();
            bundle.putString("action", action);
            bundle.putString("verName", verName);
            bundle.putString("fromWhere", "twoRound");//从一轮复习跳转过来
            bundle.putSerializable("e", e);
            openActivity(ExamReviewActivity.class, bundle);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.mv_name)//有英文公式时
                    AskMathView mvName;

            @BindView(R.id.tv_name)//章节名
                    TextView tvName;

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
}