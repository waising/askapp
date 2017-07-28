package com.asking91.app.ui.adapter.mycourse;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.mycourse.MyCourse;
import com.asking91.app.global.Constants;
import com.asking91.app.ui.mycourse.MyCourseClickListner;
import com.asking91.app.ui.mycourse.MyCourseDetailActivity;
import com.asking91.app.ui.mycourse.MyCourseUtil;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.asking91.app.R.id.tv_learn;
import static com.asking91.app.R.id.tv_learn_section;
import static com.asking91.app.util.CommonUtil.openActivity;


/**
 * 我的课程adapter
 * Created by zqshen on 2016/11/24.
 */

public class MyCourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    /**
     * 布局类型
     */
    private static final int ITEM_TYPE_SINGLE_COURSE = 0;//单课课程
    private static final int ITEM_TYPE_ALL_COURSE = 1;//全套课程


    private Context mContext;
    List<MyCourse> list;

    public MyCourseAdapter(Context context, List<MyCourse> list) {
        this.mContext = context;
        this.list = list;
    }

    public void setData(List<MyCourse> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 布局类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (!TextUtils.isEmpty(list.get(position).getCommodityType())) {
            if (list.get(position).getCommodityType().equals("TC")) {//全套
                return ITEM_TYPE_ALL_COURSE;
            } else if (list.get(position).getCommodityType().equals("KC")) {//单套课程
                return ITEM_TYPE_SINGLE_COURSE;
            }

        }
        return ITEM_TYPE_SINGLE_COURSE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_TYPE_SINGLE_COURSE) {
            return new SingleCourseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_single_course, parent, false));
        } else {
            return new AllCourseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_all_course, parent, false));
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (list != null && list.size() > 0) {
            final MyCourse myCourse = list.get(position);
            if (myCourse != null) {
                switch (getItemViewType(position)) {
                    case ITEM_TYPE_SINGLE_COURSE://单套课程
                        SingleCourseViewHolder singleCourseViewHolder = (SingleCourseViewHolder) holder;
                        singleCourseViewHolder.tvSingleName.setText(myCourse.getCommodityName());

                        singleCourseViewHolder.tvSingleTime.setText(myCourse.getCreateTime().substring(0, 10));
                        String[] str = myCourse.getCommodityId().split("-");
                        if (str.length > 1) {
                            String courseType = str[1];
                            singleCourseViewHolder.tvClass.setText(myCourse.getCommodityTypeName());
                            if (courseType.equals("KC01")) {//初升高衔接课
                                final String progress = MyCourseUtil.findProgress(myCourse.getCommodityId(), mContext);
                                if (!TextUtils.isEmpty(progress)) {//学习进度
                                    if (progress.equals("100")) {//完成学习
                                        singleCourseViewHolder.tvFinish.setVisibility(View.VISIBLE);
                                        singleCourseViewHolder.tvLearnSection.setVisibility(View.GONE);
                                        singleCourseViewHolder.tvLearnLecture.setVisibility(View.GONE);
                                        singleCourseViewHolder.tvLearn.setVisibility(View.GONE);
                                        singleCourseViewHolder.tvLearnPercent.setVisibility(View.GONE);
                                        singleCourseViewHolder.tvStartLearn.setVisibility(View.GONE);
                                    } else {//学习了多少
                                        singleCourseViewHolder.tvFinish.setVisibility(View.GONE);
                                        singleCourseViewHolder.tvLearnSection.setVisibility(View.GONE);
                                        singleCourseViewHolder.tvLearnLecture.setVisibility(View.GONE);
                                        singleCourseViewHolder.tvLearn.setVisibility(View.VISIBLE);
                                        singleCourseViewHolder.tvLearnPercent.setVisibility(View.VISIBLE);
                                        singleCourseViewHolder.tvStartLearn.setVisibility(View.GONE);
                                        singleCourseViewHolder.tvLearnPercent.setText(progress + "%");
                                    }
                                } else {//马上去学习
                                    singleCourseViewHolder.tvFinish.setVisibility(View.GONE);
                                    singleCourseViewHolder.tvLearnSection.setVisibility(View.GONE);
                                    singleCourseViewHolder.tvLearnLecture.setVisibility(View.GONE);
                                    singleCourseViewHolder.tvLearn.setVisibility(View.GONE);
                                    singleCourseViewHolder.tvLearnPercent.setVisibility(View.GONE);
                                    singleCourseViewHolder.tvStartLearn.setVisibility(View.VISIBLE);
                                }

                                singleCourseViewHolder.relMain.setOnClickListener(new View.OnClickListener() {


                                    @Override
                                    public void onClick(View v) {
                                        if (mClickListener != null) {
                                            mClickListener.click(Constants.JuniorToHighDetail.puchased, progress, position, myCourse.getCommodityId());
                                        }
                                    }
                                });


                            } else if (courseType.equals("KC03"))//同步课堂
                            {

                                if ((TextUtils.isEmpty(splitSection("section", myCourse.getScheduleTitle()))) && (TextUtils.isEmpty(splitSection("lecture", myCourse.getScheduleTitle())))) {
                                    singleCourseViewHolder.tvStartLearn.setVisibility(View.VISIBLE);
                                    singleCourseViewHolder.tvFinish.setVisibility(View.GONE);
                                    singleCourseViewHolder.tvLearnPercent.setVisibility(View.GONE);
                                    singleCourseViewHolder.tvLearnSection.setVisibility(View.GONE);
                                    singleCourseViewHolder.tvLearnLecture.setVisibility(View.GONE);
                                    singleCourseViewHolder.tvLearn.setVisibility(View.GONE);
                                } else {
                                    singleCourseViewHolder.tvStartLearn.setVisibility(View.GONE);
                                    singleCourseViewHolder.tvFinish.setVisibility(View.GONE);
                                    singleCourseViewHolder.tvLearnPercent.setVisibility(View.GONE);
                                    singleCourseViewHolder.tvLearnSection.setVisibility(View.VISIBLE);
                                    singleCourseViewHolder.tvLearnLecture.setVisibility(View.VISIBLE);
                                    singleCourseViewHolder.tvLearn.setVisibility(View.VISIBLE);
                                    singleCourseViewHolder.tvLearnSection.setText(splitSection("section", myCourse.getScheduleTitle()));
                                    singleCourseViewHolder.tvLearnLecture.setText(splitSection("lecture", myCourse.getScheduleTitle()));
                                }

                            }

                        }
                        break;
                    case ITEM_TYPE_ALL_COURSE://全套课程
                        AllCourseViewHolder allCourseViewHolder = (AllCourseViewHolder) holder;
                        allCourseViewHolder.tvAllName.setText(myCourse.getCommodityName());
                        allCourseViewHolder.tvAllTime.setText(myCourse.getCreateTime().substring(0, 10));
                        allCourseViewHolder.llExpand.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("list", (Serializable) myCourse.getChildCommodityList());
                                bundle.putString("allTitle", myCourse.getCommodityName());
                                if (!TextUtils.isEmpty(myCourse.getCreateTime())) {
                                    bundle.putString("allTime", myCourse.getCreateTime().substring(0, 10));
                                }
                                openActivity(MyCourseDetailActivity.class, bundle);
                            }
                        });


                        break;

                }

            }


        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 单课
     */
    class SingleCourseViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_single_name)
        TextView tvSingleName;
        @BindView(R.id.tv_class)
        TextView tvClass;
        @BindView(R.id.tv_finish)
        TextView tvFinish;
        @BindView(tv_learn_section)
        TextView tvLearnSection;
        @BindView(R.id.tv_learn_lecture)
        TextView tvLearnLecture;
        @BindView(tv_learn)
        TextView tvLearn;
        @BindView(R.id.tv_learn_percent)
        TextView tvLearnPercent;

        @BindView(R.id.tv_start_learn)
        TextView tvStartLearn;
        @BindView(R.id.tv_single_time)
        TextView tvSingleTime;

        @BindView(R.id.rel_main)
        RelativeLayout relMain;


        public SingleCourseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    /**
     * 全套
     */
    class AllCourseViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_all_name)
        TextView tvAllName;
        @BindView(R.id.ll_expand)
        LinearLayout llExpand;
        @BindView(R.id.tv_all_time)
        TextView tvAllTime;


        public AllCourseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    private MyCourseClickListner mClickListener;

    public void setClickListner(MyCourseClickListner onClilistner) {
        this.mClickListener = onClilistner;
    }

    /**
     * 分割章节
     *
     * @param type
     * @param content
     */
    private String splitSection(String type, String content) {
        String[] contentSplit = content.split("\n");
        if (contentSplit.length == 2) {
            if (type.equals("section")) {
                return contentSplit[0];
            } else {
                return contentSplit[1];
            }
        }
        return "";
    }

}
