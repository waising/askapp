package com.asking91.app.ui.widget.linegridlayout;

/**
 * Created by jswang on 2017/3/6.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

public class MtGridLayout extends LinearLayout {
    private BaseAdapter adapter;
    private int columnCount;
    private LayoutParams layoutParams;
    private int leftMargin;
    private OnClickListener onItemClickListener;
    private OnLongClickListener onItemLongClickListener;
    private int rightMargin;
    private int rowCount;
    private LayoutParams rowLayoutParams;
    private int rowSpace;

    public MtGridLayout(Context context) {
        super(context);
        this.columnCount = 1;
        this.rowCount = 1;
        this.rowSpace = 1;
        this.leftMargin = 1;
        this.rightMargin = 1;
    }

    public MtGridLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.columnCount = 1;
        this.rowCount = 1;
        this.rowSpace = 1;
        this.leftMargin = 1;
        this.rightMargin = 1;
    }

    public void setOnItemClickListener(OnClickListener onClickListener) {
        this.onItemClickListener = onClickListener;
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        this.onItemLongClickListener = onLongClickListener;
    }

    public void setRowSpace(int i) {
        this.rowSpace = i;
    }

    public void setColumnSpace(int i) {
        this.leftMargin = i;
        this.rightMargin = i;
    }

    public void setLeftMargin(int i) {
        this.leftMargin = i;
    }

    public void setRightMargin(int i) {
        this.rightMargin = i;
    }

    public void setColumnCount(int i) {
        this.columnCount = i;
    }

    public void setRowCount(int i) {
        this.rowCount = i;
    }

    public View getItemView(int i) {
        return ((ViewGroup) getChildAt(i / this.columnCount)).getChildAt(i % this.columnCount);
    }

    public BaseAdapter getAdapter() {
        return this.adapter;
    }

    public void setCellLayoutParams(LayoutParams layoutParams) {
        this.layoutParams = layoutParams;
    }

    public void setRowLayoutParams(LayoutParams layoutParams) {
        this.rowLayoutParams = layoutParams;
    }

    public void setAdapterWithMargin(BasicGridLayoutAdapter basicGridLayoutAdapter, int i, int i2) {
        int i3;
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.adapter = basicGridLayoutAdapter;
        int count = basicGridLayoutAdapter.getCount();
        if (count % this.columnCount == 0) {
            i3 = count / this.columnCount;
        } else {
            i3 = (count / this.columnCount) + 1;
        }
        this.rowCount = i3;
        LinearLayout.LayoutParams layoutParams = new LayoutParams(-1, -2);
        i3 = (int) (((float) this.rowSpace) * displayMetrics.density);
        layoutParams.setMargins(0, i3, 0, i3);
        if (count > 0) {
            removeAllViews();
            for (i3 = 0; i3 < this.rowCount; i3++) {
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setLayoutParams(layoutParams);
                for (int i4 = 0; i4 < this.columnCount; i4++) {
                    View view;
                    int i5 = (this.columnCount * i3) + i4;
                    if (i5 >= count) {
                        view = new View(getContext());
                    } else {
                        view = basicGridLayoutAdapter.getView(i5);
                        view.setTag(Integer.valueOf(i5));
                        if (this.onItemClickListener != null) {
                            view.setOnClickListener(this.onItemClickListener);
                        }
                        if (this.onItemLongClickListener != null) {
                            view.setOnLongClickListener(this.onItemLongClickListener);
                        }
                    }
                    LinearLayout.LayoutParams layoutParams2 = new LayoutParams(i2, i2);
                    if (i4 < this.columnCount - 1) {
                        layoutParams2.setMargins(0, 0, i, 0);
                    } else {
                        layoutParams2.setMargins(0, 0, 0, 0);
                    }
                    linearLayout.addView(view, layoutParams2);
                }
                addView(linearLayout);
            }
        }
    }

    public void setAdapter(BaseAdapter baseAdapter) {
        float density = getDensity(getContext());
        int i;
        this.adapter = baseAdapter;
        int count = baseAdapter.getCount();
        if (count % this.columnCount == 0) {
            i = count / this.columnCount;
        } else {
            i = (count / this.columnCount) + 1;
        }
        this.rowCount = i;
        if (this.rowLayoutParams == null) {
            this.rowLayoutParams = new LayoutParams(-1, -2);
        }
        i = (int) (((float) this.rowSpace) * density);
        this.rowLayoutParams.setMargins(0, i, 0, i);
        if (this.layoutParams == null) {
            this.layoutParams = new LayoutParams(-1, -2, 1.0f);
        }
        this.layoutParams.setMargins((int) (((float) this.leftMargin) * density), 0
                , (int) (((float) this.rightMargin) * density), 0);
        if (count > 0) {
            for (int i2 = 0; i2 < this.rowCount; i2++) {
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setLayoutParams(this.rowLayoutParams);
                for (i = 0; i < this.columnCount; i++) {
                    View view;
                    int i3 = (this.columnCount * i2) + i;
                    if (i3 >= count) {
                        view = new View(getContext());
                    } else {
                        view = baseAdapter.getView(i3, null, null);
                        view.setTag(Integer.valueOf(i3));
                        if (this.onItemClickListener != null) {
                            view.setOnClickListener(this.onItemClickListener);
                        }
                        if (this.onItemLongClickListener != null) {
                            view.setOnLongClickListener(this.onItemLongClickListener);
                        }
                    }
                    linearLayout.addView(view, this.layoutParams);
                }
                addView(linearLayout, this.rowLayoutParams);
            }
        }
    }

    public static float getDensity(Context mContext){
        DisplayMetrics displayMetrics = mContext.getResources()
                .getDisplayMetrics();
        float density = displayMetrics.density;
        return density;
    }

}
