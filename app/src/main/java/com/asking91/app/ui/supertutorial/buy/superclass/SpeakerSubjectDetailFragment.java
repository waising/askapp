package com.asking91.app.ui.supertutorial.buy.superclass;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.asking91.app.R;
import com.asking91.app.common.BaseFragment;
import com.asking91.app.entity.supertutorial.SuperClassComeToSpeak;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.MultiStateView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wxiao on 2016/12/12.
 * 来讲题-查看解析-变式题的frament页面
 */

public class SpeakerSubjectDetailFragment extends BaseFragment {

    @BindView(R.id.mathView)
    AskMathView mathView;

    @BindView(R.id.detail)
    CheckBox detail;

    @BindView(R.id.detailAnswer)
    AskMathView detailAnswer;


    @BindView(R.id.ll_show_content)
    LinearLayout ll_show_content;


    @BindView(R.id.answer_load_view)
    MultiStateView answer_load_view;


    SuperClassComeToSpeak.SubjectmulsBean mSubject;
    int position;

    public static SpeakerSubjectDetailFragment newInstance(int position, SuperClassComeToSpeak.SubjectmulsBean mSubject) {
        SpeakerSubjectDetailFragment fragment = new SpeakerSubjectDetailFragment();
        fragment.mSubject = mSubject;
        fragment.position = position;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_supertutorial_speaker_version_detail);
        ButterKnife.bind(this, getContentView());
    }

    @Override
    public void initView() {
        super.initView();

        mathView.formatMath();
        detailAnswer.formatMath();

        try {
            String answer = "";
            if (mSubject.getSubject_type().getType_id().equals("1")) {//选择题才入,如果又选择题
                for (SuperClassComeToSpeak.SubjectmulsBean.OptionsBean a : mSubject.getOptions()) {
                    answer = answer + a.getOption_name() + ". " + a.getOption_content_html().substring(3, a.getOption_content_html().length() - 4) + "<br/>";
                }
                mathView.setText("题" + (position + 1) + "：" + mSubject.getSubject_description_html() + answer);
            } else {
                mathView.setText("题" + (position + 1) + "：" + mSubject.getSubject_description_html());
            }
        } catch (Exception e) {
        }


        detail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ll_show_content.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                detail.setText(isChecked ? "收起解析" : "查看解析");
                if (isChecked) {
                    detailAnswer.formatMath().showWebImage(answer_load_view);
                    detailAnswer.setText(mSubject.getDetails_answer_html());
                } else {


                }
            }
        });


    }
}
