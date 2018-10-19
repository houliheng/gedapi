package com.resoft.credit.guranteeProjectList.entity;


import java.math.BigDecimal;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
* @author guoshaohua
* @version 2018年4月19日 下午2:16:45
* 
*/
public class GuranteeProjectList extends DataEntity<GuranteeProjectList>{
	private String contractNo;//合同号
	private String borrowName;//借款人名称
	private BigDecimal borrowMoney;//借款金额
	private String periodValue;//期限
	private String yearRate;//利率
	private String currentPeriod;//当前期数
	private String applyStatus;//申请状态
	private BigDecimal bail;//保证金
	private BigDecimal guranteeServicceFee;//担保服务费
	private String compensatoryStatus;//代偿状态
	private BigDecimal compensatoryMoney;//代偿金额
	private BigDecimal stayMoney;//待还金额
	private String loanStatus;//放款状态
	private String accountStatus;//账务状态
	private String paymentStatus;//缴费状态
	private String compentStatus;//代偿状态
	private String mobilePhone;//借款人手机号
	private String guranteeId;//担保公司id
	private String guranteeCompany;//担保公司名称
	private String approveLoanRepayType;//还款方式
	private String guranteeGold;//担保金实缴
	private String guranteeGoldFlag;//担保金状态
	private String guranteeServicceFeeFlag;//担保服务费状态
	private String flag;//等级
	private String custId;
	private String applyNo;
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getBorrowName() {
		return borrowName;
	}
	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}
	public BigDecimal getBorrowMoney() {
		return borrowMoney;
	}
	public void setBorrowMoney(BigDecimal borrowMoney) {
		this.borrowMoney = borrowMoney;
	}
	public String getPeriodValue() {
		return periodValue;
	}
	public void setPeriodValue(String periodValue) {
		this.periodValue = periodValue;
	}
	public String getYearRate() {
		return yearRate;
	}
	public void setYearRate(String yearRate) {
		this.yearRate = yearRate;
	}
	public String getCurrentPeriod() {
		return currentPeriod;
	}
	public void setCurrentPeriod(String currentPeriod) {
		this.currentPeriod = currentPeriod;
	}
	
	public BigDecimal getBail() {
		return bail;
	}
	public void setBail(BigDecimal bail) {
		this.bail = bail;
	}
	public BigDecimal getGuranteeServicceFee() {
		return guranteeServicceFee;
	}
	public void setGuranteeServicceFee(BigDecimal guranteeServicceFee) {
		this.guranteeServicceFee = guranteeServicceFee;
	}
	public String getCompensatoryStatus() {
		return compensatoryStatus;
	}
	public void setCompensatoryStatus(String compensatoryStatus) {
		this.compensatoryStatus = compensatoryStatus;
	}
	public BigDecimal getCompensatoryMoney() {
		return compensatoryMoney;
	}
	public void setCompensatoryMoney(BigDecimal compensatoryMoney) {
		this.compensatoryMoney = compensatoryMoney;
	}
	public BigDecimal getStayMoney() {
		return stayMoney;
	}
	public void setStayMoney(BigDecimal stayMoney) {
		this.stayMoney = stayMoney;
	}
	public String getApplyStatus() {
		return applyStatus;
	}
	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getCompentStatus() {
		return compentStatus;
	}
	public void setCompentStatus(String compentStatus) {
		this.compentStatus = compentStatus;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getGuranteeId() {
		return guranteeId;
	}
	public void setGuranteeId(String guranteeId) {
		this.guranteeId = guranteeId;
	}
	public String getGuranteeCompany() {
		return guranteeCompany;
	}
	public void setGuranteeCompany(String guranteeCompany) {
		this.guranteeCompany = guranteeCompany;
	}
	public String getApproveLoanRepayType() {
		return approveLoanRepayType;
	}
	public void setApproveLoanRepayType(String approveLoanRepayType) {
		this.approveLoanRepayType = approveLoanRepayType;
	}
	public String getGuranteeGold() {
		return guranteeGold;
	}
	public void setGuranteeGold(String guranteeGold) {
		this.guranteeGold = guranteeGold;
	}
	public String getGuranteeGoldFlag() {
		return guranteeGoldFlag;
	}
	public void setGuranteeGoldFlag(String guranteeGoldFlag) {
		this.guranteeGoldFlag = guranteeGoldFlag;
	}
	public String getGuranteeServicceFeeFlag() {
		return guranteeServicceFeeFlag;
	}
	public void setGuranteeServicceFeeFlag(String guranteeServicceFeeFlag) {
		this.guranteeServicceFeeFlag = guranteeServicceFeeFlag;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	
}
