package com.asking91.app.ui.basepacket.choose;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.asking91.app.R;
import com.asking91.app.entity.LabelEntity;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.basepacket.KnowledgeDetailEntity;
import com.asking91.app.entity.basepacket.KnowledgeEntity;
import com.asking91.app.global.BasePacketConstant;
import com.asking91.app.global.OnlineTestConstant;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.IconTreeItemHolder;
import com.google.gson.reflect.TypeToken;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.OVER_SCROLL_NEVER;

/**
 * Created by wxwang on 2016/11/1.
 */
public class KnowledgeTreeActivity extends BaseFrameActivity<ChoosePresenter, ChooseModel> implements ChooseContract.View {

    @BindView(R.id.knowledge_tree)
    ViewGroup treeLyout;

    @BindView(R.id.toolBar)
    Toolbar mToolBar;

    @BindView(R.id.knowledge_btn)
    Button mKnowledgeBtn;

    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;

    AndroidTreeView tView ;
    private boolean selectionModeEnabled = false;
    String versionLevelId = "",backActivityName="";
    boolean isMuliSelect = false;//多选
    List<LabelEntity> allLabelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_treeview);

        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        super.initView();

        mKnowledgeBtn.setVisibility(isMuliSelect? View.VISIBLE:View.GONE);
    }

    @Override
    public void initData() {
        super.initData();
        versionLevelId = getIntent().getStringExtra("versionLevelId");
        isMuliSelect = getIntent().getBooleanExtra("isMuliSelect",false);
        backActivityName = getIntent().getStringExtra("backActivity");
        if(backActivityName.equals(ChooseActivity.class.getName()))
            setToolbar(mToolBar,getString(R.string.basepacket_choose_knowledge));
        else
            setToolbar(mToolBar,getString(R.string.onlinetest_choose_classtime));
        allLabelList = getIntent().getParcelableArrayListExtra("allLabelList");
    }

    @Override
    public void initLoad(){
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        if(!isMuliSelect)
            mPresenter.getKnowledgeList(versionLevelId);
        else
            mPresenter.getKnowledgeClassicList(versionLevelId);
    }

    @Override
    public void onRequestStart() {
    }

    @Override
    public void onRequestEnd() {
    }

    @Override
    public void onRequestError(RequestEntity requestEntity){
        super.onRequestError(requestEntity);
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }


    /**
     * 初始化更多操作的
     * @param
     * @param
     *
     */
    @Override
    public void onSuccess(int type, Object obj) {
        switch (type){
            case BasePacketConstant.Choose.KNOWLEDGE:
                showTreeView(obj);
                //显示数据
                if(isMuliSelect)
                    setTreeSelectedData(tView.getRoot(),allLabelList);
                break;
        }
    }


    private void showTreeView(Object obj){
        List<KnowledgeEntity> list = CommonUtil.parseDataToList(obj,new TypeToken<List<KnowledgeEntity>>(){});
        if (list.size()>0){
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }else{
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            return;
        }

        TreeNode root = TreeNode.root();
        tView = new AndroidTreeView(this, root);

        setTreeData(root,list);

        tView.setDefaultAnimation(true);
//        tView.setUse2dScroll(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom,true);

        if (isMuliSelect){
            selectionModeEnabled = !selectionModeEnabled;
            tView.setSelectionModeEnabled(selectionModeEnabled);
        }else{
            tView.setDefaultNodeClickListener(new TreeNode.TreeNodeClickListener() {
                @Override
                public void onClick(TreeNode node, Object value) {
                    // TODO onItemClick
//                if(!node.isLeaf()){
//                    tView.toggleNode(node);
//                }
                    Bundle bundle = new Bundle();
                    bundle.putString("knowledgeId",((IconTreeItemHolder.IconTreeItem)value).getId());
                    bundle.putString("knowledgeName",((IconTreeItemHolder.IconTreeItem)value).getText());

                    openBackResultActivity(backActivityName,bundle);
                }
            });
        }


        tView.setDefaultViewHolder(IconTreeItemHolder.class);
        treeLyout.addView(tView.getView());
        //禁止出现下来阴影
        tView.setOverScrollMode(OVER_SCROLL_NEVER);
        tView.setUseAutoToggle(true);
        tView.expandAll();
    }



    private TreeNode getNode(String id, String name, boolean isLeaf, int index, KnowledgeDetailEntity knowledgeDetailEntity){
        IconTreeItemHolder.IconTreeItem ico = new IconTreeItemHolder.IconTreeItem(R.mipmap.attr_down,id,name,isLeaf,index,knowledgeDetailEntity);
        return new TreeNode(ico).setViewHolder(new IconTreeItemHolder(this));
    }

    @OnClick(R.id.knowledge_btn)
    public void onClick(){
        //选中节点
        List<TreeNode> treeNodeList = tView.getSelected();
        int size = 0;
        //选中章节节点
        List<LabelEntity> list = new ArrayList<>();
//        选中的所有节点
        List<LabelEntity> allLabelList = new ArrayList<>();

        for (TreeNode treeNode : treeNodeList){
            IconTreeItemHolder.IconTreeItem item =  (IconTreeItemHolder.IconTreeItem)treeNode.getValue();
            //如果是知识点
            if(item.isLeaf() && item.getKnowledgeDetail()!=null){
                list.add(new LabelEntity(item.getKnowledgeDetail().getTipId(),item.getId(),item.getText()));
                size ++;
            }

            allLabelList.add(new LabelEntity(item.getKnowledgeDetail()!=null ? item.getKnowledgeDetail().getTipId():"",item.getId(),item.getText()));
        }
        if(size ==0 ){
            showShortToast(getString(R.string.onlinetest_knowledge_max_size_zero));
        }else if(size > OnlineTestConstant.KNOWLEDGE_MAX_SIZE){
            showShortToast(getString(R.string.onlinetest_knowledge_max_size_err));
        }else{
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("labelList", (ArrayList<? extends Parcelable>) list);
            bundle.putParcelableArrayList("allLabelList", (ArrayList<? extends Parcelable>) allLabelList);

            openBackResultActivity(backActivityName,bundle);
        }
    }

    public void setTreeSelectedData(TreeNode node,List<LabelEntity> allLabelList){
        if(allLabelList==null) return;
        for (TreeNode treeNode : node.getChildren()){
            IconTreeItemHolder.IconTreeItem item =  (IconTreeItemHolder.IconTreeItem)treeNode.getValue();
            for (LabelEntity labelEntity : allLabelList){
                if(item.getId().equals(labelEntity.getTmpId()))  {
                    tView.selectNode(treeNode,true);
                }
            }

            setTreeSelectedData(treeNode,allLabelList);
        }
    }

    /**
     * 递归遍历树数据
     * @param node
     * @param list
     */
    private void setTreeData(TreeNode node,List<KnowledgeEntity> list){
        int index = 0;
        for (KnowledgeEntity knowledgeEntity:list){
            TreeNode tempNode = getNode(knowledgeEntity.getId(),knowledgeEntity.getText(),isLeaf(knowledgeEntity),index,knowledgeEntity.getKnowledgeDetailEntity());
            node.addChild(tempNode);
            if (knowledgeEntity.getKnowledgeList()!=null)
                setTreeData(tempNode,knowledgeEntity.getKnowledgeList());
            index++;
        }
    }

    private boolean isLeaf(KnowledgeEntity knowledgeEntity){
        return knowledgeEntity.getKnowledgeList() == null;
    }

}
