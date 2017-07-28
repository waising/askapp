package com.asking91.app.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.common.BaseActivity;
import com.asking91.app.common.SwipeBackActivity;
import com.asking91.app.util.SystemUtil;

import junit.framework.Test;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jun on 2016/4/20.
 */
public class AboutAppActivity extends SwipeBackActivity {

    @BindView(R.id.version_tv)
    TextView mVersionTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolbar, "");
        mCollapsingToolbar.setTitle(getResources().getString(R.string.app_about));

        mVersionTv.setText(String.valueOf("V " + SystemUtil.getVersionName(this)));
    }

    @OnClick(R.id.version_tv)
    public void onClick(){
    }

    @Override
    public void initLoad() {
        super.initLoad();
    }
}
