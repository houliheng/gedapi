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
 * SV回盘客户基本信息Entity
 * 
 * @author admin
 * @version 2016-04-21
 */
@XmlAccessorType(XmlAccessType.NONE)
public class SVCustInfo implements Serializable {

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
	
	@XmlElement(required = true, name = "CUST_NAME")
	private String custName; // 名称
	@XmlElement(required = true, name = "ID_TYPE")
	private String idType; // 证件类型
	@XmlElement(required = true, name = "ID_NUM")
	private String idNum; // 证件号
	@XmlElement(required = true, name = "PERSON_ID_START_DATE")
	private Date personIdStartDate; // 身份证起始日期
	@XmlElement(required = true, name = "PERSON_ID_END_DATE")
	private Date personIdEndDate; // 身份证终止日期
	@XmlElement(required = true, name = "MOBILE_NUM")
	private String mobileNum; // 手机号
	@XmlElement(required = true, name = "NATION_NO")
	private String nationNo; // 民族
	@XmlElement(required = true, name = "BIRTH_DAY")
	private Date birthDay; // 出生日期
	@XmlElement(required = true, name = "AGE_NO")
	private String ageNo; // 年龄
	@XmlElement(required = true, name = "SEX_NO")
	private String sexNo; // 性别（字典类型：sex）
	@XmlElement(required = true, name = "WED_STATUS")
	private String wedStatus; // 婚姻状况（字典类型：WED_STATUS）
	@XmlElement(required = true, name = "CHILDREN_SON")
	private String childrenSon; // 子女：男（数目）（字典类型：CHILDREN_NUM）
	@XmlElement(required = true, name = "CHILDREN_GIRL")
	private String childrenGirl; // 子女：女（数目）（字典类型：CHILDREN_NUM）
	@XmlElement(required = true, name = "HOUSEHOLD_SPENDING_MONTH")
	private String householdSpendingMonth; // 每月家庭支出
	@XmlElement(required = true, name = "TOP_EDUCATION_NO")
	private String topEducationNo; // 最高学历(教育程度)（字典类型：EDUCATION）
	@XmlElement(required = true, name = "HOME_PHONE_NO")
	private String homePhoneNo; // 家庭电话
	@XmlElement(required = true, name = "HOSE_PHONE_NO")
	private String hosePhoneNo; // 住宅电话
	@XmlElement(required = true, name = "REG_IN_DATE")
	private Date regInDate; // 现住宅地居住日期
	@XmlElement(required = true, name = "CITY_IN_DATE")
	private Date cityInDate; // 来本市日期
	@XmlElement(required = true, name = "REG_PROVINCE")
	private String regProvince; // 户籍地址省
	@XmlElement(required = true, name = "REG_CITY")
	private String regCity; // 户籍地址市
	@XmlElement(required = true, name = "REG_DISTINCT")
	private String regDistinct; // 户籍地址区
	@XmlElement(required = true, name = "REG_DETAILS")
	private String regDetails; // 户籍地址详细
	@XmlElement(required = true, name = "IS_LOCAL")
	private String isLocal; // 是否本地户籍
	@XmlElement(required = true, name = "CONT_PROVINCE")
	private String contProvince; // 现居地址省
	@XmlElement(required = true, name = "CONT_CITY")
	private String contCity; // 现居地址市
	@XmlElement(required = true, name = "CONT_DISTINCT")
	private String contDistinct; // 现居地址区
	@XmlElement(required = true, name = "CONT_DETAILS")
	private String contDetails; // 现居地址详细
	@XmlElement(required = true, name = "ADDR_RESI")
	private String addrResi; // 现居地与户籍地址是否一致
	@XmlElement(required = true, name = "IS_FIXEDHOUSE")
	private String isFixedhouse; // 有无固定居所
	@XmlElement(required = true, name = "LIVING_STATUS")
	private String livingStatus; // 居住状况（字典类型：LivingStatus）
	@XmlElement(required = true, name = "LIVING_STATUS_DESC")
	private String livingStatusDesc; // 居住状况说明
	@XmlElement(required = true, name = "PER_ANNUAL_INCOME")
	private String perAnnualIncome; // 个人年收入
	@XmlElement(required = true, name = "SOURCE_OF_INCOME")
	private String sourceOfIncome; // 收入来源
	@XmlElement(required = true, name = "EMAIL_NO")
	private String emailNo; // 电子邮箱
	@XmlElement(required = true, name = "MICRO_NO")
	private String microNo; // 微博号码
	@XmlElement(required = true, name = "WECHAT_NO")
	private String wechatNo; // 微信号码
	@XmlElement(required = true, name = "QQ_NO")
	private String qqNo; // QQ号码
	@XmlElement(required = true, name = "OTHER_CONTACT_TYPE")
	private String otherContactType; // 其他联系方式
	@XmlElement(required = true, name = "CATEGORY_MAIN")
	private String categoryMain; // 所属行业-门类(行业表-levle1)
	@XmlElement(required = true, name = "CATEGORY_LARGE")
	private String categoryLarge; // 所属行业-大类(行业表-levle2)
	@XmlElement(required = true, name = "CATEGORY_MEDIUM")
	private String categoryMedium; // 所属行业-中类(行业表-levle3)
	@XmlElement(required = true, name = "CATEGORY_SMALL")
	private String categorySmall; // 所属行业-小类(行业表-levle4)

