package com.shenkangyun.doctor.DBFolder;

import org.litepal.crud.LitePalSupport;

/**
 * Created by Administrator on 2018/7/5.
 */

public class User extends LitePalSupport {
    private int UserID;
    private int RoleID;
    private int HospitalID;
    private String LoginName;
    private String Name;
    private int Sex;
    private String National;
    private String Birthday;
    private String Mobile;
    private int degree;
    private String IDCard;
    private int PhotoID;
    private String PhotoUrl;
    private String Address;
    private String Password;
    private long FirstLoginTime;
    private long CreateTime;
    private long UpdateTime;
    private int DelFlag;
    private Object DelTime;
    private String Remark;

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;

    }

    public int getRoleID() {
        return RoleID;
    }

    public void setRoleID(int roleID) {
        RoleID = roleID;
    }

    public int getHospitalID() {
        return HospitalID;
    }

    public void setHospitalID(int hospitalID) {
        HospitalID = hospitalID;
    }

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String loginName) {
        LoginName = loginName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getSex() {
        return Sex;
    }

    public void setSex(int sex) {
        Sex = sex;
    }

    public String getNational() {
        return National;
    }

    public void setNational(String national) {
        National = national;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
    }

    public int getPhotoID() {
        return PhotoID;
    }

    public void setPhotoID(int photoID) {
        PhotoID = photoID;
    }

    public String getPhotoUrl() {
        return PhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        PhotoUrl = photoUrl;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public long getFirstLoginTime() {
        return FirstLoginTime;
    }

    public void setFirstLoginTime(long firstLoginTime) {
        FirstLoginTime = firstLoginTime;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public long getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(long updateTime) {
        UpdateTime = updateTime;
    }

    public int getDelFlag() {
        return DelFlag;
    }

    public void setDelFlag(int delFlag) {
        DelFlag = delFlag;
    }

    public Object getDelTime() {
        return DelTime;
    }

    public void setDelTime(Object delTime) {
        DelTime = delTime;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
