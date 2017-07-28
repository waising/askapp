package com.asking91.app.ui.adapter.basepacket;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asking91.app.R;
import com.asking91.app.entity.basepacket.BasePacketEntity;
import com.asking91.app.entity.basepacket.SubjectCacaLogEntity;
import com.asking91.app.ui.basepacket.choose.ChooseActivity;
import com.asking91.app.ui.mine.MineSchoolInfoActivity;
import com.asking91.app.ui.widget.AskSimpleDraweeView;
import com.asking91.app.util.CommonUtil;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wxwang on 2016/10/27.
 */

public class BasePacketAdapter extends RecyclerView.Adapter<BasePacketAdapter.ViewHolder> {

    private List<SubjectCacaLogEntity> mDatas;
    private Context mContext;

    public void setUserDataPerfect(boolean userDataPerfect) {
        isUserDataPerfect = userDataPerfect;
    }

    private boolean isUserDataPerfect = false;

    public void setMap(Map<String, BasePacketEntity> map) {
        this.map = map;
    }

    Map<String ,BasePacketEntity> map;
    public BasePacketAdapter(Context context, List<SubjectCacaLogEntity> datas){
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_basepacket_list,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SubjectCacaLogEntity item = mDatas.get(position);
//        InputStream in= null;
//        try {

//            in = mContext.getAssets().open(item.getImgurl());
//            Bitmap bmp= BitmapFactory.decodeStream(in);
//            holder.mCyclerImg.setImageBitmap(bmp);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        holder.mCyclerImg.setImageResource(map.get(item.getSubjectCatalogCode()).getImgurl());
        //图片点击事件
        holder.mCyclerImg.setOnClickListener(new CyclerImgOnClick(map.get(item.getSubjectCatalogCode()).getType()));
    }

    @Override
    public int getItemCount() {
        return mDatas ==null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.classify_cycler_img)
        AskSimpleDraweeView mCyclerImg;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class CyclerImgOnClick implements View.OnClickListener{
        String type;

        public CyclerImgOnClick(String type){
            this.type = type;
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString("type",type);

            //如果资料已经完善直接跳
          //if(isUserDataPerfect) {
                 CommonUtil.openActivity(mContext, ChooseActivity.class,bundle);
//            }else{
//                bundle.putString("openCameraActivity", ChooseActivity.class.getName());
//                CommonUtil.openCameraActivity(mContext, MineSchoolInfoActivity.class, bundle);
//            }
        }
    }
}
