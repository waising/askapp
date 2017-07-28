package com.asking91.app.ui.adapter.mycourse;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.mycourse.MyCourse;
import com.asking91.app.global.Constants;
import com.asking91.app.ui.mycourse.MyCourseClickListner;
import com.asking91.app.ui.mycourse.MyCourseUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的课程的adapter
 */
public class MyCourseDetailAdapter extends RecyclerView.Adapter<MyCourseDetailAdapter.MyHolder> {


    /**
     * 头部布局
     */
    public static final int TYPE_HEADER = 0;

    /**
     * 正常布局
     */
    public static final int TYPE_NORMAL = 1;

    private ArrayList<MyCourse.ChildCommodityListBean> list = new ArrayList<>();


    /**
     * headerView
     */
    private View mHeaderView;


    private Context mContext;


    public MyCourseDetailAdapter(Context context) {
        mContext = context;
    }


    @Override
    public int getItemCount() {
        return mHeaderView == null ? list.size() : list.size() + 1;
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_single_name)
        TextView tvSingleName;
        @BindView(R.id.tv_class)
        TextView tvClass;
        @BindView(R.id.tv_finish)
        TextView tvFinish;
        @BindView(R.id.tv_learn_section)
        TextView tvLearnSection;
        @BindView(R.id.tv_learn_lecture)
        TextView tvLearnLecture;
        @BindView(R.id.tv_learn)
        TextView tvLearn;
        @BindView(R.id.tv_learn_percent)
        TextView tvLearnPercent;

        @BindView(R.id.tv_start_learn)
        TextView tvStartLearn;
        @BindView(R.id.tv_single_time)
        TextView tvSingleTime;

        @BindView(R.id.view_line)
        View viewLine;

        @BindView(R.id.rel_main)
        RelativeLayout relMain;


        private MyHolder(View v) {
            super(v);
            if (v == mHeaderView) return;
            ButterKnife.bind(this, v);


        }
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        if (mHeaderView != null && i == TYPE_HEADER) {//如果是headerView布局，则返回头部布局
            return new MyHolder(mHeaderView);
        }
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_single_course, null);//默认返回item布局
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
        final int pos = getRealPositon(myHolder);
        final MyCourse.ChildCommodityListBean item = list.get(pos);
        if (item != null) {
            myHolder.tvSingleName.setText(item.getCommodityName());
            myHolder.tvClass.setText(item.getCommodityTypeName());
            myHolder.tvSingleTime.setText(item.getCreateTime().substring(0, 10));
            String[] str = item.getCommodityId().split("-");
            if (str.length > 1) {
                String courseType = str[1];
                if (courseType.equals("KC01")) {//初升高衔接课
                    final String progress = MyCourseUtil.findProgress(item.getCommodityId(), mContext);
                    if (item.getIsPresent() == 1) {//赠品
                        myHolder.tvClass.setVisibility(View.INVISIBLE);
                        myHolder.tvFinish.setVisibility(View.GONE);
                        myHolder.viewLine.setVisibility(View.GONE);
                        myHolder.tvStartLearn.setVisibility(View.GONE);
                        myHolder.tvLearnPercent.setVisibility(View.GONE);
                        myHolder.tvLearnSection.setVisibility(View.GONE);
                        myHolder.tvLearnLecture.setVisibility(View.GONE);
                        myHolder.tvLearn.setVisibility(View.GONE);

                    } else {//非赠品
                        if (!TextUtils.isEmpty(progress)) {//学习进度
                            if (progress.equals("100")) {//完成学习
                                myHolder.tvFinish.setVisibility(View.VISIBLE);
                                myHolder.tvLearnSection.setVisibility(View.GONE);
                                myHolder.tvLearnLecture.setVisibility(View.GONE);
                                myHolder.tvLearn.setVisibility(View.GONE);
                                myHolder.tvLearnPercent.setVisibility(View.GONE);
                                myHolder.tvStartLearn.setVisibility(View.GONE);
                            } else {//学习了多少
                                myHolder.tvFinish.setVisibility(View.GONE);
                                myHolder.tvLearnSection.setVisibility(View.GONE);
                                myHolder.tvLearnLecture.setVisibility(View.GONE);
                                myHolder.tvLearn.setVisibility(View.VISIBLE);
                                myHolder.tvLearnPercent.setVisibility(View.VISIBLE);
                                myHolder.tvStartLearn.setVisibility(View.GONE);
                                myHolder.tvLearnPercent.setText(progress + "%");
                            }
                        } else {//马上去学习
                            myHolder.tvFinish.setVisibility(View.GONE);
                            myHolder.tvLearnSection.setVisibility(View.GONE);
                            myHolder.tvLearnLecture.setVisibility(View.GONE);
                            myHolder.tvLearn.setVisibility(View.GONE);
                            myHolder.tvLearnPercent.setVisibility(View.GONE);
                            myHolder.tvStartLearn.setVisibility(View.VISIBLE);
                        }


                    }
                    myHolder.relMain.setOnClickListener(new View.OnClickListener() {


                        @Override
                        public void onClick(View v) {
                            if (mClickListener != null) {
                                if (item.getIsPresent() == 1)//赠品
                                {
                                    mClickListener.click(Constants.JuniorToHighDetail.present, null, pos, item.getCommodityId());
                                } else {
                                    mClickListener.click(Constants.JuniorToHighDetail.puchased, progress, pos, item.getCommodityId());
                                }
                            }

                        }
                    });


                } else if (courseType.equals("KC03"))//同步课堂
                {

                    if ((TextUtils.isEmpty(splitSection("section", item.getScheduleTitle()))) && (TextUtils.isEmpty(splitSection("lecture", item.getScheduleTitle())))) {
                        myHolder.tvStartLearn.setVisibility(View.VISIBLE);
                        myHolder.tvFinish.setVisibility(View.GONE);
                        myHolder.tvLearnPercent.setVisibility(View.GONE);
                        myHolder.tvLearnSection.setVisibility(View.GONE);
                        myHolder.tvLearnLecture.setVisibility(View.GONE);
                        myHolder.tvLearn.setVisibility(View.GONE);
                    } else {
                        myHolder.tvStartLearn.setVisibility(View.GONE);
                        myHolder.tvFinish.setVisibility(View.GONE);
                        myHolder.tvLearnPercent.setVisibility(View.GONE);
                        myHolder.tvLearnSection.setVisibility(View.VISIBLE);
                        myHolder.tvLearnLecture.setVisibility(View.VISIBLE);
                        myHolder.tvLearn.setVisibility(View.VISIBLE);
                        myHolder.tvLearnSection.setText(splitSection("section", item.getScheduleTitle()));
                        myHolder.tvLearnLecture.setText(splitSection("lecture", item.getScheduleTitle()));
                    }


                }

            }

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
     * 获取headerView
     *
     * @return
     */
    public View getHeaderView() {
        return mHeaderView;
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

    public void addDatas(ArrayList<MyCourse.ChildCommodityListBean> datas) {
        list.clear();
        list.addAll(datas);
        notifyDataSetChanged();
    }


    public MyCourse.ChildCommodityListBean getItemData(int position) {
        return list.get(position);
    }


    /**
     * 获取View类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
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
