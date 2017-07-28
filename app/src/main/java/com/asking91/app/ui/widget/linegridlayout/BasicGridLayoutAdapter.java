package com.asking91.app.ui.widget.linegridlayout;

/**
 * Created by jswang on 2017/3/6.
 */

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class BasicGridLayoutAdapter<T> extends BaseAdapter {
    public Context context;
    public LayoutInflater layoutInflater;
    public List<T> resource;

    public abstract View getView(int i);

    public BasicGridLayoutAdapter(Context context) {
        this(context, null);
    }

    public BasicGridLayoutAdapter(Context context, List<T> list) {
        this.context = context;
        this.resource = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public long getItemId(int i) {
        return 0;
    }

    public T getItem(int i) {
        if (i < getCount()) {
            return this.resource.get(i);
        }
        return null;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        return getView(i);
    }

    protected void setData(List<T> list) {
        this.resource = list;
    }

    public int getCount() {
        if (CollectionUtils.isEmpty(this.resource)) {
            return 0;
        }
        return this.resource.size();
    }
}