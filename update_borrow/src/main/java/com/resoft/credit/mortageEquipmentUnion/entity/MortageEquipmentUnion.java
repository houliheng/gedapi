package com.resoft.credit.mortageEquipmentUnion.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 联合授信设备抵押Entity
 * @author wangguodong
 * @version 2016-12-26
 */
public class MortageEquipmentUnion extends DataEntity<MortageEquipmentUnion> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String approveId;		// 批复ID
	private String model;		// 型号
	private String buyingPrice;		// 购买价格
	private String valueOfAssessment;		// 估值
	private String maketValue;		// 市值
	
	public MortageEquipmentUnion() {
		super();
	}

	public MortageEquipmentUnion(String id){
		super(id);
	}

	@Length(min=1, max=32, message="申请编号长度必须介于 1 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@Length(min=1, max=32, message="批复ID长度必须介于 1 和 32 之间")
	public String getApproveId() {
		return approveId;
	}

	public void setApproveId(String approveId) {
		this.approveId = approveId;
	}
	
	@Length(min=0, max=32, message="型号长度必须介于 0 和 32 之间")
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	@Length(min=0, max=20, message="购买价格长度必须介于 0 和 20 之间")
	public String getBuyingPrice() {
		return buyingPrice;
	}

	public void setBuyingPrice(String buyingPrice) {
		this.buyingPrice = buyingPrice;
	}
	
	@Length(min=0, max=20, message="估值长度必须介于 0 和 20 之间")
	public String getValueOfAssessment() {
		return valueOfAssessment;
	}

	public void setValueOfAssessment(String valueOfAssessment) {
		this.valueOfAssessment = valueOfAssessment;
	}
	
	@Length(min=0, max=20, message="市值长度必须介于 0 和 20 之间")
	public String getMaketValue() {
		return maketValue;
	}

	public void setMaketValue(String maketValue) {
		this.maketValue = maketValue;
	}
	
}