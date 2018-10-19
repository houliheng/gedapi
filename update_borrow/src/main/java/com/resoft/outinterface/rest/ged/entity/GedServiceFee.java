package com.resoft.outinterface.rest.ged.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* @author guoshaohua
* @version 2018年3月16日 下午3:15:27
* 
*/
public class GedServiceFee implements Serializable{
	private static final long serialVersionUID = 1L;
	private String applyNo;
	private String contractNo;
	private String loanStatus;
	private String flag;
	private String withdrawAmount;
	private String seqNo;
	private String mchn;
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getWithdrawAmount() {
		return withdrawAmount;
	}
	public void setWithdrawAmount(String withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getMchn() {
		return mchn;
	}
	public void setMchn(String mchn) {
		this.mchn = mchn;
	}
}
