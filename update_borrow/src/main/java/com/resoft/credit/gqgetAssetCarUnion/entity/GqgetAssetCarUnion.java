package com.resoft.credit.gqgetAssetCarUnion.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 联合授信冠e通车辆资产Entity
 * @author lixing
 * @version 2016-12-26
 */
public class GqgetAssetCarUnion extends DataEntity<GqgetAssetCarUnion> {
	
	private static final long serialVersionUID = 1L;
	private String gqgetApplyNo;		// 申请编号
	private String approveId;		// 批复ID
	private String gqgetVehicleNo;		// 车牌型号
	private String gqgetUsedYears;		// 使用年限
	private String gqgetCarEvaluatePrice;		// 车辆评估价格
	private String gqgetMarketPrice;		// 市场参考价
	
	public GqgetAssetCarUnion() {
		super();
	}

	public GqgetAssetCarUnion(String id){
		super(id);
	}

	@Length(min=1, max=32, message="申请编号长度必须介于 1 和 32 之间")
	public String getGqgetApplyNo() {
		return gqgetApplyNo;
	}

	public void setGqgetApplyNo(String gqgetApplyNo) {
		this.gqgetApplyNo = gqgetApplyNo;
	}
	
	@Length(min=1, max=32, message="批复ID长度必须介于 1 和 32 之间")
	public String getApproveId() {
		return approveId;
	}

	public void setApproveId(String approveId) {
		this.approveId = approveId;
	}
	
	@Length(min=0, max=10, message="车牌型号长度必须介于 0 和 10 之间")
	public String getGqgetVehicleNo() {
		return gqgetVehicleNo;
	}

	public void setGqgetVehicleNo(String gqgetVehicleNo) {
		this.gqgetVehicleNo = gqgetVehicleNo;
	}
	
	@Length(min=0, max=3, message="使用年限长度必须介于 0 和 3 之间")
	public String getGqgetUsedYears() {
		return gqgetUsedYears;
	}

	public void setGqgetUsedYears(String gqgetUsedYears) {
		this.gqgetUsedYears = gqgetUsedYears;
	}
	
	public String getGqgetCarEvaluatePrice() {
		return gqgetCarEvaluatePrice;
	}

	public void setGqgetCarEvaluatePrice(String gqgetCarEvaluatePrice) {
		this.gqgetCarEvaluatePrice = gqgetCarEvaluatePrice;
	}
	
	public String getGqgetMarketPrice() {
		return gqgetMarketPrice;
	}

	public void setGqgetMarketPrice(String gqgetMarketPrice) {
		this.gqgetMarketPrice = gqgetMarketPrice;
	}
	
}