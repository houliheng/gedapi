package com.resoft.outinterface.rest.financialPlatform.entry;

import java.util.Date;



import com.thinkgem.jeesite.common.persistence.DataEntity;

public class DeductResult extends DataEntity<DeductResult>{
	private static final long serialVersionUID = 1L;
	private String dataDt;
	private String capitalNo;
	private String contractNo;
	private String sysSeqNo;
	private String deductApplyNo;
	private String steamNo;
	private Date   streamTime;
	private double deductAmount;
	private String deductResult;
	private String deductUserId;
	private String isLock;
	private String strikeFlg;
	private String description;
	private String traDeductResult;
	private String deductMsg;
	public String getDataDt() {
		return dataDt;
	}
	public void setDataDt(String dataDt) {
		this.dataDt = dataDt;
	}
	public String getCapitalNo() {
		return capitalNo;
	}
	public void setCapitalNo(String capitalNo) {
		this.capitalNo = capitalNo;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getSysSeqNo() {
		return sysSeqNo;
	}
	public void setSysSeqNo(String sysSeqNo) {
		this.sysSeqNo = sysSeqNo;
	}
	public String getDeductApplyNo() {
		return deductApplyNo;
	}
	public void setDeductApplyNo(String deductApplyNo) {
		this.deductApplyNo = deductApplyNo;
	}
	public String getSteamNo() {
		return steamNo;
	}
	public void setSteamNo(String steamNo) {
		this.steamNo = steamNo;
	}
	public Date getStreamTime() {
		return streamTime;
	}
	public void setStreamTime(Date streamTime) {
		this.streamTime = streamTime;
	}
	public double getDeductAmount() {
		return deductAmount;
	}
	public void setDeductAmount(double deductAmount) {
		this.deductAmount = deductAmount;
	}
	public String getDeductResult() {
		return deductResult;
	}
	public void setDeductResult(String deductResult) {
		this.deductResult = deductResult;
	}
	public String getDeductUserId() {
		return deductUserId;
	}
	public void setDeductUserId(String deductUserId) {
		this.deductUserId = deductUserId;
	}
	public String getIsLock() {
		return isLock;
	}
	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}
	public String getStrikeFlg() {
		return strikeFlg;
	}
	public void setStrikeFlg(String strikeFlg) {
		this.strikeFlg = strikeFlg;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTraDeductResult() {
		return traDeductResult;
	}
	public void setTraDeductResult(String traDeductResult) {
		this.traDeductResult = traDeductResult;
	}
	public String getDeductMsg() {
		return deductMsg;
	}
	public void setDeductMsg(String deductMsg) {
		this.deductMsg = deductMsg;
	}
	
	
}
