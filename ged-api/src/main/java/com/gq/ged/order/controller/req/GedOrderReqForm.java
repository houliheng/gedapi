package com.gq.ged.order.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * Created by wrh on 2018/1/19.
 */
@ApiModel
public class GedOrderReqForm {
    @ApiModelProperty(value = "企业名称")
    public String companyName;
    @ApiModelProperty(value = "公司类型")
    public String companyType;
    @ApiModelProperty(value = "借款金额")
    public BigDecimal loanAmount;
    @ApiModelProperty(value = "借款期限")
    public Integer loanTerm;
    @ApiModelProperty(value = "管理地址")
    public String managementAddr;
    @ApiModelProperty(value = "联系电话")
    public String contactPhone;
    @ApiModelProperty(value = "担保人")
    public Integer guaranteeFlag;
    @ApiModelProperty(value = "借款用途")
    private Integer loanPurpose;
    @ApiModelProperty(value = "借款类型")
    private Integer loanType;
    @ApiModelProperty(value = "标签库")
    public String OrderTags;
    @ApiModelProperty(value = "省")
    private Long provinceId;
    @ApiModelProperty(value = "市")
    private Long cityId;
    @ApiModelProperty(value = "区")
    private Long areaId;
    @ApiModelProperty(value = "社会信用统一代码")
    private String socialCreditCode;
    @ApiModelProperty(value = "营业执照编号")
    private String businessLicence;
    @ApiModelProperty(value = "税务登记号")
    private String taxCode;
    @ApiModelProperty(value = "组织机构代码")
    private String organizationCode;
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(Integer loanTerm) {
        this.loanTerm = loanTerm;
    }

    public String getManagementAddr() {
        return managementAddr;
    }

    public void setManagementAddr(String managementAddr) {
        this.managementAddr = managementAddr;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Integer getGuaranteeFlag() {
        return guaranteeFlag;
    }

    public void setGuaranteeFlag(Integer guaranteeFlag) {
        this.guaranteeFlag = guaranteeFlag;
    }

    public Integer getLoanPurpose() {
        return loanPurpose;
    }

    public void setLoanPurpose(Integer loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    public Integer getLoanType() {
        return loanType;
    }

    public void setLoanType(Integer loanType) {
        this.loanType = loanType;
    }

    public String getOrderTags() {
        return OrderTags;
    }

    public void setOrderTags(String orderTags) {
        OrderTags = orderTags;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
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
}
