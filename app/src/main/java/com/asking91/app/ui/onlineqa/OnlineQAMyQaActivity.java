package com.asking91.app.ui.onlineqa;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.common.SwipeBackActivity;
import com.asking91.app.global.OnlineQAConstant;
import com.asking91.app.util.JLog;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by wxiao on 2016/11/8.
 * 我的问答
 */

public class OnlineQAMyQaActivity extends SwipeBackActivity {

    @BindView(R.id.user_name_tv)
    TextView userNameTv;
    @BindView(R.id.title_more)
    ImageView titleMore;
    @BindView(R.id.title_click)
    LinearLayout titleClick;
    @BindView(R.id.user_info_rl)
    RelativeLayout userInfoRl;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.magic_indicator4)
    MagicIndicator magicIndicator4;
    @BindView(R.id.container)
    FrameLayout container;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlineqa_myqa);
        ButterKnife.bind(this);
        setFrameId(R.id.container);
    }

    @Override
    public void initData() {
        super.initData();
        fragments = new ArrayList<>();
        OnlineQaMyAskFragment onlineQaMyAskFragment = new OnlineQaMyAskFragment();
//        Bundle bundle = new Bundle();
//        bundle.putBoolean("type", false);
//        onlineQaMyAskFragment.setArguments(bundle);
        fragments.add(onlineQaMyAskFragment);
        OnlineQaMyAnswerFragment onlineQaMyQaFragment2 = new OnlineQaMyAnswerFragment();
//        Bundle bundle2 = new Bundle();
//        bundle2.putBoolean("type", true);
//        onlineQaMyQaFragment2.setArguments(bundle2);
        fragments.add(onlineQaMyQaFragment2);
    }

    @Override
    public void initView() {
        super.initView();
        toolBar.setTitle(getString(R.string.app_onlineqa_myqa_ask));
        setCurrFragment(fragments.get(0));
        toFragment(fragments.get(0));

        initMagicIndicator4();
    }

    @Override
    public void initListener() {
        super.initListener();
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initLoad() {
        super.initLoad();
    }

    private List<String> mDataList = Arrays.asList(OnlineQAConstant.onlineQaMyQa);
    private int width = 0, currentPage = -1;
    private void initMagicIndicator4() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) container.getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        width /= fragments.size();
        magicIndicator4.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setWidth(width);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#c8c8c8"));
                simplePagerTitleView.setSelectedColor(ContextCompat.getColor(mthis, R.color.colorAccent));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        JLog.i(mthis.getClass().getSimpleName(), "page="+index);
                        if (index < fragments.size()) {
                            currentPage = index;

                            toFragment(fragments.get(currentPage));
                            magicIndicator4.onPageSelected(index);
                            magicIndicator4.onPageScrollStateChanged(index);
                            magicIndicator4.onPageScrolled(index,0, 0);

                            toolBar.setTitle(currentPage!=1?getString(R.string.app_onlineqa_myqa_ask):getString(R.string.app_onlineqa_myqa));
                        }
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(ContextCompat.getColor(mthis, R.color.colorAccent));
                return indicator;
            }
        });
        magicIndicator4.setNavigator(commonNavigator);
    }
}
