package com.resoft.outinterface.SV.client2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 企业基础信息
 * 
 * @author wangguodong
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanyBasicInformation {
	@XmlElement(name = "work_id")
	private String work_id;// 子工单编号
	@XmlElement(name = "c_operatingPeriod")
	private String c_operatingPeriod;// 33、经营期限
	@XmlElement(name = "c_entryName")
	private String c_entryName; // 12、工商登记名称
	@XmlElement(name = "c_unifiedSocialCreditCode")
	private String c_unifiedSocialCreditCode; // 13、统一社会信用代码
	@XmlElement(name = "c_organizationCode")
	private String c_organizationCode;// 14、组织机构代码
	@XmlElement(name = "c_busRegistrationNumber")
	private String c_busRegistrationNumber; // 15、营业执照注册号
	@XmlElement(name = "c_registrationNumberCountry")
	private String c_registrationNumberCountry;// 16、登记税号(国)
	@XmlElement(name = "c_registrationNumberGround")
	private String c_registrationNumberGround; // 17、登记税号(地)
	@XmlElement(name = "c_enterpriseLand")
	private String c_enterpriseLand;// 18、企业用地
	@XmlElement(name = "c_enterpriseType")
	private String c_enterpriseType;// 19、企业类型
	@XmlElement(name = "c_enterpriseProperty")
	private String c_enterpriseProperty;// 20、企业性质
	@XmlElement(name = "c_lawPersonName")
	private String c_lawPersonName;// 21、法人代表姓名
	@XmlElement(name = "c_lawPersonNumber")
	private String c_lawPersonNumber;// 22、法人代表身份证号
	@XmlElement(name = "c_lawPersonPhoneNumber")
	private String c_lawPersonPhoneNumber;// 23、法人代表手机号
	@XmlElement(name = "c_registeredCapital")
	private String c_registeredCapital; // 24、注册资金(万元)
	@XmlElement(name = "c_foundingTime")
	private String c_foundingTime;// 25、成立时间
	@XmlElement(name = "c_registeredAddress")
	private String c_registeredAddress;// 26、注册地址区
	@XmlElement(name = "c_registeredDetailAddress")
	private String c_registeredDetailAddress;// 27、注册详细地址
	@XmlElement(name = "c_businessAddress")
	private String c_businessAddress; // 经营地址区
	@XmlElement(name = "c_businessDetailAddress")
	private String c_businessDetailAddress;// 29、经营详细地址
	@XmlElement(name = "c_whetherConsistent")
	private String c_whetherConsistent; // 30、经营地址与注册地址是否一致
	@XmlElement(name = "c_numberEmployees")
	private String c_numberEmployees;// 31、员工数量
	@XmlElement(name = "c_operatingArea")
	private String c_operatingArea;// 32、经营面积
	@XmlElement(name = "c_mainBusiness")
	private String c_mainBusiness; // 34、主营业务
	@XmlElement(name = "c_annualTurnover")
	private String c_annualTurnover;// 35、年营业额
	@XmlElement(name = "c_specialOperatingPermit")
	private String c_specialOperatingPermit;// 36、特殊经营许可证
	@XmlElement(name = "c_specialPermitStartTime")
	private String c_specialPermitStartTime;// 37、特殊经营许可证开始日期
	@XmlElement(name = "c_specialPermitEndTime")
	private String c_specialPermitEndTime; // 38、特殊经营许可证结束日期
	@XmlElement(name = "c_whetherProperty")
	private String c_whetherProperty; // 39、企业名下是否有房产

	public String getWorkId() {
		return work_id;
	}

	public void setWorkId(String workId) {
		this.work_id = workId;
	}

	public String getOperatingPeriod() {
		return c_operatingPeriod;
	}

	public void setOperatingPeriod(String operatingPeriod) {
		this.c_operatingPeriod = operatingPeriod;
	}

	public String getEntryName() {
		return c_entryName;
	}

	public void setEntryName(String entryName) {
		this.c_entryName = entryName;
	}

	public String getUnifiedSocialCreditCode() {
		return c_unifiedSocialCreditCode;
	}

	public void setUnifiedSocialCreditCode(String unifiedSocialCreditCode) {
		this.c_unifiedSocialCreditCode = unifiedSocialCreditCode;
	}

	public String getOrganizationCode() {
		return c_organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.c_organizationCode = organizationCode;
	}

	public String getBusRegistrationNumber() {
		return c_busRegistrationNumber;
	}

	public void setBusRegistrationNumber(String busRegistrationNumber) {
		this.c_busRegistrationNumber = busRegistrationNumber;
	}

	public String getRegistrationNumberCountry() {
		return c_registrationNumberCountry;
	}

	public void setRegistrationNumberCountry(String registrationNumberCountry) {
		this.c_registrationNumberCountry = registrationNumberCountry;
	}

	public String getRegistrationNumberGround() {
		return c_registrationNumberGround;
	}

	public void setRegistrationNumberGround(String registrationNumberGround) {
		this.c_registrationNumberGround = registrationNumberGround;
	}

	public String getEnterpriseLand() {
		return c_enterpriseLand;
	}

	public void setEnterpriseLand(String enterpriseLand) {
		this.c_enterpriseLand = enterpriseLand;
	}

	public String getEnterpriseType() {
		return c_enterpriseType;
	}

	public void setEnterpriseType(String enterpriseType) {
		this.c_enterpriseType = enterpriseType;
	}

	public String getEnterpriseProperty() {
		return c_enterpriseProperty;
	}

	public void setEnterpriseProperty(String enterpriseProperty) {
		this.c_enterpriseProperty = enterpriseProperty;
	}

	public String getLawPersonName() {
		return c_lawPersonName;
	}

	public void setLawPersonName(String lawPersonName) {
		this.c_lawPersonName = lawPersonName;
	}

	public String getLawPersonNumber() {
		return c_lawPersonNumber;
	}

	public void setLawPersonNumber(String lawPersonNumber) {
		this.c_lawPersonNumber = lawPersonNumber;
	}

	public String getLawPersonPhoneNumber() {
		return c_lawPersonPhoneNumber;
	}

	public void setLawPersonPhoneNumber(String lawPersonPhoneNumber) {
		this.c_lawPersonPhoneNumber = lawPersonPhoneNumber;
	}

	public String getRegisteredCapital() {
		return c_registeredCapital;
	}

	public void setRegisteredCapital(String registeredCapital) {
		this.c_registeredCapital = registeredCapital;
	}

	public String getFoundingTime() {
		return c_foundingTime;
	}

	public void setFoundingTime(String foundingTime) {
		this.c_foundingTime = foundingTime;
	}

	public String getRegisteredAddress() {
		return c_registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.c_registeredAddress = registeredAddress;
	}

	public String getRegisteredDetailAddress() {
		return c_registeredDetailAddress;
	}

	public void setRegisteredDetailAddress(String registeredDetailAddress) {
		this.c_registeredDetailAddress = registeredDetailAddress;
	}

	public String getBusinessAddress() {
		return c_businessAddress;
	}

	public void setBusinessAddress(String businessAddress) {
		this.c_businessAddress = businessAddress;
	}

	public String getBusinessDetailAddress() {
		return c_businessDetailAddress;
	}

	public void setBusinessDetailAddress(String businessDetailAddress) {
		this.c_businessDetailAddress = businessDetailAddress;
	}

	public String getWhetherConsistent() {
		return c_whetherConsistent;
	}

	public void setWhetherConsistent(String whetherConsistent) {
		this.c_whetherConsistent = whetherConsistent;
	}

	public String getNumberEmployees() {
		return c_numberEmployees;
	}

	public void setNumberEmployees(String numberEmployees) {
		this.c_numberEmployees = numberEmployees;
	}

	public String getOperatingArea() {
		return c_operatingArea;
	}

	public void setOperatingArea(String operatingArea) {
		this.c_operatingArea = operatingArea;
	}

	public String getMainBusiness() {
		return c_mainBusiness;
	}

	public void setMainBusiness(String mainBusiness) {
		this.c_mainBusiness = mainBusiness;
	}

	public String getAnnualTurnover() {
		return c_annualTurnover;
	}

	public void setAnnualTurnover(String annualTurnover) {
		this.c_annualTurnover = annualTurnover;
	}

	public String getSpecialOperatingPermit() {
		return c_specialOperatingPermit;
	}

	public void setSpecialOperatingPermit(String specialOperatingPermit) {
		this.c_specialOperatingPermit = specialOperatingPermit;
	}

	public String getSpecialPermitStartTime() {
		return c_specialPermitStartTime;
	}

	public void setSpecialPermitStartTime(String specialPermitStartTime) {
		this.c_specialPermitStartTime = specialPermitStartTime;
	}

	public String getSpecialPermitEndTime() {
		return c_specialPermitEndTime;
	}

	public void setSpecialPermitEndTime(String specialPermitEndTime) {
		this.c_specialPermitEndTime = specialPermitEndTime;
	}

	public String getWhetherProperty() {
		return c_whetherProperty;
	}

	public void setWhetherProperty(String whetherProperty) {
		this.c_whetherProperty = whetherProperty;
	}

}
