package com.resoft.credit.companyInfo.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 企业基本信息Entity
 *
 * @author caoyinglong
 * @version 2016-02-27
 */
public class CompanyInfo extends DataEntity<CompanyInfo> {

	private static final long serialVersionUID = 1L;
	private String busiRegName; // 工商登记名称
	private String unSocCreditNo; // 统一社会信用代码
	private Date unSocCreditStartDate; // 统一社会信用代码起始日期
	private Date unSocCreditEndDate; // 统一社会信用代码终止日期
	private String organizationNo; // 组织机构代码
	private Date organizationStartDate; // 组织机构代码起始日期
	private Date organizationEndDate; // 组织机构代码终止日期
	private String corporationName; // 法人名称
	private String busiLicRegNo; // 营业执照注册号
	private Date busiLicRegStartDate; // 营业执照注册号起始日期
	private Date busiLicRegEndDate; // 营业执照注册号终止日期
	private String registerNationalTaxNo; // 登记税号（国）
	private String companyStatus; // 企业状况(字典：COMPANY_STATUS)
	private Date registerNatTaxStartDate; // 登记税号（国）起始日期
	private Date registerNatTaxEndDate; // 登记税号（国）终止日期
	private Date foundDate; // 成立时间
	private String registerLandTaxNo; // 登记税号（地）
	private String currStaffNum; // 当前员工人数
	private Date registerLandTaxStartDate; // 登记税号（地）起始日期
	private Date registerLandTaxEndDate; // 登记税号（地）终止日期
	private BigDecimal lastStaffNum; // 去年员工人数
	private String coreBusiness; // 主营范围
	private String companyLandUse; // 企业用地(字典：LAND_USE)
	private String operateType; // 经营方式（字典表：OPERATE_TYPE)
	private String companyType; // 企业类型
	private BigDecimal registerCapital; // 注册资金
	private String paidCapital; //实缴资本
	private String companyProperty; // 企业性质
	private String registerCode; // 登记注册代码
	private Date registerDate; // 登记注册日期
	private String regProvince; // 注册地址：省
	private String regCity; // 注册地址：市
	private String corporationCardType; // 法人证件类型
	private String regDistinct; // 注册地址：区
	private String corporationCardIdNo; // 法人证件号
	private String regDetails; // 注册地址明细
	private String corporationMobile; // 法人手机号
	private String companySite; // 企业网址
	private String operateProvince; // 经营地址：省
	private String operateCity; // 经营地址：市
	private String operateDistinct; // 经营地址：区
	private String operateDetails; // 经营地址详细 
	private String isAddrResi; // 经营地址与注册地址是否一致(字典类型：yes_no)
	private String categoryMain; // 所属行业-门类(行业表-levle1)
	private String categoryLarge; // 所属行业-大类(行业表-levle2)
	private String categoryMedium; // 所属行业-中类(行业表-levle3)
	private String categorySmall; // 所属行业-小类(行业表-levle4)
	private String operateAreas; // 经营面积
	private String operatePeriod; // 经营期限
	private String mainBusiness; // 主营业务
	private BigDecimal annualTurnover; // 年营业额
	private String specialOperatePermit; // 特殊经营许可证
	private Date specialOperateStartDate; // 特殊经营许可证开始日期
	private Date specialOperateEndDate; // 特殊经营许可证结束日期
	//private Date landEndDate; // 借款到期日期
	private String isHaveHouses; // 企业名下是否有房产
	private String stateOfTaxation; // 纳税情况
	private String capitalStructureCom; // 股本结构描述（大文本，1000字）
	private String currOfficeLifetime; // 办公环境使用年限
	private String isEarnings; //是否有财报
	private String svFlag;
	private String operateArea;//经营区域
	private String registerType;

	// 非表字段---用于存储当前申请编号
	private String currApplyNo;
	private String gedAccount;
	private String roleType;

	//apply_relation -> isConfirm;
	private String isConfirm;

	public String getCurrApplyNo() {
		return currApplyNo;
	}

