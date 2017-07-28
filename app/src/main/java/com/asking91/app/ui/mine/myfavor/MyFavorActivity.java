package com.asking91.app.ui.mine.myfavor;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.basepacket.CommentEntity;
import com.asking91.app.entity.basepacket.SubjectEntity;
import com.asking91.app.global.Constants;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.adapter.mine.MyFavorAdapter;
import com.asking91.app.ui.login.LoginActivity;
import com.asking91.app.ui.mine.mytestrecord.TestRecordContract;
import com.asking91.app.ui.mine.mytestrecord.TestRecordModel;
import com.asking91.app.ui.mine.mytestrecord.TestRecordPresenter;
import com.asking91.app.ui.widget.AskSwipeRefreshLayout;
import com.asking91.app.ui.widget.RecycleViewDivider;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.ResUtil;
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

/**
 * 我的收藏界面
 */
public class MyFavorActivity extends BaseFrameActivity<TestRecordPresenter, TestRecordModel> implements TestRecordContract.View, Toolbar.OnMenuItemClickListener {
    /**
     * 指示器
     */
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.swipe_layout)
    AskSwipeRefreshLayout swipeLayout;
    @BindView(R.id.recyclerView)
    SwipeMenuRecyclerView recyclerView;

    private String token;
    private MyFavorAdapter myFavorAdapter;
    private int start = 0, limit = 6, subjectCatalog = 1;
    private List<String> mDataList = new ArrayList<>();//顶部tab
    private List<CommentEntity> commentEntity = new ArrayList<>();//知识点或教育咨询
    private List<SubjectEntity> subjectDetailEntity = new ArrayList<>();//题目

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfavor);
        ButterKnife.bind(this);
        //取消全屏滑动
        getSwipeBackLayout().setSwipeMode(SwipeBackLayout.ORIGINAL);
    }

    @Override
    public void initData() {
        super.initData();
        token = getUserConstant().getToken();
        if (token == null) {//未登录
            CommonUtil.openActivity(this, LoginActivity.class, null);
        }
        mDataList.add(getString(R.string.knowledge)); // 知识点
        //暂时隐藏题目
        //mDataList.add(getString(R.string.title)); // 题目
        mDataList.add(getString(R.string.refer)); // 教育资讯

    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolbar, getString(R.string.my_collect));
        initmagicIndicator();
        initRecycleView();
    }

    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        recyclerView.setSwipeMenuCreator(swipeMenuCreator); // 为SwipeRecyclerView的Item创建菜单
        recyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.HORIZONTAL, ResUtil.dp2px(this,8), ContextCompat.getColor(this, R.color.colorTest_f8)));//设置分割线
        recyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        recyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
    }

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。侧滑删除
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width =  ResUtil.dp2px(MyFavorActivity.this,80);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            // 添加右侧的
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
     * 菜单点击监听。删除监听
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
            if (menuPosition == 0) {// 删除按钮被点击。
                String id = myFavorAdapter.getCommentEntityItemId(adapterPosition);
                if (!TextUtils.isEmpty(id)) {
                    mPresenter.presenterDelMyFavor(222, token, id);
                }
                myFavorAdapter.removeCommentEntityItem(adapterPosition);
            }
        }
    };


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_buy:
                //openCameraActivity(BuyActivity.class);
                break;
        }
        return true;
    }

    @Override
    public void initListener() {
        super.initListener();
        swipeLayout.setViewPager(recyclerView);
        swipeLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout ptrFrameLayout) {//上拉加载更多
                start += limit;
                if (subjectCatalog == Constants.Collect.title) {//如果是题目接口
                    mPresenter.presenterMyFavor(333, token, Constants.Collect.title, start, limit); //请求题目 在 onRefreshData 方法里接收服务器的返回值
                } else {//知识点或教育资讯
                    getDataNow();
                }
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {//下拉刷新
                swipeLayout.setMode(PtrFrameLayout.Mode.BOTH);
                start = 0;
                if (subjectCatalog == Constants.Collect.title) {//如果是题目接口
                    commentEntity.clear();
                    subjectDetailEntity.clear();
                    mPresenter.presenterMyFavor(333, token, Constants.Collect.title, start, limit); //请求题目
                } else {//知识点或教育资讯
                    commentEntity.clear();
                    getDataNow();
                }
            }
        });
    }

    /**
     * 请求数据
     */
    public void getDataNow() {
        mPresenter.presenterMyFavor(111, token, subjectCatalog, start, limit);
    }

    @Override
    public void initLoad() {
        super.initLoad();
        getDataNow();
       // mPresenter.presenterMyFavor(333, token, Constants.Collect.title, start, limit); //请求题目
    }

    /**
     * 初始化指示器
     */
    private void initmagicIndicator() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getWindow().getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
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
                    public void onClick(View v) {
                        // 收藏题目的接口有问题，下面的代码是临时方案，后面得改掉
                        switch (index) {
                            case 0:
                                subjectCatalog = Constants.Collect.knowledge; // 1 是知识点
                                initRefsh(index);
                                getDataNow();
                                break;
//                            case 1:
//                                subjectCatalog = Constants.Collect.title; // 2 是题目
//                                initRefsh(index);
//                                // 由于题目收藏这个接口后台还在改造，目前先屏蔽掉
//                                myFavorAdapter = null;
//                                recyclerView.setAdapter(null);
//                                // 后面有需要测试旧接口的话可以把上面两行代码注释掉，然后把下面一行请求代码给取消注释
//                                mPresenter.presenterMyFavor(333, token, Constants.Collect.title, start, limit); //请求题目
//                                break;
                            case 1:
                                subjectCatalog = Constants.Collect.refer; // 4 是教育资讯
                                initRefsh(index);
                                getDataNow();
                                break;
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
        magicIndicator.setNavigator(commonNavigator);
    }

    /**
     * 刷新顶部导航栏
     *
     * @param index
     */
    private void initRefsh(int index) {
        magicIndicator.onPageSelected(index);
        magicIndicator.onPageScrollStateChanged(index);
        magicIndicator.onPageScrolled(index, 0, 0);
        swipeLayout.refreshComplete(); //关闭刷新 这很重要
        swipeLayout.setMode(PtrFrameLayout.Mode.BOTH);
        start = 0; // 这边要恢复初始值0
        commentEntity.clear();
        subjectDetailEntity.clear();
    }

    @Override
    public void onRequestError(RequestEntity requestEntity) {
        super.onRequestError(requestEntity);
        // mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    @Override
    public void onRequestStart() {
    }

    @Override
    public void onRequestEnd() {
    }

    public void refshAdapt(List<CommentEntity> entityComment, List<SubjectEntity> subjectDetailEntity) {
        if (myFavorAdapter == null) {//首次
            myFavorAdapter = new MyFavorAdapter(this, subjectCatalog, entityComment, subjectDetailEntity);
            recyclerView.setAdapter(myFavorAdapter);
        } else {
            if (start > 0) {
                // 加载更多
                myFavorAdapter.setData(subjectCatalog, commentEntity, subjectDetailEntity);
            } else {
                // 顶部tab切换
                myFavorAdapter.setData(subjectCatalog, entityComment, subjectDetailEntity);
            }
        }
    }

    /**
     * requestType不是333请求成功后返回的数据
     *
     * @param requestType
     * @param responseBody
     */
    @Override
    public void onRequestSuccess(int requestType, ResponseBody responseBody) {
        swipeLayout.refreshComplete();
        Map<String, Object> map = CommonUtil.parseDataToMap(responseBody);
        switch (requestType) {
            case 111:
                // 知识点 && 教育资讯
                if (map != null && map.get("list") != null) {
                    List<CommentEntity> entity = CommonUtil.parseDataToList(map.get("list"), new TypeToken<List<CommentEntity>>() {
                    });
                    if (entity != null && entity.size() > 0) {
                        commentEntity.addAll(entity);
                    } else {
                        if (start > 0) { // 后台返回的 total 值是 double 类型，不能用 int 整型去接，暂时先用这个方法判断
                            swipeLayout.setMode(PtrFrameLayout.Mode.REFRESH); // 刷到底部没有数据了的话给个提示，并且不让再加载更多。
                            showShortToast(R.string.no_more_data);
/*                            if (map.get("total") != null) {
                                Integer total = Integer.valueOf((String) map.get("total"));
                                if (total <= start) {
                                    showShortToast("没有更多数据了");
                                }*/
                        }
                    }
                    refshAdapt(entity, subjectDetailEntity);
                }
                break;
            case 222:
                // 删除
                if (map != null && map.get("flag") != null && map.get("flag").equals("0")) {
                    //删除成功
                    return;
                }
                break;
            case 444:
                // 题目详情
                SubjectEntity subjectEntity = CommonUtil.data2Clazz(map, SubjectEntity.class);
                subjectDetailEntity.add(subjectEntity);
                refshAdapt(commentEntity, subjectDetailEntity);
                break;
        }
    }

    @Override
    public void onRequestSuccess(ResponseBody responseBody) {

    }

    /**
     * requestType是333请求成功返回的数据,即题目相关的信息
     *
     * @param responseBody
     */
    @Override
    public void onRefreshData(ResponseBody responseBody) {
        // 向服务器请求到的题目
        swipeLayout.refreshComplete();
        Map<String, Object> map = CommonUtil.parseDataToMap(responseBody);
        // subjectCatalog == Constants.Collect.title
        if (map != null && map.get("list") != null) {
            List<CommentEntity> entity = CommonUtil.parseDataToList(map.get("list"), new TypeToken<List<CommentEntity>>() {
            });
            if (entity != null && entity.size() > 0) {
                commentEntity.addAll(entity);
                for (int i = 0; i < entity.size(); i++) {
                    String objId = entity.get(i).getObjId(); // 题目ID
                    String km = entity.get(i).getKm(); //题目类型
                    if (!TextUtils.isEmpty(km)) {
                        if (km.startsWith("M")) {
                            km = "M";
                        }
                        if (km.startsWith("P")) {
                            km = "P";
                        }
                    }
                    //请求题目详情（服务器只返回给我们题目的id和类型，题目标题和解析得再次请求）
                    mPresenter.presenterGetMyFavSubjectDetail(444, km, objId);
                }
            } else {
                if (start > 0) {
                    swipeLayout.setMode(PtrFrameLayout.Mode.REFRESH);
                    showShortToast(R.string.no_more_data);
                } else {
                    refshAdapt(commentEntity, subjectDetailEntity);
                }
            }
        }
    }

    @Override
    public void onLoadMoreData(ResponseBody responseBody) {

    }

    @Override
    public void onInternetError(String methodName) {
        if (swipeLayout != null) {
            swipeLayout.refreshComplete();
        }
        swipeLayout.setMode(PtrFrameLayout.Mode.REFRESH);
        myFavorAdapter = null;
        recyclerView.setAdapter(null);
    }

}
