package com.asking91.app.ui.widget.camera.utils;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build.VERSION;

import java.util.concurrent.Executor;

public class MultiThreadAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    public static final Executor getExecutor() {
        return WenbaThreadPool.getExecutor();
    }

    public static void poolExecute(Runnable runnable) {
        getExecutor().execute(runnable);
    }

    protected Result doInBackground(Params... paramsArr) {
        return null;
    }

    @SuppressLint({"NewApi"})
    public AsyncTask<Params, Progress, Result> executeMultiThread(Params... paramsArr) {
        return VERSION.SDK_INT >= 11 ? super.executeOnExecutor(getExecutor(), paramsArr) : execute(paramsArr);
    }
}