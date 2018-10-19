package com.resoft.accounting.advanceRepayGET.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 提前还款记录Entity
 * @author jiangmenglun
 * @version 2018-01-15
 */
public class AdvanceRepayGet extends DataEntity<AdvanceRepayGet> {
	
	private static final long serialVersionUID = 1L;
	private String contractNo;		// 合同编号
	private String periodNum;		// 期限值
	private BigDecimal remainCapitalAmount;		// 应还本金
	private BigDecimal remainInterestAmount;		// 剩余应还利息
	private BigDecimal remainManagementFee;		// 剩余应还管理费
	private BigDecimal remainAllInterest;		//利息总额
	private BigDecimal allAdvanceRepay;		// 提前还款总额
	private String repayDate;		// 还款日
	private BigDecimal remainServiceFee;//分期服务费
	
	private String advanceStatus;//提前还款状态（0.已申请待回盘1.成功2.失败）
	private String advanceServiceFee;
	
	
	public AdvanceRepayGet() {
		super();
	}

	public AdvanceRepayGet(String id){
		super(id);
	}

	@Length(min=0, max=50, message="合同编号长度必须介于 0 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Length(min=0, max=4, message="期限值长度必须介于 0 和 4 之间")
	public String getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}
	
	public BigDecimal getRemainCapitalAmount() {
		return remainCapitalAmount;
	}

	public void setRemainCapitalAmount(BigDecimal remainCapitalAmount) {
		this.remainCapitalAmount = remainCapitalAmount;
	}
	
	public BigDecimal getRemainInterestAmount() {
		return remainInterestAmount;
	}

	public void setRemainInterestAmount(BigDecimal remainInterestAmount) {
		this.remainInterestAmount = remainInterestAmount;
	}
	
	public BigDecimal getRemainServiceFee() {
		return remainServiceFee;
	}

	public void setRemainServiceFee(BigDecimal remainServiceFee) {
		this.remainServiceFee = remainServiceFee;
	}
	
	public BigDecimal getRemainManagementFee() {
		return remainManagementFee;
	}

	public void setRemainManagementFee(BigDecimal remainManagementFee) {
		this.remainManagementFee = remainManagementFee;
	}
	
	public BigDecimal getAllAdvanceRepay() {
		return allAdvanceRepay;
	}

	public void setAllAdvanceRepay(BigDecimal allAdvanceRepay) {
		this.allAdvanceRepay = allAdvanceRepay;
	}
	
	public String getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}

	public BigDecimal getRemainAllInterest() {
		return remainAllInterest;
	}

	public void setRemainAllInterest(BigDecimal remainAllInterest) {
		this.remainAllInterest = remainAllInterest;
	}

	public String getAdvanceStatus() {
		return advanceStatus;
	}

	public void setAdvanceStatus(String advanceStatus) {
		this.advanceStatus = advanceStatus;
	}

	public String getAdvanceServiceFee() {
		return advanceServiceFee;
	}

	public void setAdvanceServiceFee(String advanceServiceFee) {
		this.advanceServiceFee = advanceServiceFee;
	}
	
	
	
}