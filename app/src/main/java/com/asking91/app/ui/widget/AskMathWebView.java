package com.asking91.app.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.asking91.app.common.WebAppClient;
import com.asking91.app.common.WebAppInterface;
import com.asking91.app.global.Constants;
import com.asking91.app.util.NetworkUtils;
import com.x5.template.Chunk;
import com.x5.template.Theme;
import com.x5.template.providers.AndroidTemplates;

public class AskMathWebView extends WebView {
    private String mText;
    public String mConfig =
            "  MathJax.Hub.Config({\n" +
                    "    extensions: [\"tex2jax.js\"],\n" +
                    "    jax: [\"input/TeX\", \"output/HTML-CSS\"]," +
                    "    showProcessingMessages: false,\n" +
                    "    tex2jax: { inlineMath: [['$','$'],['\\(','\\)']]," +
                    "    displayMath: [ ['$$','$$'], [\"\\\\[\",\"\\\\]\"] ]," +
                    "    processEscapes: true},\n" +
                    "\"HTML-CSS\": { availableFonts: [\"TeX\"] }" +
                    "});";

    @SuppressLint("SetJavaScriptEnabled")
    public AskMathWebView(Context context, AttributeSet attrs) {
        super(context, attrs);

        getSettings().setJavaScriptEnabled(true);
        // 设置 缓存模式
        if (NetworkUtils.isNetworkAvailable(getContext())) {
            getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        getSettings().setBlockNetworkImage(false);
        setBackgroundColor(Color.TRANSPARENT);

        getSettings().setDomStorageEnabled(true); // 开启 DOM storage API 功能
        getSettings().setDatabaseEnabled(true);   //开启 database storage API 功能
        getSettings().setAppCacheEnabled(true);//开启 Application Caches 功能


        String cacheDirPath = Constants.getAppCachePath(Constants.APP_CACHE_WEB_PATH);
        getSettings().setAppCachePath(cacheDirPath);

        if (Build.VERSION.SDK_INT >= 11) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        } else {
            setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
    }

    @Override
    public void loadUrl(String url) {
        super.loadUrl(url);
    }

    @SuppressLint("AddJavascriptInterface")
    public AskMathWebView showWebImage(MultiStateView multiStateView) {
        showWebImage(getContext(), multiStateView);
        return this;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return false;
        return super.onTouchEvent(event);
    }


    @SuppressLint("AddJavascriptInterface")
    public AskMathWebView showWebImage(Context context, final MultiStateView multiStateView) {
        this.setWebViewClient(new WebAppClient(context, multiStateView, this));
        this.addJavascriptInterface(new WebAppInterface(context), Constants.PLATFORM);

        return this;
    }

    /**
     * 设置加载过程
     */
    public AskMathWebView setWebChromeClient() {
        setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress >= 100) {
//                    multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
//                    view.setVisibility(View.VISIBLE);
//                    if(bitmap==null) {//第一次初始化
//                        view.setDrawingCacheEnabled(true);
//                        bitmap = view.getDrawingCache();
//                        view.setDrawingCacheEnabled(false);
//                    }

                } else if (newProgress == 0) {
//                    if(bitmap!=null){//初始化的时候，bitmap不为null
////                        view.loadUrl("javascript:onSaveCallback('data:image/png;base64," + CommonUtil.bitmaptoString(bitmap)  + "')");
//                    }
                } else {
//                    multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
//                    view.setVisibility(View.INVISIBLE);
                }
            }
        });
        return this;
    }

    /**
     * 点击图片
     *
     * @return
     */
    @SuppressLint("AddJavascriptInterface")
    public AskMathWebView showWebImage() {
        return showWebImage(getContext(), null);
    }

    @SuppressLint("AddJavascriptInterface")
    public AskMathWebView showWebImage(Context context) {
        return showWebImage(context, null);
    }

    public AskMathWebView setWebText(String text) {
        setText(text);
        return this;
    }

    public void setText(String text) {
        if (!TextUtils.isEmpty(text)) {
            mText = text;
            Chunk chunk = getChunk();

            String TAG_FORMULA = "formula";
            chunk.set(TAG_FORMULA, mText);
            super.getSettings().setDefaultTextEncodingName("UTF-8");
            try {
                this.loadDataWithBaseURL(null, chunk.toString(), "text/html", "utf-8", "about:blank");
            } catch (Exception e) {

            }

        }
    }

    /**
     * 格式化数学公式
     *
     * @return
     */
    public AskMathWebView formatMath() {
        getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        setVerticalScrollBarEnabled(false);
        setVerticalScrollbarOverlay(false);
        setHorizontalScrollBarEnabled(false);
        setHorizontalScrollbarOverlay(false);
        return this;
    }

    private Chunk getChunk() {
        String TEMPLATE_MATHJAX = "mathjax";
        AndroidTemplates loader = new AndroidTemplates(getContext(), "mathjax");

        return new Theme(loader).makeChunk(TEMPLATE_MATHJAX);
    }

    public String getText() {
        return mText;
    }




}