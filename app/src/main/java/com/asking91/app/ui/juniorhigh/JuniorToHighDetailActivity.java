package com.asking91.app.ui.juniorhigh;

import android.Manifest;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.juniorTohigh.CourseDataArrayBean;
import com.asking91.app.entity.juniorTohigh.GoodsCoupon;
import com.asking91.app.global.Constants;
import com.asking91.app.global.HttpCodeConstant;
import com.asking91.app.global.PayConstant;
import com.asking91.app.global.UserConstant;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.pay.PayConfrimActivity;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.ui.widget.camera.utils.BitmapUtil;
import com.asking91.app.ui.widget.dialog.NoWifiWarningDialog;
import com.asking91.app.util.CommonUtil;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import pub.devrel.easypermissions.EasyPermissions;
import tcking.github.com.giraffeplayer.GiraffePlayer;


/**
 * 初升高详情
 */
public class JuniorToHighDetailActivity extends BaseFrameActivity<JuniorToHighPresenter, JuniorToHighModel> implements GiraffePlayer.PreparedListener, NoWifiWarningDialog.ClickListner, GiraffePlayer.PlayListener, EasyPermissions.PermissionCallbacks {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    String title;

    GiraffePlayer player;//播放器
    /**
     * 未购买，已购买
     */
    int purchaseState;
    @BindView(R.id.tv_free_look)
    TextView tvFreeLook;

    @BindView(R.id.ll_no_purchased)
    LinearLayout llNotPurchased;


    @BindView(R.id.bottom_pay)
    LinearLayout llBottomPay;


    @BindView(R.id.pdfView)
    PDFView pdfView;


    @BindView(R.id.view_line)
    View viewLine;

    /**
     * 课程详情
     */
    @BindView(R.id.tv_course_detail)
    AskMathView tvCourseDetail;


    /**
     * 报名人数
     */
    @BindView(R.id.tv_sign_up_num)
    TextView tvSignUpNum;
    /**
     * 购买价格
     */
    @BindView(R.id.tv_pay_money)
    TextView tvPayMoney;
    /**
     * 缓冲条
     */
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;

    @BindView(R.id.fr_player)
    FrameLayout frPlayer;

    private JuniorToHighDownLoadManager juniorToHighDownLoadManager;//初升高下载文件管理类

    private String price;


    private String description;


    private int progress = 0;//播放进度

    private String courseTypeFullName;

    private String progressPercente;

    private boolean isSeekTo = true;

    private String savePath;


    private int listPosition;


    @BindView(R.id.tv_sales_promotion)//促销布局
            TextView tvSalePromotion;
    @BindView(R.id.recycler_coupon_type)
    RecyclerView recyclerViewCouponType;


    /**
     * 继续观看
     */
    private boolean isContinue;

    private boolean isFirst;

    private CommAdapter mCommAdapter;
    @BindView(R.id.multiStateView_webView)
    MultiStateView msView;


    private NoWifiWarningDialog noWifiWarningDialog;


    @BindView(R.id.ll_coupon)
    LinearLayout llCoupon;


    @BindView(R.id.iv_teacher)
    ImageView ivTeacher;


    @BindView(R.id.tv_teacher_name)
    TextView tvTeacherName;


    @BindView(R.id.tv_course_name)
    TextView tvCourseName;
    private String commodityId;

