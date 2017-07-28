package com.asking91.app.entity.user;

/**
 * Created by wxiao on 2016/10/25.
 */

public class RegisterEntity{
    private String mobile;
    private String passWord;
    private String verifyCode;

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    private String inviteCode;
//    private String name = "";
//    private Integer sex = 0;
//    @SerializedName("school_id")
//    private String schoolId = "";
//    @SerializedName("school_name")
//    private String schoolName = "";
//    @SerializedName("region_code")
//    private String regionCode = "";
//    @SerializedName("region_name")
//    private String regionName = "";
//    private String levelId = "";
//    private String classId = "";


    public RegisterEntity() {
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Integer getSex() {
//        return sex;
//    }
//
//    public void setSex(Integer sex) {
//        this.sex = sex;
//    }
//
//    public String getSchoolId() {
//        return schoolId;
//    }
//
//    public void setSchoolId(String schoolId) {
//        this.schoolId = schoolId;
//    }
//
//    public String getSchoolName() {
//        return schoolName;
//    }
//
//    public void setSchoolName(String schoolName) {
//        this.schoolName = schoolName;
//    }
//
//    public String getRegionCode() {
//        return regionCode;
//    }
//
//    public void setRegionCode(String regionCode) {
//        this.regionCode = regionCode;
//    }
//
//    public String getRegionName() {
//        return regionName;
//    }
//
//    public void setRegionName(String regionName) {
//        this.regionName = regionName;
//    }
//
//    public String getLevelId() {
//        return levelId;
//    }
//
//    public void setLevelId(String levelId) {
//        this.levelId = levelId;
//    }
//
//    public String getClassId() {
//        return classId;
//    }
//
//    public void setClassId(String classId) {
//        this.classId = classId;
//    }
}
