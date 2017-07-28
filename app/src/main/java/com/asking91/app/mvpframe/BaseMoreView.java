package com.asking91.app.mvpframe;

/**
 * Created by wxwang on 2016/11/11.
 */
public interface BaseMoreView<T> extends BaseView {
    void onRefreshData(T t);
    void onLoadMoreData(T t);
}
