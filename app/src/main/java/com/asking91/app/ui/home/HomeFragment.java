package com.asking91.app.ui.home;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asking91.app.Asking91;
import com.asking91.app.R;
import com.asking91.app.common.BaseFragment;
import com.asking91.app.common.BigImgShowActivity;
import com.asking91.app.entity.TImage;
import com.asking91.app.entity.supertutorial.StudyHistory;
import com.asking91.app.global.Constants;
import com.asking91.app.ui.adapter.HomePagerAdapter;
import com.asking91.app.ui.onlineqa.OnlineQAActivity;
import com.asking91.app.ui.oto.OtoAskActivity;
import com.asking91.app.ui.search.knowledge.SearchKnowledgeActivity;
import com.asking91.app.ui.search.subject.SearchSubjectActivity;
import com.asking91.app.ui.supertutorial.buy.superclass.SuperClassActivity;
import com.asking91.app.ui.supertutorial.selected.ExamReviewActivity;
import com.asking91.app.ui.widget.camera.ui.CameraActivity;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.util.CommonUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.ScaleCircleNavigator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * 首页Fragment
 * Created by wxiao on 2016/10/26.
 */

public class HomeFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks {
    private final static String TAG = "HomeFragment";

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.magic_indicator6)
    MagicIndicator magicIndicator6;

    @BindView(R.id.ll_studyhis)
    LinearLayout ll_studyhis;

    @BindView(R.id.tv_studyhis)
    TextView tv_studyhis;

    private List<String> mDataList;
    private List<String> mBigDataList;
    private HomePagerAdapter mExamplePagerAdapter;

    String[] perms = {Manifest.permission.CAMERA, Manifest.permission.CHANGE_WIFI_STATE};

    private final int CAMERA = 0x03;

    StudyHistory mHistory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        ButterKnife.bind(this, getContentView());
        EventBus.getDefault().register(this);
        mthis = this;
        mDataList = Arrays.asList(Constants.main_top_pics);
        mBigDataList = Arrays.asList(Constants.main_top_big_pics);
        mExamplePagerAdapter = new HomePagerAdapter(mDataList, mBigDataList);
    }

    @Override
    public void initData() {
        super.initData();
        initMagicIndicator();

        ll_studyhis.setVisibility(View.GONE);
        mHistory = getUserConstant().getStudyHistory();
        if (mHistory != null) {
            ll_studyhis.setVisibility(View.VISIBLE);
            tv_studyhis.setText(mHistory.studyName);
        }
    }

    private void onStudyHis() {
        if (mHistory != null) {
            switch (mHistory.type) {
                case 0:
                    Bundle bundle = new Bundle();
                    bundle.putInt("isFromStudyHis", 1);
                    bundle.putString("gradeId", mHistory.values[0]);
                    bundle.putString("knowledgeId", mHistory.values[1]);
                    bundle.putString("knowledgeName", mHistory.values[2]);
                    bundle.putString("versionName", mHistory.values[3]);
                    bundle.putString("verName", mHistory.values[4]);
                    bundle.putString("action", mHistory.values[5]);
                    CommonUtil.openActivity(SuperClassActivity.class, bundle);
                    getUserConstant().setStudyHistory(mHistory);
                    break;
                case 1:
                    Bundle bundleOneRound = new Bundle();
                    bundleOneRound.putString("action", mHistory.values[0]);
                    bundleOneRound.putString("verName", mHistory.values[1]);
                    bundleOneRound.putString("fromWhere", "oneRound");//从一轮复习跳转过来
                    bundleOneRound.putSerializable("e", mHistory.e);
                    openActivity(ExamReviewActivity.class, bundleOneRound);
                    break;
                case 2:
                    Bundle bundleTwoRound = new Bundle();
                    bundleTwoRound.putString("action", mHistory.values[0]);
                    bundleTwoRound.putString("verName", mHistory.values[1]);
                    bundleTwoRound.putString("fromWhere", "twoRound");//从二轮复习跳转过来
                    bundleTwoRound.putSerializable("e", mHistory.e);
                    openActivity(ExamReviewActivity.class, bundleTwoRound);
                    break;
            }
        }
    }

    public void onEventMainThread(StudyHistory event) {
        mHistory = event;
        tv_studyhis.setText(mHistory.studyName);
        getUserConstant().setStudyHistory(event);
        ll_studyhis.setVisibility(View.VISIBLE);
    }

    public void onEventMainThread(AppEventType event) {
        switch (event.type) {
            case AppEventType.HOME_CAMERA_REQUEST:
                TImage img = TImage.of("", TImage.FromType.OTHER);
                img.setCompressPath((String) event.values[0]);
                ArrayList<TImage> images = new ArrayList<TImage>();
                images.add(img);
                Asking91.getmInstance().getmDataList().clear();
                Asking91.getmInstance().getmDataList().add(images);
                Intent intent = new Intent(getContext(), OtoAskActivity.class);
                startActivity(intent);
                break;
            case AppEventType.RE_CAMERA_QUS_REQUEST:
                CameraActivity.openCameraActivity(getActivity(), AppEventType.HOME_CAMERA_REQUEST,0);
                break;
        }
    }
    //-----------拍照End------------------

    @Override
    public void initListener() {
        super.initListener();
        viewPager.setAdapter(mExamplePagerAdapter);
    }

    @OnClick({R.id.basepacket_tv, R.id.tv_take_pic, R.id.main_img1, R.id.more_tv, R.id.main_img2, R.id.ll_studyhis})
    public void startOnClick(View v) {
        switch (v.getId()) {
            case R.id.basepacket_tv:
                //CommonUtil.openCameraActivity(getActivity(), BasePacketActivity.class);
                openActivity(SearchKnowledgeActivity.class);
                break;
            case R.id.tv_take_pic: //拍照搜题
                if (EasyPermissions.hasPermissions(getActivity(), perms)) {
                    openActivity(SearchSubjectActivity.class);
                } else {
                    requestPermissions();
                }
                break;
            case R.id.main_img1://超级辅导课
                //CommonUtil.openCameraActivity(getActivity(), SuperSelectFragment.class);
                break;
            case R.id.more_tv:
                //openCameraActivity(MoreWonderfulActivity.class);
                CommonUtil.openAuthActivity(getActivity(), OnlineQAActivity.class);
                break;
            case R.id.main_img2://一对一答疑
                if (getUserConstant().isTokenLogin()) {
                    if (EasyPermissions.hasPermissions(getActivity(), perms)) {
                        CameraActivity.openCameraActivity(getActivity(), AppEventType.HOME_CAMERA_REQUEST,0);
                    } else {
                        requestPermissions();
                    }
                } else {
                    Intent intent = new Intent(getActivity(), BigImgShowActivity.class);
                    intent.putExtra("imgPath", Constants.main_top_big_pics[1]);
                    getActivity().startActivity(intent);
                }
                break;
            case R.id.ll_studyhis:
                onStudyHis();
                break;
        }
    }

    private ShowDialog showDialog;

    public void setShowDialog(ShowDialog showDialog) {
        this.showDialog = showDialog;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
////        View rootView = super.onCreateView(inflater, container, savedInstanceState);
//        View rootView=inflater.inflate(R.layout.fragment_home,null);
//        ButterKnife.bind(this, rootView);
//        mthis = this;
//
//        return rootView;
//    }

    public interface ShowDialog {
        void show();
    }

    /**
     * 初始化顶部滚图
     */
    private void initMagicIndicator() {
        ScaleCircleNavigator scaleCircleNavigator = new ScaleCircleNavigator(getActivity());
        scaleCircleNavigator.setCircleCount(mDataList.size());
        scaleCircleNavigator.setNormalCircleColor(ContextCompat.getColor(getActivity(), R.color.white));
        scaleCircleNavigator.setSelectedCircleColor(ContextCompat.getColor(getActivity(), R.color.theme_blue_theme));
        scaleCircleNavigator.setCircleClickListener(new ScaleCircleNavigator.OnCircleClickListener() {
            @Override
            public void onClick(int index) {
                viewPager.setCurrentItem(index);
            }
        });
        magicIndicator6.setNavigator(scaleCircleNavigator);
        ViewPagerHelper.bind(magicIndicator6, viewPager);
    }

    private Handler handler;
    private int runTime = 3000;

    private void viewpagerRun() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int c = viewPager.getCurrentItem() + 1;
                if (c == mDataList.size()) {
                    c = 0;
                }
                viewPager.setCurrentItem(c);
                if (handler != null) {
                    handler.postDelayed(this, runTime);
                }
            }
        }, runTime);
    }

    /**
     * 摄像头权限
     */
    public void requestPermissions() {
        if (!EasyPermissions.hasPermissions(getActivity(), perms)) {
            EasyPermissions.requestPermissions(this, "需要开启摄像头权限",
                    CAMERA, perms);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
