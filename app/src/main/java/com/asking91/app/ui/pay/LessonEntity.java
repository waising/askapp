package com.asking91.app.ui.pay;

/**
 * Created by linbin on 2017/5/12.
 */

public class LessonEntity {


    /**
     * versionId : 22
     * versionName : 华师大版
     * versionState : 1
     * subjectCatalogId : 2
     * stage : 2
     * agency : 2
     * sort : 2
     * purchased : 2
     * purchasedMsg : 未全部购买
     */

    private int versionId;
    private String versionName;
    private String versionState;
    private int subjectCatalogId;
    private String stage;
    private String agency;
    private int sort;
    private int purchased;
    private String purchasedMsg;


    private boolean isExpand;//是否展开标记

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionState() {
        return versionState;
    }

    public void setVersionState(String versionState) {
        this.versionState = versionState;
    }

    public int getSubjectCatalogId() {
        return subjectCatalogId;
    }

    public void setSubjectCatalogId(int subjectCatalogId) {
        this.subjectCatalogId = subjectCatalogId;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getPurchased() {
        return purchased;
    }

    public void setPurchased(int purchased) {
        this.purchased = purchased;
    }

    public String getPurchasedMsg() {
        return purchasedMsg;
    }

    public void setPurchasedMsg(String purchasedMsg) {
        this.purchasedMsg = purchasedMsg;
    }
}
