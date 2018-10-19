package com.resoft.outinterface.rest.ged.entity;

import java.io.Serializable;
import java.util.Date;


/**
* @author guoshaohua
* @version 2018年3月26日 上午10:42:07
* 
*/
public class GedAccAccountDeduct implements Serializable{
	private static final long serialVersionUID = 1L;
	private String contractNo; // 合同编号
	private String streamNo; // 流水号
	private Date compeleteTime; // 完成时间
	private String deductAmount; // 扣款金额
	private String isLock; // 是否锁定（字典类型：yes_no）
	private String seqNo;//交易流水号
	private String capitalTerraceNo; // 资金平台账号
	private String deductApplyNo; // 扣款申请序列号  （你们产生）
	private String deductId;//扣款者id暂时固定
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getStreamNo() {
		return streamNo;
	}
	public void setStreamNo(String streamNo) {
		this.streamNo = streamNo;
	}
	public Date getCompeleteTime() {
		return compeleteTime;
	}
	public void setCompeleteTime(Date compeleteTime) {
		this.compeleteTime = compeleteTime;
	}
	public String getDeductAmount() {
		return deductAmount;
	}
	public void setDeductAmount(String deductAmount) {
		this.deductAmount = deductAmount;
	}
	public String getIsLock() {
		return isLock;
	}
	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getCapitalTerraceNo() {
		return capitalTerraceNo;
	}
	public void setCapitalTerraceNo(String capitalTerraceNo) {
		this.capitalTerraceNo = capitalTerraceNo;
	}
	public String getDeductApplyNo() {
		return deductApplyNo;
	}
	public void setDeductApplyNo(String deductApplyNo) {
		this.deductApplyNo = deductApplyNo;
	}
	public String getDeductId() {
		return deductId;
	}
	public void setDeductId(String deductId) {
		this.deductId = deductId;
	}
	
	
}
