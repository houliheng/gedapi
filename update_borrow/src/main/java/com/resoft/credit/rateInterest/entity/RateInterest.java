package com.resoft.credit.rateInterest.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 申请借款信息表Entity
 * @author chenshaojia
 * @version 2016-03-01
 */
public class RateInterest extends DataEntity<RateInterest> {
	
	private static final long serialVersionUID = 1L;
	private String loanRepayType;
	private String loanRepayDesc;
	private String periodValue;
	private String rateInterest;
	
	private String productTypeCode;  // 产品类型
	private Date startTime;		     // 起始时间
	private Date endTime;		     // 截止时间
	
	
	@Length(min=0, max=4, message="还款方式长度必须介于 0 和 4 之间")
	public String getLoanRepayType() {
		return loanRepayType;
	}
	public void setLoanRepayType(String loanRepayType) {
		this.loanRepayType = loanRepayType;
	}
	
	@Length(min=0, max=30, message="还款方式名称长度必须介于 0 和 30 之间")
	public String getLoanRepayDesc() {
		return loanRepayDesc;
	}
	public void setLoanRepayDesc(String loanRepayDesc) {
		this.loanRepayDesc = loanRepayDesc;
	}
	
	@Length(min=0, max=4, message="总期数长度必须介于 0 和 4 之间")
	public String getPeriodValue() {
		return periodValue;
	}
	public void setPeriodValue(String periodValue) {
		this.periodValue = periodValue;
	}
	
	public String getRateInterest() {
		return rateInterest;
	}
	public void setRateInterest(String rateInterest) {
		this.rateInterest = rateInterest;
	}
	
	@Length(min=0, max=32, message="产品类型长度必须介于 0 和 32 之间")
	public String getProductTypeCode() {
		return productTypeCode;
	}
	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	
}