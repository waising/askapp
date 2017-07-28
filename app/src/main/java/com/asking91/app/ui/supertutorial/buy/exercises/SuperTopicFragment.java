package com.asking91.app.ui.supertutorial.buy.exercises;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.common.SpaceItemDecoration;
import com.asking91.app.entity.onlinetest.ResultEntity;
import com.asking91.app.entity.supertutorial.SuperBuyClearanceEntity;
import com.asking91.app.mvpframe.base.BaseFrameFragment;
import com.asking91.app.ui.adapter.supertutorial.SuperBuyClearanceTopicAskRecycleOptionsAdapter;
import com.asking91.app.ui.supertutorial.SuperSelectContract;
import com.asking91.app.ui.supertutorial.SuperSelectModelImpl;
import com.asking91.app.ui.supertutorial.SuperSelectPresenterImpl;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.StarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * 练吧做题
 * Created by wxwang on 2016/11/16.
 */
public class SuperTopicFragment extends BaseFrameFragment<SuperSelectPresenterImpl, SuperSelectModelImpl> implements SuperSelectContract.View {

    @BindView(R.id.topic_index_tv)
    TextView mTopicIndexTv;

    @BindView(R.id.topic_mathview)
    AskMathView mTopicMv;

    @BindView(R.id.mathView)
    AskMathView mTopicAnswerMv;
    /**
     * 答案提示recyvlerview
     */
    @BindView(R.id.topic_rv)
    RecyclerView mTopicRv;

    @BindView(R.id.submit_area)
    Button submitArea;

    @BindView(R.id.start_view)
    StarView startView;

    @BindView(R.id.detail)
    CheckBox detail;
    /**
     * 题目实体，有rightanswer选项
     */
    private SuperBuyClearanceEntity mTopicEntity;
    private int index = 0;
    /**
     * 题目选项数据
     */
    private List<SuperBuyClearanceEntity.OptionsBean> mDatas = new ArrayList<>();

    /**
     * 答案提示recyvlerview
     */
    private SuperBuyClearanceTopicAskRecycleOptionsAdapter recycleOptionsAdapter;
    private SwitchFragmentCall switchFragmentCall;
    /**
     * 选中的答案
     */
    private String answerTmp;

    private String action;

    public static SuperTopicFragment newInstance(SuperBuyClearanceEntity mTopicEntity, int index, String action) {
        SuperTopicFragment fragment = new SuperTopicFragment();
        fragment.mTopicEntity = mTopicEntity;
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        bundle.putString("action", action);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_supertutorial_buy_clearance_topic_ask);
        ButterKnife.bind(this, getContentView());

        Bundle bundle = getArguments();
        if (bundle != null) {
            index = bundle.getInt("index");
            action = bundle.getString("action");
        }
    }

    @Override
    public void initView() {
        super.initView();

        switchFragmentCall = new SwitchFragmentCall() {//选择答案的监听
            @Override
            public void sendNextFragmentMessage(String id, String answer) {
                answerTmp = answer;
            }

            @Override
            public void sendIndex(int index) {
            }
        };
        recycleOptionsAdapter = new SuperBuyClearanceTopicAskRecycleOptionsAdapter(getContext(), mDatas, switchFragmentCall);

        GridLayoutManager mgr = new GridLayoutManager(getContext(), 4);
        mTopicRv.setLayoutManager(mgr);
        mTopicRv.addItemDecoration(new SpaceItemDecoration(30).isFirstTop(true));
        mTopicRv.setAdapter(recycleOptionsAdapter);
        mTopicMv.formatMath().showWebImage();
        mTopicAnswerMv.formatMath().showWebImage();

        mTopicAnswerMv.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.lightyellow));


    }

    @OnClick({R.id.submit_area, R.id.detail})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_area://提交题目
                if (answerTmp == null) {
                    showShortToast("请先答题");
                    return;
                }
