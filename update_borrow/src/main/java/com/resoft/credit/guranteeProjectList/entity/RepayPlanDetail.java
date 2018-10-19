package com.resoft.credit.guranteeProjectList.entity;

import java.math.BigDecimal;

import com.resoft.credit.pricedRisk.dao.PricedRiskDao;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
* @author guoshaohua
* @version 2018年4月25日 下午2:53:18
* 
*/
public class RepayPlanDetail extends DataEntity<RepayPlanDetail>{
	private String periodNum;//期数
	private BigDecimal repayMoney;//待还金额
	private BigDecimal principal;//本息
	private BigDecimal compenMoney;//代偿金额
	private BigDecimal factRepayMoney;//实还金额
	private BigDecimal defaultInterest;//应还罚息
	private BigDecimal penalty;//应还违约金
	private String repayStatus;//还款状态
	public String getPeriodNum() {
		return periodNum;
	}
	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}
	
	
	public BigDecimal getRepayMoney() {
		return repayMoney;
	}
	public void setRepayMoney(BigDecimal repayMoney) {
		this.repayMoney = repayMoney;
	}
	public BigDecimal getPrincipal() {
		return principal;
	}
	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}
	public BigDecimal getCompenMoney() {
		return compenMoney;
	}
	public void setCompenMoney(BigDecimal compenMoney) {
		this.compenMoney = compenMoney;
	}
	public BigDecimal getFactRepayMoney() {
		return factRepayMoney;
	}
	public void setFactRepayMoney(BigDecimal factRepayMoney) {
		this.factRepayMoney = factRepayMoney;
	}
	public BigDecimal getDefaultInterest() {
		return defaultInterest;
	}
	public void setDefaultInterest(BigDecimal defaultInterest) {
		this.defaultInterest = defaultInterest;
	}
	public BigDecimal getPenalty() {
		return penalty;
	}
	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}
	public String getRepayStatus() {
		return repayStatus;
	}
	public void setRepayStatus(String repayStatus) {
		this.repayStatus = repayStatus;
	}
	
	
	

}
