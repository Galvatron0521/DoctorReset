package com.shenkangyun.doctor.BeanFolder;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by Administrator on 2018/10/18.
 */

public class ShowTableEntity implements MultiItemEntity {

    /**
     * id : 928
     * diseasesID : null
     * patientID : 112
     * moduleCode : SP020123
     * modelFlag : 0
     * modelName : null
     * createTime : 1542945599000
     * updateTime : 1543049980000
     * delFlag : 0
     * delTime : null
     * remark : null
     * scores : 10
     * recordFlag : 0
     * followRecordId : null
     * crfDate : 1542945599000
     * writePerson : null
     * results : 患者评估正常
     * groupScore : null
     * recordsimagedataID : null
     */

    public static final int TITLE = 1;
    public static final int TABLE = 2;
    private int itemType;

    public ShowTableEntity(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    private String title;
    private int fieldRecordID;
    private Object diseasesID;
    private int patientID;
    private String moduleCode;
    private int modelFlag;
    private String modelName;
    private long createTime;
    private long updateTime;
    private int delFlag;
    private Object delTime;
    private Object remark;
    private String scores;
    private String recordFlag;
    private Object followRecordId;
    private long crfDate;
    private Object writePerson;
    private String results;
    private String groupScore;
    private Object recordsimagedataID;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getFieldRecordID() {
        return fieldRecordID;
    }

    public void setFieldRecordID(int fieldRecordID) {
        this.fieldRecordID = fieldRecordID;
    }

    public Object getDiseasesID() {
        return diseasesID;
    }

    public void setDiseasesID(Object diseasesID) {
        this.diseasesID = diseasesID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public int getModelFlag() {
        return modelFlag;
    }

    public void setModelFlag(int modelFlag) {
        this.modelFlag = modelFlag;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public Object getDelTime() {
        return delTime;
    }

    public void setDelTime(Object delTime) {
        this.delTime = delTime;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }

    public String getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(String recordFlag) {
        this.recordFlag = recordFlag;
    }

    public Object getFollowRecordId() {
        return followRecordId;
    }

    public void setFollowRecordId(Object followRecordId) {
        this.followRecordId = followRecordId;
    }

    public long getCrfDate() {
        return crfDate;
    }

    public void setCrfDate(long crfDate) {
        this.crfDate = crfDate;
    }

    public Object getWritePerson() {
        return writePerson;
    }

    public void setWritePerson(Object writePerson) {
        this.writePerson = writePerson;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getGroupScore() {
        return groupScore;
    }

    public void setGroupScore(String groupScore) {
        this.groupScore = groupScore;
    }

    public Object getRecordsimagedataID() {
        return recordsimagedataID;
    }

    public void setRecordsimagedataID(Object recordsimagedataID) {
        this.recordsimagedataID = recordsimagedataID;
    }
}
