package com.asking91.app.ui.supertutorial.selected;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.asking91.app.R;
import com.asking91.app.common.SwipeBackActivity;
import com.asking91.app.entity.supertutorial.ExerAskEntity;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.util.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jswang on 2017/2/28.
 */

public class ExamTopicDetailsActivity extends SwipeBackActivity {
    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @BindView(R.id.load_view)
    MultiStateView load_view;

    @BindView(R.id.web_title)
    AskMathView web_title;

    @BindView(R.id.radio_group)
    RadioGroup radio_group;

    @BindView(R.id.rb_tab1)
    RadioButton rb_tab1;
    @BindView(R.id.rb_tab2)
    RadioButton rb_tab2;
    @BindView(R.id.rb_tab3)
    RadioButton rb_tab3;
    @BindView(R.id.rb_tab4)
    RadioButton rb_tab4;

    @BindView(R.id.load_content_view)
    MultiStateView load_content_view;

    @BindView(R.id.web_zhanycm)
    AskMathView web_zhanycm;

    /**
     * 1-一轮复习备高考|3个要求 0-二轮复习题型诊断分析
     */
    private int showType;
    private String zhanycm;
    private ExerAskEntity mEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_topic_details);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        setToolbar(toolBar, "典例示范");

        showType = getIntent().getIntExtra("showType", 0);
        zhanycm = getIntent().getStringExtra("zhanycm");
        mEntity = (ExerAskEntity) getIntent().getSerializableExtra("ExerAskEntity");
        if (showType == 1) {
            String silyx = mEntity.getSilyx();
            if (!TextUtils.isEmpty(silyx)) {
                zhanycm = silyx;
                rb_tab1.setText("思路研析");
            }
        }

        web_title.formatMath().showWebImage(load_view);
        try{
            String strTmp = "";
            for(ExerAskEntity.OptionsBean o: mEntity.options){
                strTmp = strTmp+ o.getOptionName() + ". " + o.getOptionContentHtml().substring(3, o.getOptionContentHtml().length()-4) + "<br/>";
            }
            web_title.setText((mEntity.subject_description_html+strTmp));
        }catch (Exception e){
            web_title.setText((mEntity.subject_description_html));
        }

        web_zhanycm.formatMath().showWebImage(load_content_view);

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setCurTab(checkedId);
            }
        });

        if (TextUtils.isEmpty(zhanycm)) {
            rb_tab1.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(mEntity.xiangxjt)) {
            rb_tab2.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(mEntity.qiaoxqj)) {
            rb_tab3.setVisibility(View.GONE);
        }

        if (showType == 1) {
            if(mEntity.bianst.size() == 0){
                rb_tab4.setVisibility(View.GONE);
            }
        } else if (mEntity.biansts.size() == 0) {
            rb_tab4.setVisibility(View.GONE);
        }

        if (rb_tab1.getVisibility() == View.VISIBLE) {
            radio_group.check(R.id.rb_tab1);
        } else {
            if (rb_tab2.getVisibility() == View.VISIBLE) {
                radio_group.check(R.id.rb_tab2);
            } else {
                if (rb_tab3.getVisibility() == View.VISIBLE) {
                    radio_group.check(R.id.rb_tab3);
                } else {
                    radio_group.check(R.id.rb_tab4);
                }
            }
        }
    }

    private void setCurTab(int checkedId) {
        switch (checkedId) {
            case R.id.rb_tab1:
                if (!TextUtils.isEmpty(zhanycm)) {
                    load_content_view.setVisibility(View.VISIBLE);
                    web_zhanycm.setText(zhanycm);
                } else {
                    load_content_view.setVisibility(View.GONE);
                }
                break;
            case R.id.rb_tab2:
                if (!TextUtils.isEmpty(mEntity.xiangxjt)) {
                    load_content_view.setVisibility(View.VISIBLE);
                    web_zhanycm.setText(mEntity.xiangxjt);
                } else {
                    load_content_view.setVisibility(View.GONE);
                }
                break;
            case R.id.rb_tab3:
                if (!TextUtils.isEmpty(mEntity.qiaoxqj)) {
                    load_content_view.setVisibility(View.VISIBLE);
                    web_zhanycm.setText(mEntity.qiaoxqj);
                } else {
                    load_content_view.setVisibility(View.GONE);
                }
                break;
            case R.id.rb_tab4:
                Bundle mBundle = new Bundle();
                mBundle.putInt("showType", showType);
                mBundle.putSerializable("ExerAskEntity", mEntity);
                CommonUtil.openActivity(SubjectDetailActivity.class, mBundle);
                break;
        }
    }
}
