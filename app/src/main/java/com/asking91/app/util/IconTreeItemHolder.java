package com.asking91.app.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.basepacket.KnowledgeDetailEntity;
import com.unnamed.b.atv.model.TreeNode;

import java.util.List;

/**
 * Created 2/12/15.
 */
public class IconTreeItemHolder extends TreeNode.BaseNodeViewHolder<IconTreeItemHolder.IconTreeItem> {
    private TextView tvValue;
    private ImageView arrowView;
    private TextView tvId;
    private CheckBox nodeSelector;

    public IconTreeItemHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(final TreeNode node, IconTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_icon_node, null, false);
        tvValue = (TextView) view.findViewById(R.id.node_value);
        nodeSelector = (CheckBox) view.findViewById(R.id.node_selector);
        tvValue.setText(value.text);

        arrowView = (ImageView) view.findViewById(R.id.icon);
        if(!((IconTreeItemHolder.IconTreeItem)node.getValue()).isLeaf()){
            arrowView.setVisibility(View.VISIBLE);
            arrowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tView.toggleNode(node);
                }
            });

        }else{
            arrowView.setVisibility(View.GONE);
        }

        nodeSelector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                node.setSelected(isChecked);
                for (TreeNode n : node.getChildren()) {
                    getTreeView().selectNode(n, isChecked);
                }
            }
        });

        nodeSelector.setChecked(node.isSelected());
        return view;
    }

    @Override
    public void toggleSelectionMode(boolean editModeEnabled) {
        nodeSelector.setVisibility(editModeEnabled ? View.VISIBLE : View.GONE);
        nodeSelector.setChecked(mNode.isSelected());
    }

    @Override
    public void toggle(boolean active) {
        arrowView.setImageDrawable(ContextCompat.getDrawable(context, active ? R.mipmap.attr_down : R.mipmap.attr_right));
    }

    public static class IconTreeItem {
        private int icon;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        private String id;
        private String text;
        private int position;

        public KnowledgeDetailEntity getKnowledgeDetail() {
            return KnowledgeDetail;
        }

        public void setKnowledgeDetail(KnowledgeDetailEntity knowledgeDetail) {
            KnowledgeDetail = knowledgeDetail;
        }

        private KnowledgeDetailEntity KnowledgeDetail;

        public boolean isLeaf() {
            return isLeaf;
        }

        public void setLeaf(boolean leaf) {
            isLeaf = leaf;
        }

        private boolean isLeaf;

        public IconTreeItem(){

        }

        public IconTreeItem(int icon, String id,String text,boolean isLeaf, int position,KnowledgeDetailEntity KnowledgeDetail) {
            this.icon = icon;
            this.id = id;
            this.text = text;
            this.position = position;
            this.isLeaf = isLeaf;
            this.KnowledgeDetail = KnowledgeDetail;
        }
    }
}
