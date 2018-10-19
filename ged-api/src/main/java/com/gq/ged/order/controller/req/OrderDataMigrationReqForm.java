package com.gq.ged.order.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * Created by wrh on 2018/5/22.
 */
@ApiModel
public class OrderDataMigrationReqForm {
    @ApiModelProperty(value = "申请编号")
    private String applyId;
    @ApiModelProperty(value = "子申请编号")
    private String applyIdChild;
    @ApiModelProperty(value = "合同号")
    private String contractCode;
    @ApiModelProperty(value = "证件号码")
    private String idNum;
    @ApiModelProperty(value = "申请日期")
    private String applyDate;
    @ApiModelProperty(value = "借款类型")
    private Integer productType;
    @ApiModelProperty(value = "联系电话")
    public String contactPhone;
    @ApiModelProperty(value = "企业名称")
    public String companyName;
    @ApiModelProperty(value = "企业证件类型")
    public String companyCardType;
    @ApiModelProperty(value = "企业证件号码")
    private String companyCardNum;
    @ApiModelProperty(value = "借款金额")
    public BigDecimal applyAmount;
    @ApiModelProperty(value = "借款期限")
    public Integer applyPeriod;
    @ApiModelProperty(value = "借款用途")
    private Integer loanPurpose;
    @ApiModelProperty(value = "省")
    private Long province;
    @ApiModelProperty(value = "市")
    private Long city;
    @ApiModelProperty(value = "县区")
    private Long district;
    @ApiModelProperty(value = "还款方式")
    private String repaymentStyle;
    @ApiModelProperty(value = "借款状态")
    private Integer status;
    @ApiModelProperty(value = "应收保证金")
    private BigDecimal receivableCashDeposit;
    @ApiModelProperty(value = "应收担保费")
    private BigDecimal receivableGuaranteeFee;
    @ApiModelProperty(value = "月利率")
    private BigDecimal rateDay;
    @ApiModelProperty(value = "实收保证金")
    private BigDecimal cashDeposit;
    @ApiModelProperty(value = "实收担保费")
    private BigDecimal guaranteeFee;
    @ApiModelProperty(value = "服务费")
    private BigDecimal serviceFee;
    @ApiModelProperty(value = "放款额度")
    private BigDecimal creditAmount;
    @ApiModelProperty(value = "批复期限")
    private String replyTerm;
    @ApiModelProperty(value = "服务方所在地")
    private int serviceProvinceId;
    @ApiModelProperty(value = "所在城市")
    private String contCity;
    @ApiModelProperty(value = "放款日期")
    private String loanDate;
    @ApiModelProperty(value = "用户角色1个人 2企业")
    private Integer userRole;
    @ApiModelProperty(value = "客户名称")
    private String custName;
    @ApiModelProperty(value = "法人名称")
    private String legalName;
    @ApiModelProperty(value = "法人手机号")
    private String legalMobile;
    @ApiModelProperty(value = "法人证件号码")
    private String legalCardNumber;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getApplyIdChild() {
        return applyIdChild;
    }

    public void setApplyIdChild(String applyIdChild) {
        this.applyIdChild = applyIdChild;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

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

    public String getCompanyCardNum() {
        return companyCardNum;
    }

    public void setCompanyCardNum(String companyCardNum) {
        this.companyCardNum = companyCardNum;
    }

    public BigDecimal getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(BigDecimal applyAmount) {
        this.applyAmount = applyAmount;
    }

    public Integer getApplyPeriod() {
        return applyPeriod;
    }

    public void setApplyPeriod(Integer applyPeriod) {
        this.applyPeriod = applyPeriod;
    }

    public Integer getLoanPurpose() {
        return loanPurpose;
    }

    public void setLoanPurpose(Integer loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    public Long getProvince() {
        return province;
    }

    public void setProvince(Long province) {
        this.province = province;
    }

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }

    public Long getDistrict() {
        return district;
    }

    public void setDistrict(Long district) {
        this.district = district;
    }

    public String getRepaymentStyle() {
        return repaymentStyle;
    }

    public void setRepaymentStyle(String repaymentStyle) {
        this.repaymentStyle = repaymentStyle;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getReceivableCashDeposit() {
        return receivableCashDeposit;
    }

    public void setReceivableCashDeposit(BigDecimal receivableCashDeposit) {
        this.receivableCashDeposit = receivableCashDeposit;
    }

    public BigDecimal getReceivableGuaranteeFee() {
        return receivableGuaranteeFee;
    }

    public void setReceivableGuaranteeFee(BigDecimal receivableGuaranteeFee) {
        this.receivableGuaranteeFee = receivableGuaranteeFee;
    }

    public BigDecimal getRateDay() {
        return rateDay;
    }

    public void setRateDay(BigDecimal rateDay) {
        this.rateDay = rateDay;
    }

    public BigDecimal getCashDeposit() {
        return cashDeposit;
    }

    public void setCashDeposit(BigDecimal cashDeposit) {
        this.cashDeposit = cashDeposit;
    }

    public BigDecimal getGuaranteeFee() {
        return guaranteeFee;
    }

    public void setGuaranteeFee(BigDecimal guaranteeFee) {
        this.guaranteeFee = guaranteeFee;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getReplyTerm() {
        return replyTerm;
    }

    public void setReplyTerm(String replyTerm) {
        this.replyTerm = replyTerm;
    }

    public int getServiceProvinceId() {
        return serviceProvinceId;
    }

    public void setServiceProvinceId(int serviceProvinceId) {
        this.serviceProvinceId = serviceProvinceId;
    }

    public String getContCity() {
        return contCity;
    }

    public void setContCity(String contCity) {
        this.contCity = contCity;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getLegalMobile() {
        return legalMobile;
    }

    public void setLegalMobile(String legalMobile) {
        this.legalMobile = legalMobile;
    }

    public String getLegalCardNumber() {
        return legalCardNumber;
    }

    public void setLegalCardNumber(String legalCardNumber) {
        this.legalCardNumber = legalCardNumber;
    }
}
