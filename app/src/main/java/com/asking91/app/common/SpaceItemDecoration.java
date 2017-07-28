package com.asking91.app.common;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by wxwang on 2016/11/8.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpaceItemDecoration isLeft(boolean isLeft) {
        this.isLeft = isLeft;
        return this;
    }

    private boolean isLeft;
    public SpaceItemDecoration isFirstTop(boolean firstTop) {
        this.firstTop = firstTop;
        return this;
    }

    private boolean firstTop;
    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if(parent.getChildItemId(view) != 0 || firstTop)
            outRect.top = space;

        if(isLeft)
            outRect.left = space;
    }
}
