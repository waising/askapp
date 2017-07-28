package com.asking91.app.ui.widget.camera.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WenbaThreadPool {
    private static volatile Executor a;
    private static volatile Handler b;
    private static Handler c = new Handler(Looper.getMainLooper());

    public static final Executor getExecutor() {
        if (a != null) {
            return a;
        }
        synchronized (WenbaThreadPool.class) {
            if (a == null) {
                a = Executors.newCachedThreadPool();
            }
        }
        return a;
    }

    public static final Handler getSerialHandler() {
        if (b != null) {
            return b;
        }
        synchronized (WenbaThreadPool.class) {
            if (b == null) {
                HandlerThread handlerThread = new HandlerThread("serial-looper");
                handlerThread.start();
                b = new Handler(handlerThread.getLooper());
            }
        }
        return b;
    }

    public static void poolExecute(Runnable runnable) {
        getExecutor().execute(runnable);
    }

    public static void runOnUiThread(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        }
        c.post(runnable);
    }

    public static void serialExecute(Runnable runnable) {
        getSerialHandler().post(runnable);
    }
}