	public void setCurrApplyNo(String currApplyNo) {
		this.currApplyNo = currApplyNo;
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

	public Date getUnSocCreditStartDate() {
		return unSocCreditStartDate;
	}

	public void setUnSocCreditStartDate(Date unSocCreditStartDate) {
		this.unSocCreditStartDate = unSocCreditStartDate;
	}

	public Date getUnSocCreditEndDate() {
		return unSocCreditEndDate;
	}

	public void setUnSocCreditEndDate(Date unSocCreditEndDate) {
		this.unSocCreditEndDate = unSocCreditEndDate;
	}

	public String getOrganizationNo() {
		return organizationNo;
	}

	public void setOrganizationNo(String organizationNo) {
		this.organizationNo = organizationNo;
	}

	public Date getOrganizationStartDate() {
		return organizationStartDate;
	}

	public void setOrganizationStartDate(Date organizationStartDate) {
		this.organizationStartDate = organizationStartDate;
	}

	public Date getOrganizationEndDate() {
		return organizationEndDate;
	}

	public void setOrganizationEndDate(Date organizationEndDate) {
		this.organizationEndDate = organizationEndDate;
	}

	public String getCorporationName() {
		return corporationName;
	}

	public void setCorporationName(String corporationName) {
		this.corporationName = corporationName;
	}

	public String getBusiLicRegNo() {
		return busiLicRegNo;
	}

	public void setBusiLicRegNo(String busiLicRegNo) {
		this.busiLicRegNo = busiLicRegNo;
	}

	public Date getBusiLicRegStartDate() {
		return busiLicRegStartDate;
	}

	public void setBusiLicRegStartDate(Date busiLicRegStartDate) {
		this.busiLicRegStartDate = busiLicRegStartDate;
	}

	public Date getBusiLicRegEndDate() {
		return busiLicRegEndDate;
	}

	public void setBusiLicRegEndDate(Date busiLicRegEndDate) {
		this.busiLicRegEndDate = busiLicRegEndDate;
	}

	public String getRegisterNationalTaxNo() {
		return registerNationalTaxNo;
	}

	public void setRegisterNationalTaxNo(String registerNationalTaxNo) {
		this.registerNationalTaxNo = registerNationalTaxNo;
	}

	public String getCompanyStatus() {
		return companyStatus;
	}

	public void setCompanyStatus(String companyStatus) {
		this.companyStatus = companyStatus;
	}

	public Date getRegisterNatTaxStartDate() {
		return registerNatTaxStartDate;
	}

	public void setRegisterNatTaxStartDate(Date registerNatTaxStartDate) {
		this.registerNatTaxStartDate = registerNatTaxStartDate;
	}

	public Date getRegisterNatTaxEndDate() {
		return registerNatTaxEndDate;
	}

	public void setRegisterNatTaxEndDate(Date registerNatTaxEndDate) {
		this.registerNatTaxEndDate = registerNatTaxEndDate;
	}

	public Date getFoundDate() {
		return foundDate;
	}

	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
	}

	public String getRegisterLandTaxNo() {
		return registerLandTaxNo;
	}

	public void setRegisterLandTaxNo(String registerLandTaxNo) {
		this.registerLandTaxNo = registerLandTaxNo;
	}

	public String getCurrStaffNum() {
		return currStaffNum;
	}

	public void setCurrStaffNum(String currStaffNum) {
		this.currStaffNum = currStaffNum;
	}

	public Date getRegisterLandTaxStartDate() {
		return registerLandTaxStartDate;
	}

	public void setRegisterLandTaxStartDate(Date registerLandTaxStartDate) {
		this.registerLandTaxStartDate = registerLandTaxStartDate;
	}

	public Date getRegisterLandTaxEndDate() {
		return registerLandTaxEndDate;
	}

	public void setRegisterLandTaxEndDate(Date registerLandTaxEndDate) {
		this.registerLandTaxEndDate = registerLandTaxEndDate;
	}

	public BigDecimal getLastStaffNum() {
		return lastStaffNum;
	}

	public void setLastStaffNum(BigDecimal lastStaffNum) {
		this.lastStaffNum = lastStaffNum;
	}

	public String getCoreBusiness() {
		return coreBusiness;
	}

