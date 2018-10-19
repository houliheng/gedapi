package com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility=Visibility.PUBLIC_ONLY)
public class CreditInfo {
	private String firm_desc;
	private String operate_actuality;
	private String income_actuality;
	private String industry_desc;
	private int loan_status;
	private String loan_use;
	private int loan_use_type;
	private String refund_source;
	private int refund_source_type;
	
	private String loan_person_desc;
	private String firm_product_desc;
	private String firm_sum_desc;
	private int credit_level;
	private int is_loan_record;
	private int is_overdue;
	private String bank_loan_count;
	private int credit_record_level;
	
	private int property_type;
	private String refund_source2;
	private String refund_source3;
	private String refund_source4;

	public String getFirm_desc() {
		return firm_desc;
	}

	public void setFirm_desc(String firm_desc) {
		this.firm_desc = firm_desc;
	}

	public String getOperate_actuality() {
		return operate_actuality;
	}

	public void setOperate_actuality(String operate_actuality) {
		this.operate_actuality = operate_actuality;
	}

	public String getIncome_actuality() {
		return income_actuality;
	}

	public void setIncome_actuality(String income_actuality) {
		this.income_actuality = income_actuality;
	}

	public String getIndustry_desc() {
		return industry_desc;
	}

	public void setIndustry_desc(String industry_desc) {
		this.industry_desc = industry_desc;
	}

	public int getLoan_status() {
		return loan_status;
	}

	public void setLoan_status(int loan_status) {
		this.loan_status = loan_status;
	}

	public String getLoan_use() {
		return loan_use;
	}

	public void setLoan_use(String loan_use) {
		this.loan_use = loan_use;
	}

	public int getLoan_use_type() {
		return loan_use_type;
	}

	public void setLoan_use_type(int loan_use_type) {
		this.loan_use_type = loan_use_type;
	}

	public String getRefund_source() {
		return refund_source;
	}

	public void setRefund_source(String refund_source) {
		this.refund_source = refund_source;
	}

	public int getRefund_source_type() {
		return refund_source_type;
	}

	public void setRefund_source_type(int refund_source_type) {
		this.refund_source_type = refund_source_type;
	}

	public String getLoan_person_desc() {
		return loan_person_desc;
	}

	public void setLoan_person_desc(String loan_person_desc) {
		this.loan_person_desc = loan_person_desc;
	}

	public String getFirm_product_desc() {
		return firm_product_desc;
	}

	public void setFirm_product_desc(String firm_product_desc) {
		this.firm_product_desc = firm_product_desc;
	}

	public String getFirm_sum_desc() {
		return firm_sum_desc;
	}

	public void setFirm_sum_desc(String firm_sum_desc) {
		this.firm_sum_desc = firm_sum_desc;
	}

	public int getCredit_level() {
		return credit_level;
	}

	public void setCredit_level(int credit_level) {
		this.credit_level = credit_level;
	}

	public int getIs_loan_record() {
		return is_loan_record;
	}

	public void setIs_loan_record(int is_loan_record) {
		this.is_loan_record = is_loan_record;
	}

	public int getIs_overdue() {
		return is_overdue;
	}

	public void setIs_overdue(int is_overdue) {
		this.is_overdue = is_overdue;
	}

	public String getBank_loan_count() {
		return bank_loan_count;
	}

	public void setBank_loan_count(String bank_loan_count) {
		this.bank_loan_count = bank_loan_count;
	}

	public int getCredit_record_level() {
		return credit_record_level;
	}

	public void setCredit_record_level(int credit_record_level) {
		this.credit_record_level = credit_record_level;
	}

	public int getProperty_type() {
		return property_type;
	}

	public void setProperty_type(int property_type) {
		this.property_type = property_type;
	}
	
	
	
	
	
	protected String getFirmDesc() {
		return firm_desc;
	}

	protected void setFirmDesc(String firm_desc) {
		this.firm_desc = firm_desc;
	}

