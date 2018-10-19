package com.resoft.credit.financialStateImport.entity.req;

import java.io.Serializable;

public class CompanyInfo implements Serializable{
    private String  userId;
    private String   password;
    private String  depCode;
    private String   companyCode;
    private String   comCName;
    private String    comEName;
    private String   createDate;
    private String    companyNature;
    private String    sw_indu_1;
    private String    sw_indu_2;
    private String     marketClass;
    private String      postCode;
    private String     isMerger;
    private String     reportUnit;
    private String     reportType;
    private String    type;

    public CompanyInfo(/*String userId, String password,*/ String depCode, String companyCode, String comCName, String comEName, String createDate, String companyNature, String sw_indu_1, String sw_indu_2, String marketClass, String postCode, String isMerger, String reportUnit, String reportType, String type) {
//        this.userId = userId;
//        this.password = password;
        this.depCode = depCode;
        this.companyCode = companyCode;
        this.comCName = comCName;
        this.comEName = comEName;
        this.createDate = createDate;
        this.companyNature = companyNature;
        this.sw_indu_1 = sw_indu_1;
        this.sw_indu_2 = sw_indu_2;
        this.marketClass = marketClass;
        this.postCode = postCode;
        this.isMerger = isMerger;
        this.reportUnit = reportUnit;
        this.reportType = reportType;
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getComCName() {
        return comCName;
    }

    public void setComCName(String comCName) {
        this.comCName = comCName;
    }

    public String getComEName() {
        return comEName;
    }

    public void setComEName(String comEName) {
        this.comEName = comEName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCompanyNature() {
        return companyNature;
    }

    public void setCompanyNature(String companyNature) {
        this.companyNature = companyNature;
    }

    public String getSw_indu_1() {
        return sw_indu_1;
    }

    public void setSw_indu_1(String sw_indu_1) {
        this.sw_indu_1 = sw_indu_1;
    }

    public String getSw_indu_2() {
        return sw_indu_2;
    }

    public void setSw_indu_2(String sw_indu_2) {
        this.sw_indu_2 = sw_indu_2;
    }

    public String getMarketClass() {
        return marketClass;
    }

    public void setMarketClass(String marketClass) {
        this.marketClass = marketClass;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getIsMerger() {
        return isMerger;
    }

    public void setIsMerger(String isMerger) {
        this.isMerger = isMerger;
    }

    public String getReportUnit() {
        return reportUnit;
    }

    public void setReportUnit(String reportUnit) {
        this.reportUnit = reportUnit;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CompanyInfo{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", depCode='" + depCode + '\'' +
                ", companyCode='" + companyCode + '\'' +
                ", comCName='" + comCName + '\'' +
                ", comEName='" + comEName + '\'' +
                ", createDate='" + createDate + '\'' +
                ", companyNature='" + companyNature + '\'' +
                ", sw_indu_1='" + sw_indu_1 + '\'' +
                ", sw_indu_2='" + sw_indu_2 + '\'' +
                ", marketClass='" + marketClass + '\'' +
                ", postCode='" + postCode + '\'' +
                ", isMerger='" + isMerger + '\'' +
                ", reportUnit='" + reportUnit + '\'' +
                ", reportType='" + reportType + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
