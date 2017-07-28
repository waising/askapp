package com.asking91.app.ui.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.common.BaseActivity;
import com.asking91.app.common.WebAppClient;
import com.asking91.app.global.Constants;
import com.asking91.app.ui.main.WebViewActivity;
import com.asking91.app.ui.widget.MultiStateView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by wxiao on 2016/10/26.
 * 服务条款
 */

public class WebViewProtocolActivity extends BaseActivity {
    @BindView(R.id.user_name_tv)
    TextView userNameTv;
    @BindView(R.id.title_more)
    ImageView titleMore;
    @BindView(R.id.title_click)
    LinearLayout titleClick;
    @BindView(R.id.user_info_rl)
    RelativeLayout userInfoRl;
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.mathView)
    WebView mWebView;
    @BindView(R.id.multiStateView)
    MultiStateView multiStateView;
    @BindView(R.id.ok)
    Button ok;
    @BindView(R.id.bottom)
    LinearLayout bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_protocol);
        ButterKnife.bind(this);
        ok = (Button) findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent("com.asking91.app.ui.register.RegisterActivity.ok");
                //intent.putExtra("tag", true);
                //sendBroadcast(intent);
                finish();
            }
        });
    }

    private String mWebUrl, mTitle;

    public static Intent newIntent(Context context, String title, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(Constants.Key.WEB_TITLE, title);
        intent.putExtra(Constants.Key.WEB_URL, url);
        return intent;
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

        mWebView.setWebViewClient(new WebAppClient(this, multiStateView, mWebView));
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
//        if (mWebView.canGoBack()) {
//            mWebView.goBack();
//        } else {
            super.finish();
//        }
    }
}
