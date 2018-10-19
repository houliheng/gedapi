package com.resoft.postloan.mortgageEvaluateInfo.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 抵质押物检查项目Entity
 * @author 赵华奎
 * @version 2016-05-31
 */
public class MortgageEvaluateItems extends DataEntity<MortgageEvaluateItems> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		    // 申请编号
	private String checkItems;		// 检查项目
	private String checkValue;		// 检查结果
	private String description;		// 备注
	private String infoId;		// 抵质押Id
	
	public MortgageEvaluateItems() {
		super();
	}

	public MortgageEvaluateItems(String id){
		super(id);
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	@Length(min=0, max=10, message="检查项目长度必须介于 0 和 10 之间")
	public String getCheckItems() {
		return checkItems;
	}

	public void setCheckItems(String checkItems) {
		this.checkItems = checkItems;
	}
	
	@Length(min=0, max=1000, message="检查结果长度必须介于 0 和 1000 之间")
	public String getCheckValue() {
		return checkValue;
	}

	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}
	
	@Length(min=0, max=1000, message="备注长度必须介于 0 和 1000 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}