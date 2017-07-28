package com.asking91.app.ui.set;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.api.Networks;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.global.Constants;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.aq.AqActivity;
import com.asking91.app.ui.login.LoginActivity;
import com.asking91.app.ui.main.AboutAppActivity;
import com.asking91.app.ui.main.MainActivity;
import com.asking91.app.ui.update.CheckUpdateManager;
import com.asking91.app.ui.update.UpdateEvent;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.util.AskFileUtil;
import com.asking91.app.util.FileUtils;
import com.asking91.app.util.SystemUtil;
import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AfterPermissionGranted;

/**
 * Created by wxiao on 2016/12/7.
 * 设置
 */

public class SetActivity extends BaseFrameActivity<SetPresenter, SetModel> implements SetPresenter.View {
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
    @BindView(R.id.checkUpdate)
    RelativeLayout checkUpdate;
    @BindView(R.id.cacheSize)
    TextView cacheSize;
    @BindView(R.id.clearCache)
    RelativeLayout clearCache;
    @BindView(R.id.reply)
    Button reply;
    @BindView(R.id.version_tv)
    TextView versionTv;
    private boolean pLoginFlag;

    private File fileCache;
    private CheckUpdateManager checkUpdateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {
        super.initData();
        checkUpdateManager = new CheckUpdateManager(mthis, true);
        pLoginFlag = getUserConstant().isTokenLogin();//保存进来的登录状态
        fileCache = new File(Environment.getExternalStorageDirectory(), Constants.APP_CACHE_CACHE_PATH);
        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(toolBar, "设置");
        versionTv.setText("当前版本：v"+SystemUtil.getVersionName(mthis));
        cacheSize.setText(AskFileUtil.getAutoFileOrFilesSize(fileCache.getPath()));
    }

    @Override
    public void initLoad() {
        super.initLoad();
        setButton();
    }

    @OnClick({R.id.accout_aq,R.id.reply,R.id.about_ask, R.id.checkUpdate, R.id.clearCache})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.accout_aq:
                //账号安全
                openActivity(AqActivity.class);
                break;
            case R.id.reply:
                if(getUserConstant().isTokenLogin()) {
//                    pLoginFlag = true;
                    mPresenter.logout();
                    logout();
                    getUserConstant().logout();
                    setButton();
                }else{
                    openActivity(LoginActivity.class);
                }
                break;
            case R.id.about_ask:
                openActivity(AboutAppActivity.class);
                break;
            case R.id.checkUpdate://更新
                requestAlertWindow();
                break;
            case R.id.clearCache://清楚缓存
                NormalDialogStyleTwo();
                break;
        }
    }

    private final int SYSTEM_ALERT_WINDOW = 0x06;//存储权限
    @AfterPermissionGranted(SYSTEM_ALERT_WINDOW)
    public void requestAlertWindow() {//自动更新弹窗
        checkUpdateManager.checkUpdate();
    }

    public void onEventMainThread(UpdateEvent event) {//强制更新
        switch(event.state){
            case 0://开始更新
                checkUpdateManager.showProgressDialog();
                break;
            case 1://更新中
                checkUpdateManager.setProgressDialog(event.titProgress,event.progress);
                break;
            case 2://下载完成
                checkUpdateManager.dismissProgressDialog();
                break;
        }
    }

    public void onEventMainThread(AppEventType event) {//强制更新
        if(event.type == AppEventType.LOGIN_OUT){
            //退出登录
            logout();
            setButton();
        }
    }

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    private NormalDialog dialog;
    private void NormalDialogStyleTwo() {
        if(dialog==null) {
            dialog = new NormalDialog(mthis);
            dialog.content("确定要删除缓存数据吗？")//
                    .style(NormalDialog.STYLE_TWO)//
                    .titleTextSize(23)//
                    .showAnim(mBasIn)//
                    .dismissAnim(mBasOut);//

            dialog.setOnBtnClickL(
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            dialog.dismiss();
                        }
                    },
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            FileUtils.deleteDir(fileCache);//删除文件夹
                            fileCache.mkdirs();//创建文件夹
                            cacheSize.setText(AskFileUtil.getAutoFileOrFilesSize(fileCache.getPath()));
                            dialog.dismiss();
                        }
                    });
        }
        dialog.show();
    }

    @Override
    public void onSuccess(String method, String responseBody) {
        if (method.equals("logout")) {
            getUserConstant().logout();
            //退出网易云
            NIMClient.getService(AuthService.class).logout();
        }
    }

    @Override
    public void onSuccess(String method, ResponseBody baseRsqEntity) {

    }

    private void setButton(){
        if(getUserConstant().isTokenLogin()){
            reply.setBackgroundResource(R.drawable.btn_set_logout_bg);
            reply.setText("退出当前账号");
        }else{

            reply.setBackgroundResource(R.drawable.common_btn_bg);
            reply.setText("登  录");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    public void onRequestStart() {
    }
    @Override
    public void onRequestEnd() {
    }
    @Override
    public void onRequestSuccess(int requestType, ResponseBody responseBody) {
    }

    @Override
    public void onRequestError(String method, RequestEntity requestEntity) {
        if(method.equals("logout")){
            Networks.setToken("");
            getUserConstant().logout();
        }
    }

    private void logout(){
        showShortToast("已退出");

        // 用户注销埋点
        mManService.getMANAnalytics().updateUserAccount("", "");

        //退出网易云
        NIMClient.getService(AuthService.class).logout();
    }

    @Override
    public void onBackPressed() {
        if(pLoginFlag&&!getUserConstant().isTokenLogin()) {
            openActivity(MainActivity.class);
        }
        super.onBackPressed();
    }

    @Override
    public void finish() {
        if(pLoginFlag&&!getUserConstant().isTokenLogin()) {
            openActivity(MainActivity.class);
        }
        super.finish();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
