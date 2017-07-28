package com.asking91.app.ui.widget;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.util.JLog;


/**
 * Created by firetomato on 2016/7/26.
 * 超级辅导课 底部菜单栏
 */

public class SuperFreeBottomMenuControl {
    private static SuperFreeBottomMenuControl mthis = null;
    private LinearLayout bottom_linearlayout;
    private int widthNor, widthSelect, widthMore=2;
    private static int positionNow;
    private Context context;


    private int[] maps = new int[]{R.mipmap.menu_company, R.mipmap.menu_download,
    R.mipmap.menu_message, R.mipmap.menu_personalcenter};
    private int[] mapsSelect = new int[]{R.mipmap.menu_company_select, R.mipmap.menu_download_select,
            R.mipmap.menu_message_select, R.mipmap.menu_personalcenter_select};
    private String[] array ;
    /**
     * 底部菜单按钮个数*/
    private int menuNum = 4;

    public static SuperFreeBottomMenuControl getInstance(Context context, LinearLayout bottom_linearlayout){
        if(mthis==null){
            mthis = new SuperFreeBottomMenuControl(context,bottom_linearlayout);
        }else{
            mthis.RefreshView(context, bottom_linearlayout);
        }
        return mthis;
    }

    public SuperFreeBottomMenuControl(Context context, LinearLayout bottom_linearlayout) {
        array = context.getResources().getStringArray(R.array.super_free_menu);
        this.context = context;
        this.bottom_linearlayout = bottom_linearlayout;
        initBottom(context);
        positionNow = 0;
        setNowBg(positionNow);

    }

//    public SuperFreeBottomMenuControl(Context mContext, LinearLayout bottom_linearlayout, int menuNum) {
//        this.mContext = mContext;
//        this.bottom_linearlayout = bottom_linearlayout;
//        this.menuNum = menuNum;
//        initBottom(mContext);
//        positionNow = 2;
//        setNowBg(positionNow);
//        array = mContext.getResources().getStringArray(R.array.super_free_menu);
//    }

    public void initBottom(Context context){
//        String[] array = mContext.getResources().getStringArray(R.array.super_free_menu);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int wTmp = displayMetrics.widthPixels;

        widthNor  = (int) (wTmp * (180f/1080f));
        widthSelect = (int) (wTmp * ((1080 -(menuNum-1)*180)/1080f));//设置选中的菜单栏是最大宽度的
        JLog.d("SuperFreeBottomMenuControl","wTmp="+wTmp+"==widthNor="+widthNor+"==widthSelect="+widthSelect);
        //
        LayoutInflater inflater = LayoutInflater.from(context);
        View view1 = inflater.inflate(R.layout.layout_supertutorial_bottom_menu,null,false);
        view1.setTag(R.id.menu_tag_key,0);
        view1.findViewById(R.id.bg).setBackgroundResource(R.color.super_bottom_bg1);
        ImageView img = (ImageView) view1.findViewById(R.id.menu_img);
        img.setImageResource(R.mipmap.menu_message);
//        TextView textView = (TextView)view1.findViewById(R.id.textView);
//        textView.setText(array[0]);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthNor, LinearLayout.LayoutParams.WRAP_CONTENT);
        view1.setOnClickListener(clickListener);
        bottom_linearlayout.addView(view1, params);
        //
        View view2 = inflater.inflate(R.layout.layout_supertutorial_bottom_menu,null,false);
        img = (ImageView) view2.findViewById(R.id.menu_img);
        img.setImageResource(R.mipmap.menu_message);
        view2.setTag(R.id.menu_tag_key,1);
        view2.findViewById(R.id.bg).setBackgroundResource(R.color.super_bottom_bg2);
//        textView = (TextView)view2.findViewById(R.id.textView);
//        textView.setText(array[1]);
        view2.setOnClickListener(clickListener);
        bottom_linearlayout.addView(view2, params);
        //
        View view3 = inflater.inflate(R.layout.layout_supertutorial_bottom_menu,null,false);
        img = (ImageView) view3.findViewById(R.id.menu_img);
        img.setImageResource(R.mipmap.menu_message);
        view3.setTag(R.id.menu_tag_key,2);
        view3.findViewById(R.id.bg).setBackgroundResource(R.color.super_bottom_bg3);
//        textView = (TextView)view3.findViewById(R.id.textView);
//        textView.setText(array[2]);
        view3.setOnClickListener(clickListener);
        bottom_linearlayout.addView(view3, params);
//        if(menuNum==4){
            View view4 = inflater.inflate(R.layout.layout_supertutorial_bottom_menu,null,false);
            img = (ImageView) view4.findViewById(R.id.menu_img);
            img.setImageResource(R.mipmap.menu_message);
            view4.setTag(R.id.menu_tag_key,3);
            view4.findViewById(R.id.bg).setBackgroundResource(R.color.super_bottom_bg4);
//            textView = (TextView)view4.findViewById(R.id.textView);
//            textView.setText(array[3]);
            view4.setOnClickListener(clickListener);
            bottom_linearlayout.addView(view4, params);
//        }
    }


    /**
     * 底部导航栏选中切换
     */
    public void setBottomSelect(int position){
        clearNowBg();
        setNowBg(position);
    }

    /**
     * 清除当前选中的背景
     */
    private void clearNowBg(){
        View viewTmp = bottom_linearlayout.getChildAt(positionNow);
        TextView tmp2 = (TextView) viewTmp.findViewById(R.id.textView);
        tmp2.setText("");
        LinearLayout.LayoutParams params = null;
        if(positionNow==bottom_linearlayout.getChildCount()){
            params = new LinearLayout.LayoutParams(widthNor+widthMore, LinearLayout.LayoutParams.WRAP_CONTENT);
        }else {
            params = new LinearLayout.LayoutParams(widthNor, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        viewTmp.setLayoutParams(params);
    }

    /**
     * 设置当前选中的背景
     */
    private void setNowBg(int position){
        //改变选中的变化
        View viewNow = bottom_linearlayout.getChildAt(positionNow);
        TextView tmp2 = (TextView) viewNow.findViewById(R.id.textView);
        tmp2.setText("");

        View viewTmp = bottom_linearlayout.getChildAt(position);
        TextView tmp = (TextView) viewTmp.findViewById(R.id.textView);
        tmp.setText(array[position]);
        LinearLayout.LayoutParams params = null;
        if(positionNow==bottom_linearlayout.getChildCount()){
            params = new LinearLayout.LayoutParams(widthSelect+widthMore, LinearLayout.LayoutParams.WRAP_CONTENT);
        }else {
            params = new LinearLayout.LayoutParams(widthSelect, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        viewTmp.setLayoutParams(params);
        positionNow = position;
    }


    public interface ChangePageOnClickListener{
        void onClickPage(int pagePoint);
    }
    private ChangePageOnClickListener changePageOnClickListener = null;

    public void setChangePageOnClickListener(ChangePageOnClickListener changePageOnClickListener){
        this.changePageOnClickListener = changePageOnClickListener;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        int tag = (Integer) view.getTag(R.id.menu_tag_key);
        setBottomSelect(tag);
        if(changePageOnClickListener!=null) {
            changePageOnClickListener.onClickPage(tag);
        }
        }
    };

    /**
     * 刷新数据
     */
    private void RefreshView(Context context, LinearLayout bottom_linearlayout){
        this.bottom_linearlayout = bottom_linearlayout;
        initBottom(context);
        setNowBg(positionNow);
    }

}
