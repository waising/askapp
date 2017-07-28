package com.asking91.app.ui.adapter.pay;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.basepacket.CourseEntity;
import com.asking91.app.ui.pay.PayServerActivity;
import com.asking91.app.ui.pay.PaySupActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wxwang on 2016/10/27.
 */

public class PayServerVersionAdapter extends RecyclerView.Adapter<PayServerVersionAdapter.ViewHolder> {

    private List<CourseEntity> mDatas;
    private Context mContext;

    public void setPrePosition(int mPrePosition) {
        this.mPrePosition = mPrePosition;
    }

    private int mPrePosition = -1;
    private RadioButton preBtn;

    public PayServerVersionAdapter(Context context, List<CourseEntity> datas){
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_pay_server_version_list,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final CourseEntity courseEntity = mDatas.get(position);

        holder.mVersionBtn.setText(courseEntity.getVersionName());
        holder.versionId = courseEntity.getVersionId();
        holder.position = position;

        if(mPrePosition == -1) {
            holder.mVersionBtn.setChecked(position == 0);
            if(position==0)
                preBtn = holder.mVersionBtn;
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.server_version_btn)
        RadioButton mVersionBtn;

        public int versionId;

        int position;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick(R.id.server_version_btn)
        public void onClick(View view){
            if(mPrePosition==-1)mPrePosition=0;
            if(mPrePosition!=position) {
                notifyItemChanged(mPrePosition);
                preBtn.setChecked(false);
            }

            mVersionBtn.setChecked(true);

            mPrePosition = position;
            preBtn = mVersionBtn;
        }
    }
}
