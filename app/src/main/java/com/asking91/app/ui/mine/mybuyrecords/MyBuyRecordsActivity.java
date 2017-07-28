package com.asking91.app.ui.mine.mybuyrecords;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.user.BuyRecordsEntity;
import com.asking91.app.global.Constants;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.adapter.mine.MyBuyRecordsAdapter;
import com.asking91.app.ui.login.LoginActivity;
import com.asking91.app.ui.mine.mytestrecord.TestRecordContract;
import com.asking91.app.ui.mine.mytestrecord.TestRecordModel;
import com.asking91.app.ui.mine.mytestrecord.TestRecordPresenter;
import com.asking91.app.ui.pay.PayActivity;
import com.asking91.app.ui.widget.AskSwipeRefreshLayout;
import com.asking91.app.util.CommonUtil;
import com.google.gson.reflect.TypeToken;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import okhttp3.ResponseBody;

//我的购买记录

public class MyBuyRecordsActivity extends BaseFrameActivity<TestRecordPresenter, TestRecordModel> implements TestRecordContract.View {

    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;//指示器
    @BindView(R.id.toolBar)
    Toolbar mToolbar;//toolbar
    @BindView(R.id.swipe_layout)
    AskSwipeRefreshLayout swipeLayout;//下拉刷新
    @BindView(R.id.recyclerView)
    SwipeMenuRecyclerView recyclerView;//recycleeview

