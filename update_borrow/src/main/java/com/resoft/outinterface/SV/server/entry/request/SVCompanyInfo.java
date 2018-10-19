package com.resoft.outinterface.SV.server.entry.request;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.thinkgem.jeesite.common.utils.IdGen;

/**
 * SV回盘企业Entity
 * 
 * @author admin
 * @version 2016-04-21
 */
@XmlAccessorType(XmlAccessType.NONE)
public class SVCompanyInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String applyNo; // 申请编号
	private Date createDate; // 创建日期
	private Date updateDate; // 更新日期
	private String delFlag; // 删除标记（0：正常；1：删除；2：审核）

	@XmlElement(required = false, name = "BUSI_REG_NAME")
	private String busiRegName; // 工商登记名称
	@XmlElement(required = false, name = "UN_SOC_CREDIT_NO")
	private String unSocCreditNo; // 统一社会信用代码
	@XmlElement(required = false, name = "REGISTER_CODE")
	private String registerCode; // 登记注册代码
	@XmlElement(required = false, name = "ORGANIZATION_NO")
	private String organizationNo; // 组织机构代码
	@XmlElement(required = false, name = "BUSI_LIC_REG_NO")
	private String busiLicRegNo; // 营业执照注册号
	@XmlElement(required = false, name = "REGISTER_NATIONAL_TAX_NO")
	private String registerNationalTaxNo; // 登记税号（国）
	@XmlElement(required = false, name = "REGISTER_LAND_TAX_NO")
	private String registerLandTaxNo; // 登记税号（地）
	@XmlElement(required = false, name = "COMPANY_LAND_USE")
	private String companyLandUse; // 企业用地(字典：COMPANY_LAND_USE)
	@XmlElement(required = false, name = "COMPANY_TYPE")
	private String companyType; // 企业类型（字典类型：COMPANY_TYPE）
	@XmlElement(required = false, name = "COMPANY_PROPERTY")
	private String companyProperty; // 企业性质
	@XmlElement(required = false, name = "COMPANY_STATUS")
	private String companyStatus; // 企业状况(字典：COMPANY_STATUS)
	@XmlElement(required = false, name = "COMPANY_SITE")
	private String companySite; // 企业网址
	@XmlElement(required = false, name = "CORPORATION_NAME")
	private String corporationName; // 法人名称
	@XmlElement(required = false, name = "CORPORATION_CARD_ID_NO")
	private String corporationCardIdNo; // 法人证件号
	@XmlElement(required = false, name = "CORPORATION_MOBILE")
	private String corporationMobile; // 法人手机号
	@XmlElement(required = false, name = "REGISTER_CAPITAL")
	private String registerCapital; // 注册资金
	@XmlElement(required = false, name = "FOUND_DATE")
	private Date foundDate; // 成立日期
	@XmlElement(required = false, name = "REG_PROVINCE")
	private String regProvince; // 注册地址：省
	@XmlElement(required = false, name = "REG_CITY")
	private String regCity; // 注册地址：市
	@XmlElement(required = false, name = "REG_DISTINCT")
	private String regDistinct; // 注册地址：区
	@XmlElement(required = false, name = "REG_DETAILS")
	private String regDetails; // 注册地址明细
	@XmlElement(required = false, name = "OPERATE_PROVINCE")
	private String operateProvince; // 经营地址：省
	@XmlElement(required = false, name = "OPERATE_CITY")
	private String operateCity; // 经营地址：市
	@XmlElement(required = false, name = "OPERATE_DISTINCT")
	private String operateDistinct; // 经营地址：区
	@XmlElement(required = false, name = "OPERATE_DETAILS")
	private String operateDetails; // 经营地址详细
	@XmlElement(required = false, name = "IS_ADDR_RESI")
	private String isAddrResi; // 经营地址与注册地址是否一致(字典类型：yes_no)
	@XmlElement(required = false, name = "CORE_BUSINESS")
	private String coreBusiness; // 主营范围
	@XmlElement(required = false, name = "CURR_STAFF_NUM")
	private String currStaffNum; // 当前员工人数
	@XmlElement(required = false, name = "OPERATE_AREAS")
	private String operateAreas; // 经营面积
	@XmlElement(required = false, name = "MAIN_BUSINESS")
	private String mainBusiness; // 主营业务
	@XmlElement(required = false, name = "ANNUAL_TURNOVER")
	private String annualTurnover; // 年营业额
	@XmlElement(required = false, name = "SPECIAL_OPERATE_PERMIT")
	private String specialOperatePermit; // 特殊经营许可证
	@XmlElement(required = false, name = "SPECIAL_OPERATE_START_DATE")
	private Date specialOperateStartDate; // 特殊经营许可证开始日期
	@XmlElement(required = false, name = "SPECIAL_OPERATE_END_DATE")
	private Date specialOperateEndDate; // 特殊经营许可证结束日期
	@XmlElement(required = false, name = "IS_HAVE_HOUSES")
	private String isHaveHouses; // 企业名下是否有房产
	@XmlElement(required = false, name = "STATE_OF_TAXATION")
	private String stateOfTaxation; // 纳税情况
	@XmlElement(required = false, name = "CAPITAL_STRUCTURE_COM")
	private String capitalStructureCom; // 股本结构描述（大文本，1000字）

	public SVCompanyInfo() {
		super();
	}

	public String getBusiRegName() {
		return busiRegName;
	}

	public void setBusiRegName(String busiRegName) {
		this.busiRegName = busiRegName;
	}

	public String getUnSocCreditNo() {
		return unSocCreditNo;
	}

	public void setUnSocCreditNo(String unSocCreditNo) {
		this.unSocCreditNo = unSocCreditNo;
	}

	public String getRegisterCode() {
		return registerCode;
	}

	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
	}

	public String getOrganizationNo() {
		return organizationNo;
	}

	public void setOrganizationNo(String organizationNo) {
		this.organizationNo = organizationNo;
	}

	public String getBusiLicRegNo() {
		return busiLicRegNo;
	}

	public void setBusiLicRegNo(String busiLicRegNo) {
		this.busiLicRegNo = busiLicRegNo;
	}

	public String getRegisterNationalTaxNo() {
		return registerNationalTaxNo;
	}

	public void setRegisterNationalTaxNo(String registerNationalTaxNo) {
		this.registerNationalTaxNo = registerNationalTaxNo;
	}

	public String getRegisterLandTaxNo() {
		return registerLandTaxNo;
	}

	public void setRegisterLandTaxNo(String registerLandTaxNo) {
		this.registerLandTaxNo = registerLandTaxNo;
	}

	public String getCompanyLandUse() {
		return companyLandUse;
	}

	public void setCompanyLandUse(String companyLandUse) {
		this.companyLandUse = companyLandUse;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getCompanyProperty() {
		return companyProperty;
	}

	public void setCompanyProperty(String companyProperty) {
		this.companyProperty = companyProperty;
	}

	public String getCompanyStatus() {
		return companyStatus;
	}

	public void setCompanyStatus(String companyStatus) {
		this.companyStatus = companyStatus;
	}

	public String getCompanySite() {
		return companySite;
	}

	public void setCompanySite(String companySite) {
		this.companySite = companySite;
	}

	public String getCorporationName() {
		return corporationName;
	}

	public void setCorporationName(String corporationName) {
		this.corporationName = corporationName;
	}

	public String getCorporationCardIdNo() {
		return corporationCardIdNo;
	}

	public void setCorporationCardIdNo(String corporationCardIdNo) {
		this.corporationCardIdNo = corporationCardIdNo;
	}

	public String getCorporationMobile() {
		return corporationMobile;
	}

	public void setCorporationMobile(String corporationMobile) {
		this.corporationMobile = corporationMobile;
	}

	public String getRegisterCapital() {
		return registerCapital;
	}

	public void setRegisterCapital(String registerCapital) {
		this.registerCapital = registerCapital;
	}

	public Date getFoundDate() {
		return foundDate;
	}

	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
	}

	public String getRegProvince() {
		return regProvince;
	}

	public void setRegProvince(String regProvince) {
		this.regProvince = regProvince;
	}

	public String getRegCity() {
		return regCity;
	}

	public void setRegCity(String regCity) {
		this.regCity = regCity;
	}

	public String getRegDistinct() {
		return regDistinct;
	}

	public void setRegDistinct(String regDistinct) {
		this.regDistinct = regDistinct;
	}

	public String getRegDetails() {
		return regDetails;
	}

	public void setRegDetails(String regDetails) {
		this.regDetails = regDetails;
	}

	public String getOperateProvince() {
		return operateProvince;
	}

	public void setOperateProvince(String operateProvince) {
		this.operateProvince = operateProvince;
	}

	public String getOperateCity() {
		return operateCity;
	}

	public void setOperateCity(String operateCity) {
		this.operateCity = operateCity;
	}

	public String getOperateDistinct() {
		return operateDistinct;
	}

	public void setOperateDistinct(String operateDistinct) {
		this.operateDistinct = operateDistinct;
	}

	public String getOperateDetails() {
		return operateDetails;
	}

	public void setOperateDetails(String operateDetails) {
		this.operateDetails = operateDetails;
	}

	public String getIsAddrResi() {
		return isAddrResi;
	}

	public void setIsAddrResi(String isAddrResi) {
		this.isAddrResi = isAddrResi;
	}

	public String getCoreBusiness() {
		return coreBusiness;
	}

	public void setCoreBusiness(String coreBusiness) {
		this.coreBusiness = coreBusiness;
	}

	public String getCurrStaffNum() {
		return currStaffNum;
	}

	public void setCurrStaffNum(String currStaffNum) {
		this.currStaffNum = currStaffNum;
	}

	public String getOperateAreas() {
		return operateAreas;
	}

	public void setOperateAreas(String operateAreas) {
		this.operateAreas = operateAreas;
	}

	public String getMainBusiness() {
		return mainBusiness;
	}

	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}

	public String getAnnualTurnover() {
		return annualTurnover;
	}

	public void setAnnualTurnover(String annualTurnover) {
		this.annualTurnover = annualTurnover;
	}

	public String getSpecialOperatePermit() {
		return specialOperatePermit;
	}

	public void setSpecialOperatePermit(String specialOperatePermit) {
		this.specialOperatePermit = specialOperatePermit;
	}

	public Date getSpecialOperateStartDate() {
		return specialOperateStartDate;
	}

	public void setSpecialOperateStartDate(Date specialOperateStartDate) {
		this.specialOperateStartDate = specialOperateStartDate;
	}

	public Date getSpecialOperateEndDate() {
		return specialOperateEndDate;
	}

	public void setSpecialOperateEndDate(Date specialOperateEndDate) {
		this.specialOperateEndDate = specialOperateEndDate;
	}

	public String getIsHaveHouses() {
		return isHaveHouses;
	}

	public void setIsHaveHouses(String isHaveHouses) {
		this.isHaveHouses = isHaveHouses;
	}

	public String getStateOfTaxation() {
		return stateOfTaxation;
	}

	public void setStateOfTaxation(String stateOfTaxation) {
		this.stateOfTaxation = stateOfTaxation;
	}

	public String getCapitalStructureCom() {
		return capitalStructureCom;
	}

	public void setCapitalStructureCom(String capitalStructureCom) {
		this.capitalStructureCom = capitalStructureCom;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
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

	public void preInsert() {
		setId(IdGen.uuid());
		this.createDate = new Date();
	}

}