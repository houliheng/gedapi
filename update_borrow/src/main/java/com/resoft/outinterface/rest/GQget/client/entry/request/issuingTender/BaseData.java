package com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

/**
 * 用户数据
 */
@JsonAutoDetect(fieldVisibility = Visibility.PUBLIC_ONLY)
public class BaseData {
	private String bid_offline_id;
	private String bid_title;
	private String contract_no;
	private int loan_type;
	private BigDecimal bid_amount;
	private int period;
	private int bid_repayment_type;
	private int contract_repayment_type;
	private BigDecimal quality_service_deposit;
	private BigDecimal service_fee;
	private BigDecimal security_amount;
	private BigDecimal risk_prepare_amount;
	private String borrow_contract_date;
	private String mortgage_contract_date;
	private String filiale_name;
	private String bid_year_irr;
	private String contract_year_irr;
	private String area;
	private String account_no;
	private String creditor_company;

	private String jointcredit_key;
	private int jointcredit_count;
	private Integer bidOfflineType;//区分新老系统
	protected BigDecimal getQualityServiceDeposit() {
		return quality_service_deposit;
	}

	protected void setQualityServiceDeposit(BigDecimal quality_service_deposit) {
		this.quality_service_deposit = quality_service_deposit;
	}

	public BigDecimal getQuality_service_deposit() {
		return quality_service_deposit;
	}

	public void setQuality_service_deposit(BigDecimal quality_service_deposit) {
		this.quality_service_deposit = quality_service_deposit;
	}

	public BigDecimal getService_fee() {
		return service_fee;
	}

	public void setService_fee(BigDecimal service_fee) {
		this.service_fee = service_fee;
	}

	public BigDecimal getSecurity_amount() {
		return security_amount;
	}

	public void setSecurity_amount(BigDecimal security_amount) {
		this.security_amount = security_amount;
	}

	public BigDecimal getRisk_prepare_amount() {
		return risk_prepare_amount;
	}

	public void setRisk_prepare_amount(BigDecimal risk_prepare_amount) {
		this.risk_prepare_amount = risk_prepare_amount;
	}

	public String getBorrow_contract_date() {
		return borrow_contract_date;
	}

	public void setBorrow_contract_date(String borrow_contract_date) {
		this.borrow_contract_date = borrow_contract_date;
	}

	public String getMortgage_contract_date() {
		return mortgage_contract_date;
	}

	public void setMortgage_contract_date(String mortgage_contract_date) {
		this.mortgage_contract_date = mortgage_contract_date;
	}

	public String getJointcredit_key() {
		return jointcredit_key;
	}

	public void setJointcredit_key(String jointcredit_key) {
		this.jointcredit_key = jointcredit_key;
	}

	public int getJointcredit_count() {
		return jointcredit_count;
	}

	public void setJointcredit_count(int jointcredit_count) {
		this.jointcredit_count = jointcredit_count;
	}

	protected String getJointcreditKey() {
		return jointcredit_key;
	}

	protected void setJointcreditKey(String jointcredit_key) {
		this.jointcredit_key = jointcredit_key;
	}

	protected int getJointcreditCount() {
		return jointcredit_count;
	}

	protected void setJointcreditCount(int jointcredit_count) {
		this.jointcredit_count = jointcredit_count;
	}

	protected BigDecimal getServiceFee() {
		return service_fee;
	}

	protected void setServiceFee(BigDecimal service_fee) {
		this.service_fee = service_fee;
	}

	protected BigDecimal getSecurityAmount() {
		return security_amount;
	}

	protected void setSecurityAmount(BigDecimal security_amount) {
		this.security_amount = security_amount;
	}

	protected BigDecimal getRiskPrepareAmount() {
		return risk_prepare_amount;
	}

	protected void setRiskPrepareAmount(BigDecimal risk_prepare_amount) {
		this.risk_prepare_amount = risk_prepare_amount;
	}

	protected String getBorrowContractDate() {
		return borrow_contract_date;
	}

	protected void setBorrowContractDate(String borrow_contract_date) {
		this.borrow_contract_date = borrow_contract_date;
	}

	protected String getMortgageContractDate() {
		return mortgage_contract_date;
	}

