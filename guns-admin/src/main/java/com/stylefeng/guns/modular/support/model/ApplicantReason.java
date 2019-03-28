package com.stylefeng.guns.modular.support.model;

public class ApplicantReason {
    private Integer OPTYPENUM;
    private Integer RECYEAR;
    private Integer RECNUM;
    private String regional="";                    //区域范围district_BZF
    private String application_mode="";           //申请方式appplytype_BZF
    private String apartment="";                   //户型housetype_BZF
    private String reason="";                      //申请理由
    private String promise_applicant="";         //承诺书——申请人
    private String promise_spouse="";            //承诺书——配偶
    private String promise_time="";              //承诺书——时间

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

    public String getRegional() {
        return regional;
    }

    public void setRegional(String regional) {
        this.regional = regional;
    }

    public String getApplication_mode() {
        return application_mode;
    }

    public void setApplication_mode(String application_mode) {
        this.application_mode = application_mode;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPromise_applicant() {
        return promise_applicant;
    }

    public void setPromise_applicant(String promise_applicant) {
        this.promise_applicant = promise_applicant;
    }

    public String getPromise_spouse() {
        return promise_spouse;
    }

    public void setPromise_spouse(String promise_spouse) {
        this.promise_spouse = promise_spouse;
    }

    public String getPromise_time() {
        return promise_time;
    }

    public void setPromise_time(String promise_time) {
        this.promise_time = promise_time;
    }
}
