package com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.PUBLIC_ONLY)
public class GetClientBidDataRequest {
	private BaseData base_data;
	private LoanCustInfo loan_custinfo;
	private HypothecariusCustInfo hypothecarius_custinfo;
	private MiddleCustInfo thrid_custinfo;
	private CreditInfo credit_info;
	private List<CreditAutomobile> credit_automobile;
	private List<CreditHouse> credit_house;
	private List<CreditAppliance> credit_appliance;
	private List<CreditSurety> credit_surety;
	private List<CreditCompany> credit_company;
	private List<CreditAdjunt> credit_adjunt;
	private List<BidRepayment> bid_repayment;
	private List<CreditOther> credit_other;
	private LoanCompany loan_company;

	public List<CreditOther> getCredit_other() {
		return credit_other;
	}

	public void setCredit_other(List<CreditOther> credit_other) {
		this.credit_other = credit_other;
	}

	public LoanCompany getLoan_company() {
		return loan_company;
	}

	public void setLoan_company(LoanCompany loan_company) {
		this.loan_company = loan_company;
	}

	public MiddleCustInfo getThrid_custinfo() {
		return thrid_custinfo;
	}

	public void setThrid_custinfo(MiddleCustInfo thrid_custinfo) {
		this.thrid_custinfo = thrid_custinfo;
	}

	public BaseData getBase_data() {
		return base_data;
	}

	public void setBase_data(BaseData base_data) {
		this.base_data = base_data;
	}

	public LoanCustInfo getLoan_custinfo() {
		return loan_custinfo;
	}

	public void setLoan_custinfo(LoanCustInfo loan_custinfo) {
		this.loan_custinfo = loan_custinfo;
	}

	public HypothecariusCustInfo getHypothecarius_custinfo() {
		return hypothecarius_custinfo;
	}

	public void setHypothecarius_custinfo(HypothecariusCustInfo hypothecarius_custinfo) {
		this.hypothecarius_custinfo = hypothecarius_custinfo;
	}

	public CreditInfo getCredit_info() {
		return credit_info;
	}

	public void setCredit_info(CreditInfo credit_info) {
		this.credit_info = credit_info;
	}

	public List<CreditAutomobile> getCredit_automobile() {
		return credit_automobile;
	}

	public void setCredit_automobile(List<CreditAutomobile> credit_automobile) {
		this.credit_automobile = credit_automobile;
	}

	public List<CreditHouse> getCredit_house() {
		return credit_house;
	}

	public void setCredit_house(List<CreditHouse> credit_house) {
		this.credit_house = credit_house;
	}

	public List<CreditAppliance> getCredit_appliance() {
		return credit_appliance;
	}

	public void setCredit_appliance(List<CreditAppliance> credit_appliance) {
		this.credit_appliance = credit_appliance;
	}

	public List<CreditSurety> getCredit_surety() {
		return credit_surety;
	}

	public void setCredit_surety(List<CreditSurety> credit_surety) {
		this.credit_surety = credit_surety;
	}

	public List<CreditCompany> getCredit_company() {
		return credit_company;
	}

	public void setCredit_company(List<CreditCompany> credit_company) {
		this.credit_company = credit_company;
	}

	public List<CreditAdjunt> getCredit_adjunt() {
		return credit_adjunt;
	}

	public void setCredit_adjunt(List<CreditAdjunt> credit_adjunt) {
		this.credit_adjunt = credit_adjunt;
	}

	public List<BidRepayment> getBid_repayment() {
		return bid_repayment;
	}

	public void setBid_repayment(List<BidRepayment> bid_repayment) {
		this.bid_repayment = bid_repayment;
	}

	BaseData getBaseData() {
		return base_data;
	}

	void setBaseData(BaseData base_data) {
		this.base_data = base_data;
	}

	LoanCustInfo getLoanCustinfo() {
		return loan_custinfo;
	}

	void setLoanCustinfo(LoanCustInfo loan_custinfo) {
		this.loan_custinfo = loan_custinfo;
	}

	HypothecariusCustInfo getHypothecariusCustinfo() {
		return hypothecarius_custinfo;
	}

	void setHypothecariusCustinfo(HypothecariusCustInfo hypothecarius_custinfo) {
		this.hypothecarius_custinfo = hypothecarius_custinfo;
	}

	CreditInfo getCreditInfo() {
		return credit_info;
	}

	void setCreditInfo(CreditInfo credit_info) {
		this.credit_info = credit_info;
	}

	List<CreditAutomobile> getCreditAutomobile() {
		return credit_automobile;
	}

	void setCreditAutomobile(List<CreditAutomobile> credit_automobile) {
		this.credit_automobile = credit_automobile;
	}

	List<CreditHouse> getCreditHouse() {
		return credit_house;
	}

	void setCreditHouse(List<CreditHouse> credit_house) {
		this.credit_house = credit_house;
	}

	List<CreditAppliance> getCreditAppliance() {
		return credit_appliance;
	}

	void setCreditAppliance(List<CreditAppliance> credit_appliance) {
		this.credit_appliance = credit_appliance;
	}

	List<CreditSurety> getCreditSurety() {
		return credit_surety;
	}

	void setCreditSurety(List<CreditSurety> credit_surety) {
		this.credit_surety = credit_surety;
	}

	List<CreditAdjunt> getCreditAdjunt() {
		return credit_adjunt;
	}

	void setCreditAdjunt(List<CreditAdjunt> credit_adjunt) {
		this.credit_adjunt = credit_adjunt;
	}

	List<BidRepayment> getBidRepayment() {
		return bid_repayment;
	}

	void setBidRepayment(List<BidRepayment> bid_repayment) {
		this.bid_repayment = bid_repayment;
	}

	List<CreditCompany> getCreditCompany() {
		return credit_company;
	}

	void setCreditCompany(List<CreditCompany> credit_company) {
		this.credit_company = credit_company;
	}
}
