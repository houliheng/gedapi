package com.gq.ged.order.controller.req;

import java.io.Serializable;

/**
 * 冠e通资产车辆信息
 * Created by wrh on 2018/4/26.
 */
public class GqgetAssetCarInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String gqgetVehicleNo;		// 车牌型号
	private String gqgetUsedYears;		// 使用年限
	private String gqgetCarEvaluatePrice;		// 车辆评估价格
	private String gqgetMarketPrice;		// 市场参考价

	public String getGqgetVehicleNo() {
		return gqgetVehicleNo;
	}

	public void setGqgetVehicleNo(String gqgetVehicleNo) {
		this.gqgetVehicleNo = gqgetVehicleNo;
	}

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