	public void setCoreBusiness(String coreBusiness) {
		this.coreBusiness = coreBusiness;
	}

	public String getCompanyLandUse() {
		return companyLandUse;
	}

	public void setCompanyLandUse(String companyLandUse) {
		this.companyLandUse = companyLandUse;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public BigDecimal getRegisterCapital() {
		return registerCapital;
	}

	public void setRegisterCapital(BigDecimal registerCapital) {
		this.registerCapital = registerCapital;
	}

	public String getCompanyProperty() {
		return companyProperty;
	}

	public void setCompanyProperty(String companyProperty) {
		this.companyProperty = companyProperty;
	}

	public String getRegisterCode() {
		return registerCode;
	}

	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
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

	public String getCorporationCardType() {
		return corporationCardType;
	}

	public void setCorporationCardType(String corporationCardType) {
		this.corporationCardType = corporationCardType;
	}

	public String getRegDistinct() {
		return regDistinct;
	}

	public void setRegDistinct(String regDistinct) {
		this.regDistinct = regDistinct;
	}

	public String getCorporationCardIdNo() {
		return corporationCardIdNo;
	}

	public void setCorporationCardIdNo(String corporationCardIdNo) {
		this.corporationCardIdNo = corporationCardIdNo;
	}

	public String getRegDetails() {
		return regDetails;
	}

	public void setRegDetails(String regDetails) {
		this.regDetails = regDetails;
	}

	public String getCorporationMobile() {
		return corporationMobile;
	}

	public void setCorporationMobile(String corporationMobile) {
		this.corporationMobile = corporationMobile;
	}

	public String getCompanySite() {
		return companySite;
	}

	public void setCompanySite(String companySite) {
		this.companySite = companySite;
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

	public String getCategoryMain() {
		return categoryMain;
	}

	public void setCategoryMain(String categoryMain) {
		this.categoryMain = categoryMain;
	}

	public String getCategoryLarge() {
		return categoryLarge;
	}

	public void setCategoryLarge(String categoryLarge) {
		this.categoryLarge = categoryLarge;
	}

	public String getCategoryMedium() {
		return categoryMedium;
	}

	public void setCategoryMedium(String categoryMedium) {
		this.categoryMedium = categoryMedium;
	}

	public String getCategorySmall() {
		return categorySmall;
	}

	public void setCategorySmall(String categorySmall) {
		this.categorySmall = categorySmall;
	}

	public String getOperateAreas() {
		return operateAreas;
	}

	public void setOperateAreas(String operateAreas) {
		this.operateAreas = operateAreas;
	}

	public String getOperatePeriod() {
		return operatePeriod;
	}

	public void setOperatePeriod(String operatePeriod) {
		this.operatePeriod = operatePeriod;
	}

	public String getMainBusiness() {
		return mainBusiness;
	}

	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}

	public BigDecimal getAnnualTurnover() {
		return annualTurnover;
	}

	public void setAnnualTurnover(BigDecimal annualTurnover) {
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

	/*public Date getLandEndDate() {
		return landEndDate;
	}

	public void setLandEndDate(Date landEndDate) {
		this.landEndDate = landEndDate;
	}*/

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

	public String getCurrOfficeLifetime() {
		return currOfficeLifetime;
	}

	public void setCurrOfficeLifetime(String currOfficeLifetime) {
		this.currOfficeLifetime = currOfficeLifetime;
	}

	public String getIsEarnings() {
		return isEarnings;
	}

	public void setIsEarnings(String isEarnings) {
		this.isEarnings = isEarnings;
	}

	public String getSvFlag() {
		return svFlag;
	}

	public void setSvFlag(String flag) {
		this.svFlag = flag;
	}

	

	public String getPaidCapital() {
		return paidCapital;
	}

	public void setPaidCapital(String paidCapital) {
		this.paidCapital = paidCapital;
	}

	public String getOperateArea() {
		return operateArea;
	}

	public void setOperateArea(String operateArea) {
		this.operateArea = operateArea;
	}

	public String getGedAccount() {
		return gedAccount;
	}

	public void setGedAccount(String gedAccount) {
		this.gedAccount = gedAccount;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}
	
	
}