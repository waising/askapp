package com.asking91.app.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.sdk.android.man.MANService;
import com.alibaba.sdk.android.man.MANServiceProvider;
import com.asking91.app.R;
import com.asking91.app.api.Networks;
import com.asking91.app.entity.user.UserEntity;
import com.asking91.app.global.UserConstant;
import com.asking91.app.mvpframe.rx.RxBus;
import com.asking91.app.util.JLog;
import com.bugtags.library.Bugtags;
import com.jaeger.library.StatusBarUtil;

import java.util.Map;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by  on 2016/4/29.
 */
public class BaseActivity extends AppCompatActivity implements BaseFuncIml , View.OnClickListener {
    protected BaseActivity mthis;

    protected boolean isTransparent = false;

    protected Fragment mCurrFragment;

    private int mFragmentId;

    private static final String TAG = "BaseActivity";

    private UserConstant mUserConstant;

    private MaterialDialog.Builder mLoadingDialog;

    private long mExitTime;

    private boolean isExit = false;

    private Subscription mSubscription;

    public MANService mManService ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mManService = MANServiceProvider.getService();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initData();
        initView();
        initListener();
        initLoad();
    }

    @Override
    protected void onStart() {
        JLog.logd(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        JLog.logd(TAG, "onResume");
        super.onResume();
        //注：回调 1
        //Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        JLog.logd(TAG, "onPause");
        super.onPause();
        //注：回调 2
        Bugtags.onPause(this);
    }

    @Override
    protected void onStop() {
        JLog.logd(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        JLog.logd(TAG, "onDestroy");
        if(mSubscription!=null && !mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //注：回调 3
        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }

    public void setFrameId(int mFragmentId) {
        this.mFragmentId = mFragmentId;
    }

    public Fragment getCurrFragment() {
        return mCurrFragment;
    }

    public void setCurrFragment(Fragment mCurrFragment) {
        this.mCurrFragment = mCurrFragment;
    }
    protected void toFragment(Fragment toFragment) {
        if (mCurrFragment == null) {
            showShortToast("mCurrFragment is null!");
            return;
        }

        if (toFragment == null) {
            showShortToast("toFragment is null!");
            return;
        }

        if (toFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().hide(mCurrFragment)
                    .show(toFragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().hide(mCurrFragment)
                    .add(mFragmentId, toFragment).show(toFragment)
                    .commit();
        }

        mCurrFragment = toFragment;

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initData() {
        mthis = this;
        mUserConstant = UserConstant.getInstance(this);
    }

    @Override
    public void initView() {
        initLoadingDialog();
    }

    private void initLoadingDialog() {
        mLoadingDialog = new MaterialDialog.Builder(this);
        mLoadingDialog.title("请稍候");
        mLoadingDialog.progress(true, 0);
    }

    @Override
    public void initListener() {
        mSubscription = RxBus.$().register(UserEntity.class, new Action1<UserEntity>() {
            @Override
            public void call(UserEntity userEntity) {
                try {
                    initLoad();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void initLoad() {

    }

    @Override
    public void onClick(View v) {

    }

    public void showShortToast(String msg) {
        if(!TextUtils.isEmpty(msg))
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    /**根据String的Id来弹出提示*/
    protected void showShortToast(int msgId) {
        Toast.makeText(this, getResources().getString(msgId), Toast.LENGTH_SHORT).show();
    }


    public void showLongToast(String msg) {
        if(!TextUtils.isEmpty(msg))
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected void openActivity(Class<? extends BaseActivity> toActivity) {
        openActivity(toActivity, null);
    }

    protected void openActivity(Class<? extends BaseActivity> toActivity, Bundle parameter) {
        Intent intent = new Intent(this, toActivity);
        if (parameter != null) {
            intent.putExtras(parameter);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.enter_left_in,R.anim.enter_left_out);
    }
    protected void openResultActivity(Class<? extends BaseActivity> toActivity) {
        openResultActivity(toActivity, null);
    }

    public void openResultActivity(Class<? extends BaseActivity> toActivity, Bundle parameter) {
        Intent intent = new Intent(this, toActivity);
        if (parameter != null) {
            intent.putExtras(parameter);
        }
        startActivityForResult(intent,0);
        overridePendingTransition(R.anim.enter_left_in,R.anim.enter_left_out);
    }

    protected void openBackResultActivity(Class<? extends BaseActivity> toActivity, Bundle parameter) {
        Intent intent = new Intent(this, toActivity);
        if (parameter != null) {
            intent.putExtras(parameter);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    public void openBackResultActivity(String backActivityName, Bundle parameter) {
        Intent intent = null;
        try {
            intent = new Intent(this, Class.forName(backActivityName));
            if (parameter != null) {
                intent.putExtras(parameter);
            }
            setResult(RESULT_OK, intent);
            finish();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void openActivity(String openActivityName, Bundle parameter) {
        Intent intent = null;
        try {
            intent = new Intent(this, Class.forName(openActivityName));
            if (parameter != null) {
                intent.putExtras(parameter);
            }
            startActivity(intent);
            overridePendingTransition(R.anim.enter_left_in,R.anim.enter_left_out);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public UserConstant getUserConstant() {
        return mUserConstant;
    }

    public MaterialDialog.Builder getLoadingDialog() {
        return mLoadingDialog;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit) {
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    showShortToast("再按一次退出阿思可在线");
                    mExitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setExit(boolean isExit) {
        this.isExit = isExit;
    }

    protected  void setToolbar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.exit_left_in,R.anim.exit_left_out);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setStatusBar();
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.theme_blue_theme),0);
    }


    public Map<String, String> getAuth() {
        ArrayMap<String, String> header = new ArrayMap<>();
        header.put("Authorization", "Bearer " + Networks.getToken());
        return header;
    }

    /**
     * 当你登录成功接收到你传递出去的标志后可以做一些当前页面更新操作
     */
    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
    }



}
