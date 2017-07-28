package com.asking91.app;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.alibaba.sdk.android.man.MANService;
import com.alibaba.sdk.android.man.MANServiceProvider;
import com.asking91.app.entity.TImage;
import com.asking91.app.global.Constants;
import com.asking91.app.global.UserConstant;
import com.asking91.app.ui.oto.OtoLearningActivity;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.DeviceUtil;
import com.asking91.app.util.JLog;
import com.asking91.app.util.SystemUtil;
import com.asking91.app.util.networkfetch.CustomImageDownaloder;
import com.asking91.app.util.networkfetch.OkHttpNetworkFetcher;
import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsOptions;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.fresco.FrescoImageLoader;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.rts.RTSManager;
import com.netease.nimlib.sdk.rts.model.RTSData;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.L;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

import static com.bugtags.library.Bugtags.BTGInvocationEventNone;

/**
 * Created by  on 2016/4/10.
 */
public class Asking91 extends MultiDexApplication {

    public static Asking91 mInstance;

    public static Context applicationContext;


    public List<ArrayList<TImage>> getmDataList() {
        return mDataList;
    }

    public void setmDataList(List<ArrayList<TImage>> mDataList) {
        this.mDataList = mDataList;
    }

    /**
     * 一对一辅导界面的图片地址列表
     */
    private List<ArrayList<TImage>> mDataList = new ArrayList<>();

    public static Asking91 getmInstance() {
        if (mInstance == null) {
            mInstance = new Asking91();
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        mInstance = this;
        /**
         * 设置 Fresco 图片缓存的路径
         */
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(getApplicationContext())
                .setBaseDirectoryPath(getOwnCacheDirectory(this, Constants.APP_CACHE_PATH))
                .build();
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(getApplicationContext())
                .setNetworkFetcher(new OkHttpNetworkFetcher(this))
                .setMainDiskCacheConfig(diskCacheConfig)
                .setSmallImageDiskCacheConfig(diskCacheConfig)
                .build();

        //初始化 Fresco 图片缓存库ImagePipelineConfigUtils.getDefaultImagePipelineConfig(getApplicationContext())
        //Fresco.initialize(this, config);
        BigImageViewer.initialize(FrescoImageLoader.with(this, config));
        //初始化日志输出工具
        JLog.initialize(BuildConfig.LOG_DEBUG);
        /**********网易白板************/
        // SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将完成自动登录）
        NIMClient.init(this, loginInfo(), getOptions());
        // ... your codes
//        if (inMainProcess()) {
//            // 注册白板会话
//            //enableRTS();
//        }

        ImageLoaderConfiguration iconfig = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480, 800)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new WeakMemoryCache())
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .memoryCacheSize(2*1024*1024)
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .imageDownloader(new CustomImageDownaloder(this))
                .build();
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(iconfig);
        L.writeLogs(false);


        //aliyun
        MANService manService = MANServiceProvider.getService();
        manService.getMANAnalytics().init(this, getApplicationContext());

        String chanel = SystemUtil.getChannel(getApplicationContext());
        if(chanel!=null && -1 != chanel.indexOf("_")){
            // 设置渠道（用以标记该app的分发渠道名称），如果不关心可以不设置即不调用该接口，渠道设置将影响控制台【渠道分析】栏目的报表展现。如果文档3.3章节更能满足您渠道配置的需求，就不要调用此方法，按照3.3进行配置即可；
            manService.getMANAnalytics().setChannel(chanel.split("_")[1]);
        }

        //设置版本号
        manService.getMANAnalytics().setAppVersion(SystemUtil.getVersionName(getApplicationContext()));

