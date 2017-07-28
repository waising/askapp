package com.asking91.app.ui.widget.camera.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by jswang on 2017/2/28.
 */

public abstract class CBaseAdapter<T> extends BaseAdapter {
    protected Activity activity;// 运行上下文
    protected List<T> listItems;// 数据集合
    protected LayoutInflater listContainer;// 视图容器

    public CBaseAdapter(Activity activity, List<T> data) {
        this.activity = activity;
        this.listContainer = LayoutInflater.from(activity); // 创建视图容器并设置上下文
        this.listItems = data;
    }

    public int getCount() {
        return listItems.size();
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        return null;
    }

}

