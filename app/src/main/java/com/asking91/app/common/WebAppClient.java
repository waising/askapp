
package com.asking91.app.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.polok.localify.LocalifyClient;
import com.github.polok.localify.module.LocalifyModule;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.global.Constants;
import com.orhanobut.logger.Logger;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Jun on 2016/5/8.
 */
public class WebAppClient extends WebViewClient {

    protected final static String PLATFORM = "Android";

    private Context context;

    private MultiStateView multiStateView;

    private WebView contentView;

    private WebSettings settings;

    public WebAppClient(Context context,
                        MultiStateView multiStateView,
                        WebView contentView) {
        this.context = context;
        this.multiStateView = multiStateView;
        this.contentView = contentView;

        initSetting();
    }

    public WebAppClient(Context context,
                        WebView contentView) {
        this.context = context;
        this.contentView = contentView;

        initSetting();
    }



    private void initSetting() {
        settings = contentView.getSettings();

        settings.setJavaScriptEnabled(true);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Uri uri = Uri.parse(url);
        if (uri.getHost().contains(Constants.ASK_HOST)) {
            ArrayMap<String, String> segments = new ArrayMap<>();
            String key = null;
            for (String segment : uri.getPathSegments()) {
                if (key == null) {
                    key = segment;
                } else {
                    segments.put(key, segment);
                    key = null;
                }
            }
            if (segments.size() > 0) {

            }
        }

        if (url.contains(Constants.DEEP_LINK_PREFIX)) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {
                context.startActivity(Intent.createChooser(intent, "请选择浏览器"));
            }
        } else {
//                Intent intentToLaunch = WebViewPageActivity.getCallingIntent(mContext, webUrl);
//                mContext.startActivity(intentToLaunch);
//                navigator.navigateToWebView(mContext, url);
        }
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (multiStateView != null) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (multiStateView != null) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
        contentView.setLayerType(View.LAYER_TYPE_NONE, null);
        addImageClickEvent();
    }

    private void addImageClickEvent() {
        LocalifyModule localify = new LocalifyClient.Builder()
                .withAssetManager(context.getAssets())
                .build()
                .localify();

        localify.rx()
                .loadAssetsFile("js/ImageClickEvent.js")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String javascript) {
                        return !TextUtils.isEmpty(javascript);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String javascript) {
                        contentView.loadUrl(javascript.replace("{platform}", PLATFORM));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.toString());
                    }
                });
    }
}
