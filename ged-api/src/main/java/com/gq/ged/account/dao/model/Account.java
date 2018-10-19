package com.gq.ged.account.dao.model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel
public class Account  implements Serializable {
    @ApiModelProperty("主键")
    private Long id;

    private String accountCode;

    private Long userId;

    private String custId;

    private String corporationName;
    @ApiModelProperty("手机号")
    private String corporationPhone;
    @ApiModelProperty("身份证")
    private String corporationCardNum;
    @ApiModelProperty("公司名称")
    private String companyName;
    @ApiModelProperty("社会统一代码")
    private String socialCreditCode;
    @ApiModelProperty("银行code")
    private String companyBankOfDeposit;
    @ApiModelProperty("银行名称")
    private String companyBankOfDepositValue;
    @ApiModelProperty("银行卡号")
    private String companyAccount;
    @ApiModelProperty("支行名称")
    private String companyBankBranchName;
    @ApiModelProperty("省编码")
    private String provinceCode;
    @ApiModelProperty("市编码")
    private String cityCode;
    @ApiModelProperty("区编码")
    private String areaCode;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("状态")
    private Integer status;

    private Long createId;

    private Date createTime;

    private Long modifyId;

    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode == null ? null : accountCode.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

    public String getCorporationName() {
        return corporationName;
    }

    public void setCorporationName(String corporationName) {
        this.corporationName = corporationName == null ? null : corporationName.trim();
    }

    public String getCorporationPhone() {
        return corporationPhone;
    }

    public void setCorporationPhone(String corporationPhone) {
        this.corporationPhone = corporationPhone == null ? null : corporationPhone.trim();
    }

    public String getCorporationCardNum() {
        return corporationCardNum;
    }

    public void setCorporationCardNum(String corporationCardNum) {
        this.corporationCardNum = corporationCardNum == null ? null : corporationCardNum.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode == null ? null : socialCreditCode.trim();
    }

    public String getCompanyBankOfDeposit() {
        return companyBankOfDeposit;
    }

    public void setCompanyBankOfDeposit(String companyBankOfDeposit) {
        this.companyBankOfDeposit = companyBankOfDeposit == null ? null : companyBankOfDeposit.trim();
    }

    public String getCompanyBankOfDepositValue() {
        return companyBankOfDepositValue;
    }

    public void setCompanyBankOfDepositValue(String companyBankOfDepositValue) {
        this.companyBankOfDepositValue = companyBankOfDepositValue == null ? null : companyBankOfDepositValue.trim();
    }

    public String getCompanyAccount() {
        return companyAccount;
    }

    public void setCompanyAccount(String companyAccount) {
        this.companyAccount = companyAccount == null ? null : companyAccount.trim();
    }

    public String getCompanyBankBranchName() {
        return companyBankBranchName;
    }

    public void setCompanyBankBranchName(String companyBankBranchName) {
        this.companyBankBranchName = companyBankBranchName == null ? null : companyBankBranchName.trim();
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getModifyId() {
        return modifyId;
    }

    public void setModifyId(Long modifyId) {
        this.modifyId = modifyId;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}