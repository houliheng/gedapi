package com.resoft.outinterface.SV.server.entry.request;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * SV回盘车辆抵押信息Entity
 * 
 * @author admin
 * @version 2016-04-21
 */
@XmlAccessorType(XmlAccessType.NONE)
public class SVMortgageCar implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String applyNo; // 申请编号
	private String remarks; // 备注
	private Date createDate; // 创建日期
	private Date updateDate; // 更新日期
	private String delFlag; // 删除标记（0：正常；1：删除；2：审核）
	@XmlElement(required = true, name = "PROPERTY_RIGHT")
	private String propertyRight; // 产权属性（个人，企业）
	@XmlElement(required = true, name = "MORTGAGE_TYPE")
	private String mortgageType; // 抵押质押类型
	@XmlElement(required = true, name = "PROPERTY_CO_NAME")
	private String propertyCoName; // 产权共有人名称
	@XmlElement(required = true, name = "PROPERTY_NAME")
	private String propertyName; // 产权人名称
	@XmlElement(required = true, name = "BUY_DATE")
	private Date buyDate; // 购置日期
	@XmlElement(required = true, name = "VEHICLE_NO")
	private String vehicleNo; // 车辆编号
	@XmlElement(required = true, name = "VEHICLE_BRAND")
	private String vehicleBrand; // 车辆品牌
	@XmlElement(required = true, name = "VEHICLE_TYPE")
	private String vehicleType; // 车辆类型
	@XmlElement(required = true, name = "VEHICLE_SERIES")
	private String vehicleSeries; // 车辆系列
	@XmlElement(required = true, name = "PROPERTY_OF_COM_OWNERS")
	private String propertyOfComOwners; // 财产共有人姓名
	@XmlElement(required = true, name = "OTHER_INFORMATION")
	private String otherInformation; // 其他信息
	@XmlElement(required = true, name = "BUSI_REG_NAME")
	private String busiRegName; // 工商登记名称
	@XmlElement(required = true, name = "BUSI_LIC_REG_NO")
	private String busiLicRegNo; // 营业执照注册号
	@XmlElement(required = true, name = "OPERATE_PROVINCE")
	private String operateProvince; // 经营地址：省
	@XmlElement(required = true, name = "OPERATE_CITY")
	private String operateCity; // 经营地址：市
	@XmlElement(required = true, name = "OPERATE_DISTINCT")
	private String operateDistinct; // 经营地址：区
	@XmlElement(required = true, name = "OPERATE_DETAILS")
	private String operateDetails; // 经营地址详细
	@XmlElement(required = true, name = "CORPORATION_NAME")
	private String corporationName; // 法人名称
	@XmlElement(required = true, name = "CORPORATION_CARD_ID_NO")
	private String corporationCardIdNo; // 法人证件号
	@XmlElement(required = true, name = "CLIENT_NAME")
	private String clientName; // 委托人名称
	@XmlElement(required = true, name = "CLIENT_ID_NO")
	private String clientIdNo; // 委托人身份号
	@XmlElement(required = true, name = "CLIENT_PHONE")
	private String clientPhone; // 委托人手机号
	@XmlElement(required = true, name = "CLIENT_COMPANY_RELATION")
	private String clientCompanyRelation; // 委托人与公司的关系
	@XmlElement(required = true, name = "MOTOR_VEHI_REGI_CERTI_NO")
	private String motorVehiRegiCertiNo; // 机动车辆登记证号
	@XmlElement(required = true, name = "ENGINE_NUM")
	private String engineNum; // 发动机号
	@XmlElement(required = true, name = "VEHICLE_SHELF_NO")
	private String vehicleShelfNo; // 车架号
	@XmlElement(required = true, name = "MORTGAGE_RATE")
	private String mortgageRate; // mortgage_rate
	@XmlElement(required = true, name = "NET_PURCHASE_PRICE")
	private String netPurchasePrice; // 净车购买价格
	@XmlElement(required = true, name = "IS_PROCEDURE_COMPLETE")
	private String isProcedureComplete; // 抵押车辆手续是否齐全
	@XmlElement(required = true, name = "TRAVEL_KMS")
	private String travelKms; // 行驶公里数
	@XmlElement(required = true, name = "ANNUAL_INSPECTION_STATUS")
	private String annualInspectionStatus; // 年检情况
	@XmlElement(required = true, name = "USE_PROPERTY")
	private String useProperty; // 使用性质（字典类型：CAR_USE_PROPERTY）
	@XmlElement(required = true, name = "CAR_STATUS")
	private String carStatus; // 车辆状态
	@XmlElement(required = true, name = "IS_KEEP_PAPERS")
	private String isKeepPapers; // 是否留存相关证件
	@XmlElement(required = true, name = "IS_HANDLE")
	private String isHandle; // 有无处置能力
	@XmlElement(required = true, name = "USED_CHANGES")
	private String usedChanges; // 已过户次数
	@XmlElement(required = true, name = "USED_YEARS")
	private String usedYears; // 已使用年限
	@XmlElement(required = true, name = "IS_COMMON")
	private String isCommon; // 是否常用流通性高
	@XmlElement(required = true, name = "CAR_EVALUATE_PRICE")
	private String carEvaluatePrice; // 车辆评估价格
	@XmlElement(required = true, name = "MARKET_PRICE1")
	private String marketPrice1; // 市场参考价1
	@XmlElement(required = true, name = "MARKET_PHONE1")
	private String marketPhone1; // 二手市场单位电话1
	@XmlElement(required = true, name = "MARKET_PRICE2")
	private String marketPrice2; // 市场参考价2
	@XmlElement(required = true, name = "MARKET_PHONE2")
	private String marketPhone2; // 二手市场单位电话2
	@XmlElement(required = true, name = "MARKET_PRICE3")
	private String marketPrice3; // 市场参考价3
	@XmlElement(required = true, name = "MARKET_PHONE3")
	private String marketPhone3; // 二手市场单位电话3

	public SVMortgageCar() {
		super();
	}

	public SVMortgageCar(String id, String applyNo, String remarks, User createBy, Date createDate, User updateBy, Date updateDate, String delFlag, String propertyRight, String mortgageType, String propertyCoName, String propertyName, Date buyDate, String vehicleNo, String vehicleBrand, String vehicleType, String vehicleSeries, String propertyOfComOwners, String otherInformation, String busiRegName, String busiLicRegNo, String operateProvince, String operateCity, String operateDistinct, String operateDetails, String corporationName, String corporationCardIdNo, String clientName, String clientIdNo, String clientPhone, String clientCompanyRelation, String motorVehiRegiCertiNo, String engineNum, String vehicleShelfNo, String mortgageRate, String netPurchasePrice, String isProcedureComplete, String travelKms, String annualInspectionStatus, String useProperty, String carStatus, String isKeepPapers, String isHandle, String usedChanges, String usedYears, String isCommon, String carEvaluatePrice, String marketPrice1, String marketPhone1, String marketPrice2, String marketPhone2, String marketPrice3, String marketPhone3) {
		super();
		this.id = id;
		this.applyNo = applyNo;
		this.remarks = remarks;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.delFlag = delFlag;
		this.propertyRight = propertyRight;
		this.mortgageType = mortgageType;
		this.propertyCoName = propertyCoName;
		this.propertyName = propertyName;
		this.buyDate = buyDate;
		this.vehicleNo = vehicleNo;
		this.vehicleBrand = vehicleBrand;
		this.vehicleType = vehicleType;
		this.vehicleSeries = vehicleSeries;
		this.propertyOfComOwners = propertyOfComOwners;
		this.otherInformation = otherInformation;
		this.busiRegName = busiRegName;
		this.busiLicRegNo = busiLicRegNo;
		this.operateProvince = operateProvince;
		this.operateCity = operateCity;
		this.operateDistinct = operateDistinct;
		this.operateDetails = operateDetails;
		this.corporationName = corporationName;
		this.corporationCardIdNo = corporationCardIdNo;
		this.clientName = clientName;
		this.clientIdNo = clientIdNo;
		this.clientPhone = clientPhone;
		this.clientCompanyRelation = clientCompanyRelation;
		this.motorVehiRegiCertiNo = motorVehiRegiCertiNo;
		this.engineNum = engineNum;
		this.vehicleShelfNo = vehicleShelfNo;
		this.mortgageRate = mortgageRate;
		this.netPurchasePrice = netPurchasePrice;
		this.isProcedureComplete = isProcedureComplete;
		this.travelKms = travelKms;
		this.annualInspectionStatus = annualInspectionStatus;
		this.useProperty = useProperty;
		this.carStatus = carStatus;
		this.isKeepPapers = isKeepPapers;
		this.isHandle = isHandle;
		this.usedChanges = usedChanges;
		this.usedYears = usedYears;
		this.isCommon = isCommon;
		this.carEvaluatePrice = carEvaluatePrice;
		this.marketPrice1 = marketPrice1;
		this.marketPhone1 = marketPhone1;
		this.marketPrice2 = marketPrice2;
		this.marketPhone2 = marketPhone2;
		this.marketPrice3 = marketPrice3;
		this.marketPhone3 = marketPhone3;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	@Length(min = 1, max = 32, message = "申请编号长度必须介于 1 和 32 之间")
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

	@Length(min = 0, max = 4, message = "抵押质押类型长度必须介于 0 和 4 之间")
	public String getMortgageType() {
		return mortgageType;
	}

	public void setMortgageType(String mortgageType) {
		this.mortgageType = mortgageType;
	}

	@Length(min = 0, max = 20, message = "产权共有人名称长度必须介于 0 和 20 之间")
	public String getPropertyCoName() {
		return propertyCoName;
	}

	public void setPropertyCoName(String propertyCoName) {
		this.propertyCoName = propertyCoName;
	}

	@Length(min = 0, max = 20, message = "产权人名称长度必须介于 0 和 20 之间")
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	@Length(min = 0, max = 50, message = "车辆编号长度必须介于 0 和 50 之间")
	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	@Length(min = 0, max = 10, message = "车辆品牌长度必须介于 0 和 10 之间")
	public String getVehicleBrand() {
		return vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

	@Length(min = 0, max = 10, message = "车辆类型长度必须介于 0 和 10 之间")
	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	@Length(min = 0, max = 10, message = "车辆系列长度必须介于 0 和 10 之间")
	public String getVehicleSeries() {
		return vehicleSeries;
	}

	public void setVehicleSeries(String vehicleSeries) {
		this.vehicleSeries = vehicleSeries;
	}

	@Length(min = 0, max = 20, message = "财产共有人姓名长度必须介于 0 和 20 之间")
	public String getPropertyOfComOwners() {
		return propertyOfComOwners;
	}

	public void setPropertyOfComOwners(String propertyOfComOwners) {
		this.propertyOfComOwners = propertyOfComOwners;
	}

	@Length(min = 0, max = 1000, message = "其他信息长度必须介于 0 和 1000 之间")
	public String getOtherInformation() {
		return otherInformation;
	}

	public void setOtherInformation(String otherInformation) {
		this.otherInformation = otherInformation;
	}

	@Length(min = 0, max = 300, message = "工商登记名称长度必须介于 0 和 300 之间")
	public String getBusiRegName() {
		return busiRegName;
	}

	public void setBusiRegName(String busiRegName) {
		this.busiRegName = busiRegName;
	}

	@Length(min = 0, max = 50, message = "营业执照注册号长度必须介于 0 和 50 之间")
	public String getBusiLicRegNo() {
		return busiLicRegNo;
	}

	public void setBusiLicRegNo(String busiLicRegNo) {
		this.busiLicRegNo = busiLicRegNo;
	}

	@Length(min = 0, max = 10, message = "经营地址：省长度必须介于 0 和 10 之间")
	public String getOperateProvince() {
		return operateProvince;
	}

	public void setOperateProvince(String operateProvince) {
		this.operateProvince = operateProvince;
	}

	@Length(min = 0, max = 10, message = "经营地址：市长度必须介于 0 和 10 之间")
	public String getOperateCity() {
		return operateCity;
	}

	public void setOperateCity(String operateCity) {
		this.operateCity = operateCity;
	}

	@Length(min = 0, max = 10, message = "经营地址：区长度必须介于 0 和 10 之间")
	public String getOperateDistinct() {
		return operateDistinct;
	}

	public void setOperateDistinct(String operateDistinct) {
		this.operateDistinct = operateDistinct;
	}

	@Length(min = 0, max = 300, message = "经营地址详细长度必须介于 0 和 300 之间")
	public String getOperateDetails() {
		return operateDetails;
	}

	public void setOperateDetails(String operateDetails) {
		this.operateDetails = operateDetails;
	}

	@Length(min = 0, max = 300, message = "法人名称长度必须介于 0 和 300 之间")
	public String getCorporationName() {
		return corporationName;
	}

	public void setCorporationName(String corporationName) {
		this.corporationName = corporationName;
	}

	@Length(min = 0, max = 18, message = "法人证件号长度必须介于 0 和 18 之间")
	public String getCorporationCardIdNo() {
		return corporationCardIdNo;
	}

	public void setCorporationCardIdNo(String corporationCardIdNo) {
		this.corporationCardIdNo = corporationCardIdNo;
	}

	@Length(min = 0, max = 20, message = "委托人名称长度必须介于 0 和 20 之间")
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	@Length(min = 0, max = 18, message = "委托人身份号长度必须介于 0 和 18 之间")
	public String getClientIdNo() {
		return clientIdNo;
	}

	public void setClientIdNo(String clientIdNo) {
		this.clientIdNo = clientIdNo;
	}

	@Length(min = 0, max = 15, message = "委托人手机号长度必须介于 0 和 15 之间")
	public String getClientPhone() {
		return clientPhone;
	}

	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}

	@Length(min = 0, max = 4, message = "委托人与公司的关系长度必须介于 0 和 4 之间")
	public String getClientCompanyRelation() {
		return clientCompanyRelation;
	}

	public void setClientCompanyRelation(String clientCompanyRelation) {
		this.clientCompanyRelation = clientCompanyRelation;
	}

	@Length(min = 0, max = 30, message = "机动车辆登记证号长度必须介于 0 和 30 之间")
	public String getMotorVehiRegiCertiNo() {
		return motorVehiRegiCertiNo;
	}

	public void setMotorVehiRegiCertiNo(String motorVehiRegiCertiNo) {
		this.motorVehiRegiCertiNo = motorVehiRegiCertiNo;
	}

	@Length(min = 0, max = 50, message = "发动机号长度必须介于 0 和 50 之间")
	public String getEngineNum() {
		return engineNum;
	}

	public void setEngineNum(String engineNum) {
		this.engineNum = engineNum;
	}

	@Length(min = 0, max = 50, message = "车架号长度必须介于 0 和 50 之间")
	public String getVehicleShelfNo() {
		return vehicleShelfNo;
	}

	public void setVehicleShelfNo(String vehicleShelfNo) {
		this.vehicleShelfNo = vehicleShelfNo;
	}

	public String getMortgageRate() {
		return mortgageRate;
	}

	public void setMortgageRate(String mortgageRate) {
		this.mortgageRate = mortgageRate;
	}

	public String getNetPurchasePrice() {
		return netPurchasePrice;
	}

	public void setNetPurchasePrice(String netPurchasePrice) {
		this.netPurchasePrice = netPurchasePrice;
	}

	@Length(min = 0, max = 1, message = "抵押车辆手续是否齐全长度必须介于 0 和 1 之间")
	public String getIsProcedureComplete() {
		return isProcedureComplete;
	}

	public void setIsProcedureComplete(String isProcedureComplete) {
		this.isProcedureComplete = isProcedureComplete;
	}

	public String getTravelKms() {
		return travelKms;
	}

	public void setTravelKms(String travelKms) {
		this.travelKms = travelKms;
	}

	@Length(min = 0, max = 4, message = "年检情况长度必须介于 0 和 4 之间")
	public String getAnnualInspectionStatus() {
		return annualInspectionStatus;
	}

	public void setAnnualInspectionStatus(String annualInspectionStatus) {
		this.annualInspectionStatus = annualInspectionStatus;
	}

	@Length(min = 0, max = 4, message = "使用性质（字典类型：CAR_USE_PROPERTY）长度必须介于 0 和 4 之间")
	public String getUseProperty() {
		return useProperty;
	}

	public void setUseProperty(String useProperty) {
		this.useProperty = useProperty;
	}

	@Length(min = 0, max = 4, message = "车辆状态长度必须介于 0 和 4 之间")
	public String getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(String carStatus) {
		this.carStatus = carStatus;
	}

	@Length(min = 0, max = 1, message = "是否留存相关证件长度必须介于 0 和 1 之间")
	public String getIsKeepPapers() {
		return isKeepPapers;
	}

	public void setIsKeepPapers(String isKeepPapers) {
		this.isKeepPapers = isKeepPapers;
	}

	@Length(min = 0, max = 1, message = "有无处置能力长度必须介于 0 和 1 之间")
	public String getIsHandle() {
		return isHandle;
	}

	public void setIsHandle(String isHandle) {
		this.isHandle = isHandle;
	}

	@Length(min = 0, max = 10, message = "已过户次数长度必须介于 0 和 10 之间")
	public String getUsedChanges() {
		return usedChanges;
	}

	public void setUsedChanges(String usedChanges) {
		this.usedChanges = usedChanges;
	}

	@Length(min = 0, max = 3, message = "已使用年限长度必须介于 0 和 3 之间")
	public String getUsedYears() {
		return usedYears;
	}

	public void setUsedYears(String usedYears) {
		this.usedYears = usedYears;
	}

	@Length(min = 0, max = 1, message = "是否常用流通性高长度必须介于 0 和 1 之间")
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

	@Length(min = 0, max = 15, message = "二手市场单位电话1长度必须介于 0 和 15 之间")
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

	@Length(min = 0, max = 15, message = "二手市场单位电话2长度必须介于 0 和 15 之间")
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

	@Length(min = 0, max = 15, message = "二手市场单位电话3长度必须介于 0 和 15 之间")
	public String getMarketPhone3() {
		return marketPhone3;
	}

	public void setMarketPhone3(String marketPhone3) {
		this.marketPhone3 = marketPhone3;
	}

	public void preInsert() {
		setId(IdGen.uuid());
		this.createDate = new Date();
	}

}