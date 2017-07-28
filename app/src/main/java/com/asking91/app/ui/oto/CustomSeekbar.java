package com.asking91.app.ui.oto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.asking91.app.R;

import java.util.ArrayList;

/**
 * Created by jswang on 2017/3/27.
 */

public class CustomSeekbar extends View {
    private int width;
    private int height;
    private int downX = 0;
    private int downY = 0;
    private int upX = 0;
    private int upY = 0;
    private int moveX = 0;
    private int moveY = 0;
    private int perWidth = 0;
    private Paint mPaint;
    private Paint mTextPaint;
    private Paint buttonPaint;

    private Bitmap tipImg;
    private Bitmap spot;
    private Bitmap spot_on;

    private int cur_sections = 0;
    private OnCustomSeekbarListener mListener;
    private int bitMapHeight = 38;//第一个点的起始位置起始，图片的长宽是76，所以取一半的距离
    private int textMove = 60;//字与下方点的距离，因为字体字体是40px，再加上10的间隔
    private int[] colors = new int[]{0xFFFFC178, 0xFFF2F1F1};//进度条的橙色,进度条的灰色,字体的灰色
    private int textSize;
    private ArrayList<String> section_title = new ArrayList<>();

    private int viewLeft;
    private int tipImgW;
    private int tipImgH;

    public CustomSeekbar(Context context) {
        super(context);
        initView();
    }

    public CustomSeekbar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    public CustomSeekbar(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        initView();
    }

    /**
     * 实例化后调用，设置bar的段数和文字
     */
    public void initView() {
        cur_sections = 0;

        tipImg = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_cus_seekbar_tip);
        spot = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_ratingbar_money2);
        spot_on = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_ratingbar_money1);

        bitMapHeight = spot.getHeight() / 2;
        textMove = bitMapHeight + 22;
        textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());
        mPaint = new Paint(Paint.DITHER_FLAG);
        mPaint.setAntiAlias(true);//锯齿不显示
        mPaint.setStrokeWidth(10);
        mTextPaint = new Paint(Paint.DITHER_FLAG);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(0xffb5b5b4);
        buttonPaint = new Paint(Paint.DITHER_FLAG);
        buttonPaint.setAntiAlias(true);

        viewLeft = (spot_on.getWidth() + tipImgW / 2);
        tipImgW = tipImg.getWidth();
        tipImgH = tipImg.getHeight();

        for (int i = 0; i < 3; i++) {
            section_title.add(i + "");
        }
    }

    public void setMax(int max) {
        section_title.clear();
        for (int i = 0; i < max; i++) {
            section_title.add(i + "");
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        width = widthSize;
        height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics());
        setMeasuredDimension(width, height);
        width = width - bitMapHeight / 2 - tipImgW;
        perWidth = (width - section_title.size() * spot.getWidth() - spot.getWidth() / 2) / (section_title.size() - 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAlpha(0);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        mPaint.setAlpha(255);
        mPaint.setColor(colors[1]);

        int lineY = height * 2 / 3;
        canvas.drawLine(spot_on.getWidth() + tipImgW / 2, lineY, width - spot_on.getWidth() / 2 + tipImgW / 2, lineY, mPaint);
        for (int section = 0; section < section_title.size(); section++) {
            int bitmapX = spot.getWidth() / 2 + section * perWidth + section * spot_on.getWidth() + tipImgW / 2;
            if (section <= cur_sections) {
                canvas.drawBitmap(spot_on, bitmapX, lineY - spot_on.getHeight() / 2, mPaint);
            } else {
                canvas.drawBitmap(spot, bitmapX, lineY - spot.getHeight() / 2, mPaint);
            }
            if (section < cur_sections) {
                mPaint.setColor(colors[0]);
                int lineStartX = section * perWidth + (section + 1) * spot_on.getWidth() + tipImgW / 2;
                int lineEndX = spot.getWidth() + section * perWidth + (section + 1) * spot_on.getWidth() + perWidth + tipImgW / 2;
                canvas.drawLine(lineStartX, lineY, lineEndX, lineY, mPaint);
            }
            int fontTop = getFontHeight() + spot.getHeight() + bitMapHeight;
            int txtX = 0;
            try {
                txtX = (spot.getWidth() - getTextWidth(section_title.get(section))) / 2;
            } catch (Exception e) {
            }
            mTextPaint.setTextSize(textSize);
            mTextPaint.setColor(0xffb5b5b4);
            canvas.drawText(section_title.get(section), txtX + bitmapX, lineY - textMove + fontTop, mTextPaint);
        }

        if(cur_sections>0){
            int tipX = spot.getWidth() + cur_sections * perWidth + cur_sections * spot_on.getWidth();
            int tipY = lineY - tipImgH - spot.getWidth() / 2;

            canvas.drawBitmap(tipImg, tipX,tipY , mPaint);

            mTextPaint.setTextSize(textSize);
            mTextPaint.setColor(0xffffffff);

            String tipStr = cur_sections+"枚";
            int tipTxtX =tipX+(tipImgW - getTextWidth(tipStr)) / 2;
            int tipTxtY =tipY+tipImgH-getFontHeight()*3/4;
            canvas.drawText(tipStr, tipTxtX, tipTxtY, mTextPaint);
        }
    }

    public int getFontHeight() {
        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.ascent);
    }

    public int getTextWidth(String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            mTextPaint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        cur_sections = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                responseTouch(downX);
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = (int) event.getX();
                moveY = (int) event.getY();
                responseTouch(moveX);
                break;
            case MotionEvent.ACTION_UP:
                upX = (int) event.getX();
                upY = (int) event.getY();
                responseTouch(upX);
                break;
        }
        return true;
    }

    private void responseTouch(int x) {
        try {
            int lineStartX = x - viewLeft;
            int lineEndX = width / section_title.size();
            cur_sections = lineStartX / lineEndX;
            if (cur_sections >= section_title.size()) {
                cur_sections = section_title.size() - 1;
            }
            if (mListener != null) {
                mListener.OnCustomSeekbar(cur_sections);
            }
            invalidate();
        } catch (Exception e) {
        }

    }

    //设置监听
    public void setCustomSeekbarListener(OnCustomSeekbarListener mListener) {
        this.mListener = mListener;
    }

    //设置进度
    public void setProgress(int progress) {
        cur_sections = progress;
        invalidate();
    }

    public interface OnCustomSeekbarListener {
        void OnCustomSeekbar(int volume);
    }
}
