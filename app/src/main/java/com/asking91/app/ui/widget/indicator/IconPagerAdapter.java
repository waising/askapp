package com.asking91.app.ui.widget.indicator;

/**
 * Created by jswang on 2017/3/13.
 */

public interface IconPagerAdapter {
    /**
     * Get icon representing the page at {@code index} in the adapter.
     */
    int getIconResId(int index);

    // From PagerAdapter
    int getCount();
}
