package com.stylefeng.guns.modular.support.model;

import java.io.Serializable;

public class JointApplicant implements Serializable {
    private Integer OPTYPENUM;
    private Integer RECYEAR;
    private Integer RECNUM;
    private String jointApplicantId;                  //共同申请人ID  FamilyID
    private String jointApplicantName="";                //姓名FAMILYNAME
    private String jointApplicantSex="";                 //性别OWNERSEXCODE
    private String jointApplicantMarry="";               //婚否marriedornot
    private String jointApplicantAge="";                 //年龄ownerage
    private String jointApplicantPhone="";               //联系电话TELCODE
    private String OWNERCERTTYPECODE = "1";          //证件类型FAMILYCERTTYPE
    private String jointApplicantCard="";                //证件号码familycertnum
    private String jointApplicantWork="";                //职业或工作单位OWNERUNIT
    private String jointApplicantRelation="";           //与申请人关系FAMILYTYPE
    private String jointApplicantIncome="";              //本人收入情况ownerincome
    private String jointApplicantResidence="";          //户口所在地FAMILYHUJI
    private String jointApplicantAddress="";              //现住址OWNERADDRESS
    private String jointApplicantResidenceTime ="";      //户口迁入三亚的时间residence_time

    private byte[] MY_PHOTO;//申请人图片
    public String getJointApplicantName() {
        return jointApplicantName;
    }

    public void setJointApplicantName(String jointApplicantName) {
        this.jointApplicantName = jointApplicantName;
    }

    public String getJointApplicantSex() {
        return jointApplicantSex;
    }

    public void setJointApplicantSex(String jointApplicantSex) {
        this.jointApplicantSex = jointApplicantSex;
    }

    public String getJointApplicantMarry() {
        return jointApplicantMarry;
    }

    public void setJointApplicantMarry(String jointApplicantMarry) {
        this.jointApplicantMarry = jointApplicantMarry;
    }

    public String getJointApplicantAge() {
        return jointApplicantAge;
    }

    public void setJointApplicantAge(String jointApplicantAge) {
        this.jointApplicantAge = jointApplicantAge;
    }

    public String getJointApplicantPhone() {
        return jointApplicantPhone;
    }

    public void setJointApplicantPhone(String jointApplicantPhone) {
        this.jointApplicantPhone = jointApplicantPhone;
    }

    public String getOWNERCERTTYPECODE() {
        return OWNERCERTTYPECODE;
    }

    public void setOWNERCERTTYPECODE(String OWNERCERTTYPECODE) {
        this.OWNERCERTTYPECODE = OWNERCERTTYPECODE;
    }

    public String getJointApplicantCard() {
        return jointApplicantCard;
    }

    public void setJointApplicantCard(String jointApplicantCard) {
        this.jointApplicantCard = jointApplicantCard;
    }

    public String getJointApplicantWork() {
        return jointApplicantWork;
    }

    public void setJointApplicantWork(String jointApplicantWork) {
        this.jointApplicantWork = jointApplicantWork;
    }

    public String getJointApplicantRelation() {
        return jointApplicantRelation;
    }

    public void setJointApplicantRelation(String jointApplicantRelation) {
        this.jointApplicantRelation = jointApplicantRelation;
    }

    public String getJointApplicantIncome() {
        return jointApplicantIncome;
    }

    public void setJointApplicantIncome(String jointApplicantIncome) {
        this.jointApplicantIncome = jointApplicantIncome;
    }

    public String getJointApplicantResidenceTime() {
        return jointApplicantResidenceTime;
    }

    public void setJointApplicantResidenceTime(String jointApplicantResidenceTime) {
        this.jointApplicantResidenceTime = jointApplicantResidenceTime;
    }

    public String getJointApplicantResidence() {
        return jointApplicantResidence;
    }

    public void setJointApplicantResidence(String jointApplicantResidence) {
        this.jointApplicantResidence = jointApplicantResidence;
    }

    public String getJointApplicantAddress() {
        return jointApplicantAddress;
    }

    public void setJointApplicantAddress(String jointApplicantAddress) {
        this.jointApplicantAddress = jointApplicantAddress;
    }

    public Integer getOPTYPENUM() {
        return OPTYPENUM;
    }

    public void setOPTYPENUM(Integer OPTYPENUM) {
        this.OPTYPENUM = OPTYPENUM;
    }

    public Integer getRECYEAR() {
        return RECYEAR;
    }

    public void setRECYEAR(Integer RECYEAR) {
        this.RECYEAR = RECYEAR;
    }

    public Integer getRECNUM() {
        return RECNUM;
    }

    public void setRECNUM(Integer RECNUM) {
        this.RECNUM = RECNUM;
    }

    public String getJointApplicantId() {
        return jointApplicantId;
    }

    public void setJointApplicantId(String jointApplicantId) {
        this.jointApplicantId = jointApplicantId;
    }

    public byte[] getMY_PHOTO() {
        return MY_PHOTO;
    }

    public void setMY_PHOTO(byte[] MY_PHOTO) {
        this.MY_PHOTO = MY_PHOTO;
    }
}
