package com.stylefeng.guns.modular.support.model;

import java.io.Serializable;

public class FamilySurvey  implements Serializable {
    private Integer OPTYPENUM;
    private Integer RECYEAR;
    private Integer RECNUM;
    private String applicantName="";                //姓名OWNERNAME
    private String applicantSex="";                 //性别OWNERSEXCODE
    private String applicantMarry="";               //婚否marriedornot
    private String applicantAge="";                 //年龄ownerage
    private String applicantWork="";                //职业或工作单位OWNERUNIT
    private String applicantResidence="";          //户口所在地NATIVEPLACE
    private String applicantAdress="";              //现住址OWNERADDRESS
    private String applicantNature="";              //现居住房屋性质housepropcode
    private String applicantIncome="";              //本人收入情况ownerincome
    private String applicantOtherHouse="";         //家庭其他自有住房owneraddressother
    private String applicantPhone="";               //联系电话TELCODE
    private String OWNERCERTTYPECODE = "1";     //证件类型OWNERCERTTYPECODE
    private String applicantCard="";                //证件号码OWNERCERTNUM
    private String applicantResidenceTime="";      //户口迁入三亚的时间residence_time

    private ApplicantReason applicantReason = new ApplicantReason();     //申请理由等

    private String OWNERNAME_PEIOU;     //配偶姓名OWNERNAME_PEIOU
    private String OWNERSEXCODE_PEIOU;  //配偶性别OWNERSEXCODE_PEIOU
    private String OWNERCERTTYPECODE_PEIOU;//配偶证件类型OWNERCERTTYPECODE_PEIOU
    private String OWNERCERTNUM_PEIOU;//配偶证件号码OWNERCERTNUM_PEIOU
    private String PROJNAME;//
    private String ARCHITAREA;//

    private byte[] MY_PHOTO;//申请人图片
    private Integer civilServant;//是否为公务员

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

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantSex() {
        return applicantSex;
    }

    public void setApplicantSex(String applicantSex) {
        this.applicantSex = applicantSex;
    }

    public String getApplicantMarry() {
        return applicantMarry;
    }

    public void setApplicantMarry(String applicantMarry) {
        this.applicantMarry = applicantMarry;
    }

    public String getApplicantAge() {
        return applicantAge;
    }

    public void setApplicantAge(String applicantAge) {
        this.applicantAge = applicantAge;
    }

    public String getApplicantWork() {
        return applicantWork;
    }

    public void setApplicantWork(String applicantWork) {
        this.applicantWork = applicantWork;
    }

    public String getApplicantResidence() {
        return applicantResidence;
    }

    public void setApplicantResidence(String applicantResidence) {
        this.applicantResidence = applicantResidence;
    }

    public String getApplicantAdress() {
        return applicantAdress;
    }

    public void setApplicantAdress(String applicantAdress) {
        this.applicantAdress = applicantAdress;
    }

    public String getApplicantNature() {
        return applicantNature;
    }

    public void setApplicantNature(String applicantNature) {
        this.applicantNature = applicantNature;
    }

    public String getApplicantIncome() {
        return applicantIncome;
    }

    public void setApplicantIncome(String applicantIncome) {
        this.applicantIncome = applicantIncome;
    }

    public String getApplicantOtherHouse() {
        return applicantOtherHouse;
    }

    public void setApplicantOtherHouse(String applicantOtherHouse) {
        this.applicantOtherHouse = applicantOtherHouse;
    }

    public String getApplicantPhone() {
        return applicantPhone;
    }

    public void setApplicantPhone(String applicantPhone) {
        this.applicantPhone = applicantPhone;
    }

    public String getOWNERCERTTYPECODE() {
        return OWNERCERTTYPECODE;
    }

    public void setOWNERCERTTYPECODE(String OWNERCERTTYPECODE) {
        this.OWNERCERTTYPECODE = OWNERCERTTYPECODE;
    }

    public String getApplicantCard() {
        return applicantCard;
    }

    public void setApplicantCard(String applicantCard) {
        this.applicantCard = applicantCard;
    }

    public String getApplicantResidenceTime() {
        return applicantResidenceTime;
    }

    public void setApplicantResidenceTime(String applicantResidenceTime) {
        this.applicantResidenceTime = applicantResidenceTime;
    }

    public ApplicantReason getApplicantReason() {
        return applicantReason;
    }

    public void setApplicantReason(ApplicantReason applicantReason) {
        this.applicantReason = applicantReason;
    }

    public String getOWNERNAME_PEIOU() {
        return OWNERNAME_PEIOU;
    }

    public void setOWNERNAME_PEIOU(String OWNERNAME_PEIOU) {
        this.OWNERNAME_PEIOU = OWNERNAME_PEIOU;
    }

    public String getOWNERSEXCODE_PEIOU() {
        return OWNERSEXCODE_PEIOU;
    }

    public void setOWNERSEXCODE_PEIOU(String OWNERSEXCODE_PEIOU) {
        this.OWNERSEXCODE_PEIOU = OWNERSEXCODE_PEIOU;
    }

    public String getOWNERCERTTYPECODE_PEIOU() {
        return OWNERCERTTYPECODE_PEIOU;
    }

    public void setOWNERCERTTYPECODE_PEIOU(String OWNERCERTTYPECODE_PEIOU) {
        this.OWNERCERTTYPECODE_PEIOU = OWNERCERTTYPECODE_PEIOU;
    }

    public String getOWNERCERTNUM_PEIOU() {
        return OWNERCERTNUM_PEIOU;
    }

    public void setOWNERCERTNUM_PEIOU(String OWNERCERTNUM_PEIOU) {
        this.OWNERCERTNUM_PEIOU = OWNERCERTNUM_PEIOU;
    }

    public byte[] getMY_PHOTO() {
        return MY_PHOTO;
    }

    public void setMY_PHOTO(byte[] MY_PHOTO) {
        this.MY_PHOTO = MY_PHOTO;
    }

    public Integer getCivilServant() {
        return civilServant;
    }

    public void setCivilServant(Integer civilServant) {
        this.civilServant = civilServant;
    }

    @Override
    public String toString() {
        return "FamilySurvey{" +
                "OPTYPENUM=" + OPTYPENUM +
                ", RECYEAR=" + RECYEAR +
                ", RECNUM=" + RECNUM +
                '}';
    }
}
