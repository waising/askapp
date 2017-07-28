package com.asking91.app.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.asking91.app.R;
import com.asking91.app.common.BaseActivity;
import com.asking91.app.common.WebAppClient;
import com.asking91.app.global.Constants;
import com.asking91.app.ui.widget.MultiStateView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jun on 2016/4/18.
 */
public class WebViewActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;

    private String mWebUrl, mTitle;

    public static Intent newIntent(Context context, String title, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(Constants.Key.WEB_TITLE, title);
        intent.putExtra(Constants.Key.WEB_URL, url);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        mWebUrl = getIntent().getStringExtra(Constants.Key.WEB_URL);
        mTitle = getIntent().getStringExtra(Constants.Key.WEB_TITLE);
    }

    @Override
    public void initView() {
        super.initView();
        mToolBar.setTitle(mTitle);
    }

    @Override
    public void initLoad() {
        super.initLoad();

        mWebView.setWebViewClient(new WebAppClient(this, mMultiStateView, mWebView));
        mWebView.loadUrl(mWebUrl, getAuth());
    }

    @Override
    public void initListener() {
        super.initListener();
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.finish();
        }
    }
}
