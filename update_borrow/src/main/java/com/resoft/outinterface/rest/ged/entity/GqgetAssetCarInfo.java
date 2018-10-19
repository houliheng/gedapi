package com.resoft.outinterface.rest.ged.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 冠e通资产车辆信息Entity
 * @author wangguodong
 * @version 2016-10-13
 */
public class GqgetAssetCarInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String gqgetVehicleNo;		// 车牌型号
	private String gqgetUsedYears;		// 使用年限
	private String gqgetCarEvaluatePrice;		// 车辆评估价格
	private String gqgetMarketPrice;		// 市场参考价
	
	public GqgetAssetCarInfo() {
		super();
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