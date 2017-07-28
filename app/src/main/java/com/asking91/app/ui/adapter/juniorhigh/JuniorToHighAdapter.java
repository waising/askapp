package com.asking91.app.ui.adapter.juniorhigh;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.juniorTohigh.JuniorToHighEntity;
import com.asking91.app.global.PayConstant;
import com.asking91.app.ui.juniorhigh.JuniorToHighDetailActivity;
import com.asking91.app.ui.juniorhigh.JuniorToHighFragment;
import com.asking91.app.ui.mycourse.MyCourseUtil;
import com.asking91.app.ui.widget.camera.utils.BitmapUtil;

import java.io.Serializable;
import java.util.ArrayList;

import static com.asking91.app.util.CommonUtil.openActivity;

/**
 * 初升高adapter
 */
public class JuniorToHighAdapter extends RecyclerView.Adapter<JuniorToHighAdapter.MyHolder> {


    /**
     * 头部布局
     */
    public static final int TYPE_HEADER = 0;
    /**
     * 尾部布局
     */
    public static final int TYPE_FOOTER = 1;

    /**
     * 正常布局
     */
    public static final int TYPE_NORMAL = 2;

    private ArrayList<JuniorToHighEntity> mList = new ArrayList<>();


    /**
     * headerView
     */
    private View mHeaderView;

    /**
     * footerView
     */
    private View mfooterView;

    private Context mContext;


    /**
     * 是否购买过全套
     */
    public boolean isHasBuy=false;


    public JuniorToHighAdapter(Context context) {
        mContext = context;
    }



