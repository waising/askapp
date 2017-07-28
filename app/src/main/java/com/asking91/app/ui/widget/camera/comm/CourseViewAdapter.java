package com.asking91.app.ui.widget.camera.comm;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.LabelEntity;
import com.asking91.app.ui.widget.camera.adapter.CBaseAdapter;

import java.util.List;

/**
 * Created by jswang on 2017/3/25.
 */

class CourseViewAdapter extends CBaseAdapter<LabelEntity> {
    OnItemLabelEntityListener mListener;

    public CourseViewAdapter(Activity activity, List<LabelEntity> data, OnItemLabelEntityListener mListener) {
        super(activity, data);
        this.mListener = mListener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = listContainer.inflate(R.layout.item_course_view, null);
            holder.item_name = (TextView) convertView.findViewById(R.id.item_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final LabelEntity e = listItems.get(position);
        holder.item_name.setSelected(e.getSelect());
        holder.item_name.setText(e.getName());
        holder.item_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (LabelEntity ii : listItems) {
                    ii.setSelect(false);
                }
                e.setSelect(true);
                notifyDataSetChanged();
                mListener.OnItemLabelEntity(e);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView item_name;
    }
}