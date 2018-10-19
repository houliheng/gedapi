package com.resoft.credit.gqgetComInfo.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class WarehouseGoods extends DataEntity<WarehouseGoods> {

	private static final long serialVersionUID = 1L;
	private String applyNo; // 申请编号
	private String approId;// 批复Id
	private String description; // 描述
	private String wareType;// 类型

	public String getApproId() {
		return approId;
	}

	public void setApproId(String approId) {
		this.approId = approId;
	}

	public String getWareType() {
		return wareType;
	}

	public void setWareType(String wareType) {
		this.wareType = wareType;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
