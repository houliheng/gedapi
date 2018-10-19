package com.resoft.postloan.accountClean.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 账务清收Entity
 * @author yanwanmei
 * @version 2016-06-02
 */
public class AccountClean extends DataEntity<AccountClean> {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String contractNo;		// 合同编号
	private String cleanById;		// 清收人ID
	private String cleanBy;		// 清收人
	private Double cleanAmount;		// 清收金额
	private String cleanApplyStatus;		// 清收申请状态（1审批中2通过3打回）
	private String cleanReturnAmount;		// 清收回款金额
	private String allocateType;		// 分配状态（1待清收 2已清收 3清收结束
	
	public AccountClean() {
		super();
	}

	public AccountClean(String id){
		super(id);
	}

	@Length(min=1, max=50, message="合同编号长度必须介于 1 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Length(min=0, max=30, message="清收人ID长度必须介于 0 和 30 之间")
	public String getCleanById() {
		return cleanById;
	}

	public void setCleanById(String cleanById) {
		this.cleanById = cleanById;
	}
	
	@Length(min=0, max=30, message="清收人长度必须介于 0 和 30 之间")
	public String getCleanBy() {
		return cleanBy;
	}

	public void setCleanBy(String cleanBy) {
		this.cleanBy = cleanBy;
	}
	
	public Double getCleanAmount() {
		return cleanAmount;
	}

	public void setCleanAmount(Double cleanAmount) {
		this.cleanAmount = cleanAmount;
	}
	
	@Length(min=0, max=4, message="清收申请状态（1审批中2通过3打回）长度必须介于 0 和 4 之间")
	public String getCleanApplyStatus() {
		return cleanApplyStatus;
	}

	public void setCleanApplyStatus(String cleanApplyStatus) {
		this.cleanApplyStatus = cleanApplyStatus;
	}
	
	public String getCleanReturnAmount() {
		return cleanReturnAmount;
	}

	public void setCleanReturnAmount(String cleanReturnAmount) {
		this.cleanReturnAmount = cleanReturnAmount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAllocateType() {
		return allocateType;
	}

	public void setAllocateType(String allocateType) {
		this.allocateType = allocateType;
	}
	
	
	
}