	public SVCustInfo() {
		super();
	}


	public SVCustInfo(String id, String applyNo, String remarks, User createBy, Date createDate, User updateBy, Date updateDate, String delFlag, String custName, String idType, String idNum, Date personIdStartDate, Date personIdEndDate, String mobileNum, String nationNo, Date birthDay, String ageNo, String sexNo, String wedStatus, String childrenSon, String childrenGirl, String householdSpendingMonth, String topEducationNo, String homePhoneNo, String hosePhoneNo, Date regInDate, Date cityInDate, String regProvince, String regCity, String regDistinct, String regDetails, String isLocal, String contProvince, String contCity, String contDistinct, String contDetails, String addrResi, String isFixedhouse, String livingStatus, String livingStatusDesc, String perAnnualIncome, String sourceOfIncome, String emailNo, String microNo, String wechatNo, String qqNo, String otherContactType, String categoryMain, String categoryLarge, String categoryMedium, String categorySmall) {
		super();
		this.id = id;
		this.applyNo = applyNo;
		this.remarks = remarks;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.delFlag = delFlag;
		this.custName = custName;
		this.idType = idType;
		this.idNum = idNum;
		this.personIdStartDate = personIdStartDate;
		this.personIdEndDate = personIdEndDate;
		this.mobileNum = mobileNum;
		this.nationNo = nationNo;
		this.birthDay = birthDay;
		this.ageNo = ageNo;
		this.sexNo = sexNo;
		this.wedStatus = wedStatus;
		this.childrenSon = childrenSon;
		this.childrenGirl = childrenGirl;
		this.householdSpendingMonth = householdSpendingMonth;
		this.topEducationNo = topEducationNo;
		this.homePhoneNo = homePhoneNo;
		this.hosePhoneNo = hosePhoneNo;
		this.regInDate = regInDate;
		this.cityInDate = cityInDate;
		this.regProvince = regProvince;
		this.regCity = regCity;
		this.regDistinct = regDistinct;
		this.regDetails = regDetails;
		this.isLocal = isLocal;
		this.contProvince = contProvince;
		this.contCity = contCity;
		this.contDistinct = contDistinct;
		this.contDetails = contDetails;
		this.addrResi = addrResi;
		this.isFixedhouse = isFixedhouse;
		this.livingStatus = livingStatus;
		this.livingStatusDesc = livingStatusDesc;
		this.perAnnualIncome = perAnnualIncome;
		this.sourceOfIncome = sourceOfIncome;
		this.emailNo = emailNo;
		this.microNo = microNo;
		this.wechatNo = wechatNo;
		this.qqNo = qqNo;
		this.otherContactType = otherContactType;
		this.categoryMain = categoryMain;
		this.categoryLarge = categoryLarge;
		this.categoryMedium = categoryMedium;
		this.categorySmall = categorySmall;
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


	@Length(min = 0, max = 20, message = "名称长度必须介于 0 和 20 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Length(min = 0, max = 10, message = "证件类型长度必须介于 0 和 10 之间")
	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	@Length(min = 0, max = 18, message = "证件号长度必须介于 0 和 18 之间")
	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPersonIdStartDate() {
		return personIdStartDate;
	}

	public void setPersonIdStartDate(Date personIdStartDate) {
		this.personIdStartDate = personIdStartDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPersonIdEndDate() {
		return personIdEndDate;
	}

	public void setPersonIdEndDate(Date personIdEndDate) {
		this.personIdEndDate = personIdEndDate;
	}

	@Length(min = 0, max = 15, message = "手机号长度必须介于 0 和 15 之间")
	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	@Length(min = 0, max = 4, message = "民族长度必须介于 0 和 4 之间")
	public String getNationNo() {
		return nationNo;
	}

	public void setNationNo(String nationNo) {
		this.nationNo = nationNo;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	@Length(min = 0, max = 3, message = "年龄长度必须介于 0 和 3 之间")
	public String getAgeNo() {
		return ageNo;
	}

	public void setAgeNo(String ageNo) {
		this.ageNo = ageNo;
	}

	@Length(min = 0, max = 1, message = "性别（字典类型：sex）长度必须介于 0 和 1 之间")
	public String getSexNo() {
		return sexNo;
	}

	public void setSexNo(String sexNo) {
		this.sexNo = sexNo;
	}

	@Length(min = 0, max = 1, message = "婚姻状况（字典类型：WED_STATUS）长度必须介于 0 和 1 之间")
	public String getWedStatus() {
		return wedStatus;
	}

	public void setWedStatus(String wedStatus) {
		this.wedStatus = wedStatus;
	}

	@Length(min = 0, max = 4, message = "子女：男（数目）（字典类型：CHILDREN_NUM）长度必须介于 0 和 4 之间")
	public String getChildrenSon() {
		return childrenSon;
	}

	public void setChildrenSon(String childrenSon) {
		this.childrenSon = childrenSon;
	}

	@Length(min = 0, max = 4, message = "子女：男（数目）（字典类型：CHILDREN_NUM）长度必须介于 0 和 4 之间")
	public String getChildrenGirl() {
		return childrenGirl;
	}

	public void setChildrenGirl(String childrenGirl) {
		this.childrenGirl = childrenGirl;
	}

	public String getHouseholdSpendingMonth() {
		return householdSpendingMonth;
	}

	public void setHouseholdSpendingMonth(String householdSpendingMonth) {
		this.householdSpendingMonth = householdSpendingMonth;
	}

	@Length(min = 0, max = 10, message = "最高学历(教育程度)（字典类型：EDUCATION）长度必须介于 0 和 10 之间")
	public String getTopEducationNo() {
		return topEducationNo;
	}

	public void setTopEducationNo(String topEducationNo) {
		this.topEducationNo = topEducationNo;
	}

	@Length(min = 0, max = 15, message = "家庭电话长度必须介于 0 和 15 之间")
	public String getHomePhoneNo() {
		return homePhoneNo;
	}

	public void setHomePhoneNo(String homePhoneNo) {
		this.homePhoneNo = homePhoneNo;
	}

	@Length(min = 0, max = 15, message = "住宅电话长度必须介于 0 和 15 之间")
	public String getHosePhoneNo() {
		return hosePhoneNo;
	}

	public void setHosePhoneNo(String hosePhoneNo) {
		this.hosePhoneNo = hosePhoneNo;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRegInDate() {
		return regInDate;
	}

	public void setRegInDate(Date regInDate) {
		this.regInDate = regInDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCityInDate() {
		return cityInDate;
	}

	public void setCityInDate(Date cityInDate) {
		this.cityInDate = cityInDate;
	}

	@Length(min = 0, max = 10, message = "户籍地址省长度必须介于 0 和 10 之间")
	public String getRegProvince() {
		return regProvince;
	}

	public void setRegProvince(String regProvince) {
		this.regProvince = regProvince;
	}

	@Length(min = 0, max = 10, message = "户籍地址市长度必须介于 0 和 10 之间")
	public String getRegCity() {
		return regCity;
	}

	public void setRegCity(String regCity) {
		this.regCity = regCity;
	}

	@Length(min = 0, max = 10, message = "户籍地址区长度必须介于 0 和 10 之间")
	public String getRegDistinct() {
		return regDistinct;
	}

	public void setRegDistinct(String regDistinct) {
		this.regDistinct = regDistinct;
	}

	@Length(min = 0, max = 300, message = "户籍地址详细长度必须介于 0 和 300 之间")
	public String getRegDetails() {
		return regDetails;
	}

	public void setRegDetails(String regDetails) {
		this.regDetails = regDetails;
	}

	@Length(min = 0, max = 1, message = "是否本地户籍长度必须介于 0 和 1 之间")
	public String getIsLocal() {
		return isLocal;
	}

	public void setIsLocal(String isLocal) {
		this.isLocal = isLocal;
	}

	@Length(min = 0, max = 10, message = "现居地址省长度必须介于 0 和 10 之间")
	public String getContProvince() {
		return contProvince;
	}

	public void setContProvince(String contProvince) {
		this.contProvince = contProvince;
	}

	@Length(min = 0, max = 10, message = "现居地址市长度必须介于 0 和 10 之间")
	public String getContCity() {
		return contCity;
	}

	public void setContCity(String contCity) {
		this.contCity = contCity;
	}

	@Length(min = 0, max = 10, message = "现居地址区长度必须介于 0 和 10 之间")
	public String getContDistinct() {
		return contDistinct;
	}

	public void setContDistinct(String contDistinct) {
		this.contDistinct = contDistinct;
	}

	@Length(min = 0, max = 300, message = "现居地址详细长度必须介于 0 和 300 之间")
	public String getContDetails() {
		return contDetails;
	}

	public void setContDetails(String contDetails) {
		this.contDetails = contDetails;
	}

	@Length(min = 0, max = 1, message = "现居地与户籍地址是否一致长度必须介于 0 和 1 之间")
	public String getAddrResi() {
		return addrResi;
	}

	public void setAddrResi(String addrResi) {
		this.addrResi = addrResi;
	}

	@Length(min = 0, max = 1, message = "名下是否有房产长度必须介于 0 和 1 之间")
	public String getIsFixedhouse() {
		return isFixedhouse;
	}

	public void setIsFixedhouse(String isFixedhouse) {
		this.isFixedhouse = isFixedhouse;
	}

	@Length(min = 0, max = 4, message = "居住状况（字典类型：LivingStatus）长度必须介于 0 和 4 之间")
	public String getLivingStatus() {
		return livingStatus;
	}

	public void setLivingStatus(String livingStatus) {
		this.livingStatus = livingStatus;
	}

	@Length(min = 0, max = 300, message = "居住状况说明长度必须介于 0 和 300 之间")
	public String getLivingStatusDesc() {
		return livingStatusDesc;
	}

	public void setLivingStatusDesc(String livingStatusDesc) {
		this.livingStatusDesc = livingStatusDesc;
	}

	public String getPerAnnualIncome() {
		return perAnnualIncome;
	}

	public void setPerAnnualIncome(String perAnnualIncome) {
		this.perAnnualIncome = perAnnualIncome;
	}

	@Length(min = 0, max = 100, message = "收入来源长度必须介于 0 和 100 之间")
	public String getSourceOfIncome() {
		return sourceOfIncome;
	}

	public void setSourceOfIncome(String sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}

	@Length(min = 0, max = 50, message = "电子邮箱长度必须介于 0 和 50 之间")
	public String getEmailNo() {
		return emailNo;
	}

	public void setEmailNo(String emailNo) {
		this.emailNo = emailNo;
	}

	@Length(min = 0, max = 50, message = "微博号码长度必须介于 0 和 50 之间")
	public String getMicroNo() {
		return microNo;
	}

	public void setMicroNo(String microNo) {
		this.microNo = microNo;
	}

	@Length(min = 0, max = 50, message = "微信号码长度必须介于 0 和 50 之间")
	public String getWechatNo() {
		return wechatNo;
	}

	public void setWechatNo(String wechatNo) {
		this.wechatNo = wechatNo;
	}

	@Length(min = 0, max = 50, message = "QQ号码长度必须介于 0 和 50 之间")
	public String getQqNo() {
		return qqNo;
	}

	public void setQqNo(String qqNo) {
		this.qqNo = qqNo;
	}

	@Length(min = 0, max = 100, message = "其他联系方式长度必须介于 0 和 100 之间")
	public String getOtherContactType() {
		return otherContactType;
	}

	public void setOtherContactType(String otherContactType) {
		this.otherContactType = otherContactType;
	}

	@Length(min = 0, max = 10, message = "所属行业-门类(行业表-levle1)长度必须介于 0 和 10 之间")
	public String getCategoryMain() {
		return categoryMain;
	}

	public void setCategoryMain(String categoryMain) {
		this.categoryMain = categoryMain;
	}

	@Length(min = 0, max = 10, message = "所属行业-大类(行业表-levle2)长度必须介于 0 和 10 之间")
	public String getCategoryLarge() {
		return categoryLarge;
	}

	public void setCategoryLarge(String categoryLarge) {
		this.categoryLarge = categoryLarge;
	}

	@Length(min = 0, max = 10, message = "所属行业-中类(行业表-levle3)长度必须介于 0 和 10 之间")
	public String getCategoryMedium() {
		return categoryMedium;
	}

	public void setCategoryMedium(String categoryMedium) {
		this.categoryMedium = categoryMedium;
	}

	@Length(min = 0, max = 10, message = "所属行业-小类(行业表-levle4)长度必须介于 0 和 10 之间")
	public String getCategorySmall() {
		return categorySmall;
	}

	public void setCategorySmall(String categorySmall) {
		this.categorySmall = categorySmall;
	}
	
	public void preInsert() {
		setId(IdGen.uuid());
		this.createDate = new Date();
	}


}