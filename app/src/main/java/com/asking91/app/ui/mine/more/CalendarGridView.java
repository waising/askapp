package com.asking91.app.ui.mine.more;

/**
 * Created by jswang on 2017/3/21.
 */

import android.content.Context;

import android.util.AttributeSet;
import android.widget.GridView;

public class CalendarGridView extends GridView {
    public CalendarGridView(Context context) {
        super(context);
    }

    public CalendarGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CalendarGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(536870911, Integer.MIN_VALUE));
    }
}