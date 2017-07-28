package com.asking91.app.ui.adapter.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.user.UserEntity;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by zqshen on 2016/11/24.
 */

public class SelectAddressAdapter extends RecyclerView.Adapter<SelectAddressAdapter.MessageViewHolder> {

    private Context mContext;
    List<UserEntity> userEntity;

    public SelectAddressAdapter(Context context, List<UserEntity> userEntity) {
        this.mContext = context;
        this.userEntity = userEntity;
    }

    public void setData(List<UserEntity> userEntity) {
        this.userEntity = userEntity;
        notifyDataSetChanged();
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_address, parent, false));
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder holder, final int position) {
        final String regionName = userEntity.get(position).getRegionName();
        final String regionCode = userEntity.get(position).getRegionCode();
        if (!TextUtils.isEmpty(regionName)) {
            holder.itemName.setText(regionName);
        }
        holder.itemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemName.setTextColor(holder.colorBlue);
                clickItem.setClickItem(regionCode, regionName);
            }
        });
    }

    ClickItem clickItem;

    public void getClickItem(ClickItem clickItem) {
        this.clickItem = clickItem;
    }

    public interface ClickItem {
        void setClickItem(String regionCode, String regionName);
    }

    @Override
    public int getItemCount() {
        return userEntity.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_name)
        TextView itemName;
        @BindColor(R.color.login_gomain)
        int colorBlue;

        public MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
