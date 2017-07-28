package com.asking91.app.ui.mine.more;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.asking91.app.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jswang on 2017/3/22.
 */

public class CalendarView extends RelativeLayout {
    ViewFlipper mViewFlipper;

    public static final int COLUMN_NUMBER = 7;
    private static final int SIGNED = 1;
    private View ll_week;
    private Calendar mCalendar;
    private GestureDetector mGestureDetector;
    private Calendar mSelectedCalendar;

    private int todayYear;
    private int todayMonth;
    public Integer todayDay;

    private boolean isShowPrNeDay = false;

    public OnCalendarViewListener mListener;

    public interface OnCalendarViewListener {
        void OnCalendarView(String date);
    }

    public void setOnCalendarViewListener(OnCalendarViewListener mListener) {
        this.mListener = mListener;
    }

    public void setShowPrNeDay(boolean isShowPrNeDay) {
        this.isShowPrNeDay = isShowPrNeDay;
    }

    public void setShowWeekHead(int isShowWeekHead) {
        ll_week.setVisibility(isShowWeekHead);
    }

    public CalendarView(Context context) {
        super(context);
        init(context);
    }

    public CalendarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public CalendarView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_calendar_view, this);
        ll_week = findViewById(R.id.ll_week);
        mViewFlipper = (ViewFlipper) findViewById(R.id.vf);
        mGestureDetector = new GestureDetector(getContext(),
                new GestureDetector.SimpleOnGestureListener() {
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                        if (e1.getX() > e2.getX()) {
                            slideToNextMonth(0);
                        } else if (e1.getX() < e2.getX()) {
                            slidePreviousMonth(0);
                        }
                        return true;
                    }
                });

        mCalendar = Calendar.getInstance();
        todayYear = mCalendar.get(Calendar.YEAR);
        todayMonth = mCalendar.get(Calendar.MONTH) + SIGNED;
        todayDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        mSelectedCalendar = Calendar.getInstance();
        mSelectedCalendar.setTime(mCalendar.getTime());
        mViewFlipper.addView(getGridView(mSelectedCalendar));

        if (mListener != null) {
            mListener.OnCalendarView("今天：" + todayYear + "年" + todayMonth + "月" + todayDay + "日");
        }
    }

    public String getToDayDate() {
        return "今天：" + todayYear + "年" + todayMonth + "月" + todayDay + "日";
    }

    DateGridViewAdapter mAdapter;

    private GridView getGridView(Calendar calendar) {
        mAdapter = new DateGridViewAdapter(calendar, getContext());
        GridView gridView = (GridView) View.inflate(getContext(), R.layout.calendar_gridview, null);
        gridView.setNumColumns(COLUMN_NUMBER);
        gridView.setAdapter(mAdapter);
        gridView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });
        return gridView;
    }

    public void slideToNextMonth(int gvFlag) {
        gvFlag += SIGNED;
        mSelectedCalendar.add(Calendar.MONTH, SIGNED);
        mViewFlipper.addView(getGridView(mSelectedCalendar), gvFlag);
        mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_left_in));
        mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_left_out));
        mViewFlipper.showNext();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH) + SIGNED;
        int selectedYear = mSelectedCalendar.get(Calendar.YEAR);
        int selectedMonth = mSelectedCalendar.get(Calendar.MONTH) + SIGNED;
        if (selectedYear == year && selectedMonth == month) {
            setTodayDate(year, month, mCalendar.get(Calendar.DAY_OF_WEEK));
        } else {
            if (mListener != null) {
                mListener.OnCalendarView(new StringBuilder(String
                        .valueOf(selectedYear)).append("年").append(selectedMonth)
                        .append("月").toString());
            }
        }
        mViewFlipper.removeViewAt(0);
    }

    public void slidePreviousMonth(int gvFlag) {
        gvFlag += SIGNED;
        mSelectedCalendar.add(Calendar.MONTH, -1);
        mViewFlipper.addView(getGridView(mSelectedCalendar), gvFlag);
        mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_right_in));
        mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_right_out));
        mViewFlipper.showPrevious();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH) + SIGNED;
        int selectedYear = mSelectedCalendar.get(Calendar.YEAR);
        int selectedMonth = mSelectedCalendar.get(Calendar.MONTH) + SIGNED;
        if (selectedYear == year && selectedMonth == month) {
            setTodayDate(year, month, mCalendar.get(Calendar.DAY_OF_WEEK));
        } else {
            if (mListener != null) {
                mListener.OnCalendarView(new StringBuilder(String.valueOf(selectedYear)).append("年").append(selectedMonth)
                        .append("月").toString());
            }
        }
        mViewFlipper.removeViewAt(0);
    }

    private void setTodayDate(int year, int month, int dayOfWeek) {
        if (mListener != null) {
            mListener.OnCalendarView("今天：" + year + "年" + month + "月");
        }
    }

    public void reCalenView(List<Integer> list) {
        try{
            int selectedYear = mSelectedCalendar.get(Calendar.YEAR);
            if(selectedYear == todayYear){
                siginDates.clear();
                siginDates.addAll(list);
                if (mAdapter != null) {
                    List<CalendarInfo> mCaList = mAdapter.mCalendarInfoList;
                    for (int j = 0; j < mCaList.size(); j++) {
                        CalendarInfo e = mCaList.get(j);
                        e.signed = false;
                        if(list.contains(e.date2)){
                            e.signed = true;
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        }catch(Exception e){}
    }

    private List<Integer> siginDates = new ArrayList<>();

    public class DateGridViewAdapter extends BaseAdapter {
        private List<CalendarInfo> mCalendarInfoList;
        private Context mContext;

        private class ViewHolder {
            private TextView textView;
            private View iv_sigin;

            private ViewHolder() {
            }
        }

        public DateGridViewAdapter(Calendar calendar, Context context) {
            this.mContext = context;
            initData(calendar);
        }

        public void initData(Calendar calendar) {
            int selectedYear = calendar.get(Calendar.YEAR);
            int selectedMonth = calendar.get(Calendar.MONTH) + SIGNED;

            Calendar preCalendar = (Calendar) calendar.clone();
            preCalendar.add(Calendar.MONTH, -1);
            int pre_maximum_date = preCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            List<CalendarInfo> calendarInfoList = null;
            int actual_minimum_date;
            int day_of_week_start;
            int actual_maximum_date;
            int day_of_week_end;
            int i;
            CalendarInfo calendarInfo;

            if (calendarInfoList == null) {
                calendarInfoList = new ArrayList();
                actual_minimum_date = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
                calendar.set(Calendar.DAY_OF_MONTH, actual_minimum_date);
                day_of_week_start = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                actual_maximum_date = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                calendar.set(Calendar.DAY_OF_MONTH, actual_maximum_date);
                day_of_week_end = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                if (day_of_week_start == 0) {
                    day_of_week_start = 7;
                }
                if (day_of_week_end == 0) {
                    day_of_week_end = 7;
                }
                int pre_date = pre_maximum_date - day_of_week_start + 2;
                for (i = 1; i < day_of_week_start; i++) {
                    calendarInfo = new CalendarInfo();
                    if (isShowPrNeDay) {
                        calendarInfo.date = String.valueOf(pre_date);
                    } else {
                        calendarInfo.date = "";
                    }
                    calendarInfo.txtColor = "#ff999999";
                    calendarInfoList.add(calendarInfo);
                    pre_date++;
                }
                for (int j = actual_minimum_date; j <= actual_maximum_date; j++) {
                    calendarInfo = new CalendarInfo();
                    String date2 = String.valueOf(j);
                    calendarInfo.date = date2;
                    calendarInfo.date2 = j;
                    calendarInfo.signed = false;
                    if (selectedYear == todayYear && selectedMonth == todayMonth && j == todayDay) {
                        calendarInfo.isToDay = true;
                        calendarInfo.date = "今天";
                        calendarInfo.txtColor = "#ffffffff";
                    }
                    if (selectedYear == todayYear && selectedMonth == todayMonth && siginDates.contains(j)) {
                        calendarInfo.signed = true;
                    }
                    calendarInfoList.add(calendarInfo);
                }
                int nextD = 1;
                for (int k = day_of_week_end; k < 7; k++) {
                    calendarInfo = new CalendarInfo();
                    if (isShowPrNeDay) {
                        calendarInfo.date = String.valueOf(nextD);
                    } else {
                        calendarInfo.date = "";
                    }
                    calendarInfo.txtColor = "#ff999999";
                    calendarInfoList.add(calendarInfo);
                    nextD++;
                }
                this.mCalendarInfoList = calendarInfoList;
            }
        }

        public int getCount() {
            return this.mCalendarInfoList == null ? 0 : this.mCalendarInfoList.size();
        }

        public Object getItem(int position) {
            if (this.mCalendarInfoList == null || position >= this.mCalendarInfoList.size()) {
                return null;
            }
            return this.mCalendarInfoList.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                view = View.inflate(this.mContext, R.layout.item_calendar, null);
                viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) view.findViewById(R.id.tv);
                viewHolder.iv_sigin = view.findViewById(R.id.iv_sigin);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.textView.setSelected(false);
            CalendarInfo calendarInfo = (CalendarInfo) getItem(position);
            if (calendarInfo != null) {
                viewHolder.textView.setText(calendarInfo.date);
                if (calendarInfo.signed) {
                    viewHolder.textView.setSelected(true);
                }
            }
            viewHolder.textView.setSelected(calendarInfo.isToDay);
            viewHolder.textView.setTextColor(Color.parseColor(calendarInfo.txtColor));
            viewHolder.iv_sigin.setVisibility(View.GONE);
            if (calendarInfo.signed) {
                viewHolder.iv_sigin.setVisibility(View.VISIBLE);
                if (calendarInfo.isToDay) {
                    viewHolder.textView.setSelected(false);
                    viewHolder.textView.setTextColor(Color.parseColor("#ff333333"));
                }
            }
            return view;
        }

        public boolean isEnabled(int position) {
            return false;
        }
    }
}
