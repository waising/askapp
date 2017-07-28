package com.asking91.app.ui.widget.camera.comm;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.LabelEntity;
import com.asking91.app.ui.widget.NoScrollGridView;

import java.util.List;

/**
 * Created by jswang on 2017/3/1.
 */

public class CourseViewHelper{
    public static  void getView(Context mContext, LinearLayout pView,String title, BaseAdapter adapter) {
        View mRootView= LayoutInflater.from(mContext).inflate(R.layout.layout_cours_view, null);
        ((TextView) mRootView.findViewById(R.id.title)).setText(title);
        NoScrollGridView gridView = (NoScrollGridView) mRootView.findViewById(R.id.course_view);
        gridView.setAdapter(adapter);
        gridView.setNumColumns(3);
        pView.removeAllViews();
        pView.addView(mRootView);
    }

    public static  void getView2(Activity mContext, LinearLayout pView,String title, BaseAdapter adapter) {
        View mRootView= LayoutInflater.from(mContext).inflate(R.layout.layout_cours_view2, null);
        ((TextView) mRootView.findViewById(R.id.title)).setText(title);
        NoScrollGridView gridView = (NoScrollGridView) mRootView.findViewById(R.id.course_view);
        gridView.setNumColumns(3);
        gridView.setAdapter(adapter);
        pView.removeAllViews();
        pView.addView(mRootView);
    }

    public static  CourseViewAdapter getCourseViewAdapter(Activity mContext,List<LabelEntity> data,OnItemLabelEntityListener mListenerr) {
      return new CourseViewAdapter(mContext,data,mListenerr);
    }


}

