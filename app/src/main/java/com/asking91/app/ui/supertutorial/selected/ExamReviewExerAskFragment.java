package com.asking91.app.ui.supertutorial.selected;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.common.SpaceItemDecoration;
import com.asking91.app.entity.onlinetest.ResultEntity;
import com.asking91.app.entity.supertutorial.ExerAskEntity;
import com.asking91.app.mvpframe.base.BaseFrameFragment;
import com.asking91.app.ui.supertutorial.SuperSelectModelImpl;
import com.asking91.app.ui.supertutorial.SuperSelectPresenterImpl;
import com.asking91.app.ui.supertutorial.buy.exercises.SuperTopicFragment;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.StarView;
import com.asking91.app.ui.widget.dialog.ContinueLearnDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 实战演练
 * Created by jswang on 2017/3/2.
 */

public class ExamReviewExerAskFragment extends BaseFrameFragment<SuperSelectPresenterImpl, SuperSelectModelImpl> implements ContinueLearnDialog.ClickListner {
    private static final String TAG = SuperTopicFragment.class.getName();

    @BindView(R.id.topic_index_tv)
    TextView mTopicIndexTv;
    @BindView(R.id.topic_mathview)
    AskMathView mTopicMv;
    @BindView(R.id.mathView)
    AskMathView mTopicAnswerMv;
    @BindView(R.id.topic_rv)
    RecyclerView mTopicRv;
    @BindView(R.id.submit_area)
    Button submitArea;//提交按钮
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.start_view)
    StarView startView;
    @BindView(R.id.tip1)
    TextView tip1;
    @BindView(R.id.detail)
    CheckBox detail;//查看解析

    private ExerAskEntity mTopicEntity;
    private int index = 0;//第几页

    private int totalSize;//总页数
    private List<ExerAskEntity.OptionsBean> mDatas;
    private ExerAdapter mAdapter;
    private OnExerFragmentListener mListener;

    private String answerTmp;
    private ExerAskEntity.OptionsBean user_answer;

    private ContinueLearnDialog continueLearnDialog;

    @Override
    public void ok() {
        getActivity().finish();
    }

    public interface OnExerFragmentListener {
        void sendNextFragmentMessage(String id, String answer);

        void sendIndex(int index);
    }

    public static ExamReviewExerAskFragment newInstance(int position, ExerAskEntity mTopicEntity, OnExerFragmentListener mListener, int totalSize) {
        ExamReviewExerAskFragment fragment = new ExamReviewExerAskFragment();
        fragment.mListener = mListener;
        fragment.mTopicEntity = mTopicEntity;
        fragment.index = position;
        fragment.totalSize = totalSize;
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_supertutorial_buy_clearance_topic_ask);
        ButterKnife.bind(this, getContentView());
    }

    @Override
    public void initView() {
        super.initView();
        GridLayoutManager mgr = new GridLayoutManager(getContext(), 4);
        mTopicRv.setLayoutManager(mgr);
        mTopicRv.addItemDecoration(new SpaceItemDecoration(30).isFirstTop(true));
        mTopicRv.setAdapter(mAdapter);
        mTopicMv.formatMath().showWebImage();
        mTopicAnswerMv.formatMath().showWebImage();
        mTopicAnswerMv.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.lightyellow));
    }

    @Override
    public void initData() {
        super.initData();
        mDatas = new ArrayList<>();
        OnExerFragmentListener listener = new OnExerFragmentListener() {
            @Override
            public void sendNextFragmentMessage(String id, String answer) {
                mListener.sendNextFragmentMessage(id, answer);
                answerTmp = answer;
            }

            @Override
            public void sendIndex(int index) {
                mListener.sendIndex(index);
            }
        };
        mAdapter = new ExerAdapter(getContext(), mDatas, listener);
    }

    @OnClick({R.id.submit_area, R.id.detail})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_area://提交按钮
                if (answerTmp == null) {
                    showShortToast("请先答题");
                    return;
                }
                String answerstr = String.format("{\"subList\":[{\"id\":\"%s\",\"subject_type\":{\"type_id\":\"%s\"},\"user_answer\":\"%s\"}]}",
                        mTopicEntity._id, mTopicEntity.getSubjectTypeId(), answerTmp);
                ResultEntity e = new ResultEntity(user_answer._id, user_answer.option_name,
                        mTopicEntity.right_answer, 0);
                for (int i = 0; i < mDatas.size(); i++) {
                    mDatas.get(i).setResultEntity(e);
                }
                mAdapter.updateResult(e);
                submitArea.setVisibility(View.INVISIBLE);
                detail.setVisibility(View.VISIBLE);
                mTopicEntity.showReultBtn = true;
                break;
            case R.id.detail://查看解析
                mTopicEntity.showReult = detail.isChecked();
                mTopicAnswerMv.setVisibility(mTopicEntity.showReult ? View.VISIBLE : View.GONE);
                if (index == totalSize)//最后一页，点击查看解析，15秒后弹出是否继续学习的弹窗
                {
                    if (mTopicEntity.showReult) {
                        showContinueLearnDialog();
                    }
                }
                break;
        }
    }

    /**
     * 显示继续学习弹窗
     */
    private void showContinueLearnDialog() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (continueLearnDialog == null) {
                    continueLearnDialog = new ContinueLearnDialog();
                    continueLearnDialog.setFromWhere(ContinueLearnDialog.FROM_COLLEAGE);
                    continueLearnDialog.setClickListner(ExamReviewExerAskFragment.this);
                }
                continueLearnDialog.show(getChildFragmentManager(), "");
            }
        }, 15000);   //延时15秒
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mTopicEntity != null) {
            mTopicIndexTv.setText(index + ".");
            String answerTmp = "";
            if (mTopicEntity.getOptions() != null) {
                for (ExerAskEntity.OptionsBean a : mTopicEntity.getOptions()) {
                    answerTmp = answerTmp + a.getOptionName() + ". " + a.getOptionContentHtml().substring(3, a.getOptionContentHtml().length() - 4) + "<br/>";
                }
            }
            mTopicMv.setText(mTopicEntity.subject_description_html + answerTmp);
            mTopicAnswerMv.setText(mTopicEntity.details_answer_html);
            mDatas.clear();
            mDatas.addAll(mTopicEntity.getOptions());
            mAdapter.setId(mTopicEntity._id);
            mAdapter.notifyDataSetChanged();
            startView.setmStarNum(mTopicEntity.difficulty);

            if (TextUtils.equals(mTopicEntity.getSubjectTypeId(), "1")) {
                if (mTopicEntity.getOptions() != null && mTopicEntity.getOptions().size() > 0) {
                    detail.setVisibility(mTopicEntity.showReultBtn ? View.VISIBLE : View.INVISIBLE);
                    submitArea.setVisibility(mTopicEntity.showReultBtn ? View.INVISIBLE : View.VISIBLE);
                    detail.setChecked(mTopicEntity.showReultBtn);

                    mTopicAnswerMv.setVisibility(mTopicEntity.showReult ? View.VISIBLE : View.GONE);
                }
            } else {
                mTopicRv.setVisibility(View.GONE);
                submitArea.setVisibility(View.INVISIBLE);
                detail.setVisibility(View.VISIBLE);
                mTopicAnswerMv.setVisibility(mTopicEntity.showReult ? View.VISIBLE : View.GONE);
            }
        }
    }

    class ExerAdapter extends RecyclerView.Adapter<ViewHolder> {
        private List<ExerAskEntity.OptionsBean> answerOptions;
        private Context context;
        private LayoutInflater layoutInflater;

        public void setId(String id) {
            this.id = id;
        }

        private RadioButton view;

        /**
         * 题目id
         */
        private String id;
        private Map<Integer, RadioButton> lays;
        private OnExerFragmentListener mListener;

        public ExerAdapter(Context context, List<ExerAskEntity.OptionsBean> answerOptions,
                           OnExerFragmentListener mListener) {
            this.context = context;
            this.answerOptions = answerOptions;
            this.mListener = mListener;
            layoutInflater = LayoutInflater.from(this.context);
            lays = new ArrayMap<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.item_common_recycle_options, null, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final ExerAskEntity.OptionsBean answerOption = answerOptions.get(position);
            holder.rBtn.setText(answerOption.getOptionName());
            holder.rBtn.setChecked(answerOption.isSelect);

            lays.put(position, holder.rBtn);
            if (answerOption.getResultEntity() != null) {
                ResultEntity resultEntity = answerOption.getResultEntity();
                boolean isOption = resultEntity.getAnswerResult().equals(answerOptions.get(position).getOptionName());
                boolean isRight = resultEntity.getAnswerResult().equals(resultEntity.getRightResult());
                ImageView mStateIv = (ImageView) ((RelativeLayout) (lays.get(position).getParent())).findViewById(R.id.state_bg);
                lays.get(position).setEnabled(false);
                if (isOption) {
                    if (isRight) {
                        mStateIv.setImageResource(R.mipmap.ask_right);
                    } else {
                        mStateIv.setImageResource(R.mipmap.ask_wrong);
                    }
                    mStateIv.setVisibility(View.VISIBLE);
                } else {
                    if (!isRight && answerOptions.get(position).getOptionName().equals(resultEntity.getRightResult())) {
                        mStateIv.setImageResource(R.mipmap.ask_wrong);
                    }
                }
                if (answerOptions.get(position).getOptionName().equals(resultEntity.getRightResult())) {
                    mStateIv.setImageResource(R.mipmap.ask_right);
                }
            } else {
                holder.rBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user_answer = answerOption;
                        if (view != null && view != v) {
                            view.setChecked(false);
                        }
                        if (view != v) {
                            view = (RadioButton) v;
                            view.setChecked(true);
                            //跳转
                            mListener.sendNextFragmentMessage(id, view.getText().toString());
                        }
                    }
                });
            }
        }

        /**
         * 更新结果
         */
        public void updateResult(ResultEntity resultEntity) {
            for (int i = 0; i < answerOptions.size(); i++) {
                boolean isOption = resultEntity.getAnswerResult().equals(answerOptions.get(i).getOptionName());
                boolean isRight = resultEntity.getAnswerResult().equals(resultEntity.getRightResult());
                ImageView mStateIv = (ImageView) ((RelativeLayout) (lays.get(i).getParent())).findViewById(R.id.state_bg);
                lays.get(i).setEnabled(false);
                if (isOption) {
                    if (isRight) {
                        mStateIv.setImageResource(R.mipmap.ask_right);
                    } else {
                        mStateIv.setImageResource(R.mipmap.ask_wrong);
                    }
                    mStateIv.setVisibility(View.VISIBLE);
                } else {
                    if (!isRight && answerOptions.get(i).getOptionName().equals(resultEntity.getRightResult())) {
                        mStateIv.setImageResource(R.mipmap.ask_wrong);
                    }
                }
                if (answerOptions.get(i).getOptionName().equals(resultEntity.getRightResult())) {
                    mStateIv.setImageResource(R.mipmap.ask_right);
                }
            }
        }

        @Override
        public int getItemCount() {
            return answerOptions.size();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rBtn)
        RadioButton rBtn;
        @BindView(R.id.state_bg)
        ImageView stateBg;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

}