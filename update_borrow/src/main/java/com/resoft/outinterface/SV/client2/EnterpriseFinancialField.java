package com.resoft.outinterface.SV.client2;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnterpriseFinancialField {
    //企业财务信息
    @JsonProperty(value = "c_companyCode")
     private String  ccompanyCode ; //企业编号
    @JsonProperty(value = "c_EnglishName")
    private String cEnglishName;//企业英⽂文名称
    @JsonProperty(value = "c_companyNature")
    private String ccompanyNature ; //公司性质
    @JsonProperty(value = "c_nationOneIndustry")
    private String cnationOneIndustry ;//国标⼀一级⾏行行业
    @JsonProperty(value = "c_nationTwoIndustry")
    private String cnationTwoIndustry ;//国标⼆二级⾏行行业
    @JsonProperty(value = "c_comeInFlag")
    private String ccomeInFlag ;//是否上市
    @JsonProperty(value = "c_mail")
    private String cmail ; //邮政编码
    @JsonProperty(value = "c_reportMergerFlag")
    private String creportMergerFlag ;//是否合并
    @JsonProperty(value = "c_reportUnit")
    private String creportUnit ;//报表单位
    @JsonProperty(value = "c_reportType")
    private String creportType ;  //报表类型

    public String getCcompanyCode() {
        return ccompanyCode;
    }

    public void setCcompanyCode(String ccompanyCode) {
        this.ccompanyCode = ccompanyCode;
    }

    public String getcEnglishName() {
        return cEnglishName;
    }

    public void setcEnglishName(String cEnglishName) {
        this.cEnglishName = cEnglishName;
    }

    public String getCcompanyNature() {
        return ccompanyNature;
    }

    public void setCcompanyNature(String ccompanyNature) {
        this.ccompanyNature = ccompanyNature;
    }

    public String getCnationOneIndustry() {
        return cnationOneIndustry;
    }

    public void setCnationOneIndustry(String cnationOneIndustry) {
        this.cnationOneIndustry = cnationOneIndustry;
    }

    public String getCnationTwoIndustry() {
        return cnationTwoIndustry;
    }

    public void setCnationTwoIndustry(String cnationTwoIndustry) {
        this.cnationTwoIndustry = cnationTwoIndustry;
    }

    public String getCcomeInFlag() {
        return ccomeInFlag;
    }

    public void setCcomeInFlag(String ccomeInFlag) {
        this.ccomeInFlag = ccomeInFlag;
    }

    public String getCmail() {
        return cmail;
    }

    public void setCmail(String cmail) {
        this.cmail = cmail;
    }

    public String getCreportMergerFlag() {
        return creportMergerFlag;
    }

    public void setCreportMergerFlag(String creportMergerFlag) {
        this.creportMergerFlag = creportMergerFlag;
    }

    public String getCreportUnit() {
        return creportUnit;
    }

    public void setCreportUnit(String creportUnit) {
        this.creportUnit = creportUnit;
    }

    public String getCreportType() {
        return creportType;
    }

    public void setCreportType(String creportType) {
        this.creportType = creportType;
    }
}
