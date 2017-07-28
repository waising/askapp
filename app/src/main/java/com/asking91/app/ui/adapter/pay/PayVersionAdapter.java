package com.asking91.app.ui.adapter.pay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.basepacket.CourseEntity;
import com.asking91.app.global.PayConstant;
import com.asking91.app.ui.pay.PaySupActivity;

import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 课程教材adapter
 * Created by wxwang on 2016/10/27.
 */

public class PayVersionAdapter extends RecyclerView.Adapter<PayVersionAdapter.ViewHolder> {

    private List<CourseEntity> mDatas;
    private Context mContext;

    public void setPrePosition(int mPrePosition) {
        this.mPrePosition = mPrePosition;
    }

    private int mPrePosition = -1;

    public PayVersionAdapter(Context context, List<CourseEntity> datas){
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_pay_version_list,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final CourseEntity courseEntity = mDatas.get(position);
        try {
            String imageName = PayConstant.getVersionImages(courseEntity.getSubjectImgKey());
            if (!TextUtils.isEmpty(imageName)) {
                InputStream in;
                in = mContext.getAssets().open("images/km/" + imageName);
                Bitmap bmp = BitmapFactory.decodeStream(in);
                holder.mVersionIv.setImageBitmap(bmp);
            }
        } catch (Exception e) {
            holder.mVersionIv.setImageResource(R.mipmap.class_img);
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            holder.mVersionIv.setImageResource(R.mipmap.class_img);
            e.printStackTrace();
        }

        holder.mVersionTv.setText(courseEntity.getVersionName());//版本名
        holder.versionId = courseEntity.getVersionId();
        holder.position = position;

        if (position==0 && mPrePosition==-1){
            holder.mClassLy.setBackgroundResource(R.mipmap.pay_jc_border_bg);
        }else{
            holder.mClassLy.setBackgroundResource(0);
        }

    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.version_iv)
        ImageView mVersionIv;
        @BindView(R.id.version_tv)
        TextView mVersionTv;
        @BindView(R.id.class_ly)
        LinearLayout mClassLy;

        public int versionId;

        int position;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick({R.id.class_ly,R.id.version_tv,R.id.version_iv})
        public void onClick(View view){//课程教材点击选中事件
            if (mPrePosition==-1) mPrePosition=0;
            if(mPrePosition!=position)
                notifyItemChanged(mPrePosition);

            mClassLy.setBackgroundResource(R.mipmap.pay_jc_border_bg);
            ((PaySupActivity)mContext).setVersionId(versionId);

            mPrePosition = position;
        }
    }
}
