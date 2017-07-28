package com.asking91.app.ui.mine.mytestrecord;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.asking91.app.R;
import com.asking91.app.entity.onlinetest.PaperEntity;
import com.asking91.app.global.Constants;
import com.asking91.app.global.OnlineQAConstant;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.adapter.mine.TestRecordAdapter;
import com.asking91.app.ui.widget.AskSwipeRefreshLayout;
import com.asking91.app.util.CommonUtil;
import com.google.gson.reflect.TypeToken;

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
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import okhttp3.ResponseBody;

//在线检测记录

public class TestRecordActivity extends BaseFrameActivity<TestRecordPresenter, TestRecordModel> implements TestRecordContract.View {

    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.swipe_layout)
    AskSwipeRefreshLayout swipeLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    int start = 0, limit = 10;
    String subjectCatalog = Constants.SubjectCatalog.CZSX; // 初中数学 M2
    private List<String> mDataList;
    private List<PaperEntity> list = new ArrayList<>();
    TestRecordAdapter testRecordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_record);
        ButterKnife.bind(this);
        //取消全屏滑动
        getSwipeBackLayout().setSwipeMode(SwipeBackLayout.ORIGINAL);
    }

    @Override
    public void initData() {
        super.initData();
        mDataList = Arrays.asList(OnlineQAConstant.classVersionNoAll);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolbar, getString(R.string.test_record));
        initmagicIndicator();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void initListener() {
        super.initListener();
        swipeLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout ptrFrameLayout) {
                start += limit;
                getDataNow();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                start = 0;
                list.clear();
                swipeLayout.setMode(PtrFrameLayout.Mode.BOTH);
                getDataNow();
            }
        });
    }

    private void getDataNow() {
        mPresenter.presenterTestRecord(111, subjectCatalog, start, limit);
    }

    @Override
    public void initLoad() {
        super.initLoad();
        getDataNow();
    }

    private void initmagicIndicator() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getWindow().getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        if (mDataList != null && mDataList.size() > 0) {
            width /= mDataList.size();
        } else {
            width /= 4;
        }
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        final int finalWidth = width;
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setWidth(finalWidth);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#c8c8c8"));
                simplePagerTitleView.setSelectedColor(ContextCompat.getColor(mthis, R.color.colorAccent));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (index) {
                            case 0:
                                subjectCatalog = Constants.SubjectCatalog.CZSX; // 初中数学 M2
                                break;
                            case 1:
                                subjectCatalog = Constants.SubjectCatalog.CZWL; // 初中物理 P2
                                break;
                            case 2:
                                subjectCatalog = Constants.SubjectCatalog.GZSX; // 高中数学 M3
                                break;
                            case 3:
                                subjectCatalog = Constants.SubjectCatalog.GZWL; // 高中物理 P3
                                break;
                            default:
                                subjectCatalog = Constants.SubjectCatalog.CZSX; // 初中数学 M2
                                break;
                        }
                        initRefsh(index);
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
        magicIndicator.setNavigator(commonNavigator);
    }

    private void initRefsh(int index) {
        magicIndicator.onPageSelected(index);
        magicIndicator.onPageScrollStateChanged(index);
        magicIndicator.onPageScrolled(index, 0, 0);
        swipeLayout.refreshComplete(); //关闭刷新 这很重要
        swipeLayout.setMode(PtrFrameLayout.Mode.BOTH);
        start = 0; // 这边要恢复初始值0
        list.clear();
        getDataNow();
    }

    @Override
    public void onRequestSuccess(int type, ResponseBody responseBody) {
        swipeLayout.refreshComplete();
        switch (type) {
            case 111:
                Map<String, Object> map = CommonUtil.parseDataToMap(responseBody);
                if (map != null) {
                    List<PaperEntity> listPaper = CommonUtil.parseDataToList(map.get("list"), new TypeToken<List<PaperEntity>>() {
                    });
                    if (listPaper != null && listPaper.size() > 0) {
                        list.addAll(listPaper);
                    } else {
                        if (start > 0) {
                            // 后台返回的 total 值是 double 类型，不能用 int 整型去接，麻烦，暂时先用这个方法判断
                            swipeLayout.setMode(PtrFrameLayout.Mode.REFRESH);
                            showShortToast(R.string.no_more_data);
                        }
                    }
                    refshAdapt(listPaper);
                }
                break;
        }
    }

    private void refshAdapt(List<PaperEntity> listPaper) {
        if (testRecordAdapter == null) {
            testRecordAdapter = new TestRecordAdapter(this, listPaper, subjectCatalog);
            recyclerView.setAdapter(testRecordAdapter);
        } else {
            if (start > 0) {
                // 加载更多
                testRecordAdapter.setDate(list, subjectCatalog);
            } else {
                // 顶部tab切换
                testRecordAdapter.setDate(listPaper, subjectCatalog);
            }
        }
    }

    @Override
    public void onRequestSuccess(ResponseBody responseBody) {
    }

    @Override
    public void onRefreshData(ResponseBody responseBody) {
    }

    @Override
    public void onLoadMoreData(ResponseBody responseBody) {

    }

    @Override
    public void onRequestStart() {
    }

    @Override
    public void onRequestEnd() {
    }

    @Override
    public void onInternetError(String methodName) {
        swipeLayout.refreshComplete();
        swipeLayout.setMode(PtrFrameLayout.Mode.REFRESH);
        testRecordAdapter = null;
        recyclerView.setAdapter(null);
        //showShortToast(methodName);
    }

}
