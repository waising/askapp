package com.asking91.app.ui.widget.camera.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CameraFreelyLineView extends View {
    private Paint mPaint;
    private int lineX;
    private int lineX1;
    private int lineY;
    private int lineY1;

    public CameraFreelyLineView(Context context) {
        super(context);
        inint();
    }

    public CameraFreelyLineView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        inint();
    }

    public CameraFreelyLineView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        inint();
    }

    private void inint() {
        mPaint = new Paint();
        mPaint.setColor(-1);
        mPaint.setAntiAlias(true);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        lineX = width / 3;
        lineX1 = (width * 2) / 3;
        lineY = height / 3;
        lineY1 = (height * 2) / 3;
        canvas.drawLine((float) lineX, 0.0f, (float) lineX, (float) height, mPaint);
        canvas.drawLine((float) lineX1, 0.0f, (float) lineX1, (float) height, mPaint);
        canvas.drawLine(0.0f, (float) lineY, (float) width, (float) lineY, mPaint);
        canvas.drawLine(0.0f, (float) lineY1, (float) width, (float) lineY1, mPaint);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }
}