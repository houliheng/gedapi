package com.resoft.outinterface.rest.ged.entity;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;


/**
* @author guoshaohua
* @version 2018年4月20日 上午11:35:24
* 
*/
@JsonAutoDetect(fieldVisibility = Visibility.PUBLIC_ONLY)
public class ContractRepayPlanDetail implements Serializable{
	private static final Long SerialVersionUIDAdder = 1L;
	private String contractNo;//合同号
	private String loanDate;//放款日期
	private String contractMoney;//合同金额
	private String peroidValue;//期限
	private List<RepayPalnDetail>  repayPalnDetails;//计划明细
	private String contractNoStayMoney;//待还金额
	private String contractStayMoney;//待还金额
	private String contractStayOverdueMoney;//逾期违约金金额
	private String contractStayFine;//逾期罚息金额
	private String contractManage;//合同管理费总计
	private String contractService;//合同服务费总计
	private String contractRepayAmount;//合同应总金额
	private String contractFactRepayAmount;//合同实还总金额
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	public String getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}
	public String getContractMoney() {
		return contractMoney;
	}
	public void setContractMoney(String contractMoney) {
		this.contractMoney = contractMoney;
	}
	public String getPeroidValue() {
		return peroidValue;
	}
	public void setPeroidValue(String peroidValue) {
		this.peroidValue = peroidValue;
	}
	public List<RepayPalnDetail> getRepayPalnDetails() {
		return repayPalnDetails;
	}
	public void setRepayPalnDetails(List<RepayPalnDetail> repayPalnDetails) {
		this.repayPalnDetails = repayPalnDetails;
	}
	
	public String getContractStayMoney() {
		return contractStayMoney;
	}
	public void setContractStayMoney(String contractStayMoney) {
		this.contractStayMoney = contractStayMoney;
	}
	public String getContractStayOverdueMoney() {
		return contractStayOverdueMoney;
	}
	public void setContractStayOverdueMoney(String contractStayOverdueMoney) {
		this.contractStayOverdueMoney = contractStayOverdueMoney;
	}
	public String getContractStayFine() {
		return contractStayFine;
	}
	public void setContractStayFine(String contractStayFine) {
		this.contractStayFine = contractStayFine;
	}
	public String getContractNoStayMoney() {
		return contractNoStayMoney;
	}
	public void setContractNoStayMoney(String contractNoStayMoney) {
		this.contractNoStayMoney = contractNoStayMoney;
	}
	public String getContractManage() {
		return contractManage;
	}
	public void setContractManage(String contractManage) {
		this.contractManage = contractManage;
	}
	public String getContractService() {
		return contractService;
	}
	public void setContractService(String contractService) {
		this.contractService = contractService;
	}
	public String getContractRepayAmount() {
		return contractRepayAmount;
	}
	public void setContractRepayAmount(String contractRepayAmount) {
		this.contractRepayAmount = contractRepayAmount;
	}
	public String getContractFactRepayAmount() {
		return contractFactRepayAmount;
	}
	public void setContractFactRepayAmount(String contractFactRepayAmount) {
		this.contractFactRepayAmount = contractFactRepayAmount;
	}
	
	
	
}