    String token;
    MyBuyRecordsAdapter myBuyRecordsAdapter;//adapter
    int start = 0, limit = 20, subjectCatalog = 1;
    private List<String> mDataList = new ArrayList<>();
    private List<BuyRecordsEntity> listBase = new ArrayList<>(); //　基础知识包(我的服务)
    private List<BuyRecordsEntity> listMyLessons = new ArrayList<>(); //超级辅导课（我的课程）
    private List<BuyRecordsEntity> listAsk = new ArrayList<>(); // 阿思币（我的充值）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybuy_records);
        ButterKnife.bind(this);
        //取消全屏滑动
        getSwipeBackLayout().setSwipeMode(SwipeBackLayout.ORIGINAL);
        //setSwipeBackEnable(false);
    }

    @Override
    public void initData() {
        super.initData();
        token = getUserConstant().getToken();
        if (token == null) {
            CommonUtil.openActivity(this, LoginActivity.class, null);
        }
        mDataList.add(getString(R.string.myLessons)); // 我的课程
        mDataList.add(getString(R.string.myRecharge)); // 我的充值
        mDataList.add(getString(R.string.myService)); // 我的服务
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolbar, getString(R.string.shopping_record));
        initmagicIndicator();
        initRecycleView();
    }

    /**
     * 初始化recyclew
     */
    private void initRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator()); // 设置点击动画 可加可不加
        //设置recyclerView的行间距
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildLayoutPosition(view) != 0) {
                    outRect.top = CommonUtil.dip2px(mthis, 8f);
                }
            }
        });
    }

    @Override
    public void initListener() {
        super.initListener();
        swipeLayout.setViewPager(recyclerView); // 解决上下拉刷新控件 PtrHTFrameLayout 和 viewpager 以及侧滑删除等横向控件冲突
        swipeLayout.setPtrHandler(new PtrDefaultHandler2() {
            public void onLoadMoreBegin(PtrFrameLayout ptrFrameLayout) {//上拉，加载更多
                start += limit;
                getDataNow();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {//下拉刷新
                start = 0;
                listBase.clear();
                listMyLessons.clear();
                listAsk.clear();
                swipeLayout.setMode(PtrFrameLayout.Mode.BOTH);
                getDataNow();
            }
        });
    }

    @Override
    public void initLoad() {
        super.initLoad();
        getDataNow(); // 请求数据得放在 initLoad 这个方法里
    }

    // 指示器
    private void initmagicIndicator() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getWindow().getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;//定义头部tab指示器宽度
        if (mDataList != null && mDataList.size() > 0) {
            width /= mDataList.size();
        } else {
            width /= 3;
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
                    public void onClick(View v) {//点击指示器头部
                        switch (index) {
                            case 0:
                                subjectCatalog = Constants.Collect.knowledge; // 1 是知识点
                                break;
                            case 1:
                                subjectCatalog = Constants.Collect.title; // 2 是题目
                                break;
                            case 2:
                                subjectCatalog = Constants.Collect.refer; // 4 是教育资讯
                                break;
                            default:
                                subjectCatalog = Constants.Collect.knowledge;// 默认是知识点
                                break;
                        }

                        swipeLayout.refreshComplete(); //关闭刷新 这很重要
                        listBase.clear();//清空三个tab页的list
                        listMyLessons.clear();
                        listAsk.clear();
                        start = 0; // 这边要恢复初始值0

                        magicIndicator.onPageSelected(index);
                        magicIndicator.onPageScrollStateChanged(index);
                        magicIndicator.onPageScrolled(index, 0, 0);

                        swipeLayout.setMode(PtrFrameLayout.Mode.BOTH);//清空tab页的数据
                        myBuyRecordsAdapter = null;
                        recyclerView.setAdapter(null); // 清空recycleview 这很重要
                        getDataNow();
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {//指示器颜色
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(ContextCompat.getColor(mthis, R.color.colorAccent));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
    }

    /**
     * 刷新数据
     */
    public void refsh() {
        switch (subjectCatalog) {
            case Constants.Collect.knowledge:
                // 超级辅导课（我的课程）
                myBuyRecordsAdapter.setData(listMyLessons, subjectCatalog);
                break;
            case Constants.Collect.title:
                // 阿思币充值（我的充值）
                myBuyRecordsAdapter.setData(listAsk, subjectCatalog);
                break;
            case Constants.Collect.refer:
                // 基础知识包（我的服务）
                myBuyRecordsAdapter.setData(listBase, subjectCatalog);
                break;
        }
        myBuyRecordsAdapter.notifyDataSetChanged();
    }

    /**
     * 我的购买记录的请求
     */
    private void getDataNow() {
        mPresenter.presenterMyBuyRecords(token, start, limit);
    }

    /**
     * 请求成功回来
     * @param responseBody
     */
    @Override
    public void onRequestSuccess(ResponseBody responseBody) {
        swipeLayout.refreshComplete();
        // 这里的 map 解析过后要加个try catch 捕捉异常
        Map<String, Object> map = CommonUtil.parseDataToMap(responseBody);
        if (map != null) {
            List<BuyRecordsEntity> entity = CommonUtil.parseDataToList(map.get("list"), new TypeToken<List<BuyRecordsEntity>>() {
            });
            if (entity != null && entity.size() > 0) {
                for (int i = 0; i < entity.size(); i++) {
                    // 将后台返回给我们的数据进行筛选分类
                    BuyRecordsEntity buyRecordsEntity = entity.get(i);
                    // 对于多个产品组合而成的订单（购物车），目前暂时取第一个产品作为分类的依据
                    if (buyRecordsEntity.getProducts() != null && buyRecordsEntity.getProducts().size() > 0) {
                        String commodityType = buyRecordsEntity.getProducts().get(0).getCommodityType();
                        BuyRecordsEntity.ProductsBean.RechargeBean recharge = buyRecordsEntity.getProducts().get(0).getRecharge();
                        if (recharge != null) {
                            // recharge 不为 null 的话是阿思币充值（我的充值），为 null 的话是基础知识包和超级辅导课购买
                            listAsk.add(buyRecordsEntity);
                        } else {
                            if (commodityType != null && commodityType.equals(String.valueOf(Constants.Collect.knowledge))) {
                                // commodityType 的值为 1 的话是基础知识包，(我的服务)
                                listBase.add(buyRecordsEntity);
                            } else {
                                // commodityType 的值为 2 的话是超级辅导课（我的课程） ,为 null 是知识点购买
                                listMyLessons.add(buyRecordsEntity);
                            }

                        }
                    }
                }
                if (myBuyRecordsAdapter == null) {
                    myBuyRecordsAdapter = new MyBuyRecordsAdapter();
                    recyclerView.setAdapter(myBuyRecordsAdapter);
                    recyclerView.setSwipeMenuCreator(swipeMenuCreator); // 为SwipeRecyclerView的Item创建菜单
                    recyclerView.setSwipeMenuItemClickListener(menuItemClickListener); // 设置菜单Item点击监听。
                }
                refsh(); // 刷新数据
            } else if (start > 0) {
                //刷到底部没有数据了的话给个提示，并且不让再加载更多。
                // 后台返回的 total 值是 double 类型，不能用 int 整型去接，麻烦，暂时先用这个方法判断
/*                if(map.get("total")!=null){
                    Integer total = Integer.valueOf((String) map.get("total"));
                    if(total<=start){
                        showShortToast("没有更多数据了");
                    }*/
                swipeLayout.setMode(PtrFrameLayout.Mode.REFRESH);
                showShortToast(R.string.no_more_data);
            }
        }
    }

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {//侧滑删除菜单
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.space_80);
            int height = RecyclerView.LayoutParams.MATCH_PARENT;
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(mthis)
                        .setBackgroundDrawable(R.color.red86)
                        .setImage(R.mipmap.ic_del)
                        .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
            }
        }
    };

    /**
     * 菜单点击监听。侧滑删除监听
     */
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        /**
         * Item的菜单被点击的时候调用。
         * @param closeable       closeable. 用来关闭菜单。
         * @param adapterPosition adapterPosition. 这个菜单所在的item在Adapter中position。
         * @param menuPosition    menuPosition. 这个菜单的position。比如你为某个Item创建了2个MenuItem，那么这个position可能是是 0、1，
         * @param direction       如果是左侧菜单，值是：SwipeMenuRecyclerView#LEFT_DIRECTION，如果是右侧菜单，值是：SwipeMenuRecyclerView#RIGHT_DIRECTION.
         */
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                // Toast.makeText(mthis, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
            // TODO 如果是删除：推荐调用Adapter.notifyItemRemoved(position)，不推荐Adapter.notifyDataSetChanged();
            if (menuPosition == 0) {// 删除按钮被点击。
                switch (subjectCatalog) {
                    case Constants.Collect.knowledge:
                        // 超级辅导课（我的课程）
                        String id = listMyLessons.get(adapterPosition).getId();
                        if (!TextUtils.isEmpty(id)) {
                            mPresenter.presenterDelMyBuyRecords(id);
                        }
                        listMyLessons.remove(adapterPosition);
                        break;
                    case Constants.Collect.title:
                        // 阿思币充值（我的充值）
                        String idAsk = listAsk.get(adapterPosition).getId();
                        if (!TextUtils.isEmpty(idAsk)) {
                            mPresenter.presenterDelMyBuyRecords(idAsk);
                        }
                        listAsk.remove(adapterPosition);
                        break;
                    case Constants.Collect.refer:
                        // 基础知识包（我的服务）
                        String idBasic = listBase.get(adapterPosition).getId();
                        if (!TextUtils.isEmpty(idBasic)) {
                            mPresenter.presenterDelMyBuyRecords(idBasic);
                        }
                        listBase.remove(adapterPosition);
                        break;
                }
                myBuyRecordsAdapter.notifyItemRemoved(adapterPosition);

            }
        }
    };

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.menu_all_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
/*        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.menu_open_rv_menu) {
            recyclerView.smoothOpenRightMenu(0);
        }*/
        return true;
    }


    @Override
    public void onRefreshData(ResponseBody responseBody) {
    }

    @Override
    public void onLoadMoreData(ResponseBody responseBody) {

    }

    @Override
    public void onInternetError(String methodName) {

    }

    @Override
    public void onRequestSuccess(int type, ResponseBody responseBody) {
    }

    @Override
    public void onRequestStart() {
    }

    /**
     * 请求失败
     * @param requestEntity
     */
    @Override
    public void onRequestError(RequestEntity requestEntity) {
        super.onRequestError(requestEntity);
        if (swipeLayout != null) {
            swipeLayout.refreshComplete();
        }
        start = 0;
        swipeLayout.setMode(PtrFrameLayout.Mode.REFRESH);
        //showShortToast(R.string.no_more_data);
    }

    @Override
    public void onRequestEnd() {
    }

    public void openPayActivity(Bundle bundle) {
        openResultActivity(PayActivity.class, bundle);
    }

    /**
     * 重新回到界面
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            start = 0;
            listBase.clear();
            listMyLessons.clear();
            listAsk.clear();
            // swipeLayout.setMode(PtrFrameLayout.Mode.BOTH);
            getDataNow();
        }

    }
}
