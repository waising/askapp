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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 第几讲的fragment，选择的第二层
 * Created by linbin on 2017/3/2.
 */

public class CatalogueSelectDetailFragment extends BaseFrameFragment<SuperSelectPresenterImpl, SuperSelectModelImpl> {

    RecyclerView recycler;

    public ArrayList<ExamReviewTree> dataList = new ArrayList<>();//第几讲的list

    private CommAdapter mAdapter;
    ExamReviewTree mPExam;


    private TextView tvChapter;

    private String action;

    private String verName;


    /**
     * M2-初中数学（8）  P2-初中物理（6）  M3-高中数学（9.5）  P3-高中物理（7）
     */
    String actionType;

    public static CatalogueSelectDetailFragment newInstance(String actionType) {
        CatalogueSelectDetailFragment fragment = new CatalogueSelectDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("actionType", actionType);
        fragment.setArguments(bundle);
        return fragment;
    }


    public static CatalogueSelectDetailFragment newInstance(ExamReviewTree mPExam, String action, String verName, View.OnClickListener clickListener) {
        CatalogueSelectDetailFragment fragment = new CatalogueSelectDetailFragment();
        fragment.mPExam = mPExam;//当前章节对象
        fragment.action = action;
        fragment.verName = verName;
        fragment.clickListener = clickListener;
        return fragment;
    }

    public static CatalogueSelectDetailFragment newInstance(String actionType, ArrayList<ExamReviewTree> dataList) {
        CatalogueSelectDetailFragment fragment = new CatalogueSelectDetailFragment();
        fragment.dataList = dataList;
        fragment.actionType = actionType;
        return fragment;
    }


    private View.OnClickListener clickListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catelogue_selected_detail, container, false);
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        tvChapter = (TextView) view.findViewById(R.id.tv_chapter);
        view.findViewById(R.id.ll_back).setOnClickListener(clickListener);
        Bundle bundle = getArguments();
        if (bundle != null) {
            actionType = bundle.getString("actionType");
        }
        if (mPExam != null) {
            requestSectionDetail();
            tvChapter.setText(mPExam.getText());//章节名
        }
        return view;
    }

    /**
     * 请求一轮复习的当前章节的第几讲目录
     */
    private void requestSectionDetail() {
        mPresenter.firstreviewkesjd(getActivity(), mPExam._id, new ApiRequestListener<String>() {//获取课时节点数据，即第几讲界面，根据章节的id去匹配第几讲
            @Override
            public void onResultSuccess(String res) {
                dataList.addAll(JSON.parseArray(res, ExamReviewTree.class));
                mPExam.list.clear();
                mPExam.list.addAll(dataList);
                mAdapter = new CommAdapter(getActivity());
                recycler.setAdapter(mAdapter);
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
            return new CommAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_section_detail_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(final CommAdapter.ViewHolder holder, final int position) {
            final ExamReviewTree e = dataList.get(position);
            holder.mvName.setOnTouchListener(new View.OnTouchListener() {//点击某项，有公式的
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        //跳转到一轮复习，二轮复习总界面
                        Bundle bundle = new Bundle();
                        bundle.putString("action", action);
                        bundle.putString("verName", verName);
                        bundle.putString("fromWhere", "oneRound");//从一轮复习跳转过来
                        bundle.putSerializable("e", e);
                        openActivity(ExamReviewActivity.class, bundle);
                        // EventBus.getDefault().post(new AppEventType(AppEventType.CATA_SELECT_REQUEST, mPExam, e));
                        return false;
                    }
                    return true;
                }
            });
            holder.mvName.setText(e.getText());
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.mv_name)//有英文公式时
                    AskMathView mvName;

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