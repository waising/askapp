package com.asking91.app.ui.mine.mywrongtopic;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.asking91.app.R;
import com.asking91.app.entity.user.MyWrongTopicEntity;
import com.asking91.app.mvpframe.base.BaseFrameFragment;
import com.asking91.app.ui.mine.mytestrecord.TestRecordContract;
import com.asking91.app.ui.mine.mytestrecord.TestRecordModel;
import com.asking91.app.ui.mine.mytestrecord.TestRecordPresenter;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.StarView;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;


public class MyWrongFragment extends BaseFrameFragment<TestRecordPresenter, TestRecordModel> implements TestRecordContract.View {

    @BindView(R.id.tv_index)
    TextView tv_index;

    @BindView(R.id.topic_mathview)
    AskMathView topic_mathview;

    @BindView(R.id.tv_error_count)
    TextView tv_error_count;

    @BindView(R.id.start_view)
    StarView start_view;

    @BindView(R.id.answer_mathView)
    AskMathView answer_mathView;

    @BindView(R.id.tv_parsing)
    TextView tv_parsing;

    ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
    
    private MyWrongTopicEntity mEntity;
    private int position;
    private String subjectCode;

    public static MyWrongFragment newInstance(int position,String subjectCode,MyWrongTopicEntity mEntity) {
        MyWrongFragment fragment = new MyWrongFragment();
        fragment.position = position;
        fragment.subjectCode = subjectCode;
        fragment.mEntity = mEntity;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_my_wrong_topic);
        ButterKnife.bind(this, getContentView());

    }

    @Override
    public void initView() {
        super.initView();

        tv_index.setText(""+(position+1));
        topic_mathview.setText(mEntity.getSubjectDescriptionHtml());

        String error_count = String.format("错误次数：%s次",mEntity.getErrorCount());
        SpannableStringBuilder builder = new SpannableStringBuilder(error_count);
        builder.setSpan(redSpan, 5, 5+(mEntity.getErrorCount()+"").length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_error_count.setText(builder);

        answer_mathView.setText(mEntity.analysisTopic);
        answer_mathView.setVisibility(mEntity.isSelect?View.VISIBLE:View.GONE);
        tv_parsing.setSelected(mEntity.isSelect);

        if(TextUtils.isEmpty(mEntity.analysisTopic)){
            tv_parsing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.convertTabLClassic(subjectCode, mEntity.getSubjectId(), new ApiRequestListener<String>() {
                        @Override
                        public void onResultSuccess(String res) {
                            if(TextUtils.isEmpty(mEntity.analysisTopic)){
                                JSONObject jsonRes = JSONObject.parseObject(res);
                                mEntity.analysisTopic = jsonRes.getJSONArray("taps").getJSONObject(0).getString("tab_content_html");
                                answer_mathView.setText(mEntity.analysisTopic);
                            }
                            mEntity.isSelect = !mEntity.isSelect;
                            answer_mathView.setVisibility(mEntity.isSelect?View.VISIBLE:View.GONE);
                        }
                    });
                }
            });
        }else{
            mEntity.isSelect = !mEntity.isSelect;
            answer_mathView.setVisibility(mEntity.isSelect?View.VISIBLE:View.GONE);
        }
    }

    @Override
    public void onRequestSuccess(int type, ResponseBody responseBody) {

    }

    @Override
    public void onRequestSuccess(ResponseBody responseBody) {

    }

    @Override
    public void onRefreshData(ResponseBody responseBody) {

    }

    @Override
    public void onLoadMoreData(ResponseBody responseBody) {

    }

    @Override
    public void onInternetError(String methodName) {

    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }
}
