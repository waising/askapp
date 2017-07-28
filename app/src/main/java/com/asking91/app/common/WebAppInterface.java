package com.asking91.app.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.asking91.app.global.Constants;
import com.asking91.app.ui.main.PhotoShowActivity;

/**
 * Created by wxwang on 2016/11/25.
 */
public class WebAppInterface {
    private Context context;


    public WebAppInterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void openImage(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.Key.WEB_IMAGE_URL, url);
        Intent intent = new Intent(context, PhotoShowActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(0,0);
    }
}