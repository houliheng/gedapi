package com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility=Visibility.PUBLIC_ONLY)
public class CreditCompany {
	private String relation;
	private String company_desc;
	private String income_state;
	private String operation_state;
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getCompany_desc() {
		return company_desc;
	}
	public void setCompany_desc(String company_desc) {
		this.company_desc = company_desc;
	}
	public String getIncome_state() {
		return income_state;
	}
	public void setIncome_state(String income_state) {
		this.income_state = income_state;
	}
	public String getOperation_state() {
		return operation_state;
	}
	public void setOperation_state(String operation_state) {
		this.operation_state = operation_state;
	}
	
	
	protected String getCompanyDesc() {
		return company_desc;
	}
	protected void setCompanyDesc(String company_desc) {
		this.company_desc = company_desc;
	}
	protected String getIncomeState() {
		return income_state;
	}
	protected void setIncomeState(String income_state) {
		this.income_state = income_state;
	}
	protected String getOperationState() {
		return operation_state;
	}
	protected void setOperationState(String operation_state) {
		this.operation_state = operation_state;
	}
}
