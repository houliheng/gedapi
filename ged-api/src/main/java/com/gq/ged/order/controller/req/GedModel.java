package com.gq.ged.order.controller.req;

import java.math.BigDecimal;
import java.util.Date;

public class GedModel {
    private Integer id;

    private Integer userId;

    private Integer getCustId;

    private String contractCode;

    private String companyName;

    private String companyType;

    private String companyCardNum;

    private Integer personCardType;

    private String personCardNum;

    private Integer loanPurpose;

    private Integer loanType;

    private BigDecimal loanAmount;

    private String otherLoanAmount;

    private Integer loanTerm;

    private BigDecimal creditAmount;

    private BigDecimal replyAmount;

    private Integer replyTerm;

    private Integer withdrawFlag;

    private BigDecimal withdrawAmount;

    private BigDecimal rateDay;

    private Long provinceId;

    private Long cityId;

    private Long areaId;

    private String managementAddr;

    private String contactPhone;

    private String contractSignProvince;

    private String contractSignCity;

    private String contractSignArea;

    private Integer guaranteeFlag;

    private String recommendCode;

    private BigDecimal receivableCashDeposit;

    private BigDecimal receivableGuaranteeFee;

    private BigDecimal cashDeposit;

    private BigDecimal guaranteeFee;

    private Integer serviceFeeWay;

    private BigDecimal serviceFee;

    private Integer serviceFeeFlag;

    private Integer serviceFeeResult;

    private Integer guaranteeFeeFlag;

    private BigDecimal accountManageFee;

    private String repaymentStyle;

    private Integer serviceProvinceId;

    private Integer sysFlag;

    private String branchOffice;

    private String regionOffice;

    private Integer readFlag;

    private Integer repaymentLockFlag;

    private Integer stockFlag;

    private Integer signContractFlag;

    private Integer status;

    private Date loanDate;

    private BigDecimal terraceServiceFee;

    private BigDecimal consultServiceFee;

    private Integer userType;

    private String userName;

    private String companyAccount;
    //借款用途
    private String loanpose;

    private String password;

    private String orderCode;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoanpose() {
        return loanpose;
    }

    public void setLoanpose(String loanpose) {
        this.loanpose = loanpose;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode == null ? null : contractCode.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType == null ? null : companyType.trim();
    }

    public String getCompanyCardNum() {
        return companyCardNum;
    }

    public void setCompanyCardNum(String companyCardNum) {
        this.companyCardNum = companyCardNum == null ? null : companyCardNum.trim();
    }

    public Integer getPersonCardType() {
        return personCardType;
    }

    public void setPersonCardType(Integer personCardType) {
        this.personCardType = personCardType;
    }

    public String getPersonCardNum() {
        return personCardNum;
    }

