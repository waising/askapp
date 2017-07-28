package com.asking91.app.entity.supertutorial;

/**
 * Created by linbin on 2017/7/7.
 */

public class SummaryEntity {


    /**
     * attrId : 107
     * tipId : 186
     * attrType : 3
     * attrContentHtml : <p>1.机械运动：一个物体相对另一个物体有位置的改变。</p><p>2.机械运动的判断方法：被研究物体的位置相对于其他物体的位置是否发生改变。</p><p>3.参照物：描述物体的运动，判断一个物体的运动情况，需要选定一个物体作为标准，这个被选作参照标准的物体就叫做参照物。</p><p>4.运动和静止的相对性：运动和静止都是相对于所选的参照物而言的，选取的参照物不同，对同一物体运动状态的描述就不一定相同。</p>
     */

    private int attrId;
    private int tipId;
    private int attrType;
    private String attrContentHtml;

    public int getAttrId() {
        return attrId;
    }

    public void setAttrId(int attrId) {
        this.attrId = attrId;
    }

    public int getTipId() {
        return tipId;
    }

    public void setTipId(int tipId) {
        this.tipId = tipId;
    }

    public int getAttrType() {
        return attrType;
    }

    public void setAttrType(int attrType) {
        this.attrType = attrType;
    }

    public String getAttrContentHtml() {
        return attrContentHtml;
    }

    public void setAttrContentHtml(String attrContentHtml) {
        this.attrContentHtml = attrContentHtml;
    }
}
