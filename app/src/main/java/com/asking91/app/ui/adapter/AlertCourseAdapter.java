package com.asking91.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.basepacket.CourseEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wxwang on 2016/11/1.
 */
public class AlertCourseAdapter extends BaseAdapter {

    List<CourseEntity> mDatas;
    Context context;
    private LayoutInflater inflater;

    public AlertCourseAdapter(Context context,List<CourseEntity> datas){
        this.context = context;
        this.mDatas = datas;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_alertdialog_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        holder.nameTv.setText(mDatas.get(position).getSubjectCatalogName());
        holder.idTv.setText(String.valueOf(mDatas.get(position).getSubjectCatalogId()));

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.item_name)
        TextView nameTv;
        @BindView(R.id.item_id)
        TextView idTv;

        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
