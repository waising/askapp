package com.asking91.app.ui.mine.more;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.common.SwipeBackActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jswang on 2017/3/21.
 */

public class MineStuRecordActivity extends SwipeBackActivity {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    @BindView(R.id.calen)
    CalendarView calen;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    private ArrayList<String> dataList = new ArrayList<>();
    CommAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_stu_record);
        ButterKnife.bind(this);
        setSwipeBackEnable(false);
    }

    @Override
    public void initView() {
        super.initView();
        calen.setOnCalendarViewListener(new CalendarView.OnCalendarViewListener(){
            @Override
            public void OnCalendarView(String date) {
                setToolbar(mToolbar,date);
            }
        });
        setToolbar(mToolbar,calen.getToDayDate());
        for(int i=0 ; i<1;i++){
            dataList.add("暂无数据");
        }
        recycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CommAdapter(this);
        recycler.setAdapter(mAdapter);
    }

    int[] resIconId = {R.mipmap.ic_stu_record1,R.mipmap.ic_stu_record2,R.mipmap.ic_stu_record3};

    class CommAdapter extends RecyclerView.Adapter<CommViewHolder>{
        private Context mContext;

        public CommAdapter(Context context){
            this.mContext = context;
        }

        @Override
        public CommViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CommViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_stu_record_layout,parent,false));
        }

        @Override
        public void onBindViewHolder(final CommViewHolder holder, final int position) {
            final String e = dataList.get(position);
            holder.tvName.setText(e);
            holder.tvName.setCompoundDrawablesWithIntrinsicBounds(resIconId[position%resIconId.length],0,0,0);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }

    class CommViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;

        public CommViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



}
