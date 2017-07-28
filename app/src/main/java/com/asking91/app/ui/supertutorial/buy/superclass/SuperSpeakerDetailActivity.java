package com.asking91.app.ui.supertutorial.buy.superclass;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.common.SwipeBackActivity;
import com.asking91.app.entity.supertutorial.SuperClassComeToSpeak;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.AskRadioGroup;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * Created by wxiao on 2016/12/9.
 * 典例详情
 * 来讲题的详情--只用来显示，没有网络访问
 */

public class SuperSpeakerDetailActivity extends SwipeBackActivity {
    @BindView(R.id.user_name_tv)
    TextView userNameTv;

    @BindView(R.id.title_more)
    ImageView titleMore;

    @BindView(R.id.title_click)
    LinearLayout titleClick;

    @BindView(R.id.user_info_rl)
    RelativeLayout userInfoRl;

    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @BindView(R.id.detailMathView)
    AskMathView detailMathView;

    @BindView(R.id.rbtn1)
    RadioButton rbtn1;

    @BindView(R.id.rbtn2)
    RadioButton rbtn2;

    @BindView(R.id.rbtn3)
    RadioButton rbtn3;

    @BindView(R.id.rbtn4)
    RadioButton rbtn4;

    @BindView(R.id.rbtn5)
    RadioButton rbtn5;

    @BindView(R.id.radioGroup1)
    AskRadioGroup radioGroup1;

    @BindView(R.id.moreMathView)
    AskMathView moreMathView;

    private SuperClassComeToSpeak listBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supertutorial_speaker_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        listBean = (SuperClassComeToSpeak) getIntent().getSerializableExtra("SuperClassComeToSpeak.ListBean");//前面传后面的数据
    }

    private int size, nowSelectId = -1;

    @Override
    public void initView() {
        super.initView();
        getSwipeBackLayout().setSwipeMode(SwipeBackLayout.ORIGINAL);
        setToolbar(toolBar, "典例详情");
        WebSettings webSettings = detailMathView.getSettings();
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSupportZoom(false);
        detailMathView.formatMath().showWebImage();
        moreMathView.formatMath().showWebImage();
//上面的题目详情
        try {//有题目选项情况下
            String strTmp = "";
            for (SuperClassComeToSpeak.SubjectBean.Options o : listBean.getSubject().getOptions()) {//for循环选项
                strTmp = strTmp + o.getOption_name() + ". " + o.getOption_content_html().substring(3, o.getOption_content_html().length() - 4) + "<br/>";
            }
            detailMathView.setText(listBean.getSubject().getSubject_description_html() + strTmp);
        } catch (Exception e) {//无题目选项情况下
            detailMathView.setText(listBean.getSubject().getSubject_description_html());
        }


//解析下面的列表项
        try {
            if (listBean.getTabList().size() != 0) {//tabList不为空情况
                size = listBean.getTabList().size() + 1;
                for (int i = 0; i < size; i++) {
                    RadioButton radioButton = (RadioButton) radioGroup1.getChildAt(i);
                    if (i == size - 1) {//如果到最后一项后的一项，添加详细解题
                        initShowDetailChild(radioButton, listBean.getSubject().getDetails_answer_html());
                    } else {//其他，逐步添加tab页
                        radioButton.setVisibility(View.VISIBLE);
                        radioButton.setText(listBean.getTabList().get(i).getTabTypeName());
                        radioButton.setTag(i);
                        radioButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (v.getId() != nowSelectId) {
                                    nowSelectId = v.getId();
                                    moreMathView.setWebText(listBean.getTabList().get(Integer.parseInt(v.getTag() + "")).getTabContentHtml());
                                    moreMathView.setVisibility(View.VISIBLE);
                                } else {
                                    nowSelectId = -1;
                                    radioGroup1.clearCheck();
                                    moreMathView.setVisibility(View.GONE);
                                    moreMathView.setText("");
                                }
                            }
                        });
                    }
                }
            } else {//tabList为空情况
                if (!listBean.getSubject().getDetails_answer_html().isEmpty()) {//详细解题
                    initShowDetailChild(rbtn1, listBean.getSubject().getDetails_answer_html());
                    radioGroup1.setVisibility(View.VISIBLE);
                } else {//详细解题也没有的情况
                    radioGroup1.setVisibility(View.GONE);
                    rbtn1.setVisibility(View.INVISIBLE);
                    rbtn2.setVisibility(View.INVISIBLE);
                    rbtn3.setVisibility(View.INVISIBLE);
                    rbtn4.setVisibility(View.INVISIBLE);
                    rbtn5.setVisibility(View.INVISIBLE);
                }
            }
        } catch (Exception e) {
        }

        //最后添加变式题这一项，点击后跳转到变式题详情页
        final RadioButton radioButton = (RadioButton) radioGroup1.getChildAt(size);
        radioButton.setText("变式题");
        radioButton.setVisibility(View.VISIBLE);
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("variant_problem_list", (Serializable) listBean.getSubjectmuls());
                openActivity(SpeakerSubjectDetailActivity.class, bundle);
            }
        });
    }

    /**
     * 添加详细解题选项
     *
     * @param radioButton
     * @param htmlString
     */
    private void initShowDetailChild(RadioButton radioButton, final String htmlString) {
        radioButton.setText("详细解题");
        radioButton.setVisibility(View.VISIBLE);
        radioButton.setTag(htmlString);
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() != nowSelectId) {
                    nowSelectId = v.getId();
                    moreMathView.setWebText(v.getTag() + "");
                    moreMathView.setVisibility(View.VISIBLE);
                } else {
                    nowSelectId = -1;
                    radioGroup1.clearCheck();
                    moreMathView.setVisibility(View.GONE);
                    moreMathView.setText("");
                }
            }
        });
    }
}
