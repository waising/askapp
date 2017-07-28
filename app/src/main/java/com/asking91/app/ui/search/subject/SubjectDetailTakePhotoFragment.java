package com.asking91.app.ui.search.subject;

import android.os.Bundle;
import android.view.View;

import com.asking91.app.R;
import com.asking91.app.common.BaseFragment;
import com.asking91.app.entity.basepacket.KnowledgeTypeDetailEntity;
import com.asking91.app.ui.widget.AskMathView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SubjectDetailTakePhotoFragment extends BaseFragment {

    @BindView(R.id.mathViewTitle)
    AskMathView mathViewTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getContentView();
        if (contentView == null) {
            setContentView(R.layout.layout_subject_detail_take_photo);
            ButterKnife.bind(this, getContentView());
            mathViewTitle.formatMath().showWebImage();
            String subjectTitle = getArguments().getString("subjectTitle");// 题目
            String meDetailAnswer = getArguments().getString("meDetailAnswer");
            if (meDetailAnswer.equals("meDetailAnswerOther")) {
                KnowledgeTypeDetailEntity detailEntity = getArguments().getParcelable("subjectDetailEntity"); // 题目解析
                if (detailEntity != null) {
                    String tabContentHtml = detailEntity.getTabContentHtml(); // 答案
                    String str = ("<b>题目</b>" + "<br/>" + "<p>" + subjectTitle + "<p>" + tabContentHtml);
                    mathViewTitle.setText(str);
                }
            }
            if (meDetailAnswer.equals("meDetailAnswerChoice")) {
                String detailsAnswerHtml = getArguments().getString("detailsAnswerHtml"); // 题目解析
                String str = ("<b>题目</b>" + "<br/>" + "<p>" + subjectTitle + "<p>" + detailsAnswerHtml);
                mathViewTitle.setText(str);
            }

        }
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initView() {
        super.initView();
    }

}
