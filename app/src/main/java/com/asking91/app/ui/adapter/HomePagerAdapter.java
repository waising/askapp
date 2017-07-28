package com.asking91.app.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.common.BigImgShowActivity;
import com.asking91.app.ui.widget.AskSimpleDraweeView;
import com.asking91.app.util.CommonUtil;

import java.util.List;

/**
 * 顶部viewPager图片切换
 * Created by hackware on 2016/9/10.
 */

public class HomePagerAdapter extends PagerAdapter {
    private List<String> mDataList;
    private List<String> mBigDataList;
    private int width = 0;

    public HomePagerAdapter(List<String> dataList,List<String> bigDataList) {
        mDataList = dataList;
        mBigDataList = bigDataList;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        AskSimpleDraweeView draweeView = (AskSimpleDraweeView) LayoutInflater.from(container.getContext()).inflate(R.layout.layout_simpledraweeview, null, false);
        draweeView.setMaxHeight(CommonUtil.dip2px(container.getContext(), 200));
        if(width==0){
            DisplayMetrics displayMetrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager)container.getContext().getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            width = displayMetrics.widthPixels;
        }
        draweeView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        draweeView.setMaxWidth(width);
        draweeView.setImageURI(mDataList.get(position));
        draweeView.setTag(position);
        draweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//图片点击事件
                int p = (int) v.getTag();
                if(p==0||p==1) {
                    Intent intent = new Intent(container.getContext(), BigImgShowActivity.class);
                    intent.putExtra("imgPath", mBigDataList.get(p));
                    container.getContext().startActivity(intent);
                }
            }
        });
        container.addView(draweeView);
        return draweeView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        TextView textView = (TextView) object;
        String text = textView.getText().toString();
        int index = mDataList.indexOf(text);
        if (index >= 0) {
            return index;
        }
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDataList.get(position);
    }
}
