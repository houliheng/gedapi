package com.resoft.credit.mortgageCarEvaluateInfo.entity;

import org.hibernate.validator.constraints.Length;

import com.resoft.credit.mortgageCarInfo.entity.MortgageCarInfo;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 分公司风控审核-抵质押物信息Entity
 * @author yanwanmei
 * @version 2016-03-01
 */
public class MortgageCarEvaluateInfo extends DataEntity<MortgageCarEvaluateInfo> {
	
	private static final long serialVersionUID = 1L;
	private String carId;		// 抵押车辆ID
	private String motorVehiRegiCertiNo;		// 机动车辆登记证号
	private String vehicleNo;		// 车牌号码
	private String engineNum;		// 发动机号
	private String vehicleShelfNo;		// 车架号
	private String netPurchasePrice;		// 净车购买价格
	private String travelKms;		// 行驶公里数
	private String annualInspectionStatus;		// 年检情况
	private String useProperty;		// 使用性质（字典类型：CAR_USE_PROPERTY）
	private String carStatus;		// 车辆状态
	private String isKeepPapers;		// 是否留存相关证件
	private String isHandle;		// 有无处置能力
	private String usedChanges;		// 已过户次数
	private String usedYears;		// 已使用年限
	private String isCommon;		// 是否常用流通性高
	private String carEvaluatePrice;		// 车辆评估价格
	private String marketPrice1;		// 市场参考价1
	private String marketPhone1;		// 二手市场单位电话1
	private String marketPrice2;		// 市场参考价2
	private String marketPhone2;		// 二手市场单位电话2
	private String marketPrice3;		// 市场参考价3
	private String marketPhone3;		// 二手市场单位电话3
	private String mortgageRate; //抵押率
	private String isProcedureComplete;//抵押车辆手续是否齐全
	private MortgageCarInfo mortgageCarInfo;
	
	
	public String getIsProcedureComplete() {
		return isProcedureComplete;
	}

	public void setIsProcedureComplete(String isProcedureComplete) {
		this.isProcedureComplete = isProcedureComplete;
	}
	
	public String getMortgageRate() {
		return mortgageRate;
	}

	public void setMortgageRate(String mortgageRate) {
		this.mortgageRate = mortgageRate;
	}
	
	public MortgageCarInfo getMortgageCarInfo() {
		return mortgageCarInfo;
	}

	public void setMortgageCarInfo(MortgageCarInfo mortgageCarInfo) {
		this.mortgageCarInfo = mortgageCarInfo;
	}

	public MortgageCarEvaluateInfo() {
		super();
	}

	public MortgageCarEvaluateInfo(String id){
		super(id);
	}

	@Length(min=0, max=32, message="抵押车辆ID长度必须介于 0 和 32 之间")
	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}
	
	@Length(min=0, max=30, message="机动车辆登记证号长度必须介于 0 和 30 之间")
	public String getMotorVehiRegiCertiNo() {
		return motorVehiRegiCertiNo;
	}

	public void setMotorVehiRegiCertiNo(String motorVehiRegiCertiNo) {
		this.motorVehiRegiCertiNo = motorVehiRegiCertiNo;
	}
	
	@Length(min=0, max=10, message="车牌号码长度必须介于 0 和 10 之间")
	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	@Length(min=0, max=50, message="发动机号长度必须介于 0 和 50 之间")
	public String getEngineNum() {
		return engineNum;
	}

	public void setEngineNum(String engineNum) {
		this.engineNum = engineNum;
	}
	
	@Length(min=0, max=50, message="车架号长度必须介于 0 和 50 之间")
	public String getVehicleShelfNo() {
		return vehicleShelfNo;
	}

	public void setVehicleShelfNo(String vehicleShelfNo) {
		this.vehicleShelfNo = vehicleShelfNo;
	}
	
	public String getNetPurchasePrice() {
		return netPurchasePrice;
	}

	public void setNetPurchasePrice(String netPurchasePrice) {
		this.netPurchasePrice = netPurchasePrice;
	}
	
	public String getTravelKms() {
		return travelKms;
	}

	public void setTravelKms(String travelKms) {
		this.travelKms = travelKms;
	}
	
	@Length(min=0, max=4, message="年检情况长度必须介于 0 和 4 之间")
	public String getAnnualInspectionStatus() {
		return annualInspectionStatus;
	}

	public void setAnnualInspectionStatus(String annualInspectionStatus) {
		this.annualInspectionStatus = annualInspectionStatus;
	}
	
	@Length(min=0, max=4, message="使用性质（字典类型：CAR_USE_PROPERTY）长度必须介于 0 和 4 之间")
	public String getUseProperty() {
		return useProperty;
	}

	public void setUseProperty(String useProperty) {
		this.useProperty = useProperty;
	}
	
	@Length(min=0, max=4, message="车辆状态长度必须介于 0 和 4 之间")
	public String getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(String carStatus) {
		this.carStatus = carStatus;
	}
	
	@Length(min=0, max=1, message="是否留存相关证件长度必须介于 0 和 1 之间")
	public String getIsKeepPapers() {
		return isKeepPapers;
	}

	public void setIsKeepPapers(String isKeepPapers) {
		this.isKeepPapers = isKeepPapers;
	}
	
	@Length(min=0, max=1, message="有无处置能力长度必须介于 0 和 1 之间")
	public String getIsHandle() {
		return isHandle;
	}

	public void setIsHandle(String isHandle) {
		this.isHandle = isHandle;
	}
	
	@Length(min=0, max=10, message="已过户次数长度必须介于 0 和 10 之间")
	public String getUsedChanges() {
		return usedChanges;
	}

	public void setUsedChanges(String usedChanges) {
		this.usedChanges = usedChanges;
	}
	
	@Length(min=0, max=3, message="已使用年限长度必须介于 0 和 3 之间")
	public String getUsedYears() {
		return usedYears;
	}

	public void setUsedYears(String usedYears) {
		this.usedYears = usedYears;
	}
	
	@Length(min=0, max=1, message="是否常用流通性高长度必须介于 0 和 1 之间")
	public String getIsCommon() {
		return isCommon;
	}

	public void setIsCommon(String isCommon) {
		this.isCommon = isCommon;
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
	
	@Length(min=0, max=15, message="二手市场单位电话1长度必须介于 0 和 15 之间")
	public String getMarketPhone1() {
		return marketPhone1;
	}

	public void setMarketPhone1(String marketPhone1) {
		this.marketPhone1 = marketPhone1;
	}
	
	public String getMarketPrice2() {
		return marketPrice2;
	}

	public void setMarketPrice2(String marketPrice2) {
		this.marketPrice2 = marketPrice2;
	}
	
	@Length(min=0, max=15, message="二手市场单位电话2长度必须介于 0 和 15 之间")
	public String getMarketPhone2() {
		return marketPhone2;
	}

	public void setMarketPhone2(String marketPhone2) {
		this.marketPhone2 = marketPhone2;
	}
	
	public String getMarketPrice3() {
		return marketPrice3;
	}

	public void setMarketPrice3(String marketPrice3) {
		this.marketPrice3 = marketPrice3;
	}
	
	@Length(min=0, max=15, message="二手市场单位电话3长度必须介于 0 和 15 之间")
	public String getMarketPhone3() {
		return marketPhone3;
	}

	public void setMarketPhone3(String marketPhone3) {
		this.marketPhone3 = marketPhone3;
	}
	
}