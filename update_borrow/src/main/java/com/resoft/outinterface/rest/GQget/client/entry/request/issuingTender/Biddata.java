package com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

/**
 * @description:提前还款发送冠E通信息
 * @Author:jiangmenglun
 * @E-mail:
 * @version:2018年1月16日 下午1:54:41
 */
@JsonAutoDetect(fieldVisibility=Visibility.PUBLIC_ONLY)
public class Biddata {
	private String contract_no;//线下真实的合同编号
	private String principal;//还款本金
	private String contract_interest;//还款合同利息(总利息包含账户管理费)
	private String repayment_day;//还款日期
	private String service_period_fee;//分期服务费
	private String penalty_fee;
	public Biddata() {
		super();
	}
	public Biddata(String contract_no, String principal, String contract_interest, String repayment_day,
			String service_period_fee) {
		super();
		this.contract_no = contract_no;
		this.principal = principal;
		this.contract_interest = contract_interest;
		this.repayment_day = repayment_day;
		this.service_period_fee = service_period_fee;
	}
	public String getContract_no() {
		return contract_no;
	}
	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getContract_interest() {
		return contract_interest;
	}
	public void setContract_interest(String contract_interest) {
		this.contract_interest = contract_interest;
	}
	public String getRepayment_day() {
		return repayment_day;
	}
	public void setRepayment_day(String repayment_day) {
		this.repayment_day = repayment_day;
	}
	public String getService_period_fee() {
		return service_period_fee;
	}
	public void setService_period_fee(String service_period_fee) {
		this.service_period_fee = service_period_fee;
	}
	public String getPenalty_fee() {
		return penalty_fee;
	}
	public void setPenalty_fee(String penalty_fee) {
		this.penalty_fee = penalty_fee;
	}
	
	
}
