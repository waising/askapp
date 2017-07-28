
package com.asking91.app.entity.user;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * 用户信息
 */
public class UserEntity implements Parcelable {
    private String id;
    private String name;
    private String role;
    private String classId;
    private boolean isLocked;
    private String userName;
    private String ip;
    private int level;
    private String userType;
    private String levelId;
    private String nickName;
    private String email;
    private String mobile;
    private double integral;//阿思币数据
    private String ipAddress;
    private int messageSize;
    private String active;
    private String area;
    private String avatar;
    @SerializedName("avatar_url")
    private String avatarUrl;
    private Date birthday;
    private String birthdayStr;
    private String ips;
    private String lastLoginIP;
    private String lastLoginTime;
    private String passWord;
    private String registerCode;
    private String registerTime;
    private boolean isActived;
    private int sex;
    @SerializedName("school_id")
    private String schoolId;
    @SerializedName("school_name")
    private String schoolName;
    @SerializedName("region_code")
    private String regionCode;
    @SerializedName("region_name")
    private String regionName;
    private boolean online;
    private boolean isDeleted;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBirthdayStr() {
        return birthdayStr;
    }

    public void setBirthdayStr(String birthdayStr) {
        this.birthdayStr = birthdayStr;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        this.isDeleted = deleted;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        this.isLocked = locked;
    }

    public int getMessageSize() {
        return messageSize;
    }

    public void setMessageSize(int messageSize) {
        this.messageSize = messageSize;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getActive() {
        return active;
    }

    private String remark;

    public boolean isActived() {
        return isActived;
    }

    public void setActived(boolean actived) {
        isActived = actived;
    }


    public void setActive(String active) {
        this.active = active;
    }

    public double getIntegral() {
        return integral;
    }

    public void setIntegral(double integral) {
        this.integral = integral;
    }

    public UserEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.role);
        dest.writeString(this.classId);
        dest.writeByte(this.isLocked ? (byte) 1 : (byte) 0);
        dest.writeString(this.userName);
        dest.writeString(this.ip);
        dest.writeInt(this.level);
        dest.writeString(this.userType);
        dest.writeString(this.levelId);
        dest.writeString(this.nickName);
        dest.writeString(this.email);
        dest.writeString(this.mobile);
        dest.writeDouble(this.integral);
        dest.writeString(this.ipAddress);
        dest.writeInt(this.messageSize);
        dest.writeString(this.active);
        dest.writeString(this.area);
        dest.writeString(this.avatar);
        dest.writeString(this.avatarUrl);
        dest.writeLong(this.birthday != null ? this.birthday.getTime() : -1);
        dest.writeString(this.birthdayStr);
        dest.writeString(this.ips);
        dest.writeString(this.lastLoginIP);
        dest.writeString(this.lastLoginTime);
        dest.writeString(this.passWord);
        dest.writeString(this.registerCode);
        dest.writeString(this.registerTime);
        dest.writeByte(this.isActived ? (byte) 1 : (byte) 0);
        dest.writeInt(this.sex);
        dest.writeString(this.schoolId);
        dest.writeString(this.schoolName);
        dest.writeString(this.regionCode);
        dest.writeString(this.regionName);
        dest.writeByte(this.online ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isDeleted ? (byte) 1 : (byte) 0);
        dest.writeString(this.remark);
    }

    protected UserEntity(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.role = in.readString();
        this.classId = in.readString();
        this.isLocked = in.readByte() != 0;
        this.userName = in.readString();
        this.ip = in.readString();
        this.level = in.readInt();
        this.userType = in.readString();
        this.levelId = in.readString();
        this.nickName = in.readString();
        this.email = in.readString();
        this.mobile = in.readString();
        this.integral = in.readDouble();
        this.ipAddress = in.readString();
        this.messageSize = in.readInt();
        this.active = in.readString();
        this.area = in.readString();
        this.avatar = in.readString();
        this.avatarUrl = in.readString();
        long tmpBirthday = in.readLong();
        this.birthday = tmpBirthday == -1 ? null : new Date(tmpBirthday);
        this.birthdayStr = in.readString();
        this.ips = in.readString();
        this.lastLoginIP = in.readString();
        this.lastLoginTime = in.readString();
        this.passWord = in.readString();
        this.registerCode = in.readString();
        this.registerTime = in.readString();
        this.isActived = in.readByte() != 0;
        this.sex = in.readInt();
        this.schoolId = in.readString();
        this.schoolName = in.readString();
        this.regionCode = in.readString();
        this.regionName = in.readString();
        this.online = in.readByte() != 0;
        this.isDeleted = in.readByte() != 0;
        this.remark = in.readString();
    }

    public static final Creator<UserEntity> CREATOR = new Creator<UserEntity>() {
        @Override
        public UserEntity createFromParcel(Parcel source) {
            return new UserEntity(source);
        }

        @Override
        public UserEntity[] newArray(int size) {
            return new UserEntity[size];
        }
    };
}