    public void setPersonCardNum(String personCardNum) {
        this.personCardNum = personCardNum == null ? null : personCardNum.trim();
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

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getOtherLoanAmount() {
        return otherLoanAmount;
    }

    public void setOtherLoanAmount(String otherLoanAmount) {
        this.otherLoanAmount = otherLoanAmount == null ? null : otherLoanAmount.trim();
    }

    public Integer getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(Integer loanTerm) {
        this.loanTerm = loanTerm;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public BigDecimal getReplyAmount() {
        return replyAmount;
    }

    public void setReplyAmount(BigDecimal replyAmount) {
        this.replyAmount = replyAmount;
    }

    public Integer getReplyTerm() {
        return replyTerm;
    }

    public void setReplyTerm(Integer replyTerm) {
        this.replyTerm = replyTerm;
    }

    public Integer getWithdrawFlag() {
        return withdrawFlag;
    }

    public void setWithdrawFlag(Integer withdrawFlag) {
        this.withdrawFlag = withdrawFlag;
    }

    public BigDecimal getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(BigDecimal withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public BigDecimal getRateDay() {
        return rateDay;
    }

    public void setRateDay(BigDecimal rateDay) {
        this.rateDay = rateDay;
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

    public String getManagementAddr() {
        return managementAddr;
    }

    public void setManagementAddr(String managementAddr) {
        this.managementAddr = managementAddr == null ? null : managementAddr.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public String getContractSignProvince() {
        return contractSignProvince;
    }

    public void setContractSignProvince(String contractSignProvince) {
        this.contractSignProvince = contractSignProvince == null ? null : contractSignProvince.trim();
    }

    public String getContractSignCity() {
        return contractSignCity;
    }

    public void setContractSignCity(String contractSignCity) {
        this.contractSignCity = contractSignCity == null ? null : contractSignCity.trim();
    }

    public String getContractSignArea() {
        return contractSignArea;
    }

    public void setContractSignArea(String contractSignArea) {
        this.contractSignArea = contractSignArea == null ? null : contractSignArea.trim();
    }

    public Integer getGuaranteeFlag() {
        return guaranteeFlag;
    }

    public void setGuaranteeFlag(Integer guaranteeFlag) {
        this.guaranteeFlag = guaranteeFlag;
    }

    public String getRecommendCode() {
        return recommendCode;
    }

    public void setRecommendCode(String recommendCode) {
        this.recommendCode = recommendCode == null ? null : recommendCode.trim();
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

    public Integer getServiceFeeWay() {
        return serviceFeeWay;
    }

    public void setServiceFeeWay(Integer serviceFeeWay) {
        this.serviceFeeWay = serviceFeeWay;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Integer getServiceFeeFlag() {
        return serviceFeeFlag;
    }

    public void setServiceFeeFlag(Integer serviceFeeFlag) {
        this.serviceFeeFlag = serviceFeeFlag;
    }

    public Integer getServiceFeeResult() {
        return serviceFeeResult;
    }

    public void setServiceFeeResult(Integer serviceFeeResult) {
        this.serviceFeeResult = serviceFeeResult;
    }

    public Integer getGuaranteeFeeFlag() {
        return guaranteeFeeFlag;
    }

    public void setGuaranteeFeeFlag(Integer guaranteeFeeFlag) {
        this.guaranteeFeeFlag = guaranteeFeeFlag;
    }

    public BigDecimal getAccountManageFee() {
        return accountManageFee;
    }

    public void setAccountManageFee(BigDecimal accountManageFee) {
        this.accountManageFee = accountManageFee;
    }

    public String getRepaymentStyle() {
        return repaymentStyle;
    }

    public void setRepaymentStyle(String repaymentStyle) {
        this.repaymentStyle = repaymentStyle == null ? null : repaymentStyle.trim();
    }

    public Integer getServiceProvinceId() {
        return serviceProvinceId;
    }

    public void setServiceProvinceId(Integer serviceProvinceId) {
        this.serviceProvinceId = serviceProvinceId;
    }

    public Integer getSysFlag() {
        return sysFlag;
    }

    public void setSysFlag(Integer sysFlag) {
        this.sysFlag = sysFlag;
    }

    public String getBranchOffice() {
        return branchOffice;
    }

    public void setBranchOffice(String branchOffice) {
        this.branchOffice = branchOffice == null ? null : branchOffice.trim();
    }

    public String getRegionOffice() {
        return regionOffice;
    }

    public void setRegionOffice(String regionOffice) {
        this.regionOffice = regionOffice == null ? null : regionOffice.trim();
    }

    public Integer getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(Integer readFlag) {
        this.readFlag = readFlag;
    }

    public Integer getRepaymentLockFlag() {
        return repaymentLockFlag;
    }

    public void setRepaymentLockFlag(Integer repaymentLockFlag) {
        this.repaymentLockFlag = repaymentLockFlag;
    }

    public Integer getStockFlag() {
        return stockFlag;
    }

    public void setStockFlag(Integer stockFlag) {
        this.stockFlag = stockFlag;
    }

    public Integer getSignContractFlag() {
        return signContractFlag;
    }

    public void setSignContractFlag(Integer signContractFlag) {
        this.signContractFlag = signContractFlag;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getTerraceServiceFee() {
        return terraceServiceFee;
    }

    public void setTerraceServiceFee(BigDecimal terraceServiceFee) {
        this.terraceServiceFee = terraceServiceFee;
    }

    public BigDecimal getConsultServiceFee() {
        return consultServiceFee;
    }

    public void setConsultServiceFee(BigDecimal consultServiceFee) {
        this.consultServiceFee = consultServiceFee;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCompanyAccount() {
        return companyAccount;
    }

    public void setCompanyAccount(String companyAccount) {
        this.companyAccount = companyAccount;
    }

    public Integer getGetCustId() {
        return getCustId;
    }

    public void setGetCustId(Integer getCustId) {
        this.getCustId = getCustId;
    }
}