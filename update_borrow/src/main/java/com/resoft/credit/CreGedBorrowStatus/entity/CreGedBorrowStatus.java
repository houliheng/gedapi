package com.resoft.credit.CreGedBorrowStatus.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 冠E贷同步状态Entity
 * @author zcl
 * @version 2018-03-13
 */
public class CreGedBorrowStatus extends DataEntity<CreGedBorrowStatus> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private Integer status;		// 状态
	private Integer isUnion;	// 是否是联合授信  1是0否
	
	public CreGedBorrowStatus() {
		super();
	}

	public CreGedBorrowStatus(String id){
		super(id);
	}

	@Length(min=0, max=32, message="申请编号长度必须介于 0 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getIsUnion() {
		return isUnion;
	}

	public void setIsUnion(Integer isUnion) {
		this.isUnion = isUnion;
	}
	
}