package com.asking91.app.ui.adapter.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.LabelEntity;
import com.asking91.app.ui.mine.more.MineMoreActivity;
import com.asking91.app.ui.mine.more.MineStuRecordActivity;
import com.asking91.app.ui.mine.mybuyrecords.MyBuyRecordsActivity;
import com.asking91.app.ui.mine.myfavor.MyFavorActivity;
import com.asking91.app.ui.mine.mywrongtopic.MyWrongTopicActivity;
import com.asking91.app.ui.onlinetest.OnlineTestActivity;
import com.asking91.app.util.CommonUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wxwang on 2016/10/27.
 */

public class MineAdapter extends RecyclerView.Adapter<MineAdapter.ViewHolder> {

    private List<LabelEntity> mDatas;
    private Context mContext;

    public MineAdapter(Context context, List<LabelEntity> datas){
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_mine_list,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final LabelEntity labelEntity = mDatas.get(position);
        holder.mMenuText.setText(labelEntity.getName());
        holder.mMenuImg.setImageResource(labelEntity.getIcon());

        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.openAuthActivity(mContext, labelEntity.getContext());
//                switch (labelEntity.getIcon()){
//                    // 购买记录
//                    case R.mipmap.mine_shopping_record:
//                        CommonUtil.openAuthActivity(mContext, MyBuyRecordsActivity.class);
//                        break;
//                    // 在线检测
//                    case R.mipmap.mine_online_test:
//                        CommonUtil.openAuthActivity(mContext,OnlineTestActivity.class);
//                        break;
//                    // 我的收藏
//                    case R.mipmap.mine_collect:
//                        CommonUtil.openAuthActivity(mContext, MyFavorActivity.class);
//                        break;
//                    // 学习足迹
//                    case R.mipmap.mine_stu_record:
//                        CommonUtil.openAuthActivity(mContext, MineStuRecordActivity.class);
//                        break;
//                    case R.mipmap.mine_error_note:
//                        CommonUtil.openAuthActivity(mContext, MyWrongTopicActivity.class);
//                        break;
//                    case R.mipmap.mine_more:
//                        CommonUtil.openAuthActivity(mContext, MineMoreActivity.class);
//                        break;
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.menu_img)
        ImageView mMenuImg;
        @BindView(R.id.menu_text)
        TextView mMenuText;
        @BindView(R.id.menu_item_ry)
        RelativeLayout mRelativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }

}
