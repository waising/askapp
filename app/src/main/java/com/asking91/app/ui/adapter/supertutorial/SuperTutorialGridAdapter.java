package com.asking91.app.ui.adapter.supertutorial;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.asking91.app.R;
import com.asking91.app.ui.widget.camera.utils.ImageService;

import java.util.ArrayList;

/**
 * 课程选择GridView 的adapter
 */
public class SuperTutorialGridAdapter extends RecyclerView.Adapter<SuperTutorialGridAdapter.ViewHolder> {
    private Activity mActivity;


    private ArrayList<String> selectNameList = new ArrayList<String>();


    private void setData(ArrayList<String> selectName) {
        this.selectNameList = selectName;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llSelectImage;

        public ViewHolder(View itemView) {
            super(itemView);
            llSelectImage = (LinearLayout) itemView.findViewById(R.id.l1);
        }
    }

    public SuperTutorialGridAdapter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onBindViewHolder(SuperTutorialGridAdapter.ViewHolder holder, final int position) {

        String selectName = selectNameList.get(position);
        if (!TextUtils.isEmpty(selectName)) {
//            if (selectName.equals(mActivity.getString(R.string.ask_czsx))) {
//
//
//            } else if () {
//
//
//            }


        }
        holder.llSelectImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


            }

        });


    }

    @Override
    public SuperTutorialGridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SuperTutorialGridAdapter.ViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.item_supertutorial_select, parent, false));
    }

    @Override
    public int getItemCount() {
        return ImageService.getInstance().getCurrentDirImages().size();
    }

    public Object getItem(int position) {
        return ImageService.getInstance().getCurrentDirImages().get(position);
    }
}