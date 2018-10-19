package com.resoft.postloan.mortgageHouseInfo.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 房产抵质押物Entity
 * @author yanwanmei
 * @version 2016-02-29
 */
public class MortgageHouseInfo extends DataEntity<MortgageHouseInfo> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String propertyRight;		// 产权属性
	private String propertyName;		// 产权人名称
	private String currentUseStatus;		// 当前状态
	private String houseUseProperty;		// 房产使用性质
	private String landUseProperty;		// 规划用途
	private String houseType;		// 房产类型
	private String contProvince;		// 地址省
	private String contCity;		// 地址市
	private String contDistinct;		// 地址区
	private String contDetails;		// 地址详细
	private String buildingArea;		// 建筑面积
	private String usingArea;		// 使用面积
	private String floorNum;		// 层数
	private String houseYears;		// 房龄
	private String serviceLife;		// 使用年限
	private String housePurchasePrice;		// 房产取得价格
	private String isKeepPapers;		// 是否留存房产证相关证件
	private String isPropertyLine;		// 有无产权纠纷
	private String isShiftRecord;		// 有无调档记录
	private String propertyStatus;		// 产权目前状态
	private String isUniqueHouse;		// 是否唯一住房
	private String evaluateWay;		// 评估方式价格取得途径
	private String marketPrice;		// 当前市场价格
	private String evaluatePrice;		// 评估价格
	private String mortgageeId;		// 抵押权限人ID
	private String mortgageeName;		// 抵押权限人姓名
	private String mortgageeIdNum;		// 抵押权限人身份证号
	private String delFlag;		// 抵押权限人身份证号
	private String mortgageRate;
	private String houseCardNumber; //房产证登记号
	private String landCardNumber;//土地证登记号
	private String landProperty;//土地性质
	private String isPushData;//是否追加的数据
	private String dealAmount;//处置金额
	private String dealStatus;//处置状态
	
	public String getDealAmount() {
		return dealAmount;
	}

	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}

	public String getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}
	public String getHouseCardNumber() {
		return houseCardNumber;
	}

	public void setHouseCardNumber(String houseCardNumber) {
		this.houseCardNumber = houseCardNumber;
	}

	public String getLandCardNumber() {
		return landCardNumber;
	}

	public void setLandCardNumber(String landCardNumber) {
		this.landCardNumber = landCardNumber;
	}

	public String getLandProperty() {
		return landProperty;
	}

	public void setLandProperty(String landProperty) {
		this.landProperty = landProperty;
	}

	public MortgageHouseInfo() {
		super();
	}

	public MortgageHouseInfo(String id){
		super(id);
	}

	@Length(min=1, max=32, message="申请编号长度必须介于 1 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@Length(min=0, max=1, message="产权属性长度必须介于 0 和 1 之间")
	public String getPropertyRight() {
		return propertyRight;
	}

	public void setPropertyRight(String propertyRight) {
		this.propertyRight = propertyRight;
	}
	
	@Length(min=0, max=20, message="产权人名称长度必须介于 0 和 20 之间")
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
	@Length(min=0, max=4, message="当前状态长度必须介于 0 和 4 之间")
	public String getCurrentUseStatus() {
		return currentUseStatus;
	}

	public void setCurrentUseStatus(String currentUseStatus) {
		this.currentUseStatus = currentUseStatus;
	}
	
	@Length(min=0, max=4, message="房产使用性质长度必须介于 0 和 4 之间")
	public String getHouseUseProperty() {
		return houseUseProperty;
	}

	public void setHouseUseProperty(String houseUseProperty) {
		this.houseUseProperty = houseUseProperty;
	}
	
	@Length(min=0, max=4, message="规划用途长度必须介于 0 和 4 之间")
	public String getLandUseProperty() {
		return landUseProperty;
	}

	public void setLandUseProperty(String landUseProperty) {
		this.landUseProperty = landUseProperty;
	}
	
	@Length(min=0, max=4, message="房产类型长度必须介于 0 和 4 之间")
	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
	
	@Length(min=0, max=10, message="地址省长度必须介于 0 和 10 之间")
	public String getContProvince() {
		return contProvince;
	}

	public void setContProvince(String contProvince) {
		this.contProvince = contProvince;
	}
	
	@Length(min=0, max=10, message="地址市长度必须介于 0 和 10 之间")
	public String getContCity() {
		return contCity;
	}

	public void setContCity(String contCity) {
		this.contCity = contCity;
	}
	
	@Length(min=0, max=10, message="地址区长度必须介于 0 和 10 之间")
	public String getContDistinct() {
		return contDistinct;
	}

	public void setContDistinct(String contDistinct) {
		this.contDistinct = contDistinct;
	}
	
	@Length(min=0, max=300, message="地址详细长度必须介于 0 和 300 之间")
	public String getContDetails() {
		return contDetails;
	}

	public void setContDetails(String contDetails) {
		this.contDetails = contDetails;
	}
	
	public String getBuildingArea() {
		return buildingArea;
	}

	public void setBuildingArea(String buildingArea) {
		this.buildingArea = buildingArea;
	}
	
	public String getUsingArea() {
		return usingArea;
	}

	public void setUsingArea(String usingArea) {
		this.usingArea = usingArea;
	}
	
	@Length(min=0, max=10, message="层数长度必须介于 0 和 10 之间")
	public String getFloorNum() {
		return floorNum;
	}

	public void setFloorNum(String floorNum) {
		this.floorNum = floorNum;
	}
	
	@Length(min=0, max=4, message="房龄长度必须介于 0 和 4 之间")
	public String getHouseYears() {
		return houseYears;
	}

	public void setHouseYears(String houseYears) {
		this.houseYears = houseYears;
	}
	
	@Length(min=0, max=4, message="使用年限长度必须介于 0 和 4 之间")
	public String getServiceLife() {
		return serviceLife;
	}

	public void setServiceLife(String serviceLife) {
		this.serviceLife = serviceLife;
	}
	
	public String getHousePurchasePrice() {
		return housePurchasePrice;
	}

	public void setHousePurchasePrice(String housePurchasePrice) {
		this.housePurchasePrice = housePurchasePrice;
	}
	
	@Length(min=0, max=1, message="是否留存房产证相关证件长度必须介于 0 和 1 之间")
	public String getIsKeepPapers() {
		return isKeepPapers;
	}

	public void setIsKeepPapers(String isKeepPapers) {
		this.isKeepPapers = isKeepPapers;
	}
	
	@Length(min=0, max=1, message="有无产权纠纷长度必须介于 0 和 1 之间")
	public String getIsPropertyLine() {
		return isPropertyLine;
	}

	public void setIsPropertyLine(String isPropertyLine) {
		this.isPropertyLine = isPropertyLine;
	}
	
	@Length(min=0, max=1, message="有无调档记录长度必须介于 0 和 1 之间")
	public String getIsShiftRecord() {
		return isShiftRecord;
	}

	public void setIsShiftRecord(String isShiftRecord) {
		this.isShiftRecord = isShiftRecord;
	}
	
	@Length(min=0, max=4, message="产权目前状态长度必须介于 0 和 4 之间")
	public String getPropertyStatus() {
		return propertyStatus;
	}

	public void setPropertyStatus(String propertyStatus) {
		this.propertyStatus = propertyStatus;
	}
	
	@Length(min=0, max=1, message="是否唯一住房长度必须介于 0 和 1 之间")
	public String getIsUniqueHouse() {
		return isUniqueHouse;
	}

	public void setIsUniqueHouse(String isUniqueHouse) {
		this.isUniqueHouse = isUniqueHouse;
	}
	
	@Length(min=0, max=200, message="评估方式价格取得途径长度必须介于 0 和 200 之间")
	public String getEvaluateWay() {
		return evaluateWay;
	}

	public void setEvaluateWay(String evaluateWay) {
		this.evaluateWay = evaluateWay;
	}
	
	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}
	
	public String getEvaluatePrice() {
		return evaluatePrice;
	}

	public void setEvaluatePrice(String evaluatePrice) {
		this.evaluatePrice = evaluatePrice;
	}
	
	@Length(min=0, max=32, message="抵押权限人ID长度必须介于 0 和 32 之间")
	public String getMortgageeId() {
		return mortgageeId;
	}

	public void setMortgageeId(String mortgageeId) {
		this.mortgageeId = mortgageeId;
	}
	
	@Length(min=0, max=20, message="抵押权限人姓名长度必须介于 0 和 20 之间")
	public String getMortgageeName() {
		return mortgageeName;
	}

	public void setMortgageeName(String mortgageeName) {
		this.mortgageeName = mortgageeName;
	}
	
	@Length(min=0, max=18, message="抵押权限人身份证号长度必须介于 0 和 18 之间")
	public String getMortgageeIdNum() {
		return mortgageeIdNum;
	}

	public void setMortgageeIdNum(String mortgageeIdNum) {
		this.mortgageeIdNum = mortgageeIdNum;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getMortgageRate() {
		return mortgageRate;
	}

	public void setMortgageRate(String mortgageRate) {
		this.mortgageRate = mortgageRate;
	}

	public String getIsPushData() {
		return isPushData;
	}

	public void setIsPushData(String isPushData) {
		this.isPushData = isPushData;
	}
	
}