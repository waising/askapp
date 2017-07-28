package com.asking91.app.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.LabelEntity;
import com.asking91.app.ui.widget.dialog.ChooseDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wxwang on 2016/11/14.
 */
public class ChooseDialogAdapter extends RecyclerView.Adapter<ChooseDialogAdapter.ViewHolder> {

    Context mContext;

    List<LabelEntity> mDatas;

    public void setListener(ChooseDialog.DialogCallBackListener listener) {
        this.listener = listener;
    }

    public ChooseDialog.DialogCallBackListener listener;
    int selectIndex = -1;
    public ChooseDialogAdapter(Context context,List<LabelEntity> datas){
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public ChooseDialogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_dialog_list,parent,false));
    }

    @Override
    public void onBindViewHolder(ChooseDialogAdapter.ViewHolder holder, final int position) {
        final LabelEntity dialogEntity = mDatas.get(position);
        holder.selectBtn.setText(dialogEntity.getName());
        holder.selectIdTv.setText(dialogEntity.getId());

        holder.selectBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    notifyItemChanged(selectIndex);
                    selectIndex = position;
                    listener.callBack(dialogEntity);
                }
            }
        });

        holder.selectBtn.setChecked(selectIndex == position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.select_btn)
        RadioButton selectBtn;

        @BindView(R.id.select_id_tv)
        TextView selectIdTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }
}
