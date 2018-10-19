package com.resoft.credit.GedImportGedOrder.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 冠e通线下数据导入给冠e贷Entity
 * @author lb
 * @version 2018-07-18
 */
public class CreGedImportGetOrder extends DataEntity<CreGedImportGetOrder> {
	

	private String contractNo;		// 合同号
	private String orderCode;		// 订单编号
	private String companyName;		// 企业名称
	private String companyCardNo;		// 社会统一代码
	private String idNumber;		// 身份证
	private String loanPurpose;		// 借款用途
	private String loanType;		// 借款类型
	private String loanAmount;		// 借款金额
	private String creditAmount;		// 放款金额
	private String loanTerm;		// 借款期限
	private String approTerm;		// 审批期限
	private Date loanDate;		// 借款日期
	private String monthRate;		// 月利率
	private String address;		// 地址
	private String contractPhone;		// 联系电话
	private String cashDeposit;		// 保证金
	private String serviceFee;		// 服务费
	private String serviceFeeWay;		// 服务费支付方式
	private String manageFee;		// 管理费
	private String repaymentType;		// 还款方式
	private String status;		// 订单状态
	private String accountCode;		// 账户code
	private String userName;		// 用户名称
	private String userType;		// 用户类型
	private String custId;		// 客户Id
	private String cityCode;		// 城市code
	private String bankBranchName;		// 支行名称
	private String handleStatus;		// 处理状态0未处理1成功2失败
	private String bankCode;//银行码
	private String batchDate;//导入日期格式 2018-09-10
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCardNo() {
		return companyCardNo;
	}

	public void setCompanyCardNo(String companyCardNo) {
		this.companyCardNo = companyCardNo;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getLoanPurpose() {
		return loanPurpose;
	}

	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}

	public String getLoanTerm() {
		return loanTerm;
	}

	public void setLoanTerm(String loanTerm) {
		this.loanTerm = loanTerm;
	}

	public String getApproTerm() {
		return approTerm;
	}

	public void setApproTerm(String approTerm) {
		this.approTerm = approTerm;
	}

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	public String getMonthRate() {
		return monthRate;
	}

	public void setMonthRate(String monthRate) {
		this.monthRate = monthRate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContractPhone() {
		return contractPhone;
	}

	public void setContractPhone(String contractPhone) {
		this.contractPhone = contractPhone;
	}

	public String getCashDeposit() {
		return cashDeposit;
	}

	public void setCashDeposit(String cashDeposit) {
		this.cashDeposit = cashDeposit;
	}

	public String getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}

	public String getServiceFeeWay() {
		return serviceFeeWay;
	}

	public void setServiceFeeWay(String serviceFeeWay) {
		this.serviceFeeWay = serviceFeeWay;
	}

	public String getManageFee() {
		return manageFee;
	}

	public void setManageFee(String manageFee) {
		this.manageFee = manageFee;
	}

	public String getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getBankBranchName() {
		return bankBranchName;
	}

	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	public String getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBatchDate() {
		return batchDate;
	}

	public void setBatchDate(String batchDate) {
		this.batchDate = batchDate;
	}
	
	
}