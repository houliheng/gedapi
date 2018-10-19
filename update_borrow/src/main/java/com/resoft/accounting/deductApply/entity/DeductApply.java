package com.resoft.accounting.deductApply.entity;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 还款划扣Entity
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
public class DeductApply extends DataEntity<DeductApply> {

	private static final long serialVersionUID = 1L;
	private String dataDt; // 数据日期
	private String deductCustId; // 划扣人Id
	private String deductCustName; // 划扣人Name
	private String streamNo; // 申请序列号
	private String allDeductAmount; // 可扣款金额
	private String deductAmount; // 扣款金额
	private String contractNo; // 合同编号
	private String capitalTerraceNo; // 资金平台账号
	private String midCapitalTerraceNo; // 中间人资金平台账号
	private String deductTime; // 划扣时间
	private String isLock; // 是否锁定（字典类型：yes_no）
	private String periodNum; // 期数
	private String deductApplyNo; // 扣款申请序列号
	private String streamType;// 流水类型
	private String deductType;// 划扣类型
	private String mobileNum;// 手机号
	private String bankcardNo;// 银行卡号
	private String bank; // 开户银行（字典类型：BANKS）
	private String custName;// 姓名
	private String idNum;//身份证号
	
	
	private String flag;
	
	public String getMidCapitalTerraceNo() {
		return midCapitalTerraceNo;
	}

	public void setMidCapitalTerraceNo(String midCapitalTerraceNo) {
		this.midCapitalTerraceNo = midCapitalTerraceNo;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
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

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getDeductCustName() {
		return deductCustName;
	}

	public void setDeductCustName(String deductCustName) {
		this.deductCustName = deductCustName;
	}

	public String getStreamType() {
		return streamType;
	}

	public void setStreamType(String streamType) {
		this.streamType = streamType;
	}

	public String getDeductType() {
		return deductType;
	}

	public void setDeductType(String deductType) {
		this.deductType = deductType;
	}

	public String getDeductApplyNo() {
		return deductApplyNo;
	}

	public void setDeductApplyNo(String deductApplyNo) {
		this.deductApplyNo = deductApplyNo;
	}

	public String getAllDeductAmount() {
		return allDeductAmount;
	}

	public void setAllDeductAmount(String allDeductAmount) {
		this.allDeductAmount = allDeductAmount;
	}

	public DeductApply() {
		super();
	}

	public DeductApply(String id) {
		super(id);
	}

	@Length(min = 0, max = 10, message = "数据日期长度必须介于 0 和 10 之间")
	public String getDataDt() {
		return dataDt;
	}

	public void setDataDt(String dataDt) {
		this.dataDt = dataDt;
	}

	@Length(min = 0, max = 32, message = "划扣人长度必须介于 0 和 32 之间")
	public String getDeductCustId() {
		return deductCustId;
	}

	public void setDeductCustId(String deductCustId) {
		this.deductCustId = deductCustId;
	}

	@Length(min = 0, max = 32, message = "流水号长度必须介于 0 和 32 之间")
	public String getStreamNo() {
		return streamNo;
	}

	public void setStreamNo(String streamNo) {
		this.streamNo = streamNo;
	}

	public String getDeductAmount() {
		return deductAmount;
	}

	public void setDeductAmount(String deductAmount) {
		this.deductAmount = deductAmount;
	}

	@Length(min = 0, max = 32, message = "合同编号长度必须介于 0 和 32 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Length(min = 0, max = 32, message = "资金平台账号长度必须介于 0 和 32 之间")
	public String getCapitalTerraceNo() {
		return capitalTerraceNo;
	}

	public void setCapitalTerraceNo(String capitalTerraceNo) {
		this.capitalTerraceNo = capitalTerraceNo;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public String getDeductTime() {
		return deductTime;
	}

	public void setDeductTime(String deductTime) {
		this.deductTime = deductTime;
	}

	@Length(min = 0, max = 1, message = "是否锁定（字典类型：yes_no）长度必须介于 0 和 1 之间")
	public String getIsLock() {
		return isLock;
	}

	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}

	@Length(min = 0, max = 10, message = "期数长度必须介于 0 和 10 之间")
	public String getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	
}