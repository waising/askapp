package com.asking91.app.ui.main;

import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.asking91.app.R;
import com.asking91.app.api.Networks;
import com.asking91.app.common.BaseActivity;
import com.asking91.app.global.Constants;
import com.asking91.app.ui.juniorhigh.JuniorToHighFrameFragment;
import com.asking91.app.ui.mine.MineFragment;
import com.asking91.app.ui.oto.OtoFragment;
import com.asking91.app.ui.supertutorial.SuperSelectFragment;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.util.JLog;
import com.asking91.app.util.JPushUtil;
import com.asking91.app.util.SharePreferencesHelper;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

import static com.asking91.app.util.PermissionsChecker.REQUEST_STORAGE_PERMISSION;

/**
 * 主activity
 */
public class MainActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener,
        MaterialDialog.SingleButtonCallback {
    @BindView(R.id.container)
    FrameLayout mContainer;
    @BindView(R.id.main_content)
    RelativeLayout mMainContentLl;

    private BottomNavigationBar mBottomNavigationBar;
    private JuniorToHighFrameFragment juniorToHighFrameFragment;//初升高界面
    //private HomeFragment homeFragment;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    private MaterialDialog.Builder mExitLoginDialog;
    private MaterialDialog materialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setFrameId(R.id.container);
        EventBus.getDefault().register(this);
        setExit(true);

        mBottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC
                );

        mBottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ctg, getString(R.string.home_menu_ctg)))
                .addItem(new BottomNavigationItem(R.mipmap.sync_book, getString(R.string.home_menu_syncbook)))
                .addItem(new BottomNavigationItem(R.mipmap.oto, getString(R.string.home_menu_oto)))
                .addItem(new BottomNavigationItem(R.mipmap.test, getString(R.string.home_menu_test)))
                .addItem(new BottomNavigationItem(R.mipmap.my, getString(R.string.home_menu_me)))
                .setFirstSelectedPosition(0)
                .initialise();

        mBottomNavigationBar.setActiveColor(R.color.theme_blue_theme);

        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                toFragment(fragments.get(position));
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        JLog.logd("MainActivity", "initData");
        // homeFragment = new HomeFragment();

        juniorToHighFrameFragment = new JuniorToHighFrameFragment();
        fragments.clear();
        fragments.add(juniorToHighFrameFragment);
        SuperSelectFragment superSelectFragment0 = new SuperSelectFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("showType", 0);

        superSelectFragment0.setArguments(bundle);
        SuperSelectFragment superSelectFragment1 = new SuperSelectFragment();
        Bundle bundle1 = new Bundle();
        bundle.putInt("showType", 1);
        superSelectFragment1.setArguments(bundle1);

        fragments.add(superSelectFragment1);
        fragments.add(new OtoFragment());
        fragments.add(superSelectFragment0);
        fragments.add(new MineFragment());
        mExitLoginDialog = new MaterialDialog.Builder(this);

        String token = SharePreferencesHelper.getInstance(this).getString(Constants.Key.TOKEN);
        if (!TextUtils.isEmpty(token)) {
            Networks.setToken(token);
        }
        //如果已登录
        if (getUserConstant().isTokenLogin() && getUserConstant().getUserEntity() != null) {
            JPushUtil.jPushAlias(this, getUserConstant().getUserName());
        }
    }

    @Override
    public void initView() {
        initExitLoginDialog();
        initToolbar();

        setCurrFragment(fragments.get(0));
        toFragment(fragments.get(0));
        materialDialog = new MaterialDialog.Builder(this).build();
        materialDialog.setContent("加载中...");
    }

    private void initToolbar() {
        Resources.Theme theme = getTheme();
        TypedValue navIcon = new TypedValue();
        TypedValue overFlowIcon = new TypedValue();

        theme.resolveAttribute(R.attr.navIcon, navIcon, true);
        theme.resolveAttribute(R.attr.overFlowIcon, overFlowIcon, true);
    }

    private void initExitLoginDialog() {
        mExitLoginDialog.content(getString(R.string.dialog_exit_login))
                .positiveColorRes(R.color.colorPrimary)
                .negativeColorRes(R.color.colorPrimary)
                .positiveText(getString(R.string.dialog_exit_positive_text))
                .negativeText(getString(R.string.dialog_exit_negative_text));
    }

    @Override
    public void initListener() {
        mExitLoginDialog.onPositive(this);
//        ((HomeFragment)fragments.get(0)).setShowDialog(new HomeFragment.ShowDialog(){
//            @Override
//            public void show() {
//                materialDialog.show();
//            }
//        });
    }

    /**
     * 退出登录 dialog 按钮点击监听事件
     *
     * @param dialog
     * @param which
     */
    @Override
    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
        switch (which) {
            case POSITIVE:
                if (getUserConstant().logout()) { //退出登录
                    dialog.dismiss();
                }
                break;
            case NEGATIVE:
                dialog.dismiss();
                break;
        }
    }


    /**
     * toolbar menu click callback
     *
     * @param item menu item
     * @return true
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
        }
        return true;
    }

    /**
     * 权限操作回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) { //允许
            } else { // 拒绝
                showShortToast(getString(R.string.toast_permission_fail));
            }
        }
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, 13, mMainContentLl);
    }

    public void onEventMainThread(AppEventType event) {
        switch (event.type) {
            case AppEventType.SYNCHRONOUS_PAY_SUCCESS://
//                setCurrFragment(fragments.get(4));
                toFragment(fragments.get(1));//切换到同步课程
                break;
            case AppEventType.LOGIN_SUCCESS://登录成功
                JPushUtil.jPushAlias(this, getUserConstant().getUserName());
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

}