	protected String getOperateActuality() {
		return operate_actuality;
	}

	protected void setOperateActuality(String operate_actuality) {
		this.operate_actuality = operate_actuality;
	}

	protected String getIncomeActuality() {
		return income_actuality;
	}

	protected void setIncomeActuality(String income_actuality) {
		this.income_actuality = income_actuality;
	}

	protected String getIndustryDesc() {
		return industry_desc;
	}

	protected void setIndustryDesc(String industry_desc) {
		this.industry_desc = industry_desc;
	}

	protected int getLoanStatus() {
		return loan_status;
	}

	protected void setLoanStatus(int loan_status) {
		this.loan_status = loan_status;
	}

	protected String getLoanUse() {
		return loan_use;
	}

	protected void setLoanUse(String loan_use) {
		this.loan_use = loan_use;
	}

	protected int getLoanUseType() {
		return loan_use_type;
	}

	protected void setLoanUseType(int loan_use_type) {
		this.loan_use_type = loan_use_type;
	}

	protected String getRefundSource() {
		return refund_source;
	}

	protected void setRefundSource(String refund_source) {
		this.refund_source = refund_source;
	}

	protected int getRefundSourceType() {
		return refund_source_type;
	}

	protected void setRefundSourceType(int refund_source_type) {
		this.refund_source_type = refund_source_type;
	}

	protected String getLoanPersonDesc() {
		return loan_person_desc;
	}

	protected void setLoanPersonDesc(String loan_person_desc) {
		this.loan_person_desc = loan_person_desc;
	}
	
	protected String getFirmProductDesc() {
		return firm_product_desc;
	}

	protected void setFirmProductDesc(String firm_product_desc) {
		this.firm_product_desc = firm_product_desc;
	}

	protected String getFirmSumDesc() {
		return firm_sum_desc;
	}

	protected void setFirmSumDesc(String firm_sum_desc) {
		this.firm_sum_desc = firm_sum_desc;
	}

	protected int getCreditLevel() {
		return credit_level;
	}

	protected void setCreditLevel(int credit_level) {
		this.credit_level = credit_level;
	}

	protected int getIsLoanRecord() {
		return is_loan_record;
	}

	protected void setIsLoanRecord(int is_loan_record) {
		this.is_loan_record = is_loan_record;
	}

	protected int getIsOverdue() {
		return is_overdue;
	}

	protected void setIsOverdue(int is_overdue) {
		this.is_overdue = is_overdue;
	}

	protected String getBankLoanCount() {
		return bank_loan_count;
	}

	protected void setBankLoanCount(String bank_loan_count) {
		this.bank_loan_count = bank_loan_count;
	}

	protected int getCreditRecordLevel() {
		return credit_record_level;
	}

	protected void setCreditRecordLevel(int credit_record_level) {
		this.credit_record_level = credit_record_level;
	}

	protected int getPropertyType() {
		return property_type;
	}

	protected void setPropertyType(int property_type) {
		this.property_type = property_type;
	}

	public String getRefund_source2() {
		return refund_source2;
	}

	public void setRefund_source2(String refund_source2) {
		this.refund_source2 = refund_source2;
	}

	public String getRefund_source3() {
		return refund_source3;
	}

	public void setRefund_source3(String refund_source3) {
		this.refund_source3 = refund_source3;
	}

	public String getRefund_source4() {
		return refund_source4;
	}

	public void setRefund_source4(String refund_source4) {
		this.refund_source4 = refund_source4;
	}
	
	protected String getRefundSource2() {
		return refund_source2;
	}

	protected void setRefundSource2(String refund_source2) {
		this.refund_source2 = refund_source2;
	}

	protected String getRefundSource3() {
		return refund_source3;
	}

	protected void setRefundSource3(String refund_source3) {
		this.refund_source3 = refund_source3;
	}

	protected String getRefundSource4() {
		return refund_source4;
	}

	protected void setRefundSource4(String refund_source4) {
		this.refund_source4 = refund_source4;
	}
}