    @Override
    public int getItemCount() {
        if (mHeaderView == null && mfooterView == null) {//如果没有headView和footerView,返回list的长度
            return mList.size();
        } else if (mHeaderView == null && mfooterView != null) {//如果只有footerView,返回list的长度+1
            return mList.size() + 1;
        } else if (mHeaderView != null && mfooterView == null) {//如果只有headView,返回list的长度+1
            return mList.size() + 1;
        } else {//其他情况,返回list的长度+2，即有headView和footerView的情况下
            return mList.size() + 2;
        }
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        protected TextView tv_class_name;//课程名
        protected TextView tv_class_price;//课程价格
        protected TextView tv_learn_percent;
        RelativeLayout RelItem;//item布局
        protected TextView tv_buy_people_num;//购买人数
        protected TextView tv_class;//科目
        protected View view_line;
        protected LinearLayout ll_bottom;

        /**
         * 老师头像
         */
        protected ImageView ivTeacher;

        /**
         * 教师名字
         */
        protected TextView tvTeacherNickName;


        private MyHolder(View v) {
            super(v);
            if (v == mHeaderView) return;
            if (v == mfooterView) return;
            this.tv_class_name = (TextView) v.findViewById(R.id.tv_class_name);
            this.tv_class_price = (TextView) v.findViewById(R.id.tv_class_price);
            this.RelItem = (RelativeLayout) v.findViewById(R.id.item_layout);
            this.tv_learn_percent = (TextView) v.findViewById(R.id.tv_learn_percent);
            this.tv_buy_people_num = (TextView) v.findViewById(R.id.tv_buy_people_num);
            this.tv_class = (TextView) v.findViewById(R.id.tv_class);
            this.view_line = v.findViewById(R.id.view_line);
            this.ll_bottom = (LinearLayout) v.findViewById(R.id.ll_bottom);
            this.ivTeacher = (ImageView) v.findViewById(R.id.iv_teacher);
            this.tvTeacherNickName = (TextView) v.findViewById(R.id.tv_teacher_nick_name);
        }
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        if (mHeaderView != null && i == TYPE_HEADER) {//如果是headerView布局，则返回头部布局
            return new MyHolder(mHeaderView);
        }
        if (mfooterView != null && i == TYPE_FOOTER) {//如果是footerView布局，则返回尾部布局
            return new MyHolder(mfooterView);
        }
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_junior_to_high_class, null);//默认返回item布局
        return new MyHolder(v);
    }

    /**
     * 绑定数据
     *
     * @param myHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(MyHolder myHolder, int i) {

        if (getItemViewType(i) == TYPE_HEADER) {//如果是头部布局
            return;
        }

        if (getItemViewType(i) == TYPE_FOOTER) {//如果是尾部布局
            return;
        }
        final int pos = getRealPositon(myHolder);
        final JuniorToHighEntity item = mList.get(pos);

        if (item != null) {
            myHolder.tv_class_name.setText(item.getCourseName());
            if (!TextUtils.isEmpty(item.getCourseTypeName())) {
                myHolder.tv_class.setText(item.getCourseTypeName().substring(0, 1));
            }

            String progress = "";
            BitmapUtil.displayUserImage(mContext,item.getTeacherImgUrl(),  myHolder.ivTeacher);
            myHolder.tvTeacherNickName.setText(item.getTeacherNickName()+"老师");
            myHolder.view_line.setVisibility(View.VISIBLE);
            myHolder.ll_bottom.setVisibility(View.VISIBLE);
            myHolder.tv_class_price.setVisibility(View.VISIBLE);
            myHolder.tv_buy_people_num.setText(mContext.getString(R.string.purchased_num, String.valueOf(item.getPurchasedNum()+item.getBuyNumber())));
            myHolder.tv_class_price.setText("¥"+PayConstant.formatPrice(item.getCoursePrice()));
            if (item.getPurchaseState() == 0)//未购买
            {
                myHolder.tv_learn_percent.setVisibility(View.GONE);
            } else if (item.getPurchaseState() == 1)//已购买
            {
                myHolder.tv_learn_percent.setVisibility(View.VISIBLE);
                progress = MyCourseUtil.findProgress(item.getCommodityId(), mContext);

                if (!TextUtils.isEmpty(progress)) {
                    myHolder.tv_learn_percent.setText("已学习" + progress + "%");
                } else {
                    myHolder.tv_learn_percent.setText("马上去学习");
                }
            }


            final String finalProgress = progress;
            myHolder.RelItem.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title", item.getCourseName());//标题
                    bundle.putInt("purchaseState", item.getPurchaseState());//购买状态
                    bundle.putString("detail", item.getDetail());//课程详情
                    bundle.putString("price", item.getCoursePrice());//课程价格
                    bundle.putString("targetUser", item.getTargetUser());//适用人群
                    bundle.putInt("purchasedNum", item.getPurchasedNum()+item.getBuyNumber());//购买人数
                    bundle.putString("freeTime", item.getFreeTime());//免费试看时间
                    bundle.putString("progress", finalProgress);//id
                    bundle.putString("courseTypeName", item.getCourseTypeName());//id
                    bundle.putString("courseTypeFullName", item.getCourseTypeFullName());//
                    bundle.putInt("listPositon", pos);//list中的position
                    bundle.putString("ivTeacher", item.getTeacherImgUrl());
                    bundle.putString("tvTeacher", item.getTeacherNickName());
                    bundle.putString("commodityId", item.getCommodityId());
                    bundle.putSerializable("urlAndPdf", (Serializable) item.getCourseDataArray());//pdf和url
                    openActivity(JuniorToHighDetailActivity.class, bundle);
                }
            });


        }


    }


    /**
     * 添加headerView
     *
     * @param headView
     */
    public void setHeaderView(View headView) {
        mHeaderView = headView;
        notifyItemInserted(0);//刷新0position的view
    }

    /**
     * 添加footerView
     *
     * @param footerView
     */
    public void setFooterView(View footerView) {
        mfooterView = footerView;
        notifyItemInserted(getItemCount() - 1);//刷新最后一个position的view，即footerView的位置
    }


    /**
     * 获取headerView
     *
     * @return
     */
    public View getHeaderView() {
        return mHeaderView;
    }


    /**
     * 获取footerView
     *
     * @return
     */
    public View getFooterView() {
        return mfooterView;
    }

    /**
     * 获取真实的position位置
     *
     * @return
     */
    public int getRealPositon(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;//如果header为空，则
    }

    public void addDatas(ArrayList<JuniorToHighEntity> datas, int loadType) {
        if (loadType == JuniorToHighFragment.REFRESH||loadType==JuniorToHighFragment.FIRST_LOAD) {//下拉刷新
            mList.clear();
            mList.addAll(datas);
        } else {//上拉加载更多
            mList.addAll(datas);
        }

        notifyDataSetChanged();
    }


    public JuniorToHighEntity getItemData(int position) {
        return mList.get(position);
    }


    /**
     * 获取View类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mfooterView == null) {//如果heardView为空，并且footerView为空，则返回item布局
            return TYPE_NORMAL;
        } else if (mHeaderView != null && mfooterView == null) {//有头部，没尾部
            if (position == 0) {//如果是第一项，返回header布局
                return TYPE_HEADER;
            } else {
                return TYPE_NORMAL;
            }

        } else if (mHeaderView == null && mfooterView != null) {//没头部，有尾部

            if (position == getItemCount() - 1) {//如果是最后一项，返回footer布局
                //最后一个,应该加载Footer
                return TYPE_FOOTER;
            } else {
                return TYPE_NORMAL;
            }
        } else {//头尾都有
            if (position == 0) {//如果是第一项，返回header布局
                return TYPE_HEADER;
            }
            if (position == getItemCount() - 1) {//如果是最后一项，返回footer布局
                //最后一个,应该加载Footer
                return TYPE_FOOTER;
            }
        }
        return TYPE_NORMAL;
    }
}
