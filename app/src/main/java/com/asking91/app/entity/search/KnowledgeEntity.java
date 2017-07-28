package com.asking91.app.entity.search;

/**
 * Created by zqshen on 2016/11/18.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KnowledgeEntity {

    @SerializedName("tip_name")
    private String tipName;
    @SerializedName("tip_id")
    private String tipId;
    private List<ListBean> list;

    public String getTipName() {
        return tipName;
    }

    public void setTipName(String tipName) {
        this.tipName = tipName;
    }

    public String getTipId() {
        return tipId;
    }

    public void setTipId(String tipId) {
        this.tipId = tipId;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        @SerializedName("tip_name")
        private String tipName;
        @SerializedName("tip_id")
        private String tipId;

        public String getTipName() {
            return tipName;
        }

        public void setTipName(String tipName) {
            this.tipName = tipName;
        }

        public String getTipid() {
            return tipId;
        }

        public void setTipid(String tipId) {
            this.tipId = tipId;
        }
    }
}
