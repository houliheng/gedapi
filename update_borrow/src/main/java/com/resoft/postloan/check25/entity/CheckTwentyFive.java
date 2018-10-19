package com.resoft.postloan.check25.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 25日复核Entity
 * @author yanwanmei
 * @version 2016-05-25
 */
public class CheckTwentyFive extends DataEntity<CheckTwentyFive> {
	
	private static final long serialVersionUID = 1L;
	private String contractNo;		// 合同编号
	private String allocateId;		//分配id
	private String checkedBy;		// 复核人员
	private String checkTwentyFiveType;		// 复核方式（1只通过电话2只通过实地走访3两者都实施）
	private String checkPhone;		// 电话号码
	private String checkAddress;		// 走访地址
	private String checkTwentyFiveResult;		// 复核结果（1保存通过2清收3法务4签署保险合同）
	private String checkTwentyFiveAdvice;		// 25复核审核结果具体建议
	
	public String getAllocateId() {
		return allocateId;
	}

	public void setAllocateId(String allocateId) {
		this.allocateId = allocateId;
	}

	public CheckTwentyFive() {
		super();
	}

	public CheckTwentyFive(String id){
		super(id);
	}

	@Length(min=1, max=50, message="合同编号长度必须介于 1 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Length(min=0, max=30, message="复核人员长度必须介于 0 和 30 之间")
	public String getCheckedBy() {
		return checkedBy;
	}

	public void setCheckedBy(String checkedBy) {
		this.checkedBy = checkedBy;
	}
	
	@Length(min=0, max=4, message="复核方式（1只通过电话2只通过实地走访3两者都实施）长度必须介于 0 和 4 之间")
	public String getCheckTwentyFiveType() {
		return checkTwentyFiveType;
	}

	public void setCheckTwentyFiveType(String checkTwentyFiveType) {
		this.checkTwentyFiveType = checkTwentyFiveType;
	}
	
	@Length(min=0, max=15, message="电话号码长度必须介于 0 和 15 之间")
	public String getCheckPhone() {
		return checkPhone;
	}

	public void setCheckPhone(String checkPhone) {
		this.checkPhone = checkPhone;
	}
	
	@Length(min=0, max=300, message="走访地址长度必须介于 0 和 300 之间")
	public String getCheckAddress() {
		return checkAddress;
	}

	public void setCheckAddress(String checkAddress) {
		this.checkAddress = checkAddress;
	}
	
	@Length(min=0, max=4, message="复核结果（1保存通过2清收3法务4签署保险合同）长度必须介于 0 和 4 之间")
	public String getCheckTwentyFiveResult() {
		return checkTwentyFiveResult;
	}

	public void setCheckTwentyFiveResult(String checkTwentyFiveResult) {
		this.checkTwentyFiveResult = checkTwentyFiveResult;
	}
	
	@Length(min=0, max=1000, message="25复核审核结果具体建议长度必须介于 0 和 1000 之间")
	public String getCheckTwentyFiveAdvice() {
		return checkTwentyFiveAdvice;
	}

	public void setCheckTwentyFiveAdvice(String checkTwentyFiveAdvice) {
		this.checkTwentyFiveAdvice = checkTwentyFiveAdvice;
	}
	
}