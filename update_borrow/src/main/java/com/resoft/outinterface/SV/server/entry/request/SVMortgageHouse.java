package com.resoft.outinterface.SV.server.entry.request;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.utils.IdGen;

/**
 * SV回盘房产抵押信息Entity
 * 
 * @author admin
 * @version 2016-04-21
 */
@XmlAccessorType(XmlAccessType.NONE)
public class SVMortgageHouse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String applyNo; // 申请编号
	private Date createDate; // 创建日期
	private Date updateDate; // 更新日期
	private String delFlag; // 删除标记（0：正常；1：删除；2：审核）

	@XmlElement(required = true, name = "PROPERTY_RIGHT")
	private String propertyRight; // 产权属性（个人，企业）
	@XmlElement(required = true, name = "PROPERTY_NAME")
	private String propertyName; // 产权人名称
	@XmlElement(required = true, name = "CURRENT_USE_STATUS")
	private String currentUseStatus; // 当前房屋状态
	@XmlElement(required = true, name = "HOUSE_USE_PROPERTY")
	private String houseUseProperty; // 房产使用性质
	@XmlElement(required = true, name = "LAND_USE_PROPERTY")
	private String landUseProperty; // 规划用途
	@XmlElement(required = true, name = "HOUSE_TYPE")
	private String houseType; // 房产类型
	@XmlElement(required = true, name = "CONT_PROVINCE")
	private String contProvince; // 地址省
	@XmlElement(required = true, name = "CONT_CITY")
	private String contCity; // 地址市
	@XmlElement(required = true, name = "CONT_DISTINCT")
	private String contDistinct; // 地址区
	@XmlElement(required = true, name = "CONT_DETAILS")
	private String contDetails; // 地址详细
	@XmlElement(required = true, name = "BUILDING_AREA")
	private String buildingArea; // 建筑面积
	@XmlElement(required = true, name = "USING_AREA")
	private String usingArea; // 使用面积
	@XmlElement(required = true, name = "FLOOR_NUM")
	private String floorNum; // 层数
	@XmlElement(required = true, name = "HOUSE_YEARS")
	private String houseYears; // 房龄
	@XmlElement(required = true, name = "SERVICE_LIFE")
	private String serviceLife; // 使用年限
	@XmlElement(required = true, name = "HOUSE_PURCHASE_PRICE")
	private String housePurchasePrice; // 房产取得价格
	@XmlElement(required = true, name = "IS_KEEP_PAPERS")
	private String isKeepPapers; // 是否留存房产证相关证件
	@XmlElement(required = true, name = "IS_PROPERTY_LINE")
	private String isPropertyLine; // 有无产权纠纷
	@XmlElement(required = true, name = "IS_SHIFT_RECORD")
	private String isShiftRecord; // 有无调档记录
	@XmlElement(required = true, name = "PROPERTY_STATUS")
	private String propertyStatus; // 产权目前状态
	@XmlElement(required = true, name = "IS_UNIQUE_HOUSE")
	private String isUniqueHouse; // 是否唯一住房
	@XmlElement(required = true, name = "EVALUATE_WAY")
	private String evaluateWay; // 评估方式价格取得途径
	@XmlElement(required = true, name = "MARKET_PRICE")
	private String marketPrice; // 当前市场价格
	@XmlElement(required = true, name = "EVALUATE_PRICE")
	private String evaluatePrice; // 评估价格

	public SVMortgageHouse() {
		super();
	}

	public SVMortgageHouse(String id, String applyNo, Date createDate, Date updateDate, String delFlag, String propertyRight, String propertyName, String currentUseStatus, String houseUseProperty, String landUseProperty, String houseType, String contProvince, String contCity, String contDistinct, String contDetails, String buildingArea, String usingArea, String floorNum, String houseYears, String serviceLife, String housePurchasePrice, String isKeepPapers, String isPropertyLine, String isShiftRecord, String propertyStatus, String isUniqueHouse, String evaluateWay, String marketPrice, String evaluatePrice) {
		super();
		this.id = id;
		this.applyNo = applyNo;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.delFlag = delFlag;
		this.propertyRight = propertyRight;
		this.propertyName = propertyName;
		this.currentUseStatus = currentUseStatus;
		this.houseUseProperty = houseUseProperty;
		this.landUseProperty = landUseProperty;
		this.houseType = houseType;
		this.contProvince = contProvince;
		this.contCity = contCity;
		this.contDistinct = contDistinct;
		this.contDetails = contDetails;
		this.buildingArea = buildingArea;
		this.usingArea = usingArea;
		this.floorNum = floorNum;
		this.houseYears = houseYears;
		this.serviceLife = serviceLife;
		this.housePurchasePrice = housePurchasePrice;
		this.isKeepPapers = isKeepPapers;
		this.isPropertyLine = isPropertyLine;
		this.isShiftRecord = isShiftRecord;
		this.propertyStatus = propertyStatus;
		this.isUniqueHouse = isUniqueHouse;
		this.evaluateWay = evaluateWay;
		this.marketPrice = marketPrice;
		this.evaluatePrice = evaluatePrice;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	@Length(min = 0, max = 32, message = "申请编号长度必须介于 0 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	@Length(min = 0, max = 1, message = "产权属性（个人，企业）长度必须介于 0 和 1 之间")
	public String getPropertyRight() {
		return propertyRight;
	}

	public void setPropertyRight(String propertyRight) {
		this.propertyRight = propertyRight;
	}

	@Length(min = 0, max = 20, message = "产权人名称长度必须介于 0 和 20 之间")
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	@Length(min = 0, max = 4, message = "当前房屋状态长度必须介于 0 和 4 之间")
	public String getCurrentUseStatus() {
		return currentUseStatus;
	}

	public void setCurrentUseStatus(String currentUseStatus) {
		this.currentUseStatus = currentUseStatus;
	}

	@Length(min = 0, max = 4, message = "房产使用性质长度必须介于 0 和 4 之间")
	public String getHouseUseProperty() {
		return houseUseProperty;
	}

	public void setHouseUseProperty(String houseUseProperty) {
		this.houseUseProperty = houseUseProperty;
	}

	@Length(min = 0, max = 4, message = "规划用途长度必须介于 0 和 4 之间")
	public String getLandUseProperty() {
		return landUseProperty;
	}

	public void setLandUseProperty(String landUseProperty) {
		this.landUseProperty = landUseProperty;
	}

	@Length(min = 0, max = 4, message = "房产类型长度必须介于 0 和 4 之间")
	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	@Length(min = 0, max = 10, message = "地址省长度必须介于 0 和 10 之间")
	public String getContProvince() {
		return contProvince;
	}

	public void setContProvince(String contProvince) {
		this.contProvince = contProvince;
	}

	@Length(min = 0, max = 10, message = "地址市长度必须介于 0 和 10 之间")
	public String getContCity() {
		return contCity;
	}

	public void setContCity(String contCity) {
		this.contCity = contCity;
	}

	@Length(min = 0, max = 10, message = "地址区长度必须介于 0 和 10 之间")
	public String getContDistinct() {
		return contDistinct;
	}

	public void setContDistinct(String contDistinct) {
		this.contDistinct = contDistinct;
	}

	@Length(min = 0, max = 300, message = "地址详细长度必须介于 0 和 300 之间")
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

	@Length(min = 0, max = 3, message = "层数长度必须介于 0 和 3 之间")
	public String getFloorNum() {
		return floorNum;
	}

	public void setFloorNum(String floorNum) {
		this.floorNum = floorNum;
	}

	@Length(min = 0, max = 4, message = "房龄长度必须介于 0 和 4 之间")
	public String getHouseYears() {
		return houseYears;
	}

	public void setHouseYears(String houseYears) {
		this.houseYears = houseYears;
	}

	@Length(min = 0, max = 4, message = "使用年限长度必须介于 0 和 4 之间")
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

	@Length(min = 0, max = 1, message = "是否留存房产证相关证件长度必须介于 0 和 1 之间")
	public String getIsKeepPapers() {
		return isKeepPapers;
	}

	public void setIsKeepPapers(String isKeepPapers) {
		this.isKeepPapers = isKeepPapers;
	}

	@Length(min = 0, max = 1, message = "有无产权纠纷长度必须介于 0 和 1 之间")
	public String getIsPropertyLine() {
		return isPropertyLine;
	}

	public void setIsPropertyLine(String isPropertyLine) {
		this.isPropertyLine = isPropertyLine;
	}

	@Length(min = 0, max = 1, message = "有无调档记录长度必须介于 0 和 1 之间")
	public String getIsShiftRecord() {
		return isShiftRecord;
	}

	public void setIsShiftRecord(String isShiftRecord) {
		this.isShiftRecord = isShiftRecord;
	}

	@Length(min = 0, max = 4, message = "产权目前状态长度必须介于 0 和 4 之间")
	public String getPropertyStatus() {
		return propertyStatus;
	}

	public void setPropertyStatus(String propertyStatus) {
		this.propertyStatus = propertyStatus;
	}

	@Length(min = 0, max = 1, message = "是否唯一住房长度必须介于 0 和 1 之间")
	public String getIsUniqueHouse() {
		return isUniqueHouse;
	}

	public void setIsUniqueHouse(String isUniqueHouse) {
		this.isUniqueHouse = isUniqueHouse;
	}

	@Length(min = 0, max = 200, message = "评估方式价格取得途径长度必须介于 0 和 200 之间")
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

	public void preInsert() {
		setId(IdGen.uuid());
		this.createDate = new Date();
	}

}