package com.resoft.accounting.discountStream.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
* @author guoshaohua
* @version 2018年5月29日 下午5:34:08
* 
*/
public class ContractDetailVo extends DataEntity<ContractDetailVo>{
	private static final long serialVersionUID = 1L;
	private String contractNo;//合同编号
	private String custName;//企业名称
	private String discountFee;//应贴息总金额
	private String factDiscountFee;//已贴息总金额
	private String stayMoney;//客户待还总金额
	private String factMoney;//客户实还总金额
	private String repayMoney;//当期应还本息和
	private String borrowLoanStatus;//借款状态
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getDiscountFee() {
		return discountFee;
	}
	public void setDiscountFee(String discountFee) {
		this.discountFee = discountFee;
	}
	public String getFactDiscountFee() {
		return factDiscountFee;
	}
	public void setFactDiscountFee(String factDiscountFee) {
		this.factDiscountFee = factDiscountFee;
	}
	public String getStayMoney() {
		return stayMoney;
	}
	public void setStayMoney(String stayMoney) {
		this.stayMoney = stayMoney;
	}
	public String getFactMoney() {
		return factMoney;
	}
	public void setFactMoney(String factMoney) {
		this.factMoney = factMoney;
	}
	public String getRepayMoney() {
		return repayMoney;
	}
	public void setRepayMoney(String repayMoney) {
		this.repayMoney = repayMoney;
	}
	public String getBorrowLoanStatus() {
		return borrowLoanStatus;
	}
	public void setBorrowLoanStatus(String borrowLoanStatus) {
		this.borrowLoanStatus = borrowLoanStatus;
	}
	
	
	
}
