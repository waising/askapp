package com.asking91.app.ui.mine.more;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.common.SwipeBackActivity;
import com.asking91.app.entity.LabelEntity;
import com.asking91.app.entity.basepacket.BasePacketEntity;
import com.asking91.app.ui.mine.mynote.MyNoteActivity;
import com.asking91.app.ui.widget.DividerGridItemDecoration;
import com.asking91.app.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jswang on 2017/3/20.
 */

public class MineMoreActivity  extends SwipeBackActivity {
    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    @BindView(R.id.mine_rv)
    RecyclerView mineRv;

    List<LabelEntity> mDatas;
    CommAdapter mineAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_more);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        mDatas = new ArrayList<>();
        mDatas.add(new LabelEntity(R.mipmap.mine_note, getString(R.string.my_note)));
        mDatas.add(new LabelEntity(R.mipmap.mine_oto_record, getString(R.string.myOtoRecord)));
        mDatas.add(new LabelEntity(R.mipmap.mine_test_record, getString(R.string.test_record)));
        mDatas.add(new LabelEntity(R.mipmap.mine_recommend, getString(R.string.recommend_reward)));
        mDatas.add(new LabelEntity(R.mipmap.mine_faceback, getString(R.string.myFeedBack)));

        mineAdapter = new CommAdapter(this, mDatas);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolbar, getString(R.string.myMore));

        GridLayoutManager mgr = new GridLayoutManager(this, 3);
        mineRv.setLayoutManager(mgr);
        //设置分割线
        DividerGridItemDecoration l = new DividerGridItemDecoration(this);
        l.setDrawable(R.drawable.divider_bg);
        mineRv.addItemDecoration(l);
        mineRv.setAdapter(mineAdapter);
    }

    class CommViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.menu_img)
        ImageView mMenuImg;
        @BindView(R.id.menu_text)
        TextView mMenuText;
        @BindView(R.id.menu_item_ry)
        RelativeLayout mRelativeLayout;
        public CommViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class CommAdapter extends RecyclerView.Adapter<CommViewHolder> {

        private List<LabelEntity> mDatas;
        private Context mContext;

        Map<String ,BasePacketEntity> map;
        public CommAdapter(Context context, List<LabelEntity> datas){
            this.mContext = context;
            this.mDatas = datas;
        }

        @Override
        public CommViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CommViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_mine_list,parent,false));
        }

        @Override
        public void onBindViewHolder(CommViewHolder holder, int position) {
            final LabelEntity labelEntity = mDatas.get(position);
            holder.mMenuText.setText(labelEntity.getName());
            holder.mMenuImg.setImageResource(labelEntity.getIcon());

            holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (labelEntity.getIcon()){
                        // 我的笔记
                        case R.mipmap.mine_note:
                            CommonUtil.openAuthActivity(mContext, MyNoteActivity.class);
                            break;
                        case R.mipmap.mine_test_record:
                            CommonUtil.openAuthActivity(mContext, MineStuRecordActivity.class);
                            break;
                        case R.mipmap.mine_recommend:
                            CommonUtil.openAuthActivity(mContext, ShareAwardActivity.class);
                            break;
                        case R.mipmap.mine_faceback:
                            CommonUtil.openAuthActivity(mContext, FeedBackActivity.class);
                            break;
                        case R.mipmap.mine_oto_record:
                            CommonUtil.openAuthActivity(mContext, MineOtoRecordActivity.class);
                            break;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
    }
}
