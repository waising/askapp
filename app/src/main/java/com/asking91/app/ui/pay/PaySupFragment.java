package com.asking91.app.ui.pay;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.asking91.app.R;
import com.asking91.app.entity.pay.CourseDetailEntity;
import com.asking91.app.mvpframe.base.BaseFrameFragment;
import com.asking91.app.ui.adapter.pay.PaySupAdapter;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.ui.widget.camera.utils.AppEventType;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 课程购买子页
 * Created by linbin on 2016/10/26.
 */

public class PaySupFragment extends BaseFrameFragment<PayPresenter, PayModel> implements PaySupAdapter.MyItemClickListener {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;


    PaySupAdapter paySupAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pay_sup);//设置View
        ButterKnife.bind(this, getContentView());

    }


    public static PaySupFragment newInstance(String packageTypeId, String timeLimit, String PackageTypeName) {
        PaySupFragment fragment = new PaySupFragment();
        Bundle bundle = new Bundle();
        bundle.putString("packageTypeId", packageTypeId);
        bundle.putString("timeLimit", timeLimit);
        bundle.putString("PackageTypeName", PackageTypeName);
        fragment.setArguments(bundle);
        return fragment;
    }


    /**
     * 请求list
     */
    private void findClassDetailList(final String packageTypeId, String timeLimit) {

        mPresenter.findClassDetailList(packageTypeId, timeLimit, "0", "100", new ApiRequestListener<String>() {
            @Override
            public void onResultSuccess(String res) {
                ArrayList<CourseDetailEntity> list = (ArrayList<CourseDetailEntity>) JSON.parseArray(res, CourseDetailEntity.class);
                if (list != null && list.size() > 0) {//有数据
                    paySupAdapter = new PaySupAdapter(getActivity(), list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(paySupAdapter);
                    paySupAdapter.setOnItemClickListener(PaySupFragment.this);
                    CourseDetailEntity courseDetailEntity = list.get(0);
                    if (courseDetailEntity != null) {
                        EventBus.getDefault().post(new AppEventType(AppEventType.PRICE, courseDetailEntity,getArguments().getString("timeLimit"),getArguments().getString("PackageTypeName")));
                    }
                } else {//没数据
                    EventBus.getDefault().post(new AppEventType(AppEventType.PRICE, new CourseDetailEntity(),getArguments().getString("timeLimit"),getArguments().getString("PackageTypeName")));
                }

            }

            @Override
            public void onResultFail() {

            }
        });


    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public void initView() {
        super.initView();

    }

    @Override
    public void initListener() {
        super.initListener();
        Bundle bundle = getArguments();
        if (bundle != null) {
            findClassDetailList(bundle.getString("packageTypeId"), bundle.getString("timeLimit"));
        }

    }

    @Override
    public void initLoad() {
        super.initLoad();

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


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onItemClick(View view, int postion) {
        if (paySupAdapter.mPosition != postion) {//避免重复点击
            paySupAdapter.mPosition = postion;
            paySupAdapter.notifyDataSetChanged();
            CourseDetailEntity courseDetailEntity = paySupAdapter.getItemData(postion);
            if (courseDetailEntity != null) {
                EventBus.getDefault().post(new AppEventType(AppEventType.PRICE, courseDetailEntity,getArguments().getString("timeLimit"),getArguments().getString("PackageTypeName")));
            }

        }

    }


}
