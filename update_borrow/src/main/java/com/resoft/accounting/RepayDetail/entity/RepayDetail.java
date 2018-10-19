package com.resoft.accounting.RepayDetail.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class RepayDetail extends DataEntity<RepayDetail>{
	private static final long serialVersionUID = 1L;
	private String createTime;
	private Integer periodNum;
	private Date repayDate;
	private String deductApplyNo;
	private String streamNo;
	private Date creTime;
	private Double repayServiceFee;
	private Double repayManFee;
	private Double repayIntAmo;
	private Double repayCapAmo;
	private Double repayPenAmo;
	private Double repayFinAmo;
	private Double repayAddAmo;
	private Double repayBreAmo;
	private Double backAmo;
	private String contractNo;
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getPeriodNum() {
		return periodNum;
	}
	public void setPeriodNum(Integer periodNum) {
		this.periodNum = periodNum;
	}
	public Date getRepayDate() {
		return repayDate;
	}
	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}
	public String getDeductApplyNo() {
		return deductApplyNo;
	}
	public void setDeductApplyNo(String deductApplyNo) {
		this.deductApplyNo = deductApplyNo;
	}
	public String getStreamNo() {
		return streamNo;
	}
	public void setStreamNo(String streamNo) {
		this.streamNo = streamNo;
	}
	public Date getCreTime() {
		return creTime;
	}
	public void setCreTime(Date creTime) {
		this.creTime = creTime;
	}
	public Double getRepayServiceFee() {
		return repayServiceFee;
	}
	public void setRepayServiceFee(Double repayServiceFee) {
		this.repayServiceFee = repayServiceFee;
	}
	public Double getRepayManFee() {
		return repayManFee;
	}
	public void setRepayManFee(Double repayManFee) {
		this.repayManFee = repayManFee;
	}
	public Double getRepayIntAmo() {
		return repayIntAmo;
	}
	public void setRepayIntAmo(Double repayIntAmo) {
		this.repayIntAmo = repayIntAmo;
	}
	public Double getRepayCapAmo() {
		return repayCapAmo;
	}
	public void setRepayCapAmo(Double repayCapAmo) {
		this.repayCapAmo = repayCapAmo;
	}
	public Double getRepayPenAmo() {
		return repayPenAmo;
	}
	public void setRepayPenAmo(Double repayPenAmo) {
		this.repayPenAmo = repayPenAmo;
	}
	public Double getRepayFinAmo() {
		return repayFinAmo;
	}
	public void setRepayFinAmo(Double repayFinAmo) {
		this.repayFinAmo = repayFinAmo;
	}
	public Double getRepayAddAmo() {
		return repayAddAmo;
	}
	public void setRepayAddAmo(Double repayAddAmo) {
		this.repayAddAmo = repayAddAmo;
	}
	public Double getRepayBreAmo() {
		return repayBreAmo;
	}
	public void setRepayBreAmo(Double repayBreAmo) {
		this.repayBreAmo = repayBreAmo;
	}
	public Double getBackAmo() {
		return backAmo;
	}
	public void setBackAmo(Double backAmo) {
		this.backAmo = backAmo;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	
}
