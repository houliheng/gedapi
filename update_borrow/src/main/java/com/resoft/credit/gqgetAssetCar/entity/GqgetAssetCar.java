package com.resoft.credit.gqgetAssetCar.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 冠e通资产车辆信息Entity
 * @author wangguodong
 * @version 2016-10-13
 */
public class GqgetAssetCar extends DataEntity<GqgetAssetCar> {
	
	private static final long serialVersionUID = 1L;
	private String gqgetApplyNo;		// 申请编号
	private String gqgetVehicleNo;		// 车牌型号
	private String gqgetUsedYears;		// 使用年限
	private String gqgetCarEvaluatePrice;		// 车辆评估价格
	private String gqgetMarketPrice;		// 市场参考价
	
	public GqgetAssetCar() {
		super();
	}

	public GqgetAssetCar(String id){
		super(id);
	}

	@Length(min=1, max=32, message="申请编号长度必须介于 1 和 32 之间")
	public String getGqgetApplyNo() {
		return gqgetApplyNo;
	}

	public void setGqgetApplyNo(String gqgetApplyNo) {
		this.gqgetApplyNo = gqgetApplyNo;
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