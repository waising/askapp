package com.asking91.app.ui.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.global.Constants;
import com.asking91.app.util.CommonUtil;

public class AskMathView extends RelativeLayout {
    private AskMathWebView web_name;
    private TextView tv_name;

    public AskMathView(Context context) {
        super(context);
        init(context);
    }

    public AskMathView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public AskMathView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_math_web_txt_view, this);
        web_name = (AskMathWebView) findViewById(R.id.math_web_name);
        web_name.setEnabled(false);
        tv_name = (TextView) findViewById(R.id.math_tv_name);

        web_name.setVisibility(View.GONE);
        tv_name.setVisibility(View.GONE);
    }

    public AskMathWebView showWebImage(Context context, final MultiStateView multiStateView) {
        return web_name.showWebImage(context, multiStateView);
    }

    public AskMathWebView showWebImage(MultiStateView multiStateView) {
        return web_name.showWebImage(multiStateView);
    }

    public AskMathWebView showWebImage() {
        return web_name.showWebImage(getContext(), null);
    }

    public AskMathWebView showWebImage(Context context) {
        return web_name.showWebImage(context, null);
    }

    public void loadUrl(String url) {
        web_name.setVisibility(View.VISIBLE);
        web_name.setText(url);
    }

    public AskMathWebView setWebText(String text) {
        setText(text);
        return web_name;
    }

    public void setText(String text) {
        web_name.setVisibility(View.GONE);
        tv_name.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(text)) {
            if (CommonUtil.isMath(text)) {//有公式
                web_name.setVisibility(View.VISIBLE);
                web_name.setText(Constants.SuperTutorialHtmlCss + text + Constants.MD_HTML_SUFFIX);
            } else {//没有公式
                tv_name.setVisibility(View.VISIBLE);
                tv_name.setText(text);
            }
        }
    }

    public WebSettings getSettings() {
        return web_name.getSettings();
    }

    public void setWebViewClient(WebViewClient client) {
        web_name.setWebViewClient(client);
    }

    public void setWebChromeClient(WebChromeClient mClient) {
        web_name.setWebChromeClient(mClient);
    }

    public AskMathWebView getMathWebView() {
        return web_name;
    }

    /**
     * 格式化数学公式
     *
     * @return
     */
    public AskMathWebView formatMath() {
        return web_name.formatMath();
    }

    public String getText() {
        if (web_name.getVisibility() == View.VISIBLE) {
            return web_name.getText();
        } else {
            return tv_name.getText().toString();
        }
    }


    /**
     * 设置webView的字体大小，有公式的情况下
     */
    public void setWebViewTextSize(int size) {
        WebSettings settings = web_name.getSettings();
        settings.setDefaultFontSize(size);
    }


    public static final String BlackHtmlCss = "<html><head><style type=\"text/css\">body{color:#333333;vertical-align:middle;}</style></head><body>";


    /**
     * 设置webView的字体颜色，有公式的情况下
     */
    public void setWebViewTextColor(String text) {
        web_name.setText(BlackHtmlCss + text + Constants.MD_HTML_SUFFIX);
    }


}