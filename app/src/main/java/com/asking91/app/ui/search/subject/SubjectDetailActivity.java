package com.asking91.app.ui.search.subject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asking91.app.BuildConfig;
import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.basepacket.AnswerOption;
import com.asking91.app.entity.basepacket.KnowledgeTypeDetailEntity;
import com.asking91.app.entity.basepacket.SubjectEntity;
import com.asking91.app.entity.basepacket.SubjectType;
import com.asking91.app.global.Constants;
import com.asking91.app.global.OtoConstant;
import com.asking91.app.ui.login.LoginActivity;
import com.asking91.app.ui.main.PhotoShowActivity;
import com.asking91.app.ui.oto.OtoAskActivity;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.camera.ui.BaseEvenActivity;
import com.asking91.app.ui.widget.camera.ui.CameraActivity;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.ui.widget.camera.utils.BitmapUtil;
import com.asking91.app.ui.widget.indicator.TabPageIndicator;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.DeviceUtil;
import com.google.gson.reflect.TypeToken;
import com.hanvon.HWCloudManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

// 文字搜题目和拍照搜题目的详情页面

public class SubjectDetailActivity extends BaseEvenActivity<SubjectDetailPresenter, SubjectDetailModel> implements SubjectDetailContract.View, Toolbar.OnMenuItemClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.indicator)
    TabPageIndicator indicator;

    @BindView(R.id.layoutTakeOnce)
    LinearLayout layoutTakeOnce;
    @BindView(R.id.layoutTeacher)
    LinearLayout layoutTeacher;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.layoutFromWord)
    LinearLayout layoutFromWord;
    @BindView(R.id.pic_take)
    ImageView picTake;
    @BindView(R.id.layoutNone)
    LinearLayout layoutNone;
    @BindView(R.id.mathView)
    AskMathView mathView;
    @BindString(R.string.ocr_identify_failure)
    String strOcrIdentifyFailure;

    @BindView(R.id.tvRetry)
    TextView mRetryTv;

    String objId; // 添加收藏需要的ID
    PagerAdapter pagerAdapter;
    int start = 0, limit = 6, maxSize;
    int type = Constants.Collect.title;  // 收藏的题目类型
    List<Fragment> listFragments = new ArrayList<>();
    private List<SubjectEntity> subjectEntity; // 题目
    List<KnowledgeTypeDetailEntity> list; // 解答
    String picTakePath, picHwyResult, subjectTitle, detailsAnswerHtml, subjectCatalog;

    private final int MAX_NUM = 6;

    //----------拍照-----------
    private HWCloudManager hwCloudManagerFormula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_subject_detail, null);
        setContentView(contentView);
        ButterKnife.bind(this);
        hwCloudManagerFormula = new HWCloudManager(this, OtoConstant.HWY_KEY);

        setSwipeBackEnable(false);
    }

    private Bitmap caBitmap;

    public void onEventMainThread(AppEventType event) {
        switch (event.type) {
            case AppEventType.SEARCH_DETAIL_CAMERA_REQUEST:
                showShortToast("解析图片...");
                picTakePath = (String) event.values[0]; // 图片路径
                //灰度处理
                caBitmap = (Bitmap) event.values[1];
                String greyPath = BitmapUtil.saveGreyBitmap(picTakePath, caBitmap);
                mPresenter.getHwyQuestion(hwCloudManagerFormula, greyPath); // 向汉王服务器请求图片解析
                break;
        }
    }
    //-----------拍照End------------------

    @Override
    public void initData() {
        super.initData();
        Intent intent = getIntent();
        if (intent != null) {
            String me = intent.getStringExtra("me");
            subjectCatalog = intent.getStringExtra("subjectCatalog"); // 题目类型
            // 文字搜题入口过来的
            if (me.equals("meWord")) {
                subjectTitle = intent.getStringExtra("subjectTitle"); // 题目标题字段
                String referSubjectId = intent.getStringExtra("referSubjectId"); // referSubjectId 用于向服务器请求详情解析
                if (!TextUtils.isEmpty(referSubjectId)) {
                    mPresenter.presenterSubjectDetail(666, subjectCatalog, referSubjectId); // 通过题目列表的 referSubjectId 向服务器请求题目解析接口
                } else { //有些題目沒有返回给我们 referSubjectId
                    detailsAnswerHtml = intent.getStringExtra("detailsAnswerHtml");
                    String str = ("<b>题目</b>" + "<br/>" + "<p>" + subjectTitle + "<p>" + detailsAnswerHtml);
                    mathView.setText(str);
                    mathView.setVisibility(View.VISIBLE);
                }
            }
            // 拍照搜题入口过来的
            if (me.equals("takePic")) {
                picTakePath = intent.getStringExtra("picTakePath");
                picHwyResult = intent.getStringExtra("picHwyResult");
                subjectEntity = intent.getParcelableArrayListExtra("data");
                fromTakePhoto();
                layoutFromWord.setVisibility(View.GONE); // 隐藏文字搜题入口的底部按钮
                layoutTakeOnce.setVisibility(View.VISIBLE); // 底部“再拍一题”按钮
                layoutTeacher.setVisibility(View.VISIBLE); // 底部“请老师答疑”按钮
            }
        }
    }

    // 拍照搜题入口过来的
    private void fromTakePhoto() {
        if (!TextUtils.isEmpty(picTakePath)) {
            picTake.setVisibility(View.VISIBLE);
            picTake.setImageBitmap(CommonUtil.decodeBitmapWithOrientationMax(picTakePath, DeviceUtil.getScreenWidth(this), DeviceUtil.getScreenHeight(this)));
        }
        // 设置最多显示3个页面
        if (subjectEntity != null && subjectEntity.size() > 0) {
            if (subjectEntity.size() > MAX_NUM) {
                maxSize = MAX_NUM;
            } else {
                maxSize = subjectEntity.size();
            }
            for (int i = 0; i < maxSize; i++) {
                String referSubjectId = ""; //这边过后改掉，不需要再根据referSubjectId去请求详细解析
                if (!TextUtils.isEmpty(referSubjectId)) {
                    mPresenter.presenterSubjectDetail(123, subjectCatalog, referSubjectId); // 请求拍照搜题的详细解答
                }
            }
            // 初始化 Fragment 并为其设置数据
            for (int i = 0; i < maxSize; i++) {
                SubjectDetailTakePhotoFragment subjectDetailTakePhotoFragment = new SubjectDetailTakePhotoFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("num", i); // 底部数字指示器-> 当前
                bundle.putInt("totalNum", maxSize); // 底部数字指示器-> 总的
                // 获取题目标题
                SubjectType subjectType = subjectEntity.get(i).getSubjectType();
                String subjectDescriptionHtml = subjectEntity.get(i).getSubjectDescriptionHtml();

                String strOptionContent = "";
                // typeId 是后台传给我们的，1是选择题，2是解答题，3是判断题，4是填空题
                if (subjectType.getTypeId().equals("1")) {
                    List<AnswerOption> answerOptions = subjectEntity.get(i).getAnswerOptions();
                    strOptionContent = "";
                    for (int j = 0; j < answerOptions.size(); j++) {
                        String optionName = answerOptions.get(j).getOptionName(); // 选项名称
                        String optionContentHtml = answerOptions.get(j).getOptionContentHtml(); // 选项内容
                        if (optionContentHtml != null && optionContentHtml.contains("</p>")) {
                            strOptionContent += (optionName + "、" + optionContentHtml.substring(3, optionContentHtml.length() - 4) + "<br/>");
                        } else {
                            strOptionContent += (optionName + "、" + optionContentHtml + "<br/>");
                        }
                    }
                    subjectTitle = subjectDescriptionHtml + "<br>" + strOptionContent;
                }
                if (subjectType.getTypeId().equals("2") || subjectType.getTypeId().equals("3") || subjectType.getTypeId().equals("4")) {
                    // subjectTitle 用于保存题目标题标题采用 subjectDescriptionHtml字段
                    subjectTitle = subjectDescriptionHtml;
                }
                // 获取题目标题结束
                bundle.putString("subjectTitle", subjectTitle); // 传题目标题给片段 Fragment
                if (list != null && list.size() > 0) {
                    // 前面通过判断 referSubjectId 是否为空，非空根据该ID向服务器请求详细解析数据，为空的话则直接利用 detailsAnswerHtml 字段
                    bundle.putString("meDetailAnswer", "meDetailAnswerOther");
                    bundle.putParcelable("subjectDetailEntity", (Parcelable) list.get(i)); // 服务器返回给我们的题目解析
                } else {
                    bundle.putString("meDetailAnswer", "meDetailAnswerChoice");
                    String detailsAnswerHtml = subjectEntity.get(i).getDetailsAnswerHtml(); // 获取题目解析
                    bundle.putString("detailsAnswerHtml", detailsAnswerHtml);
                }
                subjectDetailTakePhotoFragment.setArguments(bundle);
                listFragments.add(subjectDetailTakePhotoFragment);
            }
            pagerAdapter = new PagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(pagerAdapter);

            indicator.setViewPager(viewPager);
        } else {
            layoutNone.setVisibility(View.VISIBLE);
            //提示
            mRetryTv.setText(getString(R.string.photo_search_notice));
        }
    }

    @Override
    public void initView() {
        super.initView();
        mathView.formatMath().showWebImage();
        if(BuildConfig.DEBUG){
            mToolbar.inflateMenu(R.menu.menu_subj_tit);
            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_node:
                            showNormalDialog();
                            break;
                    }
                    return true;
                }
            });
        }
    }

    private void showNormalDialog() {
        String res = CommonUtil.getReplaceStr(picHwyResult);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.dialog_math_webview, null);

        AskMathView web_res = (AskMathView) view.findViewById(R.id.web_res);
        web_res.setText(res);

        android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(this).
                setTitle("原题解析").
                setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create();
        dialog.show();

    }

    // 检查（判断）是否已收藏，以前没有收藏的那么为其设置（添加）红色爱心图标（默认是灰色爱心图标）
    private void check(ResponseBody body) {
        Map<String, Object> map = CommonUtil.parseDataToMap(body);
        if (map.get("flag") != null && "0".equals(String.valueOf(map.get("flag")))) {
            if ("1".equals(String.valueOf(map.get("collect"))))
                mToolbar.getMenu().getItem(0).setIcon(R.mipmap.love_2);
        }
    }

    // 添加收藏
    public void save(ResponseBody body) {
        Map<String, Object> map = CommonUtil.parseDataToMap(body);
        if (map.get("flag") != null && "0".equals(String.valueOf(map.get("flag")))) {
            showShortToast(String.valueOf(map.get("msg")));
            mToolbar.getMenu().getItem(0).setIcon(R.mipmap.love_2);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_follow:
                //根据图标判断是否已关注
                if (mToolbar.getMenu().getItem(0).getIcon().equals(R.mipmap.love_2)) {
                    showShortToast("资讯已经收藏");
                } else
                    // 调用添加收藏接口
                    mPresenter.saveCollection(111, type, subjectTitle, objId);
                break;
        }
        return true;
    }

    class PagerAdapter extends FragmentStatePagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return listFragments.get(position);
        }

        @Override
        public int getCount() {
            return maxSize;
        }
    }


    @OnClick({R.id.layoutTakeOnce, R.id.layoutTeacher, R.id.layoutFromWord, R.id.pic_take, R.id.back})
    public void onClick(View v) {
        switch (v.getId()) {
            // 拍照搜题入口过来的，再拍一题
            case R.id.layoutTakeOnce:
                CameraActivity.openCameraActivity(this, AppEventType.SEARCH_DETAIL_CAMERA_REQUEST,0);
                break;
            // 拍照搜题入口过来的，请老师答疑
            case R.id.layoutTeacher:
                if (getUserConstant().isTokenLogin()) {
                    if (!TextUtils.isEmpty(picTakePath)) {
                        Bundle bundleTeacher = new Bundle();
                        bundleTeacher.putString("picTakePath", picTakePath);
                        CommonUtil.openActivity(mthis, OtoAskActivity.class, bundleTeacher);
                    }
                } else {
                    openActivity(LoginActivity.class);
                }

                break;
            // 文字搜题入口过来的，请老师答疑
            case R.id.layoutFromWord:
                if (getUserConstant().isTokenLogin()) {
                    Bundle bundleTeacher = new Bundle();
                    bundleTeacher.putString("picTakePath", "meWord");
                    CommonUtil.openActivity(mthis, OtoAskActivity.class, bundleTeacher);
                } else {
                    openActivity(LoginActivity.class);
                }
                break;
            // 拍照搜题入口过来的，点击图片查看大图
            case R.id.pic_take:

                Bundle bundle = new Bundle();
                bundle.putString(Constants.Key.WEB_IMAGE_URL, picTakePath);
                Intent intent = new Intent(SubjectDetailActivity.this, PhotoShowActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
            case R.id.back:
                finish();
                break;
        }
    }


    @Override
    public void onRequestSuccess(int type, ResponseBody responseBody) {

        switch (type) {
            case 666:
                // 文字搜题入口过来的，当 referSubjectId 不为空时，根据该id向服务器请求详细解答数据
                Map<String, Object> map = CommonUtil.parseDataToMap(responseBody);
                List<KnowledgeTypeDetailEntity> SubjectDetailEntity = CommonUtil.parseDataToList(map.get("taps"), new TypeToken<List<KnowledgeTypeDetailEntity>>() {
                });
                if (SubjectDetailEntity != null && SubjectDetailEntity.size() > 0) {
                    String tabContentHtml = SubjectDetailEntity.get(0).getTabContentHtml();
                    if (!TextUtils.isEmpty(tabContentHtml)) {
                        String str = ("<b>题目</b>" + "<br/>" + "<p>" + subjectTitle + "<br/>" + tabContentHtml);
                        mathView.setText(str);
                        mathView.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (!TextUtils.isEmpty(detailsAnswerHtml)) {
                        String str = ("<b>题目</b>" + "<br/>" + "<p>" + subjectTitle + "<br/>" + detailsAnswerHtml);
                        mathView.setText(str);
                        mathView.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case 123:
                // 拍照搜题入口过来的，当 referSubjectId 不为空时，根据该id向服务器请求详细解答数据
                Map<String, Object> mapTwo = CommonUtil.parseDataToMap(responseBody);
                List<KnowledgeTypeDetailEntity> SubjectDetailEntityTwo = CommonUtil.parseDataToList(mapTwo.get("taps"), new TypeToken<List<KnowledgeTypeDetailEntity>>() {
                });
                list.addAll(SubjectDetailEntityTwo);
                // viewPager.setAdapter(null);
                break;
            case 333:
                // 拍照搜题入口过来的，再拍一题
                if (subjectEntity != null && subjectEntity.size() > 0) {
                    subjectEntity.clear();
                }
                if (listFragments != null && listFragments.size() > 0) {
                    listFragments.clear();
                }
                Map<String, Object> mapOnce = CommonUtil.parseDataToMap(responseBody);
                subjectEntity = (ArrayList<SubjectEntity>) CommonUtil.parseDataToList(mapOnce.get("list"), new TypeToken<List<SubjectEntity>>() {
                });
                viewPager.setAdapter(null);
                fromTakePhoto();
                break;
        }
    }

    @Override
    public void initListener() {
        super.initListener();
    }

    @Override
    public void initLoad() {
        super.initLoad();
    }

    @Override
    public void onRequestStart() {
    }

    @Override
    public void onRequestError(RequestEntity requestEntity) {
    }

    @Override
    public void onRequestEnd() {
    }

    @Override
    public void onSuccess(String methodName, String string) {
        // 拍照搜题入口过来的，再拍一题调用汉王ocr识别拍照图片里的文字
        if (methodName.equals("getHwyQuestion-next")) {
            if (string.equals("formula ocr ecognize core service exception")) {
                showShortToast(strOcrIdentifyFailure);
            } else {
                picHwyResult = string;//CommonUtil.getImgReplaceStr(string);
                if (!TextUtils.isEmpty(subjectCatalog)) {
                    mPresenter.presenterSearchSubject(333, subjectCatalog, picHwyResult, start, limit); //请求拍照搜题接口
                }
            }
        }
    }

    @Override
    public void onInternetError(String methodName) {

    }

    @Override
    public void onError(int type) {

    }
}
