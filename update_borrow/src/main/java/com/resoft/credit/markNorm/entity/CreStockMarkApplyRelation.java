package com.resoft.credit.markNorm.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 股权信息加减分关系Entity
 * @author zcl
 * @version 2017-10-18
 */
public class CreStockMarkApplyRelation extends DataEntity<CreStockMarkApplyRelation> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String markNormId;		// 评分标准表主键
	
	public CreStockMarkApplyRelation() {
		super();
	}

	public CreStockMarkApplyRelation(String id){
		super(id);
	}

	@Length(min=0, max=32, message="申请编号长度必须介于 0 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@Length(min=0, max=32, message="评分标准表主键长度必须介于 0 和 32 之间")
	public String getMarkNormId() {
		return markNormId;
	}

	public void setMarkNormId(String markNormId) {
		this.markNormId = markNormId;
	}
	
}