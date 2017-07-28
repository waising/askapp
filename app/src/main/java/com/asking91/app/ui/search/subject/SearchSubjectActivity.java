package com.asking91.app.ui.search.subject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.basepacket.SubjectEntity;
import com.asking91.app.global.Constants;
import com.asking91.app.global.OnlineQAConstant;
import com.asking91.app.global.OtoConstant;
import com.asking91.app.global.SearchConstant;
import com.asking91.app.ui.widget.camera.ui.BaseEvenActivity;
import com.asking91.app.ui.widget.camera.ui.CameraActivity;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.ui.widget.camera.utils.BitmapUtil;
import com.asking91.app.util.CommonUtil;
import com.google.gson.reflect.TypeToken;
import com.hanvon.HWCloudManager;

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

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchSubjectActivity extends BaseEvenActivity<SubjectDetailPresenter, SubjectDetailModel> implements SubjectDetailContract.View, Toolbar.OnMenuItemClickListener {

    @BindView(com.asking91.app.R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(com.asking91.app.R.id.toolBar)
    Toolbar mToolbar;
    @BindView(com.asking91.app.R.id.editText)
    EditText mEditText;
    @BindView(com.asking91.app.R.id.pic_take)
    ImageView picTake;
    @BindString(com.asking91.app.R.string.please_enter_keyword)
    String strPleaseEnterKeyWord;
    @BindString(com.asking91.app.R.string.ocr_identify_failure)
    String strOcrIdentifyFailure;

    @BindView(R.id.notice_tv)
    TextView noticeTv;

    //拍照搜题 取6条数据
    int start = 0, limit = 6;
    private List<String> mDataList;
    String subjectCatalog = Constants.SubjectCatalog.CZSX; // 初中数学 M2
    private String picTakePath, picHwyResult;
    private HWCloudManager hwCloudManagerFormula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View contentView = LayoutInflater.from(this).inflate(com.asking91.app.R.layout.activity_search_subject, null);
        setContentView(contentView);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        super.initData();
        mDataList = Arrays.asList(OnlineQAConstant.classVersionNoAll);
        hwCloudManagerFormula = new HWCloudManager(mthis, OtoConstant.HWY_KEY);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolbar, SearchConstant.SearchSubject.tittle);
        initmagicIndicator();
    }


    private Bitmap caBitmap;
    public void onEventMainThread(AppEventType event) {
        switch (event.type){
            case AppEventType.SEARCH_SUBJECT_CAMERA_REQUEST:
                showShortToast("解析图片...");
                picTakePath = (String)event.values[0];
                caBitmap  = (Bitmap)event.values[1];
                picTake.setImageBitmap(caBitmap);

                noticeTv.setVisibility(View.VISIBLE);

                Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        //灰度处理
                        String greyPath = BitmapUtil.saveGreyBitmap(picTakePath,caBitmap);
                        //Bitmap bitmap = BitmapUtil.bitmap2Gray(caBitmap);
                        subscriber.onNext(greyPath);
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new rx.Observer<String>() {
                            @Override
                            public void onNext(String greyPath) {
                                mPresenter.getHwyQuestion(hwCloudManagerFormula, greyPath);
                            }
                            @Override
                            public void onCompleted() {}
                            @Override
                            public void onError(Throwable e) { }
                        });

                break;
        }
    }

    //-----------拍照End------------------

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_buy:
                //openCameraActivity(BuyActivity.class);
                break;
        }
        return true;
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
                        mEditText.setText("");
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
                        magicIndicator.onPageSelected(index);
                        magicIndicator.onPageScrollStateChanged(index);
                        magicIndicator.onPageScrolled(index, 0, 0);
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

    @Override
    public void onRequestSuccess(int type, ResponseBody responseBody) {
        Map<String, Object> map = CommonUtil.parseDataToMap(responseBody);
        List<SubjectEntity> list = CommonUtil.parseDataToList(map.get("list"), new TypeToken<List<SubjectEntity>>() {});
        switch (type) {
            case 111:
                // 文字搜题
                try {
                    String stringEditText = mEditText.getText().toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("me", "meWord");
                    bundle.putString("stringEditText", stringEditText);
                    bundle.putString("subjectCatalog", subjectCatalog);
                    bundle.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) list);
                    openActivity(SubjectListActivity.class, bundle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 222:
                // 拍照搜题
                Bundle bundle = new Bundle();
                bundle.putString("me", "takePic");
                bundle.putString("picHwyResult", picHwyResult);
                bundle.putString("picTakePath", picTakePath);
                bundle.putString("subjectCatalog", subjectCatalog);
                bundle.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) list);
                openActivity(SubjectDetailActivity.class, bundle);
                break;
        }
    }

    @OnClick({R.id.imgSearch, R.id.pic_take})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgSearch:
                if (!TextUtils.isEmpty(mEditText.getText().toString())) {
                    mPresenter.presenterSearchSubject(111, subjectCatalog, mEditText.getText().toString().trim(), start, limit); // 文字搜题
                } else {
                    showShortToast(strPleaseEnterKeyWord);
                }
                break;
            case R.id.pic_take:
                mEditText.setText(""); // 清除文字搜题关键字，避免影响拍照搜题
                CameraActivity.openCameraActivity(this,AppEventType.SEARCH_SUBJECT_CAMERA_REQUEST,0);
                break;
        }
    }

    @Override
    public void onSuccess(String methodName, String string) {
        if (methodName.equals("getHwyQuestion-next")) {
            if (string.indexOf("formula ocr ecognize core service exception") > 0) {
                showShortToast(strOcrIdentifyFailure);
            } else {
                picHwyResult = string;
                if (!TextUtils.isEmpty(picHwyResult)) {
                    mPresenter.presenterSearchSubject(222, subjectCatalog, picHwyResult, start, limit); // 请求拍照搜题接口
                }else{
                    showShortToast("图片解析失败");
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

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }
}
