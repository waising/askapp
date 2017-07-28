package com.asking91.app.ui.onlineqa;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.common.SwipeBackActivity;
import com.asking91.app.global.OnlineQAConstant;
import com.asking91.app.ui.widget.dialog.OnlineQAListBaseDialog;
import com.asking91.app.util.JLog;
import com.asking91.app.util.NetworkUtils;

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
import butterknife.OnClick;

/**
 * Created by wxiao on 2016/10/31.
 * 共享空间--首页
 */

public class OnlineQAActivity extends SwipeBackActivity {

    private static final List<String> mDataList = Arrays.asList(OnlineQAConstant.onlineQATitles);
    @BindView(R.id.content)
    RelativeLayout content;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.magic_indicator4)
    MagicIndicator magicIndicator4;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.bottom_menu)
    Button bottom_menu;

    private List<String> items = Arrays.asList(OnlineQAConstant.classVersion);
    private List<String> itemsValues = Arrays.asList(OnlineQAConstant.classVersionValues);
    private ArrayList<OnlineQAFragment1> fragments = new ArrayList<>();
    private int currentPage = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlineqa);
        ((RelativeLayout)findViewById(R.id.content)).addView(LayoutInflater.from(this).inflate(R.layout.layout_toolbar_onlineqa, null, false));
        ButterKnife.bind(this);
        setFrameId(R.id.container);
    }

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.user_name_tv)
    TextView userNameTv;
    @BindView(R.id.title_more)
    ImageView titleMore;
    @BindView(R.id.title_click)
    LinearLayout titleClick;
    @BindView(R.id.title_more_menu)
    ImageView titleMoreMenu;
    @Override
    public void initView() {
        super.initView();
        userNameTv.setText("全部科目");
        titleMore.setVisibility(View.VISIBLE);
        initMagicIndicator4();
    }

    @Override
    public void initData() {
        super.initData();
        fragments.clear();
        OnlineQAFragment1 onlineQAFragment0 = new OnlineQAFragment1();
        Bundle bundle = new Bundle();
        bundle.putInt("page", 0);
        onlineQAFragment0.setArguments(bundle);
        fragments.add(onlineQAFragment0);
        OnlineQAFragment1 onlineQAFragment1 = new OnlineQAFragment1();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("page", 1);
        onlineQAFragment1.setArguments(bundle1);
        fragments.add(onlineQAFragment1);
        OnlineQAFragment1 onlineQAFragment2 = new OnlineQAFragment1();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("page", 2);
        onlineQAFragment2.setArguments(bundle2);
        fragments.add(onlineQAFragment2);

    }

    @Override
    public void initLoad() {
        super.initLoad();
        currentPage = 0;
        setCurrFragment(fragments.get(currentPage));
        toFragment(fragments.get(currentPage));
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
    private OnlineQAListBaseDialog onlineQAListBaseDialog;
    @OnClick({R.id.title_click, R.id.back, R.id.bottom_menu, R.id.title_more_menu})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.title_more_menu:
                openActivity(OnlineQAMyQaActivity.class);
                break;
            case R.id.title_click:
                if(onlineQAListBaseDialog==null) {
                    m = "M";
                    num = "7";
                    mStr ="数学";
                    numStr = "七年级";
                    onlineQAListBaseDialog = new OnlineQAListBaseDialog(mthis);
                    onlineQAListBaseDialog.setOnClickListener(new OnlineQAListBaseDialog.OnBtnClickListener() {

                        @Override
                        public void onClick1(String str) {
                            m = "M";
                            mStr = str;
                        }

                        @Override
                        public void onClick2(String str) {
                            m = "P";
                            mStr = str;
                        }

                        @Override
                        public void onClick11(String str) {
                            num = "7";
                            numStr = str;
                        }

                        @Override
                        public void onClick12(String str) {
                            num = "8";
                            numStr = str;
                        }

                        @Override
                        public void onClick13(String str) {
                            num = "9";
                            numStr = str;
                        }

                        @Override
                        public void onClick21(String str) {
                            num = "10";
                            numStr = str;
                        }

                        @Override
                        public void onClick22(String str) {
                            num = "11";
                            numStr = str;
                        }

                        @Override
                        public void onClick23(String str) {
                            num = "12";
                            numStr = str;
                        }

                        @Override
                        public void onClickOk() {
                            if(m!=null&&num!=null) {
                                initFragmentDatas();
                                onlineQAListBaseDialog.dismiss();
                            }
                        }
                    });
                }
                onlineQAListBaseDialog.show();
                break;
            case R.id.bottom_menu://我要提问
                if(NetworkUtils.isNetworkAvailable(mthis)) {
                    openActivity(OnlineQAAskActivity.class);
                }else{
                    showShortToast(getString(R.string.network_error));
                }
                break;
        }
    }
    private String m, num, mStr, numStr;
    private void initFragmentDatas(){
        userNameTv.setText(mStr+numStr);
        for(OnlineQAFragment1 f:fragments){
            f.onRefreshListData(m, num);
        }
    }

    private int width = 0;
    private void initMagicIndicator4() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) container.getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        width /= 3;
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
                        }
//                        float pageOffset =



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
