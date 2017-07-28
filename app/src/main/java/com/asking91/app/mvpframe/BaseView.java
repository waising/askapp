package com.asking91.app.mvpframe;

import com.asking91.app.entity.RequestEntity;

/**
 *
 */
public interface BaseView {
    void onRequestStart();
    void onRequestError(RequestEntity requestEntity);
    void onRequestEnd();
}