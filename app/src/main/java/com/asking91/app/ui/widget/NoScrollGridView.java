package com.asking91.app.ui.widget;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;
import android.view.View;
import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;

/**
 * Created by jswang on 2017/2/28.
 */

public class NoScrollGridView extends GridView {
    private GestureDetector gestureDetector;

    public class GridViewGesture extends SimpleOnGestureListener {
        private boolean mHandlerClick = false;
        private View mHitView;

        public boolean onDown(MotionEvent e) {
            return true;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (e2.getAction() != 2 || (Math.abs(distanceX) <= 5.0f && Math.abs(distanceY) <= 5.0f)) {
                return true;
            }
            this.mHandlerClick = false;
            if (this.mHitView == null) {
                return false;
            }
            this.mHitView.setPressed(false);
            this.mHitView.setSelected(false);
            return false;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

        public void onLongPress(MotionEvent e) {
        }

        public void onShowPress(MotionEvent e) {
            this.mHandlerClick = true;
            this.mHitView = NoScrollGridView.this.getChildAt(NoScrollGridView.this.pointToPosition((int) e.getX(), (int) e.getY()));
            if (this.mHitView != null) {
                this.mHitView.setPressed(true);
                this.mHitView.setSelected(true);
            }
        }

        public boolean onSingleTapUp(MotionEvent e) {
            if (this.mHitView != null) {
                this.mHitView.setPressed(false);
                this.mHitView.setSelected(false);
            }
            return true;
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (this.mHitView != null) {
                this.mHitView.setPressed(false);
                this.mHitView.setSelected(false);
            }
            if (this.mHandlerClick && this.mHitView == null) {
            }
            return true;
        }
    }

    public NoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setGestureDetector(new GestureDetector(context, new GridViewGesture()));
    }

    public NoScrollGridView(Context context) {
        super(context);
        setGestureDetector(new GestureDetector(context, new GridViewGesture()));
    }

    public NoScrollGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setGestureDetector(GestureDetector gestureDetector) {
        this.gestureDetector = gestureDetector;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
        return this.gestureDetector.onTouchEvent(ev);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(536870911, Integer.MIN_VALUE));
    }
}
