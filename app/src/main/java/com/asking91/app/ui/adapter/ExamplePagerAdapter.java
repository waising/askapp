package com.asking91.app.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.ui.widget.AskSimpleDraweeView;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.DeviceUtil;

import java.util.List;

/**
 * Created by hackware on 2016/9/10.
 */

public class ExamplePagerAdapter extends PagerAdapter {
    private List<String> mDataList;
    private int width = 0;

    private Context mContext;
    public ExamplePagerAdapter(Context context,List<String> dataList) {
        this.mDataList = dataList;
        this.mContext = context;
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
    public Object instantiateItem(ViewGroup container, int position) {
        AskSimpleDraweeView draweeView = (AskSimpleDraweeView) LayoutInflater.from(container.getContext()).inflate(R.layout.layout_simpledraweeview, null, false);
        draweeView.setMaxHeight(CommonUtil.dip2px(container.getContext(), 200));
        if(width==0){
            DisplayMetrics displayMetrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager)container.getContext().getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            width = displayMetrics.widthPixels;
        }
        draweeView.setBackground(null);
        draweeView.setMaxWidth(width);

        draweeView.setImageBitmap(CommonUtil.decodeBitmapWithOrientationMax(mDataList.get(position),
                DeviceUtil.getScreenWidth(mContext), DeviceUtil.getScreenHeight(mContext)));

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
