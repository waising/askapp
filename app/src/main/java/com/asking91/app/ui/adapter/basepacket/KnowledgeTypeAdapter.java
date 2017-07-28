package com.asking91.app.ui.adapter.basepacket;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.basepacket.AnswerOption;
import com.asking91.app.entity.basepacket.KnowledgeTypeEntity;
import com.asking91.app.global.BasePacketConstant;
import com.asking91.app.ui.basepacket.details.KnowledgeTypeDetailActivity;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wxwang on 2016/11/7.
 */
public class KnowledgeTypeAdapter extends RecyclerView.Adapter<KnowledgeTypeAdapter.ViewHolder> {

    private Context mContext;
    List<KnowledgeTypeEntity> mDatas;
    List<AnswerOption> mAnswerOptions;
    KnowledgeTypeOptionAdapter knowledgeTypeOptionAdapter;

    public KnowledgeTypeAdapter(Context context, List<KnowledgeTypeEntity> datas){
        this.mContext = context;
        this.mDatas = datas;
        mAnswerOptions = new ArrayList<>();
        knowledgeTypeOptionAdapter = new KnowledgeTypeOptionAdapter(mContext,mAnswerOptions);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new KnowledgeTypeAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_knowledge_type_list,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.position = position;
        KnowledgeTypeEntity knowledgeTypeEntity = mDatas.get(position);

        int index = position+1;
        holder.typeNameTv.setText("类型"+index+":"+knowledgeTypeEntity.getKindName());
        holder.mMathView.setText(knowledgeTypeEntity.getSubjectEntity().getSubjectDescriptionHtml());


        mAnswerOptions.clear();

        //选择题
        if (BasePacketConstant.Subject.SUBJECT__CHOOSE.equals(knowledgeTypeEntity.getSubjectEntity().getSubjectType().getTypeId())){
            LinearLayoutManager mgr = new LinearLayoutManager(mContext);
            mgr.setOrientation(LinearLayoutManager.VERTICAL);
            holder.optionRv.setLayoutManager(mgr);
            holder.optionRv.setAdapter(knowledgeTypeOptionAdapter);
            List<AnswerOption> list = knowledgeTypeEntity.getSubjectEntity().getAnswerOptions();

            if(list!=null&&list.size()>0){
                mAnswerOptions.addAll(list);
                knowledgeTypeOptionAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.typeName)
        TextView typeNameTv;

        @BindView(R.id.multiStateView)
        MultiStateView msView;

        @BindView(R.id.mathView)
        AskMathView mMathView;

        @BindView(R.id.analysis_btn)
        Button analysisBtn;

        @BindView(R.id.recycler_type_option)
        RecyclerView optionRv;

        int position;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mMathView.showWebImage(msView).formatMath();
            analysisBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //跳转
            if (v.getId()==R.id.analysis_btn){
                Bundle bundle = new Bundle();
                bundle.putParcelable("data",mDatas.get(position));
                bundle.putInt("index",position);
                bundle.putParcelableArrayList("answerOptions", (ArrayList<? extends Parcelable>) mDatas.get(position).getSubjectEntity().getAnswerOptions());
                CommonUtil.openActivity(mContext, KnowledgeTypeDetailActivity.class,bundle);
            }
        }
    }
}
