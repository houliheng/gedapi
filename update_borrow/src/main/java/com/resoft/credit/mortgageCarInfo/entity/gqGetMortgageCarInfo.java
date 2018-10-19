package com.resoft.credit.mortgageCarInfo.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 车辆抵质押物信息Entity
 * @author yanwanmei
 * @version 2016-02-29
 */
public class gqGetMortgageCarInfo extends DataEntity<gqGetMortgageCarInfo> {
	
	private static final long serialVersionUID = 1L;
	
	private String vehicleNo;		// 车牌号码
	private String usedYears;
	private String carEvaluatePrice;
	private String marketPrice1;
	
	public gqGetMortgageCarInfo() {
		super();
	}

	public gqGetMortgageCarInfo(String id){
		super(id);
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getUsedYears() {
		return usedYears;
	}

	public void setUsedYears(String usedYears) {
		this.usedYears = usedYears;
	}

	public String getCarEvaluatePrice() {
		return carEvaluatePrice;
	}

	public void setCarEvaluatePrice(String carEvaluatePrice) {
		this.carEvaluatePrice = carEvaluatePrice;
	}

	public String getMarketPrice1() {
		return marketPrice1;
	}

	public void setMarketPrice1(String marketPrice1) {
		this.marketPrice1 = marketPrice1;
	}

}