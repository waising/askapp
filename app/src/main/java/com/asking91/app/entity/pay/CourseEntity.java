package com.asking91.app.entity.pay;

/**
 * 课程购买第一层
 * Created by linbin on 2017/6/27.
 */

public class CourseEntity {

    /**
     * _id : 59507c2a888e4c0005c4def6
     * sequence : 1
     * packageTypeName : 高中物理
     * createTime : 2017-06-26 11:14:50
     * packageTypeId : TC-TBKT-04
     */

    private String _id;
    private int sequence;
    private String packageTypeName;
    private String createTime;
    private String packageTypeId;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getPackageTypeName() {
        return packageTypeName;
    }

    public void setPackageTypeName(String packageTypeName) {
        this.packageTypeName = packageTypeName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPackageTypeId() {
        return packageTypeId;
    }

    public void setPackageTypeId(String packageTypeId) {
        this.packageTypeId = packageTypeId;
    }
}
