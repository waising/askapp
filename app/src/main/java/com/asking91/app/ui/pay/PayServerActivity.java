package com.asking91.app.ui.pay;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.common.SpaceItemDecoration;
import com.asking91.app.entity.basepacket.CourseEntity;
import com.asking91.app.entity.pay.PayClassEntity;
import com.asking91.app.global.Constants;
import com.asking91.app.global.PayConstant;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.adapter.pay.PayClassAdapter;
import com.asking91.app.ui.adapter.pay.PayServerVersionAdapter;
import com.asking91.app.util.CommonUtil;
import com.google.gson.reflect.TypeToken;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * 服务购买
 * Created by wxwang on 2016/11/30.
 */
public class PayServerActivity extends BaseFrameActivity<PayPresenter, PayModel>
        implements PayContract.View {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    @BindView(R.id.pay_km_rg)
    RadioGroup mKmRg;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerView_class)
    RecyclerView recyclerViewClass;

    @BindView(R.id.pay_study_time)
    RadioGroup mMonthRadioGroup;

    @BindView(R.id.pay_money)
    TextView mPayMoneyTv;

    private int mVersionId = 0;
    ArrayMap<Integer,String> map;
    // 教材版本
    List<CourseEntity> mVersionDatas;
    private PayServerVersionAdapter payServerVersionAdapter;

    List<PayClassEntity> mClassDatas;
    private PayClassAdapter payClassAdapter;
    int month = 12;
    String mCommodityId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_server);
        ButterKnife.bind(this);
    }

    @Override
    public void initView(){
        super.initView();
        setToolbar(mToolbar, getString(R.string.pay_server));
        recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        recyclerView.addItemDecoration(new SpaceItemDecoration(8));

        recyclerViewClass.addItemDecoration(new SpaceItemDecoration(8));
        recyclerViewClass.setLayoutManager(new GridLayoutManager(this,4));

        ((RadioButton) mMonthRadioGroup.getChildAt(0)).setChecked(true);
        ((RadioButton) mKmRg.getChildAt(0)).setChecked(true);
    }

    @Override
    public void initData(){
        super.initData();
        initMap();

        mVersionDatas = new ArrayList<>();
        payServerVersionAdapter = new PayServerVersionAdapter(this,mVersionDatas);

        mClassDatas = new ArrayList<>();
        payClassAdapter = new PayClassAdapter(this,mClassDatas);

        recyclerView.setAdapter(payServerVersionAdapter);
        recyclerViewClass.setAdapter(payClassAdapter);
    }

    @Override
    public void initLoad(){
        super.initLoad();
        initData(map.get(R.id.czsx_rb),12,Constants.Pay.basepack);
    }

    @Override
    public void initListener(){
        super.initListener();

        mKmRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mVersionId = 0;
                mCommodityId = "";
                initData(map.get(checkedId),month,Constants.Pay.basepack);
                ((RadioButton) mMonthRadioGroup.getChildAt(0)).setChecked(true);
            }
        });

        mMonthRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                month = checkedId== R.id.time_12_rb ? 12:6;
                mCommodityId = "";
                mPresenter.getCommodityList(map.get(mKmRg.getCheckedRadioButtonId()),month,Constants.Pay.basepack);
            }
        });
    }

    public void setVersionId(int versionId){
        this.mVersionId = versionId;
    }

    @SuppressLint("UseSparseArrays")
    private void initMap(){
        map = new ArrayMap<>();
        map.put(R.id.czsx_rb, Constants.SubjectCatalog.CZSX);
//        map.put(R.id.czwl_rb,Constants.SubjectCatalog.CZWL);
        map.put(R.id.gzsx_rb,Constants.SubjectCatalog.GZSX);
        map.put(R.id.gzwl_rb,Constants.SubjectCatalog.GZWL);
    }

    @Override
    public void onSuccess(int type, ResponseBody body) {
        switch (type){
            case PayConstant.Pay.version:
                version(body);
                break;
            case PayConstant.Pay.commodityList:
                commodityList(body);
                break;
        }
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    private void version(ResponseBody body){
        try{
            List<CourseEntity> list = CommonUtil.parseDataToList(body,new TypeToken<List<CourseEntity>>(){});
            mVersionDatas.clear();
            if(list!=null&&list.size()>0){
                mVersionDatas.addAll(list);
                mVersionId = list.get(0).getVersionId();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            payServerVersionAdapter.setPrePosition(-1);
            payServerVersionAdapter.notifyDataSetChanged();
        }

    }

    private void commodityList(ResponseBody body){
        try{
            List<PayClassEntity> list = CommonUtil.parseDataToList(body,new TypeToken<List<PayClassEntity>>(){});
            mClassDatas.clear();
            if(list!=null&&list.size()>0){
                mClassDatas.addAll(list);
                setData(list.get(0).getId(),list.get(0).getCommodityPrice());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            payClassAdapter.setPrePosition(-1);
            payClassAdapter.notifyDataSetChanged();
        }
    }

    private void initData(String km,int month,int type){
        mPresenter.version(km);
        mPresenter.getCommodityList(km,month,type);
    }

    @OnClick(R.id.pay_btn)
    public void onClick(){
        if(TextUtils.isEmpty(mCommodityId)||mVersionId==0||mPayMoneyTv.getText().equals("0")){
            showShortToast("数据参数不完整");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("commodityId",mCommodityId);
        bundle.putInt("versionId",mVersionId);
        openActivity(PayActivity.class,bundle);
    }

    public void setData(String id,String price){
        mCommodityId = id;
        String money = price!=null?price:"0";

        double m = Double.parseDouble(money);
        DecimalFormat df = new DecimalFormat("######0.00");
        mPayMoneyTv.setText(df.format(m/100));
    }
}
