package com.gq.ged.order.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wrh on 2018/1/19.
 */
@ApiModel
public class GedOrderloanPushReqForm implements Serializable {
    @NotNull(message = "客户名称不能为空")
    @ApiModelProperty(value = "客户名称")
    private String custName;
    @NotNull(message = "证件号码不能为空")
    @ApiModelProperty(value = "证件号码")
    private String idNum;
    @ApiModelProperty(value = "申请日期")
    private String applyDate;
    @NotNull(message = "产品类型不能为空")
    @ApiModelProperty(value = "产品类型")
    private int productType;
    @NotNull(message = "产品名称不能为空")
    @ApiModelProperty(value = "产品名称")
    private String productName;
    @NotNull(message = "客户类型不能为空")
    @ApiModelProperty(value = "客户类型")
    private int custType;
    @NotNull(message = "移动电话不能为空")
    @ApiModelProperty(value = "移动电话")
    private String phoneNo;
    @NotNull(message = "申请金额不能为空")
    @ApiModelProperty(value = "申请金额")
    private BigDecimal applyAmount;
    @NotNull(message = "申请期限不能为空")
    @ApiModelProperty(value = "申请期限")
    private int applyPeriod;
    @NotNull(message = "企业名称不能为空")
    @ApiModelProperty(value = "企业名称")
    private String companyName;
    @NotNull(message = "企业证件类型不能为空")
    @ApiModelProperty(value = "企业证件类型")
    private String companyCardType;
    @NotNull(message = "企业证件号码不能为空")
    @ApiModelProperty(value = "企业证件号码")
    private String companyCardNum;
    @NotNull(message = "客户来源不能为空")
    @ApiModelProperty(value = "客户来源")
    private int custSource;
    @ApiModelProperty(value = "营业执照")
    private String businessLicence;
    @ApiModelProperty(value = "组织机构代码")
    private String organizationCode;
    @ApiModelProperty(value = "税务号")
    private String taxCode;
    @NotNull(message = "省不能为空")
    @ApiModelProperty(value = "省")
    private  String  	province;
    @NotNull(message = "市不能为空")
    @ApiModelProperty(value = "市")
    private  String city;
    @NotNull(message = "区不能为空")
    @ApiModelProperty(value = "县区")
    private  String  district;
    @ApiModelProperty(value = "所在城市")
    private String 	contCity;
    @NotNull(message = "申请ID不能为空")
    @ApiModelProperty(value = "申请ID")
    private String applyId;
    @ApiModelProperty(value = "借款用途")
    private int loanPurpost;
    @ApiModelProperty(value = "月利率")
    private BigDecimal approveMonthRate;
    @ApiModelProperty(value = "还款方式")
    private String repaymentStyle;
    @ApiModelProperty(value = "子订单编号")
    private String applyIdChild;
    @ApiModelProperty(value = "应收保证金")
    private BigDecimal receivableCashDeposit;
    @ApiModelProperty(value = "应收担保费")
    private BigDecimal receivableGuaranteeFee;
    @ApiModelProperty(value = "法人名称")
    private String legalName;
    @ApiModelProperty(value = "法人手机号")
    private String legalMobile;
    @ApiModelProperty(value = "法人证件号码")
    private String legalCardNumber;


    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
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

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCustType() {
        return custType;
    }

    public void setCustType(int custType) {
        this.custType = custType;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public BigDecimal getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(BigDecimal applyAmount) {
        this.applyAmount = applyAmount;
    }

    public int getApplyPeriod() {
        return applyPeriod;
    }

    public void setApplyPeriod(int applyPeriod) {
        this.applyPeriod = applyPeriod;
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

    public int getCustSource() {
        return custSource;
    }

    public void setCustSource(int custSource) {
        this.custSource = custSource;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getContCity() {
        return contCity;
    }

    public void setContCity(String contCity) {
        this.contCity = contCity;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getBusinessLicence() {
        return businessLicence;
    }

    public void setBusinessLicence(String businessLicence) {
        this.businessLicence = businessLicence;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public int getLoanPurpost() {
        return loanPurpost;
    }

    public void setLoanPurpost(int loanPurpost) {
        this.loanPurpost = loanPurpost;
    }

    public BigDecimal getApproveMonthRate() {
        return approveMonthRate;
    }

    public void setApproveMonthRate(BigDecimal approveMonthRate) {
        this.approveMonthRate = approveMonthRate;
    }

    public String getRepaymentStyle() {
        return repaymentStyle;
    }

    public void setRepaymentStyle(String repaymentStyle) {
        this.repaymentStyle = repaymentStyle;
    }

    public String getApplyIdChild() {
        return applyIdChild;
    }

    public void setApplyIdChild(String applyIdChild) {
        this.applyIdChild = applyIdChild;
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
