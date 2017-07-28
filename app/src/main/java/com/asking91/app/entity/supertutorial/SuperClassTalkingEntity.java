package com.asking91.app.entity.supertutorial;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wxiao on 2016/11/29.
 * 阿思可博士有话说
 */

public class SuperClassTalkingEntity {
    private List<AttrListBean> attrList;

    public List<AttrListBean> getAttrList() {
        return attrList;
    }

    public void setAttrList(List<AttrListBean> attrList) {
        this.attrList = attrList;
    }

    public static class AttrListBean {
        /**
         * attrId : 57
         * tipId : 132
         * tipName : 正数、负数、有理数
         * typeName : 阿思可博士有话说
         * contentHtml : 哈喽，我是阿思可博士，欢迎来到我的课堂！<p>你知道有理数的发展史吗？首先，在远古时代，那时人们只需要简单的计算养几只猪，宰几头羊，用手指头算算就好了，于是像“1、2、3、4”这样的自然数就应运产生了；接着，人们在分配食物的时候，常常出现结果不是整数的情况，于是，渐渐产生了分数，由于分数记录不方便，于是又逐步产生了小数；两千多年前，农民伯伯把增产记为正，减产记为负，这样就产生了负数。这就是有理数的发展简史了，今天阿思可博士就和大家一起来深入学习正数、负数、有理数。</p><p><img src="http://7xj9ur.com1.z0.glb.clouddn.com/2016-07-30/1469844378978013468.png" title="1469844378978013468.png" alt="blob.png"/></p>
         */

        private int attrId;
        @SerializedName("tip_id")
        private String tipId;
        @SerializedName("tip_name")
        private String tipName;
        private String typeName;
        private String contentHtml;

        public int getAttrId() {
            return attrId;
        }

        public void setAttrId(int attrId) {
            this.attrId = attrId;
        }

        public String getTipId() {
            return tipId;
        }

        public void setTipId(String tipId) {
            this.tipId = tipId;
        }

        public String getTipName() {
            return tipName;
        }

        public void setTipName(String tipName) {
            this.tipName = tipName;
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
