package com.gq.ged.user.api.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * 信息采集授权书-企业.
 *
 * @author SeppSong
 */
@ApiModel
public class CompanyInfoProtocolResForm implements Serializable{

    private static final long serialVersionUID = 488439954920955696L;

    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "身份证号码")
    private String identityNo;
    @ApiModelProperty(value = "公司名称")
    private String company;
    @ApiModelProperty(value = "统一社会信用代码")
    private String socialCreditCode;
    @ApiModelProperty(value = "注册ID")
    private String registerId;
    @ApiModelProperty(value = "授权人")
    private String authorizer;
    @ApiModelProperty(value = "法定代表人")
    private String legalPerson;
    @ApiModelProperty(value = "证件类型")
    private String certificateType;
    @ApiModelProperty(value = "证件号码")
    private String certificateNo;
    @ApiModelProperty(value = "日期")
    private String date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode;
    }

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public String getAuthorizer() {
        return authorizer;
    }

    public void setAuthorizer(String authorizer) {
        this.authorizer = authorizer;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
