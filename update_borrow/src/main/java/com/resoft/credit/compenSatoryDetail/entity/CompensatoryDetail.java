package com.resoft.credit.compenSatoryDetail.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 代偿详情表Entity
 * @author jml
 * @version 2018-03-16
 */
public class CompensatoryDetail extends DataEntity<CompensatoryDetail> {
	
	private static final long serialVersionUID = 1L;
	private String serialNum;		// 流水号
	private String compensatoryStatus;		// 代偿状态
	private String contractNo;		// 合同编号
	private String periodNum;		// 期数
	private BigDecimal compensatoryAmount;		// 已代偿金额
	private String compensatoryAccount;		// from代偿账户
	private String toCompensatoryAccount;		// to代偿账户
	private String failReason;//失败原因
	private String custId;//账户ID
	private String flag;//借款人是否已还代偿人
	
	private String compensatoryType;//代偿类型    1自有担保人   2担保公司   3平台保证金账户
	private String compensatoryName;//代偿账户名称
	
	//-----非表字段
	private String compensatoryAmountOld ;
	private BigDecimal surplusAmount;//剩余应还代偿账户金额金额
	
	
	public CompensatoryDetail() {
		super();
	}

	public CompensatoryDetail(String id){
		super(id);
	}

	@Length(min=1, max=32, message="流水号长度必须介于 1 和 32 之间")
	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	
	@Length(min=0, max=1, message="compensatory_status长度必须介于 0 和 1 之间")
	public String getCompensatoryStatus() {
		return compensatoryStatus;
	}

	public void setCompensatoryStatus(String compensatoryStatus) {
		this.compensatoryStatus = compensatoryStatus;
	}
	
	@Length(min=1, max=50, message="合同编号长度必须介于 1 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Length(min=1, max=10, message="期数长度必须介于 1 和 10 之间")
	public String getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}
	
	public BigDecimal getCompensatoryAmount() {
		return compensatoryAmount;
	}

	public void setCompensatoryAmount(BigDecimal compensatoryAmount) {
		this.compensatoryAmount = compensatoryAmount;
	}
	
	@Length(min=0, max=32, message="代偿账户长度必须介于 0 和 32 之间")
	public String getCompensatoryAccount() {
		return compensatoryAccount;
	}

	public void setCompensatoryAccount(String compensatoryAccount) {
		this.compensatoryAccount = compensatoryAccount;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCompensatoryAmountOld() {
		return compensatoryAmountOld;
	}

	public void setCompensatoryAmountOld(String compensatoryAmountOld) {
		this.compensatoryAmountOld = compensatoryAmountOld;
	}

	public String getToCompensatoryAccount() {
		return toCompensatoryAccount;
	}

	public void setToCompensatoryAccount(String toCompensatoryAccount) {
		this.toCompensatoryAccount = toCompensatoryAccount;
	}

	protected String getFlag() {
		return flag;
	}

	protected void setFlag(String flag) {
		this.flag = flag;
	}

	public BigDecimal getSurplusAmount() {
		return surplusAmount;
	}

	public void setSurplusAmount(BigDecimal surplusAmount) {
		this.surplusAmount = surplusAmount;
	}

	public String getCompensatoryType() {
		return compensatoryType;
	}

	public void setCompensatoryType(String compensatoryType) {
		this.compensatoryType = compensatoryType;
	}

	public String getCompensatoryName() {
		return compensatoryName;
	}

	public void setCompensatoryName(String compensatoryName) {
		this.compensatoryName = compensatoryName;
	}

}