        //bugtags
//        BugtagsOptions options = new BugtagsOptions.Builder().
//                trackingLocation(true).//是否获取位置，默认 true
//                trackingCrashLog(true).//是否收集crash，默认 true
//                trackingConsoleLog(true).//是否收集console log，默认 true
//                trackingUserSteps(true).//是否收集用户操作步骤，默认 true
//                trackingNetworkURLFilter("(.*)").//自定义网络请求跟踪的 url 规则，默认 null
//                build();
        String key = BuildConfig.DEBUG ? BuildConfig.APP_BUG_BETA_KEY : BuildConfig.APP_BUG_LIVE_KEY;
        //BTGInvocationEventNone    // 静默模式，只收集 Crash 信息（如果允许，默认为允许）

        //BTGInvocationEventShake   // 通过摇一摇呼出 Bugtags

        //        BTGInvocationEventBubble  // 通过悬浮小球呼出 Bugtags
        int evt = BTGInvocationEventNone;

        if(BuildConfig.DEBUG)
            evt = Bugtags.BTGInvocationEventShake;
        Bugtags.start(key, this, evt);

        //jplus推送
        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(this);
    }

    public static File getOwnCacheDirectory(Context context, String cachePath) {
        File appCacheDir = null;

        if ("mounted".equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
            appCacheDir = new File(Environment.getExternalStorageDirectory(), cachePath);
        }

        if (appCacheDir == null || !appCacheDir.exists() && !appCacheDir.mkdirs()) {
            appCacheDir = context.getCacheDir();
        }

        return appCacheDir;
    }

    public static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
        return perm == 0;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 白板实时时会话配置与监听
     */
    private void enableRTS() {
        registerRTSIncomingObserver(true);
    }

    private void registerRTSIncomingObserver(boolean register) {
        RTSManager.getInstance().observeIncomingSession(new Observer<RTSData>() {
            @Override
            public void onEvent(RTSData rtsData) {
                //System.out.println(rtsData.getLocalSessionId());
                Bundle bundle = new Bundle();
                bundle.putSerializable("rtsData",rtsData);
                CommonUtil.openActivity(OtoLearningActivity.class,bundle);
//                RTSActivity.incomingSession(this, rtsData, RTSActivity.FROM_BROADCAST_RECEIVER);
            }
        }, register);
    }

    //    ****************网易白板******************
// 如果返回值为 null，则全部使用默认参数。
    private SDKOptions getOptions() {
        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
//        config.notificationEntrance = WelcomeActivity.class; // 点击通知栏跳转到该Activity
        config.notificationSmallIconId = R.mipmap.ic_logo;
        // 呼吸灯配置
        config.ledARGB = Color.GREEN;
        config.ledOnMs = 1000;
        config.ledOffMs = 1500;
        // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
        options.statusBarNotificationConfig = config;

        // 配置保存图片，文件，log 等数据的目录
        // 如果 options 中没有设置这个值，SDK 会使用下面代码示例中的位置作为 SDK 的数据目录。
        // 该目录目前包含 log, file, image, audio, video, thumb 这6个目录。
        // 如果第三方 APP 需要缓存清理功能， 清理这个目录下面个子目录的内容即可。
        String sdkPath = Environment.getExternalStorageDirectory() + "/" + Constants.APP_BASE_PATH + "/nim";
        options.sdkStorageRootPath = sdkPath;

        // 配置是否需要预下载附件缩略图，默认为 true
        options.preloadAttach = true;

        // 配置附件缩略图的尺寸大小。表示向服务器请求缩略图文件的大小
        // 该值一般应根据屏幕尺寸来确定， 默认值为 Screen.width / 2
        options.thumbnailSize = DeviceUtil.getImageMaxEdge(this);
        return options;
    }

    // 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
    private LoginInfo loginInfo() {
        String token = UserConstant.getInstance(this).getWYToken();
        if (TextUtils.isEmpty(token)) {
            return null;
        }
        String loginAcount = UserConstant.getInstance(this).getUserName().toLowerCase();
        return new LoginInfo(loginAcount, token);
    }

    public boolean inMainProcess() {
        String packageName = getPackageName();
        String processName = SystemUtil.getProcessName(this);
        return packageName.equals(processName);
    }

}
