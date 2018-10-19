package com.gq.ged.order.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wrh on 2018/1/19.
 */
@ApiModel
public class AccountWheResForm implements Serializable {
    @ApiModelProperty(value = "企业名称")
    private String companyName;
    @ApiModelProperty(value = "企业类型")
    private String companyCardType;
    @ApiModelProperty(value = "社会信用统一代码")
    private String socialCreditCode;
    @ApiModelProperty(value = "营业执照编号")
    private String businessLicence;
    @ApiModelProperty(value = "税务登记号")
    private String taxCode;
    @ApiModelProperty(value = "组织机构代码")
    private String organizationCode;
    @ApiModelProperty(value = "是否回显标识")
    private int flag;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCardType() {
        return companyCardType;
    }

    public void setCompanyCardType(String companyCardType) {
        this.companyCardType = companyCardType;
    }

    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode;
    }

    public String getBusinessLicence() {
        return businessLicence;
    }

    public void setBusinessLicence(String businessLicence) {
        this.businessLicence = businessLicence;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
