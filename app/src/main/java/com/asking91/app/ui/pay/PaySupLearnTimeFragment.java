package com.asking91.app.ui.pay;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.mvpframe.base.BaseFrameFragment;
import com.asking91.app.ui.juniorhigh.JuniorToHighModel;
import com.asking91.app.ui.juniorhigh.JuniorToHighPresenter;
import com.asking91.app.ui.widget.camera.utils.AppEventType;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


/**
 * 课程购买Fragment
 * Created by linbin on 2016/10/26.
 */

public class PaySupLearnTimeFragment extends BaseFrameFragment<JuniorToHighPresenter, JuniorToHighModel> {

    protected Fragment mCurrFragment;

    @BindView(R.id.top_cbox1)
    TextView top_cbox1;
    @BindView(R.id.top_cbox2)
    TextView top_cbox2;
    private ArrayList<Fragment> fragments;
    /**
     * 是否可见状态
     */
    private boolean isVisible;

    /**
     * 标志位，View已经初始化完成。
     * 2016/04/29
     * 用isAdded()属性代替
     * 2016/05/03
     * isPrepared还是准一些,isAdded有可能出现onCreateView没走完但是isAdded了
     */
    private boolean isPrepared;
    /**
     * 是否第一次加载
     */
    private boolean isFirstLoad = true;


    private String PackageTypeName;


    private int lastPosition = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pay_sup_learn_time);//设置View
        ButterKnife.bind(this, getContentView());
    }


    public static PaySupLearnTimeFragment newInstance(String packageTypeId, String PackageTypeName) {
        PaySupLearnTimeFragment fragment = new PaySupLearnTimeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("packageTypeId", packageTypeId);
        bundle.putString("PackageTypeName", PackageTypeName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initData() {
        super.initData();
        isFirstLoad = true;
        isPrepared = true;

    }

    @Override
    public void initView() {
        super.initView();
        top_cbox1.setSelected(true);
        top_cbox2.setSelected(false);
    }

    @Override
    public void initListener() {
        super.initListener();

    }

    @Override
    public void initLoad() {
        super.initLoad();
        lazyLoad();
    }

    /**
     * 添加两个实例
     */
    private void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstLoad) {
            return;
        }
        isFirstLoad = false;
        Bundle bundle = getArguments();
        if (bundle != null) {
            PackageTypeName = bundle.getString("PackageTypeName");
            fragments = new ArrayList<>();
            fragments.add(PaySupFragment.newInstance(bundle.getString("packageTypeId"), "12", PackageTypeName));//
            fragments.add(PaySupFragment.newInstance(bundle.getString("packageTypeId"), "6", PackageTypeName));//
            setCurrFragment(fragments.get(0));
            EventBus.getDefault().post(new AppEventType(AppEventType.CHOOSE_MONTH, PackageTypeName, "12"));//刚进入默认选中12个月
            toFragment(fragments.get(0));
        }

    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /**
     * 顶部文理科布局切换
     *
     * @param v
     */
    @OnClick({R.id.top_cbox1, R.id.top_cbox2})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_cbox1://12个月
                if (lastPosition != 0) {//避免重复点击
                    lastPosition = 0;//记录选中哪一个
                    top_cbox1.setSelected(!v.isSelected());
                    top_cbox2.setSelected(false);
                    toFragment(fragments.get(0));
                    EventBus.getDefault().post(new AppEventType(AppEventType.CHOOSE_MONTH, PackageTypeName, "12"));//
                }
                break;
            case R.id.top_cbox2://6个月
                if (lastPosition != 1) {//避免重复点击
                    lastPosition = 1;//记录选中哪一个
                    top_cbox2.setSelected(!v.isSelected());
                    top_cbox1.setSelected(false);
                    toFragment(fragments.get(1));
                    EventBus.getDefault().post(new AppEventType(AppEventType.CHOOSE_MONTH, PackageTypeName, "6"));//
                }
                break;


        }
    }


    /**
     * 设置当前显示的fragement
     *
     * @param mCurrFragment
     */
    public void setCurrFragment(Fragment mCurrFragment) {
        this.mCurrFragment = mCurrFragment;
    }

    /**
     * 跳转fragment
     *
     * @param toFragment
     */
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
            getChildFragmentManager().beginTransaction().hide(mCurrFragment)
                    .show(toFragment).commit();
        } else {
            getChildFragmentManager().beginTransaction().hide(mCurrFragment)
                    .add(R.id.fragment, toFragment).show(toFragment)
                    .commit();
        }
        mCurrFragment = toFragment;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     *
     * @param isVisibleToUser 是否显示出来了
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 可见的时候
     */
    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {


    }


}
