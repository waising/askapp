package com.asking91.app.ui.oto;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

import com.asking91.app.R;
import com.asking91.app.ui.widget.slidebar.GBSlideBarAdapter;


/**
 * 项目名称：GBSlideBar
 * 类描述：
 * 创建人：Edanel
 * 创建时间：16/1/14 下午5:45
 * 修改人：Edanel
 * 修改时间：16/1/14 下午5:45
 * 修改备注：
 */
public class SlideAdapter implements GBSlideBarAdapter {


    protected StateListDrawable[] mItems;
    protected String[] content = new String[]{"0","1","2", "3", "4", "5"};
    protected int[] textColor;
    private Resources resources;

    public SlideAdapter(Resources resources) {
        this.resources = resources;
        int size = content.length;
        mItems = new StateListDrawable[size];
        Drawable drawable;
        for (int i = 0; i < size; i++) {
            drawable = resources.getDrawable(R.mipmap.oto_slidebarimg);
            if (drawable instanceof StateListDrawable) {
                mItems[i] = (StateListDrawable) drawable;
            } else {
                mItems[i] = new StateListDrawable();
                mItems[i].addState(new int[] {}, drawable);
            }
        }
    }

    @Override
    public int getCount() {
        return mItems.length;
    }

    @Override
    public String getText(int position) {
        return content[position];
    }

    @Override
    public StateListDrawable getItem(int position) {
        return mItems[position];
    }

    @Override
    public int getTextColor(int position) {
        return resources.getColor(R.color.colorText_c7);
    }

    public void setTextColor(int[] color){
        textColor = color;
    }
}
