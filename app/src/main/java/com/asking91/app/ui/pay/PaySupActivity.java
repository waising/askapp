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
import com.asking91.app.entity.basepacket.CourseEntity;
import com.asking91.app.entity.pay.PayClassEntity;
import com.asking91.app.global.Constants;
import com.asking91.app.global.PayConstant;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.adapter.pay.PayClassAdapter;
import com.asking91.app.ui.adapter.pay.PayVersionAdapter;
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
 * 超级辅导课购买
 * Created by wxwang on 2016/11/30.
 */
public class PaySupActivity extends BaseFrameActivity<PayPresenter, PayModel>
        implements PayContract.View {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    @BindView(R.id.pay_km_rg)//科目
    RadioGroup mKmRg;

    @BindView(R.id.recyclerView)//课程教材
    RecyclerView recyclerView;
    @BindView(R.id.recyclerView_class)//所学年级
    RecyclerView recyclerViewClass;

    @BindView(R.id.pay_study_time)
    RadioGroup mMonthRadioGroup;

    @BindView(R.id.pay_money)
    TextView mPayMoneyTv;

    private int mVersionId = 0;
    ArrayMap<Integer,String> map;

    List<CourseEntity> mVersionDatas;
    private PayVersionAdapter payVersionAdapter;

    List<PayClassEntity> mClassDatas;
    private PayClassAdapter payClassAdapter;
    int month = 12;
    String mCommodityId,mKm = Constants.SubjectCatalog.CZSX;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_super);
        ButterKnife.bind(this);
    }

    @Override
    public void initView(){
        super.initView();
        setToolbar(mToolbar, getString(R.string.pay_super_class));
        recyclerView.setLayoutManager(new GridLayoutManager(this,4));//课程教材recyclerview

        recyclerViewClass.setLayoutManager(new GridLayoutManager(this,4));//所学年级recyclerview

        ((RadioButton) mMonthRadioGroup.getChildAt(0)).setChecked(true);//默认选中所学时限第一项
        ((RadioButton) mKmRg.getChildAt(0)).setChecked(true);//默认选中科目第一项
    }

    @Override
    public void initData(){
        super.initData();
        initMap();

        mVersionDatas = new ArrayList<>();
        payVersionAdapter = new PayVersionAdapter(this,mVersionDatas);

        mClassDatas = new ArrayList<>();
        payClassAdapter = new PayClassAdapter(this,mClassDatas);

        recyclerView.setAdapter(payVersionAdapter);
        recyclerViewClass.setAdapter(payClassAdapter);
    }

    @Override
    public void initLoad(){
        super.initLoad();
        initData(map.get(R.id.czsx_rb),12,Constants.Pay.supClass);
    }

    @Override
    public void initListener(){
        super.initListener();

        mKmRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {//科目点击事件
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mVersionId = 0;
                mCommodityId = "";
                mKm = map.get(checkedId);
                initData(mKm,month,Constants.Pay.supClass);
                ((RadioButton) mMonthRadioGroup.getChildAt(0)).setChecked(true);
            }
        });

        mMonthRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {//所学时限
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                month = checkedId== R.id.time_12_rb ? 12:6;
                mCommodityId = "";
                mPresenter.getCommodityList(map.get(mKmRg.getCheckedRadioButtonId()),month,Constants.Pay.supClass);//获取辅导课和知识包信息
            }
        });
    }

    public void setVersionId(int versionId){
        this.mVersionId = versionId;
    }

    /**
     * 顶部学科map
     */
    @SuppressLint("UseSparseArrays")
    private void initMap(){
        map = new ArrayMap<>();
        map.put(R.id.czsx_rb, Constants.SubjectCatalog.CZSX);
        map.put(R.id.czwl_rb,Constants.SubjectCatalog.CZWL);
        map.put(R.id.gzsx_rb,Constants.SubjectCatalog.GZSX);
        map.put(R.id.gzwl_rb,Constants.SubjectCatalog.GZWL);
    }

    @Override
    public void onSuccess(int type, ResponseBody body) {
        switch (type){
            case PayConstant.Pay.versionClassic:///超级辅导课,初中数学，高中物理等科目
                versionClassic(body);
                break;
            case PayConstant.Pay.commodityList://获取价格
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

    /**
     * 科目信息，获取课程教材信息
     * @param body
     */
    private void versionClassic(ResponseBody body){
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
            payVersionAdapter.setPrePosition(-1);
            payVersionAdapter.notifyDataSetChanged();
        }

    }

    /**
     * 所学时限
     * @param body
     */
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

    /**
     * 请求信息
     * @param km
     * @param month
     * @param type
     */
    private void initData(String km,int month,int type){
        mPresenter.versionClassic(km);//超级辅导课,初中数学，高中物理等
        mPresenter.getCommodityList(km,month,type);//获取辅导课和知识包信息
    }

    @OnClick(R.id.pay_btn)
    public void onClick(){//继续支付按钮
        if(TextUtils.isEmpty(mCommodityId)||mVersionId==0){
            showShortToast("数据参数不完整");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("commodityId",mCommodityId);
        bundle.putInt("versionId",mVersionId);
        openActivity(PayActivity.class,bundle);
    }

    /**
     * 应付价格
     * @param id
     * @param price
     */
    public void setData(String id,String price){
        mCommodityId = id;
        String money = price!=null?price:"0";

        double m = Double.parseDouble(money);
        DecimalFormat df = new DecimalFormat("######0.00");
        mPayMoneyTv.setText(df.format(m/100));
    }
}
