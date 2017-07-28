
package com.asking91.app.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.asking91.app.util.ResUtil;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * 刷新控件
 * Created by Jun on 2016/3/9.
 * <p>
 * PtrFrameLayout: 使用自定义头布局或者尾布局
 * PtrClassicFrameLayout：使用默认样式
 */
public class AskSwipeRefreshLayout extends PtrFrameLayout {

    private int[] colors = new int[]{Color.parseColor("#38c1ff"), Color.parseColor("#38c1ff"),
            Color.parseColor("#38c1ff"), Color.parseColor("#38c1ff")};

    public AskSwipeRefreshLayout(Context context) {
        super(context);

        this.initConfig();
        this.initHeader();
        this.initFooter();
    }

    public AskSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.initConfig();
        this.initHeader();
        this.initFooter();
    }

    private void initFooter() {

        MaterialHeader footer = new MaterialHeader(getContext());
        footer.setColorSchemeColors(colors);
        footer.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        footer.setPadding(0, 20, 0, 25);
        footer.setPtrFrameLayout(this);

        this.addPtrUIHandler(footer);
        this.setFooterView(footer);
    }

    private void initHeader() {
        MaterialHeader header = new MaterialHeader(getContext());
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        header.setPadding(0, 25, 0, 20);
        header.setPtrFrameLayout(this);

        this.addPtrUIHandler(header);
        this.setHeaderView(header);
    }

    private void initConfig() {
        setOffsetToRefresh(ResUtil.dp2px(getContext(), 100));
        /**
         * 阻尼系数
         * 默认: 1.7f，越大，感觉下拉时越吃力
         */
        setResistance(1.7f);

        /**
         * 触发刷新时移动的位置比例
         * 默认，1.2f，移动达到头部高度1.2倍时可触发刷新操作
         */
        setRatioOfHeaderHeightToRefresh(1.2f);

        /**
         * 回弹延时
         * 默认 200ms，回弹到刷新高度所用时间
         */
        setDurationToClose(200);

        /**
         * 头部回弹时间
         * 默认1000ms
         */
        setDurationToCloseHeader(1000);

        /**
         * 刷新是保持头部
         * 默认值 true
         */
        setKeepHeaderWhenRefresh(true);

        /**
         * 下拉刷新 / 释放刷新
         * 默认为释放刷新 false
         */
        setPullToRefresh(true);

        /**
         * 刷新时，保持内容不动，仅头部下移, 使用 Material Design 风格才好看一点
         * 默认 false
         */
        setPinContent(true);

        /**
         * 刷新模式
         * 默认 TOP：只支持下拉
         * Bottom：只支持上拉
         * BOTH：两种同时支持
         */
        setMode(Mode.BOTH);


    }


    /**
     * PtrHTFrameLayout has a viewpager
     * 解决上下拉刷新控件和viewpager以及侧滑删除等横向控件冲突
     *
     */

    // 记录viewPager是否拖拽的标记
    private boolean mIsHorizontalMove;
    // 记录事件是否已被分发
    private boolean isDeal;
    private int mTouchSlop;
    private View view;
    private float startY;
    private float startX;

    public void setViewPager(View view) {
        this.view = view;
        if (view == null) {
            throw new IllegalArgumentException("viewPager can not be null");
        }
        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (view == null) {
            return super.dispatchTouchEvent(ev);
        }
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 记录手指按下的位置
                startY = ev.getY();
                startX = ev.getX();
                // 初始化标记
                mIsHorizontalMove = false;
                isDeal = false;
                break;
            case MotionEvent.ACTION_MOVE:
                // 如果已经判断出是否由横向还是纵向处理，则跳出
                if (isDeal) {
                    break;
                }
                /**拦截禁止交给Ptr的 dispatchTouchEvent处理**/
                mIsHorizontalMove = true;
                // 获取当前手指位置
                float endY = ev.getY();
                float endX = ev.getX();
                float distanceX = Math.abs(endX - startX);
                float distanceY = Math.abs(endY - startY);
                if (distanceX != distanceY) {
                    // 如果X轴位移大于Y轴位移，那么将事件交给viewPager处理。
                    if (distanceX > mTouchSlop && distanceX > distanceY) {
                        mIsHorizontalMove = true;
                        isDeal = true;
                    } else if (distanceY > mTouchSlop) {
                        mIsHorizontalMove = false;
                        isDeal = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //下拉刷新状态时如果滚动了viewpager 此时mIsHorizontalMove为true 会导致PtrFrameLayout无法恢复原位
                // 初始化标记,
                mIsHorizontalMove = false;
                isDeal = false;
                break;
        }
        if (mIsHorizontalMove) {
            return dispatchTouchEventSupper(ev);
        }
        return super.dispatchTouchEvent(ev);
    }
}
