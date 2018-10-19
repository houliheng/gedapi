package com.resoft.outinterface.SV.client2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 个人基础信息
 * 
 * @author wangguodong
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonalBasicData {
	@XmlElement(name = "work_id")
	private String work_id; // 子工单编号
	@XmlElement(name = "p_whetherBusiness")
	private String p_whetherBusiness;// 42、是否有企业
	@XmlElement(name = "p_documentType")
	private String p_documentType; // 1、证件类型
	@XmlElement(name = "p_identificationNumber")
	private String p_identificationNumber;// 2、证件号码
	@XmlElement(name = "p_idStartDate")
	private String p_idStartDate;// 3、身份证起始日期
	@XmlElement(name = "p_idLastDate")
	private String p_idLastDate; // 4、身份证结束日期
	@XmlElement(name = "p_mobilePhone")
	private String p_mobilePhone; // 26、移动电话
	@XmlElement(name = "p_nation")
	private String p_nation; // 5、民族
	@XmlElement(name = "p_birthday")
	private String p_birthday;// 6、出生日期
	@XmlElement(name = "p_age")
	private String p_age; // 7、年龄
	@XmlElement(name = "p_sex")
	private String p_sex;// 8、性别
	@XmlElement(name = "p_maritalStatus")
	private String p_maritalStatus;// 9、婚姻状况
	@XmlElement(name = "p_highestDegree")
	private String p_highestDegree;// 13、最高学历
	@XmlElement(name = "p_otherContacts")
	private String p_otherContacts;// 37、其他联系方式
	@XmlElement(name = "p_personalAnnualIncome")
	private String p_personalAnnualIncome;// 31、个人年收入
	@XmlElement(name = "p_sourceIncome")
	private String p_sourceIncome;// 32、收入来源
	@XmlElement(name = "p_electronicMail")
	private String p_electronicMail;// 33、电子邮箱
	@XmlElement(name = "p_microblogNumber")
	private String p_microblogNumber;// 34、微博号码
	@XmlElement(name = "p_weChatNumber")
	private String p_weChatNumber; // 35、微信号码
	@XmlElement(name = "p_QQNumber")
	private String p_QQNumber; // 36、QQ号码

	public String getWorkId() {
		return work_id;
	}

	public void setWorkId(String workId) {
		this.work_id = workId;
	}

	public String getWhetherBusiness() {
		return p_whetherBusiness;
	}

	public void setWhetherBusiness(String whetherBusiness) {
		this.p_whetherBusiness = whetherBusiness;
	}

	public String getDocumentType() {
		return p_documentType;
	}

	public void setDocumentType(String documentType) {
		this.p_documentType = documentType;
	}

	public String getIdentificationNumber() {
		return p_identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.p_identificationNumber = identificationNumber;
	}

	public String getIdStartDate() {
		return p_idStartDate;
	}

	public void setIdStartDate(String idStartDate) {
		this.p_idStartDate = idStartDate;
	}

	public String getIdLastDate() {
		return p_idLastDate;
	}

	public void setIdLastDate(String idLastDate) {
		this.p_idLastDate = idLastDate;
	}

	public String getMobilePhone() {
		return p_mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.p_mobilePhone = mobilePhone;
	}

	public String getNation() {
		return p_nation;
	}

	public void setNation(String nation) {
		this.p_nation = nation;
	}

	public String getBirthday() {
		return p_birthday;
	}

	public void setBirthday(String birthday) {
		this.p_birthday = birthday;
	}

	public String getAge() {
		return p_age;
	}

	public void setAge(String age) {
		this.p_age = age;
	}

	public String getSex() {
		return p_sex;
	}

	public void setSex(String sex) {
		this.p_sex = sex;
	}

	public String getMaritalStatus() {
		return p_maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.p_maritalStatus = maritalStatus;
	}

	public String getHighestDegree() {
		return p_highestDegree;
	}

	public void setHighestDegree(String highestDegree) {
		this.p_highestDegree = highestDegree;
	}

	public String getOtherContacts() {
		return p_otherContacts;
	}

	public void setOtherContacts(String otherContacts) {
		this.p_otherContacts = otherContacts;
	}

	public String getPersonalAnnualIncome() {
		return p_personalAnnualIncome;
	}

	public void setPersonalAnnualIncome(String personalAnnualIncome) {
		this.p_personalAnnualIncome = personalAnnualIncome;
	}

	public String getSourceIncome() {
		return p_sourceIncome;
	}

	public void setSourceIncome(String sourceIncome) {
		this.p_sourceIncome = sourceIncome;
	}

	public String getElectronicMail() {
		return p_electronicMail;
	}

	public void setElectronicMail(String electronicMail) {
		this.p_electronicMail = electronicMail;
	}

	public String getMicroblogNumber() {
		return p_microblogNumber;
	}

	public void setMicroblogNumber(String microblogNumber) {
		this.p_microblogNumber = microblogNumber;
	}

	public String getWeChatNumber() {
		return p_weChatNumber;
	}

	public void setWeChatNumber(String weChatNumber) {
		this.p_weChatNumber = weChatNumber;
	}

	public String getQQNumber() {
		return p_QQNumber;
	}

	public void setQQNumber(String qQNumber) {
		this.p_QQNumber = qQNumber;
	}

}