//                String answerstr = String.format("{\"subList\":[{\"id\":\"%s\",\"subject_type\":{\"type_id\":\"%s\"},\"user_answer\":\"%s\"}]}",
//                        mTopicEntity.getId(), mTopicEntity.getSubjectType().getTypeId(), answerTmp);//将选中的答案上传
                //  mPresenter.subjectClassic(answerstr, action.substring(0, 1));
                if (!answerTmp.equals(mTopicEntity.getRightAnswer())) {//如果选中的提是错题
                    mPresenter.submitError(action, mTopicEntity.get_id(), answerTmp);
                }
                ResultEntity e = new ResultEntity(mTopicEntity.getId(), answerTmp,
                        mTopicEntity.getRightAnswer(), 0);//显示答案正确
                notifySuperBuyClearanceTopicAskOptionAdapter(e);
                submitArea.setVisibility(View.INVISIBLE);
                detail.setVisibility(View.VISIBLE);
                break;
            case R.id.detail:
                mTopicAnswerMv.setVisibility(detail.isChecked() ? View.VISIBLE : View.GONE);
                break;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mTopicEntity != null) {
            mTopicIndexTv.setText(index + ".");
            String answerTmp = "";
            for (SuperBuyClearanceEntity.OptionsBean a : mTopicEntity.getOptions()) {
                try {
                    answerTmp = answerTmp + a.getOptionName() + ". " + a.getOptionContentHtml().substring(3, a.getOptionContentHtml().length() - 4) + "<br/>";
                } catch (Exception e) {
                }
            }
            mTopicMv.setText(mTopicEntity.getSubjectDescriptionHtml() + answerTmp);
            mTopicAnswerMv.setText(mTopicEntity.getDetailsAnswerHtml());
            mDatas.clear();
            mDatas.addAll(mTopicEntity.getOptions());
            recycleOptionsAdapter.setId(mTopicEntity.getId());
            recycleOptionsAdapter.notifyDataSetChanged();
            startView.setmStarNum(mTopicEntity.getDifficulty());

            if (mTopicEntity.getOptions().size() > 0 && mTopicEntity.getOptions().get(0).getResultEntity() != null) {
                submitArea.setVisibility(View.INVISIBLE);
                detail.setVisibility(View.VISIBLE);
                mTopicAnswerMv.setVisibility(detail.isChecked() ? View.GONE : View.VISIBLE);
            } else if (mTopicEntity.getDetailsAnswerHtml() != null && mTopicEntity.getOptions().size() == 0) {
                submitArea.setVisibility(View.INVISIBLE);
                detail.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onResultSuccess(String method, ResponseBody res) {
//        if (method.equals("subjectClassic")) {//提交题目接口
//            try {
//                String result = res.string();
//                JSONArray object = new JSONArray(result);
//                SubjectEntity subjectEntity = new Gson().fromJson(object.getString(0), SubjectEntity.class);
//                ResultEntity resultEntity = new ResultEntity(subjectEntity.getId(), subjectEntity.getUserAnswer(),
//                        subjectEntity.getRightAnswer(), 0);
//                notifySuperBuyClearanceTopicAskOptionAdapter(resultEntity);
//                submitArea.setVisibility(View.INVISIBLE);
//                detail.setVisibility(View.VISIBLE);
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }
        if (method.equals("errorQuestion"))//提交错题
        {


        }
    }

    @Override
    public void onResultSuccess(String method, ResponseBody res, int position) {

    }

    @Override
    public void onGetSuperSelectSuccess(ResponseBody obj) {

    }

    @Override
    public void onFreeStudySuccess(ResponseBody obj) {

    }

    @Override
    public void onFreeStudyVersionSuccess(ResponseBody obj) {

    }

    @Override
    public void onRefreshData(ResponseBody res) {

    }

    @Override
    public void onLoadMoreData(ResponseBody res) {

    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public interface SwitchFragmentCall {
        void sendNextFragmentMessage(String id, String answer);

        void sendIndex(int index);
    }

    /**
     * 往每一项中添加正确答案的实体
     *
     * @param resultEntity
     */
    public void notifySuperBuyClearanceTopicAskOptionAdapter(ResultEntity resultEntity) {
        for (int i = 0; i < mDatas.size(); i++) {
            mDatas.get(i).setResultEntity(resultEntity);
        }
        recycleOptionsAdapter.updateResult(resultEntity);
        recycleOptionsAdapter.notifyDataSetChanged();
    }
}
