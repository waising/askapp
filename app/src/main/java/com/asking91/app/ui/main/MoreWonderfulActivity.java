package com.asking91.app.ui.main;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.asking91.app.R;
import com.asking91.app.common.SwipeBackActivity;
import com.asking91.app.ui.onlineqa.OnlineQAActivity;
import com.asking91.app.ui.onlinetest.OnlineTestActivity;
import com.asking91.app.ui.search.knowledge.SearchKnowledgeActivity;
import com.asking91.app.util.CommonUtil;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wxwang on 2016/11/11.
 * 更多精彩
 */
public class MoreWonderfulActivity extends SwipeBackActivity {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    @BindString(R.string.morewonder_title)
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morewonderful);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolbar, title);
    }


    @OnClick({R.id.s_knowledge, R.id.online_qa, R.id.online_test})
    public void imgOnClick(View view) {
        switch (view.getId()) {
            case R.id.s_knowledge:
                openActivity(SearchKnowledgeActivity.class);
                break;
            case R.id.online_qa:
                CommonUtil.openAuthActivity(this,OnlineQAActivity.class);
                break;
            case R.id.online_test:
                CommonUtil.openAuthActivity(this,OnlineTestActivity.class);
                break;
        }
    }
}
