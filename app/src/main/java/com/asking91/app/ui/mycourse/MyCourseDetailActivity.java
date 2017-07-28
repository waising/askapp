package com.asking91.app.ui.mycourse;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.asking91.app.R;
import com.asking91.app.entity.mycourse.CourseInfo;
import com.asking91.app.entity.mycourse.MyCourse;
import com.asking91.app.global.Constants;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.adapter.mycourse.MyCourseDetailAdapter;
import com.asking91.app.ui.juniorhigh.JuniorToHighDetailActivity;
import com.asking91.app.ui.supertutorial.SuperSelectModelImpl;
import com.asking91.app.ui.supertutorial.SuperSelectPresenterImpl;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.ui.widget.RecycleViewDivider;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的课程详情界面
 * create by linbin
 */
public class MyCourseDetailActivity extends BaseFrameActivity<SuperSelectPresenterImpl, SuperSelectModelImpl> implements MyCourseClickListner {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    MyCourseDetailAdapter myCourseDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycourse_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initLoad() {
        super.initLoad();
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolbar, "我的课程");//标题栏
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.HORIZONTAL, UIUtil.dip2px(this, 8), ContextCompat.getColor(this, R.color.colorTest_f8)));

    }

    @Override
    public void initListener() {
        super.initListener();
        refshAdapt();
    }


    /**
     * 填充页面数据
     */
    public void refshAdapt() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ArrayList<MyCourse.ChildCommodityListBean> list = (ArrayList<MyCourse.ChildCommodityListBean>) bundle.getSerializable("list");
            if (list != null && list.size() > 0) {
                myCourseDetailAdapter = new MyCourseDetailAdapter(this);
                setHeader(recyclerView, bundle.getString("allTitle"), bundle.getString("allTime"));
                recyclerView.setAdapter(myCourseDetailAdapter);
                myCourseDetailAdapter.addDatas(list);
                myCourseDetailAdapter.setClickListner(this);
            }

        }
    }

    private void setHeader(RecyclerView view, String allTitle, String allTime) {
        View header = LayoutInflater.from(this).inflate(R.layout.item_all_course, view, false);
        TextView tvCollpse = (TextView) header.findViewById(R.id.expand);//缩放按钮
        LinearLayout llCollpse = (LinearLayout) header.findViewById(R.id.ll_expand);//缩放按钮

        TextView tvAllName = (TextView) header.findViewById(R.id.tv_all_name);//名称
        tvAllName.setText(allTitle);
        TextView tvAllTime = (TextView) header.findViewById(R.id.tv_all_time);//时间
        tvAllTime.setText(allTime);
        tvCollpse.setText("收起课程");
        ImageView ivExpand = (ImageView) header.findViewById(R.id.iv_expand);
        ivExpand.setImageResource(R.mipmap.ic_cllopse);
        llCollpse.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                finish();
            }
        });
        myCourseDetailAdapter.setHeaderView(header);

    }


    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }


    @Override
    public void click(final int purchaseState, final String progress, final int listPositon, final String commodityId) {

        mPresenter.myCoursePdfAndUrl(this, commodityId, new ApiRequestListener<String>() {//获取我的课堂数据
            @Override
            public void onResultSuccess(String res) {
                CourseInfo courseInfo = JSON.parseObject(res, CourseInfo.class);
                if (courseInfo != null) {
                    if (purchaseState == Constants.JuniorToHighDetail.present) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("purchaseState", Constants.JuniorToHighDetail.present);//购买状态
                        bundle.putSerializable("urlAndPdf", (Serializable) courseInfo.getCourseDataArray());//pdf和url
                        openActivity(JuniorToHighDetailActivity.class, bundle);

                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putInt("purchaseState", Constants.JuniorToHighDetail.puchased);//购买状态
                        bundle.putString("progress", progress);
                        bundle.putInt("listPositon", listPositon);//list中的position
                        bundle.putString("commodityId", commodityId);
                        bundle.putSerializable("urlAndPdf", (Serializable) courseInfo.getCourseDataArray());//pdf和url
                        openActivity(JuniorToHighDetailActivity.class, bundle);

                    }


                }


            }
        });


    }
}
