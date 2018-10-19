package com.resoft.postloan.accountCleanAllocate.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 借后清收任务分配表Entity
 * @author yanwanmei
 * @version 2016-06-02
 */
public class AccountCleanAllocate extends DataEntity<AccountCleanAllocate> {
	
	private static final long serialVersionUID = 1L;
	private String contractNo;		// 合同编号
	private String allocateById;		// 借后经理ID
	private String allocateBy;		// 借后经理姓名
	private String checkedById;		// 借后25日复核专员ID
	private String checkedBy;		// 借后25日复核专员姓名
	private String allocateType;		// 分配状态（1待分配（待办任务）2已分配（已办任务）3保存）
	
	public AccountCleanAllocate() {
		super();
	}

	public AccountCleanAllocate(String id){
		super(id);
	}

	@Length(min=1, max=50, message="合同编号长度必须介于 1 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Length(min=0, max=32, message="借后经理ID长度必须介于 0 和 32 之间")
	public String getAllocateById() {
		return allocateById;
	}

	public void setAllocateById(String allocateById) {
		this.allocateById = allocateById;
	}
	
	@Length(min=0, max=30, message="借后经理姓名长度必须介于 0 和 30 之间")
	public String getAllocateBy() {
		return allocateBy;
	}

	public void setAllocateBy(String allocateBy) {
		this.allocateBy = allocateBy;
	}
	
	@Length(min=0, max=32, message="借后25日复核专员ID长度必须介于 0 和 32 之间")
	public String getCheckedById() {
		return checkedById;
	}

	public void setCheckedById(String checkedById) {
		this.checkedById = checkedById;
	}
	
	@Length(min=0, max=30, message="借后25日复核专员姓名长度必须介于 0 和 30 之间")
	public String getCheckedBy() {
		return checkedBy;
	}

	public void setCheckedBy(String checkedBy) {
		this.checkedBy = checkedBy;
	}
	
	@Length(min=0, max=4, message="分配状态（1待分配（待办任务）2已分配（已办任务）3保存）长度必须介于 0 和 4 之间")
	public String getAllocateType() {
		return allocateType;
	}

	public void setAllocateType(String allocateType) {
		this.allocateType = allocateType;
	}
	
}