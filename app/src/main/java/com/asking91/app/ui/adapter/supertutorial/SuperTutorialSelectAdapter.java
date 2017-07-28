package com.asking91.app.ui.adapter.supertutorial;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.pay.SynchronousCourseEntity;
import com.asking91.app.ui.widget.camera.utils.AppEventType;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


/**
 * Created by wxwang on 2016/10/27.
 */

public class SuperTutorialSelectAdapter extends RecyclerView.Adapter<SuperTutorialSelectAdapter.ViewHolder> {




    private ArrayList<SynchronousCourseEntity.NodelistBeanX.NodelistBean.ValueBeanXX.CourseListBean> versionDataList;

    public void setData(ArrayList<SynchronousCourseEntity.NodelistBeanX.NodelistBean.ValueBeanXX.CourseListBean> versionDataList) {
        this.versionDataList = versionDataList;

    }


    private Context mContext;

    public SuperTutorialSelectAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_supertutorial_classify_grade, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final SynchronousCourseEntity.NodelistBeanX.NodelistBean.ValueBeanXX.CourseListBean item = versionDataList.get(position);
        if (item != null) {
            holder.item_name.setText(item.getCourseName());
            holder.ll_main.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new AppEventType(AppEventType.SUPER_CLASSIFY_SELECT, versionDataList.get(position)));
                }
            });


        }

    }

    @Override
    public int getItemCount() {
        return versionDataList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_name)
        TextView item_name;
        @BindView(R.id.ll_main)
        LinearLayout ll_main;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }

}