	protected void setMortgageContractDate(String mortgage_contract_date) {
		this.mortgage_contract_date = mortgage_contract_date;
	}

	public String getContract_year_irr() {
		return contract_year_irr;
	}

	public void setContract_year_irr(String contract_year_irr) {
		this.contract_year_irr = contract_year_irr;
	}

	protected String getContractYearIrr() {
		return contract_year_irr;
	}

	protected void setContractYearIrr(String contract_year_irr) {
		this.contract_year_irr = contract_year_irr;
	}

	public String getCreditor_company() {
		return creditor_company;
	}

	public void setCreditor_company(String creditor_company) {
		this.creditor_company = creditor_company;
	}

	protected String getCreditorCompany() {
		return creditor_company;
	}

	protected void setCreditorCompany(String creditor_company) {
		this.creditor_company = creditor_company;
	}

	public String getBid_year_irr() {
		return bid_year_irr;
	}

	public void setBid_year_irr(String bid_year_irr) {
		this.bid_year_irr = bid_year_irr;
	}

	protected String getBidYearIrr() {
		return bid_year_irr;
	}

	protected void setBidYearIrr(String bid_year_irr) {
		this.bid_year_irr = bid_year_irr;
	}

	protected String getBidOfflineId() {
		return bid_offline_id;
	}

	protected void setBidOfflineId(String bid_offline_id) {
		this.bid_offline_id = bid_offline_id;
	}

	protected String getBidTitle() {
		return bid_title;
	}

	protected void setBidTitle(String bid_title) {
		this.bid_title = bid_title;
	}

	protected String getContractNo() {
		return contract_no;
	}

	protected void setContractNo(String contract_no) {
		this.contract_no = contract_no;
	}

	protected int getLoanType() {
		return loan_type;
	}

	protected void setLoanType(int loan_type) {
		this.loan_type = loan_type;
	}

	protected BigDecimal getBidAmount() {
		return bid_amount;
	}

	protected void setBidAmount(BigDecimal bid_amount) {
		this.bid_amount = bid_amount;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	protected int getBidRepaymentType() {
		return bid_repayment_type;
	}

	protected void setBidRepaymentType(int bid_repayment_type) {
		this.bid_repayment_type = bid_repayment_type;
	}

	protected int getContractRepaymentType() {
		return contract_repayment_type;
	}

	protected void setContractRepaymentType(int contract_repayment_type) {
		this.contract_repayment_type = contract_repayment_type;
	}

	protected String getFilialeName() {
		return filiale_name;
	}

	protected void setFilialeName(String filiale_name) {
		this.filiale_name = filiale_name;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	protected String getAccountNo() {
		return account_no;
	}

	protected void setAccountNo(String account_no) {
		this.account_no = account_no;
	}

	public String getBid_offline_id() {
		return bid_offline_id;
	}

	public void setBid_offline_id(String bid_offline_id) {
		this.bid_offline_id = bid_offline_id;
	}

	public String getBid_title() {
		return bid_title;
	}

	public void setBid_title(String bid_title) {
		this.bid_title = bid_title;
	}

	public String getContract_no() {
		return contract_no;
	}

	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}

	public int getLoan_type() {
		return loan_type;
	}

	public void setLoan_type(int loan_type) {
		this.loan_type = loan_type;
	}

	public BigDecimal getBid_amount() {
		return bid_amount;
	}

	public void setBid_amount(BigDecimal bid_amount) {
		this.bid_amount = bid_amount;
	}

	public int getBid_repayment_type() {
		return bid_repayment_type;
	}

	public void setBid_repayment_type(int bid_repayment_type) {
		this.bid_repayment_type = bid_repayment_type;
	}

	public int getContract_repayment_type() {
		return contract_repayment_type;
	}

	public void setContract_repayment_type(int contract_repayment_type) {
		this.contract_repayment_type = contract_repayment_type;
	}

	public String getFiliale_name() {
		return filiale_name;
	}

	public void setFiliale_name(String filiale_name) {
		this.filiale_name = filiale_name;
	}

	public String getAccount_no() {
		return account_no;
	}

	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}

	public Integer getBidOfflineType() {
		return bidOfflineType;
	}

	public void setBidOfflineType(Integer bidOfflineType) {
		this.bidOfflineType = bidOfflineType;
	}
	
	
}