    private String[] perms = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CHANGE_WIFI_STATE};
    private final int FILE = 0x04;
    private ArrayList<CourseDataArrayBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_junior_to_high_detail);
        setSwipeBackEnable(false);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        player = new GiraffePlayer(this);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            mList = (ArrayList<CourseDataArrayBean>) bundle.getSerializable("urlAndPdf");
            purchaseState = bundle.getInt("purchaseState");
            if (purchaseState == Constants.JuniorToHighDetail.present)//赠品
            {
                setToolbar(mToolbar, "详情");
                frPlayer.setVisibility(View.GONE);
                llNotPurchased.setVisibility(View.GONE);
                pdfView.setVisibility(View.VISIBLE);
                mMultiStateView.setVisibility(View.VISIBLE);
                llBottomPay.setVisibility(View.GONE);
                viewLine.setVisibility(View.GONE);
                showPdf(mList);
            } else {//非赠品
                frPlayer.setVisibility(View.VISIBLE);
                title = bundle.getString("title");
                setToolbar(mToolbar, "课程详情");
                String mp4Url = screenPdfAndUrl("ZL01", mList);
                player.setUrl(mp4Url + "?avvod/m3u8");//MP4路径
                player.setSmallUrl(mp4Url + "?vframe/jpg/offset/10/");//缩略图路径
                ImageLoader.getInstance().displayImage(mp4Url + "?vframe/jpg/offset/10/", player.$.imageView(R.id.iv_small));
                player.setPlayListener(this);
                commodityId = bundle.getString("commodityId");
                listPosition = bundle.getInt("listPositon");
                if (purchaseState == Constants.JuniorToHighDetail.not_puchased)//未购买
                {
                    BitmapUtil.displayUserImage(this, bundle.getString("ivTeacher"), ivTeacher);
                    tvTeacherName.setText(bundle.getString("tvTeacher") + "老师");
                    tvCourseName.setText(title);
                    requestCoupon(commodityId);

                    price = bundle.getString("price");
                    courseTypeFullName = bundle.getString("courseTypeFullName");
                    String courseDetail = bundle.getString("detail");
                    final String freeTime = bundle.getString("freeTime");//免费试看时间
                    int purchasedNum = bundle.getInt("purchasedNum");
                    description = bundle.getString("description");//简介
                    tvFreeLook.setVisibility(View.VISIBLE);
                    llNotPurchased.setVisibility(View.VISIBLE);
                    pdfView.setVisibility(View.GONE);
                    mMultiStateView.setVisibility(View.GONE);
                    llBottomPay.setVisibility(View.VISIBLE);
                    viewLine.setVisibility(View.VISIBLE);

                    tvCourseDetail.showWebImage(this, msView).formatMath();
                    tvCourseDetail.setText(courseDetail);

                    tvSignUpNum.setText(getString(R.string.purchased_num, purchasedNum + ""));
                    tvPayMoney.setText(getString(R.string.money_format, PayConstant.formatPrice(price)));
                    tvFreeLook.setText(getString(R.string.free_time, generateTime(freeTime)));
                    player.setProgressListner(new GiraffePlayer.ProgressListener() {//播放进度监听


                        @Override
                        public void progress(long currentTime, long totalTime) {
                            hasOrNotNetWork();
                            if (Long.parseLong(freeTime) <= currentTime) {//试看时间结束，
                                player.trySeeFinish();
                                tvFreeLook.setVisibility(View.GONE);
                            }
                        }
                    });


                } else if (purchaseState == Constants.JuniorToHighDetail.puchased) {//已购买,可查看pdf

                    tvFreeLook.setVisibility(View.GONE);
                    llNotPurchased.setVisibility(View.GONE);
                    pdfView.setVisibility(View.VISIBLE);
                    mMultiStateView.setVisibility(View.VISIBLE);
                    llBottomPay.setVisibility(View.GONE);
                    viewLine.setVisibility(View.GONE);
                    showPdf(mList);
                    progressPercente = bundle.getString("progress");
                    if (!TextUtils.isEmpty(progressPercente)) {//切换到上一次播放的时候
                        player.start();
                        player.setPreparedListener(JuniorToHighDetailActivity.this);
                    }


                    player.setProgressListner(new GiraffePlayer.ProgressListener() {


                        @Override
                        public void progress(long currentTime, long totalTime) {//已购买，测试播放进度，学习到多久
                            hasOrNotNetWork();
                            if (totalTime != 0) {
                                progress = (int) ((currentTime * 100) / totalTime);//进度保存
                            }
                        }


                    });
                }
                player.setReloadNetWorkListener(new GiraffePlayer.ReloadNetWorkListener() {//点击重新加载网络
                    @Override
                    public void netWork() {
                        if (CommonUtil.isNetWorkEnabled(JuniorToHighDetailActivity.this))//有网络
                        {
                            player.noNetWorkGone();//无网络提示消失
                            if (CommonUtil.isMobile(JuniorToHighDetailActivity.this) && !CommonUtil.isWifiEnabled(JuniorToHighDetailActivity.this)) {//移动网络开启，并且wifi未开启
                                showNoWifiDialog();

                            } else {
                                player.doPauseResume();//继续播放
                            }

                        }


                    }
                });

            }


        }

    }


    /**
     * 显示没有wifi的dialog
     */
    private void showNoWifiDialog() {
        player.pause();//暂停播放
        //将缓冲关闭
        if (noWifiWarningDialog == null) {
            noWifiWarningDialog = new NoWifiWarningDialog();
            noWifiWarningDialog.setClickListner(this);
            noWifiWarningDialog.show(JuniorToHighDetailActivity.this.getSupportFragmentManager(), "");
        }

    }

    /**
     * 时时监听是否有网络
     */
    private void hasOrNotNetWork() {
        if (!CommonUtil.isNetWorkEnabled(JuniorToHighDetailActivity.this))//如果网络未开启，显示网络未开启页面
        {
            player.noNetWork();

        } else {//网络开启情况
            if (CommonUtil.isMobile(JuniorToHighDetailActivity.this) && !CommonUtil.isWifiEnabled(JuniorToHighDetailActivity.this)) {//移动网络开启，并且wifi未开启
                if (!isContinue) {//继续观看，不弹出提示框

                    if (!isFirst) {
                        showNoWifiDialog();
                        isFirst = true;
                    }

                }
            }

        }
    }


    /**
     * 显示pdf
     *
     * @param list
     */

    private void showPdf(ArrayList<CourseDataArrayBean> list) {
        if (EasyPermissions.hasPermissions(this, perms)) {//如果有权限
            accessPdf(list);
        } else {//先去获取权限
            requestPermissions();
        }

    }

    /**
     * @param list
     */
    private void accessPdf(ArrayList<CourseDataArrayBean> list) {
        juniorToHighDownLoadManager = new JuniorToHighDownLoadManager(this);
        String pdfUrl = null;
        if (purchaseState == Constants.JuniorToHighDetail.present) {
            pdfUrl = screenPdfAndUrl("ZL03", list);//赠品
        } else {
            pdfUrl = screenPdfAndUrl("ZL02", list);//筛选pdf的url,下载文件的网路路径
        }
        if (!TextUtils.isEmpty(pdfUrl)) {
            {
                savePath = JuniorToHighDownLoadManager.getSavePath() + JuniorToHighDownLoadManager.getFileName(pdfUrl) + ".pdf";
                File file = new File(savePath);
                if (file.exists()) {//文件存在,直接读取
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                    displayFromBytes(DESHelper.decryptFile(savePath));
                } else {//文件不存在，则去下载文件
                    juniorToHighDownLoadManager.downLoadPdf(pdfUrl, savePath);
                }
            }
        }
    }


    @Override
    public void initView() {
        super.initView();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
    }


    /**
     * 横竖屏界面切换
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
            int ori = newConfig.orientation; //获取屏幕方向
            if (ori == newConfig.ORIENTATION_LANDSCAPE) {//横屏
                //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
                mToolbar.setVisibility(View.GONE);
                //  去掉虚拟按键全屏显示
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            } else if (ori == newConfig.ORIENTATION_PORTRAIT) {//竖屏
                quitFullScreen();
                mToolbar.setVisibility(View.VISIBLE);
                // 去掉虚拟按键全屏显示
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }


    /**
     * 从文件夹读取文件
     */
    private void displayFromBytes(byte[] bytes) {
        pdfView.fromBytes(bytes)
                .enableAnnotationRendering(true).//添加评论，注释等
                onRender(new OnRenderListener() {//pdf宽度适应屏幕宽度
            @Override
            public void onInitiallyRendered(int pages, float pageWidth, float pageHeight) {
                pdfView.fitToWidth(); // optionally pass page number
            }
        }).load();
    }


    @OnClick({R.id.pay_now})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_now://立即购买，单套课程购买
                Bundle bundle = new Bundle();
                bundle.putString("description", description);//简介
                bundle.putString("courseName", title);//课程名
                bundle.putString("price", price);//价格
                bundle.putString("goodsId", commodityId);//商品id
                bundle.putString("courseTypeFullName", courseTypeFullName);//商品tag
                bundle.putInt("fromWhere", Constants.ConfirmOrder.single_course);
                if (mCommAdapter != null) {
                    if (mCommAdapter.getDataList() != null && mCommAdapter.getDataList().size() > 0) {
                        bundle.putSerializable("couponList", mCommAdapter.getDataList());
                    }
                }
                openActivity(PayConfrimActivity.class, bundle);
                break;
        }
    }


    /**
     * 赛选pdf和mp4的url
     *
     * @param type ZL01 mp4 ,ZL02 pdf
     * @param list
     * @return
     */
    private String screenPdfAndUrl(String type, ArrayList<CourseDataArrayBean> list) {

        CourseDataArrayBean item;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                item = list.get(i);
                if (item != null) {
                    if (item.getCourseDataType().equals(type)) {
                        return item.getCourseDataUrl();
                    }
                }

            }

        }
        return "";
    }


    public void onEventMainThread(final JuniorToHighPdfEvent event) throws BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, ShortBufferException, NoSuchPaddingException, InvalidKeyException, IOException {//下载pdf情况
        switch (event.state) {
            case 0://开始更新

                break;
            case 1://更新中
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                break;
            case 2://下载完成
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                displayFromBytes(DESHelper.decryptFile(savePath));
                break;
        }
    }

    /**
     * 将秒转化成分钟
     *
     * @param time
     */
    private String generateTime(String time) {
        long totalTime = Long.parseLong(time);
        float seconds = ((int) (totalTime % 60) * 1F) / 60;//多少秒
        int minutes = (int) (totalTime / 60);//分钟数
        if (seconds > 0) {
            return String.valueOf(minutes + seconds);
        } else {
            return String.valueOf(minutes);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
        if (purchaseState == Constants.JuniorToHighDetail.puchased)//已购买
        {
            String userId = UserConstant.getInstance(JuniorToHighDetailActivity.this).getUserId();
            if (!TextUtils.isEmpty(userId)) {
                if (!TextUtils.isEmpty(commodityId)) {//保存播放进度
                    JuniorToHighProgressManager.getInstance().putHashMapValue(this, userId, commodityId, progress + "");
                    EventBus.getDefault().post(new AppEventType(AppEventType.PROGRESS_SUCCESS, listPosition));
                }
            }
        }
        EventBus.getDefault().unregister(this);

    }


    public void onEventMainThread(AppEventType event) {
        switch (event.type) {
            case AppEventType.SINGLE_PAY_SUCCESS://
                finish();
                break;
        }
    }

    /**
     * 暂停后再播放
     */
    @Override
    public void play() {
        noWifiWarningDialog = null;
        isFirst = false;
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.e("nihao", "");
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.e("nihao", "");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        accessPdf(mList);
    }

    class CommViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_coupon_type)
        TextView tvCouponType;

        @BindView(R.id.tv_coupon_content)
        TextView tvCouponContent;


        public CommViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class CommAdapter extends RecyclerView.Adapter<CommViewHolder> {

        private ArrayList<GoodsCoupon> mDatas;
        private Context mContext;

        public CommAdapter(Context context, ArrayList<GoodsCoupon> datas) {
            this.mContext = context;
            this.mDatas = datas;
        }

        @Override
        public CommViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CommViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_coupon, parent, false));
        }

        @Override
        public void onBindViewHolder(CommViewHolder holder, int position) {
            final GoodsCoupon item = mDatas.get(position);
            if (item != null) {
                GoodsCoupon.EventBean event = item.getEvent();
                if (event != null) {
                    GoodsCoupon.EventBean.RuleBean ruleBean = event.getRule();
                    if (ruleBean != null) {
                        holder.tvCouponContent.setText(mContext.getString(R.string.coupon_content_format, PayConstant.formatPrice(ruleBean.getOff() + "")));
                        GoodsCoupon.EventBean.RuleBean.EventTypeBean eventTypeBean = ruleBean.getEventType();
                        if (eventTypeBean != null) {
                            holder.tvCouponType.setText(eventTypeBean.getName());
                        }
                    }
                }
            }

        }


        public ArrayList<GoodsCoupon> getDataList() {
            return mDatas;
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
    }

    @Override
    public void prepare(long totalTime) {//准备就绪


        if (isSeekTo) {//跳转到上次播放的
            player.seek(Integer.parseInt(progressPercente), totalTime);
            isSeekTo = false;
        }
    }

    /**
     * 继续播放
     */
    @Override
    public void ok() {
        isContinue = true;
        player.doPauseResume();//继续播放按钮
    }

    @Override
    public void dissmiss() {
        noWifiWarningDialog = null;
    }

    /**
     * 请求优惠券
     */
    private void requestCoupon(String commodityId) {
        mPresenter.goodsCoupon(commodityId, new ApiRequestListener<String>() {
            @Override
            public void onResultSuccess(String res) {
                ArrayList<GoodsCoupon> list = (ArrayList<GoodsCoupon>) JSON.parseArray(res, GoodsCoupon.class);
                if (list != null && list.size() > 0) {
                    llCoupon.setVisibility(View.VISIBLE);
                    mCommAdapter = new JuniorToHighDetailActivity.CommAdapter(JuniorToHighDetailActivity.this, list);
                    recyclerViewCouponType.setLayoutManager(new LinearLayoutManager(JuniorToHighDetailActivity.this));
                    recyclerViewCouponType.setAdapter(mCommAdapter);
                }

            }

            @Override
            public void onResultFail() {
            }

            @Override
            public void onResultFail(RequestEntity resultEntity) {
                if (resultEntity.getCode() == HttpCodeConstant.CONNECT_401) {
                    showShortToast("未登录");
                }
            }
        });
    }

    /**
     * 动态取消全屏
     */
    private void quitFullScreen() {
        final WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setAttributes(attrs);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    public void setStatusBar() {
    }


    /**
     * 获取存储权限
     */
    public void requestPermissions() {
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "需要访问文件夹",
                    FILE, perms);
        }
    }
}
