package com.asking91.app.ui.supertutorial.selected;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.pay.SynchronousCourseEntity;
import com.asking91.app.global.Constants;
import com.asking91.app.global.PayConstant;
import com.asking91.app.mvpframe.base.BaseFrameFragment;
import com.asking91.app.ui.adapter.pay.ClassPurchaseCopyAdapter;
import com.asking91.app.ui.pay.PayConfrimActivity;
import com.asking91.app.ui.supertutorial.SuperSelectModelImpl;
import com.asking91.app.ui.supertutorial.SuperSelectPresenterImpl;
import com.asking91.app.ui.widget.WrapContentGridView;
import com.asking91.app.ui.widget.camera.adapter.CBaseAdapter;
import com.asking91.app.ui.widget.camera.utils.BitmapUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * 课程购买的fragment
 */

public class ClassPurchaseFragment extends BaseFrameFragment<SuperSelectPresenterImpl, SuperSelectModelImpl> implements ClassPurchaseCopyAdapter.MyItemClickListener {


    RecyclerView recycler;


    private ClassPurchaseCopyAdapter mAdapter;


    ArrayList<SynchronousCourseEntity.NodelistBeanX.NodelistBean> mList;


    private String productThirdName;

    public static ClassPurchaseFragment newInstance(ArrayList<SynchronousCourseEntity.NodelistBeanX.NodelistBean> nodelist, String productFirstname, String productSecondName) {
        ClassPurchaseFragment fragment = new ClassPurchaseFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", nodelist);
        bundle.putString("productFirstname", productFirstname);
        bundle.putString("productSecondName", productSecondName);
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View rootView = layoutInflater.inflate(R.layout.fragment_class_purchase, null);
        recycler = (RecyclerView) rootView.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        Bundle bundleArg = getArguments();
        if (bundleArg != null) {
            mList = (ArrayList<SynchronousCourseEntity.NodelistBeanX.NodelistBean>) bundleArg.getSerializable("list");
            if (mList != null && mList.size() > 0) {
                mAdapter = new ClassPurchaseCopyAdapter(getActivity());
                mAdapter.setData(mList);
                recycler.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(this);

            }

        }


        return rootView;
    }


    public void onRequestStart() {

    }

    public void onRequestError(RequestEntity requestEntity) {

    }

    public void onRequestEnd() {

    }


    /**
     * 点击每个展开项
     *
     * @param view
     * @param postion
     * @param myGriayout
     */
    @Override
    public void onItemClick(View view, int postion, ArrayList<SynchronousCourseEntity.NodelistBeanX.NodelistBean.ValueBeanXX.CourseListBean> list, WrapContentGridView myGriayout) {
        if (mAdapter.choosePosition == postion) {
            mAdapter.choosePosition = -1;
        } else {
            mAdapter.choosePosition = postion;
        }
        myGriayout.setAdapter(new GridAdapter(list));
        SynchronousCourseEntity.NodelistBeanX.NodelistBean nodelistBean = mList.get(postion);
        if (nodelistBean != null) {
            SynchronousCourseEntity.NodelistBeanX.NodelistBean.ValueBeanXX valueBeanXX = nodelistBean.getValue();
            if (valueBeanXX != null) {
                productThirdName = valueBeanXX.getProductName();
            }

        }

        mAdapter.notifyDataSetChanged();
    }


    /**
     * gridView的adapter
     */

    public class GridAdapter extends CBaseAdapter<SynchronousCourseEntity.NodelistBeanX.NodelistBean.ValueBeanXX.CourseListBean> {

        class ViewHolder {
            ImageView ivVersion;//书图片
            TextView tvGradeName;//年级名
            TextView tvPrice;//价格
            RelativeLayout llItem;//每一项
            ImageView ivCoupon;//优惠图片

        }

        public GridAdapter(ArrayList<SynchronousCourseEntity.NodelistBeanX.NodelistBean.ValueBeanXX.CourseListBean> list) {
            super((Activity) getContext(), list);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            GridAdapter.ViewHolder holder;
            if (convertView == null) {
                holder = new GridAdapter.ViewHolder();
                convertView = listContainer.inflate(R.layout.item_purchas_version, null);
                holder.ivVersion = (ImageView) convertView.findViewById(R.id.version_iv);
                holder.tvGradeName = (TextView) convertView.findViewById(R.id.tv_grade_name);
                holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
                holder.llItem = (RelativeLayout) convertView.findViewById(R.id.ll_item);
                holder.ivCoupon = (ImageView) convertView.findViewById(R.id.iv_coupon);

                convertView.setTag(holder);
            } else {
                holder = (GridAdapter.ViewHolder) convertView.getTag();
            }
            final SynchronousCourseEntity.NodelistBeanX.NodelistBean.ValueBeanXX.CourseListBean item = listItems.get(position);

            if (item != null) {
                holder.tvGradeName.setText(item.getCourseName());

                if (!TextUtils.isEmpty(item.getCourseImgUrl())) {
                    BitmapUtil.displayImage(item.getCourseImgUrl(), holder.ivVersion, true);
                }

                holder.tvPrice.setText("¥ " + PayConstant.formatPrice(item.getCoursePrice() + ""));

                if (!TextUtils.isEmpty(item.getFavorableStartStr()) && !TextUtils.isEmpty(item.getFavorableEndStr())) {
                    if (compareTime(item.getFavorableStartStr(), item.getFavorableEndStr()))//显示惠字
                    {
                        holder.ivCoupon.setVisibility(View.VISIBLE);
                    } else {
                        holder.ivCoupon.setVisibility(View.GONE);
                    }
                }
            }
            holder.llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();

                    bundle.putString("imageUrl", item.getCourseImgUrl());
                    bundle.putString("className", item.getCourseName());//初中数学这种
                    bundle.putString("versionName", "【" + getArguments().getString("productFirstname") + "-" + getArguments().getString("productSecondName") + "-" + productThirdName + "】");//
                    bundle.putString("price", item.getCoursePrice() + "");
                    bundle.putString("commodityId", item.getCommodityId());
                    bundle.putInt("fromWhere", Constants.ConfirmOrder.synchronous_course);
                    bundle.putString("timeLimit", item.getTimeLimit());
                    bundle.putString("favorableStart", item.getFavorableStartStr());
                    bundle.putString("favorableEnd", item.getFavorableEndStr());
                    openActivity(PayConfrimActivity.class, bundle);

                }
            });

            return convertView;
        }
    }

    /**
     * 比较时间
     */
    private boolean compareTime(String startTime, String endTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
        long endTimeLong = 0;
        long startTimeLong = 0;
        try {
            endTimeLong = simpleDateFormat.parse(endTime).getTime();//当前时间
            startTimeLong = simpleDateFormat.parse(startTime).getTime();//当前时间
            if ((System.currentTimeMillis()) >= startTimeLong && (System.currentTimeMillis() <= endTimeLong))//当前时间>优惠时间 超过优惠时间，不显示惠字
            {
                return true;
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

}
