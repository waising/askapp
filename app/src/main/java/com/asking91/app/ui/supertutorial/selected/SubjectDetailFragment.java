package com.asking91.app.ui.supertutorial.selected;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.asking91.app.R;
import com.asking91.app.common.BaseFragment;
import com.asking91.app.entity.supertutorial.ExerAskEntity;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.MultiStateView;

import net.cachapa.expandablelayout.ExpandableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 变式题详细页
 * Created by jswang on 2017/5/9.
 */

public class SubjectDetailFragment extends BaseFragment {
    @BindView(R.id.load_view)
    MultiStateView load_view;
    @BindView(R.id.tit_webview)
    AskMathView tit_webview;

    @BindView(R.id.cb_detail)
    CheckBox cb_detail;

    @BindView(R.id.el_layout)
    ExpandableLayout el_layout;

    @BindView(R.id.answer_load_view)
    MultiStateView answer_load_view;

    @BindView(R.id.web_answer)
    AskMathView web_answer;

    ExerAskEntity.OptionsBean mOptions;

    int position;

    public static SubjectDetailFragment newInstance(int position, ExerAskEntity.OptionsBean mOptions) {
        SubjectDetailFragment fragment = new SubjectDetailFragment();
        fragment.mOptions = mOptions;
        fragment.position = position;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_exam_second_topic);
        ButterKnife.bind(this, getContentView());
    }

    @Override
    public void initView() {
        super.initView();

        tit_webview.formatMath().showWebImage(load_view);
        StringBuilder titHtml = new StringBuilder();
        titHtml.append(position + 1).append("：");
        titHtml.append(mOptions.subject_description_html);
        if (mOptions.options != null && mOptions.options.size() > 0) {
//                titHtml.append("<br/>");
            for (ExerAskEntity.OptionsBean option : mOptions.options) {
                titHtml.append(option.getOptionName());
                titHtml.append(". ");
                titHtml.append(option.getOptionContentHtml().replace("<p>", "").replace("</p>", ""));
                titHtml.append("<br/>");
            }

        }
        tit_webview.setText(titHtml.toString());

        if (mOptions.isSelect) {
            el_layout.expand(false);
        } else {
            el_layout.collapse(false);
        }
        cb_detail.setChecked(mOptions.isSelect);
        cb_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOptions.isSelect = !mOptions.isSelect;
                cb_detail.setChecked(mOptions.isSelect);
                if (mOptions.isSelect) {
                    el_layout.expand();
                    cb_detail.setText("收起解析");
                } else {
                    el_layout.collapse();
                    cb_detail.setText("查看解析");
                }
            }
        });

        web_answer.formatMath().showWebImage(answer_load_view);
        web_answer.setText(mOptions.details_answer_html);
    }
}
