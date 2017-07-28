package com.asking91.app.entity.supertutorial;

import java.util.List;

/**
 * Created by wxiao on 2016/11/30.
 * 超级课堂--课堂总结
 */

public class SuperSummaryEntity {
    private List<AttrListBean> attrList;

    public List<AttrListBean> getAttrList() {
        return attrList;
    }

    public void setAttrList(List<AttrListBean> attrList) {
        this.attrList = attrList;
    }

    public AttrListBean createAttrListBean(){
        return new AttrListBean();
    }

    public static class AttrListBean {
        /**
         * attrId : 107
         * tip_id : 186
         * tip_name : 动与静
         * typeName : 课堂总结
         * contentHtml : <p style="text-indent: 2em;">判断物体是运动还是静止的关键在于：这个物体和参照物间有没有距离或方向的变化，有就是运动的，没有就是静止的。在诗歌类题目中，就看诗句中出现的物体和在这个诗句中出现的关键动词。</p>
         */

        private int attrId;
        private String tip_id;
        private String tip_name;
        private String typeName;
        private String contentHtml;

        public int getAttrId() {
            return attrId;
        }

        public void setAttrId(int attrId) {
            this.attrId = attrId;
        }

        public String getTip_id() {
            return tip_id;
        }

        public void setTip_id(String tip_id) {
            this.tip_id = tip_id;
        }

        public String getTip_name() {
            return tip_name;
        }

        public void setTip_name(String tip_name) {
            this.tip_name = tip_name;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getContentHtml() {
            return contentHtml;
        }

        public void setContentHtml(String contentHtml) {
            this.contentHtml = contentHtml;
        }
    }
}
