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
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.supertutorial.ExamReviewTree;
import com.asking91.app.mvpframe.base.BaseFrameFragment;
import com.asking91.app.ui.supertutorial.SuperSelectModelImpl;
import com.asking91.app.ui.supertutorial.SuperSelectPresenterImpl;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.util.CommonUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 章节页的fragment
 * Created by jswang on 2017/3/2.
 */

public class CardFragment extends BaseFrameFragment<SuperSelectPresenterImpl, SuperSelectModelImpl> {

    /**
     * 章节名
     */
    private Button tv_chapter;

    RecyclerView recycler;

    public ArrayList<ExamReviewTree> dataList = new ArrayList<>();//第几讲的list

    ExamReviewTree mPExam;
    int actionType;

    private CommAdapter mAdapter;

    /**
     * 平常
     *
     * @param mPExam
     * @return
     */
    public static CardFragment newInstance(ExamReviewTree mPExam) {
        CardFragment fragment = new CardFragment();
        fragment.mPExam = mPExam;//当前章节对象
        return fragment;
    }

    /**
     * 第二轮复习的时候，初中物理时
     */
    public static CardFragment newInstance(int actionType, ArrayList<ExamReviewTree> dataList) {
        CardFragment fragment = new CardFragment();
        fragment.dataList = dataList;
        fragment.actionType = actionType;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cata_cardview, container, false);
        tv_chapter = (Button) view.findViewById(R.id.tv_chapter);
        recycler = (RecyclerView) view.findViewById(R.id.recycler);

        tv_chapter.setVisibility(View.GONE);
        if (mPExam != null) {
            tv_chapter.setVisibility(View.VISIBLE);
            if (mPExam.list.size() <= 0) {//没有第二层数据，根据某个章节的id去获取第几讲数据
                mPresenter.firstreviewkesjd(getActivity(), mPExam._id, new ApiRequestListener<String>() {//
                    @Override
                    public void onResultSuccess(String res) {
                        dataList.addAll(JSON.parseArray(res, ExamReviewTree.class));
                        mPExam.list.clear();
                        mPExam.list.addAll(dataList);
                        mAdapter.notifyDataSetChanged();
                    }
                });
            } else {
                dataList.addAll(mPExam.list);//当前章节下的第几讲的list，第一层数据
            }
            tv_chapter.setText(mPExam.getText());//章节名
        }

        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CommAdapter(getActivity());
        recycler.setAdapter(mAdapter);

        return view;
    }

    class CommAdapter extends RecyclerView.Adapter<CommAdapter.ViewHolder> {
        private Context mContext;

        public CommAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public CommAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CommAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_cata_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(final CommAdapter.ViewHolder holder, final int position) {
            final ExamReviewTree e = dataList.get(position);
            if (CommonUtil.isMath(e.getText())) {//如果包含英文字符，即包含英文公式的

                holder.mvName.setVisibility(View.VISIBLE);
                holder.tvName.setVisibility(View.GONE);
                holder.mvName.setOnTouchListener(new View.OnTouchListener() {//点击某项，有公式的
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            EventBus.getDefault().post(new AppEventType(AppEventType.CATA_SELECT_REQUEST, mPExam, e));//mPExam,都是第一层数据
                            getActivity().finish();
                            return false;
                        }
                        return true;
                    }
                });

                if (actionType == 10) {//10，第二轮复习跳转过来
                    holder.mvName.setText(e.name);
                } else {
                    holder.mvName.setText(e.getText());
                }
            } else {//不包含英文公式的
                if (actionType == 10) {//第二轮复习，只有专题，没有第几讲
                    holder.tvName.setText(e.name);
                } else {
                    holder.tvName.setText(e.getText());
                }

                holder.tvName.setVisibility(View.VISIBLE);
                holder.mvName.setVisibility(View.GONE);
                holder.tvName.setOnClickListener(new View.OnClickListener() {//点击某项，没有公式的
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new AppEventType(AppEventType.CATA_SELECT_REQUEST, mPExam, e));
                        getActivity().finish();
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.mv_name)//有英文公式时
                    AskMathView mvName;

            @BindView(R.id.tv_name)//第几讲
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