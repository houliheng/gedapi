package com.resoft.outinterface.rest.ged.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
* @author guoshaohua
* @version 2018年3月24日 下午2:02:29
* 
*/
public class GedRepayment implements Serializable{
	private static final long serialVersionUID = 1L;
	private String custId;//客户id在冠E通存在的账户id
	private String contractNo;//合同号
	private String periodNum;//期数
	private String dataDt; // 数据日期   格式2016-04-01
	private String deductCustName; // 扣款人Name   扣款人姓名
	private BigDecimal deductAmount; // 扣款金额   充值金额
	private String capitalTerraceNo; // 资金平台账号 
	private String deductTime; // 划扣时间   格式2016-04-01 12:00:00
	private String isLock; // 是否锁定（字典类型：yes_no） 
	private String deductApplyNo; // 扣款申请序列号  （你们产生）
	private String mobileNum;// 手机号  扣款人
	private String bankcardNo;// 银行卡号    扣款人
	private String bank; // 开户银行（字典类型：BANKS） 扣款人
	private String accountNo;//充值流水号
	private String seqNo;
	private String deductId;//扣款者id暂时固定
	private String advanceRepayMoney;//提前结清违约金
	private String advanceFlag;//提前结清标识
	@JsonIgnoreProperties({ "internalId", "secretKey" })
	private String deductType;//划扣类型
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getPeriodNum() {
		return periodNum;
	}
	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}
	public String getDataDt() {
		return dataDt;
	}
	public void setDataDt(String dataDt) {
		this.dataDt = dataDt;
	}
	public String getDeductCustName() {
		return deductCustName;
	}
	public void setDeductCustName(String deductCustName) {
		this.deductCustName = deductCustName;
	}
	
	public BigDecimal getDeductAmount() {
		return deductAmount;
	}
	public void setDeductAmount(BigDecimal deductAmount) {
		this.deductAmount = deductAmount;
	}
	public String getCapitalTerraceNo() {
		return capitalTerraceNo;
	}
	public void setCapitalTerraceNo(String capitalTerraceNo) {
		this.capitalTerraceNo = capitalTerraceNo;
	}
	public String getDeductTime() {
		return deductTime;
	}
	public void setDeductTime(String deductTime) {
		this.deductTime = deductTime;
	}
	public String getIsLock() {
		return isLock;
	}
	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}
	public String getDeductApplyNo() {
		return deductApplyNo;
	}
	public void setDeductApplyNo(String deductApplyNo) {
		this.deductApplyNo = deductApplyNo;
	}
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	public String getBankcardNo() {
		return bankcardNo;
	}
	public void setBankcardNo(String bankcardNo) {
		this.bankcardNo = bankcardNo;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getDeductId() {
		return deductId;
	}
	public void setDeductId(String deductId) {
		this.deductId = deductId;
	}
	public String getAdvanceRepayMoney() {
		return advanceRepayMoney;
	}
	public void setAdvanceRepayMoney(String advanceRepayMoney) {
		this.advanceRepayMoney = advanceRepayMoney;
	}
	public String getAdvanceFlag() {
		return advanceFlag;
	}
	public void setAdvanceFlag(String advanceFlag) {
		this.advanceFlag = advanceFlag;
	}
	public String getDeductType() {
		return deductType;
	}
	public void setDeductType(String deductType) {
		this.deductType = deductType;
	}
	
	
}
