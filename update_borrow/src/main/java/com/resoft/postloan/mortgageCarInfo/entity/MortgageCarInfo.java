package com.resoft.postloan.mortgageCarInfo.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.resoft.postloan.mortgageCarEvaluateInfo.entity.MortgageCarEvaluateInfo;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 车辆抵质押物信息Entity
 * @author yanwanmei
 * @version 2016-02-29
 */
public class MortgageCarInfo extends DataEntity<MortgageCarInfo> {
	
	private static final long serialVersionUID = 1L;
	private String propertyRight;		// 产权属性
	private String propertyName;		// 产权人姓名
	private String applyNo;		// 申请编号
	private String busiLicRegNo;		// 营业执照注册号
	private String mortgageType;		// 抵押质押类型
	private String vehicleNo;		// 车牌号码
	private String vehicleBrand;		// 车辆品牌
	private Date buyDate;		// 购置日期
	private String vehicleType;		// 车辆类型
	private String vehicleSeries;		// 车辆系列
	private String propertyOfComOwners;		// 财产共有人姓名
	private String otherInformation;		// 其他信息
	private String busiRegName;		// 工商登记名称
	private String operateProvince;		// 经营地址：省
	private String operateCity;		// 经营地址：市
	private String operateDistinct;		// 经营地址：区
	private String operateDetails;		// 经营地址详细
	private String corporationName;		// 法人名称
	private String corporationCardIdNo;		// 法人证件号
	private String delFlag;		// 委托人名称
	private String clientName;		// 委托人名称
	private String clientIdNo;		// 委托人身份号
	private String clientPhone;		// 委托人手机号
	private String isPushData;		// 是否追加的抵质押物
	private String clientCompanyRelation;		// 委托人与公司的关系
	private MortgageCarEvaluateInfo mortgageCarEvaluateInfo;
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

	public MortgageCarInfo() {
		super();
	}

	public MortgageCarInfo(String id){
		super(id);
	}

	@Length(min=0, max=1, message="产权属性长度必须介于 0 和 1 之间")
	public String getPropertyRight() {
		return propertyRight;
	}

	public void setPropertyRight(String propertyRight) {
		this.propertyRight = propertyRight;
	}
	
	@Length(min=0, max=20, message="产权人姓名长度必须介于 0 和 20 之间")
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
	@Length(min=1, max=32, message="申请编号长度必须介于 1 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@Length(min=0, max=50, message="营业执照注册号长度必须介于 0 和 50 之间")
	public String getBusiLicRegNo() {
		return busiLicRegNo;
	}

	public void setBusiLicRegNo(String busiLicRegNo) {
		this.busiLicRegNo = busiLicRegNo;
	}
	
	@Length(min=0, max=4, message="抵押质押类型长度必须介于 0 和 4 之间")
	public String getMortgageType() {
		return mortgageType;
	}

	public void setMortgageType(String mortgageType) {
		this.mortgageType = mortgageType;
	}
	
	@Length(min=0, max=10, message="车牌号码长度必须介于 0 和 10 之间")
	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	@Length(min=0, max=10, message="车辆品牌长度必须介于 0 和 10 之间")
	public String getVehicleBrand() {
		return vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}
	
	@Length(min=0, max=10, message="车辆类型长度必须介于 0 和 10 之间")
	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	
	@Length(min=0, max=50, message="车辆系列长度必须介于 0 和 50 之间")
	public String getVehicleSeries() {
		return vehicleSeries;
	}

	public void setVehicleSeries(String vehicleSeries) {
		this.vehicleSeries = vehicleSeries;
	}
	
	@Length(min=0, max=20, message="财产共有人姓名长度必须介于 0 和 20 之间")
	public String getPropertyOfComOwners() {
		return propertyOfComOwners;
	}

	public void setPropertyOfComOwners(String propertyOfComOwners) {
		this.propertyOfComOwners = propertyOfComOwners;
	}
	
	@Length(min=0, max=1000, message="其他信息长度必须介于 0 和 1000 之间")
	public String getOtherInformation() {
		return otherInformation;
	}

	public void setOtherInformation(String otherInformation) {
		this.otherInformation = otherInformation;
	}
	
	@Length(min=0, max=300, message="工商登记名称长度必须介于 0 和 300 之间")
	public String getBusiRegName() {
		return busiRegName;
	}

	public void setBusiRegName(String busiRegName) {
		this.busiRegName = busiRegName;
	}
	
	@Length(min=0, max=10, message="经营地址：省长度必须介于 0 和 10 之间")
	public String getOperateProvince() {
		return operateProvince;
	}

	public void setOperateProvince(String operateProvince) {
		this.operateProvince = operateProvince;
	}
	
	@Length(min=0, max=10, message="经营地址：市长度必须介于 0 和 10 之间")
	public String getOperateCity() {
		return operateCity;
	}

	public void setOperateCity(String operateCity) {
		this.operateCity = operateCity;
	}
	
	@Length(min=0, max=10, message="经营地址：区长度必须介于 0 和 10 之间")
	public String getOperateDistinct() {
		return operateDistinct;
	}

	public void setOperateDistinct(String operateDistinct) {
		this.operateDistinct = operateDistinct;
	}
	
	@Length(min=0, max=300, message="经营地址详细长度必须介于 0 和 300 之间")
	public String getOperateDetails() {
		return operateDetails;
	}

	public void setOperateDetails(String operateDetails) {
		this.operateDetails = operateDetails;
	}
	
	@Length(min=0, max=300, message="法人名称长度必须介于 0 和 300 之间")
	public String getCorporationName() {
		return corporationName;
	}

	public void setCorporationName(String corporationName) {
		this.corporationName = corporationName;
	}
	
	@Length(min=0, max=18, message="法人证件号长度必须介于 0 和 18 之间")
	public String getCorporationCardIdNo() {
		return corporationCardIdNo;
	}

	public void setCorporationCardIdNo(String corporationCardIdNo) {
		this.corporationCardIdNo = corporationCardIdNo;
	}
	
	
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	@Length(min=0, max=20, message="委托人名称长度必须介于 0 和 20 之间")
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	@Length(min=0, max=18, message="委托人身份号长度必须介于 0 和 18 之间")
	public String getClientIdNo() {
		return clientIdNo;
	}

	public void setClientIdNo(String clientIdNo) {
		this.clientIdNo = clientIdNo;
	}
	
	@Length(min=0, max=15, message="委托人手机号长度必须介于 0 和 15 之间")
	public String getClientPhone() {
		return clientPhone;
	}

	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}
	
	@Length(min=0, max=4, message="委托人与公司的关系长度必须介于 0 和 4 之间")
	public String getClientCompanyRelation() {
		return clientCompanyRelation;
	}

	public void setClientCompanyRelation(String clientCompanyRelation) {
		this.clientCompanyRelation = clientCompanyRelation;
	}

	public MortgageCarEvaluateInfo getMortgageCarEvaluateInfo() {
		return mortgageCarEvaluateInfo;
	}

	public void setMortgageCarEvaluateInfo(MortgageCarEvaluateInfo mortgageCarEvaluateInfo) {
		this.mortgageCarEvaluateInfo = mortgageCarEvaluateInfo;
	}

	public String getIsPushData() {
		return isPushData;
	}

	public void setIsPushData(String isPushData) {
		this.isPushData = isPushData;
	}


}