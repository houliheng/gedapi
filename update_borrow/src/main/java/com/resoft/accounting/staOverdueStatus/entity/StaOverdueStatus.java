package com.resoft.accounting.staOverdueStatus.entity;

import java.math.BigDecimal;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 逾期信息Entity 
 * @author wangguodong
 *
 */
public class StaOverdueStatus extends DataEntity<StaOverdueStatus> {
	private static final long serialVersionUID = 1L;
	private BigDecimal fineAmount;  //罚息  FINE_AMOUNT
	private BigDecimal oweCapitalAmount;   // 逾期本金  OWE_CAPITAL_AMOUNT
	private BigDecimal oweInterestAmount;  //逾期利息  OWE_INTEREST_AMOUNT
	private String overdueDays;  //逾期天数  OVERDUE_DAYS
	
	
	public BigDecimal getFineAmount() {
		return fineAmount;
	}
	public void setFineAmount(BigDecimal fineAmount) {
		this.fineAmount = fineAmount;
	}
	public BigDecimal getOweCapitalAmount() {
		return oweCapitalAmount;
	}
	public void setOweCapitalAmount(BigDecimal oweCapitalAmount) {
		this.oweCapitalAmount = oweCapitalAmount;
	}
	public BigDecimal getOweInterestAmount() {
		return oweInterestAmount;
	}
	public void setOweInterestAmount(BigDecimal oweInterestAmount) {
		this.oweInterestAmount = oweInterestAmount;
	}
	public String getOverdueDays() {
		return overdueDays;
	}
	public void setOverdueDays(String overdueDays) {
		this.overdueDays = overdueDays;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
} 
