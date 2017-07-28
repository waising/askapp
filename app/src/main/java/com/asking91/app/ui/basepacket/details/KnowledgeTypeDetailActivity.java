package com.asking91.app.ui.basepacket.details;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.common.SpaceItemDecoration;
import com.asking91.app.entity.basepacket.AnswerOption;
import com.asking91.app.entity.basepacket.KnowledgeTypeDetailEntity;
import com.asking91.app.entity.basepacket.KnowledgeTypeEntity;
import com.asking91.app.entity.basepacket.SubjectKnowledgeEntity;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.adapter.basepacket.KnowledgeTypeDetailAdapter;
import com.asking91.app.ui.adapter.basepacket.KnowledgeTypeOptionAdapter;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.RecycleViewDivider;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * Created by wxwang on 2016/11/7.
 */
public class KnowledgeTypeDetailActivity extends BaseFrameActivity<KnowledgeDetailPresenter,KnowledgeDetailModel>
        implements KnowledgeDetailContract.View  {
    @BindView(R.id.toolBar)
    Toolbar mToolBar;

    @BindView(R.id.typeName)
    TextView mKnowNameTv;

    @BindView(R.id.mathView)
    AskMathView mMathView;
    @BindView(R.id.type_name_tv)
    TextView mTypeNameTv;
    @BindView(R.id.knowledgeNames_tv)
    TextView mKnowNamesTv;

    @BindView(R.id.recycler_type_detail)
    RecyclerView recyclerView;

    @BindView(R.id.know_expand_iv)
    ImageView mExpandIv;

    @BindView(R.id.know_expand_same_iv)
    ImageView mSameExpandIv;

    @BindView(R.id.expandable_layout)
    ExpandableLayout mExpandableLayout;
    @BindView(R.id.expandable_same_layout)
    ExpandableLayout mSameExpandableLayout;

    @BindView(R.id.type_detail_answer_name_tv)
    TextView mDetailAnswerTv;

    @BindView(R.id.know_detail_answer_expand_iv)
    ImageView mDetailAnswerIv;

    @BindView(R.id.detail_answer_expandable_layout)
    ExpandableLayout mDetailAnswerExpandableLayout;

    @BindView(R.id.detail_answer_mathView)
    AskMathView mDetailAnswerMathView;

    @BindView(R.id.type_name_same_tv)
    TextView mTypeSameTv;

    @BindView(R.id.recycler_type_option)
    RecyclerView mTypeOptionRv;

    private KnowledgeTypeEntity knowledgeTypeEntity;
    private int index;

    List<KnowledgeTypeDetailEntity> mDatas;

    KnowledgeTypeDetailAdapter mKnowledgeTypeDetailAdapter;

    List<AnswerOption> mAnswerOptions;
    KnowledgeTypeOptionAdapter mKnowledgeTypeOptionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_type_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolBar, getString(R.string.knowledge_detail_type_example));

        LinearLayoutManager mgr = new LinearLayoutManager(this);
        mgr.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mgr);
        recyclerView.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.HORIZONTAL, 3, ContextCompat.getColor(this, R.color.main_bg)));
        recyclerView.setAdapter(mKnowledgeTypeDetailAdapter);

        LinearLayoutManager mgr1 = new LinearLayoutManager(this);
        mgr1.setOrientation(LinearLayoutManager.VERTICAL);
        mTypeOptionRv.setLayoutManager(mgr1);
        //选项
        mTypeOptionRv.setAdapter(mKnowledgeTypeOptionAdapter);

        mMathView.formatMath().showWebImage();
    }

    @Override
    public void initData(){
        super.initData();
        knowledgeTypeEntity = getIntent().getParcelableExtra("data");
        index = getIntent().getIntExtra("index",0);

        mDatas = new ArrayList<>();
        mKnowledgeTypeDetailAdapter = new KnowledgeTypeDetailAdapter(this,mDatas,recyclerView);

        mAnswerOptions = getIntent().getParcelableArrayListExtra("answerOptions");

        //选项
        if(mAnswerOptions!=null&&mAnswerOptions.size()>0){
            mKnowledgeTypeOptionAdapter = new KnowledgeTypeOptionAdapter(this,mAnswerOptions);
            mKnowledgeTypeOptionAdapter.notifyDataSetChanged();
        }else{
            mAnswerOptions = new ArrayList<>();
        }
    }

    @Override
    public void initLoad(){
        super.initLoad();
        ++index;
        mKnowNameTv.setText("类型"+index+":"+knowledgeTypeEntity.getKindName());
        mMathView.setText(knowledgeTypeEntity.getSubjectEntity().getSubjectDescriptionHtml());

        mTypeNameTv.setText("知识点");
        setKnowNames();

        //详细解题
        mDetailAnswerTv.setText(getString(R.string.know_detail_answer));
        mDetailAnswerMathView.setText(knowledgeTypeEntity.getSubjectEntity().getDetailsAnswerHtml());

        //同类题库
        mTypeSameTv.setText(getString(R.string.know_same));

        List<KnowledgeTypeDetailEntity> list = knowledgeTypeEntity.getTypeDetails();
        mDatas.clear();
        mDatas.addAll(list);
        mKnowledgeTypeDetailAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccess(int type,Object obj) {

    }

    @Override
    public void onDeleteCollect(ResponseBody body) {

    }

    @Override
    public void onError(int type) {

    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    private void setKnowNames(){
        List<SubjectKnowledgeEntity> list = knowledgeTypeEntity.getSubjectEntity().getSubjectKnowledgeEntityNames();

        StringBuilder str = new StringBuilder();
        for (SubjectKnowledgeEntity sk:list){
            str.append(sk.getTipName());
            str.append(" ");
        }
        mKnowNamesTv.setText(str.toString());
    }

    @OnClick({R.id.know_expand_rl,R.id.know_expand_same_rl,R.id.know_detail_answer_expand_rl})
    public void OnClick(View view){
        //关闭按钮状态
        setImgStatus();
        switch (view.getId()){
            case R.id.know_expand_rl:
                mExpandIv.setImageResource(mExpandableLayout.isExpanded()? R.mipmap.attr_right:R.mipmap.attr_down);
                mExpandableLayout.toggle();
                break;
            case R.id.know_expand_same_rl:
                mSameExpandIv.setImageResource(mSameExpandableLayout.isExpanded()? R.mipmap.attr_right:R.mipmap.attr_down);
                mSameExpandableLayout.toggle();
                break;
            case R.id.know_detail_answer_expand_rl:
                mDetailAnswerIv.setImageResource(mDetailAnswerExpandableLayout.isExpanded()?R.mipmap.attr_right:R.mipmap.attr_down);
                mDetailAnswerExpandableLayout.toggle();
                break;
        }

        mKnowledgeTypeDetailAdapter.setItemImg();
    }

    public void setImgStatus(){
        if (mExpandableLayout.isExpanded()){
            mExpandIv.setImageResource(R.mipmap.attr_right);
            mExpandableLayout.collapse();
        }
        if(mSameExpandableLayout.isExpanded()){
            mSameExpandIv.setImageResource(R.mipmap.attr_right);
            mSameExpandableLayout.collapse();
        }

        if(mDetailAnswerExpandableLayout.isExpanded()){
            mDetailAnswerIv.setImageResource(R.mipmap.attr_right);
            mDetailAnswerExpandableLayout.collapse();
        }
    }

    @Override
    public void onRefreshData(Object o) {

    }

    @Override
    public void onLoadMoreData(Object o) {

    }
}
